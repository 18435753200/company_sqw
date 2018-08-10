package com.mall.search.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量集合
 * @author DOUBELL 
 *
 */
public final class Constant {

	public static final int JEDIS_CATELOG_LOES_TIME = 60*60*24;
	
	public static final int JEDIS_PROATTR_LOES_TIME = 60*60*24*30;
	
	public static final String FIELD_CT = "ct";
	
	public static final String FIELD_BRAND_NAME = "brandName";
	
	public static final String FIELD_TAG_NAME="tagName";
	
	public static final String FIELD_SUPPLY_NAME = "supplier_name";
	
	public static final String FIELD_B2C_SUPPLY = "b2cSupply";
	
	public static final String FIELD_PID = "pid";
	
	public static final String FIELD_COMPANY_REGION = "company_region";
	
	public static final String FIELD_B2B_DISPAT = "b2b_dispat";
	
	public static final String FIELD_CYID = "cyid";
	
	public static final String FIELD_SID = "sid";
	
	public static final String FIELD_MSP = "msp";
	
	public static final String FIELD_UNIT_PRICE = "unit_price";
	
	public static final String THIRTY_ONE = "31";
	
	public static final String STR_COUNTRY_DELIVERY = "国内发货";
	
	public static final String STR_BONDZONE_DELIVERY = "保税区发货";
	
	public static final String STR_DIRECT_DELIVERY = "海外直邮";
	
	public static final String STR_TM_DELIVERY = "海外预售";
	
	public static final String STR_POP_DELIVERY = "国际发货";
	
	public static final String STR_COUNTRY_ORIGIN = "企业";
	
	public static final String STR_SUPPLIER_ORIGIN = "企业";
	
	public static final String STR_BRANDNAME = "品牌";
	
	public static final String STR_PRICE = "价格";
	
	public static final String STR_SUPPLY = "货源";
	
	public static final String PROMOTION_PRICE_PC = "promotion_price.pcPrice";
	
	public static final String PROMOTION_PRICE_WAP = "promotion_price.wapPrice";
	
	public static final String PROMOTION_PRICE_APP = "promotion_price.appPrice";
	
	public static final String PROMOTION_PRICE_BESTPAY = "promotion_price.bestpayPrice";
	
	public static final String PROMOTION_PRICE_ZJ = "promotion_price.zjPrice";
	
	public static final String PROMOTION_PRICE_YM = "promotion_price.ymPrice";
	
	public static final String PROMOTION_PRICE_BF = "promotion_price.bfPrice";
	
	public static final String PROMOTION_PRICE_NB = "promotion_price.nbPrice";
	
	public static final String PRICE_PC = "pcPrice";
	
	public static final String PRICE_WAP = "wapPrice";
	
	public static final String PRICE_APP = "appPrice";
	
	public static final String PRICE_BESTPAY = "bestpayPrice";
	
	public static final String PRICE_ZJ = "zjPrice";
	
	public static final String PRICE_YM = "ymPrice";
	
	public static final String PRICE_BF = "bfPrice";
	
	public static final String PRICE_NB = "nbPrice";
	
    public static final String CHANNEL_APP = "2";//鑫世界安卓渠道   IOS  ANDROID  促销价格一样
	
	public static final String CHANNEL_PC = "1";//鑫世界PC渠道
	
	public static final String CHANNEL_WAP = "3";//鑫世界WAP渠道	

	public static final String CHANNEL_YUEME = "8";//悦ME渠道
	
	public static final String CHANNEL_BOC_BEIFEN = "9";//中行北分渠道
	
	public static final String CHANNEL_BESTPAY = "10";//翼支付渠道
	
	public static final String CHANNEL_ZHANGJU = "11";//掌聚渠道
	
	public static final String CHANNEL_BOC_NINGBO = "12";//中行宁波分行渠道
	
	public static final String SPECIAL_SUPPLY_TWELVE = "12"; //特殊使用supply= 12
	
	public static final String CHANNEL_WAP_B2B = "13";//鑫世界b2b WAP渠道

	/**
	 * 排序规则(按照老业务)
	 */
	public static final Map<Integer,String> SORT_TYPES = new HashMap<Integer,String>(){
	
		private static final long serialVersionUID = 5130752369611215033L;

	{	
		this.put(1, "b2c_opt");//上架时间降序
		this.put(2, "b2c_opt");//上架时间升序
		this.put(3, "unit_price");//b2c购买价格降序
		this.put(4, "unit_price");//b2c购买价格升序
		this.put(5, "inventory");//库存降序
		this.put(6, "inventory");//库存升序
		this.put(7, "salesVolume");//销量降序
		this.put(8, "salesVolume");//销量升序
	}};

}
