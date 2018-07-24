package com.mall.supplier.dao;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierProductArea;

public interface SupplierProductAreaMapper {
	
	int insert (SupplierProductArea record);
	
	SupplierProductArea   selectBySupperilerIdAndProductId (@Param(value="productId") long productId,@Param(value="supplierId") long supplierId);

	void deleteAreaByproductId(@Param(value="productId") Long productId);
}
