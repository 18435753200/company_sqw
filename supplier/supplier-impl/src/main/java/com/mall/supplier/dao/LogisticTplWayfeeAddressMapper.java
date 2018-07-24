package com.mall.supplier.dao;

import com.mall.supplier.model.LogisticTplWayfeeAddress;

public interface LogisticTplWayfeeAddressMapper {
	
	int insert(LogisticTplWayfeeAddress record); 
	
	LogisticTplWayfeeAddress selectLogisticTplWayfeeAddressById(Long id);
}
