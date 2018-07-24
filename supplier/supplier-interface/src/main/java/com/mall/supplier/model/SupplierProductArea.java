package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class SupplierProductArea implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1309491758346433494L;
	
	private Long id;
	private Long supplierId;
	private Long productId;
	private Long areaTplId;
	private Date createDateTime;
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
	public Long getAreaTplId() {
		return areaTplId;
	}
	public void setAreaTplId(Long areaTplId) {
		this.areaTplId = areaTplId;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	@Override
	public String toString() {
		return "SupplierProductArea [id=" + id + ", supplierId=" + supplierId + ", productId=" + productId
				+ ", areaTplId=" + areaTplId + ", createDateTime=" + createDateTime + "]";
	}
	
}
