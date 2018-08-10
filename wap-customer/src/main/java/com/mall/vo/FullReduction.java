package com.mall.vo;

import java.io.Serializable;

/**
 * 满减
 * @author dell
 *
 */
public class FullReduction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String promotionName;
	
	private String endTime;
	
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public FullReduction(String promotionName, String endTime) {
		this.promotionName = promotionName;
		this.endTime = endTime;
	}
	public FullReduction() {
	}
}
