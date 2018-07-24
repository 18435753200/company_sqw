package com.mall.supplier;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mall.supplier.model.LogisticTplWay;
import com.mall.supplier.model.LogisticTplWayfee;
import com.mall.supplier.model.LogisticTplWayfeeAddress;
import com.mall.supplier.model.LogisticTplWaynonefee;
import com.mall.supplier.model.LogisticTplWaynonefeeAddress;
import com.mall.supplier.model.OrderItemLogistics;
import com.mall.supplier.model.OrderLogisticsFee;
import com.mall.supplier.model.ProductSalesArea;
import com.mall.supplier.model.SuplierAreaTpl;
import com.mall.supplier.model.SupplierLogisticTpl;
import com.mall.supplier.model.SupplierProductArea;
import com.mall.supplier.model.SupplierProductLogistic;
import com.mall.supplier.service.SupplierLogisticService;
import com.mall.supplier.service.SupplierRoleManagerService;
import com.mall.supplier.service.SupplierStoreService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class SupplierProductAreaTest {
	@Autowired
	private SupplierRoleManagerService supplierRoleManagerService;
	@Autowired
	private SupplierStoreService supplierStoreService;
	@Autowired
	private SupplierLogisticService supplierLogisticService;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void supplierProductArea(){
		SupplierProductArea record = new SupplierProductArea();
		record.setProductId(8L);
		record.setSupplierId(9L);
		record.setAreaTplId(6L);
		record.setCreateDateTime(new Date());
		int area = supplierLogisticService.saveSupplierProductArea(record);
		
		System.err.print("%%%%%%%%%%%%%%%%"+area+"@@@@@@@@@@@@@@@@@");
		
	}
      @Test
	public void findSupplierareaByProductIdAndSupplierId(){
		Long supplierId=3L;
		Long productId=2L;
		SupplierProductArea area = supplierLogisticService.findSupplierAreaTmplByProductIdAndSupplierId(productId, supplierId);
		if (null!=area && null!=area.getAreaTplId()) {
			SuplierAreaTpl areaTpl = supplierLogisticService.findSupplierAreaTmplByTmplId(area.getAreaTplId());
			System.err.print("%%%%%%%%%%%%%%%%"+areaTpl.getType()+"@@@@@@@@@@@@@@@@@");
		}
		System.err.print("%%%%%%%%%%%%%%%%"+area+"@@@@@@@@@@@@@@@@@");
	
	}
      @Test
     public void savef1(){
    	 SupplierProductLogistic record=new SupplierProductLogistic();
    	 record.setCreateDatetime(new Date());
    	 record.setLogisticTplId(3L);
    	 record.setSupplierId(4L);
    	 record.setProductLogisticType(1L);
		int q = supplierLogisticService.saveSupplierProductLogistic(record);
		System.out.println(q);
     } 
      @Test
      public void find1(){
    	  SupplierProductLogistic product= supplierLogisticService.findSupplierProductLogisticByPidAndSupplierId(3L, 4L);
    	  System.out.println(product);
      
      }
      @Test
      public void saveSupplierLogisticTpl(){
    	  
    	  SupplierLogisticTpl record=new SupplierLogisticTpl();
    	  record.setCreateTime(new Date());
    	  record.setSupplierId(5L);
    	  record.setProductProvinceid(10L);
    	  record.setProductCityid(6L);
    	  record.setProductAreaid(8l);
		int saveSupplie = supplierLogisticService.saveSupplierLogisticTpl(record);
      System.out.println(saveSupplie);
      }
   
      @Test
      public void findSupplierLogisticTpl(){
    	  SupplierLogisticTpl findSupp = supplierLogisticService.findSupplierLogisticTplById(5L);
    	  System.err.println(findSupp);
      }
      @Test
      public void saveLogisticTplWayfeeAddress(){
    	  LogisticTplWayfeeAddress record=new LogisticTplWayfeeAddress();
    	  record.setCreateDatetime(new Date());
    	  record.setLogisticTplWayfeeId(2L);
		int wayfeeAddress = supplierLogisticService.saveLogisticTplWayfeeAddress(record);
		System.err.println(wayfeeAddress);
      }
      @Test
      public void findLogisticTplWayfeeAddress(){
    	  LogisticTplWayfeeAddress tplWayfeeAddress = supplierLogisticService.findLogisticTplWayfeeAddressByWaynonefeeId(2L);
    	  System.err.println(tplWayfeeAddress);
      }
      @Test
      public void saveLogisticTplWayfee(){
    	  
    	  LogisticTplWayfee record=new LogisticTplWayfee();
    	  record.setCreateDatetime(new Date());
    	  record.setLogisticWayId(8L);
		int tplWayfee = supplierLogisticService.saveLogisticTplWayfee(record);
		 System.err.println(tplWayfee);
      }
      
      @Test
      public void findLogisticTplWayfee(){
    	  List<LogisticTplWayfee> logisticTplWayfee = supplierLogisticService.findLogisticTplWayfeeByLogisticWayId(8L);
    	  System.err.println(logisticTplWayfee.size());
      }
      
      @Test
      public void saveLogisticTplWay(){
    	  LogisticTplWay record=new LogisticTplWay();
    	  record.setCreateDatetime(new Date());
    	  record.setLogisticTplId(9L);
    	  record.setLogisticWayName("lisi");
		int LogisticTplWay = supplierLogisticService.saveLogisticTplWay(record);
		 System.err.println(LogisticTplWay);
      }
      
      @Test
      public void findLogisticTplWay(){
    	  
    	  LogisticTplWay logisticTplWayById = supplierLogisticService.findLogisticTplWayById(9L);
    	  
    	  System.err.println(logisticTplWayById);
      }
      @Test
      public void saveLogisticTplWaynonefeeAddress(){
    	  
    	  LogisticTplWaynonefeeAddress record=new LogisticTplWaynonefeeAddress();
    	  record.setCreateDatetime(new Date());
    	  record.setLogisticTplWaynonefeeId(10L);
    	  record.setState(3);
		int waynonefeeAddress = supplierLogisticService.saveLogisticTplWaynonefeeAddress(record);
    	  
		System.err.println(waynonefeeAddress);
      }
      @Test
      public void findLogisticTplWaynonefeeAddress(){
   LogisticTplWaynonefeeAddress waynonefeeId = supplierLogisticService.findLogisticTplWaynonefeeAddressByWaynonefeeId(10L);
    	  System.err.println(waynonefeeId);
      }
      
      @Test
      public void saveLogisticTplWaynonefee(){
    	  LogisticTplWaynonefee record=new LogisticTplWaynonefee();
    	  record.setLogisticWayId(110L);
    	  record.setCreateDatetime(new Date());
		int tplWaynonefee = supplierLogisticService.saveLogisticTplWaynonefee(record);
		System.err.println(tplWaynonefee);
      }
      
      @Test
      public void findLogisticTplWaynonefee(){
    	  
    	  LogisticTplWaynonefee tplWaynonef = supplierLogisticService.findLogisticTplWaynonefeeByWaynonefeeId(110L);
    	  System.err.println(tplWaynonef);
      }
      
      @Test
      public void findLogisticTplWaynonefee1(){
    	Long provinceId = (long) 8;
		Long cityId = (long) 279;
		Long areaId = (long) 540;
		Long productId =  10504582936565310L;
		Long supplierId = (long) 2;
		ProductSalesArea productSalesArea = supplierLogisticService.queryAddressWhetherInSalesArea(provinceId, cityId, areaId, productId, supplierId);
    	  System.err.println(productSalesArea);
      }
      
      @Test
      public void findLogisticTplWaynonefee12(){
    	
    	Long provinceId = (long) 8;
		Long cityId = (long) 279;
		Long areaId = (long) 540;
		Long productId =  10237518080313716L;
		Long supplierId = (long) 2;
		ProductSalesArea productSalesArea = supplierLogisticService.getProductDefaultSalesArea3(productId, supplierId);
    	  System.err.println(productSalesArea);
      }
      
      @Test
      public void  findProductSupplierAreaAndReceive(){
    	  Map<Long, List<Long>> map=	new HashMap<Long, List<Long>>();
    	  ArrayList<Long> productidList = new ArrayList<Long>();
    	  productidList.add(10504582936565310L);
    	  productidList.add(10504913549008833L);
    	  productidList.add(10237518080313716L);
    	  map.put(2L, productidList);
    	  Long provinceId = (long) 8;
  		  Long cityId = (long) 279;
  		  Long areaId = (long) 540;
    	  //int findProductSupplierAreaAndReceive = supplierLogisticService.findProductSupplierAreaAndReceive(provinceId, cityId, areaId, map);
    //System.err.println(findProductSupplierAreaAndReceive);
      }
      @Test
      public void getCustomerOrderPrice(){
    	  
    	  OrderLogisticsFee orderLogisticsFee = new OrderLogisticsFee();
    	  OrderItemLogistics orderItemLogistics = new OrderItemLogistics();
    	  Long productId = 10504582936565310L;
		orderItemLogistics.setProductId(productId);
		orderItemLogistics.setSupplierId((long) 2);
		orderItemLogistics.setQuantity(new BigDecimal("4"));
		orderItemLogistics.setSupplierId(2L);
		orderItemLogistics.setPrice(new BigDecimal("5"));
    	  Long orderId = null;
		orderLogisticsFee.setOrderId(orderId);
		List<OrderItemLogistics> orderItems = new ArrayList<OrderItemLogistics>();
		orderItems.add(orderItemLogistics);
		orderLogisticsFee.setOrderItems(orderItems);
		BigDecimal customerOrderPrice = supplierLogisticService.getCustomerOrderLogisticPrice(orderLogisticsFee,(long) 12, null, null);
		System.err.println(customerOrderPrice);
      }
     
      
}
