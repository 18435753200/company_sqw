package com.mall.service.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.common.ConstantManage;
import com.mall.dao.SupplierInfoDao;
import com.mall.dao.impl.SupplierInfoDaoImpl;
import com.mall.po.AgentCity;
import com.mall.po.AgentCounty;
import com.mall.po.AgentProvince;
import com.mall.po.Supplier;
import com.mall.po.SupplierDetail;
import com.mall.po.SupplierDetailAttr;
import com.mall.po.TeSectors;
import com.mall.service.SupplierInfoService;

/**
 * 数据收集主service
 * 
 */
public class SupplierInfoServiceImpl implements SupplierInfoService {

	private static Logger logger = LoggerFactory.getLogger(SupplierInfoServiceImpl.class);
	
	private static  final SupplierInfoDao supplierInfoDao=new SupplierInfoDaoImpl();
	
	private static Integer numOne=1;
	private static Integer numTwo=2;
	private static Integer numThree=3;

	@Override
	public List<Supplier> getSupplierInfo(String pid) {
		
		List<Supplier> list=new ArrayList<>();
		List<Supplier> newList=new ArrayList<>();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("开始查询");
		try {
			//查询商家主体类
			list = supplierInfoDao.getSupplierMain(ConstantManage.DBNAME4);
			for (Supplier supplier : list) {
				if(supplier.getActiveStatus().equals(1) && supplier.getStatus().equals(1)){
					newList.add(supplier);
				}
			}
			//查询所有的省数据
			List<AgentProvince> allProvinceList = supplierInfoDao.getAllProvinceList(ConstantManage.DBNAME2);
			//查询所有的市数据
			List<AgentCity> agentCityList = supplierInfoDao.getAllCityList(ConstantManage.DBNAME2);
			//查询所有的区数据
			List<AgentCounty> agentCountryList = supplierInfoDao.getAllCountryList(ConstantManage.DBNAME2);
			//查询企业类目
			List<TeSectors> teSectorsList = supplierInfoDao.getAllTeSectorsList(ConstantManage.DBNAME2);
			//给主体类赋值
			for (Supplier supplier : newList) {
				//把格式化时间转换为字符串类型存入索引中
				if(supplier.getCreateTime()!=null){
					Date date = df.parse(supplier.getCreateTime());
					supplier.setCreatetimeDate(date.getTime()+"");
				}
				supplier.getCreateTime();
				for (AgentProvince agentProvince : allProvinceList) {
					if(supplier.getProvinceId()!=null && supplier.getProvinceId().equals(agentProvince.getProvinceid())){
						supplier.setProvinceName(agentProvince.getProvinceName());
					}
				}
				for (AgentCity agentCity : agentCityList) {
					if(supplier.getCityId()!=null && supplier.getCityId().equals(agentCity.getCityId())){
						supplier.setCityName(agentCity.getCityName());
					}
				}
				for (AgentCounty agentCounty : agentCountryList) {
					if(supplier.getAreaId()!=null && supplier.getAreaId().equals(agentCounty.getCountyId())){
						supplier.setAreaName(agentCounty.getCountyName());
					}
				}
				
				for(TeSectors teSectors:teSectorsList){
					if(supplier.getCompanyBizCategory()!=null && supplier.getCompanyBizCategory().equals(teSectors.getSectorCode())){
						String[] split = teSectors.getSectorName().split("-");
						String strOne = split[1];
						String strTwo = split[2];
						ArrayList<String> arrList=new ArrayList<String>();
						if(strOne.contains("/")){
							String newStr = strOne.replace("/", "");
							supplier.setCompanyBizCategoryName(newStr);
							for(String str: strOne.split("/")){
								arrList.add(str);
							}
						}else{
							supplier.setCompanyBizCategoryName(strOne);
							arrList.add(strOne);
						}
						if(strTwo.contains("/")){
							for(String str: strTwo.split("/")){
								arrList.add(str);
							}
						}else{
							arrList.add(strTwo);
						}
//						String arrStr="";
//						for(int i=0;i<arrList.size();i++){
//							arrStr+=arrList.get(i);
//						}
						supplier.setCompanyBizCategoryMore(arrList);
					}
				}
				
				if(supplier.getCompanyBizType()!=null){
					if(supplier.getCompanyBizType().equals(numOne)){
						supplier.setCompanyBizName("企业");
					}
					if(supplier.getCompanyBizType().equals(numTwo)){
						supplier.setCompanyBizName("个体工商户");
					}
					if(supplier.getCompanyBizType().equals(numThree)){
						supplier.setCompanyBizName("事业单位");
					}
				}
				
				
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newList;
	}

	@Override
	public List<SupplierDetail> getSupplierDetailInfo(String pid) {
		
		List<SupplierDetail> list=new ArrayList<>();
		try {
			//查询店铺详细信息表
			list=supplierInfoDao.getSupplierDetailMain(ConstantManage.DBNAME4);
			
			List<SupplierDetailAttr> arrtList=supplierInfoDao.getSupplierDetailAttr(ConstantManage.DBNAME4);
			for (SupplierDetail supplierDetail:list) {
				List<SupplierDetailAttr> newAttrList=new ArrayList<SupplierDetailAttr>();
				for (SupplierDetailAttr supplierDetailAttr : arrtList) {
					if(supplierDetail.getId().equals(supplierDetailAttr.getStoreId())){
						newAttrList.add(supplierDetailAttr);
					}
				}
				supplierDetail.setAttrList(newAttrList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


}
