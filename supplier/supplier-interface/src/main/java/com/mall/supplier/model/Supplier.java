package com.mall.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Supplier implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7593121354987753894L;

	private Long supplierId;

    private String supplierCode;
 
    private Integer type;

    private Long parentId;

    private Integer grade;

    private Integer organizationType;

    private String organizationalForm;

    private Integer businessScale;

    private String healthLicense;

    private String linesOfCredit;
    
    //公司名称
    private String name;
    //企业法人名称
    private String legalPerson;
    //企业法人联系电话
    private String legalPersonPhone;
    //企业法人证件类型
    private String legalPersonCardType;
    //企业法人证件号码
    private String legalPersonCardNo;
    //身份证正面
    private String idCardFront;
    //身份证反面
    private String idCardVerso;
    //结算银行账户名称
    private String accountName;
    //结算银行账号
    private String accountNo;
    //开户银行名称
    private String accoutBankname;
    //开户银行联合号
    private String accoutBankno;
    //企业营业执照
    private String businessLicense;
    //企业营业执照号码
    private String businessLicenseNo;
    //企业经营门头店照片
    private String companyStoreLogo;
    
    private String taxLicense;

    private String franchiseLicense;
//主营分类
    private String mainCategory;

    private Integer provinceId;

    private Integer cityId;

    private Integer areaId;

    private String address;
    //注册地址
    private String registerAddress;
    
    private String  contact;
    //业务联系人电话
    private String contactTel;

    private String email;

    private String phone;

    private String fax;

    private Integer post;

    private Date createTime;

    private String createBy;

    private Date lastModifyTime;

    private Integer lastModifyBy;

    private Integer status;

    private String companyWebsite;

    private String companyNature;

    private String countryArea;
    
    private String taxLicenseNo;
    //头像地址
    private String icon;

    private String companyDescribe;

    private String companyLegitimacyUrl;

    private String companyDetailedUrl;
    
    private String checkFailReason;
    
	private Date auditTime; //审核日期
	
	private int supplyType;//供应商 类型 50特卖 51POP
	
	private int warehouseId; // 仓库ID

    private int isOnekeySend;  // 一键代发标记 1正常pop商家  2一键代发商户
    
    private String nameJC;					//公司简称
    private String companyInfo;				//公司信息
	private String companyLevel;			//公司评级
	private String companyRegion;			//入住区域类型	（字典：1 自营、2 、非自营）
	private String manager;					//企业经理人
	private	String managerTel;				//经理人电话
