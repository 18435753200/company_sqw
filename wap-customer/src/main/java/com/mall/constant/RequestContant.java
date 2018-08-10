/**
 * 
 */
package com.mall.constant;

/**
 * @author zhengqiang.shi 2015年3月23日 上午11:27:24
 */
public class RequestContant {

	/********************************************************************
	 * 共通
	 ********************************************************************/
	public static final String REDIRECT = "redirect:";

	/********************************************************************
	 * CART:父requestMapping CART_ADD:添加购物车 CART_QTY:获取购物车商品总数 CART_INDEX : 购物车首页
	 * CART_UPDATE：更新购买数量 CART_DELETE ： 购物车删除商品 CART_CALCULATE ： 购物车计算金额
	 * CART_SETTLEMENT:结算
	 ********************************************************************/
	public static final String CART = "/cart";
	public static final String CART_ADD = "/addItem";
	public static final String CART_QTY = "/qty";
	public static final String CART_INDEX = "/index";
	public static final String CART_UPDATE = "/update";
	public static final String CART_DELETE = "/delete";
	public static final String CART_CALCULATE = "/calculate";
	public static final String CART_CHANGE_SELECT = "/changeSelect";
	public static final String CART_SETTLEMENT = "/settlement";
	public static final String DIRECT_BUY = "/directBuy";
	public static final String CHECK_PRICE = "/checkPrice";
	public static final String CHECK_USER_ACCOUNT_BALANCE = "/checkUserAccountBalance";

	/********************************************************************
	 * ORDER:父requestMapping ORDER_SUBMIT_CHECK:提交订单前检查 ORDER_SUBMIT:订单提交
	 * 
	 * ORDER_COUPONS:优惠券父requestMapping ORDER_FIND_COUPONS:获取可用优惠券
	 ********************************************************************/
	public static final String ORDER = "/order";
	public static final String ORDER_SUBMIT_CHECK = "/check";
	public static final String ORDER_SUBMIT = "/submit";
	public static final String ORDER_SUBMIT_DIRECTBUY = "/submitDirectBuy";
	public static final String ORDER_SUBMIT_RECHARGE = "/submit/recharge";

	public static final String ORDER_COUPONS = "/coupons";
	public static final String ORDER_FIND_COUPONS = "/find";
	public static final String ORDER_FIND_COUPONS_QTY = "/findQty";
	public static final String ORDER_GET_DISCOUNT = "/getDiscount";

	/*******************************************************************
	 * 我的众聚猫
	 *******************************************************************/
	public static final String CUS_TO_MYCCIG = "/toMy";
	/**
	 * 订单
	 */
	public static final String CUS_ORDER = "/cusOrder"; //
	public static final String CUS_MY_RECIVE_MONEY = "/toMyReciveMoney";
	public static final String CUS_MY_RECIVE_MONEY_N = "/toweaithCenter";
	public static final String CUS_MY_SHENGXIAN = "/toMyShengXian";
	public static final String CUS_RECIVE_MONEY_SYS_PAY_VALIDATE = "/reciveMoneySysPayValidate";
	public static final String CUS_SHENGXIAN_VALIDATE = "/shengXianValidate";
	public static final String CUS_RECIVE_MONEY_SYS_PAY = "/reciveMoneySysPay";
	public static final String CUS_SHENGXIAN_SYS_PAY = "/shengXianSysPay";
	public static final String CUS_MY_RECIVE_MONEY_ITEM = "/toMyReciveMoneyItem";
	public static final String CUS_MY_SHENGXIAN_ITEM = "/toMyShengXianItem";
	public static final String CUS_MY_JIATING_ITEM = "/toMyJiaTingItem";
	public static final String CUS_MY_CUSBUS_ITEM = "/toMyCusBusItem";
	public static final String CUS_MY_ALL_ORDER = "/toMyAllOrder"; // 获取所有订单信息
	public static final String CUS_MY_ALL_SUPPLIER_ORDER = "/toMyAllSupplierOrder"; // 获取所有商家订单信息
	public static final String CUS_FIND_MORE_ORDER = "/findMoreOrder"; // 获取更多订单信息
	public static final String CUS_ORDER_DETAIL = "/orderDetail"; // 获取更多订单信息
	public static final String CUS_ORDER_TRACE = "/trackOrder"; // 查询物流
	public static final String CUS_ORDER_CANCLE = "/cancleOrder"; // 取消订单
	public static final String CUS_ORDER_USER_CANCEL_ORDER = "/userCancelOrder"; // 取消订单
	public static final String CUS_ORDER_CANCLE_PAGE = "/cancleOrderPage"; // 取消订单
	public static final String CUS_ORDER_DEL = "/delOrder"; // 删除订单
	public static final String CUS_ORDER_TO_PAY = "/toPay"; // 去支付
	public static final String CUS_ORDER_TO_WEIXIN_PAY = "/toWeiXinPay"; // 去支付
	public static final String CUS_ORDER_PAY = "/pay"; // 支付
	public static final String CUS_ORDER_CONFIRM = "/confirmOrder"; // 确认订单
	public static final String CUS_ORDER_RECEIPT_SUCCESS = "/receiptSuccess"; // 确认订单
	public static final String CUS_CHINAUMSORDER = "/chinaumsOrder";		// 银联商务下单

