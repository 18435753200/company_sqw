package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class SupplierLogisticTpl implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3620622632252709300L;

	private Long id;
	private Long supplierId;
	private String logisticTplName;
	private Long productProvinceid;
	private Long productCityid;
	private Long productAreaid;
	private int fahuoShijian;
	private int logisticFeeType;
	private int logisticJijia;
	private int state;
	private Date createTime;
	private String createBy;
	private int  logisticTplType;
	private String memo;
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
	public String getLogisticTplName() {
		return logisticTplName;
	}
	public void setLogisticTplName(String logisticTplName) {
		this.logisticTplName = logisticTplName;
	}
	public Long getProductProvinceid() {
		return productProvinceid;
	}
	public void setProductProvinceid(Long productProvinceid) {
		this.productProvinceid = productProvinceid;
	}
	public Long getProductCityid() {
		return productCityid;
	}
	public void setProductCityid(Long productCityid) {
		this.productCityid = productCityid;
	}
	public Long getProductAreaid() {
		return productAreaid;
	}
	public void setProductAreaid(Long productAreaid) {
		this.productAreaid = productAreaid;
	}
	public int getFahuoShijian() {
		return fahuoShijian;
	}
	public void setFahuoShijian(int fahuoShijian) {
		this.fahuoShijian = fahuoShijian;
	}
	public int getLogisticFeeType() {
		return logisticFeeType;
	}
	public void setLogisticFeeType(int logisticFeeType) {
		this.logisticFeeType = logisticFeeType;
	}
	public int getLogisticJijia() {
		return logisticJijia;
	}
	public void setLogisticJijia(int logisticJijia) {
		this.logisticJijia = logisticJijia;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
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
		this.createBy = createBy;
	}
	public int getLogisticTplType() {
		return logisticTplType;
	}
	public void setLogisticTplType(int logisticTplType) {
		this.logisticTplType = logisticTplType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public String toString() {
		return "SupplierLogisticTpl [id=" + id + ", supplierId=" + supplierId + ", logisticTplName=" + logisticTplName
				+ ", productProvinceid=" + productProvinceid + ", productCityid=" + productCityid + ", productAreaid="
				+ productAreaid + ", fahuoShijian=" + fahuoShijian + ", logisticFeeType=" + logisticFeeType
				+ ", logisticJijia=" + logisticJijia + ", state=" + state + ", createTime=" + createTime + ", createBy="
				+ createBy + ", logisticTplType=" + logisticTplType + ", memo=" + memo + "]";
	}

	
}
