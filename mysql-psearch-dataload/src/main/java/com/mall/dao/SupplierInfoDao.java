package com.mall.dao;

import java.sql.SQLException;
import java.util.List;

import com.mall.po.AgentCity;
import com.mall.po.AgentCounty;
import com.mall.po.AgentProvince;
import com.mall.po.Supplier;
import com.mall.po.SupplierDetail;
import com.mall.po.SupplierDetailAttr;
import com.mall.po.TeSectors;



public interface SupplierInfoDao {
	
	public List<Supplier> getSupplierMain(String dbName) throws Exception;

	public List<AgentProvince> getAllProvinceList(String dbName)throws Exception;

	public List<AgentCity> getAllCityList(String dbName)throws SQLException;

	public List<AgentCounty> getAllCountryList(String dbName)throws SQLException;

	public List<TeSectors> getAllTeSectorsList(String dbName)throws SQLException;
	
	public List<SupplierDetail> getSupplierDetailMain(String dbName) throws Exception;

	public List<SupplierDetailAttr> getSupplierDetailAttr(String dbName)throws Exception;
	


	
}
