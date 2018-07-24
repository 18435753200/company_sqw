package com.mall.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentSupplier implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -2954415748634953867L;


	   //公司名称
	    private String name;
	  //公司简称
	    private String nameJC;	
	    
//	    =======================================
	    
	    private Integer provinceId;

	    private Integer cityId;

	    private Integer areaId;
	    //营业地址
	    private String address;
	    //注册地址
	    private String registerAddress;
	    
//	    ======================================
	    
	    //业务联系人
	    private String  contact;
	    //业务联系人电话
	    private String contactTel;
	    //业务联系人手机
	    private String phone;
	    //邮箱
	    private String email;
	    
//	    ======================================
	    
	    //企业法人名称
	    private String legalPerson;
	    //企业法人联系电话
	    private String legalPersonPhone;
	    //企业法人证件类型
	    private String legalPersonCardType;
	    //企业法人证件号码
	    private String legalPersonCardNo;
	    
//	    ======================================
	    
	    //结算银行账户名称
	    private String accountName;
	    //结算银行账号
	    private String accountNo;
	    //开户银行名称
	    private String accoutBankname;
	    //开户银行联合号
	    private String accoutBankno;
	    
//	    ======================================
	    
	    //身份证正面
	    private String idCardFront;
	    //身份证反面
	    private String idCardVerso;
	    //企业营业执照
	    private String businessLicense;
	    //企业营业执照号码
	    private String businessLicenseNo;
	    //企业经营门头店照片
	    private String companyStoreLogo;
	    //证明企业合法性的证明文件
		private String companyLegitimacyUrl;
		//公司详情文件
		private String companyDetailedUrl;
		//公司logo url
		private String logoImgurl;
	    //公司详细信息
	    private String companyInfo;	
	    
//	    ======================================
	    
	    //企业个人账号姓名
		private String userName;				
		//企业个人账号手机号
		private String userMobile;
		
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNameJC() {
			return nameJC;
		}
		public void setNameJC(String nameJC) {
			this.nameJC = nameJC;
		}
		public Integer getProvinceId() {
			return provinceId;
		}
		public void setProvinceId(Integer provinceId) {
			this.provinceId = provinceId;
		}
		public Integer getCityId() {
			return cityId;
		}
		public void setCityId(Integer cityId) {
			this.cityId = cityId;
		}
		public Integer getAreaId() {
			return areaId;
		}
		public void setAreaId(Integer areaId) {
			this.areaId = areaId;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getRegisterAddress() {
			return registerAddress;
		}
		public void setRegisterAddress(String registerAddress) {
			this.registerAddress = registerAddress;
		}
		public String getContact() {
			return contact;
		}
		public void setContact(String contact) {
			this.contact = contact;
		}
		public String getContactTel() {
			return contactTel;
		}
		public void setContactTel(String contactTel) {
			this.contactTel = contactTel;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getLegalPerson() {
			return legalPerson;
		}
		public void setLegalPerson(String legalPerson) {
			this.legalPerson = legalPerson;
		}
		public String getLegalPersonPhone() {
			return legalPersonPhone;
		}
		public void setLegalPersonPhone(String legalPersonPhone) {
			this.legalPersonPhone = legalPersonPhone;
		}
		public String getLegalPersonCardType() {
			return legalPersonCardType;
		}
		public void setLegalPersonCardType(String legalPersonCardType) {
			this.legalPersonCardType = legalPersonCardType;
		}
		public String getLegalPersonCardNo() {
			return legalPersonCardNo;
		}
		public void setLegalPersonCardNo(String legalPersonCardNo) {
			this.legalPersonCardNo = legalPersonCardNo;
		}
		public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		public String getAccountNo() {
			return accountNo;
		}
		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}
		public String getAccoutBankname() {
			return accoutBankname;
		}
		public void setAccoutBankname(String accoutBankname) {
			this.accoutBankname = accoutBankname;
		}
		public String getAccoutBankno() {
			return accoutBankno;
		}
		public void setAccoutBankno(String accoutBankno) {
			this.accoutBankno = accoutBankno;
		}
		public String getIdCardFront() {
			return idCardFront;
		}
		public void setIdCardFront(String idCardFront) {
			this.idCardFront = idCardFront;
		}
		public String getIdCardVerso() {
			return idCardVerso;
		}
		public void setIdCardVerso(String idCardVerso) {
			this.idCardVerso = idCardVerso;
		}
		public String getBusinessLicense() {
			return businessLicense;
		}
		public void setBusinessLicense(String businessLicense) {
			this.businessLicense = businessLicense;
		}
		public String getBusinessLicenseNo() {
			return businessLicenseNo;
		}
		public void setBusinessLicenseNo(String businessLicenseNo) {
			this.businessLicenseNo = businessLicenseNo;
		}
		public String getCompanyStoreLogo() {
			return companyStoreLogo;
		}
		public void setCompanyStoreLogo(String companyStoreLogo) {
			this.companyStoreLogo = companyStoreLogo;
		}
		public String getCompanyLegitimacyUrl() {
			return companyLegitimacyUrl;
		}
		public void setCompanyLegitimacyUrl(String companyLegitimacyUrl) {
			this.companyLegitimacyUrl = companyLegitimacyUrl;
		}
		public String getCompanyDetailedUrl() {
			return companyDetailedUrl;
		}
		public void setCompanyDetailedUrl(String companyDetailedUrl) {
			this.companyDetailedUrl = companyDetailedUrl;
		}
		public String getLogoImgurl() {
			return logoImgurl;
		}
		public void setLogoImgurl(String logoImgurl) {
			this.logoImgurl = logoImgurl;
		}
		public String getCompanyInfo() {
			return companyInfo;
		}
		public void setCompanyInfo(String companyInfo) {
			this.companyInfo = companyInfo;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUserMobile() {
			return userMobile;
		}
		public void setUserMobile(String userMobile) {
			this.userMobile = userMobile;
		}
		
		
		@Override
		public String toString() {
			return "AgentSupplier [name=" + name + ", nameJC=" + nameJC + ", provinceId=" + provinceId + ", cityId="
					+ cityId + ", areaId=" + areaId + ", address=" + address + ", registerAddress=" + registerAddress
					+ ", contact=" + contact + ", contactTel=" + contactTel + ", phone=" + phone + ", email=" + email
					+ ", legalPerson=" + legalPerson + ", legalPersonPhone=" + legalPersonPhone
					+ ", legalPersonCardType=" + legalPersonCardType + ", legalPersonCardNo=" + legalPersonCardNo
					+ ", accountName=" + accountName + ", accountNo=" + accountNo + ", accoutBankname=" + accoutBankname
					+ ", accoutBankno=" + accoutBankno + ", idCardFront=" + idCardFront + ", idCardVerso=" + idCardVerso
					+ ", businessLicense=" + businessLicense + ", businessLicenseNo=" + businessLicenseNo
					+ ", companyStoreLogo=" + companyStoreLogo + ", companyLegitimacyUrl=" + companyLegitimacyUrl
					+ ", companyDetailedUrl=" + companyDetailedUrl + ", logoImgurl=" + logoImgurl + ", companyInfo="
					+ companyInfo + ", userName=" + userName + ", userMobile=" + userMobile + "]";
		}	
		
		
		
		
	    
	    

}