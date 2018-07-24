package com.mall.supplier.dao;

import java.util.List;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.SupplierLogisticTpl;

public interface SupplierLogisticTplMapper {
	
	int insert(SupplierLogisticTpl record);
	
	SupplierLogisticTpl	selectSupplierLogisticTplById(Long tplId);
	
	List<SupplierLogisticTpl> selectAllTpl(PageBean<SupplierLogisticTpl> pageBean);
	
	SupplierLogisticTpl	findLogisticTplByName(String logisticTplName);
	
	
}
