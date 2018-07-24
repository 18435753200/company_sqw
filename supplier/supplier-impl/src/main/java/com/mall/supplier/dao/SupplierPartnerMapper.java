package com.mall.supplier.dao;

import com.mall.supplier.model.SupplierPartner;

public interface SupplierPartnerMapper {
    int deleteByPrimaryKey(Long partnerId);

    int insert(SupplierPartner record);

    int insertSelective(SupplierPartner record);

    SupplierPartner selectByPrimaryKey(Long partnerId);

    int updateByPrimaryKeySelective(SupplierPartner record);

    int updateByPrimaryKey(SupplierPartner record);
}