package com.mall.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 满增
 * @author dell
 *
 */
public class WithIncreasing implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String promotionName;
	
	private String endTime;
	
	private List<Gift> gifts = new ArrayList<Gift>();
	
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
	public List<Gift> getGifts() {
		return gifts;
	}
	public void setGifts(List<Gift> gifts) {
		this.gifts = gifts;
	}
	public WithIncreasing(String promotionName, String endTime) {
		this.promotionName = promotionName;
		this.endTime = endTime;
	}
	public WithIncreasing() {
	}
	
}
