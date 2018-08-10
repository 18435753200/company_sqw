package com.mall.utils;

import java.math.BigDecimal;

public class Constants {
	/**
	 * . SESSION_ID
	 */
	public static final String SESSION_ID = "retailer_session_id";

	public static final String FILESERVER1 = "Http://image01.zhongjumall.com/";
	// public static final String FILESERVER1 = "Http://192.168.1.58/";

	public static final String FILESERVER2 = "Http://image01.zhongjumall.com/";
	// public static final String FILESERVER2 = "Http://192.168.1.59/";

	public static final String FILESERVER0 = "Http://image01.zhongjumall.com/p0/";
	// 200*200
	public static final String FILESERVER3 = "Http://image01.zhongjumall.com/p1/";
	// public static final String FILESERVER3 = "Http://image01.zhongjumall.com/";

	// 340*340
	public static final String FILESERVER4 = "Http://image01.zhongjumall.com/p2/";
	// public static final String FILESERVER4 = "Http://image01.zhongjumall.com/";

	// 长描
	public static final String FILESERVER5 = "Http://image01.zhongjumall.com/h0/";
	// public static final String FILESERVER5 = "Http://image01.zhongjumall.com/";

	public static final String MENUS_LIST = "menus_list";

	public static final String MENUS_MAP = "menus_map";

	public static final String RETAILER = "retailer";
	// 普通分页大小
	public static final Integer PAGESIZE = 5;
	// 订单分页大小
	public static final Integer PAYSIZE = 10;

	// 山沟列表分页大小
	public static final Integer PAGE = 10;

	public static final Integer ZERO = 0;

	public static final int intZERO = 0;

	public static final int intONE = 1;

	public static final Integer ONE = 1;

	public static final Integer TWO = 2;

	public static final Short THREE = 3;

	public static final Integer YIBAI = 100;

	public static final String KEY = "com.mall.retailer.order.cart";

	// provinceId+","+cityId+","+districtId+","+areaName;

	public static final String AREAIDKEY = "com.zhongjumall.customer.cart.areaId";
	// public static final String AREAIDKEY = "com.mall.customer.cart.areaId";

	public static final String DEFAULTAREAIDVALUE = "18,424,2977,北京北京市朝阳区";

	public static final String YUEME_COOKIES_VALUE = "yueme";
	public static final String YUEME_COOKIES_KEY = "c_r_";

	// 用户来源的cookies key
	public static final String USERORIGIN_COOKIES_KEY = "channel_origin";
	// 订单来源的cookies key
	public static final String ORDERORIGIN_COOKIES_KEY = "c_r_";

	public static final Long INITIALIZTION = 0L;

	public static final Integer INIT = 0;

	public static final Double totalWeight = (double) 0;

	public static final String CARTKEY = "com.mall.retailer.order.cart.submit";

	public static final Integer MAX_BUY_QTY = 100;

	// 两个月有效时间
	public static final Integer OUT_TIME_COOKIE = 5184000;

	// 单位净重运费
	public static final BigDecimal freightOne = new BigDecimal(3);

	// 是否为wofe平台创建用户，默认为 0 非wofe用户
	public static final Integer iswofeCreate = 0;

	/*------------订单状态常量------------- */
	public static final short ORDER_STATUS_SUOYOUDINGDAN = 0;

	public static final short ORDER_STATUS_QUFUDINGJIN = 1;

	public static final short ORDER_STATUS_YIFUSHOUKUAN = 21;

	public static final short ORDER_STATUS_SHOUKUANQUEREN = 31;

	public static final short ORDER_STATUS_YIHEDAN = 32;

	public static final short ORDER_STATUS_YIFENPEI = 33;

	public static final short ORDER_STATUS_YIXIADAN = 34;

	public static final short ORDER_STATUS_DEALERDAIFAHUO = 41;

	public static final short ORDER_STATUS_SUPPLIERYIFAHUO = 51;

	public static final short ORDER_STATUS_DAIFUYUKUAN = 61;

	public static final short ORDER_STATUS_QUFUYUKUAN = 71;

