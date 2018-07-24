package com.mall.po;


/**
 * 商品搜索类(根据业务需求和页面展示定义)
 * @author DOUBLELL
 *
 */
public class SupplierInfo {

	private String supplierId;

    private String supplierCode;
   //公司名称
    private String name;

    private String companySimpleName;		//公司简称
	private String companyLevel;			//公司评级
	private String settledArea;				//入住区域
	private	String customerServerTele;		//客服电话
	private	String supplierLogoImgurl;		//供应商logo image url

	public String getSupplierLogoImgurl() {
		return supplierLogoImgurl;
	}

	public void setSupplierLogoImgurl(String supplierLogoImgurl) {
		 this.supplierLogoImgurl = supplierLogoImgurl == null ? null : supplierLogoImgurl.trim();
	}

	public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

     
    public String getCompanySimpleName() {
		return companySimpleName;
	}

	public void setCompanySimpleName(String companySimpleName) {
		this.companySimpleName = companySimpleName;
	}


	public String getCompanyLevel() {
		return companyLevel;
	}

	public void setCompanyLevel(String companyLevel) {
		this.companyLevel = companyLevel;
	}

	public String getSettledArea() {
		return settledArea;
	}

	public void setSettledArea(String settledArea) {
		this.settledArea = settledArea;
	}

	
	public String getCustomerServerTele() {
		return customerServerTele;
	}

	public void setCustomerServerTele(String customerServerTele) {
		this.customerServerTele = customerServerTele;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierCode="
				+ supplierCode + ", name=" + name + ", companySimpleName=" 
				+ companySimpleName + ", companyLevel=" + companyLevel 
				+ ", settledArea=" + settledArea 
				+ ", customerServerTele=" + customerServerTele +"]";
	}


}
