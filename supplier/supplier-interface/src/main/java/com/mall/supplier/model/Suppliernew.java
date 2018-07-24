package com.mall.supplier.model;

import java.io.Serializable;

public class Suppliernew implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7593121354987753894L;

	private Long supplier_id;

    private String supplier_code;
   //公司名称
    private String name;
    private String userMobile;
    public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	private Integer type;
    private Integer status;
    private Integer organization_type;
    public Integer getOrganization_type() {
		return organization_type;
	}

	public void setOrganization_type(Integer organization_type) {
		this.organization_type = organization_type;
	}

	private String userid;
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(Long supplier_id) {
		this.supplier_id = supplier_id;
	}

	public String getSupplier_code() {
		return supplier_code;
	}

	public void setSupplier_code(String supplier_code) {
		this.supplier_code = supplier_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
    

}