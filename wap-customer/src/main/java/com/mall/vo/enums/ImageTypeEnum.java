/**
 * 
 */
package com.mall.vo.enums;

/**
 * @author jianping.gao
 *
 */
public enum ImageTypeEnum {

	IMAGE("图片", 1), ATTACH("附件", 2), REFUNDATTACH("退款附件", 3);

	private String name;

	private int index;

	/**
	 * @param name
	 * @param ordinal
	 */
	private ImageTypeEnum(String name, int index) {
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
