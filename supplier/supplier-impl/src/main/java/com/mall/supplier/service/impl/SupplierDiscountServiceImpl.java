package com.mall.supplier.service.impl;

import com.mall.supplier.dao.SupplierSalesDiscountMapper;
import com.mall.supplier.model.SupplierSalesDiscount;
import com.mall.supplier.service.SupplierDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Transactional
public class SupplierDiscountServiceImpl implements SupplierDiscountService {

    @Autowired
    private SupplierSalesDiscountMapper supplierSalesDiscountMapper;

    /**
     * 设置 商家折扣
     * @param supplierSalesDiscount 商家折扣表
     * @return
     */
    public int setSalesDiscount(SupplierSalesDiscount supplierSalesDiscount){
        //先删除
        supplierSalesDiscountMapper.deleteBySupplierId(supplierSalesDiscount.getSupplierId());
        //再添加
        return supplierSalesDiscountMapper.insertSelective(supplierSalesDiscount);
    }

    /**
     * 查找 商家折扣
     * @param supplierSalesDiscount 商家折扣表
     * @return
     */
    public SupplierSalesDiscount findSupplierSalesDiscount(SupplierSalesDiscount supplierSalesDiscount){
        return supplierSalesDiscountMapper.findSupplierSalesDiscount(supplierSalesDiscount);
    }

    @Override
    public int setSalesDiscount(Long supplierId, BigDecimal salesDiscount, String discountName, Date beginDatetime, Date endDatetime, String discountRemark, Integer bizType) {
        SupplierSalesDiscount supplierSalesDiscount = new SupplierSalesDiscount();
        supplierSalesDiscount.setSupplierId(supplierId);
        supplierSalesDiscount.setSalesDiscount(salesDiscount);
        supplierSalesDiscount.setDiscountName(discountName);
        supplierSalesDiscount.setBeginDatetime(beginDatetime);
        supplierSalesDiscount.setEndDatetime(endDatetime);
        supplierSalesDiscount.setDiscountRemark(discountRemark);
        supplierSalesDiscount.setStatus(1);
        supplierSalesDiscount.setBizType(bizType);
        return this.setSalesDiscount(supplierSalesDiscount);
    }

    @Override
    public int setSalesDiscount(Long supplierId, BigDecimal salesDiscount) {
        SupplierSalesDiscount supplierSalesDiscount = new SupplierSalesDiscount();
        supplierSalesDiscount.setSupplierId(supplierId);
        supplierSalesDiscount.setSalesDiscount(salesDiscount);
        supplierSalesDiscount.setStatus(1);
        return this.setSalesDiscount(supplierSalesDiscount);
    }

    @Override
    public SupplierSalesDiscount findBySupplierId(Long supplierId) {
        SupplierSalesDiscount supplierSalesDiscount = new SupplierSalesDiscount();
        supplierSalesDiscount.setSupplierId(supplierId);
        supplierSalesDiscount.setStatus(1);
        return this.findSupplierSalesDiscount(supplierSalesDiscount);
    }


}
