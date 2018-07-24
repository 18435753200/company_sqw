package com.mall.po;

public class Family {
	private String prodType;
	private String productId;
	
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "Family [prodType=" + prodType + ", productId=" + productId + "]";
	}
	
	

}
