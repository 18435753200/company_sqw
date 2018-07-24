package com.mall.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LogisticTplWaynonefee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6754631959253346049L;

	private Long id;
	private Long logisticWayId;
	private int nonefeeNum;
	private BigDecimal nonefeePrice;
	private int type;
	private Date createDatetime;
	private int state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLogisticWayId() {
		return logisticWayId;
	}
	public void setLogisticWayId(Long logisticWayId) {
		this.logisticWayId = logisticWayId;
	}
	public int getNonefeeNum() {
		return nonefeeNum;
	}
	public void setNonefeeNum(int nonefeeNum) {
		this.nonefeeNum = nonefeeNum;
	}
	public BigDecimal getNonefeePrice() {
		return nonefeePrice;
	}
	public void setNonefeePrice(BigDecimal nonefeePrice) {
		this.nonefeePrice = nonefeePrice;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "LogisticTplWaynonefee [id=" + id + ", logisticWayId=" + logisticWayId + ", nonefeeNum=" + nonefeeNum
				+ ", nonefeePrice=" + nonefeePrice + ", type=" + type + ", createDatetime=" + createDatetime
				+ ", state=" + state + "]";
	}
	
	
	
	
}
