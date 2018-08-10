/**
 * 
 */
package com.mall.vo.enums;

/**
 * @author jianping.gao
 *
 */
public enum ServiceTypeEmun {
	RETURN("退货", 1), REPLACE("换货", 2), CANCELSTOCK("拒收(已出库)", 3), CANCELORDER("拒收(未出库)", 4);

	private String name;

	private int index;

	/**
	 * @param name
	 * @param ordinal
	 */
	private ServiceTypeEmun(String name, int index) {
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
	
	public static ServiceTypeEmun getEmunByIndex(String index){
		int _index = 0;
		try {
			_index = Integer.parseInt(index);
		} catch (Exception e) {
			return null;
		}
		
		for (ServiceTypeEmun emun : ServiceTypeEmun.values()) {
			if(_index==emun.index){
				return emun;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		
		System.out.println();
	}

}
