/**
 * 
 */
package com.mall.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.mall.check.OrderChecks.submitRechargeOrderChecks;

/**
 * @author zhengqiang.shi
 * 2016-4-22 下午5:16:42
 */
public class CustomerRechargeOrderVO implements Serializable{
	
	private static final long serialVersionUID = 2109052624547026177L;

	private Long skuId;	// 商品规格ID
	
	private String phoneNumber;	// 充值的手机号
	
	private Long couponsId;	// 优惠券id
	
	private Short couponsType;	// 优惠券类型
	
	private String orderPlatform;	// 平台
	
	//@Size(min=1,message="{CCIGMALL-100001.filed}",groups={submitTelecomOrderChecks.class})
	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	@NotNull(message="{CCIGMALL-100000.filed}",groups={submitRechargeOrderChecks.class})
	@Length(min=11,max=11,groups={submitRechargeOrderChecks.class})
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getCouponsId() {
		return couponsId;
	}

	public void setCouponsId(Long couponsId) {
		this.couponsId = couponsId;
	}

	public Short getCouponsType() {
		return couponsType;
	}

	public void setCouponsType(Short couponsType) {
		this.couponsType = couponsType;
	}

	@NotNull(message="{CCIGMALL-100000.filed}",groups={submitRechargeOrderChecks.class})
	@Range(min=2,max=4,message="{CCIGMALL-100002.filed}",groups={submitRechargeOrderChecks.class})
	public String getOrderPlatform() {
		return orderPlatform;
	}

	public void setOrderPlatform(String orderPlatform) {
		this.orderPlatform = orderPlatform;
	}
	
	

}