	public static final short ORDER_STATUS_DEALERYIFAHUO = 81;

	public static final short ORDER_STATUS_ORDERSUCCESS = 91;

	public static final short ORDER_STATUS_ORDERQUXIAO = 100;

	public static final short ORDER_STATUS_XITONGWANCHENG = 101;

	public static final short ORDER_STATUS_CANCLE = 111;
	/*------------订单状态常量------------- */

	// 列表页分页常量
	public static final int LIST_NUM = 20;

	/**
	 * 失效时间30*60.
	 */
	public static final int OUT_TIME_1800 = 1800;

	/**
	 * 公用常量 数字0.
	 */
	public static final int PUBLIC_STATIC_NUM_0 = 0;
	/**
	 * 公用常量 数字1.
	 */
	public static final int PUBLIC_STATIC_NUM_1 = 1;
	/**
	 * 公用常量 数字10.
	 */
	public static final int PUBLIC_STATIC_NUM_10 = 10;
	/**
	 * 公用常量 数字100.
	 */
	public static final int PUBLIC_STATIC_NUM_100 = 100;
	/**
	 * 公用常量 数字600.
	 */
	public static final int PUBLIC_STATIC_NUM_600 = 600;
	/**
	 * 公用常量 数字1000.
	 */
	public static final int PUBLIC_STATIC_NUM_1000 = 1000;

	/**
	 * 公用常量 数字1024.
	 */
	public static final int PUBLIC_STATIC_NUM_1024 = 1024;

	/**
	 * COOKIE_AGE_ONE_YEAR 登录完成 5年过期≈(永不过期).
	 */
	public static final Integer SESSION_AGE_ONE_YEAR = 157680000;

	public static final Integer MESSAGE_OUT_TIME = 60 * 30;

	public static final Integer LOSE_EFFICACY_TIME = 55;

	/**
	 * memcached 过期时间
	 */
	public static final Integer LOSE_EFFICACY_TIME_5_MIN = 5 * 60;
	public static final Integer LOSE_EFFICACY_TIME_60 = 60;

	/**
	 * channelNo //银行签约渠道 1:pc,2:pad
	 */
	public static final short channelNo = 2;

	public static final Short ShortONE = 1;

	public static final Short ShortTWO = 2;

	// 请先登录
	public static final String TO_LOGIN = "<script language=javascript>window.ccig.doWhenException(1)</script>";

	// 无权限访问
	public static final String NO_PURVIEW = "<script language=javascript>window.ccig.doWhenException(0)</script>";

	// <<<<<<----登录用到常量

	/**
	 * RETAILER_STATUS_UNCHECK 商户或用户已注册尚未审核.
	 */
	public static final Integer RETAILER_STATUS_UNCHECK = 0;
	/**
	 * RETAILER_STATUS_CHECKED 商户或用户注册并通过审核.
	 */
	public static final Integer RETAILER_STATUS_CHECKED = 1;
	/**
	 * RETAILER_STATUS_CHECKFAIL 商户或用户注册 审核未通过.
	 */
	public static final Integer RETAILER_STATUS_CHECKFAIL = 2;
	/**
	 * RETAILER_STATUS_DELETED 商户用户已被删除禁用 不能登陆.
	 */
	public static final Integer RETAILER_STATUS_DELETED = 3;
	/**
	 * COOKIE_AGE_ONE_YEAR 登录完成永不过期.
	 */
	public static final int SESSION_AGE_FOREVER = 0;

	/**
	 * PAY_STATUS_UNCHECK 设备付款转账 尚未审核.
	 */
	public static final Integer PAY_STATUS_UNCHECK = 0;
	/**
	 * PAY_STATUS_CHECKED 设备付款转账 通过审核.
	 */
	public static final Integer PAY_STATUS_CHECKED = 1;
	/**
	 * PAY_STATUS_CHECKFAIL 设备付款转账 审核未通过.
	 */
	public static final Integer PAY_STATUS_CHECKFAIL = -1;
	/**
	 * DEVICE_TRYTIME设备试用期.
	 */
	public static final Long DEVICE_TRYTIME = 180 * 24 * 3600 * 1000L;
	/**
	 * DEVICE_TRYTIME设备试用期.
	 */
	public static final String CAPTCHA = "captcha";

