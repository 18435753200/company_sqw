package com.mall.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * . 字典常量.
 * 
 * @author Guofy
 * 
 */
public class Constants {
	/**
	 * . platform_session_id
	 */
	public static final String SESSION_ID = "platform_session_id";
	/**
	 * . MAMCACHEDTIME.失效时间
	 */
	public static final int MAMCACHEDTIME = 30 * 60;
	/**
	 * . COOKIETIME.
	 */
	public static final int COOKIETIME = -1;
	/**
	 * 资质图片
	 */
	public static final short PRODUCT_QUALIFICATION = 100;
	/**
	 * images.
	 */
	public static final String IMAGES = "images";
	/**
	 * . menus_list
	 */
	public static final String MENUS_LIST = "menus_list";
	/**
	 * . buttons_map
	 */
	public static final String BUTTONS_MAP = "buttons_map";
	/**
	 * . MENUS_MAP
	 */
	public static final String MENUS_MAP = "menus_map";
	/**
	 * . PLATFORM
	 */
	public static final String PLATFORM = "platform";
	/**
	 * . MENUS_URL_MAP
	 */
	public static String MENUS_URL_MAP = "menus_url_map";
	/**
	 * . FILESERVER1
	 */
	public static final String FILESERVER1 = "http://image01.zhongjumall.com/";
	/**
	 * 产品 原图尺寸带水印 存储到fdfs
	 */
	public static final String P0 = "http://image01.zhongjumall.com/p0/";
	/**
	 * 产品 200*200 存cache
	 */
	public static final String P1 = "http://image01.zhongjumall.com/p1/";
	/**
	 * 产品 200*200 存M1 B2C水印图
	 */
	public static final String M1 = "http://image01.zhongjumall.com/";
	/**
	 * 产品 340*340 存cache
	 */
	public static final String P2 = "http://image01.zhongjumall.com/p2/";
	/**
	 * 其他 原图无水印
	 */
	public static final String N0 = "http://image01.zhongjumall.com/n0/";

	public static final String ND = "http://image01.zhongjumall.com/";
	/**
	 * 长描 商品长描 存储到fdfs
	 */
	public static final String H0 = "http://image01.zhongjumall.com/h0/";
	/**
	 * . 图片服务器.
	 */
	public static final String FILESERVER2 = "Http://192.168.1.58/";
	// 测试使用的固定值 正式发布时可去掉 修改项目中警报的地方
	/**
	 * . platform_id
	 */
	public static final Long TESTPLATFORMID = 1l;
	/**
	 * . platform_name
	 */
	public static final String TESTPLATFORMNAME = "三斤萝卜";
	/**
	 * . addressId
	 */
	public static final Long TESTADDRESSID = 23l;
	/**
	 * excel行宽度120
	 */
	public static final Short HSSFSHEETROW_120_WIDTH = (short) (35.7 * 120);
	/**
	 * excel行宽度150
	 */
	public static final Short HSSFSHEETROW_150_WIDTH = (short) (35.7 * 150);
	/**
	 * excel行宽度240
	 */
	public static final Short HSSFSHEETROW_240_WIDTH = (short) (35.7 * 240);
	/**
	 * excel行宽度300
	 */
	public static final Short HSSFSHEETROW_300_WIDTH = (short) (35.7 * 300);

	/**
	 * excel第0行
	 */
	public static final Short HSSFSHEETROW_0 = 0;
	/**
	 * excel第一行
	 */
	public static final Short HSSFSHEETROW_1 = 1;
	/**
	 * excel第二行
	 */
	public static final Short HSSFSHEETROW_2 = 2;
	/**
	 * excel第三行
	 */
	public static final Short HSSFSHEETROW_3 = 3;
	/**
	 * excel第四行
	 */
	public static final Short HSSFSHEETROW_4 = 4;
	/**
	 * excel第五行
	 */
	public static final Short HSSFSHEETROW_5 = 5;
	/**
	 * excel第六行
	 */
	public static final Short HSSFSHEETROW_6 = 6;
	/**
	 * excel第七行
	 */
	public static final Short HSSFSHEETROW_7 = 7;
	/**
	 * excel第八行
	 */
	public static final Short HSSFSHEETROW_8 = 8;
	/**
	 * excel第九行
	 */
	public static final Short HSSFSHEETROW_9 = 9;

