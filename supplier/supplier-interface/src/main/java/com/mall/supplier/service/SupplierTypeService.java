package com.mall.supplier.service;

import java.math.BigDecimal;

/**
 * Created by Zhutaoshen on 2016/12/26 0026.
 */
public interface SupplierTypeService {
    /**
     * 获得企业类型相关的分期还款比例
     * @param supplierType
     * @return
     */
    public BigDecimal getProportionRepayment(int supplierType);

    /**
     * 设置企业类型相关的分期还款比例
     * @param supplierType
     * @param proportionRepayment
     * @return
     */
    public int setProportionRepayment(int supplierType, BigDecimal proportionRepayment);

    /**
     * 根据企业ID获得企业的分期还款比例
     * @param supplierType
     * @return
     */
    public BigDecimal getProportionRepaymentByID(Long supplierID);
}
