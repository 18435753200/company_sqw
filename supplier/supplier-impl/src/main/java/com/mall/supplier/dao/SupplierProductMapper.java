package com.mall.supplier.dao;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierProduct;

public interface SupplierProductMapper {
	
    int deleteByPrimaryKey(Long productId);

    int insert(SupplierProduct record);

    int insertSelective(SupplierProduct record);

    SupplierProduct selectByPrimaryKey(Long productId);

    int updateByPrimaryKeySelective(SupplierProduct record);

    int updateByPrimaryKey(SupplierProduct record);
	/**
	 * 根据供应商ID获取商品信息
	 * @return
	 */
	public SupplierProduct getProductBySupplierId(@Param(value = "supplierId")Long supplierId);
	
	/**
	 * 根据供应商ID更新商品信息
	 * @return
	 */
	public int updateProductBySupplierId(SupplierProduct record);
	
	/**
	 * 根据供应商ID更新商品信息
	 * @return
	 */
	public int deleteBySupplierId(@Param(value = "supplierId")Long supplierId);
	
}