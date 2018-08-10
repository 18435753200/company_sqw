package com.mall.dsearch.vo;

import java.io.Serializable;

public class AttrTerm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6816295418739017685L;
	private String id; 
	private String attrName; //属性名
	private String attrValName; //属性值
	private boolean price;//是否是价格
	private boolean brandName;//是否是品牌
	private boolean cyid;//是否是源产国
	private boolean supplierId;//是否是企业
	private boolean inventory = false;//是否是库存
	private boolean b2csupply = false;//是否是货源
	private boolean promotion = false;//是否是促销优惠
	
	public boolean isSupplierId() {
		return supplierId;
	}
	public void setSupplierId(boolean supplierId) {
		this.supplierId = supplierId;
	}
	public boolean isPromotion() {
		return promotion;
	}
	public void setPromotion(boolean promotion) {
		this.promotion = promotion;
	}
	public boolean isInventory() {
		return inventory;
	}
	public void setInventory(boolean inventory) {
		this.inventory = inventory;
	}
	public boolean isB2csupply() {
		return b2csupply;
	}
	public void setB2csupply(boolean b2csupply) {
		this.b2csupply = b2csupply;
	}
	public boolean isCyid() {
		return cyid;
	}
	public void setCyid(boolean cyid) {
		this.cyid = cyid;
	}
	public boolean isBrandName() {
		return brandName;
	}
	public void setBrandName(boolean brandName) {
		this.brandName = brandName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrValName() {
		return attrValName;
	}
	public void setAttrValName(String attrValName) {
		this.attrValName = attrValName;
	}
	public boolean isPrice() {
		return price;
	}
	public void setPrice(boolean price) {
		this.price = price;
	}
	
}
