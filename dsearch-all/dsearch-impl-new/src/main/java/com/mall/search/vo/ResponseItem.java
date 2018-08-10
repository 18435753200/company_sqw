package com.mall.search.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mall.dsearch.vo.PlainProduct;

public class ResponseItem implements Serializable {

	private static final long serialVersionUID = 8704173071186146677L;
	
	private List<PlainProduct> productItemList;
	private Map<String, String> productCountrys;
	public List<PlainProduct> getProductItemList() {
		return productItemList;
	}
	public void setProductItemList(List<PlainProduct> productItemList) {
		this.productItemList = productItemList;
	}
	public Map<String, String> getProductCountrys() {
		return productCountrys;
	}
	public void setProductCountrys(Map<String, String> productCountrys) {
		this.productCountrys = productCountrys;
	}
	
}
