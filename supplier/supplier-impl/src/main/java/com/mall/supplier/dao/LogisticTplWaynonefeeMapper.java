package com.mall.supplier.dao;

import com.mall.supplier.model.LogisticTplWaynonefee;
import com.mall.supplier.model.LogisticTplWaynonefeeAddress;
import com.mall.supplier.model.SupplierProductLogistic;

public interface LogisticTplWaynonefeeMapper {
	
	int insert(LogisticTplWaynonefee record);
	
	LogisticTplWaynonefee selectLogisticTplWaynonefeeByWid(Long id);
}
