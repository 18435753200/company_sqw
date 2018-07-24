package com.mall.supplier;

import com.mall.supplier.model.SupplierStore;
import com.mall.supplier.service.SupplierStoreService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhengcunwen on 2016/1/31.
 * 供应商店铺接口test类
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-test.xml")
public class SupplierStoreTest {

    @Autowired
    private SupplierStoreService supplierStoreService;

    @Test
    public void saveStore(){
        SupplierStore store = new SupplierStore();
        store.setCreateBy(1);
        store.setStoreName("test的店铺");
        store.setSupplierId(123);
        int pk = supplierStoreService.insertStore(store);

        System.out.println(pk);
    }

    @Test
    public void preview(){
        //supplierStoreService.updateSupplierStorePreview(123,"11111");
    }

    @Test
    public void publish(){
        //supplierStoreService.publishStoreTemplateData(123,"etweeww",23);
    }


    @Test
    public void findSupplierStoreBySupplierId(){
        SupplierStore store = supplierStoreService.findSupplierStoreBySupplierId(3);

        System.out.println(store == null?"没值":store.getStoreName());
    }

    @Test
    public void updateStoreTemplate(){
        supplierStoreService.updateStoreTemplate(123,222);
    }

}
