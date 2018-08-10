package com.mall.dsearch.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


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
	// 下面两个字段为判断商家状态的字段
	private Integer activeStatus;

	private Integer status;

	/**
	 * 此处开始为门店详情的相关字段数据
	 */
	// id
	private Integer sdId;
	// 经营特色
	private String sdJyTS;
	// 经营时间
	private String sdJySJ;
	// 主营业务id
	private Integer sdMainBusinessId;
	// 主营业务
	private String sdMainBusiness;
	// 经纬度类型
	private Integer sdLocationType;
	// 纬度
	private BigDecimal sdLocationLat;
	// 经度
	private BigDecimal sdLocationLng;
	// 地图定位地址
	private String sdLocationPoiaddress;
	// 地图定位名称
	private String sdLocationPoiname;
	// 地图定位城市
	private String sdLocationCityname;
	// 是可用 0, 不可用
	private Integer sdStatus;
	// 门店详情的相关图片数据
	private List<SupplierDetailAttr> attrList;
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
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getCompanyBizType() {
		return companyBizType;
	}
	public void setCompanyBizType(Integer companyBizType) {
		this.companyBizType = companyBizType;
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
	public Integer getSdId() {
		return sdId;
	}
	public void setSdId(Integer sdId) {
		this.sdId = sdId;
	}
	public String getSdJyTS() {
		return sdJyTS;
	}
	public void setSdJyTS(String sdJyTS) {
		this.sdJyTS = sdJyTS;
	}
	public String getSdJySJ() {
		return sdJySJ;
	}
	public void setSdJySJ(String sdJySJ) {
		this.sdJySJ = sdJySJ;
	}
	public Integer getSdMainBusinessId() {
		return sdMainBusinessId;
	}
	public void setSdMainBusinessId(Integer sdMainBusinessId) {
		this.sdMainBusinessId = sdMainBusinessId;
	}
	public String getSdMainBusiness() {
		return sdMainBusiness;
	}
	public void setSdMainBusiness(String sdMainBusiness) {
		this.sdMainBusiness = sdMainBusiness;
	}
	public Integer getSdLocationType() {
		return sdLocationType;
	}
	public void setSdLocationType(Integer sdLocationType) {
		this.sdLocationType = sdLocationType;
	}
	public BigDecimal getSdLocationLat() {
		return sdLocationLat;
	}
	public void setSdLocationLat(BigDecimal sdLocationLat) {
		this.sdLocationLat = sdLocationLat;
	}
	public BigDecimal getSdLocationLng() {
		return sdLocationLng;
	}
	public void setSdLocationLng(BigDecimal sdLocationLng) {
		this.sdLocationLng = sdLocationLng;
	}
	public String getSdLocationPoiaddress() {
		return sdLocationPoiaddress;
	}
	public void setSdLocationPoiaddress(String sdLocationPoiaddress) {
		this.sdLocationPoiaddress = sdLocationPoiaddress;
	}
	public String getSdLocationPoiname() {
		return sdLocationPoiname;
	}
	public void setSdLocationPoiname(String sdLocationPoiname) {
		this.sdLocationPoiname = sdLocationPoiname;
	}
	public String getSdLocationCityname() {
		return sdLocationCityname;
	}
	public void setSdLocationCityname(String sdLocationCityname) {
		this.sdLocationCityname = sdLocationCityname;
	}
	public Integer getSdStatus() {
		return sdStatus;
	}
	public void setSdStatus(Integer sdStatus) {
		this.sdStatus = sdStatus;
	}
	public List<SupplierDetailAttr> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<SupplierDetailAttr> attrList) {
		this.attrList = attrList;
	}
	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierCode=" + supplierCode + ", name=" + name + ", nameJC="
				+ nameJC + ", organizationType=" + organizationType + ", type=" + type + ", provinceId=" + provinceId
				+ ", provinceName=" + provinceName + ", cityId=" + cityId + ", cityName=" + cityName + ", areaId="
				+ areaId + ", areaName=" + areaName + ", companyBizType=" + companyBizType + ", companyBizName="
				+ companyBizName + ", companyBizCategory=" + companyBizCategory + ", companyBizCategoryName="
				+ companyBizCategoryName + ", companyInfo=" + companyInfo + ", companyDescribe=" + companyDescribe
				+ ", logoImgurl=" + logoImgurl + ", companyStoreLogo=" + companyStoreLogo + ", address=" + address
				+ ", contact=" + contact + ", contactTel=" + contactTel + ", phone=" + phone + ", companyRegion="
				+ companyRegion + ", manager=" + manager + ", managerTel=" + managerTel + ", icon=" + icon
				+ ", companyQy=" + companyQy + ", createTime=" + createTime + ", createtimeDate=" + createtimeDate
				+ ", activeStatus=" + activeStatus + ", status=" + status + ", sdId=" + sdId + ", sdJyTS=" + sdJyTS
				+ ", sdJySJ=" + sdJySJ + ", sdMainBusinessId=" + sdMainBusinessId + ", sdMainBusiness=" + sdMainBusiness
				+ ", sdLocationType=" + sdLocationType + ", sdLocationLat=" + sdLocationLat + ", sdLocationLng="
				+ sdLocationLng + ", sdLocationPoiaddress=" + sdLocationPoiaddress + ", sdLocationPoiname="
				+ sdLocationPoiname + ", sdLocationCityname=" + sdLocationCityname + ", sdStatus=" + sdStatus
				+ ", attrList=" + attrList + "]";
	}


	
}