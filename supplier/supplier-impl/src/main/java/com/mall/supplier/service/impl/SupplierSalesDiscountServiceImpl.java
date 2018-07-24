package com.mall.supplier.service.impl;

import java.math.BigDecimal;

import javax.annotation.processing.SupportedOptions;

import org.springframework.beans.factory.annotation.Autowired;

import com.mall.supplier.dao.SupplierSalesDiscountMapper;
import com.mall.supplier.model.SupplierSalesDiscount;
import com.mall.supplier.service.SupplierSalesDiscountService;

public class SupplierSalesDiscountServiceImpl implements SupplierSalesDiscountService{

	@Autowired
	SupplierSalesDiscountMapper supplierSalesDiscountMapper;
	@Override
	public long insert(SupplierSalesDiscount supplierSalesDiscount) {
		supplierSalesDiscountMapper.insert(supplierSalesDiscount);
		return 0;
	}
	@Override
	public SupplierSalesDiscount getSalesDiscountBySupplicerId(Long supplierId) {
		// TODO Auto-generated method stub
		return supplierSalesDiscountMapper.selectSalesDiscountBySupplicerId(supplierId);
	}
	@Override
	public int updateSalesDiscountBySupplier(Long supplierId,
			BigDecimal salesDiscount) {
		// TODO Auto-generated method stub
		return supplierSalesDiscountMapper.updateSalesDiscountBySupplier(supplierId,salesDiscount);
	}

}
