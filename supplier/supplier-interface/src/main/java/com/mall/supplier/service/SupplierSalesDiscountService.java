package com.mall.supplier.service;

import java.math.BigDecimal;

import com.mall.supplier.model.SupplierSalesDiscount;

public interface SupplierSalesDiscountService {


	public long insert(SupplierSalesDiscount supplierSalesDiscount);

	public SupplierSalesDiscount getSalesDiscountBySupplicerId(Long supplierId);

	public int updateSalesDiscountBySupplier(Long supplierId,
			BigDecimal salesDiscount);
}
