/**
 * 
 */
package com.mall.vo.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author jianping.gao
 *
 */
public enum PatternEnum {

	B2B("b2b", 1), B2C("b2c", 2), OTHER("第三方", 3);

	private String name;

	private int index;

	/**
	 * @param name
	 * @param ordinal
	 */
	private PatternEnum(String name, int index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	public static PatternEnum getEmunByIndex(String index) {
		int _index = 0;
		try {
			_index = Integer.parseInt(index);
		} catch (Exception e) {
			return null;
		}

		for (PatternEnum emun : PatternEnum.values()) {
			if (_index == emun.index) {
				return emun;
			}
		}
		return null;
	}
	
	public static PatternEnum getEmunByName(String name) {
		if(StringUtils.isEmpty(name)){
			return null;
		}
		for (PatternEnum emun : PatternEnum.values()) {
			if (name.equals(emun.name)) {
				return emun;
			}
		}
		return null;
	}
}
