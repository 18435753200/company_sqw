package com.mall.supplier.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierUserRole;

public interface SupplierUserRoleMapper {
    int deleteByPrimaryKey(Long linkId);

    int insert(SupplierUserRole record);

    int insertSelective(SupplierUserRole record);

    SupplierUserRole selectByPrimaryKey(Long linkId);

    int updateByPrimaryKeySelective(SupplierUserRole record);

    int updateByPrimaryKey(SupplierUserRole record);
    
    int deleteAll(List<Long> list);
    
    int deleteAllByUserId(List<Long> list);
    
	int updateByUserId(SupplierUserRole userRole);

	int  deleteByUserId(@Param("userId")Long userId);
		
	int countUserByRoleId(@Param("roleId")Long roleId);
    
}