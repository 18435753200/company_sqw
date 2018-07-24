package com.mall.supplier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.common.json.JSON;
import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierProduct;
import com.mall.supplier.model.SupplierRole;
import com.mall.supplier.model.SupplierUser;
import com.mall.supplier.model.SupplierUserRoleDTO;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.supplier.service.SupplierRoleManagerService;
import com.mall.supplier.service.SupplierUserManagerService;

public class DemoTest {
	private ClassPathXmlApplicationContext context;
	
	
	private  SupplierRoleManagerService supplierRoleManagerService;
	private  SupplierUserManagerService supplierUserManagerService;
	private  SupplierManagerService supplierManagerService;
	@Before
	public void context(){
		this.context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		context.start();
		supplierRoleManagerService =(SupplierRoleManagerService) context.getBean("supplierRoleManagerService");
		supplierUserManagerService =(SupplierUserManagerService) context.getBean("supplierUserManagerService");
		supplierManagerService =(SupplierManagerService) context.getBean("supplierManagerService");
	}
	
	
    @Test
    public void getRoleUser() throws Exception{
    	//System.out.println("数量==============="+supplierUserManagerService.countUserByRoleId(24L));
    	//System.out.println(supplierRoleManagerService.countRoleByNameAndMerchId("财务会计", 1L));
    	//System.out.println("测试开始===============");
    	//System.out.println(supplierManagerService.findSupplier(1L));
    } 

    @Test
    public void findAdminUserByMerchantId() throws Exception{
    	
    	//System.out.println("测试开始===============");
       System.out.println(supplierManagerService.findAdminUserByMerchantId(1L));
    } 
    
    
    @Test
    public void deleteAll() {
    	List<Long> ids=new ArrayList<Long>();
    	ids.add(20185L);
    	System.out.println("测试删除："+supplierUserManagerService.deleteAll(ids));
	}
    
    @Test
    public void updateSupplierBaseInfo() {
    	Supplier supplier=new Supplier();
    	supplier.setSupplierId(18520L);
    	supplier.setSupplierCode("123456789");
        SupplierProduct record=new  SupplierProduct();
        record.setSupplierId(supplier.getSupplierId());
        record.setCategories("1");
    	System.out.println("测试更新基本信息："+supplierManagerService.updateSupplierBaseInfo(supplier,record));
	}
    
    @Test
    public void getPageList() throws Exception  {
    	//Supplier pa=new Supplier();
    	Map<String ,String > map=new LinkedHashMap<String, String>();
    	map.put("status", "0");
    	//此处传的参数为查询条件 
    	// pa.setName("nike");
    	 // pa.setType(1);
        // pa.setStatus(1);
          //创建分页对象指定泛型
          PageBean<Supplier> page=new  PageBean<Supplier>();
          //设置分页对象的参数
           page.setPageSize(15);//分页个数 不传默认15
          page.setSortFields("supplier_id");// 排序字段
          page.setOrder("asc");//降序 升序
  	       page.setParameter(map);//参数对象
          //调用服务 返回结果
           page=supplierManagerService.getPageList(page);
         System.out.println("分页开始==============="+JSON.json(page));
    }
    
    @Test
    public void selectRolesBySupplierIds() throws Exception  {
    	List<Long> ids=new ArrayList<Long>();
    	ids.add(1L);
    	ids.add(0L);
    	List<SupplierRole> selectRolesBySupplierIds = supplierRoleManagerService.selectRolesBySupplierIds(ids);
    	   System.out.println("开始selectRolesBySupplierIds==============="+JSON.json(selectRolesBySupplierIds));
    }
	
    @Test
    public void findSubSuppliersBySupplierId() throws Exception  {
    	  System.out.println("开始findSubSuppliersBySupplierId==============="+JSON.json(supplierUserManagerService.findSubSuppliersBySupplierId(1L)));
    };
	
    @Test
    public void  isSubSupplier() throws Exception  {
    	 System.out.println("开始isSubSupplier==============="+JSON.json(supplierUserManagerService.isSubSupplier(20194L)));
    };
    @Test
    public void  getSubSuppliersByPid() throws Exception  {
    	 System.out.println("开始getSubSuppliersByPid==============="+JSON.json(supplierManagerService.getSubSuppliersByPid(1L)));
    };
    @Test
    public void  isSubSupplier2() throws Exception  {
    	 System.out.println("开始isSubSupplier==============="+JSON.json(supplierManagerService.isSubSupplier(18532L)));
    };
    @Test
    public void  getSubSupplierIdsByPid() throws Exception  {
    	 System.out.println("开始getSubSuppliersByPid==============="+JSON.json(supplierManagerService.getSubSupplierIdsByPid(1L)));
    };
    
    @Test
    public void  getIdNameMapBySupplierIds() throws Exception  {
    	List<Long> list=new ArrayList<Long>();
    	list.add(18490L);
    	list.add(1L);
    	list.add(26L);
    	
    	 System.out.println("开始getSubSuppliersByPid==============="+JSON.json(supplierManagerService.getIdNameMapBySupplierIds(list)));
    };
    
    
}
