package com.mall.common;
/**
 * 最终类（db、sql）
 * @author 
 *
 */
public class ConstantManage {

	public static final String DBNAME = "dealer";
	
	public static final String DBNAME2 = "category";
	
	public static final String DBNAME3 = "dproduct";
	
	public static final String DBNAME4 = "supplier";
	
	public static final int THREE = 3;
	
	public static final int SIX = 6;
	
	public static final int NINE = 9;
	
	public static final int TWELVE = 12;
	
	public static final String CHANNEL_APP = "2";//安卓渠道   IOS  ANDROID  促销价格一样
	
	public static final String CHANNEL_PC = "1";//PC渠道
	
	public static final String CHANNEL_WAP = "3";//WAP渠道
	
	public static final String CHANNEL_YUEME = "8";//悦ME渠道
	
	public static final String CHANNEL_BOC_BEIFEN = "9";//中行北分渠道
	
	public static final String CHANNEL_BESTPAY = "10";//翼支付渠道
	
	public static final String CHANNEL_ZHANGJU = "11";//掌聚渠道
	
	public static final String CHANNEL_BOC_NINGBO = "12";//中行宁波分行渠道
	
	public static final String SQL_D_PRODUCT = "select  productid,brand_id,originplace as country_id,IMAGEALT as imageAlt,supplierid,istate,(select counterfeittypeid from d_product_audit where d_product.productid = d_product_audit.productid and counterfeittypeid <>'3' limit 0,1) as counterfeittypeid,vaildday,cate_pub_id,cate_disp_id,translatestatus,prod_line_id,withdrawaltype,expire_date,operatetime,createddate,srhdate,imageurl, (select measureid from d_product_package where d_product_package.productid = d_product.productid)as  measureid,b2b_istate,b2c_istate from d_product where d_product.supplierid not in (18920) and (d_product.b2b_istate=1 or d_product.b2c_istate=1)";
	
	
	
	public static final String SQL_D_TAG="select tag_name as tagName,product_id as productId from  product_tags  WHERE product_tags.product_id = ? ";
	
	public static final String SQL_S_SUPPLIER = "  select supplier_id as supplierId,trim(supplier_code) as supplierCode,trim(name) as name,trim(name_jc) as companySimpleName,trim(kf_tel) as customerServerTele,trim(company_region) as settledArea,trim(company_level) as companyLevel,trim(logo_imgurl) as supplierLogoImgurl from s_supplier ";
	
	public static final String SQL_S_SUPPLIER_MAIN = " select supplier_id as supplierId,trim(supplier_code) as supplierCode,trim(name) as name,trim(name_jc) as nameJC,trim(organization_type) as organizationType,trim(type) as type, "+
	"trim(province_id) as provinceId,trim(city_id) as cityId,trim(area_id) as areaId,trim(company_biz_type) as companyBizType,trim(company_biz_category) as companyBizCategory,trim(company_info) as companyInfo,trim(company_describe) as companyDescribe, "+
			"trim(logo_imgurl) as logoImgurl,trim(company_store_logo) as companyStoreLogo,trim(address) as address, trim(contact) as contact ,trim(contact_tel) as contactTel ,"
			+ "trim(phone) as phone,trim(company_region) as companyRegion,trim(manager) as manager,trim(active_status) as activeStatus, trim(status) as status,"
			+ "trim(create_time) as createTime,"
			+ "trim(manager_tel) as managerTel,trim(icon) as icon,trim(company_qy) as companyQy from s_supplier ";
	
	
	public static final String SQL_D_PRODUCTDESC = "  select productid,b2cDescription as imageurl from d_product_detail_b2c ";
			
	public static final String SQL_D_PRODUCT_BASE = "SELECT a.PRODUCTID,a.PRODUCTNAME,b.remark SHORTDESCRIPTION,a.KEYWORDS FROM D_PRODUCT_BASE a,d_product b where a.productid=b.productid ";
	
	public static final String SQL_D_PRODUCT_BASE_C = "SELECT PRODUCTID,B2CPRODUCTNAME,B2CSUPPLY,CREATE_TIME AS CREATETIME FROM D_PRODUCT_DETAIL_B2C ";

	public static final String SQL_D_PRODUCT_SKU = "SELECT PRODUCTID,SKU_ID,SKU_CODE,INVENTORY FROM D_PRODUCT_SKU WHERE ISTATE = '1'";
	
	public static final String SQL_D_PRODUCT_SALE_SETTING = "SELECT PRODUCTID,MIN_WHOLESALE_QTY,MIN_BUYER_PRICE,MAX_BUYER_PRICE,MIN_SELLER_PRICE,"
			+ "MAX_SELLER_PRICE,LEADINGTIME FROM D_PRODUCT_SALE_SETTING ";
	
	//public static final String NAME_SUB_BRAND = "SELECT IFNULL((SELECT  NAME_CN  FROM TD_SUB_BRAND WHERE ID = ?),0) AS BRANDNAME FROM DUAL";
	
	public static final String TD_BRAND = "SELECT ID AS BRANDID,  NAME_CN AS BRANDNAME  FROM TD_BRAND";

    //public static final String TD_SUB_BRAND = "SELECT IFNULL((SELECT PARENT_BRAND_ID FROM TD_SUB_BRAND WHERE ID = ?),0) AS BRANDNAME FROM DUAL";

    public static final String TC_COUNTRY = "SELECT NAME AS BRANDNAME,IMG AS SUBBRANDNAME,DESCRIPTION AS  BRANDID ,COUNTRYID AS DEFININGVALUE FROM TC_COUNTRY";
    
