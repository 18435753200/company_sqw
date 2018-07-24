package com.mall.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierSalesDiscount implements Serializable{
    private Long id;
    //商户id
    private Long supplierId;
    //营销名称
    private String discountName;
    //营销折扣
    private BigDecimal salesDiscount;
    //开始时间，开始时间为空立即开始
    private Date beginDatetime;
    //结束时间，结束时间为空没有结束时间
    private Date endDatetime;
    //营销说明
    private String discountRemark;
    //0，不可用  1，可用
    private Integer status;
    //1，商家店铺营销
    private Integer bizType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName == null ? null : discountName.trim();
    }

    public BigDecimal getSalesDiscount() {
        return salesDiscount;
    }

    public void setSalesDiscount(BigDecimal salesDiscount) {
        this.salesDiscount = salesDiscount;
    }

    public Date getBeginDatetime() {
        return beginDatetime;
    }

    public void setBeginDatetime(Date beginDatetime) {
        this.beginDatetime = beginDatetime;
    }

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getDiscountRemark() {
        return discountRemark;
    }

    public void setDiscountRemark(String discountRemark) {
        this.discountRemark = discountRemark == null ? null : discountRemark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }
}