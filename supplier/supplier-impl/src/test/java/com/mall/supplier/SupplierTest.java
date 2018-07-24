package com.mall.supplier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mall.mybatis.utility.PageBean;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.common.json.JSON;
import com.mall.supplier.dao.SupplierMapper;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierPartnerArea;
import com.mall.supplier.model.SupplierRegionSettings;
import com.mall.supplier.model.SupplierUser;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.supplier.service.SupplierRegionService;
import com.mall.supplier.service.SupplierTypeService;

public class SupplierTest {
	private ClassPathXmlApplicationContext context;

	@Autowired
	SupplierTypeService supplierTypeService;

	@Autowired
	SupplierRegionService regionService;

	@Autowired
	private SupplierMapper supplierMapper;

	private SupplierManagerService supplierManagerService;

	@Before
	public void context() {
		this.context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
		context.start();
		supplierManagerService = (SupplierManagerService) context
				.getBean("supplierManagerService");
		supplierTypeService = (SupplierTypeService) context
				.getBean("supplierTypeService");
		regionService = (SupplierRegionService) context
				.getBean("supplierRegionService");
		// supplierService =(SupplierManagerService)
		// context.getBean("supplierService");
	}

	@Test
	public void updateSupllier() {
		Supplier supplier = new Supplier();
		SupplierUser user = new SupplierUser();
		supplier.setSupplyType(51);
		supplier.setSupplierId(27438l);
		int type = supplierMapper.updateByPrimaryKey(supplier);
		// int type = supplierMapper.updateByPrimaryKeySelective(supplier);
		// int type =supplierManagerService.checkMerchant(supplier, user);
		// int type = supplierMapper.updateByPrimaryKeySelective(supplier);
		Supplier user2 = supplierManagerService.findSupplier(27438l);
		System.out.println("==================>" + user2.getSupplyType());
	}

	@Test
	public void getIdNameMapBySupplierIds() throws Exception {
		List<Long> list = new ArrayList<Long>();
		list.add(18490L);
		list.add(1L);
		list.add(26L);

		System.out.println("开始getSubSuppliersByPid==============="
				+ JSON.json(supplierManagerService
						.getIdNameMapBySupplierIds(list)));
	};

	@Test
	public void getSupply() {

		List<Long> subSupplierIdsByPid = supplierManagerService
				.getSubSupplierIdsByPid(1L);

		System.out.println(subSupplierIdsByPid.size());
	};

	@Test
	public void delete() {
		supplierManagerService.delete(18561L);

	};

	@Test
	public void getSupplierIdsByName() {
		List<Long> supplierIdsByName = supplierManagerService
				.getSupplierIdsByName("新世纪电子商务");
		System.out.println("===========>" + supplierIdsByName.size());

	};

	@Test
	public void findSupplier() {

		Supplier supplier = new Supplier();
		supplier.setStatus(1);
		supplier.setSupplyType(51);
		supplier.setSupplierId(27438l);
		supplier.setAuditTime(new Date());

		SupplierUser user = new SupplierUser();
		user.setSupplierId(27438l);
		user.setStatus(1);

		// int type = supplierMapper.updateByPrimaryKey(supplier);
		int type = supplierManagerService.checkMerchant(supplier, user);
		System.out.println("成功 修改 =====>" + type);

		// Supplier findSupplier = supplierManagerService.findSupplier(27438L);
		// System.out.println("==============>"+findSupplier.getStatus()+findSupplier.getName()+" "+findSupplier.getSupplyType());

	};

	@Test
	public void getPageList() {
		/*
		 * Supplier findSupplier = supplierManagerService.getPageList(pageBean);
		 * System.out.println(findSupplier.getStatus());
		 */

	};

	@Test
	public void getAllSupper() {
		List<Supplier> findAllSupplier = supplierManagerService
				.findAllSupplier();

		System.out.println(findAllSupplier.size());
	};

	@Test
	public void typeSettingsTest() {
		BigDecimal proportionRepayment = supplierTypeService
				.getProportionRepayment(1);
		supplierTypeService.setProportionRepayment(1,
				proportionRepayment.multiply(BigDecimal.valueOf(2)));
		proportionRepayment = supplierTypeService
				.getProportionRepaymentByID((long) 1);
		System.out.println("typeSettingsTest is ok!");
	}

	@Test
	public void regionSettingsTest() {
		List<SupplierRegionSettings> regionSettingsList = null;
		PageBean<SupplierRegionSettings> supplierRegionSettingsPageBean = new PageBean<SupplierRegionSettings>();
		SupplierRegionSettings regionSettings = new SupplierRegionSettings();
		supplierRegionSettingsPageBean.setPage(1);
		supplierRegionSettingsPageBean.setPageSize(2);
		supplierRegionSettingsPageBean.setParameter(regionSettings);
		regionSettingsList = regionService
				.getRegionSettings(supplierRegionSettingsPageBean);
		System.out.println(regionSettingsList.size());
		supplierRegionSettingsPageBean.setPage(2);
		regionSettingsList = regionService
				.getRegionSettings(supplierRegionSettingsPageBean);
		System.out.println(regionSettingsList.size());
		/*
		 * SupplierRegionSettings regionSettings = new SupplierRegionSettings();
		 * regionSettings.setRegionId(4);
		 * System.out.println(regionService.deleteRegionSettings
		 * (regionSettings)); regionSettings.setRegionId(1);
		 * regionSettings.setRegionText("自营");
		 * regionService.deleteRegionSettings(regionSettings);
		 * regionService.insertRegionSettings(regionSettings);
		 * regionSettings.setRegionId(2); regionSettings.setRegionText("孵化");
		 * regionService.insertRegionSettings(regionSettings);
		 * regionSettings.setRegionId(3); regionSettings.setRegionText("高新");
		 * regionService.insertRegionSettings(regionSettings);
		 * regionSettings.setRegionId(4); regionSettings.setRegionText("自营");
		 * regionService.insertRegionSettings(regionSettings);
		 * regionSettings.setRegionText("普通");
		 * regionService.updateRegionSettings(regionSettings);
		 * regionService.getRegionSettingsByID(1); List<SupplierRegionSettings>
		 * regionSettingsList = regionService.getAllRegionSettings();
		 * System.out.println(regionSettingsList.size());
		 */
		System.out.println("typeSettingsTest is ok!");
	}

	@Test
	public void test_findAreaOperators() {

		List<SupplierPartnerArea> findAreaOperators = supplierManagerService
				.findAreaOperators(5l, 423l, 2939l,1);

		System.out.println("findAreaOperators执行结果="+findAreaOperators.size());
	}

}
