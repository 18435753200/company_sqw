package com.mall.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class WeiXinGZHVO implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 312068693521008811L;
	
	private String payFlowNo;
	private Long orderId;
	private String appId;//公众账号ID
	private String timeStamp;//时间戳
	private String nonceStr;//随机数
	private String prepayId;//返回码
	private String signType;//签名方式
	private String paySign;//签名
	private String err_msg;
	private String packager;
	
	
	public WeiXinGZHVO() {
		super();
	}

	public WeiXinGZHVO(String payFlowNo, Long orderId, String appId, String timeStamp, String nonceStr,
			String prepayId, String signType, String paySign, String err_msg, String packager) {
		super();
		this.orderId = orderId;
		this.payFlowNo = payFlowNo;
		this.appId = appId;
		this.timeStamp = timeStamp;
		this.nonceStr = nonceStr;
		this.prepayId = prepayId;
		this.signType = signType;
		this.paySign = paySign;
		this.err_msg = err_msg;
		this.packager = packager;
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPayFlowNo() {
		return payFlowNo;
	}

	public void setPayFlowNo(String payFlowNo) {
		this.payFlowNo = payFlowNo;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	
	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getPackager() {
		return packager;
	}

	public void setPackager(String packager) {
		this.packager = packager;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
