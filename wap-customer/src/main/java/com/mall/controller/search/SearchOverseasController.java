package com.mall.controller.search;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.po.TcCountry;
import com.mall.constant.RequestContant;
import com.mall.controller.base.BaseController;
import com.mall.dsearch.api.SearchService;
import com.mall.dsearch.vo.PlainProduct;
import com.mall.dsearch.vo.SearchRequest;
import com.mall.dsearch.vo.SearchResponse;
import com.mall.utils.Constants;
import com.mall.utils.GetObjectAttr;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * Search 相关
 * @author yuanxiayang
 *
 */
@Controller
@RequestMapping("/view/free")
public class SearchOverseasController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(SearchOverseasController.class);	
	
	@RequestMapping("/tax")
	public String toOverseasScene(HttpServletRequest request,HttpServletResponse response,SearchRequest searchRequest,Model model) throws Exception{
		//获取原产国信息
		String countryName = request.getParameter("countryName")==null?"":request.getParameter("countryName");
		String enName = request.getParameter("enName")==null?"":request.getParameter("enName");
		
		BaseDataServiceRpc baseDataServiceRpcApi = RemoteServiceSingleton.getInstance().getBaseDataServiceRpcApi();
		List<TcCountry> tcCountries = baseDataServiceRpcApi.getAppDisplayCountries();
		
		model.addAttribute("tcCountries", tcCountries);
		
		if("".equals(countryName)||"".equals(enName)){
			searchRequest.setCountryName(tcCountries.get(0).getName());
			countryName=tcCountries.get(0).getName();
			enName=tcCountries.get(0).getDescription();
		}else{
			searchRequest.setCountryName(countryName);
		}
		model.addAttribute("curCountryName",enName+" "+countryName);
//		searchRequest.setB2C(true);
		
		//原产国  类目
		searchRequest.setB2C(true);
		String entryType = getEntryType(request);
		searchRequest.setEntryType(entryType);
		SearchService searchApi = RemoteServiceSingleton.getInstance().getSearchApi();
		SearchResponse searchResponse = searchApi.search(searchRequest);
		if(null!=searchResponse && searchResponse.getHits()>0 ){
			List<PlainProduct> items = searchResponse.getItems();
			for(PlainProduct pro:items){
				BigDecimal unitPrice= pro.getUnit_price()==null?new BigDecimal(0):pro.getUnit_price();
				BigDecimal markPrice= pro.getDomestic_price()==null?new BigDecimal(0):pro.getDomestic_price();
				if(markPrice.compareTo(unitPrice)<=0) pro.setDomestic_price(null);
			}
		}
		model.addAttribute("searchResponse",searchResponse);
		
		if(null!=searchResponse && searchResponse.getHits()>0){
			//获取总页数
			Integer totalPage = searchResponse.getHits()/searchRequest.getPageSize();
			totalPage = searchResponse.getHits() % searchRequest.getPageSize()==0 ? totalPage : totalPage+1;
			logger.info("---------------totalPage= "+totalPage);
			//页数
			model.addAttribute("totalPage", totalPage);
		}
		model.addAttribute("countryName",countryName);
		model.addAttribute("searchRequest", searchRequest);
//		model.addAttribute("picUrl",picUrl1);
		model.addAttribute("picUrl",Constants.FILESERVER0);
		return "zhuanti/overseas_scene";
	}
	
	@RequestMapping("/toOverseasSceneScroll")
	public String toOverseasSceneScroll(HttpServletRequest request,HttpServletResponse response,SearchRequest searchRequest, Model model){
		
//		searchRequest.setB2C(true);
		
		logger.info("---------------to searchResultScroll by searchRequest = "+GetObjectAttr.getFiledName(searchRequest));
		//原产国  类目
		SearchService searchApi = RemoteServiceSingleton.getInstance().getSearchApi();
		searchRequest.setB2C(true);
		String entryType = getEntryType(request);
		searchRequest.setEntryType(entryType);
		SearchResponse searchResponse = searchApi.search(searchRequest);
		if(null!=searchResponse && searchResponse.getHits()>0 ){
			List<PlainProduct> items = searchResponse.getItems();
			for(PlainProduct pro:items){
				BigDecimal unitPrice= pro.getUnit_price()==null?new BigDecimal(0):pro.getUnit_price();
				BigDecimal markPrice= pro.getDomestic_price()==null?new BigDecimal(0):pro.getDomestic_price();
				if(markPrice.compareTo(unitPrice)<=0) pro.setDomestic_price(null);
			}
		}
//		model.addAttribute("picUrl",request.getParameter("picUrl"));
		model.addAttribute("picUrl",Constants.FILESERVER0);
		model.addAttribute("searchRequest", searchRequest);
		model.addAttribute("searchResponse", searchResponse);
		
		return "zhuanti/overseas_scene_scroll";
	}
}
