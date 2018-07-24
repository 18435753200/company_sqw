package com.mall.po;

/**
 *  经销商商品销售信息（销售价格-）
 *  @author DOUBLELL
 */
public class DealerProInfoSaleSetting {
	private String productid;
	private String min_wholesale_qty;
	private String min_buyer_price;
	private String max_buyer_price;
	private String min_seller_price;
	private String max_seller_price;
	private String leadingtime;

	public String getMin_wholesale_qty() {
		return min_wholesale_qty;
	}

	public void setMin_wholesale_qty(String min_wholesale_qty) {
		this.min_wholesale_qty = min_wholesale_qty;
	}

	public String getMin_buyer_price() {
		return min_buyer_price;
	}

	public void setMin_buyer_price(String min_buyer_price) {
		this.min_buyer_price = min_buyer_price;
	}

	public String getMax_buyer_price() {
		return max_buyer_price;
	}

	public void setMax_buyer_price(String max_buyer_price) {
		this.max_buyer_price = max_buyer_price;
	}

	public String getMin_seller_price() {
		return min_seller_price;
	}

	public void setMin_seller_price(String min_seller_price) {
		this.min_seller_price = min_seller_price;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getLeadingtime() {
		return leadingtime;
	}

	public void setLeadingtime(String leadingtime) {
		this.leadingtime = leadingtime;
	}

	public String getMax_seller_price() {
		return max_seller_price;
	}

	public void setMax_seller_price(String max_seller_price) {
		this.max_seller_price = max_seller_price;
	}

}
