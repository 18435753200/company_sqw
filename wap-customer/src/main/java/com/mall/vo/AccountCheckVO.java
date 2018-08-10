package com.mall.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.mall.check.PayCheck.accountCheck;

public class AccountCheckVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5209353501210643888L;
	private String startDate;
	private String endDate;
	private Short bankPayModel;
	private Short bizType;
	private String act;
	
	@NotNull(message="{CCIGMALL-100000.filed}", groups={accountCheck.class})
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	@NotNull(message="{CCIGMALL-100000.filed}", groups={accountCheck.class})
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	@NotNull(message="{CCIGMALL-100000.filed}", groups={accountCheck.class})
	public Short getBankPayModel() {
		return bankPayModel;
	}

	public void setBankPayModel(Short bankPayModel) {
		this.bankPayModel = bankPayModel;
	}
	@NotNull(message="{CCIGMALL-100000.filed}", groups={accountCheck.class})
	public Short getBizType() {
		return bizType;
	}

	public void setBizType(Short bizType) {
		this.bizType = bizType;
	}
	@NotNull(message="{CCIGMALL-100000.filed}", groups={accountCheck.class})
	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
