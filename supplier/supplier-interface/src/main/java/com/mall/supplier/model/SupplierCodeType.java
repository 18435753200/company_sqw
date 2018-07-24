package com.mall.supplier.model;

import java.io.Serializable;

public class SupplierCodeType implements Serializable {

	private static final long serialVersionUID = 2468995938456392724L;

	private Long id;	//主键
	
	private int supplierCodeNum;		//企业编码num
	
	private String supplierCodeType;	//企业编码类型

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSupplierCodeNum() {
		return supplierCodeNum;
	}

	public void setSupplierCodeNum(int supplierCodeNum) {
		this.supplierCodeNum = supplierCodeNum;
	}

	public String getSupplierCodeType() {
		return supplierCodeType;
	}

	public void setSupplierCodeType(String supplierCodeType) {
		this.supplierCodeType = supplierCodeType;
	}

	@Override
	public String toString() {
		return "SupplierCodeType [id=" + id + ", supplierCodeNum="
				+ supplierCodeNum + ", supplierCodeType=" + supplierCodeType
				+ "]";
	}
	
}