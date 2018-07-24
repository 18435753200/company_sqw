package com.mall.po;

import java.io.Serializable;

public class AgentProvince implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer provinceid;

    private String provinceName;

    private String provinceNameEn;

    private Integer regionId;
    
    private Integer sortVal;

	public Integer getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceNameEn() {
		return provinceNameEn;
	}

	public void setProvinceNameEn(String provinceNameEn) {
		this.provinceNameEn = provinceNameEn;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getSortVal() {
		return sortVal;
	}

	public void setSortVal(Integer sortVal) {
		this.sortVal = sortVal;
	}

	@Override
	public String toString() {
		return "AgentProvince [provinceid=" + provinceid + ", provinceName=" + provinceName + ", provinceNameEn="
				+ provinceNameEn + ", regionId=" + regionId + ", sortVal=" + sortVal + "]";
	}


    

    

   
}