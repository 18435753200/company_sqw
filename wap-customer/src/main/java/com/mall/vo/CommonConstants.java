/**
 * 
 */
package com.mall.vo;

/**
 * @author jianping.gao
 *
 */
public interface CommonConstants {
	// 服务单号的前缀
	String SERVICE_PREFIX = "FW";

	String NOTICETAKEOUER_PREFIX = "RK";

	String RECORD_PREFIX = "B";

	// 退款申请单号前缀
	String NOTICEREFUND_PREFIX = "TK";

	// 常用数字
	int _ONE = -1;
	int ZERO = 0;
	int ONE = 1;
	int TWO = 2;
	int THREE = 3;

	// 退换货响应状态
	String RESPONSE_STATUS_SUCCESDS = "1";
	String RESPONSE_STATUS_ERROR = "2";

	// b2b 完成订单状态
	public static final short ORDER_STATUS_ORDERSUCCESS = 91;
	public static final short ORDER_STATUS_XITONGWANCHENG = 101;

	// 图片服务器
	public static final String FILESERVER3 = "Http://image01.mall.com/p1/";

	// 物流类型 1、订单中快递 2、客服人员填的快递
	public static final int LOGISTICS_TYPE_1 = 1;
	public static final int LOGISTICS_TYPE_2 = 2;

}
