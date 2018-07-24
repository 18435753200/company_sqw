package com.mall.supplier.dao;

import java.util.List;

import com.mall.supplier.model.LogisticTplWayfee;

public interface LogisticTplWayfeeMapper {
	
	int insert(LogisticTplWayfee record);
	
	List<LogisticTplWayfee> selectLogisticTplWayfeeById(Long id);
}