	//
	/**
	 * 支付
	 */
	public static final String ORDER_PAY = "/orderPay"; // 支付
	public static final String ORDER_PAY_INFO = "/payInfo"; // 获取支付信息(字符串拼接)
	public static final String HQ_ORDER_PAY_INFO = "/hqPayInfo"; // 红旗券支付信息(字符串拼接)
	public static final String ORDER_BOCC_PAY_INFO_APP = "/boccPayInfoApp"; // 获取支付信息(字符串拼接)app使用
	public static final String ORDER_BOCC_PAY_INFO = "/boccPayInfo"; // 获取支付信息(字符串拼接)
	public static final String ORDER_PAY_INFO_MIDDLE = "/toPayMiddle"; // 获取支付信息(中间页form表单跳转)
	public static final String ORDER_PAY_INFO_APP = "/payInfo_app"; // 获取支付信息(android或IOS)
	public static final String TRADE_PWD_PAGE = "/tradePwdPage";
	public static final String TRADE_PWD_PAGE_RECIVE_SYS = "/tradePwdPageForReciveSys";
	public static final String TRADE_PWD_PAGE_SHENGXIAN = "/tradePwdPageForShengXian";
	public static final String CHECK_TRADE_PWD = "/checkTradePwd";
	// FIXME
	// 充值return_url回调
	public static final String RECHARGE_CALLBACK = "/recharge/callback";
	// 支付宝return_url回调
	public static final String ALI_RETURN_PAY_CALLBACK = "/aLiPay/return/wap/payResultBack";
	// 支付宝 notify_url
	public static final String ALI_NOTIFY_PAY_CALLBACK = "/aLiPay/notify/wap/payResultBack";
	// 支付宝-国内return_url回调
    public static final String ALI_DIRECT_RETURN_PAY_CALLBACK = "/aLiPay/direct/wap/return/payResultBack";
    // 支付宝-国内notify_url回调
    public static final String ALI_DIRECT_NOTIFY_PAY_CALLBACK = "/aLiPay/direct/wap/notify/payResultBack";
    
	// 京东m return_url回调
    public static final String JD_RETURN_PAY_CALLBACK = "/jdPay/direct/wap/return/payResultBack";
    // 京东m notify_url回调
    public static final String JD_NOTIFY_PAY_CALLBACK = "/jdPay/direct/wap/notify/payResultBack";
    
	// 支付宝手机（IOS Android插件）支付回调/orderPay/aLiPay/notify/mobile/payResultBack
	public static final String ALI_MOBILENOTIFY_PAY_CALLBACK = "/aLiPay/notify/mobile/payResultBack";
	public static final String ALI_INLAND_MOBILE_PAY_CALLBACK = "/aLiPay/notify/inland/mobile";
	// 微信APP支付异步通知
	public static final String WEIXIN_PAY_NOTIFY = "/weixin/notify";
	// 微信支付异步通知
	public static final String WEIXIN_GZH_PAY_NOTIFY = "/weixin/gzh/notify";
	// E支付回调URL
	public static final String E_BESTOAY_PAY_CALLBACK = "/bestoayPay/return/wap/payResultBack";
	// E支付返回商城
	public static final String E_BESTOAY_PAY_NOTIFY = "/bestoayPay/notify/wap/payResultBack";
	// 银联front_url回调
	public static final String UNION_FRONT_PAY_CALLBACK = "/unionPay/front/wap/payResultBack";
	// 银联 back_url
	public static final String UNION_BACK_PAY_CALLBACK = "/unionPay/back/wap/payResultBack";
	// 中行网银 back_url
	public static final String BOC_NET_BACK_PAY_CALLBACK = "/bocNetPay/back/wap/payResultBack";
	// 中行信用卡 back_url
	public static final String BOC_NCP_BACK_PAY_CALLBACK = "/bocNcpPay/back/wap/payResultBack";

