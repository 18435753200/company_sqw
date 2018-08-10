package com.mall.dsearch.vo;

import java.io.Serializable;

public class SuggestionRequest implements Serializable{
	/**
	 * 联想词搜索请求参数
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private boolean isB2C=false;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isB2C() {
		return isB2C;
	}
	public void setB2C(boolean isB2C) {
		this.isB2C = isB2C;
	}
	
}
