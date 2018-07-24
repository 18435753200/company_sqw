package com.mall.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierApplyYhq implements Serializable{
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long supplierId;

    private String supplierName;

    private BigDecimal applyVal;

    private Date applyDatetime;

    private BigDecimal validVal;

    private Date validDatetime;

    private Integer status;

    private String memo;

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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public BigDecimal getApplyVal() {
        return applyVal;
    }

    public void setApplyVal(BigDecimal applyVal) {
        this.applyVal = applyVal;
    }

    public Date getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(Date applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public BigDecimal getValidVal() {
        return validVal;
    }

    public void setValidVal(BigDecimal validVal) {
        this.validVal = validVal;
    }

    public Date getValidDatetime() {
        return validDatetime;
    }

    public void setValidDatetime(Date validDatetime) {
        this.validDatetime = validDatetime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }
}