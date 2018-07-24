package com.mall.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class SGeneralLogisticTpl implements Serializable{
    private Long logisticTempId;

    private Long supplierId;

    private String logisticTempName;

    private Integer provinceStartId;

    private Integer cityStartId;

    private Integer type;

    private Integer baseQt;

    private BigDecimal baseFee;

    private Integer stepQt;

    private BigDecimal stepFee;

    private BigDecimal nonefeePrice;

    private Integer nonefeeNum;

    private String nonefeeProvinceId;

    private Integer tplType;

    private String memo;

    private Integer status;

    public Long getLogisticTempId() {
        return logisticTempId;
    }

    public void setLogisticTempId(Long logisticTempId) {
        this.logisticTempId = logisticTempId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getLogisticTempName() {
        return logisticTempName;
    }

    public void setLogisticTempName(String logisticTempName) {
        this.logisticTempName = logisticTempName == null ? null : logisticTempName.trim();
    }

    public Integer getProvinceStartId() {
        return provinceStartId;
    }

    public void setProvinceStartId(Integer provinceStartId) {
        this.provinceStartId = provinceStartId;
    }

    public Integer getCityStartId() {
        return cityStartId;
    }

    public void setCityStartId(Integer cityStartId) {
        this.cityStartId = cityStartId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBaseQt() {
        return baseQt;
    }

    public void setBaseQt(Integer baseQt) {
        this.baseQt = baseQt;
    }

    public BigDecimal getBaseFee() {
        return baseFee;
    }

    public void setBaseFee(BigDecimal baseFee) {
        this.baseFee = baseFee;
    }

    public Integer getStepQt() {
        return stepQt;
    }

    public void setStepQt(Integer stepQt) {
        this.stepQt = stepQt;
    }

    public BigDecimal getStepFee() {
        return stepFee;
    }

    public void setStepFee(BigDecimal stepFee) {
        this.stepFee = stepFee;
    }

    public BigDecimal getNonefeePrice() {
        return nonefeePrice;
    }

    public void setNonefeePrice(BigDecimal nonefeePrice) {
        this.nonefeePrice = nonefeePrice;
    }

    public Integer getNonefeeNum() {
        return nonefeeNum;
    }

    public void setNonefeeNum(Integer nonefeeNum) {
        this.nonefeeNum = nonefeeNum;
    }

    public String getNonefeeProvinceId() {
        return nonefeeProvinceId;
    }

    public void setNonefeeProvinceId(String nonefeeProvinceId) {
        this.nonefeeProvinceId = nonefeeProvinceId == null ? null : nonefeeProvinceId.trim();
    }

    public Integer getTplType() {
        return tplType;
    }

    public void setTplType(Integer tplType) {
        this.tplType = tplType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}