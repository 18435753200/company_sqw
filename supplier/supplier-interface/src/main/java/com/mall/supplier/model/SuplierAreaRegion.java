package com.mall.supplier.model;

import java.io.Serializable;

public class SuplierAreaRegion implements Serializable {


	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8175973282627498112L;
	private int id;
	private long tplId;
	private int countyId;
	private int cityId;
	private int provinceId;
	private int type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getTplId() {
		return tplId;
	}
	public void setTplId(long tplId) {
		this.tplId = tplId;
	}
	public int getCountyId() {
		return countyId;
	}
	public void setCountyId(int countyId) {
		this.countyId = countyId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "SupplierAreaRegion [id=" + id + ", tplId=" + tplId + ", countyId=" + countyId + ", cityId=" + cityId
				+ ", provinceId=" + provinceId + ", type=" + type + "]";
	}
	
	
	
	

}
