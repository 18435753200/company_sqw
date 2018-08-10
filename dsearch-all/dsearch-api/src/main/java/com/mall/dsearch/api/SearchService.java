package com.mall.dsearch.api;

import java.util.List;

import com.mall.dsearch.vo.SearchRequest;
import com.mall.dsearch.vo.SearchResponse;
import com.mall.dsearch.vo.SearchSupplierDetailRequest;
import com.mall.dsearch.vo.SearchSupplierDetailResponse;
import com.mall.dsearch.vo.SearchSupplierRequest;
import com.mall.dsearch.vo.SearchSupplierResponse;


/**
 * 搜索服务接口
 * @author grady.song
 *
 */
public interface SearchService {

    
   /**
    * 商品搜索接口
    * @param searchRequest 入参
    * @return
    */
	public SearchResponse search( SearchRequest  searchRequest);
	
	/**
	 * 根据pids 集合 返回 结果集接口
	 * @param searchRequest 入参  searchRequest.getPids()
	 * @return
	 */
	public List<SearchResponse> searchForIds(SearchRequest searchRequest);
	
	/**
	 * 商家搜索入口
	 * @param searchSupplierRequest
	 * @return
	 */
	public SearchSupplierResponse searchSupplier( SearchSupplierRequest  searchSupplierRequest);
	
	/**
	 * 商家店铺详情搜索入口
	 * @param searchSupplierDetailRequest
	 * @return
	 */
	public SearchSupplierDetailResponse searchSupplierDetail( SearchSupplierDetailRequest  searchSupplierDetailRequest);
}
