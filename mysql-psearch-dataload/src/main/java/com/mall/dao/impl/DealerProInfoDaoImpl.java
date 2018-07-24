package com.mall.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mall.common.ConstantManage;
import com.mall.dao.DealerProInfoDao;
import com.mall.dbcp.DBQuery;
import com.mall.po.BrandName;
import com.mall.po.CashRatio;
import com.mall.po.DealerProInfoBase;
import com.mall.po.DealerProInfoBaseC;
import com.mall.po.DealerProInfoSaleSetting;
import com.mall.po.DealerProInfoSku;
import com.mall.po.Family;
import com.mall.po.ProInfoMain;
import com.mall.po.ProductDetailB2c;
import com.mall.po.ProductSales;
import com.mall.po.SupplierInfo;
import com.mall.po.TagName;
import com.mall.po.TdCateDispCrosslink;

/**
 * dao 实现  ConstantManager：sql调用（常量表）
 * @author Administrator
 *
 */
public class DealerProInfoDaoImpl implements DealerProInfoDao {
	private Logger logger = Logger.getLogger(DealerProInfoDaoImpl.class);

	
	// 商品表
	public List<ProInfoMain> getDealerProInfoMain(String dbName) throws Exception {
		
		DBQuery<ProInfoMain> dbQuery = new DBQuery<ProInfoMain>(ProInfoMain.class);
		DBQuery<BrandName> dbQuery2 = new DBQuery<BrandName>(BrandName.class);
		DBQuery<BrandName> dbQuery3 = new DBQuery<BrandName>(BrandName.class);
		
		
		
		List<BrandName> pid = dbQuery2.queryList(ConstantManage.STOCK, null, ConstantManage.DBNAME3);
		
		List<BrandName> pid2 = dbQuery3.queryList(ConstantManage.STOCK2, null, ConstantManage.DBNAME3);
		
		StringBuffer str2 = new StringBuffer();
		 StringBuffer str = new StringBuffer();
	        for(int i=0;i<pid.size();i++){
	        	if(pid.size()-1==i){
	        		str.append(pid.get(i).getBrandId());
	        		
	        	}else{
	        		str.append(pid.get(i).getBrandId()+",");
	        	}
	        	
	        }
	        for(int i=0;i<pid2.size();i++){
	        	if(pid2.size()-1==i){
	        		str2.append(pid2.get(i).getBrandId());
	        	}else{
	        		str2.append(pid2.get(i).getBrandId()+",");
	        	}
	        }
	        List<ProInfoMain> proInfo = new ArrayList<ProInfoMain>();
	        if(str2!=null && str!=null){
	        	String sql2="select productid AS BRANDID FROM D_PRODUCT WHERE d_product.PROD_TYPE=5 and d_product.supplierid not in (18920) and (d_product.b2b_istate=1 or d_product.b2c_istate=1) and d_product.productid in("+str2+")";
		        List<BrandName> pid3 = dbQuery3.queryList(sql2, null, ConstantManage.DBNAME);
		        if(pid3!=null && pid3.size()!=1){
		        	  for(int i=0;i<pid3.size();i++){
		  	        	if(pid3.size()-1==i){
		  	        		str.append(pid3.get(i).getBrandId());
		  	        		
		  	        	}else if(pid3.size()==0){
		  	        		str.append(","+pid3.get(i).getBrandId()+",");
		  	        	}
		  	        	else{
		  	        		str.append(pid3.get(i).getBrandId()+",");
		  	        	}
		  	        }
		        }
		        if(pid3!=null && pid3.size()==1){
		        	  for(int i=0;i<pid3.size();i++){
		  	        	
		  	        		str.append(","+pid3.get(i).getBrandId());
		  	        }
		        }
		      
			String sql="select  productid,brand_id,originplace as country_id,IMAGEALT as imageAlt,supplierid,istate,(select counterfeittypeid from d_product_audit where d_product.productid = d_product_audit.productid and counterfeittypeid <>'3' limit 0,1) as counterfeittypeid,vaildday,cate_pub_id,cate_disp_id,translatestatus,prod_line_id,withdrawaltype,expire_date,operatetime,createddate,srhdate,imageurl, (select measureid from d_product_package where d_product_package.productid = d_product.productid)as  measureid,b2b_istate,b2c_istate from d_product where d_product.supplierid not in (18920) and (d_product.b2b_istate=1 or d_product.b2c_istate=1) and d_product.productid in("+str+")";
					proInfo=dbQuery.queryList(sql, null, dbName);
	        }
		
		return proInfo;
	}
	

	

	
	