	// 银联商务返回url
	public static final String CHINAUMS_RETURN_PAY_CALLBACK = "/chinaums/wap/return/payResultBack";
    // 银联商务回调url
	public static final String CHINAUMS_NOTIFY_PAY_CALLBACK = "/chinaums/wap/notify/payResultBack";

	
	/*****************中行跨境请求************************/
	public static final String CUS_PAY = "/cusPay";
	public static final String BOC_CROSS_PAY = "/bocCrossPay";//
	public static final String TO_BOC_CROSS_PAY = "/toBocCrossPay";//
	public static final String TO_BOC_CROSS_BORDER_SIGN = "/toBocCrossSign";//跳转签约页面
	public static final String BOC_CROSS_BORDER_SIGN = "/bocCrossSign";//签约
	public static final String CHECK_AUTH_CODE = "/checkAuthCode";//较验验证码
	public static final String SEND_MOBILE_CODE = "/sendMobileCode";//发送签约短信验证码
	public static final String CHOOSE_ACCOUNT = "/chooseAccount";//选择验证码
	public static final String AGREEMENT = "/agreement";//选择验证码
	/*****************支付对账************************/
	public static final String TO_ACCOUNT_CHECK = "/toAccountCheck";//
	
	/*****************中行跨境回调************************/
	public static final String BOC_CROSS_BORDER = "/bocCrossBorder";
	public static final String SIGNING_CALLBACK = "/signingNotify";
	public static final String AUTH_SINGING_CALLBACK = "/authSigningNotify";
	public static final String CHECK_SIGNING_CALLBACK = "/checkSigningNotify";
	public static final String BOC_CROSS_PAY_CALLBACK = "/bocCrossBorder/payNotify";
	public static final String BOC_CROSS_CHECK_PAY_CALLBACK = "/bocCrossBorder/checkPayNotify";
	public static final String BOC_CROSS_REFUND_CALLBACK = "/bocCrossBorder/refundNotify";
	public static final String BOC_CROSS_ACCOUNT_CHECK_CALLBACK = "/bocCrossBorder/accountCheckNotify";
	
	/*****************微信支付************************/
	public static final String WEIXIN_AUTH = "/weiXinAuth";
	public static final String TO_WEIXIN_GZH_PAY = "/toWeiXinGZHPay";
	public static final String TO_WEIXIN_GZH_PAY_TEST = "/toWeiXinGZHPayTest";
	public static final String AUTH_WEIXIN_GZH_PAY = "/authWeiXinGZHPay";
	public static final String WEIXIN_AUTH_CODE = "/getAuthCode";
	public static final String WEIXIN_TOKEN = "/toWeiXinGZHToken";
	public static final String QUERY_WEIXIN_GZH_PAY = "/queryWeiXinPay";
	
	/**
	 * 收货人地址
	 */
	public static final String CUS_ADDRESS = "/cusAddress"; // controller
	public static final String CUS_ADD_ADDRESS = "/addAddress"; // 添加
	public static final String CUS_TO_ADD_ADDRESS = "/toAddAddress"; // 跳转
	public static final String CUS_FIND_ALL_ADDRESS = "/findAllAddress"; // 查询所有
	public static final String CUS_FIND_ADDRESS = "/findAddress"; // 查询单个
	public static final String CUS_DELETE_ADDRESS = "/deleteAddress"; // 删除单个
	public static final String CUS_UPDATE_ADDRESS = "/updateAddress"; // 修改
	public static final String CUS_DEFAULT_ADDRESS = "/defaultAddress"; // 默认地址
	public static final String CUS_TO_UPDATE_ADDRESS = "/toUpdateAddress"; // 请修改用户地址
	public static final String CUS_GET_ADDRESS_BY_ID = "/getAddressById"; // 根据id获取地址
	
	public static final String CUS_FIND_ALL_MY_ADDRESS = "/findAllMyAddress"; // 个人中心查询所有
	public static final String CUS_TO_ADD_MY_ADDRESS = "/toAddMyAddress"; // 个人中心跳转添加
	public static final String CUS_TO_UPDATE_MY_ADDRESS = "/toUpdateMyAddress"; // 跳转修改地址
	public static final String CUS_GET_PROVINCE = "/getProvince"; // 获取省份
	public static final String CUS_GET_CITY = "/getCity"; // 获取城市
	public static final String CUS_GET_COUNTRY = "/getCountry"; // 获取乡镇

