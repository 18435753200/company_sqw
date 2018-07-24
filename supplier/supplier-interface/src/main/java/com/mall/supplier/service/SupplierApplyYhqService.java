package com.mall.supplier.service;

import java.util.List;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.SupplierApplyYhq;

public interface SupplierApplyYhqService {

	//查询所有
	List<SupplierApplyYhq> findAllSupplierApplyYhq();
	
	//分页查询审核优惠券
	public PageBean<SupplierApplyYhq> getYhqPageList(PageBean<SupplierApplyYhq> pageBean);

	//审核通过
	int checkPass(SupplierApplyYhq supplierApplyYhq);

	//审核不通过
	int checkNoPass(SupplierApplyYhq supplierApplyYhq);

	//申请优惠券
	

	int applyYhq(SupplierApplyYhq supplierApplyYhq);
	

}
