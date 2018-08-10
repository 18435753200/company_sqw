package com.mall.vo;

public class TradeRes {

	private String retCode = "0";// 默认0为失败1成功

	private String retInfo;// 默认0为失败1成功

	private String errorCode; // 错误码

	private String isCaptcha = "0"; // 0不需要1需要

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetInfo() {
		return retInfo;
	}

	public void setRetInfo(String retInfo) {
		this.retInfo = retInfo;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getIsCaptcha() {
		return isCaptcha;
	}

	public void setIsCaptcha(String isCaptcha) {
		this.isCaptcha = isCaptcha;
	}

}