	public static final String D_PRODUCT_DETAIL_B2C = "SELECT PRODUCTID AS PID, B2CPRODUCTNAME,ORIGIN_COUNTRY,B2CMONEYUNITID,B2CSUPPLY FROM D_PRODUCT_DETAIL_B2C";

	//public static final String B2C_PRICE = "SELECT MIN(C.UNIT_PRICE) AS BRANDNAME,MAX(C.DOMESTIC_PRICE) AS SUBBRANDNAME,A.PRODUCTID AS BRANDID,B.SKU_ID AS SKUID FROM D_PRODUCT A, D_PRODUCT_SKU B, D_PRODUCT_PRICE C WHERE A.PRODUCTID = B.PRODUCTID AND B.PRICE_ID = C.PRICE_ID GROUP BY A.PRODUCTID";
	public static final String B2C_PRICE = "SELECT MIN(t.BRANDNAME) AS BRANDNAME,t.CASHHQJ AS CASHHQJ,t.PRODTYPE AS PRODTYPE,t.DEFININGVALUE AS DEFININGVALUE,MAX(t.SUBBRANDNAME) AS SUBBRANDNAME,t.brandid AS BRANDID,t.SKUID AS SKUID,t.dealer_price AS DEALER_PRICE,t.retail_price AS RETAIL_PRICE FROM ( "+
                                           " SELECT C.UNIT_PRICE AS BRANDNAME,C.cash_hqj AS CASHHQJ,A.prod_type AS PRODTYPE,C.bestoay_price AS DEFININGVALUE,C.DOMESTIC_PRICE AS SUBBRANDNAME,A.PRODUCTID AS BRANDID, "+
                                           " B.SKU_ID AS SKUID,c.dealer_price,c.retail_price FROM D_PRODUCT A, D_PRODUCT_SKU B, D_PRODUCT_PRICE C WHERE A.PRODUCTID = B.PRODUCTID AND B.PRICE_ID = C.PRICE_ID and (A.b2b_istate=1 or A.b2c_istate=1) ORDER BY C.UNIT_PRICE) t "+
                                           " GROUP BY t.BRANDID";

	public static final String TB_MONEY_UNIT = "SELECT MONEY_UNIT_SYMBOLS AS BRANDNAME,RATE AS SUBBRANDNAME,ID AS BRANDID FROM TB_MONEY_UNIT";

	//public static final String TC_MEASURE = "SELECT CNAME AS BRANDNAME FROM TC_MEASURE WHERE MEASUREID =  ? ";

	//public static final String SKU_STOCK = "SELECT PID AS BRANDID,SUM(T.QTY-T.PRE_SUB_QTY-T.LOCK_QTY) AS BRANDNAME FROM SKU_STOCK T GROUP BY T.PID ";
	public static final String SKU_STOCK = "select  t2.PID AS BRANDID,SUM(T2.QTY-T2.PRE_SUB_QTY-T2.LOCK_QTY) AS BRANDNAME from (" +
                                           " select t.pid,t.qty,t.pre_sub_qty,t.lock_qty from sku_stock t where t.warehouse_code !=2"+
                                           " union all select t1.pid,t1.qty,t1.pre_sub_qty,t1.lock_qty from sku_stock_to_customer t1"+
                                           " ) t2 where (T2.QTY-T2.PRE_SUB_QTY-T2.LOCK_QTY)>0 group by t2.pid";
	
	/*public static final String PRO_SALES = "SELECT PID ,SKU_QTY AS QTY FROM C_GOODS_QTY";*/
	
	public static final String STOCK="select PID AS BRANDID FROM SKU_STOCK WHERE (QTY-PRE_SUB_QTY-LOCK_QTY)>0";
	
	public static final String STOCK2="select PID AS BRANDID FROM SKU_STOCK WHERE (QTY-PRE_SUB_QTY-LOCK_QTY)=0";
	
	public static final String PRO_SALES = "SELECT PID , QTY FROM SKU_STOCK";
	
	public static final String SQL_PROVINCE="SELECT provinceid AS  provinceId,provincename AS provinceName,provincenameen AS provinceNameEn,region_id AS regionId,sortval AS sortVal FROM tb_agent_province";

	public static final String SQL_CITY="SELECT cityid AS  cityId,cityname AS  cityName,POSTCODE AS  postCode,ZONECODE AS  zoneCode,PROVINCEID AS  provinceId,city AS city FROM tb_agent_city;";
	
	public static final String SQL_COUNTRY="SELECT COUNTYID AS  countyId,COUNTYNAME AS  countyName,CITYID AS  cityId,PROVINCEID ASprovinceId,COUNTYNAMEEN AS  countyNameEn FROM  tb_agent_county;";
	
	public static final String SQL_TESECTORS="SELECT sectorcode AS sectorCode,sectorType AS sectorType,sectorname AS sectorName,sectorstatus AS sectorStatus FROM  te_sectors;";
	
	public static final String SQL_SUPPLIERDETAIL="SELECT id AS id,supplier_id AS supplierId,name_jc AS nameJC,company_store_logo AS companyStoreLogo,logo_imgurl AS logoImgurl"
			+ ",contact AS contact,contact_tel AS contactTel,phone AS phone,jy_ts AS jyTS,jy_sj AS jySJ,location_type AS locationType"
			+ ",location_lat AS locationLat,location_lng AS locationLng,location_poiaddress AS locationPoiaddress,location_poiname AS locationPoiname,location_cityname AS locationCityname,status AS status FROM  s_supplier_offline_store;";
	
	public static final String SQL_SUPPLIERDETAILATTR="SELECT id AS id,store_id AS storeId,attr_type AS attrType,attr_name AS attrName,attr_value AS attrValue,"
			+ "memo AS memo,url AS url,sort AS sort,status AS status FROM  s_supplier_offlinestore_attr;";
}
