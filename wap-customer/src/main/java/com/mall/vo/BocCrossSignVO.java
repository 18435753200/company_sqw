package com.mall.vo;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

import com.mall.check.PayCheck.bocCrossAuthCheck;
import com.mall.check.PayCheck.bocCrossPayCheck;
import com.mall.check.PayCheck.bocCrossSignCheck;

public class BocCrossSignVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -275879712691110766L;
	
	private String DRACC_NO;
	private String DBT_NAME;
	private String DBT_CODE;
	private String DBT_PHONE;
	private String messageCode;
	private Long orderId;
	private String act;
	private String serNo;
	private String signNo;
	private boolean agreementFlag;
	
	
	@NotNull(message="{CCIGMALL-100000.filed}",groups={bocCrossSignCheck.class})
	@Pattern(regexp="^\\d{19}$", message="{CCIGMALL-100006.filed}",groups={bocCrossSignCheck.class})
	public String getDRACC_NO() {
		return DRACC_NO;
	}

	public void setDRACC_NO(String dRACC_NO) {
		DRACC_NO = dRACC_NO;
	}
	
	@NotNull(message="{CCIGMALL-100000.filed}",groups={bocCrossSignCheck.class})
	@Length(min=1,max=20)
	public String getDBT_NAME() {
		return DBT_NAME;
	}
	public void setDBT_NAME(String dBT_NAME) {
		DBT_NAME = dBT_NAME;
	}
	@NotNull(message="{CCIGMALL-100000.filed}",groups={bocCrossSignCheck.class})
	@Pattern(regexp="(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$)", message="{CCIGMALL-100006.filed}",groups={bocCrossSignCheck.class})
	public String getDBT_CODE() {
		return DBT_CODE;
	}
	public void setDBT_CODE(String dBT_CODE) {
		DBT_CODE = dBT_CODE;
	}
	@NotNull(message="{CCIGMALL-100000.filed}",groups={bocCrossSignCheck.class})
	@Pattern(regexp="(^1[0-9]{10}$)",message="{CCIGMALL-100006.filed}",groups={bocCrossSignCheck.class})
	public String getDBT_PHONE() {
		return DBT_PHONE;
	}
	public void setDBT_PHONE(String dBT_PHONE) {
		DBT_PHONE = dBT_PHONE;
	}
	@NotNull(message="{CCIGMALL-100000.filed}",groups={bocCrossAuthCheck.class,bocCrossPayCheck.class})
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	
	@NotNull(message="{CCIGMALL-100000.filed}",groups={bocCrossAuthCheck.class})
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@NotNull(message="{CCIGMALL-100000.filed}",groups={bocCrossAuthCheck.class})
	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}
	@NotNull(message="{CCIGMALL-100000.filed}",groups={bocCrossAuthCheck.class})
	public String getSerNo() {
		return serNo;
	}
	
	public void setSerNo(String serNo) {
		this.serNo = serNo;
	}
	@NotNull(message="{CCIGMALL-100000.filed}",groups={bocCrossPayCheck.class})
	public String getSignNo() {
		return signNo;
	}

	public void setSignNo(String signNo) {
		this.signNo = signNo;
	}
	
	@AssertTrue(message="{CCIGMALL-100000.filed}",groups={bocCrossAuthCheck.class})
	public boolean isAgreementFlag() {
		return agreementFlag;
	}

	public void setAgreementFlag(boolean agreementFlag) {
		this.agreementFlag = agreementFlag;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
