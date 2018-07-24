package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class SupplierUser implements Serializable {
    private Long userId;//id

    private Long supplierId;//供应商id

    private String loginName;//登录名

    private String password;//登录密码

    private String nickName;//名昵

    private String trueName;//真实姓名

    private String identificationCard;//商标

    private Integer type;//类型

    private Integer isAdmin;//是否管理员（1管理员，0普通用户）

    private String isPlatformCreate;//是否平台创建（1平台创建0注册）

    private Integer grade;//等级

    private Integer sex;//性别

    private String email;//邮箱

    private String phone;//电话

    private String mobile;//手机

    private String fax;//传真

    private Integer areaId;//区域Id

    private Integer cityId;// 城市id

    private Integer provinceId;//省份id

    private Integer address;//地址

    private Date birthday;//生日

    private Integer isEmailConfirmed;//邮箱是否确定

    private Integer isPhoneConfirmed;//电话是否确定

    private Integer invitor;//邀请者

    private Date lastLoginTime;//上次登录时间

    private Date createTime;//创建时间

    private String createBy;//创建人

    private Date lastModifyTime;//最后修改时间

    private Integer lastModifyBy;//最后修改人

    private Integer status;//状态（0:未审核 1:审核通过 2:审核未通过）
    
  /*  private SupplierRole supplierRole;*/
    
    /**
     * 记录企业密码明文
     */
    private String recordPwd;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard == null ? null : identificationCard.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getIsPlatformCreate() {
        return isPlatformCreate;
    }

    public void setIsPlatformCreate(String isPlatformCreate) {
        this.isPlatformCreate = isPlatformCreate == null ? null : isPlatformCreate.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getIsEmailConfirmed() {
        return isEmailConfirmed;
    }

    public void setIsEmailConfirmed(Integer isEmailConfirmed) {
        this.isEmailConfirmed = isEmailConfirmed;
    }

    public Integer getIsPhoneConfirmed() {
        return isPhoneConfirmed;
    }

    public void setIsPhoneConfirmed(Integer isPhoneConfirmed) {
        this.isPhoneConfirmed = isPhoneConfirmed;
    }

    public Integer getInvitor() {
        return invitor;
    }

    public void setInvitor(Integer invitor) {
        this.invitor = invitor;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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
/*
	public SupplierRole getSupplierRole() {
		return supplierRole;
	}

	public void setSupplierRole(SupplierRole supplierRole) {
		this.supplierRole = supplierRole;
	}*/

	public String getRecordPwd() {
		return recordPwd;
	}

	public void setRecordPwd(String recordPwd) {
		this.recordPwd = recordPwd;
	}
    
}