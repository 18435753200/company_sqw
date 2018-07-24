package com.mall.supplier.service;

import com.mall.supplier.model.SupplierSalesDiscount;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  商家折扣接口
 */
public interface SupplierDiscountService {

    /**
     * 设置/更新 商家折扣 总接口
     * @param supplierId 商户id 必传
     * @param salesDiscount 营销折扣 必传
     * @param discountName 营销名称
     * @param beginDatetime 开始时间
     * @param endDatetime 结束时间
     * @param discountRemark 营销说明
     * @param bizType 商家店铺营销
     * @return
     */
    public int setSalesDiscount(Long supplierId, BigDecimal salesDiscount,String discountName,Date beginDatetime,Date endDatetime,String discountRemark,Integer bizType);

    /**
     * 设置/更新 商家折扣
     * @param supplierId 商户id 必传
     * @param salesDiscount 营销折扣 必传
     * @return
     */
    public int setSalesDiscount(Long supplierId, BigDecimal salesDiscount);

    /**
     * 根据商户id查找商家折扣
     * @param supplierId 商户id
     * @return
     */
    public SupplierSalesDiscount findBySupplierId(Long supplierId);
}
