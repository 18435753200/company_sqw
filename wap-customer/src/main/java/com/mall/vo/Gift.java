package com.mall.vo;

import java.io.Serializable;

/**
 * 赠品信息
 * @author dell
 *
 */
public class Gift implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productId;
	private String skuId;
	private String productName;
	private String skuName;
	private int qty;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public Gift(String productId, String skuId, String productName,String skuName, int qty) {
		this.productId = productId;
		this.skuId = skuId;
		this.productName = productName;
		this.skuName = skuName;
		this.qty = qty;
	}
	public Gift() {
	}
	
	
}
