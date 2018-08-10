package com.mall.dsearch.vo;

import java.io.Serializable;

public class CatalogAttr implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4675483289003116631L;
	private String name;
	private int sortval;//排序
	private boolean priceRange = false;//是否是价格区间
	private boolean brandName = false;//是否是品牌
	private boolean cyid = false;//是否是源产国
	private boolean inventory = false;//是否是库存
	private boolean b2csupply = false;//是否是货源
	private boolean promotion = false;//是否是促销优惠
	private boolean supplierId = false;//是否是企业

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSortval() {
		return sortval;
	}

	public void setSortval(int sortval) {
		this.sortval = sortval;
	}

	public boolean isPriceRange() {
		return priceRange;
	}

	public void setPriceRange(boolean priceRange) {
		this.priceRange = priceRange;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CatalogAttr other = (CatalogAttr) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
