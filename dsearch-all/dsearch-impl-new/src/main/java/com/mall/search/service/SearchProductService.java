package com.mall.search.service;

import com.mall.dsearch.vo.SearchRequest;
import com.mall.dsearch.vo.SearchResponse;
/**
 * 搜索商品内部接口
 * @author DOUBLELL
 *
 */
public interface SearchProductService {

	public SearchResponse searchProduct(SearchRequest searchRequest);
}
