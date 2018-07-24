package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class LogisticTplWay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -134631867114626922L;
	
	private Long id;
	private Long logisticTplId;
	private int logisticWay;
	private String  logisticWayName;
	private int  state;
	private Date  createDatetime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLogisticTplId() {
		return logisticTplId;
	}
	public void setLogisticTplId(Long logisticTplId) {
		this.logisticTplId = logisticTplId;
	}
	public int getLogisticWay() {
		return logisticWay;
	}
	public void setLogisticWay(int logisticWay) {
		this.logisticWay = logisticWay;
	}
	public String getLogisticWayName() {
		return logisticWayName;
	}
	public void setLogisticWayName(String logisticWayName) {
		this.logisticWayName = logisticWayName;
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
		return "LogisticTplWay [id=" + id + ", logisticTplId=" + logisticTplId + ", logisticWay=" + logisticWay
				+ ", logisticWayName=" + logisticWayName + ", state=" + state + ", createDatetime=" + createDatetime
				+ "]";
	}
	
	

}
