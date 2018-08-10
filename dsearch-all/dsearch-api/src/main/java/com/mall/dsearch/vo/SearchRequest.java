package com.mall.dsearch.vo;

import java.io.Serializable;

/**
 * 
 * @author songzilong
 *
 */
public class SearchRequest implements Serializable{
	
	private static final long serialVersionUID = -8942590072529299833L;
	/**
	 * 是否是b2c请求参数
	 */
	private boolean isB2C=false;
	/**
	 * 是否是卓越商品
	 */
	private boolean isZy=false;
	/** 
     * 请求关键词 
	 */
    private String keyword;
    
    private String tagName;
    private String fcode;
    private String family;
	



	/**
	 * 国别
	 */
	private String countryName;
	/**
	 * 供应商ID
	 */
	private String supplierId;
	/**
	 * 供应商名称
	 */
	private String supplierName;
	/**
     *  当前页号，从1开始 
     */
    private int pageno=1; 
    /**
     *  每页显示的个数 
     */
    private int pageSize=20;
    /**
     * 排序类型,默认按照相关度排序
     */
    private Integer sortType=0;
    
    /**
    * 展示类目号
    */
    private String cdid;
    
    /**
     * 入驻区域编号
     */
     private String companyRegion;
    
    /**
     * 源产地
     */
    private String cyid;
	/**
	 * 属性-属性值编号
	 */
	 private String attrcodes;
	 /**
	  * 价格区间
	  */
	 private String priceRange;
	 /**
	  * 折扣区间
	  */
	 private String discount;
	 
	 private String pid;
	 
	 private String brandName;
	 private String isInventory;
	 private String b2csupply;
	 private String promotion;
	 
	 private String entryType = ""; //影响促销价格在搜索   ////PC/WAP/APP
	 
	 private String pids;
	 
	 
	 
	 
	 public String getFamily() {
			return family;
		}

		public void setFamily(String family) {
			this.family = family;
		}

		

		public String getFcode() {
			return fcode;
		}

		public void setFcode(String fcode) {
			this.fcode = fcode;
		}
	 
	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCompanyRegion() {
		return companyRegion;
	}

	public void setCompanyRegion(String companyRegion) {
		this.companyRegion = companyRegion;
	}

	public String getEntryType() {
		return entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isZy() {
		return isZy;
	}

	public void setZy(boolean isZy) {
		this.isZy = isZy;
	}

	public String getIsInventory() {
		return isInventory;
	}

	public void setIsInventory(String isInventory) {
		this.isInventory = isInventory;
	}

	public String getB2csupply() {
		return b2csupply;
	}

	public void setB2csupply(String b2csupply) {
		this.b2csupply = b2csupply;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		{
			this.keyword = keyword;
		}
		
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public String getCdid() {
		return cdid;
	}

	public void setCdid(String cdid) {
		this.cdid = cdid;
	}

	public String getAttrcodes() {
		return attrcodes;
	}

	public void setAttrcodes(String attrcodes) {
		this.attrcodes = attrcodes;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getCyid() {
		return cyid;
	}

	public void setCyid(String cyid) {
		this.cyid = cyid;
	}
    public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
    public boolean isB2C() {
		return isB2C;
	}

	public void setB2C(boolean isB2C) {
		this.isB2C = isB2C;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
}
