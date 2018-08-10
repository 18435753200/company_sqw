package com.mall.controller.myccigmall;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.constant.ThirdAccountConstant;
import com.mall.customer.service.*;
import com.mall.pay.api.rpc.BankPayService;
import com.mall.pay.dto.PayRequest;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mall.annotation.AuthPassport;
import com.mall.check.PayCheck.accountCheck;
import com.mall.check.PayCheck.bocCrossPayCheck;
import com.mall.commons.utils.DateUtil;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.dto.OrderItemReturnTicketDto;
import com.mall.customer.dto.UserTradeDto;
import com.mall.customer.model.CashierRecord;
import com.mall.customer.model.CashierSettings;
import com.mall.customer.model.HomeNumRecord;
import com.mall.customer.model.OneDividend;
import com.mall.customer.model.OneDividendRatio;
import com.mall.customer.model.SupplierNumRecord;
import com.mall.customer.model.User;
import com.mall.customer.model.UserOperationRecord;
import com.mall.customer.model.UserTrade;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.po.Order;
import com.mall.customer.order.po.OrderItem;
import com.mall.dealer.product.api.pop.DealerPopProductService;
import com.mall.dealer.product.po.ProductTags;
import com.mall.dealer.productTags.api.ProductTagsService;
import com.mall.exception.BadRequestException;
import com.mall.exception.BusinessException;
import com.mall.pay.common.PayConstant;
import com.mall.pay.common.StringUtils;
import com.mall.pay.common.PayConstant.AlipayCurrency;
import com.mall.pay.common.PayConstant.BankPayModel;
import com.mall.pay.common.PayConstant.BankPayStatus;
import com.mall.pay.common.PayConstant.BocNcpTxnSubType;
import com.mall.pay.common.PayConstant.MerchantName;
import com.mall.pay.common.PayConstant.PayChannel;
import com.mall.pay.common.PayConstant.PayRecordStatus;
import com.mall.pay.dto.PayResult;
import com.mall.pay.dto.QueryResult;
import com.mall.pay.po.BocCrossBorder;
import com.mall.service.CusOrderPayService;
import com.mall.service.CusOrderService;
import com.mall.service.CustomerService;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.utils.Constants;
import com.mall.utils.JsonUtil;
import com.mall.utils.ParameterTool;
import com.mall.utils.SendSMSUtil;
import com.mall.utils.getUUID;
import com.mall.vo.AccountCheckVO;
import com.mall.vo.BocCrossSignVO;
import com.mall.vo.WeiXinGZHVO;

/**
 * 支付相关
 * 
 * @author ccigQA01
 * 
 */
@Controller
@RequestMapping(value = RequestContant.ORDER_PAY)
public class CustomerOrderPayController extends BaseController {
	private static final Logger log = LoggerFactory
			.getLogger(CustomerOrderPayController.class);

	@Autowired
	private BankPayService bankPayService;

	// 支付先关的服务
	@Autowired
	private CusOrderPayService cusOrderPayService;
	// 订单相关服务
	@Autowired
	private CusOrderService cusOrderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerOrderService customerOrderService;
	@Autowired
	private UserService userService;
	@Autowired
	private SqwAccountRecordService sqwAccountRecordService;
	@Autowired
	private SupplierManagerService supplierManagerService;
	@Autowired
	private DealerPopProductService dealerPopProductService;
	@Autowired
	private DividendService dividendService;
	@Autowired
	private UserOperationRecordService userOperationRecordService;
	@Autowired
	private CashierService cashierService;
	@Autowired
	private ProductTagsService productTagsService;
	@Autowired
	private HomeNumRecordService homeNumRecordService;
	@Autowired
	private SupplierNumRecordService supplierNumRecordService;
	@Autowired
	private PsqwAccountRecordService psqwAccountRecordService;



	@RequestMapping(value = RequestContant.TRADE_PWD_PAGE)
	public String tradePwdPage(String act, String orderId, Model model,HttpServletRequest req, RedirectAttributes redirectAttributes) {

		User user = getCurrentUser(req);
		UserTradeDto userTradeDto = userService.getTrade(user.getUserId());

		if (Constants.SqwAccountRecordService.PWD_NOT_EXIST.equals(userTradeDto.getRetCode())) {
			return RequestContant.REDIRECT + "http://m.zhongjumall.com/trade/toSetting?returnUrl=/orderPay/tradePwdPage&orderId=" + orderId;
		}


		model.addAttribute("orderId", orderId);
		model.addAttribute("act", act);
		OrderDTO orderDto = customerOrderService.getOrderDtoByOrderId(Long.valueOf(orderId));

		if (CommonConstant.HQQ_AND_MONEY_PAY.equals(act)) {
			OneDividendRatio oneDividendRatio = null;
			try {
				oneDividendRatio = dividendService.getOneDividendRatio();
			} catch (Exception e) {
				log.error("tradePwdPage系统异常：" + e);
			}
			if (orderDto.getCashPrice() != null && orderDto.getHqjPrice() != null
					&& orderDto.getCashPrice().compareTo(BigDecimal.ZERO) > 0
					&& orderDto.getHqjPrice().compareTo(BigDecimal.ZERO) > 0) {

				model.addAttribute("payAmount", orderDto.getHqjPrice());

			} else {

				if (oneDividendRatio == null || oneDividendRatio.getTicketRation() == null || oneDividendRatio.getCashRation() == null) {

					redirectAttributes.addAttribute("errorMsg", "请先设置现金+M券支付比例");

					return ResponseContant.REDIRECT + RequestContant.CUS_ORDER + RequestContant.CUS_ORDER_TO_PAY + "?orderId=" + orderId;
				}

				model.addAttribute("payAmount", orderDto.getPrice().multiply(oneDividendRatio.getTicketRation().multiply(BigDecimal.valueOf(0.01)))
						.setScale(0, BigDecimal.ROUND_HALF_UP));

			}
		} else {
			model.addAttribute("payAmount", orderDto.getPrice());
		}
		return "myccig/order/checkTradePwd";
	}