	/**
	 * 问题反馈
	 */
	public static final String CUS_SUGGEST = "/suggest";
	public static final String CUS_SUGGEST_INIT = "/init";
	public static final String CUS_SUGGEST_FIND = "/find";
	public static final String CUS_SUGGEST_FINDBYID = "/findById";
	public static final String CUS_SUGGEST_FIND_MORE = "/findmore";
	public static final String CUS_SUGGEST_INITSUGGEST = "/initSuggest";
	public static final String CUS_SUGGEST_ADD = "/add";
	public static final String CUS_TOHELPCENTER = "/toHelpCenter";

	/**
	 * 评论
	 */
	public static final String CUS_COMMENT = "/cusComment";
	public static final String CUS_COMMENT_TO_LIST = "/toCusCommentList"; // 跳转评论列表
	public static final String CUS_COMMENT_TO = "/toCusComment"; // 跳转评论也
	public static final String CUS_COMMENT_ADD = "/addCusComment"; // 评论
	public static final String COMMENT = "/comment"; // 评论
	public static final String GO = "/go"; // 去
	public static final String COMMIT = "/commit"; // 提交
	public static final String CHECK = "/check"; // 检查

	/**
	 * 优惠券
	 */
	public static final String CUS_COUPON = "/cusCoupon"; //
	public static final String CUS_COUPON_GET = "/getCouponByType"; // 根据类型选取优惠券
	public static final String CUS_COUPON_RULE = "/couponRule"; // 优惠券使用规则

	/**
	 * 个人信息
	 */
	public static final String CUS_INFO = "/cusInfo"; // 获取个人信息
	public static final String CUS_UPDATE_INFO = "/updateCusInfo"; // 修改个人信息
	public static final String CUS_TO_UPDATE_MOBILE = "/toUpdateCusMobile";// 跳转手机号码验证页面
	public static final String CUS_RE_TO_UPDATE_MOBILE = "/reToUpdateCusMobile";// 跳转修改手机号码页面
	public static final String CUS_UPDATE_MOBILE = "/updateCusMobile";// 修改手机号
	public static final String CUS_INFO_GETCODE = "/getCusInfoCode"; // 获取个人信息的验证码
	public static final String CUS_INFO_GETCODE_MOBILE = "/getCusInfoCodeMobile"; // 修改用户手机的验证码
	public static final String CUS_TO_VERIFY = "/toVerify"; // 去实名认证
	public static final String CUS_VERIFY = "/verify"; // 实名认证
	public static final String CUS_CHANGE_NICKNAME = "/changeNickName"; // 修改昵称
	public static final String CUS_CHANGE_EMAIL = "/changeEmail"; // 修改昵称
	public static final String CUS_CHANGE_IDCARD = "/changeIdCard"; // 修改身份证号
	public static final String CUS_CHANGE_WEIXIN = "/changeWeixin"; // 修改微信
	public static final String CUS_CHANGE_EDUCATIONAL = "/changeEducational"; // 修改学历
	public static final String CUS_CLIENT_INDEX = "/clientIndex"; // 客户端
	public static final String CUS_DOWNLOAD_ANDROID = "/downloadAndroid"; // 下载android

	/**
	 * 客服中心
	 */
	public static final String CUS_SERVICE = "/cusService";
	public static final String CUS_KEFU = "/kf";
	public static final String CUS_KEFU_CONTACT = "/contact";
	public static final String CUS_KEFU_RRG = "/rrg";
	public static final String LIST = "/list";
	public static final String UPLOAD = "/upload";
	public static final String FILE = "/file";
	public static final String SINGLE_ORDER = "/SingleOrder";

	/*****************************************************************/
	/********************************************************
	 * 用户相关
	 */
	public static final String CUSTOMER = "/customer";
	public static final String CUSTOMER_TO_LOGIN = "/toLogin";
	public static final String CUSTOMER_TO_WXLOGIN = "/toWxLogin";
	public static final String CUSTOMER_TO_INPUT_CODE = "/toInputCode";
	public static final String CUSTOMER_TO_INPUT_PASS_CODE = "/toInputPassCode";

