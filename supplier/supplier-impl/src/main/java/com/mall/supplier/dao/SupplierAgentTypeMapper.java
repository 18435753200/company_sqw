package com.mall.supplier.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierAgentType;
import com.mall.supplier.model.SupplierAgentTypeExample;

public interface SupplierAgentTypeMapper {
    int countByExample(SupplierAgentTypeExample example);

    int deleteByExample(SupplierAgentTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SupplierAgentType record);

    int insertSelective(SupplierAgentType record);

    List<SupplierAgentType> selectByExample(SupplierAgentTypeExample example);

    SupplierAgentType selectByPrimaryKey(Integer id);

    SupplierAgentType selectByCode(Integer code);

    int updateByExampleSelective(@Param("record") SupplierAgentType record, @Param("example") SupplierAgentTypeExample example);

    int updateByExample(@Param("record") SupplierAgentType record, @Param("example") SupplierAgentTypeExample example);

    int updateByPrimaryKeySelective(SupplierAgentType record);

    int updateByPrimaryKey(SupplierAgentType record);

    /**
     * 根据pid查询子代理类型业务
     * @param pId 上级id
     * @return
     */
    SupplierAgentType findChildAgentTypeByPid(Integer id);
    
    
     //查询系统运营商分润
    BigDecimal findSOShareBenefit(Integer type);
    
    BigDecimal findNOShareBenefit(Integer type);
    
    BigDecimal findPOShareBenefit(Integer type);
   
    BigDecimal findCOShareBenefit(Integer type);

    BigDecimal findDOShareBenefit(Integer type);
    
    BigDecimal findMPShareBenefit(Integer type);
    
    BigDecimal findMShareBenefit(Integer type);

    /**
     * 根据商家代理类型查找对应分润比例
     * @param type 代理类型查
     * @return
     */
    BigDecimal findShareBenefitByType(Integer type);

    SupplierAgentType findByType(Integer type);

    SupplierAgentType findByCode(Integer code);
    
}