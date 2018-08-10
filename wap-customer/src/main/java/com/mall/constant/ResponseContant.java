/**
 * 
 */
package com.mall.constant;

/**
 * @author zhengqiang.shi 2015年3月23日 上午11:27:43
 */
public class ResponseContant {

	/******************************************
	 * CART_INDEX : 购物车首页 CONFIRM_ORDER: 订单确认页 CONFIRM_ORDER_COUPONS：可用优惠券页
	 ******************************************/
	public static final String CART_INDEX = "order/cart/cart";
	public static final String CART_MODEL_VIEW = "order/cart/async/cart";
	public static final String CONFIRM_ORDER = "order/confirm_order";
	public static final String CONFIRM_ORDER_COUPONS = "order/coupon/coupon";

	/*********************************************
	 * 用户相关 FORGOT_PASS_ACCOUNT 忘记密码 _输入账户手机号 FORGOT_PASS_NEW 忘记密码 输入新的用户密码
	 * LOGIN 登录 REG_ACCOUNT 注册 输入账号 REG_PASS 注册输入密码
	 */
	public static final String FORGOT_PASS_ACCOUNT = "/customer/retrievePassword";
	public static final String FORGOT_PASS_NEW = "/customer/forgotPassNew";
	public static final String LOGIN = "/customer/login";
	public static final String MPORMALL = "/customer/mpOrMallLogin";
	public static final String TO_PAY_LOGIN = "/customer/toPayLogin";
	public static final String TO_SUCCED = "/mallRegister/toSucced";
	public static final String FREEZE = "/customer/freeze";
	public static final String INPUT_CODE = "/customer/regInputCode";
	public static final String INPUT_CODE_PASSWORD = "/customer/retrievePasInputCode";
	public static final String REG_ACCOUNT = "/customer/register";
	public static final String REG_PASS = "/customer/regPass";
	public static final String PAYREG = "/customer/registerPay";
	
	/*********************************************
	 * 我的众聚猫相关
	 */
	public static final String CUS_ADDRESS_ADD = "/myccig/address/addressAdd";
	public static final String CUS_ADDRESS_LIST = "/myccig/address/addressList";
	public static final String CUS_ADDRESS_UPDATE = "/myccig/address/addressUpdate";

	public static final String CUS_ADDRESS_MY_ADD = "/myccig/myAddress/addressAdd";
	public static final String CUS_ADDRESS_MY_LIST = "/myccig/myAddress/addressList";
	public static final String CUS_ADDRESS_MY_UPDATE = "/myccig/myAddress/addressUpdate";

	/**
	 * 评论
	 */
	public static final String CUS_TO_COMMENT = "/myccig/comment/comment";// 评论列表页
	public static final String CUS_ADD_COMMENT = "/myccig/comment/commentAdd";// 评论页
	public static final String CUS_TO_COMMENTS = "/myccig/comment/comments";// 订单待评论
	public static final String COMMENT = "/comment";// 评论
	public static final String GO_COMMENT = "/goComment";// 添加评论

	public static final String CUS_TO_COUPON = "/myccig/coupon/couponList";
	public static final String CUS_TO_COUPON_RULE = "/myccig/coupon/couponRule";

	// 问题反馈
	public static final String CUS_ADD_SUGGEST = "/myccig/suggest/addSuggest";
	public static final String CUS_SUGGEST = "/myccig/suggest/suggest";
	public static final String CUS_SUGGESTLIST = "/myccig/suggest/suggestlist";
	public static final String CUS_SUGGES_CONTENT = "/myccig/suggest/content";
	public static final String CUS_HELP_CENTER = "/myccig/suggest/helpCenter";// 帮助中心

	public static final String CUS_HISTTORY = "/myccig/history/browseHistory";

	public static final String CUS_ACCOUNT = "/myccig/info/account";
	public static final String CUS_MYINFO = "/myccig/info/myinfo";
	public static final String CUS_VERIFY = "/myccig/info/verified";
	public static final String CUS_SET_EMAIL = "/myccig/info/changeEmail";
	public static final String CUS_SET_NICKNAME = "/myccig/info/changeNickName";
	public static final String CUS_SET_WEIXIN = "/myccig/info/changeWeixin";
	public static final String CUS_SET_IDCARD = "/myccig/info/changeIdCard";
	public static final String CUS_SET_EDUCATIONAL = "/myccig/info/changeEducational";
	public static final String CUS_TO_UPDATE_V_MOBILE = "/myccig/info/verifyMobile";
	public static final String CUS_TO_UPDATE_MOBILE = "/myccig/info/mobile";

