/**
 * 
 */
package com.mall.constant;

/**
 * @author zhengqiang.shi 2015年3月23日 上午11:37:22
 */
public final class CommonConstant {

	public static final String PATTERN = "B2C_WAP"; // 模式
	public static final String ORDER_STATUS = "ORDER_STATUS"; // 确认收货，去评价，返回
	public static final String RRG_UPFILE = "RRG_UPFILE"; // 退换货上传图片
	
	/** cps常量 */
	public static final String CPS_COOKIE_KEY = "CPS_WAP_INFO"; //cps cookie key
	
	/** common constant */
	public static final Integer MAX_AGE_ONE_MONTH = 30 * 24 * 60 * 60; // Cookie's one month
	
	public static final Integer MAX_AGE_ONE_DAY = 24 * 60 *60; // 24 hours
	
	public static final Integer ORIGIN_MAX_AGE = 2*60*60; // origin 2 hours
														  // lifecycle
	public static final String COOKIE_PATH = "/";   // The default general path
													// for Cookie
	
	public static final String SESSION_ID = "sqm_user_session_id";
	//public static final String SESSION_ID = "ccigmall_user_session_id";    // session
																		   // id
																		   // key
	
	public static final String KEY = "com.zhongjumall.customer.order.cart";
	//public static final String KEY = "com.mall.customer.order.cart";    // cookie
																			// key
																			// &
																			// redis
																			// key
	
	public static final Integer OUT_TIME_COOKIE = 5184000; // 两个月有效时间
	public static final String CUSTOMER_LOGIN = "customer_login"; // 登录
	public static final String SEND_MESSAGE = "com.mall.customer.sendMessage"; // 发送验证码
	public static final String SEND_MESSAGE_LOSE = "com.mall.customer.sendMessagelose";// 验证码失效
	public static final String SEND_REG_MESSAGE = "com.mall.customer.sendRegMessage"; // 发送验证码
	public static final String SEND_REG_MESSAGE_LOSE = "com.mall.customer.sendRegMessagelose";// 验证码失效
	// 订单来源的key
	public static final String ORDER_FORM_KEY = "c_r_";
	
	public static final String SHARE_USER_ID = "ccigmall_share_user_id";
	
	// 悦me渠道来源
	public static final String CHANNEL_FROM_VALUE_YUEME = "yueme";
	
	public static final String ORDER_FROM_WEIXIN = "weiXin";
	
	public static final Long ACCESS_MODE_PC = 1L; //pc渠道
	public static final Long ACCESS_MODE_ANDROID = 2L; //android渠道
	public static final Long ACCESS_MODE_WAP = 3L;//wap渠道
	public static final Long ACCESS_MODE_IOS = 4L; //ios渠道
	public static final Long ACCESS_MODE_YUEME = 8L;//悦ME渠道
	public static final Long ACCESS_MODE_BOC_BEIFEN = 9L;//中行北分
	public static final Long ACCESS_MODE_BESTPAY = 10L;//翼支付
	public static final Long ACCESS_MODE_ZHANGJU = 11L;//掌聚
	
	/**
	 * . memcached的有限日期
	 */
	//public static final Integer MEMCACHEDAGE =30*3600*24 ;//60 * 30
	
	//  修改为半小时
	public static final Integer MEMCACHEDAGE =1800;
	
	public static final Integer MEMCACHEDAGE_MESSAGE_LOSE = 60;
	/********************************************************************
	 * response code CODE_200: success
	 ********************************************************************/
	public static final String CODE_200 = "200";
	/**
	 * 订单每页显示的数量
	 */
	public static final int PAGE_SIZE = 6;

	/***************** 银行支付模式 *****************/
	/** 中国银联移动支付 */
	public final static short PAY_TYPE_UNION_MOBILEPAY = 6;
	/** 中国银联网银支付 */
	public final static short PAY_TYPE_UNION_NETTPAY = 7;
	/** 支付宝在线支付 */
	public final static short PAY_TYPE_ALIPAY_ONLINE = 8;
	/** 支付宝WAP支付 */
	public final static short PAY_TYPE_ALIPAY_WAP = 9;
	/** 支付宝Mobile(IOS Android插件)支付 */
	public final static short PAY_TYPE_ALIPAY_MOBILE = 12;
	/** 中国银联WAP网银支付 */
	public final static short PAY_TYPE_UNION_WAPTPAY = 10;
	/** 翼支付手机android app wap支付 */
	public final static short PAY_TYPE_BESTOAYPAY_MOBILE = 11;
	/** 翼支付手机客户端 */
	public final static short PAY_TYPE_BESTOAYPAY_CLIENT = 13;
	/** 支付平台为wap **/
	public final static short PAY_WAP_CHANNELNO = 4;
	/** 支付平台为andorid **/
	public final static short PAY_ANDROID_CHANNELNO = 1;
	/** 中国银行信用卡支付 */
	public final static short PAY_TYPE_BOC_NCPKJ = 16;
	
	public final static short PAY_TYPE_JD_WAP = 18;
	
