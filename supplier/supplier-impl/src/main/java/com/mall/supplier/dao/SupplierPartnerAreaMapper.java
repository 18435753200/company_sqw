package com.mall.supplier.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierPartnerArea;

public interface SupplierPartnerAreaMapper {

	int insert(SupplierPartnerArea supplierPartnerArea);
		
	int insertSelective(SupplierPartnerArea supplierPartnerArea);

	List<SupplierPartnerArea> findAreaOperators(@Param("provinceId")Long provinceId, @Param("cityId")Long cityId,
			@Param("countyId")Long countyId, @Param("partnerType")Integer partnerType);
	
	List<SupplierPartnerArea> findPartnerArea(@Param("supplierId")Long supplierId, @Param("partnerType")Integer partnerType);

	Integer updateSupplierPartnerAera(SupplierPartnerArea supplierPartnerArea);
	Integer delectArea(@Param(value="supplierId")Long supplierId, @Param(value="provinceId")Long provinceId, 
			@Param(value="cityId")Long cityId,@Param(value="countyId")Long countyId);
}
