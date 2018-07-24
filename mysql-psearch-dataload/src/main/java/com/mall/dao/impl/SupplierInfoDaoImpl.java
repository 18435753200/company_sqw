package com.mall.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.mall.common.ConstantManage;
import com.mall.dao.SupplierInfoDao;
import com.mall.dbcp.DBQuery;
import com.mall.po.AgentCity;
import com.mall.po.AgentCounty;
import com.mall.po.AgentProvince;
import com.mall.po.Supplier;
import com.mall.po.SupplierDetail;
import com.mall.po.SupplierDetailAttr;
import com.mall.po.TeSectors;

/**
 * dao 实现  ConstantManager：sql调用（常量表）
 * @author Administrator
 *
 */
public class SupplierInfoDaoImpl implements SupplierInfoDao {
	private Logger logger = Logger.getLogger(SupplierInfoDaoImpl.class);

	@Override
	public List<Supplier> getSupplierMain(String dbName) throws Exception {
		logger.info("查询主表数据");
		DBQuery<Supplier> dbQuery = new DBQuery<Supplier>(Supplier.class);
		List<Supplier> baseList = dbQuery.queryList(ConstantManage.SQL_S_SUPPLIER_MAIN, null, dbName);
		return baseList;
	}

	@Override
	public List<AgentProvince> getAllProvinceList(String dbName) throws Exception {
		logger.info("查询省数据");
		DBQuery<AgentProvince> dbQuery = new DBQuery<AgentProvince>(AgentProvince.class);
		List<AgentProvince> baseList = dbQuery.queryList(ConstantManage.SQL_PROVINCE, null, dbName);
		return baseList;
	}

	@Override
	public List<AgentCity> getAllCityList(String dbName) throws SQLException {
		logger.info("查询市数据");
		DBQuery<AgentCity> dbQuery = new DBQuery<AgentCity>(AgentCity.class);
		List<AgentCity> baseList = dbQuery.queryList(ConstantManage.SQL_CITY, null, dbName);
		return baseList;
	}

	@Override
	public List<AgentCounty> getAllCountryList(String dbName) throws SQLException {
		logger.info("查询区县数据");
		DBQuery<AgentCounty> dbQuery = new DBQuery<AgentCounty>(AgentCounty.class);
		List<AgentCounty> baseList = dbQuery.queryList(ConstantManage.SQL_COUNTRY, null, dbName);
		return baseList;
	}

	@Override
	public List<TeSectors> getAllTeSectorsList(String dbName) throws SQLException {
		logger.info("查询类目数据");
		DBQuery<TeSectors> dbQuery = new DBQuery<TeSectors>(TeSectors.class);
		List<TeSectors> baseList = dbQuery.queryList(ConstantManage.SQL_TESECTORS, null, dbName);
		return baseList;
	}

	@Override
	public List<SupplierDetail> getSupplierDetailMain(String dbName) throws Exception {
		logger.info("查询商家店铺详情数据");
		DBQuery<SupplierDetail> dbQuery = new DBQuery<SupplierDetail>(SupplierDetail.class);
		List<SupplierDetail> baseList = dbQuery.queryList(ConstantManage.SQL_SUPPLIERDETAIL, null, dbName);
		return baseList;
	}

	@Override
	public List<SupplierDetailAttr> getSupplierDetailAttr(String dbName) throws Exception {
		logger.info("查询商家店铺详情中的图片数据");
		DBQuery<SupplierDetailAttr> dbQuery = new DBQuery<SupplierDetailAttr>(SupplierDetailAttr.class);
		List<SupplierDetailAttr> baseList = dbQuery.queryList(ConstantManage.SQL_SUPPLIERDETAILATTR, null, dbName);
		return baseList;
	}

	



}
