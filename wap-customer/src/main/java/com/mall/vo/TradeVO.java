package com.mall.vo;

public class TradeVO {

	private String retCode = "0";// 默认0为失败1成功

	private String retInfo;// 默认0为失败1成功

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

}
