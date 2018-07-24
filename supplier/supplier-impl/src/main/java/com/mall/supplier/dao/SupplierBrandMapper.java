package com.mall.supplier.dao;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.SupplierBrand;
import com.mall.supplier.model.SupplierBrandDTO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SupplierBrandMapper {
    int deleteByPrimaryKey(BigDecimal brandId);

    int insert(SupplierBrand record);

    int insertSelective(SupplierBrand record);

    SupplierBrandDTO selectByPrimaryKey(Long brandId);

    int updateByPrimaryKeySelective(SupplierBrand record);

    int updateByPrimaryKey(SupplierBrand record);

	List<SupplierBrandDTO> getBrandPageList(PageBean<SupplierBrandDTO> pageBean);

	List<SupplierBrandDTO> findSupplierBrandsBySupplierId(Long supplierId);

	List<SupplierBrandDTO> findSupplierBrandsBySubSupplierId(Long subSupplierId);

	int isExclusiveAgent(String brandName);

	String findDescriptionBySysBrandId(@Param("supplierId")Long supplierId,@Param("sysBrandId")String sysBrandId);
}