package com.mall.supplier.model;

import java.io.Serializable;

public class SuplierAreaTpl implements Serializable {
	private static final long serialVersionUID = 9198046244378790025L;
	
	private Long id;
	private long supplierId;
	private String name;
	private int type;
	private int status;
	private int bizType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getBizType() {
		return bizType;
	}
	public void setBizType(int bizType) {
		this.bizType = bizType;
	}
	@Override
	public String toString() {
		return "SupplierAreaTpl [id=" + id + ", supplierId=" + supplierId + ", name=" + name + ", type=" + type
				+ ", status=" + status + ", bizType=" + bizType + "]";
	}
	
	
	
	
	
	
	

}
