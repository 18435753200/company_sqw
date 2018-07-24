package com.mall.po;

import java.io.Serializable;
/**
 * 大区下的城市
 * @author bowen0801@126.com
 *
 */
public class AgentCity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer cityId;

    private String cityName;

    private String postCode;

    private String zoneCode;

    private String provinceId;

    private String city;
    
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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "AgentCity [cityId=" + cityId + ", cityName=" + cityName + ", postCode=" + postCode + ", zoneCode="
				+ zoneCode + ", provinceId=" + provinceId + ", city=" + city + "]";
	}
    
    
   

}