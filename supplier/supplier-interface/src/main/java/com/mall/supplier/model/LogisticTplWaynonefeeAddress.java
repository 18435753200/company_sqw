package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class LogisticTplWaynonefeeAddress implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2046725893518575107L;

	private Long id;
	private Long logisticTplWaynonefeeId;
	private Long logisticProvinceid;
	private Long logisticCityid;
	private Long logisticAreaid;
	private  int state;
	private Date createDatetime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLogisticTplWaynonefeeId() {
		return logisticTplWaynonefeeId;
	}
	public void setLogisticTplWaynonefeeId(Long logisticTplWaynonefeeId) {
		this.logisticTplWaynonefeeId = logisticTplWaynonefeeId;
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
	public Long getLogisticAreaid() {
		return logisticAreaid;
	}
	public void setLogisticAreaid(Long logisticAreaid) {
		this.logisticAreaid = logisticAreaid;
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
		return "LogisticTplWaynonefeeAddress [id=" + id + ", logisticTplWaynonefeeId=" + logisticTplWaynonefeeId
				+ ", logisticProvinceid=" + logisticProvinceid + ", logisticCityid=" + logisticCityid
				+ ", logisticAreaid=" + logisticAreaid + ", state=" + state + ", createDatetime=" + createDatetime
				+ "]";
	}
	
}
