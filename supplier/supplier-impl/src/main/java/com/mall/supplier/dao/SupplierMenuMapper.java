package com.mall.supplier.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierMenu;


public interface SupplierMenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(SupplierMenu record);

    int insertSelective(SupplierMenu record);

    SupplierMenu selectByPrimaryKey(Long menuId);
    
    List<SupplierMenu> findMenusByUserId (@Param("userId")Long userId);

    int updateByPrimaryKeySelective(SupplierMenu record);

    int updateByPrimaryKey(SupplierMenu record);
    
    List<SupplierMenu> findMenusByRoleId(Long menuId);
    
    List<SupplierMenu> findAll();

	List<SupplierMenu> findAllMenuByMenuOwner(Integer type);
    
}