	public static final String CUSTOMER_SAVE_CUS = "/saveCustomer";
	public static final String CUSTOMER_SAVE_INVISIBLE = "/saveCustomerInvisible";
	public static final String CUSTOMER_WXLOGIN = "/wxlogin";
	public static final String CUSTOMER_LOGIN = "/login";
	public static final String CUSTOMER_TO_GETPASS = "/toGetpass";
	// 获取找回密码的验证码
	public static final String CUSTOMER_GET_CODE = "/toGetCode";
	// 获取注册密码的验证码
	public static final String CUSTOMER_GET_REG_CODE = "/toRegGetCode";
	// 校验获取密码的验证码
	public static final String CUSTOMER_VALID_CODE = "/validCode";
	// 校验注册的验证码
	public static final String CUSTOMER_VALID_REG_CODE = "/validRegCode";

	public static final String CUSTOMER_TO_REGISTER_SET_PASSWORD = "/toSetPassword";

	public static final String CUSTOMER_NEW_PASS = "/newPass";
	public static final String CUSTOMER_NEW_OLD_PASS = "/newOldPass";
	public static final String CUSTOMER_LOGOUT = "/logout";
	public static final String CUSTOMER_TO_REGISTER = "/toRegister";

	public static final String CUSTOMER_CHECK_REAL_NAME = "/checkUserRealName"; // 检查用户是否已经实名认证
	
	public static final String COUPON_FOR_CMCC = "/getCouponListForCMCC";//电信充值页面——获取可用优惠券列表

	public static final String WAP_CHANUAL_TYPE = "3";
	
	public static final String YUEME_CHANUAL_TYPE = "8";
	
	/**
	 * 系统通知
	 */
	public static final String SYS_NOTICE = "/notice";
	public static final String SYS_NOTICE_INFO = "/info";
	public static final String SYS_NOTICE_INFO_LIST = "/infolist";
	public static final String SYS_NOTICE_INFO_LIST_MORE = "/infolistmore";
	
