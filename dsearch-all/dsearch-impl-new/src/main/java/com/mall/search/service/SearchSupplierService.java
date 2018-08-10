package com.mall.search.service;

import com.mall.dsearch.vo.SearchSupplierDetailRequest;
import com.mall.dsearch.vo.SearchSupplierDetailResponse;
import com.mall.dsearch.vo.SearchSupplierRequest;
import com.mall.dsearch.vo.SearchSupplierResponse;
/**
 * 搜索商家内部接口
 * @author DOUBLELL
 *
 */
public interface SearchSupplierService {


	public SearchSupplierResponse searchSupplier(SearchSupplierRequest searchSupplierRequest);

	public SearchSupplierDetailResponse searchSupplierDetail(SearchSupplierDetailRequest searchSupplierDetailRequest);
}
