package com.mall.supplier.dao;

import org.springframework.stereotype.Repository;

import com.mall.supplier.model.SupplierAccount;

public interface SupplierAccountMapper {
	
    int deleteByPrimaryKey(Long accountId);

    int insert(SupplierAccount record);

    int insertSelective(SupplierAccount record);

    SupplierAccount selectByPrimaryKey(Long accountId);

    int updateByPrimaryKeySelective(SupplierAccount record);

    int updateByPrimaryKey(SupplierAccount record);
}