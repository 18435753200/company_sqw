package com.mall.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;
import com.mall.po.BrandName;
import com.mall.po.CashRatio;
import com.mall.po.DealerProInfoBase;
import com.mall.po.DealerProInfoBaseC;
import com.mall.po.DealerProInfoSaleSetting;
import com.mall.po.DealerProInfoSku;
import com.mall.po.Family;
import com.mall.po.ProInfoMain;
import com.mall.po.ProductDetailB2c;
import com.mall.po.SupplierInfo;
import com.mall.po.TagName;

/**
 * 经销商商品Dao层接口
 * @author DOUBLELL
 *
 */
public interface DealerProInfoDao {
	/**
	 * 获得产品主表信息
	 * 
	 * @param pid  商品id
	 * @return
	 * @throws Exception
	 */
	public List<ProInfoMain> getDealerProInfoMain(String dbName) throws Exception;
	


	/**
	 * 获得供应商表信息
	 * 
	 * @param pid  商品id
	 * @return
	 * @throws Exception
	 */
	public Map<String,SupplierInfo> getSupplierInfo(String dbName) throws Exception;
	
	/**
	 * 获得产品详描地址信息
	 * 
	 * @param pid  商品id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getDealerProDesc(String dbName) throws Exception;


	/**
	 * 查询产品的（b2b 副表）标题信息 产品 基本&国际化信息
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public Map<String, DealerProInfoBase> getDealerProInfoBase(String dbName) throws Exception;
	
	/**
	 * 查询产品的（b2c 副表）标题信息 产品 基本&国际化信息
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public Map<String, DealerProInfoBaseC> getDealerProInfoBase_c(String dbName) throws Exception;
	
	/**
	 * 商品skuCode,sku,库存量 商户自编码，库存量
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public Multimap<String, DealerProInfoSku> getDealerProInfoSku(String dbName) throws Exception;

	/**
	 * 商品销售信息
	 * 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public Map<String, DealerProInfoSaleSetting> getDealerProInfoSaleSetting(String dbName) throws Exception;
	
	/**
	 * 品牌信息
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String,BrandName> getBrandName(String dbName)throws Exception;
	
	
	/**
	 * 标签信息
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<TagName> getTag(String dbName,String pid)throws Exception;
	
	/**
	 * 现金比例
	 * @param dbName
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public List<CashRatio> getCash(String dbName,String pid)throws Exception;
	public List<Family> getFam(String dbName,String pid)throws Exception;
	
	/**
	 * 普通的商品（prod_type!=5 and !=6）
	 */
	public List<Family> getNormal(String dbName,String pid)throws Exception;
	
	public List<Family> getjth(String dbName,String pid)throws Exception;
	
	/**
	 * 原产地
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String,BrandName> getCyid(String dbName) throws Exception;
	
	
	/**
	 * 根据商品ID获取B2C商品名称与保税区
	 * 
	 * @param pid
	 * @param dbName
	 * @return
	 * @throws SQLException
	 */
	public Map<String,ProductDetailB2c> getB2cName(String dbName)throws Exception;

	/**
	 * 根据商品ID获取B2C商品价格
	 * 
	 * @param pid
	 * @param dbName
	 * @return
	 * @throws SQLException
	 */
	public Map<String,BrandName> getB2cPrice(String dbName) throws Exception;
	
	/**
	 * 根据商品id获取关键词
	 * @param id
	 * @param dbName
	 * @return
	 * @throws Exception
	 */
	//public BrandName getSearchKeywords(String pid, String dbName) throws Exception;
	
	/**
	 * 根据ID获取汇率及符号
	 * 
	 * @param pid
	 * @param dbName
	 * @return
	 * @throws Exception
	 */
	public Map<String,BrandName> getMoneyUnit(String dbName) throws Exception;
	
	/**
	 * 商品单位
	 * 
	 * @param measureid,dbName
	 * @return
	 * @throws Exception
	 */
	//public String getMeasureName(String measureid, String dbName)throws Exception;
	
	/**
	 * 根据发布类目获取b2b展示类目
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String,List<BrandName>> getb2bCateDispId(String dbName)throws Exception;
	
	/**
	 * 根据发布类目获取b2c展示类目
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String,List<BrandName>> getb2cCateDispId(String dbName)throws Exception;
	
	/**
	 * 根据发布类目获取展示类目名称
	 * 
	 * @param isB2c true b2c false b2b
	 * @return
	 * @throws Exception
	 */
	public List<String> getb2cCateDispNamesByIds(String dbName,String isB2c,String cateDispIds)throws Exception;
	
	/**
	 * 库存信息
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String,BrandName> getStock(String dbName) throws Exception;
	
	/**
	 * 产品线类目
	 * @param dbName
	 * @return
	 * @throws Exception
	 */
	public Multimap<String,String> getCrossLinkCdid(String dbName) throws Exception;
	
	/**
	 * 销量信息
	 * @param dbName
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> getProSales(String dbName) throws Exception;
}
