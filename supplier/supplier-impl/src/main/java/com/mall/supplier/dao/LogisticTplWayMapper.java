package com.mall.supplier.dao;

import com.mall.supplier.model.LogisticTplWay;

public interface LogisticTplWayMapper {
 
	int insert(LogisticTplWay record);
	
	LogisticTplWay selectLogisticTplWayByTId(Long tplId);
}
