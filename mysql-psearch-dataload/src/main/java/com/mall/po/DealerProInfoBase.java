package com.mall.po;

/**
 *  经销商产品基础信息（商品副类）
 *  @author DOUBLELL
 */
public class DealerProInfoBase {
	private String productid;
	private String shortdescription;
	private String keywords;
	private String productname;

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getShortdescription() {
		return shortdescription;
	}

	public void setShortdescription(String shortdescription) {
		this.shortdescription = shortdescription;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}
}
