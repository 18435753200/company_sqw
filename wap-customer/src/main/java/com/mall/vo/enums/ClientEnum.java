/**
 * 
 */
package com.mall.vo.enums;

/**
 * @author jianping.gao
 *
 */
public enum ClientEnum {

	PC("pc", 1), PAD("pad", 2), WAP("wap", 3), ANDROID("android", 4), IOS(
			"ios", 5);

	private String name;

	private int index;

	/**
	 * @param name
	 * @param ordinal
	 */
	private ClientEnum(String name, int index) {
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

}
