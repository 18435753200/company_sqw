package com.mall.po;

public class CashRatio {
	private String cashName;
	private String productId;
	public String getCashName() {
		return cashName;
	}
	public void setCashName(String cashName) {
		this.cashName = cashName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "CashRatio [cashName=" + cashName + ", productId=" + productId + "]";
	}
	
	

}