	/**
	 * 采购订单业务单类型
	 */
	public static final String BUSINESSTYPE101 = "101";
	/**
	 * 换货订单业务单类型
	 */
	public static final String BUSINESSTYPE102 = "102";

	// 默认值列表
	/**
	 * . DeafaultPageSize
	 */
	public static final Integer PAGESIZE = 10;
	/**
	 * . MAXPAGESIZE
	 */
	public static final Integer MAXPAGESIZE = 999999999;
	/**
	 * . DeafaultPage
	 */
	public static final Integer DEFAULTPAGE = 1;
	/**
	 * . DATE YEAR
	 */
	public static final Integer YEAR = 1999;
	/**
	 * . DeafaultFORMATDATE
	 */
	public static final Integer BUFFEREDSTREAM = 1024;
	/**
	 * . DeafaultFORMATDATE
	 */
	public static final String DEFAULTFORMATDATE = "yyyy-MM-dd";
	/**
	 * . DeafaultFORMATALLDATE
	 */
	public static final String DEFAULTDATE = "yyyy年MM月dd日 HH时mm分ss秒";
	/**
	 * 预处理常量字符串 -1
	 */
	public static final String DEFULTSTRING = "-1";

	// WOFE订单状态列表

	/**
	 * . WOFE卖家状态 待支付
	 */
	public static final Short STATUS1 = 1;

	/**
	 * . WOFE卖家状态 系统取消
	 */
	public static final Short STATUS100 = 100;
	/**
	 * . WOFE卖家状态 已收全款
	 */
	public static final Short STATUS21 = 21;
	/**
	 * . WOFE卖家状态 已经确认
	 */
	public static final Short STATUS31 = 31;
	/**
	 * . WOFE卖家状态 已经确认
	 */
	public static final Short STATUS32 = 32;
	/**
	 * . WOFE卖家状态 已经确认
	 */
	public static final Short STATUS33 = 33;
	/**
	 * . WOFE卖家状态 已经确认
	 */
	public static final Short STATUS34 = 34;
	/**
	 * . WOFE卖家状态 已经分配
	 */
	public static final Short STATUS41 = 41;
   	/** 货源种类 11.宁波海外直邮  12.宁波保税区发货   1.国内发货 21.韩国直邮*/
   	public static String PRODUCT_TYPE_OVERSEAS = "11";  //宁波海外直邮 	
   	public static String PRODUCT_TYPE_BONDED_AREA = "12";//宁波保税区发货		
   	public static String PRODUCT_TYPE_INLAND = "1";	//国内发货
   	public static String PRODUCT_TYPE_KOREA = "21";	//韩国直邮
   	
   	/** 货源种类 11.宁波海外直邮  12.宁波保税区发货   1.国内发货 21.韩国直邮 */
	public static short SUPPLY_TYPE_OVERSEAS = 11;   //宁波海外直邮	
   	public static short SUPPLY_TYPE_BONDED_AREA = 12;//宁波保税区发货			
   	public static short SUPPLY_TYPE_INLAND = 1;	//国内发货
   	public static short SUPPLY_TYPE_KOREA = 21;	//韩国直邮
   	
   	/** 港口 1 宁波 2郑州*/
   	public static String PORT_TYPE_0 = "0";//默认0
   	public static String PORT_TYPE_NB = "1";//宁波
   	public static String PORT_TYPE_ZZ = "2";//郑州
   	
