package com.mall.supplier;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mall.supplier.dao.SupplierMapper;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.service.SupplierManagerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class SupplierHqqTest {
	
	@Autowired
	private SupplierMapper supplierMapper;
	@Autowired
	private  SupplierManagerService supplierManagerService;
	
	/**
	 * <p>根据企业supplierId获取未支付红旗券</p>
	 * @auth:zw
	 */
    @Test
    public void  getNoHqqBySupplierId(){
    	String supplierId = "1390";
		BigDecimal noPayHqq = supplierManagerService.getNoPayHqq(supplierId );
		System.out.println("返回数据结果："+noPayHqq);
    };
    
    @Test
    public void  updateRemainBalanceAmountBySupplierId(){
    	String supplierId = "1389";
    	BigDecimal bigDecimal = new BigDecimal("5000");
    	int num = supplierManagerService.updateRemainBalanceAmount(supplierId, bigDecimal);
    	//1success
    	System.out.println("返回数据结果："+num);
    };
    
    @Test
    public void  getHqqPwdBySupplierId(){
    	String supplierId = "1390";
    	String hqqPayPwd = supplierManagerService.getHqqPayPwd(supplierId);
    	System.out.println("返回数据结果："+hqqPayPwd);
    };
    
    @Test
    public void  get(){
    	Supplier supplier = new Supplier();
    	supplier.setSupplierId(1389L);
//    	supplier.setSupplierCode("QE0005");
    	Supplier supplier2 = supplierManagerService.getSupplier(supplier);
    	System.out.println(supplier2);
    }
    
    @Test
    public void  updateHqqPwd(){
    	String supplierId = "1388";
    	String hqqPwd = "9A3BDC603C940A18467D167AF15D4C8C";
    	int status = supplierManagerService.updateHqqPwdBySupplierId(supplierId, hqqPwd);
    	System.out.println("返回数据结果："+status);
    }
    @Test
    public void  getSuppliersByCompanyQy(){
    	String companyQy = "4";
    	List<Supplier> list = supplierManagerService.findAllSupplierByCommpanyQy(companyQy);
    	for(Supplier s : list){
    		System.out.println(s.toString());
    	}
    }
}