	/**
	 * 数字常量 - 共通
	 */
	public final static int NUMBER_0 = 0;
	public final static int NUMBER_1 = 1;
	public final static int NUMBER_2 = 2;
	public final static int NUMBER_3 = 3;
	public final static int NUMBER_4 = 4;
	public final static int NUMBER_5 = 5;
	public final static int NUMBER_6 = 6;
	public final static int NUMBER_7 = 7;
	public final static int NUMBER_8 = 8;
	public final static int NUMBER_9 = 9;
	public final static int NUMBER_10 = 10;

	/**
	 * 订单状态
	 */
	public final static short ORDERT_STATUS_FINISHED = 91; // 已收货状态
	public final static short ORDERT_STATUS_RRG = 92; // 已收货（退换货标识）状态
	public static final String FILESERVER5 = "Http://image01.zhongjumall.com/h0/";
	
	/** 支付宝国际WAP支付*/
	public static String ALIPAY="aliPay";
	/** 支付宝国内WAP支付*/
	public static String ALIPAY_DIRECT="aliPayDirect";
	/** 翼支付国内WAP支付*/
	public static String BESTOAY_WAP_DIRECT="bestoayPayDirect";	
	/** 翼支付国内客户端支付*/
	public static String BESTOAY_CLIENT_DIRECT="bestoayClientPay";	
	/** 银联支付*/
	public static String UNIONPAY="unionPay";
	/** 中行跨境支付*/
	public static String BOC_CROSS_PAY="bocCrossPay";
	/** 中行B2C移动在线支付*/
	public static String BOC_Net_PAY="bocNetPay";
	/** 中行北分B2C移动在线支付*/
	public static String BOC_BF_Net_PAY="bocBfNetPay";
	/** 中行信用卡在线快捷支付 **/
	public static String NCPKJ_PAY = "bocNcpkjPay";
	/** 中行信用卡在线分期支付 **/
	public static String NCPFQ_PAY = "bocNcpfqPay";
	/** 微信WAP支付 **/
	public static String WEIXIN_WAP_PAY = "weiXinWapPay";
	/** 微信公众号支付 **/
	public static String WEIXIN_GZH_PAY = "weiXinGZHPay";
	/** 红旗卷支付 */
	public static String HQ_PAY = "hqPay";
	
	public static String HQQ_AND_MONEY_PAY = "hqqAndMoneyPay";
	
	public static String JD_PAY = "jdPay";
	
	public static final String ORIGIN_ZERO = "881";//0元购
	public static final String ORIGIN_HAPPY = "8";//欢购买一赠二
	public static final String ORIGIN_FLOWRATE = "883";//流量宝
	public static final String ORIGIN_EASYPAYMENT = "884";//易支付
	
	public static final String ORIGIN_COOKIE_NAME = "com.zhongjumall.m_customer_activity_origin_name";//活动来源cookie
	public static final String COOKIE_IPTV = "iptv";
	
	public static final int BOC_CROSS_SIGN_STATUS_INIT = 0;
	public static final int BOC_CROSS_SIGN_STATUS_CHECK = 2;
	public static final int BOC_CROSS_SIGN_STATUS_SUCCESS = 1;
	public static final int BOC_CROSS_SIGN_STATUS_FAIL = -1;
	
	public static final int BOC_CROSS_SIGN_STATUS_CHECK_NUMBER = 15;//中行跨境检查次数
	
	public static final int BOC_CROSS_SIGN_STATUS_INTERVAL_TIME = 2000;//2秒
	//订单来源为中行渠道
	public static final String ORDER_SOURCE_CHINABANKCREDITCARD = "chinabankcreditcard";
	public static final String ORDER_SOURCE_BOCBEIFEN = "bocbeifen";
	public static final String ORDER_SOURCE_BOCNINGBO = "bocningbo";
	
	public static final String ACCOUNT_TYPE_FHK = "1";
	public static final String ACCOUNT_TYPE_NB = "2";
	public static final String ACCOUNT_TYPE_XGX = "3";
	public static final String ACCOUNT_TYPE_ZY = "4";
	
	public static final String WEIXIN_TOKEN = "1dc3d365c68a468cba5d37fd3f09bc3b";
	
	public static final String WEIXIN_ACCESS_TOKEN = "WEIXIN_ACCESS_TOKEN";
	public static final Integer WEIXIN_ACCESS_TOKEN_VAILED_TIME =2*3600;//两小时
	public static final String WEIXIN_JSAPI_TICKET = "WEIXIN_JSAPI_TICKET";
	public static final Integer WEIXIN_JSAPI_TICKET_VAILED_TIME =2*3600;//两小时
	
	public static final String WEIXIN_JSAPI_OPENID = "WEIXIN_JSAPI_OPENID";
	public static final Integer WEIXIN_JSAPI_OPENID_VAILED_TIME =7*3600*24;
	public static final String WEIXIN_JSAPI_CODE = "WEIXIN_JSAPI_CODE";
	public static final Integer WEIXIN_JSAPI_CODE_VAILED_TIME =7*3600*24;
	
	public static final String WEIXIN_PAY_URL = "";
	
	public static class PayMsg{
		public final static String SUCCESS = "SUCCESS";
   		public final static String FAIL = "FAIL";
	}
}