	public static final String CUS_MYORDER = "/myccig/order/myorder";
	public static final String CUS_MYSUPPLIERORDER = "/myccig/supplierWealth/mySupplierorder";
	public static final String CUS_MY_RECIVE_MONEY = "/myccig/order/myReciveMoney";
	public static final String CUS_MY_RECIVE_MONEY_N = "/myccig/supplierWealth/shzx";
	public static final String CUS_MY_SHENGXIAN = "/myccig/order/myShengXian";
	public static final String CUS_MY_RECIVE_MONEY_ITEM = "/myccig/order/myReciveMoneyItem";
	public static final String CUS_MY_SHENGXIAN_ITEM = "/myccig/order/myShengXianItem";
	public static final String CUS_MY_JIATING_ITEM = "/myccig/order/myJiaTingItem";
	public static final String CUS_MY_CUSBUS_ITEM = "/myccig/order/mycusBusItem";
	public static final String CUS_ORDER_DETAIL = "/myccig/order/orderDetail";
	public static final String CUS_ORDER_TRACKING = "/myccig/order/orderTracking";
	public static final String CUS_ORDER_PAY = "/myccig/order/pay"; // 选择支付页
	public static final String CUS_ORDER_PAY_MIDDLE = "/myccig/order/payMiddle"; // 支付表跳转页
	public static final String CUS_ORDER_PAY_RESULT = "/myccig/order/payResult"; // 支付表结果页
	public static final String CUS_ORDER_PAY_RESULT_RECHARGE = "/myccig/order/rechargePayResult"; // 充值支付结果页
	public static final String CUS_ORDER_BOC_PAY_RESULT = "/myccig/order/bocPayResult"; // 中行支付表结果页
	public static final String CUS_ORDER_RECEIPT_SUCCESS = "/myccig/order/receiptSuccess";
	public static final String CUS_ORDER_CANCEL_PAGE = "/myccig/order/cancelOrderPage";

	public static final String CUS_ORDER_PAY_BOC_CROSS = "/myccig/pay/boccPay";
	public static final String CUS_ORDER_PAY_BOC_CROSS_AGREEMENT = "/myccig/pay/agreementAffirm";

	public static final String CUS_USER_MAIN = "/myccig/userMain";
	public static final String CUS_CLIENT_MAIN = "/myccig/client"; // 客户端
	public static final String CUS_MYCCIG = "/myccig"; // 我的众聚猫
	public static final String CUS_KEFU = "/kefu"; // 客服
	public static final String CUS_KEFU_RRG = "/rrg"; // 客服
	public static final String CUS_KEFU_CONTACT = "/contact"; // 联系客服
	public static final String CUS_KEFU_REPAIR = "/repair"; // 申请退换货
	public static final String CUS_KEFU_RETURN_LIST = "/return_list";
	public static final String CUS_KEFU_APPLY_FIRST = "/applyFirst"; // 申请退换货 //
																		// 第一页
	public static final String CUS_KEFU_APPLY_SECOND = "/applySecond"; // 申请退换货
																		// 第二页
	public static final String CUS_KEFU_APPLY_SUCCESS = "/applySuccess";

	public static final String CHOOSE_ACCOUNT = "/myccig/pay/chooseAccount";

	public static final String REDIRECT = "redirect:";

	public static final String INDEX = "index/toIndex";

	public static final String ERROR_500 = "/errorpage/error500"; // 系统错误
	public static final String ERROR_404 = "/errorpage/error404"; // 不存在路径

	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	/***************************************************************/

	// 系统通知
	public static final String SYS_NOTICE = "/myccig/notice/notice";
	public static final String SYS_NOTICE_LIST = "/myccig/notice/noticelist";