	public static final String FRONT_URL = "FRONTURL";

	public static final String OLD = "OLD";

	public static final Integer CMS_TARGET = 2;

	public static final Integer B2C_FLAG = 4;

	public static final String ACCESSMODE_APP = "APP";// 列表页显示促销价专用——访问方式APP

	public static final String ACCESSMODE_WAP = "WAP";// 列表页显示促销价专用——访问方式WAP

	public static final String ACCESSMODE_PC = "PC";// 列表页显示促销价专用——访问方式PC

	// / 登录用到常量-------->>>>>
	/***************** 银行支付模式 *****************/
	public static class BankPayModel {
		/** 中国银联支付 */
		public final static short PAY_TYPE_UNIONPAY = 1;
		/** 中行企业直付 */
		public final static short PAY_TYPE_BOC_B2B_PAY = 2;
		/** 中行个人协议支付 */
		public final static short PAY_TYPE_BOC_B2C_AGREEMENTPAY = 3;
		/** 中行网银支付 */
		public final static short PAY_TYPE_BOC_B2C_ENTPAY = 4;
		/** 中行企业网银支付 */
		public final static short PAY_TYPE_BOC_B2B_ENTPAY = 5;
		/** 中国银联移动支付 */
		public final static short PAY_TYPE_UNION_MOBILE_ENTPAY = 6;
	}

	public static class SqwAccountRecordService {
		// 红旗券
		public final static int HQ_BALANCE_TYPE = 1;
		// 现金额度账户
		public final static int MONEY_BALANCE_TYPE = 2;
		// 自营
		public final static String SALE_SELF = "1";
		// 首次购买的产品
		public final static String FIRST_BUY_PRODUCT_ID = "005009";
		// 支付密码不正确，你还可以输入1次
		public final static String PWD_ERROR = "0";
		// 成功
		public final static String PWD_SUCCESS = "1";
		// 请设置支付密码
		public final static String NO_PWD = "2";
		// 支付密码错误已达上限，请修改密码
		public final static String PWD_ERROR_FOR_UPDATE = "3";
		// 操作太频繁，请稍后重试
		public final static String PWD_INPUT_TOO_MANY = "4";
		// 分红类型现金
		public final static int MONEY_PAY = 1;
		// 分红类型红旗券
		public final static int HQ_PAY = 2;
		// 分红类型现金+红旗券
		public final static int HQ_MONEY_PAY = 3;

		public final static double DIVIDE_ONE_HUNDRED = 0.01;
		// app微信支付
		public final static String WEIXIN_WAY = "1";
		// 微信支付
		public final static String WEIXIN_APP_WAY = "2";
		// 红旗券支付
		public final static String HQPAY_WAY = "3";
		// 京东支付
		public final static String JDPAY_WAY = "4";

		// 支付支付
		public final static String ALIPAY_WAY = "6";

		// 支付密码不存在
		public final static String PWD_NOT_EXIST = "0";
		// 支付密码存在
		public final static String PWD_EXIST = "1";
	/*	// 红旗宝skuid
		public final static String RECIVE_MONEY_SYS_SKU_ID = "149049896422752058";*/

		// 限定现金skuid
		public final static String RECIVE_MONEY_SHOP_SKU_ID = "149049896422752058";   

	}

	public static class CmsFrameLocation {

		/** 轮播图 */
		public final static String FRAME_LB = "pad-lb";
		/** 促销 */
		public final static String FRAME_NAME = "pad-name";
		/** 专题 */
		public final static String FRAME_FLOOR = "pad-floor";
	}

	public static final Integer HQQ = 1;// 红旗券账户

	public static final Integer XJ = 2;// 现在账户

	public static final Double SECURITY_AMOUNT = 10000D;// 安全金额标准，大于或者等于需要手机验证码

}