	// 供应商信息
	public Map<String,SupplierInfo> getSupplierInfo(String dbName) throws Exception {
		
		DBQuery<SupplierInfo> dbQuery = new DBQuery<SupplierInfo>(SupplierInfo.class);
		List<SupplierInfo> baseList = dbQuery.queryList(ConstantManage.SQL_S_SUPPLIER, null, dbName);
		
		Map<String, SupplierInfo> baseMap = new HashMap<String, SupplierInfo>();
		for (SupplierInfo dealerProInfoBase : baseList) {
			String supplierId = dealerProInfoBase.getSupplierId();
			baseMap.put(supplierId, dealerProInfoBase);
		}
		return baseMap;
	}
	
	// 商品详描地址信息
	public Map<String, String> getDealerProDesc(String dbName) throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		DBQuery<ProInfoMain> dbQuery = new DBQuery<ProInfoMain>(ProInfoMain.class);
		List<ProInfoMain> list= dbQuery.queryList(ConstantManage.SQL_D_PRODUCTDESC, null, dbName);
		if(list!=null && list.size() > 0){
			for(int i=0; i<list.size();i++){
				map.put(list.get(i).getProductid(), list.get(i).getImageurl());
			}
		}
		return map;
	}
	
	// 商品副表 b2b
	public Map<String, DealerProInfoBase> getDealerProInfoBase(String dbName) throws Exception {
		
		DBQuery<DealerProInfoBase> dbQuery = new DBQuery<DealerProInfoBase>(DealerProInfoBase.class);
		
		List<DealerProInfoBase> baseList = dbQuery.queryList(ConstantManage.SQL_D_PRODUCT_BASE, null, dbName);
		//返回结果处理下
		Map<String, DealerProInfoBase> baseMap = new HashMap<String, DealerProInfoBase>();
		for (DealerProInfoBase dealerProInfoBase : baseList) {
			String tProductid = dealerProInfoBase.getProductid();
			baseMap.put(tProductid, dealerProInfoBase);
		}
		return baseMap;
	}
	
	// 商品副表 b2c
	public Map<String, DealerProInfoBaseC> getDealerProInfoBase_c(String dbName) throws Exception {
		
		DBQuery<DealerProInfoBaseC> dbQuery = new DBQuery<DealerProInfoBaseC>(DealerProInfoBaseC.class);
		
		List<DealerProInfoBaseC> baseList = dbQuery.queryList(ConstantManage.SQL_D_PRODUCT_BASE_C, null, dbName);
		//返回结果处理下
		Map<String, DealerProInfoBaseC> baseMap = new HashMap<String, DealerProInfoBaseC>();
		for (DealerProInfoBaseC dealerProInfoBasec : baseList) {
			String tProductid = dealerProInfoBasec.getProductid();
			baseMap.put(tProductid, dealerProInfoBasec);
		}
		return baseMap;
	}
	
	//商品sku表
	public Multimap<String, DealerProInfoSku> getDealerProInfoSku(String dbName) throws Exception {
		
		DBQuery<DealerProInfoSku> dbQuery = new DBQuery<DealerProInfoSku>(DealerProInfoSku.class);

		List<DealerProInfoSku> skuList = dbQuery.queryList(ConstantManage.SQL_D_PRODUCT_SKU, null, dbName);
		//处理返回结果
		Multimap<String, DealerProInfoSku> skuMap = ArrayListMultimap.create();
		for (DealerProInfoSku dealerProInfoSku : skuList) {
			String tProductid = dealerProInfoSku.getProductid();
			skuMap.put(tProductid, dealerProInfoSku);
		}
		return skuMap;
	}
	
	//商品销售信息表
	public Map<String, DealerProInfoSaleSetting> getDealerProInfoSaleSetting(String dbName) throws Exception {
		
		DBQuery<DealerProInfoSaleSetting> dbQuery = new DBQuery<DealerProInfoSaleSetting>(DealerProInfoSaleSetting.class);
		
		List<DealerProInfoSaleSetting> saleList = dbQuery.queryList(ConstantManage.SQL_D_PRODUCT_SALE_SETTING, null,dbName);
		//处理返回结果
		Map<String, DealerProInfoSaleSetting> saleMap = new HashMap<String, DealerProInfoSaleSetting>();
		for (DealerProInfoSaleSetting dealerProInfoSaleSetting : saleList) {
			String tProductid = dealerProInfoSaleSetting.getProductid();
			saleMap.put(tProductid, dealerProInfoSaleSetting);
		}
		return saleMap;
	}
	
	public Map<String,BrandName> getBrandName(String dbName)throws Exception {
		DBQuery<BrandName> dbQuery = new DBQuery<BrandName>(BrandName.class);

		List<BrandName> brandNames = dbQuery.queryList(ConstantManage.TD_BRAND, null, dbName);
		
		//处理返回结果
		Map<String, BrandName> brandNameMap = new HashMap<String, BrandName>();
		for (BrandName brandName : brandNames) {
			String brandid = brandName.getBrandId();
			brandNameMap.put(brandid, brandName);
		}
		
		return brandNameMap;
	}
	
	
	public List<TagName> getTag(String dbName,String pid)throws Exception {
		DBQuery<TagName> dbQuery = new DBQuery<TagName>(TagName.class);
		String sql = "select tag_name as tagName,product_id as productId from  product_tags  WHERE product_tags.tag_type = 1 and product_tags.product_id =  " + Long.valueOf(pid);
		List<TagName> TagNames = dbQuery.queryList(sql, null, dbName);
		/*System.out.println(pid + "______________________________" + TagNames);*/
		
		//处理返回结果
		
		return TagNames;
	}

	
	public Map<String,BrandName> getCyid(String dbName) throws Exception {
		
		DBQuery<BrandName> dbQuery = new DBQuery<BrandName>(BrandName.class);
		List<BrandName> cyNames = dbQuery.queryList(ConstantManage.TC_COUNTRY, null, dbName);
		//处理返回结果
		Map<String, BrandName> brandNameMap = new HashMap<String, BrandName>();
		for (BrandName brandName : cyNames) {
			String brandid = brandName.getDefiningValue();
			brandNameMap.put(brandid, brandName);
		}
		return brandNameMap;
	}
	
	public Map<String,ProductDetailB2c> getB2cName(String dbName)throws Exception {
		
		DBQuery<ProductDetailB2c> dbQuery = new DBQuery<ProductDetailB2c>(ProductDetailB2c.class);
		List<ProductDetailB2c> productDetailB2c = dbQuery.queryList(ConstantManage.D_PRODUCT_DETAIL_B2C, null, dbName);
		//处理返回结果
		Map<String, ProductDetailB2c> ProductDetailB2cMap = new HashMap<String, ProductDetailB2c>();
		for (ProductDetailB2c ProductDetailB2c : productDetailB2c) {
			String pid = ProductDetailB2c.getPid();
			ProductDetailB2cMap.put(pid, ProductDetailB2c);
		}
		return ProductDetailB2cMap;
	}
	
	public Map<String,BrandName> getB2cPrice(String dbName) throws Exception {
		DBQuery<BrandName> dbQuery = new DBQuery<BrandName>(BrandName.class);
		List<BrandName> b2cPrices = dbQuery.queryList(ConstantManage.B2C_PRICE, null, dbName);
		//处理返回结果
		Map<String, BrandName> B2cPriceMap = new HashMap<String, BrandName>();
		for (BrandName b2cPrice : b2cPrices) {
			String pid = b2cPrice.getBrandId();
			B2cPriceMap.put(pid, b2cPrice);
		}
		return B2cPriceMap;
	}
	
	/*public BrandName getSearchKeywords(String id, String dbName) throws Exception {
		BrandName searchKeywords = new BrandName();
		DBQuery<BrandName> dbQuery = new DBQuery<BrandName>(BrandName.class);
		String sql = "SELECT SEARCH_KEYWORDS as brandName FROM d_product_detail WHERE productid = ? ";
		String[] params = { id };
		searchKeywords = dbQuery.query(sql, params, dbName);
		return searchKeywords;
	}*/
	
	public Map<String,BrandName> getMoneyUnit(String dbName) throws Exception {
		DBQuery<BrandName> dbQuery = new DBQuery<BrandName>(BrandName.class);
		List<BrandName> moneyunits= dbQuery.queryList(ConstantManage.TB_MONEY_UNIT, null, dbName);
		//处理返回结果
		Map<String, BrandName> moneyunitMap = new HashMap<String, BrandName>();
		for (BrandName moneyunit : moneyunits) {
			String bid = moneyunit.getBrandId();
			moneyunitMap.put(bid, moneyunit);
		}
		return moneyunitMap;
	}
	
	/*public String getMeasureName(String measureid, String dbName)throws Exception {
		BrandName measure = new BrandName();
		DBQuery<BrandName> dbQuery = new DBQuery<BrandName>(BrandName.class);
		String measureName = null;
		String[] params = { measureid };
		measure = dbQuery.query(ConstantManager.TC_MEASURE, params, dbName);
		if (measure != null) {
			measureName = measure.getBrandName();
		}
		return measureName;
	}*/
	
	public Map<String,List<BrandName>> getb2bCateDispId(String dbName)throws Exception {
		DBQuery<BrandName> dbQuery = new DBQuery<BrandName>(BrandName.class);
		//展示类目和发布类目改为多对多，这次取中间表。
		String sql = "SELECT cate_disp_id as brandName,cate_pub_id as subBrandName FROM td_cate_disp_pub WHERE TYPE='b2b'";
		List<BrandName> tbrandNames = dbQuery.queryList(sql, null, dbName);
		Map<String,List<BrandName>> b2bCateDispMap = new HashMap<String,List<BrandName>>();
		/*for (BrandName tbrandName : tbrandNames) {
			String bid = tbrandName.getSubBrandName();
			List<BrandName> list=new ArrayList<BrandName>();
			for(BrandName tName:tbrandNames){
				if(tName.getSubBrandName().equals(bid)){
					list.add(tName);
				}
			}
			b2bCateDispMap.put(bid, list);
		}*/
		for (BrandName brandName : tbrandNames) {
			if(b2bCateDispMap.containsKey(brandName.getSubBrandName())){
				b2bCateDispMap.get(brandName.getSubBrandName()).add(brandName);
			}else{
				List<BrandName> list = new ArrayList<BrandName>();
				list.add(brandName);
				b2bCateDispMap.put(brandName.getSubBrandName(), list);
			}
		}
		return b2bCateDispMap;
	}

	public Map<String,List<BrandName>> getb2cCateDispId(String dbName)throws Exception {
		DBQuery<BrandName> dbQuery = new DBQuery<BrandName>(BrandName.class);
		String sql = "SELECT cate_disp_id as brandName,cate_pub_id as subBrandName FROM td_cate_disp_pub WHERE TYPE='b2c'";
		List<BrandName> tbrandNames = dbQuery.queryList(sql, null, dbName);
		Map<String,List<BrandName>> b2cCateDispMap = new HashMap<String,List<BrandName>>();
		for (BrandName brandName : tbrandNames) {
			if(b2cCateDispMap.containsKey(brandName.getSubBrandName())){
				b2cCateDispMap.get(brandName.getSubBrandName()).add(brandName);
			}else{
				List<BrandName> list = new ArrayList<BrandName>();
				list.add(brandName);
				b2cCateDispMap.put(brandName.getSubBrandName(), list);
			}
		}
		return b2cCateDispMap;
	}
	
	/**
	 * 返回商品展示类目中文名称
	 * @param dbName
	 * @param isB2c
	 * @param cateDispIds
	 * @return
	 * @throws Exception
	 */
	public List<String> getb2cCateDispNamesByIds(String dbName,String isB2c,String cateDispIds)throws Exception {
		DBQuery<BrandName> dbQuery = new DBQuery<BrandName>(BrandName.class);
		String sql = "select CONCAT(DISP_NAME,' ',DISP_NAME_CN) brandName from td_cate_disp where TYPE='"+ isB2c +"' and CATE_DISP_ID in("+ cateDispIds +")";
		List<String> listStr = new ArrayList<String>();
		//logger.info("查询类目名称"+sql);
		List<BrandName> tbrandNames = dbQuery.queryList(sql, null, dbName);
		for(int i=0; i< tbrandNames.size(); i++){
			listStr.add(tbrandNames.get(i).getBrandName());
		}
		//logger.info("查询类目名称"+listStr.toString());
		return listStr;
	}
	
	public Map<String,BrandName> getStock(String dbName) throws Exception {
		DBQuery<BrandName> dbQuery = new DBQuery<BrandName>(BrandName.class);
		List<BrandName> brandNames= dbQuery.queryList(ConstantManage.SKU_STOCK, null, dbName);
		Map<String, BrandName> stockMap = new HashMap<String, BrandName>();
		for (BrandName stock : brandNames) {
			String bid = stock.getBrandId();
			stockMap.put(bid, stock);
		}
		return stockMap;
	}
	
	
	public Multimap<String,String> getCrossLinkCdid(String dbName) throws Exception{
		Multimap<String, String> cateCrossLinkcpidMap = ArrayListMultimap.create();
		String sql = "select cate_disp_crosslink_id as cateDispCrosslinkId, cate_disp_id as cateDispId, sub_cate_disp_id as subCateDispId from td_cate_disp_crosslink";
		DBQuery<TdCateDispCrosslink> dbQuery = new DBQuery<TdCateDispCrosslink>(TdCateDispCrosslink.class);
		List<TdCateDispCrosslink> tdCateDispCrosslinkList =  dbQuery.queryList(sql.toString(), null,dbName);
		if (tdCateDispCrosslinkList == null) {
			return cateCrossLinkcpidMap;
		}
		for (TdCateDispCrosslink tdCateDispCrosslink : tdCateDispCrosslinkList) {
			String subCateDispId = tdCateDispCrosslink.getSubCateDispId();
			// crosslink中 子类目--所有的父类目
			cateCrossLinkcpidMap.put(subCateDispId, tdCateDispCrosslink.getCateDispId());
		}
		return cateCrossLinkcpidMap; 
	}
	
	public Map<String, Integer> getProSales(String dbName) throws Exception {
		Map<String,Integer> proSalesMap = new HashMap<String,Integer>();
		DBQuery<ProductSales> dbQuery = new DBQuery<ProductSales>(ProductSales.class);
		List<ProductSales> productSalseList = dbQuery.queryList(ConstantManage.PRO_SALES, dbName);
		
		if(null == productSalseList){
			return proSalesMap;
		}
		for (ProductSales productSales : productSalseList) {
			String pid = productSales.getPid();
			proSalesMap.put(pid, productSales.getQty());
		}
		return proSalesMap;
	}








	/**
	 * 幸福购商品  不含企业号，家庭号
	 */
	public List<CashRatio> getCash(String dbName,String pid)throws Exception {
		
//		DBQuery<Family> dbQuery2 = new DBQuery<Family>(Family.class);
//		String sql2="select tag_code as prodType,product_id as productId from product_tags where product_tags.tag_type=4";
//		List<Family> prodid = dbQuery2.queryList(sql2,null,dbName);
//		 StringBuffer str = new StringBuffer();
//	        for(int i=0;i<prodid.size();i++){
//	        	if(prodid.size()-1==i){
//	        		str.append(prodid.get(i).getProductId());
//	        		
//	        	}else{
//	        		str.append(prodid.get(i).getProductId()+",");
//	        	}
//	        	
//	        }
		DBQuery<CashRatio> dbQuery = new DBQuery<CashRatio>(CashRatio.class);
		String sql = "select manufacturers as cashName,productid as productId from  d_product  WHERE d_product.prod_type = 6 and  d_product.productid not in ( select product_id from product_tags where product_tags.tag_type=4 ) and d_product.productid=" + Long.valueOf(pid);
		List<CashRatio> cash = dbQuery.queryList(sql, null, dbName);
		/*System.out.println(pid + "______________________________" + TagNames);*/
		
		//处理返回结果
		
		return cash;
	}






	@Override
	public List<Family> getFam(String dbName, String pid) throws Exception {
		DBQuery<Family> dbQuery = new DBQuery<Family>(Family.class);
		String sql = "select manufacturers as prodType,productid as productId from  d_product  WHERE d_product.prod_type = 5 and d_product.productid=  " + Long.valueOf(pid);
		List<Family> family = dbQuery.queryList(sql, null, dbName);
		//处理返回结果
		return family;
	}






	/**
	 * 全券
	 */
		@Override
		public List<Family> getNormal(String dbName, String pid) throws Exception {
			DBQuery<Family> dbQuery = new DBQuery<Family>(Family.class);
			String sql = "select manufacturers as prodType,productid as productId from  d_product  WHERE d_product.prod_type != 5 and d_product.prod_type != 6 and d_product.productid not in (select product_id from product_tags where product_tags.tag_type=4) and d_product.productid=  " + Long.valueOf(pid);
			List<Family> normal = dbQuery.queryList(sql, null, dbName);
			//处理返回结果
			return normal;
		}

		
		
		//家庭号,企业号等服务
		@Override
		public List<Family> getjth(String dbName, String pid) throws Exception {
			DBQuery<Family> dbQuery = new DBQuery<Family>(Family.class);
			String sql="select tag_code as prodType,product_id as productId from  product_tags  WHERE product_tags.tag_type = 4 and product_tags.product_id =  " + Long.valueOf(pid);
			List<Family> jthfw = dbQuery.queryList(sql, null, dbName);
			return jthfw;
		}

	











}
