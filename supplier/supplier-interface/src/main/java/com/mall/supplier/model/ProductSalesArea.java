package com.mall.supplier.model;

import java.io.Serializable;
import java.util.List;

public class ProductSalesArea implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4852430125324223464L;
	private Long provinceId;
	private Long cityId;
	private Long countyId;
	private String name;
	// 限制位置：1，县/区 2，市 3，省
	private int limitAreaType;
	//  1 销售区域， 2限售区域
	private int tmplType;
	//
	private String message;
	//收货地址与销售地址是否匹配  :0匹配; 1不匹配,2没有设置限售区域
	private int status;
	//收货地址不在配送区域中的商品的   productId和  supplierId
	private List<SupplierProduct> supplierProducts;
	
	public List<SupplierProduct> getSupplierProducts() {
		return supplierProducts;
	}
	
	public void setSupplierProducts(List<SupplierProduct> supplierProducts) {
		this.supplierProducts = supplierProducts;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public Long getCountyId() {
		return countyId;
	}
	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLimitAreaType() {
		return limitAreaType;
	}
	public void setLimitAreaType(int limitAreaType) {
		this.limitAreaType = limitAreaType;
	}
	public int getTmplType() {
		return tmplType;
	}
	public void setTmplType(int tmplType) {
		this.tmplType = tmplType;
	}
	@Override
	public String toString() {
		return "ProductSalesArea [provinceId=" + provinceId + ", cityId=" + cityId + ", countyId=" + countyId
				+ ", name=" + name + ", limitAreaType=" + limitAreaType + ", tmplType=" + tmplType + ", message="
				+ message + ", status=" + status + "]";
	}
	
}
