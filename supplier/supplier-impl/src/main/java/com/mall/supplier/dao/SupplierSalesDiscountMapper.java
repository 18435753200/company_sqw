package com.mall.supplier.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierSalesDiscount;
import com.mall.supplier.model.SupplierSalesDiscountExample;

public interface SupplierSalesDiscountMapper {
    int countByExample(SupplierSalesDiscountExample example);

    int deleteByExample(SupplierSalesDiscountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SupplierSalesDiscount record);

    int insertSelective(SupplierSalesDiscount record);

    List<SupplierSalesDiscount> selectByExample(SupplierSalesDiscountExample example);

    SupplierSalesDiscount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SupplierSalesDiscount record, @Param("example") SupplierSalesDiscountExample example);

    int updateByExample(@Param("record") SupplierSalesDiscount record, @Param("example") SupplierSalesDiscountExample example);

    int updateByPrimaryKeySelective(SupplierSalesDiscount record);

    int updateByPrimaryKey(SupplierSalesDiscount record);

    /**
     * 根据商户id删除营销折扣
     * @param supplierId 商户id
     * @return
     */
    int deleteBySupplierId(Long supplierId);

    /**
     * 根据条件查询商家折扣
     * @param supplierSalesDiscount 查询条件
     * @return
     */
    SupplierSalesDiscount findSupplierSalesDiscount(SupplierSalesDiscount supplierSalesDiscount);
    /**
     * 根据商家id查询折扣
     * @param supplierId
     * @return
     */
	SupplierSalesDiscount selectSalesDiscountBySupplicerId(@Param("supplierId")Long supplierId);
	/**
	 * 修改商家折扣
	 * @param supplierId
	 * @param salesDiscount
	 * @return
	 */
	int updateSalesDiscountBySupplier(@Param("supplierId")Long supplierId, @Param("salesDiscount")BigDecimal salesDiscount);
}