	/**
	 * 财富中心
	 */
	public static final String CUS_WEALTH = "/wealth";
	public static final String CUS_WEALTH_INFO = "/info";
	public static final String CUS_WEALTH_FXSR = "/fxsr";
	public static final String CUS_WEALTH_NINFO = "/ninfo";
	public static final String CUS_WEALTH_VALIDATE = "/validate";
	public static final String CUS_WEALTH_VALIDATE_N = "/validate_n";
	public static final String CUS_WEALTH_TRANSFER = "/transfer";
	public static final String CUS_WEALTH_TRANSFER_N = "/transfer_n";
	public static final String CUS_WEALTH_REDTRANSFER = "/redTransfer";
	public static final String CUS_WEALTH_REDTRANSFER_N = "/redTransfer_n";
	public static final String CUS_WEALTH_APPLYCOUPONS = "/applyCoupons";
	public static final String CUS_WEALTH_APPLYCOUPONS_N = "/applyCoupons_n";
	public static final String CUS_WEALTH_TURNVOUCHER = "/turnVoucher";
	public static final String CUS_WEALTH_TURNVOUCHER_N = "/turnVoucher_n";
	public static final String CUS_WEALTH_SUPPLIERUSERS = "/supplierUsers";
	public static final String CUS_WEALTH_SUPPLIERUSERSMORE = "/supplierUsersMore";
	public static final String CUS_WEALTH_TOLISTBUSINESSPARTNER = "/tolistBusinessPartner";
	public static final String CUS_WEALTH_TOSUPPLIERUSERSHOUYIN = "/tosupplierUserShouyin";
	public static final String CUS_WEALTH_TOSUPPLIERSHOUYINLIST = "/tosupplierShouyinList";
	public static final String CUS_WEALTH_SETDEFAULT = "/setDefault";
	public static final String CUS_WEALTH_CANCELDEFAULT = "/cancelDefault";
	public static final String CUS_WEALTH_TOUPDATESYINFO = "/toupdateSyInfo";
	public static final String CUS_WEALTH_UPDATESYINFO = "/updateSyInfo";
	public static final String CUS_WEALTH_DELETESY = "/deleteSy";
	public static final String CUS_WEALTH_TOADDSHOUYIN = "/toAddShouYin";
	public static final String CUS_WEALTH_TOADDSHOUYINEWM = "/toAddShouYinewm";
	public static final String CUS_WEALTH_SUPPLIERADDSHOUYIN = "/supplierAddShouyin";
	public static final String CUS_WEALTH_SUPPLIERMONEYJL = "/supplierMoneyJL";
	public static final String CUS_WEALTH_USERMONEYJL = "/userMoneyJL";
	public static final String CUS_WEALTH_STARTSHOUYIN = "/startShouYin";
	public static final String CUS_WEALTH_STARTSHOUYIN2 = "/startShouYin2";
	public static final String CUS_WEALTH_OPENSHOUYIN = "/openShouYin";
	public static final String CUS_WEALTH_SHOUYINONLINE = "/startOnline";
	public static final String CUS_WEALTH_SHOUYINONLINE2 = "/startOnline2";
	public static final String CUS_WEALTH_SHOUYINNOONLINE = "/startNoOnline";
	public static final String CUS_WEALTH_GZGZH = "/gzgzh";
	public static final String CUS_WEALTH_LISTBUSINESSPARTNER = "/listBusinessPartner";
	public static final String CUS_WEALTH_TOSUPPLIERUSERS = "/tosupplierUsers";
	public static final String CUS_WEALTH_TRANSFER_INDEX = "/transferIndex";
	public static final String CUS_WEALTH_TRANSFER_INDEX_NEW = "/transferIndex_new";
	public static final String CUS_WEALTH_DETAIL = "/detail/{account}";
	public static final String CUS_WEALTH_DETAIL_NQY = "/detail_nqy/{account}";
	public static final String CUS_WEALTH_DETAIL_DJQ = "/detail_djq";
	public static final String CUS_WEALTH_DETAIL_FXED = "/detail_fxed";
	public static final String CUS_WEALTH_DETAIL_YJS = "/detail_grfxsr";
	public static final String CUS_WEALTH_DETAIL_FXSRXQ = "/detail_fxsrxq";
	public static final String CUS_WEALTH_DETAIL_GRZXFXSR_RJS = "/detail_grzxfxsr_rjs";
	public static final String CUS_WEALTH_DETAIL_GRZXFXSR = "/detail_grzxfxsr";
	public static final String CUS_WEALTH_DETAIL_SHZXDJQ = "/detail_shzxdjq";
	public static final String CUS_WEALTH_DETAIL_SHZXDDSR = "/detail_shzxddsr";
	public static final String CUS_WEALTH_DETAIL_SHZXDDSRXQ = "/detail_shzxddsrxq";
	public static final String CUS_WEALTH_DETAIL_SHZXDDSR_RJS = "/detail_shzxddsr_rjs";
	public static final String CUS_WEALTH_DETAIL_SJDDSRYJS = "/detail_yjsje";
	public static final String CUS_WEALTH_DETAIL_SJDDSRSRJE = "/detail_srje";
	public static final String CUS_WEALTH_MORE = "/more";
	public static final String CUS_WEALTH_MORE_N = "/more_n";
	public static final String CUS_WEALTH_MORE_GRZXFXSR = "/more_grzxfxsr";
	public static final String CUS_WEALTH_MORE_SHZXDDSR = "/more_shzxddsr";
	public static final String CUS_WEALTH_MORE_SHZXDJQ = "/more_shzxdjq";
	public static final String CUS_WEALTH_MORE_SHOUYINJILU = "/more_shouYinJiLi";
	public static final String CUS_WEALTH_MORE_SHOUYINJILUUSER = "/more_shouYinJiLuUser";
	public static final String CUS_WEALTH_OTALINCOME = "/otalIncome/{account}";
	
	/**
	 * 商家
	 */
	public static final String SUPPLIER = "/supplier";
	public static final String SUPPLIER_THOUSANDCITYWANSHOP = "/thousandCityWanShop";
	
	
	/**
	 * 账户设置
	 */
	public static final String CUS_SETTING = "/setting";
	public static final String CUS_SETTING_INDEX = "/index";
	
	/**
	 * 支付设置
	 */
	public static final String CUS_TRADE = "/trade";
	public static final String CUS_TRADE_TO_SETTING = "/toSetting";
	public static final String CUS_TRADE_RETRIEVE = "/toRetrieve";
	public static final String CUS_SNED_TRADE_CAPTCHA = "/sendTradeCaptcha";
	public static final String CUS_SET_TRADE = "/setTrade";
	
	/**
	 * 用户操作
	 */
	public static final String CUS_OPERATE = "/operate";
	public static final String CUS_OPERATE_LIST = "/list";
	public static final String CUS_OPERATE_DATA = "/data";
	
	/**
	 * 关于我们
	 */
	public static final String CUS_ABOUT_INDEX = "/about";
	
	/**
	 * 我的团队
	 */
	public static final String CUS_TEAM = "/team";
	public static final String CUS_TEAM_INDEX = "/index";
	public static final String CUS_TEAM_MORE = "/more";
	/**
	 * 商户中心
	 */
	public static final String Bus_Center="BusinessCenter";
}
