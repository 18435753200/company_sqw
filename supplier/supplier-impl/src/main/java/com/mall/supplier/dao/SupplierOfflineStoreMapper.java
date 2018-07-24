package com.mall.supplier.dao;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierDetail;

public interface SupplierOfflineStoreMapper {

	SupplierDetail findSupplierDetailBySupplierId(@Param("supplierId")Integer supplierId);

	Integer insertDetail(SupplierDetail supplierDetail);
	
	void updateShopTop(SupplierDetail supplierDetail);
	

}
