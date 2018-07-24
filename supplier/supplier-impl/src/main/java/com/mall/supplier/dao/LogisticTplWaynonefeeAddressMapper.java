package com.mall.supplier.dao;

import com.mall.supplier.model.LogisticTplWaynonefeeAddress;
import com.mall.supplier.model.SupplierProductLogistic;

public interface LogisticTplWaynonefeeAddressMapper {
	
	int insert(LogisticTplWaynonefeeAddress record);
	
	LogisticTplWaynonefeeAddress selectLogisticTplWaynonefeeAddressById(Long id);
}
