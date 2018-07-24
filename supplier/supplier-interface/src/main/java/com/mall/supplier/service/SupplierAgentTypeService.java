package com.mall.supplier.service;

import java.math.BigDecimal;
import java.util.List;


import com.mall.supplier.model.SupplierAgentType;

public interface SupplierAgentTypeService {

	List<SupplierAgentType> findAllSupplierAgentType();

	int  updateSupplierAgentType(SupplierAgentType supplierAgentType);

	int insertSupplierAgentType(SupplierAgentType supplierAgentType);

	int deleteByIdSupplierAgentType(Integer id);
   

	/**
	 * 根据code查询其子代理类型业务
	 * @param code 编码
	 * @return
	 */
	public SupplierAgentType findChildAgentTypeByCode(Integer code);

	/**
	 * 根据code查询自身业务类型
	 */
	public SupplierAgentType findAgentByCode(Integer code);
	
	//查询系统运营商分润
	BigDecimal findSOShareBenefit();
	//查询全国运营商分润
	BigDecimal findNOShareBenefit();
	//查询省级运营商分润
	BigDecimal findPOShareBenefit();
	//查询地市级运营商分润
	BigDecimal findCOShareBenefit();
	//查询区县级运营商分润
	BigDecimal findDOShareBenefit();
	//查询市场合伙人分润
	BigDecimal findMPShareBenefit();
	//查询线下商家分润
	BigDecimal findMShareBenefit();

	/**
	 * 根据商家代理类型查询分润比例
	 * @param type 代理类型
	 * @return 分润比例
	 */
	BigDecimal findShareBenefitByType(Integer type);

	/**
	 * 根据商家类型查询代理类型业务
	 * @param type 商家类型
	 * @return
	 */
	SupplierAgentType findByType(Integer type);

	SupplierAgentType findByCode(Integer code);

}
