/**
 * 
 */
package com.mall.vo.enums;

/**
 * @author jianping.gao
 *
 */
public enum ServiceStatusEmun {

	APPLY("提交申请", 1), SUBMIT("提交", 2), AUDIT("客服审核", 4), NOTTHEGOOD("鑫网未收货", 5), THEGOOD(
			"鑫网已收货", 6), NOTTHEDELIVERY("鑫网未发货", 7), THEDELIVERY("鑫网已发货", 8), FINISHED(
			"服务关单", 9), CANCELED("客户取消", 10), SIGNIN("客户签收", 11), AUDITREFUND(
			"审核退款", 12);

	private String name;

	private int index;

	/**
	 * @param name
	 * @param ordinal
	 */
	private ServiceStatusEmun(String name, int index) {
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
