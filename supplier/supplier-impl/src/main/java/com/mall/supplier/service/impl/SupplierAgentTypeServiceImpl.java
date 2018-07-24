package com.mall.supplier.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.supplier.dao.SupplierAgentTypeMapper;
import com.mall.supplier.model.SupplierAgentType;
import com.mall.supplier.model.SupplierAgentTypeExample;
import com.mall.supplier.service.SupplierAgentTypeService;
@Service
@Transactional
public class SupplierAgentTypeServiceImpl implements SupplierAgentTypeService {

	@Autowired
	private SupplierAgentTypeMapper supplierAgentTypeMapper;
	
	@Override
	public List<SupplierAgentType> findAllSupplierAgentType() {
		SupplierAgentTypeExample example = new SupplierAgentTypeExample();
		
		List<SupplierAgentType> list = supplierAgentTypeMapper.selectByExample(example);
		
		return list;
	}
	
	
	@Override
	public int updateSupplierAgentType(SupplierAgentType supplierAgentType) {
		
		int updateByPrimaryKey = supplierAgentTypeMapper.updateByPrimaryKey(supplierAgentType);
		return updateByPrimaryKey;
		
		
	}

    //新增
	@Override
	public int insertSupplierAgentType(SupplierAgentType supplierAgentType) {
		int row = supplierAgentTypeMapper.insert(supplierAgentType);
		return row;
	}


	@Override
	public int deleteByIdSupplierAgentType(Integer id) {
		int row = supplierAgentTypeMapper.deleteByPrimaryKey(id);
		return row;
	}


	@Override
	public SupplierAgentType findChildAgentTypeByCode(Integer code){
//		SupplierAgentTypeExample example=new SupplierAgentTypeExample();
//		SupplierAgentTypeExample.Criteria criteria = example.createCriteria();
//		criteria.andCodeEqualTo(code);
		SupplierAgentType supplierAgentType = supplierAgentTypeMapper.selectByCode(code);
		if(null!=supplierAgentType){
			SupplierAgentType childAgentType = supplierAgentTypeMapper.findChildAgentTypeByPid(supplierAgentType.getId());
			return childAgentType;
		}
		return null;
	}

	@Override
	public SupplierAgentType findAgentByCode(Integer code) {
		return  supplierAgentTypeMapper.selectByCode(code);
	}

	//查询系统运营商分润
	@Override
	public BigDecimal findSOShareBenefit() {
		BigDecimal shareBenefit = supplierAgentTypeMapper.findSOShareBenefit(1);
		return shareBenefit;
	}

	//查询全国运营商分润
	@Override
	public BigDecimal findNOShareBenefit() {
		BigDecimal shareBenefit = supplierAgentTypeMapper.findNOShareBenefit(2);
		return shareBenefit;
	}

	//查询省级运营商分润
	@Override
	public BigDecimal findPOShareBenefit() {
		BigDecimal shareBenefit = supplierAgentTypeMapper.findPOShareBenefit(3);
		return shareBenefit;
	}

	//查询地市级运营商分润
	@Override
	public BigDecimal findCOShareBenefit() {
		BigDecimal shareBenefit = supplierAgentTypeMapper.findCOShareBenefit(4);
		return shareBenefit;
	}

	//查询区县级运营商分润
	@Override
	public BigDecimal findDOShareBenefit() {
		BigDecimal shareBenefit = supplierAgentTypeMapper.findDOShareBenefit(5);
		return shareBenefit;
	}

	//查询市场合伙人分润
	@Override
	public BigDecimal findMPShareBenefit() {
		BigDecimal shareBenefit = supplierAgentTypeMapper.findMPShareBenefit(6);
		return shareBenefit;
	}

	//查询线下商家分润
	@Override
	public BigDecimal findMShareBenefit() {
		BigDecimal shareBenefit = supplierAgentTypeMapper.findMShareBenefit(7);
		return shareBenefit;
	}

	@Override
	public BigDecimal findShareBenefitByType(Integer type) {
		BigDecimal shareBenefit = supplierAgentTypeMapper.findShareBenefitByType(type);
		return shareBenefit;
	}

	@Override
	public SupplierAgentType findByType(Integer type) {
		return supplierAgentTypeMapper.findByType(type);
	}

	@Override
	public SupplierAgentType findByCode(Integer code) {
		return supplierAgentTypeMapper.findByCode(code);
	}


}
