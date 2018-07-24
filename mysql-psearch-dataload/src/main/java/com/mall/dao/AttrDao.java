package com.mall.dao;

import java.util.List;
import java.util.Map;

import com.mall.po.AttrCode;
/**
 * 属性DAO
 * @author DOUBLELL
 *
 */
public interface AttrDao {
	/**
	 * 收集非自定义属性
	 * 
	 * @param productid 产品id
	 * @param l2sid 供应商id后两位
	 * @return
	 */
	public Map<String,List<AttrCode>> getNames(String dbName)throws Exception;

	/**
	 * 收集属性对标识
	 * 
	 * @return
	 */
	public Map<String,AttrCode> getAttrCode(String dbName) throws Exception;

}
