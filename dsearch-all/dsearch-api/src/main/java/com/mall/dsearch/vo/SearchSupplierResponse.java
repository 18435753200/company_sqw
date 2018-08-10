package com.mall.dsearch.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 返回结果
 * 
 * @author DOUBLELL
 *
 */
public class SearchSupplierResponse implements Serializable {

	private static final long serialVersionUID = -7578544787952401329L;

	/** 命中总数 **/
	private int hits;

	/** 能取到（翻页）的总数 **/
	private int numFetch;

	/** 本页包含的结果集 **/
	private List<Supplier> items;

	/** 企业所属类目 */
	private List<String> companyBizCategorys;

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getNumFetch() {
		return numFetch;
	}

	public void setNumFetch(int numFetch) {
		this.numFetch = numFetch;
	}

	public List<Supplier> getItems() {
		return items;
	}

	public void setItems(List<Supplier> items) {
		this.items = items;
	}

	public List<String> getCompanyBizCategorys() {
		return companyBizCategorys;
	}

	public void setCompanyBizCategorys(List<String> companyBizCategorys) {
		this.companyBizCategorys = companyBizCategorys;
	}
	
	

	
}