   	public static String PORT_TYPE_NB_KG = "101";//宁波空港
   	public static String PORT_TYPE_NB_BL = "102";//宁波北伦
	/**
	 * . WOFE卖家状态 供应商已发货
	 */
	public static final Short STATUS51 = 51;
	/**
	 * . WOFE卖家状态 等待零售商交付余款
	 */
	public static final Short STATUS61 = 61;
	/**
	 * . WOFE卖家状态 余额已支付 等待发货
	 */
	public static final Short STATUS71 = 71;
	/**
	 * . WOFE卖家状态 已经发货 等待确认
	 */
	public static final Short STATUS81 = 81;
	/**
	 * . WOFE卖家状态 订单完成
	 */
	public static final Short STATUS91 = 91;
	/**
	 * . WOFE卖家状态 系统默认完成
	 */
	public static final Short STATUS101 = 101;
	// 预 数字常量
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM0 = 0;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM1 = 1;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM2 = 2;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM3 = 3;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM4 = 4;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM5 = 5;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM6 = 6;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM7 = 7;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM8 = 8;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM9 = 9;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM10 = 10;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM1000 = 1000;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM2000 = 2000;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUM10000 = 10000;
	/**
	 * . 预 数字常量Integer
	 */
	public static final Integer NUMMAX = 99999;
	/**
	 * . 预 数字常量 INT
	 */
	public static final int INT0 = 0;
	/**
	 * . 预 数字常量INT
	 */
	public static final int INT1 = 1;
	/**
	 * . 预 数字常量INT
	 */
	public static final int INT2 = 2;
	/**
	 * . 预 数字常量INT
	 */
	public static final int INT3 = 3;
	/**
	 * . 预 数字常量INT
	 */
	public static final int INT4 = 4;
	/**
	 * . 预 数字常量INT
	 */
	public static final int INT5 = 5;
	/**
	 * . 预 数字常量INT
	 */
	public static final int INT6 = 6;
	/**
	 * . 预 数字常量INT
	 */
	public static final int INT7 = 7;
	/**
	 * . 预 数字常量INT
	 */
	public static final int INT8 = 8;
	/**
	 * . 预 数字常量INT
	 */
	public static final int INT9 = 9;
	/**
	 * . 预 数字常量INT
	 */
	public static final int INT10 = 10;

	/**
	 * . 预 数字常量INT
	 */
	public static final int INT15 = 15;

	/**
	 * . 预 数字常量INT
	 */
	public static final int INT20 = 20;

	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT0 = 0;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT1 = 1;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT2 = 2;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT3 = 3;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT4 = 4;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT5 = 5;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT6 = 6;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT7 = 7;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT8 = 8;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT9 = 9;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT10 = 10;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT11 = 11;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT12 = 12;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT13 = 13;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT14 = 14;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT15 = 15;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT16 = 16;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT17 = 17;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT18 = 18;

	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT19 = 19;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT20 = 20;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT21 = 21;
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT22 = 22;

	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT23 = 23;
	
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT24 = 24;
	
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT26 = 26;
	
	/**
	 * . 预 数字常量Short
	 */
	public static final Short SHORT27 = 27;

	public static final Short SHORT28 = 28;
	public static final Short SHORT29 = 29;
	public static final Short SHORT30 = 30;
	public static final Short SHORT31 = 31;
	public static final Short SHORT32 = 32;
	public static final Short SHORT33 = 33;
	public static final Short SHORT34 = 34;
	public static final Short SHORT35 = 35;
	public static final Short SHORT36 = 36;
	public static final Short SHORT37 = 37;
	public static final Short SHORT38 = 38;
	public static final Short SHORT39 = 39;
	public static final Short SHORT40 = 40;
	public static final Short SHORT41 = 41;
	public static final Short SHORT42 = 42;
	public static final Short SHORT43 = 43;
	public static final Short SHORT44 = 44;
	public static final Short SHORT45 = 45;
	public static final Short SHORT46 = 46;
	public static final Short SHORT47 = 47;
	public static final Short SHORT48 = 48;
	public static final Short SHORT49 = 49;
	public static final Short SHORT50 = 50;
	public static final Short SHORT51 = 51;
	public static final Short SHORT52 = 52;
	public static final Short SHORT53 = 53;
	public static final Short SHORT54 = 54;
	public static final Short SHORT55 = 55;
	public static final Short SHORT56 = 56;
	public static final Short SHORT57 = 57;
	public static final Short SHORT58 = 58;
	public static final Short SHORT59 = 59;
	public static final Short SHORT60 = 60;
	public static final Short SHORT61 = 61;
	public static final Short SHORT62 = 62;
	public static final Short SHORT63 = 63;
	public static final Short SHORT64 = 64;
	public static final Short SHORT65 = 65;
	public static final Short SHORT66 = 66;
	public static final Short SHORT67 = 67;
	public static final Short SHORT68 = 68;
	public static final Short SHORT69 = 69;
	public static final Short SHORT70 = 70;
	public static final Short SHORT71 = 71;
	public static final Short SHORT72 = 72;
	public static final Short SHORT73 = 73;
	public static final Short SHORT74 = 74;
	public static final Short SHORT75 = 75;
	/**
	 * . 预 字符串常量String0
	 */
	public static final String STR0 = "0";

