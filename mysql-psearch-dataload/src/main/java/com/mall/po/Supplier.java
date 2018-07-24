package com.mall.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zcc
 *
 */
/**
 * @author zcc
 *
 */
public class Supplier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7593121354987753894L;

	// id
	private Long supplierId;
	// 供应商编码
	private String supplierCode;
	// 公司名称
	private String name;
	// 公司简称
	private String nameJC;
	//
	private Integer organizationType;
	// 商家类型
	private Integer type;
	// 省id
	private Integer provinceId;
	// 省name
	private String provinceName;
	// 市id
	private Integer cityId;
	// 市name
	private String cityName;
	// 区id
	private Integer areaId;
	// 区name
	private String areaName;
	// 企业 2 个体工商户 3事业单位
	private Integer companyBizType;
	// 企业 2 个体工商户 3事业单位
	private String companyBizName;
	// 企业所属类目(参考企业分类字典数据)
	private Integer companyBizCategory;
	// 企业所属类目(参考企业分类字典数据)
	private String companyBizCategoryName;
	// 企业所属类目条件细致划分
	private ArrayList<String> companyBizCategoryMore;
	// 公司信息
	private String companyInfo;
	// 公司描述
	private String companyDescribe;
	// 企业logo上传文件
	private String logoImgurl;
	// 企业经营门头店照片
	private String companyStoreLogo;
	// 办公地址/营业地址
	private String address;
	// 联系人
	private String contact;
	// 业务联系人电话
	private String contactTel;
	// 联系人手机
	private String phone;
	// 入住区域类型 （字典：1 自营、2 、非自营）
	private String companyRegion;
	// 企业经理人
	private String manager;
	// 经理人电话
	private String managerTel;
	// 头像地址
	private String icon;
	// 入驻区域（字典：1 自营、2 孵化、3 高新、4普通）
	private String companyQy;
	// 创建时间
	private String createTime;
	// 创建时间的data类型数据
	private String createtimeDate;
	
	/**
	 * 判断商家状态
	 * @return
	 */
	private Integer activeStatus;
	
	private Integer status;

	private  SupplierDetail supplierDetail;
	
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
		this.supplierCode = supplierCode;
	}

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

	public Integer getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(Integer organizationType) {
		this.organizationType = organizationType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getCompanyBizType() {
		return companyBizType;
	}

	public void setCompanyBizType(Integer companyBizType) {
		this.companyBizType = companyBizType;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCompanyBizName() {
		return companyBizName;
	}

	public void setCompanyBizName(String companyBizName) {
		this.companyBizName = companyBizName;
	}

	public Integer getCompanyBizCategory() {
		return companyBizCategory;
	}

	public void setCompanyBizCategory(Integer companyBizCategory) {
		this.companyBizCategory = companyBizCategory;
	}

	public String getCompanyBizCategoryName() {
		return companyBizCategoryName;
	}

	public void setCompanyBizCategoryName(String companyBizCategoryName) {
		this.companyBizCategoryName = companyBizCategoryName;
	}

	public ArrayList<String> getCompanyBizCategoryMore() {
		return companyBizCategoryMore;
	}

	public void setCompanyBizCategoryMore(ArrayList<String> companyBizCategoryMore) {
		this.companyBizCategoryMore = companyBizCategoryMore;
	}

	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String getCompanyDescribe() {
		return companyDescribe;
	}

	public void setCompanyDescribe(String companyDescribe) {
		this.companyDescribe = companyDescribe;
	}

	public String getLogoImgurl() {
		return logoImgurl;
	}

	public void setLogoImgurl(String logoImgurl) {
		this.logoImgurl = logoImgurl;
	}

	public String getCompanyStoreLogo() {
		return companyStoreLogo;
	}

	public void setCompanyStoreLogo(String companyStoreLogo) {
		this.companyStoreLogo = companyStoreLogo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCompanyQy() {
		return companyQy;
	}

	public void setCompanyQy(String companyQy) {
		this.companyQy = companyQy;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreatetimeDate() {
		return createtimeDate;
	}

	public void setCreatetimeDate(String createtimeDate) {
		this.createtimeDate = createtimeDate;
	}

	public SupplierDetail getSupplierDetail() {
		return supplierDetail;
	}

	public void setSupplierDetail(SupplierDetail supplierDetail) {
		this.supplierDetail = supplierDetail;
	}

	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierCode=" + supplierCode + ", name=" + name + ", nameJC="
				+ nameJC + ", organizationType=" + organizationType + ", type=" + type + ", provinceId=" + provinceId
				+ ", provinceName=" + provinceName + ", cityId=" + cityId + ", cityName=" + cityName + ", areaId="
				+ areaId + ", areaName=" + areaName + ", companyBizType=" + companyBizType + ", companyBizName="
				+ companyBizName + ", companyBizCategory=" + companyBizCategory + ", companyBizCategoryName="
				+ companyBizCategoryName + ", companyBizCategoryMore=" + companyBizCategoryMore + ", companyInfo="
				+ companyInfo + ", companyDescribe=" + companyDescribe + ", logoImgurl=" + logoImgurl
				+ ", companyStoreLogo=" + companyStoreLogo + ", address=" + address + ", contact=" + contact
				+ ", contactTel=" + contactTel + ", phone=" + phone + ", companyRegion=" + companyRegion + ", manager="
				+ manager + ", managerTel=" + managerTel + ", icon=" + icon + ", companyQy=" + companyQy
				+ ", createTime=" + createTime + ", createtimeDate=" + createtimeDate + ", activeStatus=" + activeStatus
				+ ", status=" + status + ", supplierDetail=" + supplierDetail + "]";
	}


	

}