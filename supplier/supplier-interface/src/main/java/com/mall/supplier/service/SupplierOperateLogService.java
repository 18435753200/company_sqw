package com.mall.supplier.service;

import com.mall.supplier.model.SupplierOperateLog;

/**
 *<p>操作log interface</p>
 * @author zw
 * @date 2016年11月29日
 */
public interface SupplierOperateLogService{
	
	
	/**
	 * 
	 * <p>保存操作日志</p>
	 * @param supplierOperateLog
	 * @return
	 * @auth:zw
	 */
	public long insert(SupplierOperateLog supplierOperateLog);
	
}
	
