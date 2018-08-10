/**
 * 
 */
package com.mall.vo.enums;

/**
 * @author jianping.gao
 *
 */
public enum AddressTypeEnum {

	CONSIGNOR("寄件人地址", 1), STOREHOUSE("收货人地址", 2);

	private String name;

	private int index;

	/**
	 * @param name
	 * @param ordinal
	 */
	private AddressTypeEnum(String name, int index) {
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
