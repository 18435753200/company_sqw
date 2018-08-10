package com.mall.dsearch.vo;

import java.io.Serializable;

public class SearchSupplierDetailResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2532794147142038045L;

	
	private Supplier supplier;


	public Supplier getSupplier() {
		return supplier;
	}


	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}


	@Override
	public String toString() {
		return "SearchSupplierDetailResponse [supplier=" + supplier + "]";
	}
	
	


	
	
	
}
