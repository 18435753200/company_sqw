package com.mall.supplier.model;

import java.io.Serializable;

public class SupplierProduct implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4436650784815696566L;

	private Long productId;

    private Long supplierId;

    private String categories;

    private String brand;

    private Integer isRegister;

    private Integer isOnsale;

    private Integer skuNum;
    
    private String categoriesBackup;		//类别备份
    private String brandBackup;				//品牌备份
    private Integer modifyStatus;			//修改状态

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories == null ? null : categories.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public Integer getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(Integer isRegister) {
        this.isRegister = isRegister;
    }

    public Integer getIsOnsale() {
        return isOnsale;
    }

    public void setIsOnsale(Integer isOnsale) {
        this.isOnsale = isOnsale;
    }

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }

	public String getCategoriesBackup() {
		return categoriesBackup;
	}

	public void setCategoriesBackup(String categoriesBackup) {
		this.categoriesBackup = categoriesBackup;
	}

	public String getBrandBackup() {
		return brandBackup;
	}

	public void setBrandBackup(String brandBackup) {
		this.brandBackup = brandBackup;
	}

	public Integer getModifyStatus() {
		return modifyStatus;
	}

	public void setModifyStatus(Integer modifyStatus) {
		this.modifyStatus = modifyStatus;
	}

}