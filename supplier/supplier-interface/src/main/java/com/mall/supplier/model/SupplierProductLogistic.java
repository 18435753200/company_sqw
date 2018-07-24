package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class SupplierProductLogistic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5329802194061834624L;

	private Long id;
	private Long supplierId;
	private Long productId;
	private Long logisticTplId;
	private Long productLogisticType;
	private Date createDatetime;
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getLogisticTplId() {
		return logisticTplId;
	}
	public void setLogisticTplId(Long logisticTplId) {
		this.logisticTplId = logisticTplId;
	}
	public Long getProductLogisticType() {
		return productLogisticType;
	}
	public void setProductLogisticType(Long productLogisticType) {
		this.productLogisticType = productLogisticType;
	}
	public Date getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	@Override
	public String toString() {
		return "SupplierProductLogistic [id=" + id + ", supplierId=" + supplierId + ", productId=" + productId
				+ ", logisticTplId=" + logisticTplId + ", productLogisticType=" + productLogisticType
				+ ", createDatetime=" + createDatetime + "]";
	}
	
	
}
