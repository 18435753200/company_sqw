package com.mall.service;

import java.util.List;

import com.mall.po.Supplier;
import com.mall.po.SupplierDetail;


public interface SupplierInfoService {

	/**
	 * 获取商家信息
	 * @param pid
	 * @return
	 */
	public List<Supplier> getSupplierInfo(String pid);
	
	/**
	 * 获取商家店铺详细信息
	 * @param pid
	 * @return
	 */
	public List<SupplierDetail> getSupplierDetailInfo(String pid);

}
