package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class LogisticTplWayfeeAddress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3415783176526833100L;

	private Long id;
	private Long logisticTplWayfeeId;
	private Long logisticProvinceid;
	private Long logisticCityid;
	private Long logisticAreaId;
	private int state;
	private Date createDatetime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLogisticTplWayfeeId() {
		return logisticTplWayfeeId;
	}
	public void setLogisticTplWayfeeId(Long logisticTplWayfeeId) {
		this.logisticTplWayfeeId = logisticTplWayfeeId;
	}
	public Long getLogisticProvinceid() {
		return logisticProvinceid;
	}
	public void setLogisticProvinceid(Long logisticProvinceid) {
		this.logisticProvinceid = logisticProvinceid;
	}
	public Long getLogisticCityid() {
		return logisticCityid;
	}
	public void setLogisticCityid(Long logisticCityid) {
		this.logisticCityid = logisticCityid;
	}
	public Long getLogisticAreaId() {
		return logisticAreaId;
	}
	public void setLogisticAreaId(Long logisticAreaId) {
		this.logisticAreaId = logisticAreaId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	@Override
	public String toString() {
		return "LogisticTplWayfeeAddress [id=" + id + ", logisticTplWayfeeId=" + logisticTplWayfeeId
				+ ", logisticProvinceid=" + logisticProvinceid + ", logisticCityid=" + logisticCityid
				+ ", logisticAreaId=" + logisticAreaId + ", state=" + state + ", createDatetime=" + createDatetime
				+ "]";
	}
	
	
}
