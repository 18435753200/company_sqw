package com.mall.controller.flashsale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mall.constant.RequestContant;
import com.mall.controller.base.BaseController;
import com.mall.dsearch.api.SearchService;
import com.mall.dsearch.vo.SearchRequest;
import com.mall.dsearch.vo.SearchResponse;
import com.mall.utils.Constants;


@Controller
@RequestMapping("/view/zhuoyue")
public class ZhuoyueController extends BaseController {
	
	
	@Autowired
	private SearchService searchService;
	
	
	private static final Logger logger = Logger.getLogger(ZhuoyueController.class);
	
	
	@RequestMapping(value="/toZhuoYue",method=RequestMethod.GET) 
	public String toZhuoYue(Model model,HttpServletRequest hsrequest){
		logger.info("------------卓悦列表页");
		SearchRequest request = new SearchRequest();
		request.setB2C(true);
		request.setZy(true);
		String entryType = getEntryType(hsrequest);
		request.setEntryType(entryType);
		SearchResponse response = searchService.search(request);
		
		//获取总页数
		long totalPage = response.getHits()%request.getPageSize()==0?response.getHits()/request.getPageSize():response.getHits()/request.getPageSize()+1;
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("productList", response);
		model.addAttribute("imgurl", Constants.FILESERVER0);
		
		model.addAttribute("title", "卓悦bonjour");
		logger.info("------------卓悦列表页totalPage="+totalPage);
		return "zhuoyue/zhuoyue";
	}
	
	@RequestMapping(value="/toZhuoYuescroll") 
	public String toZhuoYueScroll(SearchRequest searchRequest,Model model,HttpServletRequest request){
		logger.info("-----------卓悦滚动翻页-"+searchRequest.getPageno());
		
//		SearchRequest request = new SearchRequest();
		searchRequest.setB2C(true);
		searchRequest.setZy(true);
		String entryType = getEntryType(request);
		searchRequest.setEntryType(entryType);
		SearchResponse response = searchService.search(searchRequest);
		
		model.addAttribute("productList", response);
		model.addAttribute("imgurl", Constants.FILESERVER0);
		
		return "zhuoyue/zhuoyue_scroll";
	}
	

}
