package com.mall.supplier.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.supplier.dao.SupplierOperateLogMapper;
import com.mall.supplier.model.SupplierOperateLog;
import com.mall.supplier.service.SupplierOperateLogService;

@Service
@Transactional
public class SupplierOperateLogServiceImpl implements SupplierOperateLogService {

	Logger logger = Logger.getLogger(SupplierOperateLogServiceImpl.class);

	@Autowired
	private SupplierOperateLogMapper supplierOperateLogMapper;

	public long insert(SupplierOperateLog supplierOperateLog) {
		long result = supplierOperateLogMapper.insert(supplierOperateLog);
		logger.info("保存操作日志 结束"); 
		return result;
	}

}
