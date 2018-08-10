package com.mall.vo;

import java.io.Serializable;

public class CommodityProductVo implements Serializable{
	
	private String productId;
	
	private String pname;

	private String imgUrl;

	private String price;
	
	private String domesticPrice;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDomesticPrice() {
		return domesticPrice;
	}

	public void setDomesticPrice(String domesticPrice) {
		this.domesticPrice = domesticPrice;
	}
	
	
}
