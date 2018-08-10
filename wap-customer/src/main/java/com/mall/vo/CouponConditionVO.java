/**
 * 
 */
package com.mall.vo;

import java.util.List;

/**
 * @author zhengqiang.shi
 * 2015年5月15日 上午10:56:05
 */
public class CouponConditionVO {

	private int index;
	private List<String> stockIdList;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public List<String> getStockIdList() {
		return stockIdList;
	}
	public void setStockIdList(List<String> stockIdList) {
		this.stockIdList = stockIdList;
	}
}
