package com.mall.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mall.dao.AttrDao;
import com.mall.dbcp.DBQuery;
import com.mall.po.AttrCode;


public class AttrDaoImpl implements AttrDao {

	public Map<String,List<AttrCode>> getNames(String dbName)throws Exception {
		long s=System.currentTimeMillis();
		DBQuery<AttrCode> dbQuery = new DBQuery<AttrCode>(AttrCode.class);
		String sql = "SELECT DISTINCT ATTRVAL.ATTR_VAL_ID, ATTR.ATTR_NAME_CN AS ATTRNAME,ATTRVAL.LINE_ATTRVAL_NAME_CN AS ATTRVALNAME ,ATTR.PRODUCTID AS ID"
				+ " FROM D_PRODUCT_ATTR ATTR JOIN D_PRODUCT_ATTRVAL ATTRVAL ON ATTR.ATTR_ID = ATTRVAL.ATTR_ID"
				+ " WHERE ATTR.ATTR_ID <> 11 AND ATTRVAL.ISPRIVATE <> 1";
		List<AttrCode> attrCodeList = dbQuery.queryList(sql, null, dbName);
		Map<String,List<AttrCode> > tmp=new HashMap<String, List<AttrCode>>();
		for(AttrCode a:attrCodeList){
			if(tmp.containsKey(a.getId())){
				tmp.get(a.getId()).add(a);
			}else {
				List<AttrCode> list=new ArrayList<AttrCode>();
				list.add(a);
				tmp.put(a.getId(),list);
			}
		}
		return tmp;
//		Map<String,List<AttrCode>> attrCodeMap = new HashMap<String,List<AttrCode>>();
//		for (AttrCode attrCode : attrCodeList) {
//			String attrid = attrCode.getId();
//			List<AttrCode> list=new ArrayList<AttrCode>();
//			for(AttrCode attrCode_ :attrCodeList){
//				if(attrCode_.getId()!=null && attrCode_.getId().equals(attrid)){
//					list.add(attrCode_);
//				}
//			}
//			attrCodeMap.put(attrid, list);
//		}
//		System.out.println(System.currentTimeMillis()-s);
//		return attrCodeMap;
	}

	public Map<String,AttrCode> getAttrCode(String dbName) throws Exception {
		
		DBQuery<AttrCode> dbQuery = new DBQuery<AttrCode>(AttrCode.class);
		//String sql = "SELECT ID,ATTRNAME,ATTRVALNAME FROM TD_ATTR_VAL_CODE WHERE ATTRNAME = ? AND ATTRVALNAME = ?";
		String sql = "SELECT ID,ATTRNAME,ATTRVALNAME FROM TD_ATTR_VAL_CODE";
		List<AttrCode> attrlist = dbQuery.queryList(sql, dbName);
		Map<String,AttrCode> attrMap = new HashMap<String,AttrCode>();
		for (AttrCode attrCode : attrlist) {
			String attrid = attrCode.getAttrname()+attrCode.getAttrvalname();
			attrMap.put(attrid, attrCode);
		}
		return attrMap;
	}

}
