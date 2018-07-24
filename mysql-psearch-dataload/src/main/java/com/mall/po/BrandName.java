package com.mall.po;
/**
 * 品牌po
 * @author DOUBLELL
 *
 */
public class BrandName {
	private String brandId;
	private String brandName;
	private String subBrandName;
	private String definingValue; 
	private Long skuid;
	private String dealer_price;//b2b wap端售价 dealer_price
	private String retail_price;//b2b wap端建议售价 retail_price
	private String cashHqj;
	private String prodType;
	public String getDealer_price() {
		return dealer_price;
	}

	public void setDealer_price(String dealer_price) {
		this.dealer_price = dealer_price;
	}

	public String getRetail_price() {
		return retail_price;
	}

	public void setRetail_price(String retail_price) {
		this.retail_price = retail_price;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSubBrandName() {
		return subBrandName;
	}

	public void setSubBrandName(String subBrandName) {
		this.subBrandName = subBrandName;
	}

	public String getDefiningValue() {
		return definingValue;
	}

	public void setDefiningValue(String definingValue) {
		this.definingValue = definingValue;
	}

	public Long getSkuid() {
		return skuid;
	}

	public void setSkuid(Long skuid) {
		this.skuid = skuid;
	}

	public String getCashHqj() {
		return cashHqj;
	}

	public void setCashHqj(String cashHqj) {
		this.cashHqj = cashHqj;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	
	

}
