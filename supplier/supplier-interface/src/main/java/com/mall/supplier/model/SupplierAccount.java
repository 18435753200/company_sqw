package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class SupplierAccount implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7570437974466200659L;

	private Long accountId;

    private Long supplierId;

    private Integer type;

    private Double amount;

    private Double validAmount;

    private Double creditAmount;

    private Double loanAmount;

    private Integer loanSchedule;

    private String description;

    private Date createTime;

    private String createBy;

    private Date lastModifyTime;

    private Integer lastModifyBy;

    private Integer status;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getValidAmount() {
        return validAmount;
    }

    public void setValidAmount(Double validAmount) {
        this.validAmount = validAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanSchedule() {
        return loanSchedule;
    }

    public void setLoanSchedule(Integer loanSchedule) {
        this.loanSchedule = loanSchedule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(Integer lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}