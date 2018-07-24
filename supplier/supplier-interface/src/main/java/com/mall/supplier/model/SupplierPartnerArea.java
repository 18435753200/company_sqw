package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author Administrator
 * 
 */
public class SupplierPartnerArea implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8928292546633508623L;
	private int id;
	private Long supplierId;
	private Long countyId;
	private Long cityId;
	private Long provinceId;
	private int type;
	private Date startDateTime;
	private Date endDateTime;
	private int partnerType;
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public int getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(int partnerType) {
		this.partnerType = partnerType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SupplierPartnerArea [id=" + id + ", supplierId=" + supplierId
				+ ", countyId=" + countyId + ", cityId=" + cityId
				+ ", provinceId=" + provinceId + ", type=" + type
				+ ", StartDateTime=" + startDateTime + ", endDateTime="
				+ endDateTime + ", partnerType=" + partnerType + ", status="
				+ status + "]";
	}

}
