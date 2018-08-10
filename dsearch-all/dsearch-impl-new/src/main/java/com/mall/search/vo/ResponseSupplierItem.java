package com.mall.search.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.mall.dsearch.vo.Supplier;

public class ResponseSupplierItem implements Serializable {

	private static final long serialVersionUID = 8704173071186146677L;
	
	private List<Supplier> supplierItemList;
	private List<String> companyBizCategorys;
	
	
	public List<Supplier> getSupplierItemList() {
		return supplierItemList;
	}
	public void setSupplierItemList(List<Supplier> supplierItemList) {
		this.supplierItemList = supplierItemList;
	}
	public List<String> getCompanyBizCategorys() {
		return companyBizCategorys;
	}
	public void setCompanyBizCategorys(List<String> companyBizCategorys) {
		this.companyBizCategorys = companyBizCategorys;
	}
	
	
	
	
}
