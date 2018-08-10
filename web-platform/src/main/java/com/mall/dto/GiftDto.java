package com.mall.dto;

import java.io.Serializable;
/**
 * 赠品Dto
 *
 */
public class GiftDto implements Serializable{
	
	private String giftId;
	private String giftName;
	private String giftType;
	
	
	public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	public String getGiftType() {
		return giftType;
	}
	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}
	
	

}
