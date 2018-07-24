package com.mall.supplier.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierRole;

public interface SupplierRoleMapper {
	
    int deleteByPrimaryKey(Long roleId);

    int insert(SupplierRole record);

    int insertSelective(SupplierRole record);

    SupplierRole selectByPrimaryKey(Long roleId);
    
    List<SupplierRole> selectRolesBySupplierId(@Param("supplierId")Long supplierId);

    int updateByPrimaryKeySelective(SupplierRole record);

    int updateByPrimaryKey(SupplierRole record);
    
    int countRoleByName(@Param("name")String name);
    
    int countRoleByNameAndMerchId(@Param("name")String name,@Param("supplierId")Long supplierId);

	List<SupplierRole> selectRolesBySupplierIds(List<Long> sids);
}