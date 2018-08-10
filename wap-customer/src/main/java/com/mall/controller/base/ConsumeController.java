package com.mall.controller.base;

import com.mall.customer.dto.ConsumeParamDto;
import com.mall.customer.service.ConsumeService;
import com.mall.supplier.model.SupplierSalesDiscount;
import com.mall.supplier.service.SupplierDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 计算
 */
@Controller
@RequestMapping("/consume")
public class ConsumeController {

    @Autowired
    private ConsumeService consumeService;

    @Autowired
    private SupplierDiscountService supplierDiscountService;

    /**
     * 全现金支付 返回代金券
     * @param price
     * @param supplierId
     * @return
     */
    @RequestMapping(value = "/allCashPay")
    @ResponseBody
    public Map<String,BigDecimal> addCashPayUserAddDjq(BigDecimal price,Long supplierId){
        SupplierSalesDiscount salesDiscount = supplierDiscountService.findBySupplierId(supplierId);
        BigDecimal discount = salesDiscount.getSalesDiscount();
        Map<String,BigDecimal> map = new HashMap<String, BigDecimal>();
        map.put("djq",consumeService.addCashPayUserAddDjq(price,discount));
        return map;
    }

    /**
     * 现金+券支付 应付现金和代金券
     * @param supplierId
     * @param price
     * @return
     */
    @RequestMapping(value = "/mixPay")
    @ResponseBody
    public Map<String,BigDecimal> getAddCashPay(Long supplierId,BigDecimal price){
        ConsumeParamDto dto = consumeService.getAddCashPay(supplierId, price);
        Map<String,BigDecimal> map = new HashMap<String, BigDecimal>();
        map.put("cash",dto.getCash());
        map.put("djq",dto.getDjq());
        return map;
    }

}
