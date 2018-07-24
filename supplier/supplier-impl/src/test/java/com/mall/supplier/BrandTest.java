package com.mall.supplier;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.common.json.JSON;
import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.SupplierBrand;
import com.mall.supplier.model.SupplierBrandDTO;
import com.mall.supplier.service.SupplierBrandManagerService;
import com.mall.supplier.util.PKUtils;

public class BrandTest {
	private ClassPathXmlApplicationContext context;
	
	private  SupplierBrandManagerService supplierBrandManagerService;
	@Before
	public void context(){
		this.context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		context.start();
		supplierBrandManagerService =(SupplierBrandManagerService) context.getBean("supplierBrandManagerServiceImpl");
	}
	
	
    @Test
    public void  insert() throws Exception  {
    	SupplierBrand brand=new SupplierBrand();
    	brand.setBrandId(PKUtils.getLongPrimaryKey());
    	brand.setName("Nike1");
    	brand.setSupplierId(1L);
    	brand.setCreateTime(new Date());
       System.out.println("开始insert==============="+JSON.json(supplierBrandManagerService.insertBrand(brand)));
    };
	
    @Test
    public void  findSupplierBrandsBySupplierId() throws Exception  {
    
       System.out.println("开始insert==============="+JSON.json(supplierBrandManagerService.findSupplierBrandsBySupplierId(1L)));
    };
    
    @Test
    public void  findPageList() throws Exception  {
    	PageBean<SupplierBrandDTO> pageBean = new PageBean<SupplierBrandDTO>();
		SupplierBrandDTO brand = new SupplierBrandDTO();
		brand.setSupplierId(1L);
		brand.setStatus(0);
		pageBean.setParameter(brand);
		PageBean<SupplierBrandDTO> pageList = supplierBrandManagerService.getBrandsPageList(pageBean );
		List<SupplierBrandDTO> result = pageList.getResult();
		System.out.println();
       System.out.println("开始insert==============="+JSON.json(result));
    };
    
    
    
    @Test
    public void testBrand(){
    	int exclusiveAgent = supplierBrandManagerService.isExclusiveAgent("aaaa");
    	
    	System.out.println(exclusiveAgent);
    }
    
    @Test
    public void testDesc(){
    	String description = supplierBrandManagerService.findDescriptionBySysBrandId(1L, "141023160186711095");
    	
    	System.out.println(description);
    	
    	if (null != description){
			if(description.indexOf("group1") != -1){
				System.out.println("富文本描述------------------"+description);
				
				description = "aaaaaaaaaaaaaaa";
			}
		}
    	
    	System.out.println("最终的描述："+description);
    }
}
