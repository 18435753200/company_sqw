package com.mall.supplier.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.dao.SGeneralLogisticTplMapper;
import com.mall.supplier.model.SGeneralLogisticTpl;
import com.mall.supplier.model.SGeneralLogisticTplExample;
import com.mall.supplier.model.SGeneralLogisticTplExample.Criteria;
import com.mall.supplier.service.GeneralLogisTplService;
@Service
@Transactional
public class GeneralLogisticTplServiceImpl implements GeneralLogisTplService {
	@Autowired
	private SGeneralLogisticTplMapper generalLogisticTplMapper;
	@Override
	public void addGeneralLogisTpl(SGeneralLogisticTpl sGeneralLogisticTpl) {
		generalLogisticTplMapper.insert(sGeneralLogisticTpl);

	}
	@Override
	public List<SGeneralLogisticTpl> findAllGeneralLogisTpl(PageBean<SGeneralLogisticTpl> pageBean) {
		// TODO Auto-generated method stub
		return generalLogisticTplMapper.selectAllTpl(pageBean);
	}
	@Override
	public void deleteGeneralLogisTpl(Long logisticTempId) {
		// TODO Auto-generated method stub
		generalLogisticTplMapper.deleteByPrimaryKey(logisticTempId);
	}
	@Override
	public void updateGeneralLogisTpl(SGeneralLogisticTpl sGeneralLogisticTpl) {
		// TODO Auto-generated method stub
		Long logisticTempId = sGeneralLogisticTpl.getLogisticTempId();
		generalLogisticTplMapper.deleteByPrimaryKey(logisticTempId);
		Long supplierId = sGeneralLogisticTpl.getSupplierId();
		if (!supplierId.equals(null) && !supplierId.equals("")) {
			sGeneralLogisticTpl.setTplType(2);
		}else {
			sGeneralLogisticTpl.setTplType(1);
		}
		String nonefeeProvinceId = sGeneralLogisticTpl.getNonefeeProvinceId();
		if (nonefeeProvinceId!=null&&nonefeeProvinceId!="") {
			String[] nonefeeProvinceIds = nonefeeProvinceId.split(",,");
			 for (int i = 0; i < nonefeeProvinceIds.length; i++) {
				 nonefeeProvinceId = nonefeeProvinceIds[nonefeeProvinceIds.length-1];
			}
			 sGeneralLogisticTpl.setNonefeeProvinceId(nonefeeProvinceId);
		}
		sGeneralLogisticTpl.setStatus(1);
		sGeneralLogisticTpl.setSupplierId(supplierId);
		generalLogisticTplMapper.insert(sGeneralLogisticTpl);
	}
	@Override
	public SGeneralLogisticTpl findGeneralLogisticTplById(Long logisticTempId) {
		// TODO Auto-generated method stub
		return generalLogisticTplMapper.selectByPrimaryKey(logisticTempId);
	}
	@Override
	public List<SGeneralLogisticTpl> findGeneralLogisTplByAjax(Long supplierId) {
		SGeneralLogisticTplExample example = new SGeneralLogisticTplExample();
		Criteria criteria = example.createCriteria();
		criteria.andSupplierIdEqualTo(supplierId);
		return generalLogisticTplMapper.selectByExample(example);
	}
	@Override
	public PageBean<SGeneralLogisticTpl> findAllGeneralLogisTpl(
			SGeneralLogisticTpl sg, Integer page, Integer pageSize) {
		// TODO Auto-generated method stub
		PageBean<SGeneralLogisticTpl> pageBean = new PageBean<SGeneralLogisticTpl>();
		pageBean.setPage(page);
		pageBean.setPageSize(pageSize);
	    pageBean.setParameter(sg);
	    /*pageBean.setSortFields("id");
		pageBean.setOrder("desc");*/
	    List<SGeneralLogisticTpl> list = generalLogisticTplMapper.selectAllTplPageList(pageBean);
	    pageBean.setResult(list);
		return pageBean;
	}

}