	/**
	 * 使用M宝时检查是否设置M券支付密码
	 * 
	 * @param shopUserId
	 * @param shopRealName
	 * @param price
	 * @param model
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.TRADE_PWD_PAGE_RECIVE_SYS)
	public String tradePwdPageForReciveSys(String shopUserId,
			String shopRealName, String price, Model model,
			HttpServletRequest req, HttpServletResponse response) {
		User user = getCurrentUser(req);
		UserTradeDto userTradeDto = userService.getTrade(user.getUserId());
		if (Constants.SqwAccountRecordService.PWD_NOT_EXIST.equals(userTradeDto
				.getRetCode())) {
			return RequestContant.REDIRECT
					+ "http://m.zhongjumall.com/trade/toSetting";
			// M券支付密码设置
		}
		CashierSettings cashierSettingsQuery = new CashierSettings();
		// M券倍数
		cashierSettingsQuery.setDividendType(1);
		CashierSettings cashierSettingsFromDb = null;
		try {
			cashierSettingsFromDb = cashierService
					.findCashierSettsingsByDividendType(cashierSettingsQuery);
		} catch (Exception e) {
			log.error("系统异常" + e);
		}
		BigDecimal payPrice = new BigDecimal(price)
				.multiply(cashierSettingsFromDb.getRealpayMultipe())
				.add((new BigDecimal(price)).multiply(cashierSettingsFromDb
						.getCounterFee().multiply(BigDecimal.valueOf(0.01))))
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		model.addAttribute("payPrice", payPrice);
		model.addAttribute("price", price);
		model.addAttribute("shopRealName", shopRealName);
		model.addAttribute("shopUserId", shopUserId);
		return "myccig/order/checkTradePwdForReciveSys";
	}

	/**
	 * 使用生鲜宝时检查是否设置M券支付密码
	 * 
	 * @param shopUserId
	 * @param shopRealName
	 * @param price
	 * @param model
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.TRADE_PWD_PAGE_SHENGXIAN)
	public String tradePwdPageForShengXian(String shopUserId,
			String shopRealName, String price, Model model,
			HttpServletRequest req, HttpServletResponse response) {
		User user = getCurrentUser(req);
		UserTradeDto userTradeDto = userService.getTrade(user.getUserId());
		if (Constants.SqwAccountRecordService.PWD_NOT_EXIST.equals(userTradeDto
				.getRetCode())) {
			return RequestContant.REDIRECT
					+ "http://m.zhongjumall.com/trade/toSetting";
			// M券支付密码设置
		}
		CashierSettings cashierSettingsQuery = new CashierSettings();
		// M券倍数
		cashierSettingsQuery.setDividendType(2);
		CashierSettings cashierSettingsFromDb = null;
		try {
			cashierSettingsFromDb = cashierService
					.findCashierSettsingsByDividendType(cashierSettingsQuery);
		} catch (Exception e) {
			log.error("系统异常" + e);
		}
		BigDecimal payPrice = new BigDecimal(price)
				.multiply(cashierSettingsFromDb.getRealpayMultipe())
				.add((new BigDecimal(price)).multiply(cashierSettingsFromDb
						.getCounterFee().multiply(BigDecimal.valueOf(0.01))))
				.setScale(0, BigDecimal.ROUND_HALF_UP);
		model.addAttribute("payPrice", payPrice);
		model.addAttribute("price", price);
		model.addAttribute("shopRealName", shopRealName);
		model.addAttribute("shopUserId", shopUserId);
		return "myccig/order/checkTradePwdForShengXian";
	}

	@ResponseBody
	@RequestMapping(value = RequestContant.CHECK_TRADE_PWD)
	public UserTradeDto checkTradePwd(String pwd, Model model,
			HttpServletRequest req, HttpServletResponse response) {
		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		UserTrade userTrade = new UserTrade();
		userTrade.setUserId(user.getUserId());
		userTrade.setPwd(pwd);
		UserTradeDto userTradeDto = userService.checkTradePwd(userTrade);
		return userTradeDto;
	}

	/**
	 * 获取支付信息(android)
	 * 
	 * @param orderId
	 * @param act
	 *            支付方式
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.ORDER_PAY_INFO_APP)
	// @AuthPassport
	public String payInfoForApp(Long orderId, String act, String userId,
			String platForm, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("payInfoForAndoird execute ...");
		if (orderId == null || StringUtils.isEmpty(act)) {
			log.info("orderId is null or act is null");
			throw new BadRequestException("请求失败");
		}

		Short channelNo = PayChannel.WAY_ANDROID;

		User currentUser = new User();
		if ("1".equals(platForm)) {
			// andorid 平台 --3
			channelNo = PayChannel.WAY_ANDROID;
			currentUser.setUserId(Long.valueOf(userId));

			// String sessionid = request.getSession().getId();
			String sessionid = getUUID.getSessionId(request, response);
			String orderIdUserId = userId + " " + orderId;
			log.info("app android session id --->" + sessionid);
			try {
				memcachedClient.set("pay" + sessionid,
						CommonConstant.MEMCACHEDAGE, orderIdUserId);
				memcachedClient.set("payPlatForm" + sessionid,
						CommonConstant.MEMCACHEDAGE, platForm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("2".equals(platForm)) {
			// andorid 平台 --3
			channelNo = PayChannel.WAY_IOS;
			currentUser.setUserId(Long.valueOf(userId));

			// String sessionid = request.getSession().getId();
			String sessionid = getUUID.getSessionId(request, response);
			String orderIdUserId = userId + " " + orderId;
			log.info("app IOS session id --->" + sessionid);
			try {
				memcachedClient.set("pay" + sessionid,
						CommonConstant.MEMCACHEDAGE, orderIdUserId);
				memcachedClient.set("payPlatForm" + sessionid,
						CommonConstant.MEMCACHEDAGE, platForm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		OrderDTO findOrderByOrderId = cusOrderService.findOrderByOrderId(
				orderId, currentUser);

		Short status = findOrderByOrderId.getStatus();

		String jumpUrl = null;
		// 1 为待支付
		if (status == 1) {
			if (CommonConstant.ORDER_SOURCE_CHINABANKCREDITCARD
					.equals(findOrderByOrderId.getOrderSource())) {
				if (CommonConstant.BOC_Net_PAY.equals(act)) {
					jumpUrl = cusOrderPayService.bocB2CMobileNetPay(orderId,
							channelNo, currentUser, request, response, model,
							act);
				}
			} else {
				// 支付宝
				if (CommonConstant.ALIPAY.equals(act)) {

					jumpUrl = cusOrderPayService.aliPayInfo(orderId, channelNo,
							currentUser, request, response, model, act);
				}
				if (CommonConstant.BOC_Net_PAY.equals(act)) {
					jumpUrl = cusOrderPayService.bocB2CMobileNetPay(orderId,
							channelNo, currentUser, request, response, model,
							act);
				}
			}

		} else {
			// 订单不是在待支付状态 不能进行支付
			jumpUrl = "payError";
		}
		return "redirect:" + jumpUrl;
	}

	/**
	 * 中行跨境 去支付
	 * 
	 * @param orderId
	 * @param act
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.ORDER_BOCC_PAY_INFO_APP)
	public String boccPayInfoApp(Long orderId, String act, Long userId,
			String platForm, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("boccPayInfoApp execute ...");
		try {

			if (orderId == null || StringUtils.isEmpty(act) || userId == null) {
				log.info("orderId is null or act is null");
				throw new BadRequestException("请求失败");
			}

			User user = customerService.findUserById(userId);
			customerService.login(request, response, user);

			Short channelNo = PayChannel.WAY_ANDROID;
			User currentUser = new User();
			if ("1".equals(platForm)) {
				// andorid 平台 --3
				channelNo = PayChannel.WAY_ANDROID;
				currentUser.setUserId(Long.valueOf(userId));

				// String sessionid = request.getSession().getId();
				String sessionid = getUUID.getSessionId(request, response);
				String orderIdUserId = userId + " " + orderId;
				try {
					memcachedClient.set("pay" + sessionid,
							CommonConstant.MEMCACHEDAGE, orderIdUserId);
					memcachedClient.set("payPlatForm" + sessionid,
							CommonConstant.MEMCACHEDAGE, platForm);
					model.addAttribute("payPlatForm", platForm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if ("2".equals(platForm)) {
				// andorid 平台 --3
				channelNo = PayChannel.WAY_IOS;
				currentUser.setUserId(Long.valueOf(userId));

				// String sessionid = request.getSession().getId();
				String sessionid = getUUID.getSessionId(request, response);
				String orderIdUserId = userId + " " + orderId;
				try {
					memcachedClient.set("pay" + sessionid,
							CommonConstant.MEMCACHEDAGE, orderIdUserId);
					memcachedClient.set("payPlatForm" + sessionid,
							CommonConstant.MEMCACHEDAGE, platForm);
					model.addAttribute("payPlatForm", platForm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			OrderDTO orderDto = cusOrderService.findOrderByOrderId(orderId,
					currentUser);

			Short status = orderDto.getStatus();
			if (status != 1) {
				throw new BusinessException("order statu isn't 1, is " + status);
			}
			String jumpUrl;
			// 检查是否签约中行跨境协议支付
			BocCrossBorder bocCrossBorder = cusOrderPayService
					.findBoccSignByUserIdDefualt(currentUser);
			if (bocCrossBorder != null) {
				model.addAttribute("merchantName", MerchantName.BOCCROSS);
				model.addAttribute("orderId", orderId);
				model.addAttribute("amount", orderDto.getPrice());
				model.addAttribute("accNo", bocCrossBorder.getDraccNo());
				model.addAttribute("phone", bocCrossBorder.getDbtPhone());
				model.addAttribute("signNo", bocCrossBorder.getTrxSerno());
				jumpUrl = ResponseContant.CUS_ORDER_PAY_BOC_CROSS;
			} else {
				model.addAttribute("orderId", orderId);
				model.addAttribute("act", act);
				jumpUrl = "/myccig/pay/boccSigning";
			}
			log.info("jumpUrl is " + jumpUrl);
			return jumpUrl;
		} catch (Exception e) {
			log.error("跳转支付页面失败：" + e.getMessage(), e);
			throw new BusinessException("跳转支付页面失败：" + e.getMessage(), e);
		}
	}

	/**
	 * 获取支付信息(拼接跳转)
	 * 
	 * @param orderId
	 * @param act
	 *            支付方式
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.ORDER_PAY_INFO, produces = "text/html;charset=UTF-8")
	@AuthPassport
	@ResponseBody
	public String payInfo(Long orderId, String pwd, String act, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("payInfo execute ...");
		if (log.isDebugEnabled()) {
			Map<String, String> allRequestParam = ParameterTool
					.getAllRequestParam(request);
			log.debug("requestparames:{" + allRequestParam + "}");
		}
		try {
			if (orderId == null || StringUtils.isEmpty(act)) {
				log.info("orderId is null or act is null");
				throw new BadRequestException("请求失败");
			}
			User currentUser = getCurrentUser(request);
			// 平台号
			Short channelNo = PayChannel.WAY_WAP;

			OrderDTO orderDto = cusOrderService.findOrderByOrderId(orderId,
					currentUser);

			Short status = orderDto.getStatus();

			String jumpUrl = null;
			// 1 为待支付
			if (status == 1) {
				if (CommonConstant.ORDER_SOURCE_CHINABANKCREDITCARD
						.equals(orderDto.getOrderSource())) {
					if (CommonConstant.BOC_Net_PAY.equals(act)) {
						jumpUrl = cusOrderPayService.bocB2CMobileNetPay(
								orderId, channelNo, currentUser, request,
								response, model, act);
					}
				} else {
					// 支付宝
					if (CommonConstant.ALIPAY.equals(act)
							|| CommonConstant.ALIPAY_DIRECT.equals(act)) {
						jumpUrl = cusOrderPayService.aliPayInfo(orderId,
								channelNo, currentUser, request, response,
								model, act);
					}

					if (CommonConstant.JD_PAY.equals(act)) {
						// local url
						// jumpUrl =
						// "/wap-customer/orderPay/toPayMiddle?orderId=" +
						// orderId + "&act=jdPay";
						jumpUrl = "/orderPay/toPayMiddle?orderId=" + orderId
								+ "&act=jdPay";
					}

					if (CommonConstant.UNIONPAY.equals(act)) {
						jumpUrl = "/orderPay/toPayMiddle?orderId=" + orderId
								+ "&act=unionPay";
					}
					if (CommonConstant.BOC_Net_PAY.equals(act)) {
						jumpUrl = cusOrderPayService.bocB2CMobileNetPay(
								orderId, channelNo, currentUser, request,
								response, model, act);
					}
					// 微信公众号支付
					if (CommonConstant.WEIXIN_GZH_PAY.equals(act)) {
						jumpUrl = cusOrderPayService.weiXinWapPay(orderId,
								channelNo, currentUser, request, response,
								model, act);
					}
					if (CommonConstant.WEIXIN_WAP_PAY.equals(act)) {
						jumpUrl = cusOrderPayService.weiXinWapPay(orderId,
								channelNo, currentUser, request, response,
								model, act);
					}
					// 翼支付WAP
					if (CommonConstant.BESTOAY_WAP_DIRECT.equals(act)) {
						log.info("sart bestoay proccessing...");
						PayResult bestoayPay = cusOrderPayService.bestoayPay(
								orderId, channelNo, currentUser, request,
								response, model, act);
						/*
						 * String
						 * productName=orderDto.getOrderItemDTOs().get(0).
						 * getpName(); if(productName!=null &&
						 * productName.length()>8){
						 * productName=productName.substring(0, 8) + "..."; }
						 * log.info("翼支付WAP支付请求参数");
						 * log.info(JsonUtil.serializeBeanToJsonString
						 * (bestoayPay)); model.addAttribute("payResult",
						 * bestoayPay); model.addAttribute("payType",
						 * BankPayModel.PAY_TYPE_BESTOAYPAY_MOBILE_WAP);
						 * model.addAttribute("productDesc", productName);
						 * model.addAttribute("subject", productName);
						 * log.info("bestoay proccess end!!");
						 */
						jumpUrl = bestoayPay.getAction();
					}

				}
			} else {
				// 订单不是在待支付状态 不能进行支付
				jumpUrl = "payError";
			}
			return jumpUrl;
		} catch (Exception e) {
			log.error("跳转支付页面失败：" + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * M券支付(拼接跳转)
	 * 
	 * @param orderId
	 * @param act
	 *            支付方式
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.HQ_ORDER_PAY_INFO, produces = "text/html;charset=UTF-8")
	@AuthPassport
	public String hqPayInfo(Long orderId, String pwd, String act, Model model,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		log.info("payInfo execute ...");
		if (log.isDebugEnabled()) {
			Map<String, String> allRequestParam = ParameterTool
					.getAllRequestParam(request);
			log.debug("requestparames:{" + allRequestParam + "}");
		}
		try {
			if (orderId == null || StringUtils.isEmpty(act)) {
				log.info("orderId is null or act is null");
				throw new BadRequestException("请求失败");
			}
			User currentUser = getCurrentUser(request);
			// 平台号
			Short channelNo = PayChannel.WAY_WAP;

			OrderDTO orderDto = cusOrderService.findOrderByOrderId(orderId,
					currentUser);

			Short status = orderDto.getStatus();

			String jumpUrl = null;
			// 1 为待支付
			if (status == 1) {
				// 开始处理M卷支付
				if (act.equals(CommonConstant.HQ_PAY)) {
					// 支付密码验证接口（暂时屏蔽）
					UserTrade userTrade = new UserTrade();
					userTrade.setUserId(currentUser.getUserId());
					userTrade.setPwd(pwd);
					UserTradeDto userTradeDto = userService
							.checkTradePwd(userTrade);
					// OrderDTO orderHQDto =
					// customerOrderService.getOrderDtoByOrderId(orderId);
					if (Constants.SqwAccountRecordService.PWD_ERROR
							.equals(userTradeDto.getRetCode())
							|| Constants.SqwAccountRecordService.NO_PWD
									.equals(userTradeDto.getRetCode())
							|| Constants.SqwAccountRecordService.PWD_ERROR_FOR_UPDATE
									.equals(userTradeDto.getRetCode())
							|| Constants.SqwAccountRecordService.PWD_INPUT_TOO_MANY
									.equals(userTradeDto.getRetCode())) {
						model.addAttribute("orderId", orderId);
						model.addAttribute("act", "hqPay");
						model.addAttribute("payAmount", orderDto.getPrice());
						return "myccig/order/checkTradePwd";
					}

					List<OrderItem> orderItemList = customerOrderService
							.getOrderItemByOrderId(orderId);
					// 调用预支付方法，生成支付流水payResult
					PayResult payResult = cusOrderPayService.hqPayInfo(orderId,
							channelNo, currentUser, request, response, model,
							act);


					// 调用M卷支付接口
			/*		int hqPayStatus = sqwAccountRecordService.payOrderByHqq(
							currentUser.getUserId(), orderId,
							orderDto.getPrice());*/

					int hqPayStatus = psqwAccountRecordService.payOrderByVoucher(currentUser.getUserId(), orderId, orderDto.getPrice(), 2, "线上全券支付");

					// M卷支付成功
					if (PayConstant.HqPayTradeStatus.TRADE_SUCCESS
							.equals(String.valueOf(hqPayStatus))) {
						// M券回调参数
						Map<String, String> requestParams = HqPayParamMap(
								orderId, payResult.getLocalFlowNo(),
								PayConstant.HqPayTradeStatus.TRADE_SUCCESS,
								orderDto.getPrice().toString(), null);
						// M券支付回调（修改订单状态，支付流水状态）
						PayResult callBackpayResult = cusOrderPayService
								.HqPayCallBack(
										requestParams,
										PayConstant.BankPayModel.PAY_TYPE_HQ_INTEGRAL);
						// M券支付成功
						if (PayRecordStatus.SUCCESS.equals(callBackpayResult
								.getPayStatus())) {
							List<OrderItemReturnTicketDto> OrderItemReturnTicketDtoList = new ArrayList<OrderItemReturnTicketDto>();
							User user = userService.findUserById(orderDto
									.getUserId());

							Order order = new Order();
							Supplier supplier = supplierManagerService
									.findSupplier(Long.parseLong(orderDto
											.getMerchantid()));
							OrderDTO orderDtoNew = customerOrderService
									.getOrderDtoByOrderId(orderId);
							// OneDividend hqOneDividend =
							// dividendService.findOneDivByType(Constants.SqwAccountRecordService.HQ_PAY,
							// Integer.parseInt(supplier.getCompanyQy()));

							// order.setOrderHqj(orderDto.getPrice().multiply(hqOneDividend.getGiftHqj().multiply(BigDecimal.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED))).doubleValue());
							// order.setOrderFhed(orderDto.getPrice().multiply(hqOneDividend.getGiftFhed().multiply(BigDecimal.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED))).doubleValue());

			/*				Double sumReturn_hqq = 0d;
							Double sumReturn_fhed = 0d;*/

							order.setCompanyQy(supplier.getCompanyQy());
							order.setPayWay(Constants.SqwAccountRecordService.HQPAY_WAY);
							order.setId(orderDto.getId());
							order.setVersion(orderDtoNew.getVersion());
							order.setPaidPrice(orderDto.getPrice());
		/*					Long firstUserId = null;
							Long secondUserId = null;
							if (!StringUtils.isEmpty(user.getTjMobile())) {
								User firstTjUser = userService
										.findUserByMobile(user.getTjMobile());
								if (firstTjUser != null) {
									firstUserId = firstTjUser.getUserId();
									if (!StringUtils.isEmpty(firstTjUser
											.getTjMobile())) {
										User secondTjUser = userService
												.findUserByMobile(firstTjUser
														.getTjMobile());
										if (secondTjUser != null)
											secondUserId = secondTjUser
													.getUserId();
									}
								}
							}*/
							/*List<OrderItem> orderItemUpdates = new ArrayList<OrderItem>();
							for (OrderItem orderItem : orderItemList) {*/
								// M宝
								/*if (productTagsService.LimitContentOrNull(
										orderItem.getPid(), "hqb", 4) != null) {
									CashierRecord cashierRecord = new CashierRecord();
									cashierRecord.setUserId(currentUser
											.getUserId());
									cashierRecord.setOrderNo(orderId);
									cashierRecord.setCommodityName(orderItem
											.getpName());
									cashierRecord.setStartDate(new Date());
									cashierRecord.setSpecDesc(orderItem
											.getUnit());
									cashierRecord.setAmount(orderItem
											.getSkuQty());
									cashierRecord.setAmountOfMoney(orderItem
											.getSubtotalPirce());
									cashierRecord.setType(1);
									cashierService
											.insertCashierRecord(cashierRecord);
								}
								// 生鲜宝
								if (productTagsService.LimitContentOrNull(
										orderItem.getPid(), "sxb", 4) != null) {
									CashierRecord cashierRecord = new CashierRecord();
									cashierRecord.setUserId(currentUser
											.getUserId());
									cashierRecord.setOrderNo(orderId);
									cashierRecord.setCommodityName(orderItem
											.getpName());
									cashierRecord.setStartDate(new Date());
									cashierRecord.setSpecDesc(orderItem
											.getUnit());
									cashierRecord.setAmount(orderItem
											.getSkuQty());
									cashierRecord.setAmountOfMoney(orderItem
											.getSubtotalPirce());
									cashierRecord.setType(2);
									cashierService
											.insertCashierRecord(cashierRecord);
								}
								// 家庭宝
								if (productTagsService.LimitContentOrNull(
										orderItem.getPid(), "jtb", 4) != null) {
									HomeNumRecord homeNumRecord = new HomeNumRecord();
									homeNumRecord.setUserId(currentUser
											.getUserId().intValue());
									homeNumRecord.setProductId(orderItem
											.getPid().toString());
									homeNumRecord.setOrderNo(orderId);
									homeNumRecord.setCommodityName(orderItem
											.getpName());
									homeNumRecord.setStartDate(new Date());
									homeNumRecord.setSpecDesc(orderItem
											.getUnit());

									homeNumRecord.setAmountOfMoney(orderItem
											.getSubtotalPirce());

									homeNumRecordService
											.insertHomeNumRecord(homeNumRecord);
								}
								// 会员企业注册
								ProductTags limitContentOrNull = productTagsService.LimitContentOrNull(
										orderItem.getPid(), "qyb", 4);
								if (limitContentOrNull != null) {
									SupplierNumRecord supplierNumRecord = new SupplierNumRecord();
									supplierNumRecord
											.setUserId(orderDto.getUserId().intValue());

									supplierNumRecord.setProductId(orderItem.getPid()
											.toString());
									supplierNumRecord.setOrderNo(orderId);
									supplierNumRecord.setCommodityName(orderItem.getpName());
									supplierNumRecord.setStartDate(new Date());
									supplierNumRecord.setSpecDesc(orderItem.getUnit());

									supplierNumRecord.setAmountOfMoney(orderItem
											.getSubtotalPirce());
									supplierNumRecordService.insertSupplierNumRecord(supplierNumRecord);
									
								}*/
								/*
								OneDividend hqOneDividend = dividendService
										.findOneDivByType(
												Constants.SqwAccountRecordService.HQ_PAY,
												Integer.parseInt(supplier
														.getCompanyQy()),
												orderItem.getPid());
								sumReturn_hqq += (orderItem.getSubtotalPirce()
										.multiply(hqOneDividend
												.getGiftHqj()
												.multiply(
														BigDecimal
																.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED))))
										.doubleValue();
								sumReturn_fhed += (orderItem.getSubtotalPirce()
										.multiply(hqOneDividend
												.getGiftFhed()
												.multiply(
														BigDecimal
																.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED))))
										.doubleValue();

								OrderItemReturnTicketDto orderItemReturnTicketDto = new OrderItemReturnTicketDto();
								OrderItem orderItemUpdate = new OrderItem();
								orderItemReturnTicketDto.setpNum(orderItem
										.getSkuQty());
								orderItemReturnTicketDto
										.setProductSkuId(orderItem.getSkuId());
								orderItemReturnTicketDto
										.setProductRegin(supplier
												.getCompanyQy());
								orderItemReturnTicketDto
										.setRefObjType(Constants.intONE);
								orderItemReturnTicketDto.setRefObjId(orderId);
								orderItemReturnTicketDto.setPrice_hqq(orderItem
										.getSubtotalPirce());

								orderItemReturnTicketDto
										.setReturn_fhed((orderItem
												.getSubtotalPirce().multiply(hqOneDividend
												.getGiftFhed()
												.multiply(
														BigDecimal
																.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))));
								orderItemReturnTicketDto
										.setReturn_hqq((orderItem
												.getSubtotalPirce().multiply(hqOneDividend
												.getGiftHqj()
												.multiply(
														BigDecimal
																.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))));
								OrderItemReturnTicketDtoList
										.add(orderItemReturnTicketDto);
								orderItemUpdate
										.setFhed((orderItem.getSubtotalPirce().multiply(hqOneDividend
												.getGiftFhed()
												.multiply(
														BigDecimal
																.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED))))
												.doubleValue());
								orderItemUpdate
										.setHqj((orderItem.getSubtotalPirce().multiply(hqOneDividend
												.getGiftHqj()
												.multiply(
														BigDecimal
																.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED))))
												.doubleValue());
								orderItemUpdate.setId(orderItem.getId());
								orderItemUpdates.add(orderItemUpdate);*/
							/*}*/
							// 调用积分返利服务
/*							sqwAccountRecordService.recordAccountByUserbuy(
									user.getUserId(),
									OrderItemReturnTicketDtoList, firstUserId,
									secondUserId);*/
							// 计算订单总返券和总返分红
/*							order.setOrderHqj(sumReturn_hqq);
							order.setOrderFhed(sumReturn_fhed);*/

							customerOrderService.updateOrder(order);
/*							customerOrderService
									.updateOrderItems(orderItemUpdates);*/
							model.addAttribute("orderId", orderId);
							model.addAttribute("userId",
									currentUser.getUserId());
							model.addAttribute("msgCode", "SUCCESS");
							model.addAttribute("orderDto", orderDto);
							payResult.setMsgCode("SUCCESS");
							payResult.setOrderOrPayId(orderId);
							payResult.setPayAmount(orderDto.getPrice());
							model.addAttribute("back", payResult);
							UserOperationRecord userOperationRecord = new UserOperationRecord();
							userOperationRecord.setCreateTime(new Date());
							userOperationRecord.setOperationType("consume");
							userOperationRecord.setRemark("用户使用M券支付,已付款订单号:"
									+ orderId);
							userOperationRecord.setUserId(currentUser
									.getUserId().intValue());
							userOperationRecordService
									.saveUserOperationRecord(userOperationRecord);
							return "/myccig/order/payResult";
						} else {
							log.info("M卷支付失败，订单号:{} 支付状态:payStatus:{}",
									orderId, callBackpayResult.getPayStatus());
							model.addAttribute("orderId", orderId);
							model.addAttribute("userId",
									currentUser.getUserId());
							model.addAttribute("msgCode", "FA");
							model.addAttribute("orderDto", orderDto);
							payResult.setMsgCode("FA");
							payResult.setOrderOrPayId(orderId);
							payResult.setPayAmount(orderDto.getPrice());
							model.addAttribute("back", payResult);
							return "/myccig/order/payResult";
						}
					}
					if (2 == hqPayStatus) {
						log.info("账户M券余额不足,无法支付！订单号:{}", orderId);
						model.addAttribute("orderId", orderId);
						model.addAttribute("userId", currentUser.getUserId());
						model.addAttribute("msgCode", "FA");
						model.addAttribute("orderDto", orderDto);
						payResult.setMsgCode("FA");
						payResult.setOrderOrPayId(orderId);
						payResult.setPayAmount(orderDto.getPrice());
						model.addAttribute("back", payResult);
						return "/myccig/order/payResult";
					}
					if (1 == hqPayStatus) {
						log.info("账户被冻结,无法支付！订单号:{}", orderId);
						model.addAttribute("orderId", orderId);
						model.addAttribute("userId", currentUser.getUserId());
						model.addAttribute("msgCode", "FA");
						model.addAttribute("orderDto", orderDto);
						payResult.setMsgCode("FA");
						payResult.setOrderOrPayId(orderId);
						payResult.setPayAmount(orderDto.getPrice());
						model.addAttribute("back", payResult);
						return "/myccig/order/payResult";
					}
				}
				// 处理现金+M券支付（M券支付）
				if (act.equals(CommonConstant.HQQ_AND_MONEY_PAY)) {

					// 支付密码验证接口（暂时屏蔽）
					UserTrade userTrade = new UserTrade();
					userTrade.setUserId(currentUser.getUserId());
					userTrade.setPwd(pwd);
					UserTradeDto userTradeDto = userService
							.checkTradePwd(userTrade);
					if (Constants.SqwAccountRecordService.PWD_ERROR
							.equals(userTradeDto.getRetCode())
							|| Constants.SqwAccountRecordService.NO_PWD
									.equals(userTradeDto.getRetCode())
							|| Constants.SqwAccountRecordService.PWD_ERROR_FOR_UPDATE
									.equals(userTradeDto.getRetCode())
							|| Constants.SqwAccountRecordService.PWD_INPUT_TOO_MANY
									.equals(userTradeDto.getRetCode())) {
						model.addAttribute("orderId", orderId);
						model.addAttribute("act", "hqqAndMoneyPay");
						model.addAttribute("payAmount", orderDto.getPrice());
						return "myccig/order/checkTradePwd";
					}

					OneDividendRatio oneDividendRatio = dividendService
							.getOneDividendRatio();
					if (orderDto.getCashPrice() != null
							&& orderDto.getHqjPrice() != null) {

						if (oneDividendRatio == null
								|| oneDividendRatio.getTicketRation() == null
								|| oneDividendRatio.getCashRation() == null) {
							redirectAttributes.addAttribute("errorMsg",
									"请先设置现金+M券支付比例");
							return ResponseContant.REDIRECT
									+ RequestContant.CUS_ORDER
									+ RequestContant.CUS_ORDER_TO_PAY
									+ "?orderId=" + orderId;
						}
					}

					if (!"1".equals(orderDto.getMixPayStatus())) {
						// 调用M卷支付接口
						int hqPayStatus = 0;
						if (orderDto.getCashPrice() != null && orderDto.getHqjPrice() != null
								&& orderDto.getCashPrice().compareTo(BigDecimal.ZERO) == 1 && orderDto.getHqjPrice().compareTo(BigDecimal.ZERO) == 1) {

							hqPayStatus = psqwAccountRecordService.payOrderByVoucher(currentUser.getUserId(), orderId, orderDto.getHqjPrice(), 2, "线上券+现金支付时 --- 扣券");

						}
						// M卷支付成功
						if (PayConstant.HqPayTradeStatus.TRADE_SUCCESS.equals(String.valueOf(hqPayStatus))) {
							// M券支付成功
							Order order = new Order();
							Supplier supplier = supplierManagerService
									.findSupplier(Long.parseLong(orderDto
											.getMerchantid()));
							OrderDTO orderDtoNew = customerOrderService
									.getOrderDtoByOrderId(orderId);
							order.setCompanyQy(supplier.getCompanyQy());
							order.setId(orderDto.getId());
							order.setVersion(orderDtoNew.getVersion());
							order.setMixPayStatus("1");
							order.setAccountType("3");

							UserOperationRecord userOperationRecord = new UserOperationRecord();
							userOperationRecord.setCreateTime(new Date());
							userOperationRecord.setOperationType("consume");
							userOperationRecord.setRemark("用户使用M券支付+现金（已付M券,未付现金）,已付款订单号:" + orderId);
							userOperationRecord.setUserId(currentUser.getUserId().intValue());
							
							userOperationRecordService.saveUserOperationRecord(userOperationRecord);
							customerOrderService.updateOrder(order);
							redirectAttributes.addAttribute("errorMsg",
									"M券支付成功,请继续支付现金！");
							return ResponseContant.REDIRECT
									+ RequestContant.CUS_ORDER
									+ RequestContant.CUS_ORDER_TO_PAY
									+ "?orderId=" + orderId;
						}
						if (2 == hqPayStatus) {
							log.info(String.format("账户M券余额不足,无法支付！订单号:%s",
									orderId));
							redirectAttributes.addAttribute("errorMsg",
									"账户M券余额不足,无法支付！");
							return ResponseContant.REDIRECT
									+ RequestContant.CUS_ORDER
									+ RequestContant.CUS_ORDER_TO_PAY
									+ "?orderId=" + orderId;
						}
						if (1 == hqPayStatus) {
							log.info(String.format("账户被冻结,无法支付！订单号:%s", orderId));
							redirectAttributes.addAttribute("errorMsg", "账户被冻结,无法支付！");

							return ResponseContant.REDIRECT
									+ RequestContant.CUS_ORDER
									+ RequestContant.CUS_ORDER_TO_PAY
									+ "?orderId=" + orderId;
						}
					} else {
						redirectAttributes.addAttribute("errorMsg",
								"已支付完M券，请选择现金支付，完成订单支付！");
						return ResponseContant.REDIRECT
								+ RequestContant.CUS_ORDER
								+ RequestContant.CUS_ORDER_TO_PAY + "?orderId="
								+ orderId;
					}
				}

			} else {
				// 订单不是在待支付状态 不能进行支付
				// jumpUrl = "payError";
			}
			PayResult payResult = new PayResult();
			model.addAttribute("orderId", orderId);
			model.addAttribute("userId", currentUser.getUserId());
			model.addAttribute("msgCode", "ERROR");
			model.addAttribute("orderDto", orderDto);
			payResult.setMsgCode("ERROR");
			payResult.setOrderOrPayId(orderId);
			payResult.setPayAmount(orderDto.getPrice());
			model.addAttribute("back", payResult);
			return "/myccig/order/payResult";
		} catch (Exception e) {
			log.error("M券支付失败：{} 订单号:{}" + e.getMessage(), e, orderId);
			return null;
		}
	}

	/**
	 * 中行跨境 去支付
	 * 
	 * @param orderId
	 * @param act
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.ORDER_BOCC_PAY_INFO)
	public String boccPayInfo(Long orderId, String act, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("boccPayInfo execute ...");
		try {
			if (orderId == null || StringUtils.isEmpty(act)) {
				log.info("orderId is null or act is null");
				throw new BadRequestException("请求失败");
			}
			User currentUser = getCurrentUser(request);
			String sid = getUUID.getSessionId(request, response);
			String platForm = null;
			try {
				platForm = memcachedClient.get("payPlatForm" + sid);
				model.addAttribute("payPlatForm", platForm);
			} catch (TimeoutException e) {
				log.error(
						"memcached timeout get pay or platForm"
								+ e.getMessage(), e);
			} catch (InterruptedException e) {
				log.error(
						"memcached Interrupted get pay or platForm"
								+ e.getMessage(), e);
			} catch (MemcachedException e) {
				log.error("memcached get pay or platForm" + e.getMessage(), e);
			}
			OrderDTO orderDto = cusOrderService.findOrderByOrderId(orderId,
					currentUser);
			if (StringUtils.isEmpty(platForm)) {
				String orderJson = JsonUtil.serializeBeanToJsonString(orderDto);
				// 说明是android端支付的
				model.addAttribute("supplyType", orderDto.getSupplyType());
				model.addAttribute("status", orderDto.getStatus());
				model.addAttribute("userId", currentUser.getUserId());
				model.addAttribute("orderId", orderId);
				model.addAttribute("platForm", platForm);
				model.addAttribute("order", orderJson);
			}
			Short status = orderDto.getStatus();
			if (status != 1) {
				throw new BusinessException("order statu isn't 1, is " + status);
			}
			String jumpUrl;
			// 检查是否签约中行跨境协议支付
			BocCrossBorder bocCrossBorder = cusOrderPayService
					.findBoccSignByUserIdDefualt(currentUser);
			if (bocCrossBorder != null) {
				model.addAttribute("merchantName", MerchantName.BOCCROSS);
				model.addAttribute("orderId", orderId);
				model.addAttribute("amount", orderDto.getPrice());
				model.addAttribute("accNo",
						addPass(bocCrossBorder.getDraccNo()));
				model.addAttribute("phone", bocCrossBorder.getDbtPhone());
				model.addAttribute("signNo", bocCrossBorder.getTrxSerno());
				jumpUrl = ResponseContant.CUS_ORDER_PAY_BOC_CROSS;
			} else {
				model.addAttribute("orderId", orderId);
				model.addAttribute("act", act);
				jumpUrl = "/myccig/pay/boccSigning";
			}
			log.info("jumpUrl is " + jumpUrl);
			return jumpUrl;
		} catch (Exception e) {
			log.error("跳转支付页面失败：" + e.getMessage(), e);
			throw new BusinessException("跳转支付页面失败：" + e.getMessage(), e);
		}
	}

	/**
	 * 中行跨境
	 * 
	 * @param orderId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.TO_BOC_CROSS_PAY)
	public String toBoccPay(
			@Validated({ bocCrossPayCheck.class }) BocCrossSignVO bocCrossSignVO,
			BindingResult br, Long orderId, String signNo, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			// 请求参数验证
			checkRequest(br);
			if (orderId == null) {
				log.info("orderId is null or act is null");
				throw new BadRequestException("请求失败");
			}

			User currentUser = getCurrentUser(request);
			String sid = getUUID.getSessionId(request, response);
			String platForm = null;
			try {
				platForm = memcachedClient.get("payPlatForm" + sid);
			} catch (TimeoutException e) {
				log.error(
						"memcached timeout get pay or platForm"
								+ e.getMessage(), e);
			} catch (InterruptedException e) {
				log.error(
						"memcached Interrupted get pay or platForm"
								+ e.getMessage(), e);
			} catch (MemcachedException e) {
				log.error("memcached get pay or platForm" + e.getMessage(), e);
			}

			// 平台号
			Short channelNo = PayChannel.WAY_WAP;
			if (String.valueOf(PayChannel.WAY_ANDROID).equals(platForm)) {
				channelNo = PayChannel.WAY_ANDROID;
			}
			if (String.valueOf(PayChannel.WAY_IOS).equals(platForm)) {
				channelNo = PayChannel.WAY_IOS;
			}

			OrderDTO orderDto = cusOrderService.findOrderByOrderId(orderId,
					currentUser);
			if (!StringUtils.isEmpty(platForm)) {
				String orderJson = JsonUtil.serializeBeanToJsonString(orderDto);
				// 说明是android端支付的
				model.addAttribute("supplyType", orderDto.getSupplyType());
				model.addAttribute("status", orderDto.getStatus());
				model.addAttribute("userId", currentUser.getUserId());
				model.addAttribute("orderId", orderId);
				model.addAttribute("platForm", platForm);
				model.addAttribute("order", orderJson);
			}

			Short status = orderDto.getStatus();
			if (status != 1) {
				throw new BusinessException(
						"current order status isn't 1.throw exception.");
			}

			// 1 为待支付
			PayResult payResult = cusOrderPayService.boccPay(orderId,
					channelNo, currentUser, request, response, model, signNo);
			model.addAttribute("msgCode", payResult.getMsgCode());
			model.addAttribute("back", payResult);
			model.addAttribute("payType", String
					.valueOf(BankPayModel.PAY_TYPE_BOC_B2C_CROSSBORDER_WAP));
			return ResponseContant.CUS_ORDER_BOC_PAY_RESULT;

		} catch (Exception e) {
			log.error("支付失败：" + e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * 支付对账
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = RequestContant.TO_ACCOUNT_CHECK)
	public String toAccountCheck(
			@Validated({ accountCheck.class }) AccountCheckVO accountCheckVO,
			BindingResult br, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			// 请求参数验证
			checkRequest(br);
			Date startDate = DateUtil.strToDate(accountCheckVO.getStartDate());
			Date endDate = DateUtil.strToDate(accountCheckVO.getEndDate());
			cusOrderPayService.accountCheck(startDate, endDate,
					accountCheckVO.getBankPayModel(),
					accountCheckVO.getBizType(), accountCheckVO.getAct(),
					request, response, model);
			return ResponseContant.CUS_ORDER_PAY_RESULT;

		} catch (Exception e) {
			log.error("支付失败：" + e.getMessage(), e);
			throw new BusinessException(e.getMessage(), e);
		}
	}


	/**
	 * 跳转支付中间页(form表单跳转)
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.ORDER_PAY_INFO_MIDDLE)
	@AuthPassport
	public String toPayMiddle(Long orderId, String act, String planId,
			String planNum, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("toPayMiddle");

		if (orderId == null || StringUtils.isEmpty(act)) {
			log.info("orderId is null or act is null");
			throw new BadRequestException("请求失败");
		}
		// 平台号
		Short channelNo = CommonConstant.PAY_WAP_CHANNELNO;
		User currentUser = getCurrentUser(request);
		OrderDTO orderDto = cusOrderService.findOrderByOrderId(orderId,
				currentUser);
		Short status = orderDto.getStatus();

		if (status == 1) {
			// 银联支付
			if ("unionPay".equals(act)) {
				PayResult unionWapPayInfo = cusOrderPayService.unionWapPayInfo(
						orderId, channelNo, currentUser, request, response,
						model);

				// 非待支付状态 一律跳转订单列表首页
				if ("00".equals(unionWapPayInfo.getPayStatus())) {
					return ResponseContant.REDIRECT + RequestContant.CUS_ORDER
							+ RequestContant.CUS_MY_ALL_ORDER + "?pageNow=1";
				}
				log.info("===================银联支付请求参数 Start===================");
				log.info(JsonUtil.serializeBeanToJsonString(unionWapPayInfo));
				log.info("===================银联支付请求参数 End===================");

				model.addAttribute("payResult", unionWapPayInfo);
				model.addAttribute("payType",
						CommonConstant.PAY_TYPE_UNION_WAPTPAY);
			}

			if ("jdPay".equals(act)) {
				PayResult jdPayInfo = cusOrderPayService.jdPayInfo(orderId,
						channelNo, currentUser, request, response, model);

				// 非待支付状态 一律跳转订单列表首页
				if ("00".equals(jdPayInfo.getPayStatus())) {
					return ResponseContant.REDIRECT + RequestContant.CUS_ORDER
							+ RequestContant.CUS_MY_ALL_ORDER + "?pageNow=1";
				}
				log.info("===================京东支付请求参数 Start===================");
				log.info(JsonUtil.serializeBeanToJsonString(jdPayInfo));
				log.info("===================京东支付请求参数 End===================");

				model.addAttribute("payResult", jdPayInfo);
				model.addAttribute("payType", CommonConstant.PAY_TYPE_JD_WAP);
			}

			// 中行信用卡快捷
			if (CommonConstant.NCPKJ_PAY.equals(act)) {
				log.info("=====start cusOrderPayService.ncpkjPay()=====");
				PayResult ncpPayResult = cusOrderPayService.ncpPay(orderId,
						channelNo, currentUser, request, response, model,
						BocNcpTxnSubType.DEFAULT, null, null);

				log.info("===================银联支付请求参数 Start===================");
				log.info(JsonUtil.serializeBeanToJsonString(ncpPayResult));
				log.info("===================银联支付请求参数 End===================");

				model.addAttribute("payResult", ncpPayResult);
				model.addAttribute("payType", BankPayModel.PAY_TYPE_NCPKJ_PAY);
			}
			// 中行信用卡分期
			if (CommonConstant.NCPFQ_PAY.equals(act)) {
				log.info("=====start cusOrderPayService.ncpPay()=====");
				planId = "IP10";
				planNum = "03";
				PayResult ncpPayResult = cusOrderPayService.ncpPay(orderId,
						channelNo, currentUser, request, response, model,
						BocNcpTxnSubType.PLAN, planId, planNum);

				log.info("===================银联支付请求参数 Start===================");
				log.info(JsonUtil.serializeBeanToJsonString(ncpPayResult));
				log.info("===================银联支付请求参数 End===================");

				model.addAttribute("payResult", ncpPayResult);
				model.addAttribute("payType", BankPayModel.PAY_TYPE_NCPFQ_PAY);
			}

			// 翼支付WAP
			if (CommonConstant.BESTOAY_WAP_DIRECT.equals(act)) {
				log.info("sart bestoay proccessing...");
				PayResult bestoayPay = cusOrderPayService.bestoayPay(orderId,
						channelNo, currentUser, request, response, model, act);
				String productName = orderDto.getOrderItemDTOs().get(0)
						.getpName();
				if (productName != null && productName.length() > 8) {
					productName = productName.substring(0, 8) + "...";
				}
				log.info("翼支付WAP支付请求参数");
				log.info(JsonUtil.serializeBeanToJsonString(bestoayPay));
				model.addAttribute("payResult", bestoayPay);
				model.addAttribute("payType",
						BankPayModel.PAY_TYPE_BESTOAYPAY_MOBILE_WAP);
				model.addAttribute("productDesc", productName);
				model.addAttribute("subject", productName);
				log.info("bestoay proccess end!!");
				return bestoayPay.getAction();
			}
		}

		return ResponseContant.CUS_ORDER_PAY_MIDDLE;

	}

	// @AuthPassport
	@ResponseBody
	@RequestMapping(value = RequestContant.SEND_MOBILE_CODE)
	public String sendMobileCode(HttpServletRequest request,
			HttpServletResponse response) {
		User user = null;
		String sid = getUUID.getSessionId(request, response);
		String orderIdUserId = null;
		try {
			orderIdUserId = memcachedClient.get("pay" + sid);
		} catch (TimeoutException e) {
			log.error("memcached timeout get pay or platForm" + e.getMessage(),
					e);
		} catch (InterruptedException e) {
			log.error(
					"memcached Interrupted get pay or platForm"
							+ e.getMessage(), e);
		} catch (MemcachedException e) {
			log.error("memcached get pay or platForm" + e.getMessage(), e);
		}
		if (!StringUtils.isBlank(orderIdUserId)) {
			String[] split = orderIdUserId.split(" ");
			String userId = split[0];
			// setAppParam(model, orderId, userId, platForm, payResult);
			/*
			 * user = new User(); user.setUserId(Long.valueOf(userId));
			 */
			user = customerService.findUserById(Long.valueOf(userId));
		} else {
			user = getCurrentUser(request);
		}
		try {
			String LoseEfficacy = memcachedClient
					.get("boc_cross_pay_LoseEfficacy" + user.getMobile()
							+ user.getUserId());
			if (LoseEfficacy != null && "LoseEfficacy".equals(LoseEfficacy)) {
				log.info("发送短信验证码过于频繁");
				return "codeNotLoseEfficacy";
			}

		} catch (TimeoutException e1) {
			log.error("获取 memcached验证码超时", e1);
		} catch (InterruptedException e1) {
			log.error("获取 memcached验证码失败", e1);
		} catch (MemcachedException e1) {
			log.error("获取 memcached验证码失败", e1);
		}
		Integer value = SendSMSUtil.sendMessage(user.getMobile());// 发送短信
																	// 返回值为验证码的值
		if (null != value) {
			log.info("验证码=" + value);
			try {
				memcachedClient.set(
						"boc_cross_pay_mobileCode" + user.getMobile()
								+ user.getUserId(), Constants.MESSAGE_OUT_TIME,
						value);
				memcachedClient.set(
						"boc_cross_pay_LoseEfficacy" + user.getMobile()
								+ user.getUserId(),
						Constants.LOSE_EFFICACY_TIME, "LoseEfficacy");
			} catch (Exception e) {
				log.error("验证码缓存失败+", e);
				return "验证码发送失败!";
			}
			return "success" + value;
		} else {
			return "验证码发送失败!";
		}

	}

	public static String addPass(String draccNo) {

		String substring = draccNo.substring(4, draccNo.length() - 4);

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < substring.length(); i++) {
			if (i % 4 == 0) {
				sb.append(" ");
			}
			sb.append("*");
		}
		return draccNo.replaceAll("(\\d{4})(\\d{" + substring.length()
				+ "})(\\d{4})", "$1 " + sb.toString() + " $3");
	}

	/**
	 * 去支付
	 * 
	 * @param orderId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = RequestContant.TO_WEIXIN_GZH_PAY)
	public void toWeiXinGZHPay(Long orderId, String openId, String nonceStr,
			String timestamp, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("to auth code execute....");

		log.info("toPay execute....");
		if (orderId == null) {
			log.error("orderId is null");
			throw new BadRequestException("call toPay errror ");
		}

		/*
		 * 逻辑
		 */
		User currentUser = getCurrentUser(request);
		OrderDTO findOrderByOrderId = cusOrderService.findOrderByOrderId(
				orderId, currentUser);

		// 支付前检查订单状态
		String supplyType = findOrderByOrderId.getSupplyType();
		Short orderStatus = findOrderByOrderId.getStatus();

		log.info("orderStatus:" + orderStatus);
		WeiXinGZHVO weiXinGZH = new WeiXinGZHVO();
		switch (orderStatus) {
		// 待支付状态
		case 1:
			model.addAttribute("orderId", orderId);
			model.addAttribute("supplyType", supplyType);
			model.addAttribute("openId", openId);
			PayResult pay = cusOrderPayService.weiXinGZHPay(orderId, openId,
					nonceStr, timestamp, CommonConstant.PAY_WAP_CHANNELNO,
					currentUser, request, response, model,
					CommonConstant.WEIXIN_GZH_PAY);
			weiXinGZH.setPayFlowNo(pay.getLocalFlowNo());
			weiXinGZH.setOrderId(orderId);
			log.info("微信公从号 支付信息 {}", pay);
			Map<String, String> paramsMap = pay.getRequestParams();
			weiXinGZH.setAppId(paramsMap.get("appId"));
			weiXinGZH.setTimeStamp(paramsMap.get("timeStamp"));
			weiXinGZH.setPackager(paramsMap.get("package"));
			weiXinGZH.setPrepayId(paramsMap.get("prepayid"));
			weiXinGZH.setNonceStr(paramsMap.get("nonceStr"));
			weiXinGZH.setPaySign(paramsMap.get("paySign"));
			weiXinGZH.setSignType(paramsMap.get("signType"));
			weiXinGZH.setErr_msg(paramsMap.get("err_msg"));
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				String strPayJson = JsonUtil
						.serializeBeanToJsonString(weiXinGZH);
				log.info("strPayJson-->" + strPayJson);
				out.write(strPayJson);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				out.flush();
				out.close();
			}

			// return JsonUtil.serializeBeanToJsonString(payJson);
			// 其他状态
		default:
			// return "error";
		}
	}


    /**
     * 银联线上支付
     * @param orderId
     * @param openId
     * @param payType
     * @param request
     * @return
     */
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = "/chinumsOnlinePay")
	public PayResult chinaumsOnlinePay(Long orderId, String openId, String payType, HttpServletRequest request) {
		if (orderId == null) {
			throw new BadRequestException("orderId不可为空");
		}

		User currentUser = getCurrentUser(request);
		// 获取订单
		OrderDTO order = cusOrderService.findOrderByOrderId(orderId, currentUser);
		if (order == null || order.getStatus() != 1) {
			throw new RuntimeException("订单信息异常");
		}

		// 取IP
		String ip;
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		// 取商户信息
		Supplier supplier = supplierManagerService.findSupplier(Long.valueOf(order.getMerchantid()));


		// --------------------  银联商务下单  -----------------------------
		Map<String, String> map = new TreeMap<String, String>();
		map.put("subOpenId", openId);
		map.put("attachedData", order.getHqjPrice().toString());
		map.put("shopName", supplier.getNameJC());
        map.put("returnUrl", "http://zrap2c.natappfree.cc/orderPay/chinaumsOnlinePayReturn");
		if (ThirdAccountConstant.ACCOUNT_WEIXIN.equalsIgnoreCase(payType)) {
			map.put("msgType", PayConstant.PayClient.CLIENT_WEIXIN);
		} else if (ThirdAccountConstant.ACCOUNT_ALIPAY.equalsIgnoreCase(payType)) {
			map.put("msgType", PayConstant.PayClient.CLIENT_ALI);
		} else {
		    log.error("payType不可为空");
			throw new RuntimeException("payType不可为空");
		}



		PayRequest payRequest = new PayRequest();
		payRequest.setBankPayModel(BankPayModel.PAY_TYPE_CHINAUMS_WAP);        // 银联商务wap支付
		payRequest.setB2C(true);                                        		// b2c
		payRequest.setIp(ip);                                            	// IP
		payRequest.setOrderId(order.getId());                            	// 订单ID
		payRequest.setRetailerId(order.getUserId());                		// 消费者ID
		payRequest.setChannelNo(PayChannel.WAY_WAP);                    	// 支付渠道
		payRequest.setOrderDate(new Date());                            	// 支付时间
		payRequest.setPayModel(PayConstant.PaymentType.PAYMENT_TYPE_CODE_ALL);
		payRequest.setRequestParams(map);                                	// 支付参数

		if (order.getMixPayStatus() == null || !order.getMixPayStatus().equals("1")) {
			payRequest.setPayAmount(order.getPrice());
		} else {
			if (order.getCashPrice() != null && order.getHqjPrice() != null
					&& order.getCashPrice().compareTo(BigDecimal.ZERO) > 0
					&& order.getHqjPrice().compareTo(BigDecimal.ZERO) > 0) {

				payRequest.setPayAmount(order.getCashPrice());
			} else {
                throw new RuntimeException("订单信息异常");
			}
		}

		// 银联商务下单
		PayResult payResult = bankPayService.pay(payRequest);


		log.info("线上支付url：{}",payResult.getAction());

		return payResult;
	}



	/**
	 * 去支付
	 * 
	 * @param orderId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = RequestContant.TO_WEIXIN_GZH_PAY_TEST)
	public void toWeiXinGZHPayTest(Long orderId, String openId,
			String nonceStr, String timestamp, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("to auth code execute....");

		log.info("toPay execute....");
		if (orderId == null) {
			log.error("orderId is null");
			throw new BadRequestException("call toPay errror ");
		}
		/*
		 * 逻辑
		 */
		User currentUser = getCurrentUser(request);
		OrderDTO findOrderByOrderId = cusOrderService.findOrderByOrderId(
				orderId, currentUser);

		// 支付前检查订单状态
		String supplyType = findOrderByOrderId.getSupplyType();
		Short orderStatus = findOrderByOrderId.getStatus();

		log.info("orderStatus:" + orderStatus);
		WeiXinGZHVO weiXinGZH = new WeiXinGZHVO();
		switch (orderStatus) {
		// 待支付状态
		case 1:
			model.addAttribute("orderId", orderId);
			model.addAttribute("supplyType", supplyType);
			model.addAttribute("openId", openId);
			PayResult pay = cusOrderPayService.weiXinGZHPay(orderId, openId,
					nonceStr, timestamp, CommonConstant.PAY_WAP_CHANNELNO,
					currentUser, request, response, model,
					CommonConstant.WEIXIN_GZH_PAY);
			log.info("微信公从号 支付信息 {}", pay);
			weiXinGZH.setPayFlowNo(pay.getLocalFlowNo());
			weiXinGZH.setOrderId(orderId);
			Map<String, String> paramsMap = pay.getRequestParams();
			weiXinGZH.setAppId(paramsMap.get("appId"));
			weiXinGZH.setTimeStamp(paramsMap.get("timeStamp"));
			weiXinGZH.setPackager(paramsMap.get("package"));
			weiXinGZH.setPrepayId(paramsMap.get("prepayid"));
			weiXinGZH.setNonceStr(paramsMap.get("nonceStr"));
			weiXinGZH.setPaySign(paramsMap.get("paySign"));
			weiXinGZH.setSignType(paramsMap.get("signType"));
			weiXinGZH.setErr_msg(paramsMap.get("err_msg"));
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				String strPayJson = JsonUtil
						.serializeBeanToJsonString(weiXinGZH);
				log.info("strPayJson-->" + strPayJson);
				out.write(strPayJson);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				out.flush();
				out.close();
			}

			// return JsonUtil.serializeBeanToJsonString(payJson);
			// 其他状态
		default:
			// return "error";
		}
	}

	@RequestMapping(value = RequestContant.QUERY_WEIXIN_GZH_PAY)
	public String queryWeiXinPay(String payFlowNo, Long orderId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(payFlowNo)) {
			log.error("payFlowNo is null");
			throw new NullPointerException("payFlowNo is null");
		}
		QueryResult result = cusOrderPayService.queryWeiXinResult(payFlowNo);
		log.info("queryWeiXinPay result-->{}", result);
		PayResult back = new PayResult(PayRecordStatus.SUCCESS);
		if (BankPayStatus.CODEA.equals(result.getStatus())) {
			back.setPayStatus(PayRecordStatus.SUCCESS);
			back.setMsgCode(CommonConstant.PayMsg.SUCCESS);
		} else {
			back.setPayStatus(PayRecordStatus.FAIL);
			back.setMsgCode(CommonConstant.PayMsg.FAIL);
		}
		back.setOrderOrPayId(orderId);
		back.setPayAmount(new BigDecimal(result.getPayAmount() == null ? "0"
				: result.getPayAmount()));
		back.setPayModel(PayConstant.BankPayModel.PAY_TYPE_WEIXIN_GZH);
		model.addAttribute("back", back);
		model.addAttribute("payType",
				PayConstant.BankPayModel.PAY_TYPE_WEIXIN_GZH);
		// model.addAttribute("orderDto", orderDto);
		return ResponseContant.CUS_ORDER_PAY_RESULT;
	}

	@RequestMapping(value = RequestContant.WEIXIN_TOKEN)
	public String weiXinGZHToken(Model model,
			@RequestParam Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("params-->" + params.toString());
		String token = params.get("token");
		/*
		 * if(!CommonConstant.WEIXIN_TOKEN.equals(token)){ throw new
		 * PayException("check token fail"); }
		 */
		response.reset();
		return params.get("echostr");
		// model.addAttribute("echostr", params.get("echostr"));
	}

	private Map<String, String> HqPayParamMap(Long orderId, String flowNo,
			String tradeStatus, String totalFee, String refundStatus) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("notify_time", DateUtil.getCurrentDateYyyyMmDdHhmiss());
		map.put("trade_no", String.valueOf(orderId));
		map.put("out_trade_no", flowNo);
		map.put("trade_status", tradeStatus);
		map.put("currency", AlipayCurrency.PAY_TYPE_RMB);
		map.put("total_fee", totalFee);
		map.put("refund_status", refundStatus);
		return map;
	}

}
