package com.mall.po;

/**
 * 商品销量实体
 * @author DOUBLELL
 *
 */
public class ProductSales {
	 
	private String pid;  //商品ID
	private int qty;  //销量
	 
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}

}
