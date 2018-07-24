package com.mall.supplier.dto;

import java.io.Serializable;

public class CompanyDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 企业LOGO
	private String companyLogo;

	// 企业名称
	private String companyName;

	// 企业id
	private Long companyId;

	// 联系人名称
	private String contactName;

	// 联系人电话号码
	private String contactPhone;

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

}
