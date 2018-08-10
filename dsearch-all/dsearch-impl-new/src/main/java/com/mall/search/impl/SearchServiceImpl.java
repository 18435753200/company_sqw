package com.mall.search.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.dsearch.api.SearchService;
import com.mall.dsearch.vo.SearchRequest;
import com.mall.dsearch.vo.SearchResponse;
import com.mall.dsearch.vo.SearchSupplierDetailRequest;
import com.mall.dsearch.vo.SearchSupplierDetailResponse;
import com.mall.dsearch.vo.SearchSupplierRequest;
import com.mall.dsearch.vo.SearchSupplierResponse;
import com.mall.search.service.SearchProductService;
import com.mall.search.service.SearchSupplierService;
import com.mall.search.utils.StringUtils;

@Service
public class SearchServiceImpl implements SearchService {

	private static final Logger LOGGER=LoggerFactory.getLogger(SearchServiceImpl.class); 
	@Autowired
	private SearchProductService searchProductService;
	
	@Autowired
	private SearchSupplierService searchSupplierService;
	
	
	
	//商品信息查询
	public SearchResponse search(SearchRequest searchRequest) {
		long start_time = System.currentTimeMillis();
		// B2B原因注释
		//每个站点都要传入该站点的渠道，否则返回空对象
    	/*if(null==searchRequest||StringUtils.isEmpty(searchRequest.getEntryType())){
    		LOGGER.info("error:搜索没有入参或者没有渠道-------------");
    		return new SearchResponse();
    	}*/
		String specialStr = "&,%,@,#,!,！,?,？,'";
		LOGGER.info("---------关键字："+searchRequest.getKeyword());
		if(StringUtils.isNotEmpty(searchRequest.getKeyword()) && specialStr.contains(searchRequest.getKeyword())){
			LOGGER.info("包含特殊字符的搜索");
			SearchResponse searchResponse = new SearchResponse();
			return searchResponse;
		}else{
			LOGGER.info("不包含特殊字符的正常搜索");
			SearchResponse searchResponse = searchProductService.searchProduct(searchRequest);
			LOGGER.info("搜索商品消耗时间："+(System.currentTimeMillis()-start_time));
			return searchResponse;
		}
		
	}
	
	//商家信息查询
	@Override
	public SearchSupplierResponse searchSupplier(SearchSupplierRequest searchSupplierRequest) {
		long start_time = System.currentTimeMillis();
		String specialStr = "&,%,@,#,!,！,?,？,'";
		LOGGER.info("---------关键字："+searchSupplierRequest.getKeyWord());
		if(StringUtils.isNotEmpty(searchSupplierRequest.getKeyWord()) && specialStr.contains(searchSupplierRequest.getKeyWord())){
			LOGGER.info("包含特殊字符的搜索");
			SearchSupplierResponse searchSupplierResponse = new SearchSupplierResponse();
			return searchSupplierResponse;
		}else{
			LOGGER.info("不包含特殊字符的正常搜索");
			SearchSupplierResponse searchSupplierResponse = searchSupplierService.searchSupplier(searchSupplierRequest);
			LOGGER.info("搜索商家消耗时间："+(System.currentTimeMillis()-start_time));
			return searchSupplierResponse;
		}
	}
	
	
	//商家店铺详情信息查询
	@Override
	public SearchSupplierDetailResponse searchSupplierDetail(SearchSupplierDetailRequest searchSupplierDetailRequest) {
		long start_time = System.currentTimeMillis();
		SearchSupplierDetailResponse searchSupplierDetailResponse = searchSupplierService.searchSupplierDetail(searchSupplierDetailRequest);
		return searchSupplierDetailResponse;
	}
	
	
	public List<SearchResponse> searchForIds(SearchRequest searchRequest) {
		List<SearchResponse> srList = new ArrayList<SearchResponse>();
		SearchRequest searchRequestForPid = new SearchRequest();
		SearchResponse searchResponse = null;
		searchRequestForPid.setB2C(searchRequest.isB2C());
		Long s = System.currentTimeMillis();
		if(StringUtils.isNotEmpty(searchRequest.getPids())){
			String[] pids = searchRequest.getPids().split(",");
			for (String pid : pids) {
				searchRequestForPid.setPid(pid);
				searchResponse = searchProductService.searchProduct(searchRequestForPid);
				srList.add(searchResponse);
			}
		}
		LOGGER.info("根据PID集合查询消耗时间："+(System.currentTimeMillis()-s));
		return srList;
	}

	public SearchProductService getSearchProductService() {
		return searchProductService;
	}

	public void setSearchProductService(SearchProductService searchProductService) {
		this.searchProductService = searchProductService;
	}

	

	
	
	
}
