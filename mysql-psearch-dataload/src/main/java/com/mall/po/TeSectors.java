package com.mall.po;

import java.io.Serializable;

public class TeSectors implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7241333425884859973L;

	private Integer sectorCode;

    private String sectorType;

    private String sectorName;

    private String sectorStatus;

	public Integer getSectorCode() {
		return sectorCode;
	}

	public void setSectorCode(Integer sectorCode) {
		this.sectorCode = sectorCode;
	}

	public String getSectorType() {
		return sectorType;
	}

	public void setSectorType(String sectorType) {
		this.sectorType = sectorType;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getSectorStatus() {
		return sectorStatus;
	}

	public void setSectorStatus(String sectorStatus) {
		this.sectorStatus = sectorStatus;
	}

	@Override
	public String toString() {
		return "TeSectors [sectorCode=" + sectorCode + ", sectorType=" + sectorType + ", sectorName=" + sectorName
				+ ", sectorStatus=" + sectorStatus + "]";
	}

   
}