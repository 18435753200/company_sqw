package com.mall.supplier.service.impl;

import com.mall.supplier.annotation.ArgsLog;
import com.mall.supplier.dao.SupplierTypeSettingsMapper;
import com.mall.supplier.enums.SupplierType;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierTypeSettings;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.supplier.service.SupplierTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Zhutaoshen on 2016/12/26 0026.
 */
@Service
@Transactional
public class SupplierTypeServiceImpl implements SupplierTypeService {

    @Autowired
    private SupplierTypeSettingsMapper settingsMapper;

    @Autowired
    private SupplierManagerService supplierManagerService;


    @Override
    @ArgsLog
    public BigDecimal getProportionRepayment(int supplierType) {
        SupplierTypeSettings settings=settingsMapper.selectByPrimaryKey(supplierType);
        if(settings==null){
            settings = new SupplierTypeSettings();
            settings.setSupplierType(supplierType);
            settings.setProportionRepayment(BigDecimal.ONE);
            settingsMapper.insertSelective(settings);
        }
        return settings.getProportionRepayment();
    }

    @Override
    @ArgsLog
    public int setProportionRepayment(int supplierType, BigDecimal proportionRepayment) {
        SupplierTypeSettings settings = new SupplierTypeSettings();
        settings.setSupplierType(supplierType);
        settings.setProportionRepayment(proportionRepayment);
        settings.setOperateDate(new Date());
        settingsMapper.updateByPrimaryKeySelective(settings);
        return 0;
    }

    @Override
    @ArgsLog
    public BigDecimal getProportionRepaymentByID(Long supplierID) {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(supplierID);
        supplier = supplierManagerService.getSupplier(supplier);
        return this.getProportionRepayment(supplier.getType());
    }
}