	/**
	 * . 预 字符串常量String1
	 */
	public static final String STR1 = "1";
	/**
	 * 常用数据-1 表示该字段无效 Tyep Long.
	 */
	public static final Long LONGINVALID = -1L;
	/**
	 * 常用数据-1 表示该字段无效 Tyep Int.
	 */
	public static final Integer INTEGERINVALID = -1;
	/**
	 * 图文详情
	 */
	public static final short PRODUCT_DESCRIPTIONS = 1;

	public static final short PRODUCT_APTITUDE = 100;
	/**
	 * json 处理代码,1 处理正常
	 */
	public static final String SUCCESS = "1";
	/**
	 * json 处理代码,0 处理异常
	 */
	public static final String ERROR = "0";
	/**
	 * json 处理代码,-1 某数据为NULL
	 */
	public static final String SAMEISNULL = "-1";
	/**
	 * json 处理代码,-2 服务器异常
	 */
	public static final String SERVERISBESY = "-2";

	// use by qijj
	public static final String ISGENUINE_ZP = "正品";

	public static final String ISGENUINE_CP = "残品";

	public static final String PLEASE_SELECT = "请选择";
	public static final String MEMBER = "authority_id";
    public static final String AUTHORITY_KEY = "authroityKey";
    
    public static final Integer SPECIAL_APP_ACTIVITY_TAB = 1;
    public static final Integer SPECIAL_APP_ACTIVITY_PC = 2;
    
	//订单类型B2B
	public static final String B2B ="B2B";
	//订单类型B2C
	public static final String B2C ="B2C";
	public static final String ORDERFAULT = "5";
	
	/**
	 * 
	 */
	public static final String IMAGES_VIEW1 = "Http://image01.zhongjumall.com/p1/";
	/**
	 * 
	 */
	public static final String IMAGES_VIEW2 = "Http://image01.zhongjumall.com/h0/";
	
	public static final Short SHORT25 = 25;
	
	//需要记录日志的功能
	public static Map<String,OptLogBean> OPT_ISLOG_MAP = new HashMap<String,OptLogBean>(); 
	
	public class FinalNumber{
		public static final short ONE = -1;
		
		public static final short ORDER_PAID = 21;
		
		public static final short ORDER_SHIPMENTS = 41;
		
		public static final short ORDER_PACKAGE = 81;
		
	}
	
    private static Map<Integer, String> OUTTYPE_MAP = new LinkedHashMap<Integer, String>();
    
    private static void initOutTypeMap(){
    	OUTTYPE_MAP.put(21, "兑兑碰");
    	OUTTYPE_MAP.put(22, "旺旺");
    	OUTTYPE_MAP.put(23, "沃美电影院");
    	OUTTYPE_MAP.put(24, "乐糖影城");
    	OUTTYPE_MAP.put(25, "中行易商优品");
    	OUTTYPE_MAP.put(26, "北燃5s店");
    	OUTTYPE_MAP.put(27, "港湾网");
    	OUTTYPE_MAP.put(28, "国商易购");
    	OUTTYPE_MAP.put(29, "北京晚报");
    	OUTTYPE_MAP.put(30, "思埠集团");
    	OUTTYPE_MAP.put(31, "百度糯米");
    	OUTTYPE_MAP.put(32, "美团");
    	OUTTYPE_MAP.put(33, "众聚商城抽奖");
    	OUTTYPE_MAP.put(40, "宁波海洋鑫海通达");
    	OUTTYPE_MAP.put(41, "供销集团");
    	OUTTYPE_MAP.put(42, "供销社客户");
    	OUTTYPE_MAP.put(43, "上海鉴致");
    	OUTTYPE_MAP.put(45, "江山汇（北京）");
    	OUTTYPE_MAP.put(46, "B2B销售");
    	OUTTYPE_MAP.put(47, "上海智添");
    }
    /** 出库单：销售出库-客户名称 */
    public static class OutType{
    	 public static Map<Integer, String> getOutTypeMap() {
    		 initOutTypeMap();
    		 return OUTTYPE_MAP;
    	 }
    }
}
