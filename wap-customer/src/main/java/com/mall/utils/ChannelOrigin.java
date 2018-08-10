package com.mall.utils;

import org.apache.commons.lang.StringUtils;

public enum ChannelOrigin {
	CHANNEL_ORIGIN_YICHUIYUAN_JIALIANHUA("914","逸翠园嘉年华");
	private String code;
	private String name;
	private ChannelOrigin(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	/**
	 * 验证来源是否在枚举类中，存在返回来源，不存在返回null
	 * @param origin 传入的来源
	 * @return
	 */
	public static String getOrigin(String origin) {
		if(StringUtils.isBlank(origin)) {
			return null;
		}
		ChannelOrigin[] values = ChannelOrigin.values();
		for (int i = 0; i < values.length; i++) {
			String cod = values[i].getCode();
			if(cod.equals(origin)) {
				return origin;
			}
		}
		return null;
	}
	
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
