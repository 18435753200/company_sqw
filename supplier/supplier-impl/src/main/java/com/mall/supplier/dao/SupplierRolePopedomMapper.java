package com.mall.supplier.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mall.supplier.model.SupplierRolePopedom;

public interface SupplierRolePopedomMapper {
    int deleteByPrimaryKey(Long linkId);

    int insert(SupplierRolePopedom record);

    int insertSelective(SupplierRolePopedom record);

    SupplierRolePopedom selectByPrimaryKey(Long linkId);

    int updateByPrimaryKeySelective(SupplierRolePopedom record);

    int updateByPrimaryKey(SupplierRolePopedom record);
    
    int deleteByRolePopedomId(@Param(value="roleId") long roleId);
    
    int insertRolePopedom(List<SupplierRolePopedom> list);
}