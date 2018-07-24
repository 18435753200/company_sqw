package com.mall.po;

public class TagName 
{
	private String tagName;
	private String productId;

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "TagName [tagName=" + tagName + ", productId=" + productId + "]";
	}

	


	
	

}
