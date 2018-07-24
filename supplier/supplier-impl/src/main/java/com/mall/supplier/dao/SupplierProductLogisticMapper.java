package com.mall.supplier.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierProductLogistic;

public interface SupplierProductLogisticMapper {
  
	int insert(SupplierProductLogistic record);
	
	SupplierProductLogistic selectSupplierProductLogisticByPidAndSid(@Param("productId")Long productId,@Param("supplierId")Long supplierId);

	List<SupplierProductLogistic> selectSupplierProductLogisticByIds(
			@Param("productId")Long productId,@Param("supplierId")Long supplierId);

	void deleteProductLogisticsByprodictId(@Param("productId")Long productId);
}
