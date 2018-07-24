package com.mall.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SupplierDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6562880300453631565L;

	// id
	private Integer id;
	// supplierId
	private Integer supplierId;
	// 公司简称/公司营业名称
	private String nameJC;
	// 企业经营门店照片上传文件
	private String companyStoreLogo;
	// 企业logo上传文件
	private String logoImgurl;
	// 联系人
	private String contact;
	// 联系人电话
	private String contactTel;
	// 联系人手机
	private String phone;
	// 经营特色
	private String jyTS;
	// 经营时间
	private String jySJ;
	//主营业务
	private String mainBusiness;
	// 经纬度类型
	private Integer locationType;
	// 纬度
	private BigDecimal locationLat;
	// 经度
	private BigDecimal locationLng;
	// 地图定位地址
	private String locationPoiaddress;
	// 地图定位名称
	private String locationPoiname;
	// 地图定位城市
	private String locationCityname;
	// 是可用 0, 不可用
	private Integer status;
	//详情页面图片
	private List<SupplierDetailAttr> attrList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public String getNameJC() {
		return nameJC;
	}
	public void setNameJC(String nameJC) {
		this.nameJC = nameJC;
	}
	public String getCompanyStoreLogo() {
		return companyStoreLogo;
	}
	public void setCompanyStoreLogo(String companyStoreLogo) {
		this.companyStoreLogo = companyStoreLogo;
	}
	public String getLogoImgurl() {
		return logoImgurl;
	}
	public void setLogoImgurl(String logoImgurl) {
		this.logoImgurl = logoImgurl;
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
	public String getJyTS() {
		return jyTS;
	}
	public void setJyTS(String jyTS) {
		this.jyTS = jyTS;
	}
	public String getJySJ() {
		return jySJ;
	}
	public void setJySJ(String jySJ) {
		this.jySJ = jySJ;
	}
	public String getMainBusiness() {
		return mainBusiness;
	}
	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}
	public Integer getLocationType() {
		return locationType;
	}
	public void setLocationType(Integer locationType) {
		this.locationType = locationType;
	}
	public BigDecimal getLocationLat() {
		return locationLat;
	}
	public void setLocationLat(BigDecimal locationLat) {
		this.locationLat = locationLat;
	}
	public BigDecimal getLocationLng() {
		return locationLng;
	}
	public void setLocationLng(BigDecimal locationLng) {
		this.locationLng = locationLng;
	}
	public String getLocationPoiaddress() {
		return locationPoiaddress;
	}
	public void setLocationPoiaddress(String locationPoiaddress) {
		this.locationPoiaddress = locationPoiaddress;
	}
	public String getLocationPoiname() {
		return locationPoiname;
	}
	public void setLocationPoiname(String locationPoiname) {
		this.locationPoiname = locationPoiname;
	}
	public String getLocationCityname() {
		return locationCityname;
	}
	public void setLocationCityname(String locationCityname) {
		this.locationCityname = locationCityname;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<SupplierDetailAttr> getAttrList() {
		return attrList;
	}
	public void setAttrList(List<SupplierDetailAttr> attrList) {
		this.attrList = attrList;
	}
	@Override
	public String toString() {
		return "SupplierDetail [id=" + id + ", supplierId=" + supplierId + ", nameJC=" + nameJC + ", companyStoreLogo="
				+ companyStoreLogo + ", logoImgurl=" + logoImgurl + ", contact=" + contact + ", contactTel="
				+ contactTel + ", phone=" + phone + ", jyTS=" + jyTS + ", jySJ=" + jySJ + ", mainBusiness="
				+ mainBusiness + ", locationType=" + locationType + ", locationLat=" + locationLat + ", locationLng="
				+ locationLng + ", locationPoiaddress=" + locationPoiaddress + ", locationPoiname=" + locationPoiname
				+ ", locationCityname=" + locationCityname + ", status=" + status + ", attrList=" + attrList + "]";
	}
	
	
	
}