package com.mall.supplier.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierDetailAttr;

public interface SupplierOfflineStoreAttrMapper {

	List<SupplierDetailAttr> findSupplierOfflineStoreAttrByStroeId(@Param("id")Integer id);

	void insertDetailAttr(SupplierDetailAttr supplierDetailAttr);

}
