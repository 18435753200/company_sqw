package com.mall.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LogisticTplWayfee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7146867952729177306L;
	
	private Long id;
	private Long logisticWayId;
	private int startNum;
	private BigDecimal startPrice;
	private int addNum;
	private BigDecimal addPrice;
	private int type;
	private Date createDatetime;
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
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public BigDecimal getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}
	public int getAddNum() {
		return addNum;
	}
	public void setAddNum(int addNum) {
		this.addNum = addNum;
	}
	public BigDecimal getAddPrice() {
		return addPrice;
	}
	public void setAddPrice(BigDecimal addPrice) {
		this.addPrice = addPrice;
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
	@Override
	public String toString() {
		return "LogisticTplWayfee [id=" + id + ", logisticWayId=" + logisticWayId + ", startNum=" + startNum
				+ ", startPrice=" + startPrice + ", addNum=" + addNum + ", addPrice=" + addPrice + ", type=" + type
				+ ", createDatetime=" + createDatetime + "]";
	}
	
}