//	private double platformDiscount;		//平台折扣	这一期先不加
	private	String kfTel;					//客服电话
	private String userTj;					//推荐人
	private String logoImgurl;				//公司logo url
	private String companyQy;				//入驻区域（字典：1 自营、2 孵化、3 高新、4普通）

 //   reason for failure
	
	/************************** 新增 修改备份字段 ********************************/
	
	private String managerBackup;				//企业经理人备份
	private String managerTelBackup;			//经理人电话备份
	private String kfTelBackup;					//客服电话电话备份
	private String addressBackup;				//公司地址备份
	private String contactBackup;				//联系人备份
	private String phoneBackup;					//手机备份
	private String emailBackup;					//邮箱备份
	private String faxBackup;					//固定电话备份
	private String countryAreaBackup;			//国家地区备份
	private String postBackup;					//邮编备份
	private String logoImgurlBackup;			//企业logo备份
	
	private Integer modifyStatus;				//修改状态
	private String sjSupplierId;				//上级企业代码
	
	private String userLoginName;				//用户登录名称
	private Integer withdrawStatus; 			//线下企业二维码收款状态 0，不能收款 1，可以收款
	
	public Integer getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(Integer withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

	/**
	 * 企业个人账号姓名
	 */
	private String userName;				
	/**
	 * 企业个人账号手机号
	 */
	private String userMobile;				
	/**
	 * 企业个人账号红旗券支付密码
	 */
	private String hqqPwd;					
	/**
	 * 未支付红旗券总额
	 */
	private BigDecimal	noPayhqq;		
	/**
	 * 激活购买商品金额
	 */
	private BigDecimal	fanliPrice;		
	/**
	 * 剩余欠款额度
	 */
	private BigDecimal	remainBalanceAmount;
	/**
	 * 激活状态 -1未激活；0表示之前；1激活
	 */
	private Integer activeStatus;
	/**
	 * 企业个人账号ID 
	 */
	private Long userId;
	/**
	 * 企业个人账户姓名	不存库
	 */
	private String uName;
	/**
	 * 上级企业代码 不映射库字段 页面展示用
	 */
	private String sjSupplierCode;
	private BigDecimal priceTotal;
   
    //折扣
    private BigDecimal discount;
    private Integer companyBizCategory;
    private Integer companyBizType;
    
    
    
   
    
    public String getTaxLicenseNo() {
		return taxLicenseNo;
	}

	public void setTaxLicenseNo(String taxLicenseNo) {
		this.taxLicenseNo = taxLicenseNo;
	}

	public Integer getCompanyBizCategory() {
		return companyBizCategory;
	}

	public void setCompanyBizCategory(Integer companyBizCategory) {
		this.companyBizCategory = companyBizCategory;
	}

	public Integer getCompanyBizType() {
		return companyBizType;
	}

	public void setCompanyBizType(Integer companyBizType) {
		this.companyBizType = companyBizType;
	}

	public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
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

    public BigDecimal getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}

	private Integer level;			//等级标示，不映射数据库字段	1：上级；2:下级；
	
    public int getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(int supplyType) {
		this.supplyType = supplyType;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(Integer organizationType) {
        this.organizationType = organizationType;
    }

    public String getOrganizationalForm() {
        return organizationalForm;
    }

    public void setOrganizationalForm(String organizationalForm) {
        this.organizationalForm = organizationalForm == null ? null : organizationalForm.trim();
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense == null ? null : businessLicense.trim();
    }

    public Integer getBusinessScale() {
        return businessScale;
    }

    public void setBusinessScale(Integer businessScale) {
        this.businessScale = businessScale;
    }

    public String getHealthLicense() {
        return healthLicense;
    }

    public void setHealthLicense(String healthLicense) {
        this.healthLicense = healthLicense == null ? null : healthLicense.trim();
    }

    public String getLinesOfCredit() {
        return linesOfCredit;
    }

    public void setLinesOfCredit(String linesOfCredit) {
        this.linesOfCredit = linesOfCredit == null ? null : linesOfCredit.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }
    
    public String getLegalPersonPhone() {
		return legalPersonPhone;
	}

	public void setLegalPersonPhone(String legalPersonPhone) {
		this.legalPersonPhone = legalPersonPhone;
	}

	public String getTaxLicense() {
        return taxLicense;
    }

    public void setTaxLicense(String taxLicense) {
        this.taxLicense = taxLicense == null ? null : taxLicense.trim();
    }

    public String getFranchiseLicense() {
        return franchiseLicense;
    }

    public void setFranchiseLicense(String franchiseLicense) {
        this.franchiseLicense = franchiseLicense == null ? null : franchiseLicense.trim();
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory == null ? null : mainCategory.trim();
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
        this.address = address == null ? null : address.trim();
    }
    
    public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(Integer lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCompanyWebsite() {
		return companyWebsite;
	}

	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}

	public String getCompanyNature() {
        return companyNature;
    }

    public void setCompanyNature(String companyNature) {
        this.companyNature = companyNature == null ? null : companyNature.trim();
    }

    public String getCountryArea() {
        return countryArea;
    }

    public void setCountryArea(String countryArea) {
        this.countryArea = countryArea == null ? null : countryArea.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getCompanyDescribe() {
		return companyDescribe;
	}

	public void setCompanyDescribe(String companyDescribe) {
		this.companyDescribe = companyDescribe;
	}

	public String getCompanyLegitimacyUrl() {
        return companyLegitimacyUrl;
    }

    public void setCompanyLegitimacyUrl(String companyLegitimacyUrl) {
        this.companyLegitimacyUrl = companyLegitimacyUrl == null ? null : companyLegitimacyUrl.trim();
    }

    public String getCompanyDetailedUrl() {
        return companyDetailedUrl;
    }

    public void setCompanyDetailedUrl(String companyDetailedUrl) {
        this.companyDetailedUrl = companyDetailedUrl == null ? null : companyDetailedUrl.trim();
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

	public String getCheckFailReason() {
		return checkFailReason;
	}

	public void setCheckFailReason(String checkFailReason) {
		this.checkFailReason = checkFailReason;
	}

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getIsOnekeySend() {
        return isOnekeySend;
    }

    public void setIsOnekeySend(int isOnekeySend) {
        this.isOnekeySend = isOnekeySend;
    }
    
	public String getCompanyLevel() {
		return companyLevel;
	}

	public void setCompanyLevel(String companyLevel) {
		this.companyLevel = companyLevel;
	}

	public String getNameJC() {
		return nameJC;
	}

	public void setNameJC(String nameJC) {
		this.nameJC = nameJC;
	}

	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String getCompanyRegion() {
		return companyRegion;
	}

	public void setCompanyRegion(String companyRegion) {
		this.companyRegion = companyRegion;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManagerTel() {
		return managerTel;
	}

	public void setManagerTel(String managerTel) {
		this.managerTel = managerTel;
	}

	public String getKfTel() {
		return kfTel;
	}

	public void setKfTel(String kfTel) {
		this.kfTel = kfTel;
	}

	public String getUserTj() {
		return userTj;
	}

	public void setUserTj(String userTj) {
		this.userTj = userTj;
	}

	public String getLogoImgurl() {
		return logoImgurl;
	}

	public void setLogoImgurl(String logoImgurl) {
		this.logoImgurl = logoImgurl;
	}
	
	public String getCompanyQy() {
		return companyQy;
	}

	public void setCompanyQy(String companyQy) {
		this.companyQy = companyQy;
	}
	
	public String getManagerBackup() {
		return managerBackup;
	}

	public void setManagerBackup(String managerBackup) {
		this.managerBackup = managerBackup;
	}

	public String getManagerTelBackup() {
		return managerTelBackup;
	}

	public void setManagerTelBackup(String managerTelBackup) {
		this.managerTelBackup = managerTelBackup;
	}

	public String getKfTelBackup() {
		return kfTelBackup;
	}

	public void setKfTelBackup(String kfTelBackup) {
		this.kfTelBackup = kfTelBackup;
	}

	public String getAddressBackup() {
		return addressBackup;
	}

	public void setAddressBackup(String addressBackup) {
		this.addressBackup = addressBackup;
	}

	public String getContactBackup() {
		return contactBackup;
	}

	public void setContactBackup(String contactBackup) {
		this.contactBackup = contactBackup;
	}

	public String getPhoneBackup() {
		return phoneBackup;
	}

	public void setPhoneBackup(String phoneBackup) {
		this.phoneBackup = phoneBackup;
	}

	public String getEmailBackup() {
		return emailBackup;
	}

	public void setEmailBackup(String emailBackup) {
		this.emailBackup = emailBackup;
	}

	public String getFaxBackup() {
		return faxBackup;
	}

	public void setFaxBackup(String faxBackup) {
		this.faxBackup = faxBackup;
	}

	public String getCountryAreaBackup() {
		return countryAreaBackup;
	}

	public void setCountryAreaBackup(String countryAreaBackup) {
		this.countryAreaBackup = countryAreaBackup;
	}

	public String getPostBackup() {
		return postBackup;
	}

	public void setPostBackup(String postBackup) {
		this.postBackup = postBackup;
	}

	public String getLogoImgurlBackup() {
		return logoImgurlBackup;
	}

	public void setLogoImgurlBackup(String logoImgurlBackup) {
		this.logoImgurlBackup = logoImgurlBackup;
	}
	
	public Integer getModifyStatus() {
		return modifyStatus;
	}

	public void setModifyStatus(Integer modifyStatus) {
		this.modifyStatus = modifyStatus;
	}
	
	public String getSjSupplierId() {
		return sjSupplierId;
	}

	public void setSjSupplierId(String sjSupplierId) {
		this.sjSupplierId = sjSupplierId;
	}
	
	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	
	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
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

	public String getHqqPwd() {
		return hqqPwd;
	}

	public void setHqqPwd(String hqqPwd) {
		this.hqqPwd = hqqPwd;
	}

	public BigDecimal getNoPayhqq() {
		return noPayhqq;
	}

	public void setNoPayhqq(BigDecimal noPayhqq) {
		this.noPayhqq = noPayhqq;
	}

	public BigDecimal getRemainBalanceAmount() {
		return remainBalanceAmount;
	}

	public void setRemainBalanceAmount(BigDecimal remainBalanceAmount) {
		this.remainBalanceAmount = remainBalanceAmount;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}
	
	public String getSjSupplierCode() {
		return sjSupplierCode;
	}

	public void setSjSupplierCode(String sjSupplierCode) {
		this.sjSupplierCode = sjSupplierCode;
	}
	
	public BigDecimal getFanliPrice() {
		return fanliPrice;
	}

	public void setFanliPrice(BigDecimal fanliPrice) {
		this.fanliPrice = fanliPrice;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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

	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierCode=" + supplierCode + ", type=" + type
				+ ", parentId=" + parentId + ", grade=" + grade + ", organizationType=" + organizationType
				+ ", organizationalForm=" + organizationalForm + ", businessScale=" + businessScale + ", healthLicense="
				+ healthLicense + ", linesOfCredit=" + linesOfCredit + ", name=" + name + ", legalPerson=" + legalPerson
				+ ", legalPersonPhone=" + legalPersonPhone + ", legalPersonCardType=" + legalPersonCardType
				+ ", legalPersonCardNo=" + legalPersonCardNo + ", idCardFront=" + idCardFront + ", idCardVerso="
				+ idCardVerso + ", accountName=" + accountName + ", accountNo=" + accountNo + ", accoutBankname="
				+ accoutBankname + ", accoutBankno=" + accoutBankno + ", businessLicense=" + businessLicense
				+ ", businessLicenseNo=" + businessLicenseNo + ", companyStoreLogo=" + companyStoreLogo
				+ ", taxLicense=" + taxLicense + ", franchiseLicense=" + franchiseLicense + ", mainCategory="
				+ mainCategory + ", provinceId=" + provinceId + ", cityId=" + cityId + ", areaId=" + areaId
				+ ", address=" + address + ", registerAddress=" + registerAddress + ", contact=" + contact
				+ ", contactTel=" + contactTel + ", email=" + email + ", phone=" + phone + ", fax=" + fax + ", post="
				+ post + ", createTime=" + createTime + ", createBy=" + createBy + ", lastModifyTime=" + lastModifyTime
				+ ", lastModifyBy=" + lastModifyBy + ", status=" + status + ", companyWebsite=" + companyWebsite
				+ ", companyNature=" + companyNature + ", countryArea=" + countryArea + ", taxLicenseNo=" + taxLicenseNo
				+ ", icon=" + icon + ", companyDescribe=" + companyDescribe + ", companyLegitimacyUrl="
				+ companyLegitimacyUrl + ", companyDetailedUrl=" + companyDetailedUrl + ", checkFailReason="
				+ checkFailReason + ", auditTime=" + auditTime + ", supplyType=" + supplyType + ", warehouseId="
				+ warehouseId + ", isOnekeySend=" + isOnekeySend + ", nameJC=" + nameJC + ", companyInfo=" + companyInfo
				+ ", companyLevel=" + companyLevel + ", companyRegion=" + companyRegion + ", manager=" + manager
				+ ", managerTel=" + managerTel + ", kfTel=" + kfTel + ", userTj=" + userTj + ", logoImgurl="
				+ logoImgurl + ", companyQy=" + companyQy + ", managerBackup=" + managerBackup + ", managerTelBackup="
				+ managerTelBackup + ", kfTelBackup=" + kfTelBackup + ", addressBackup=" + addressBackup
				+ ", contactBackup=" + contactBackup + ", phoneBackup=" + phoneBackup + ", emailBackup=" + emailBackup
				+ ", faxBackup=" + faxBackup + ", countryAreaBackup=" + countryAreaBackup + ", postBackup=" + postBackup
				+ ", logoImgurlBackup=" + logoImgurlBackup + ", modifyStatus=" + modifyStatus + ", sjSupplierId="
				+ sjSupplierId + ", userLoginName=" + userLoginName + ", userName=" + userName + ", userMobile="
				+ userMobile + ", hqqPwd=" + hqqPwd + ", noPayhqq=" + noPayhqq + ", fanliPrice=" + fanliPrice
				+ ", remainBalanceAmount=" + remainBalanceAmount + ", activeStatus=" + activeStatus + ", userId="
				+ userId + ", uName=" + uName + ", sjSupplierCode=" + sjSupplierCode + ", priceTotal=" + priceTotal
				+ ", discount=" + discount + ", companyBizCategory=" + companyBizCategory + ", companyBizType="
				+ companyBizType + ", withdrawStatus=" + withdrawStatus + ", level=" + level + "]";
	}

	


	
	
}