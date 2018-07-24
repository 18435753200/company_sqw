package com.mall.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.dao.SupplierApplyYhqMapper;
import com.mall.supplier.model.SupplierApplyYhq;
import com.mall.supplier.model.SupplierApplyYhqExample;
import com.mall.supplier.service.SupplierApplyYhqService;

public class SupplierApplyYhqServiceImpl implements SupplierApplyYhqService {

	@Autowired
	private SupplierApplyYhqMapper supplierApplyYhqMapper;
	@Override
	public List<SupplierApplyYhq> findAllSupplierApplyYhq() {
		
		SupplierApplyYhqExample example=new SupplierApplyYhqExample();
		List<SupplierApplyYhq> list = supplierApplyYhqMapper.selectByExample(example);
		return list;
	}
	
	
	@Override
	public PageBean<SupplierApplyYhq> getYhqPageList(PageBean<SupplierApplyYhq> pageBean) {
		List<SupplierApplyYhq> list = supplierApplyYhqMapper.getYhqPageList(pageBean);
		pageBean.setResult(list);
		return pageBean;
	}


	@Override
	public int checkPass(SupplierApplyYhq supplierApplyYhq) {
		SupplierApplyYhqExample example=new SupplierApplyYhqExample();
		int row = supplierApplyYhqMapper.updateByExampleSelective(supplierApplyYhq, example);
		return row;
	}


	@Override
	public int checkNoPass(SupplierApplyYhq supplierApplyYhq) {
		SupplierApplyYhqExample example=new SupplierApplyYhqExample();
		int row = supplierApplyYhqMapper.updateByExampleSelective(supplierApplyYhq, example);
		return row;
	}


	@Override
	public int applyYhq(SupplierApplyYhq supplierApplyYhq) {
		int row = supplierApplyYhqMapper.insertSelective(supplierApplyYhq);
		return row;
	}


	

}
