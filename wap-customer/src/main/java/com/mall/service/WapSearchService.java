package com.mall.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.dsearch.api.HotService;
import com.mall.dsearch.vo.Suggestion;
import com.mall.dsearch.vo.SuggestionRequest;
import com.mall.utils.CookieUtil;
import com.mall.utils.StringUtil;
import com.mall.wap.proxy.RemoteServiceSingleton;

public class WapSearchService {

	private static final Logger logger = Logger.getLogger(WapSearchService.class);
	public static final String COOKIE_NAME = "near";
	/**
	 * 推荐搜索
	 * @param keyWord
	 * @return
	 */
	public static List<Suggestion> suggestionSearch(String keyWord){
		try {
			
			SuggestionRequest suggestion=new SuggestionRequest();
			suggestion.setKey(keyWord);
			suggestion.equals(false);
			return RemoteServiceSingleton.getInstance().getSuggestionApi().suggestionSearch(suggestion);
		} catch (Exception e) {
			logger.error("call suggestionSearch faild!"+e.getMessage(),e);
			return null;
		}
	}
	
	
	/**
	 * 获取热门搜索
	 * @param model
	 */
	public static List<String> getHotSearch() {
		HotService searchHotWordApi = RemoteServiceSingleton.getInstance().getHotService();
		List<String> findSearchHotWords = searchHotWordApi.hotSearch();
		return findSearchHotWords;
	}
	

	/**
	 * 获取最近搜索
	 * @param request
	 * @param model
	 */
	private void getNearSearch(HttpServletRequest request, Model model){
		//获取cookie
		Cookie cookie = CookieUtil.getCookie(request, COOKIE_NAME);
		if(null!=cookie){
			String value = cookie.getValue();
			String decode_value = "";
			try {
				decode_value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//切割字符串 
			String[] cookieVlaues = decode_value.split("_");
			//去除重复元素
			StringUtil.distinct(cookieVlaues, model);
		}
	}
	
	/**
	 * 清楚最近的搜索记录
	 * @param request
	 * @param model
	 */
	@RequestMapping("/delNearSearch")
	public String delNearSearch(HttpServletRequest request,HttpServletResponse response, Model model){
		Cookie cookie = CookieUtil.getCookie(request, "near");
		if(null!=cookie){
			CookieUtil.deleteCookie(response, cookie);
		}
		return "redirect:/searchController/toSearchPage";
	}
}
