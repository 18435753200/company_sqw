package com.mall.vo;

import java.util.List;

public class CpsOrder {
	
	private String campaignId;//活动ID
	private String fare;//运费
	private String favorable;//优惠额
	private String favorableCode;//优惠码
	private String feedback;//反馈标签 亿起发对应 wi，领克特对应cookie值
	private String orderNo;//订单编号
	private String orderTime;//下单时间
	private String orderstatus;//订单状态
	private String paymentStatus;//支付状态
	private String paymentType;//支付方式
	private String updateTime;//更新时间
	private String totalPrice;//订单应付金额
	
	private List<CpsOrderItem> orderItems;

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<CpsOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<CpsOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getFare() {
		return fare;
	}

	public void setFare(String fare) {
		this.fare = fare;
	}

	public String getFavorable() {
		return favorable;
	}

	public void setFavorable(String favorable) {
		this.favorable = favorable;
	}

	public String getFavorableCode() {
		return favorableCode;
	}

	public void setFavorableCode(String favorableCode) {
		this.favorableCode = favorableCode;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