	// 财富中心
	public static final String CUS_WEALTH_INFO = "/myccig/wealth/index";
	public static final String CUS_WEALTH_INFO_N = "/myccig/wealth/index_n";
	public static final String CUS_WEALTH_TRANSFER_INDEX = "/myccig/wealth/transferIndex";
	public static final String CUS_WEALTH_TRANSFER_INDEX_NEW = "/myccig/wealth/transferIndex_new";
	public static final String CUS_WEALTH_DETAIL = "/myccig/wealth/detail";
	public static final String CUS_WEALTH_DETAIL_FXSR = "/myccig/wealth/detail_fxsr";
	public static final String CUS_WEALTH_DETAIL_NQY = "/myccig/wealth/detail_nqy";
	public static final String CUS_WEALTH_DETAIL_DJQ = "/myccig/wealth/detail_djq";
	public static final String CUS_WEALTH_DETAIL_FXED = "/myccig/wealth/detail_fxed";
	public static final String CUS_WEALTH_DETAIL_GRFXSR = "/myccig/wealth/detail_grfxsr";
	public static final String CUS_WEALTH_DETAIL_FXSRXQ = "/myccig/wealth/detail_fxsrxq";
	public static final String CUS_WEALTH_DETAIL_GRZXFXSR_RJS = "/myccig/wealth/detail_grzxfxsr_rjs";
	public static final String CUS_WEALTH_DETAIL_GRZXFXSR = "/myccig/wealth/detail_grzxfxsr";
	public static final String CUS_WEALTH_DETAIL_SHZXDDSR = "/myccig/wealth/detail_shzxddsr";
	public static final String CUS_WEALTH_DETAIL_SHZXDDSRXQ = "/myccig/wealth/detail_shzxddsrxq";
	public static final String CUS_WEALTH_DETAIL_SHZXDDSR_RJS = "/myccig/wealth/detail_shzxddsr_rjs";
	public static final String CUS_WEALTH_DETAIL_YJSJE = "/myccig/wealth/detail_yjsje";
	public static final String CUS_WEALTH_DETAIL_SRJE = "/myccig/wealth/detail_srje";
	public static final String CUS_WEALTH_DETAIL_SHZXDJQ = "/myccig/wealth/detail_shzxdjq";
	public static final String CUS_WEALTH_REDTRANSFER = "/myccig/supplierWealth/redTransfer";
	public static final String CUS_WEALTH_REDTRANSFER_N = "/myccig/supplierWealth/redTransfer_n";
	public static final String CUS_WEALTH_TURNVOUCHER = "/myccig/supplierWealth/turnVoucher";
	public static final String CUS_WEALTH_SUPPLIERUSERS = "/myccig/supplierWealth/supplierUsers";
	public static final String CUS_WEALTH_LISTBUSINESSPARTNER = "/myccig/supplierWealth/listBusinessPartner";
	public static final String CUS_WEALTH_SUPPLIERUSERSHOUYIN = "/myccig/supplierWealth/supplierUserShouyin";
	public static final String CUS_WEALTH_SUPPLIERSHOUYINLIST = "/myccig/supplierWealth/supplierShouyinList";
	public static final String CUS_WEALTH_UPDATESYINFO = "/myccig/supplierWealth/updateSyInfo";
	public static final String CUS_WEALTH_SUPPLIERMONEYJL = "/myccig/supplierWealth/supplierMoneyJL";
	public static final String CUS_WEALTH_USERMONEYJL = "/myccig/supplierWealth/userMoneyJL";
	public static final String CUS_WEALTH_STARTSHOUYIN = "/myccig/supplierWealth/startShouYin";
	public static final String CUS_WEALTH_STARTSHOUYIN2 = "/myccig/supplierWealth/startShouYin2";
	public static final String CUS_WEALTH_SUPPLIERSHOUYINJILU = "/myccig/supplierWealth/supplierShouYinJiLu";
	public static final String CUS_WEALTH_SUPPLIERADDSHOUYIN = "/myccig/supplierWealth/supplierAddShouyin";
	public static final String CUS_WEALTH_APPLYCOUPONS = "/myccig/supplierWealth/applyCoupons";
	public static final String CUS_WEALTH_OTALINCOME = "/myccig/wealth/otalIncome";
	
	//商家
	public static final String SUPPLIER_THOUSANDCITYWANSHOP = "/myccig/supplierWealth/thousandCityWanShop";
	
	//商家详情
	public static final String SUPPLIER_SHOPDETAIL="/myccig/supplierWealth/shopDetail";
	//修改商家详情
	public static final String SUPPLIER_UPDATESHOPDETAIL="/myccig/supplierWealth/updateShopDetail";
	//修改商家定位
	public static final String SUPPLIER_UPDATESHOPLOCATION="/myccig/supplierWealth/location";
	
	// 关于我们
	public static final String CUS_ABOUT_INDEX = "/myccig/setting/about";
	
	// 账户设置
	public static final String CUS_SETTING_INDEX = "/myccig/setting/index";
	
	// 支付设置
	public static final String CUS_TRADE_SETTING = "/myccig/trade/setting";
	public static final String CUS_TRADE_UPDATE_FORM = "/myccig/trade/updateForm";
	public static final String CUS_TRADE_RETRIEVE = "/myccig/trade/retrieve";
	
	// 用户操作
	public static final String CUS_OPERATION_LIST = "/myccig/operation/list";
	
	// 我的团队
	public static final String CUS_TEAM_INDEX = "/myccig/team/index";
}
