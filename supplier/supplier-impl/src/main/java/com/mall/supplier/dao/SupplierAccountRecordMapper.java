package com.mall.supplier.dao;

import com.mall.supplier.model.SupplierAccountRecord;

public interface SupplierAccountRecordMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(SupplierAccountRecord record);

    int insertSelective(SupplierAccountRecord record);

    SupplierAccountRecord selectByPrimaryKey(Long recordId);

    int updateByPrimaryKeySelective(SupplierAccountRecord record);

    int updateByPrimaryKey(SupplierAccountRecord record);
}