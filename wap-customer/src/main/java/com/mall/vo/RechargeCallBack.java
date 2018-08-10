/**
 * 
 */
package com.mall.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author zhengqiang.shi
 * 2016-5-20 下午1:54:21
 */
public class RechargeCallBack implements java.io.Serializable{

	private static final long serialVersionUID = -669476082111558357L;

	private String orderid;
	
	private String status;
	
	private String ordermoney;
	
	private String verifystring;
	
	private String mobileBalance;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getVerifystring() {
		return verifystring;
	}

	public void setVerifystring(String verifystring) {
		this.verifystring = verifystring;
	}
	
	public String getOrdermoney() {
		return ordermoney;
	}

	public void setOrdermoney(String ordermoney) {
		this.ordermoney = ordermoney;
	}

	public String getMobileBalance() {
		return mobileBalance;
	}

	public void setMobileBalance(String mobileBalance) {
		this.mobileBalance = mobileBalance;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
