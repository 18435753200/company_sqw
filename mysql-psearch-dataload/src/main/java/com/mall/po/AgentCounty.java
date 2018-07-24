package com.mall.po;

import java.io.Serializable;

/**
 * 大区下的县区
 * @author bowen0801@126.com
 *
 */
public class AgentCounty implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer countyId;

    private String countyName;

    private Integer cityId;

    private Integer provinceId;

    private String countyNameEn;

	public Integer getCountyId() {
		return countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
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

	public String getCountyNameEn() {
		return countyNameEn;
	}

	public void setCountyNameEn(String countyNameEn) {
		this.countyNameEn = countyNameEn;
	}

	@Override
	public String toString() {
		return "AgentCounty [countyId=" + countyId + ", countyName=" + countyName + ", cityId=" + cityId
				+ ", provinceId=" + provinceId + ", countyNameEn=" + countyNameEn + "]";
	}

    
    
	
}