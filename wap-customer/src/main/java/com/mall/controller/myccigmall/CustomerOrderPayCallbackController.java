package com.mall.controller.myccigmall;

import com.mall.commons.utils.DateUtil;
import com.mall.constant.CommonConstant;
import com.mall.constant.CommonConstant.PayMsg;
import com.mall.constant.OrderConstants;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.constant.RecordBkStatus;
import com.mall.customer.dto.OrderItemReturnTicketDto;
import com.mall.customer.dto.TurnoverDto;
import com.mall.customer.model.*;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.po.Order;
import com.mall.customer.order.po.OrderItem;
import com.mall.customer.service.*;
import com.mall.dealer.productTags.api.ProductTagsService;
import com.mall.exception.BusinessException;
import com.mall.pay.api.rpc.B2CPayRecodeService;
import com.mall.pay.api.rpc.PayRecordServer;
import com.mall.pay.common.ConvertUtils;
import com.mall.pay.common.PayConstant;
import com.mall.pay.common.PayConstant.BankPayModel;
import com.mall.pay.common.PayConstant.BestoayNotifyResult;
import com.mall.pay.common.PriceUtils;
import com.mall.pay.common.StringUtils;
import com.mall.pay.dto.BocNcpResponseDto;
import com.mall.pay.dto.CheckResult;
import com.mall.pay.dto.PayResult;
import com.mall.pay.dto.QueryResult;
import com.mall.pay.po.PayRecord;
import com.mall.pay.po.alipay.AliMobilePayResult;
import com.mall.pay.po.bestoay.BestoayMobilePayResultUM;
import com.mall.pay.po.bestoay.BestoayWapPayNotify;
import com.mall.service.CusOrderPayService;
import com.mall.service.CusOrderService;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierSalesDiscount;
import com.mall.supplier.service.SupplierDiscountService;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.utils.*;
import com.mall.vo.WeiXinPayResult;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeoutException;

//import com.mall.controller.impl.TelecomOrderPayCallBackControllerImpl;

/**
 * 支付方式回调
 * 
 * @author ccigQA01
 * 
 */
@Controller
@RequestMapping(value = RequestContant.ORDER_PAY)
public class CustomerOrderPayCallbackController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(CustomerOrderPayCallbackController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	// 支付的服务
	@Autowired
	private CusOrderPayService cusOrderPayService;
	// 订单 的服务
	@Autowired
	private CusOrderService cusOrderService;

	@Autowired
	private SupplierManagerService supplierManagerService;

	@Autowired
	private PsqwAccountRecordService psqwAccountRecordService;

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private UserService userService;

	@Autowired
	private B2CPayRecodeService b2CPayRecodeService;

	@Autowired
	private SqwAccountRecordService sqwAccountRecordService;
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
	private UserMsgService userMsgService;

	@Autowired
	public SupplierDiscountService supplierDiscountService;
	@Autowired
	public ConsumeService consumeService;

	@Autowired
	private PayRecordServer payRecordServer;


	/**
	 * 支付宝支付return返回
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.ALI_RETURN_PAY_CALLBACK, method = RequestMethod.GET)
	// @AuthPassport
	public String aliPayReturn(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info(" aliPayReturn call back execute.......");

		String sign = request.getParameter("sign");
		log.info("sign ...." + sign);

		Map<String, String> allRequestParam = ParameterTool.getAllRequestParam(request);

		log.info("return ---requestparames:{" + allRequestParam + "}");

		// 如果返回码都为空 就是支付失败
		if (StringUtils.isBlank(allRequestParam.get("out_trade_no"))) {
			PayResult callBackError = new PayResult();
			callBackError.setMsgCode("F");

			// String sid = request.getSession().getId();
			String sid = getUUID.getSessionId(request, response);
			String orderIdUserId = null;
			try {
				orderIdUserId = memcachedClient.get("pay" + sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!"".equals(orderIdUserId) && orderIdUserId != null) {
				String[] split = orderIdUserId.split(" ");
				String userId = split[0];
				String orderId = split[1];

				// 判断是否活动是否为0元购
				setActivityReturnUrl(userId, model);
				setActivityNewReturnUrl(userId, model);

				Long orderOrPayId = callBackError.getOrderOrPayId();
				// 说明是android端支付的
				if (("" + orderOrPayId).equals(orderId)) {
					model.addAttribute("userId", userId);
					model.addAttribute("orderId", orderId);
					model.addAttribute("msgCode", callBackError.getMsgCode());
				}
			}

			model.addAttribute("back", callBackError);
			return ResponseContant.CUS_ORDER_PAY_RESULT;
		}

		PayResult callBack = cusOrderPayService.aliMobileCallBack(allRequestParam, CommonConstant.PAY_TYPE_ALIPAY_WAP);
		if (callBack == null) {
			log.error("callback is null ");
			throw new BusinessException("call back is null ");
		}

		String sid = getUUID.getSessionId(request, response);
		String orderIdUserId = null;
		try {
			orderIdUserId = memcachedClient.get("pay" + sid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!"".equals(orderIdUserId) && orderIdUserId != null) {
			String[] split = orderIdUserId.split(" ");
			String userId = split[0];
			String orderId = split[1];

			// 判断是否活动是否为0元购
			setActivityReturnUrl(userId, model);
			setActivityNewReturnUrl(userId, model);

			Long orderOrPayId = callBack.getOrderOrPayId();
			// 说明是android端支付的
			if (("" + orderOrPayId).equals(orderId)) {
				model.addAttribute("userId", userId);
				model.addAttribute("orderId", orderId);
				model.addAttribute("msgCode", callBack.getMsgCode());
			}
		}

		User user = getCurrentUser(request);
		// 判断是否活动是否为0元购
		setActivityReturnUrl(String.valueOf(user.getUserId()), model);
		setActivityNewReturnUrl(String.valueOf(user.getUserId()), model);

		// 平台订单号
		Long orderId = callBack.getOrderOrPayId();

		model.addAttribute("back", callBack);

		return ResponseContant.CUS_ORDER_PAY_RESULT;

	}

	/**
	 * 支付宝支付 notify返回 检验
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	// @AuthPassport
	@RequestMapping(value = RequestContant.ALI_NOTIFY_PAY_CALLBACK, method = RequestMethod.POST)
	public String aliPayNotify(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("aliPayNotify call back execute.......");
		String sign = request.getParameter("sign");
		log.info("sign ...." + sign);
		Map<String, String> allRequestParam = ParameterTool.getAllRequestParam(request);
		log.info("notify--requestparames:{" + allRequestParam + "}");
		log.info("return call back map is " + allRequestParam);
		PayResult callBack = cusOrderPayService.aliMobileCallBack(allRequestParam, CommonConstant.PAY_TYPE_ALIPAY_WAP);
		if (callBack == null) {
			log.error("callback is null ");
			throw new BusinessException("call back is null ");
		}
		model.addAttribute("back", callBack);
		return null;
	}

	@RequestMapping(value = RequestContant.ALI_INLAND_MOBILE_PAY_CALLBACK, method = RequestMethod.POST)
	public void aliInlandMobilePayCallBackNotify(@RequestParam Map<String, String> requestParams,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("ali SDK notify--requestparames:{" + requestParams + "}");
		PrintWriter writer = null;
		try {
			AliMobilePayResult aliMobilePayResult = (AliMobilePayResult) ConvertUtils.mapToBean(requestParams,
					AliMobilePayResult.class);
			// 开始返利，积分
			Long orderIdByLong = b2CPayRecodeService.getOrderIdByPayFlowNo(aliMobilePayResult.getOut_trade_no());
			PayRecord payRecord = b2CPayRecodeService.getB2COrderPayRecordByOrderId(orderIdByLong);
			if (payRecord != null) {
				log.error("手机支付宝支付已成功回调，拦截重复回调请求");
				return;
			}
			writer = response.getWriter();
			User user = null;
			cusOrderPayService.aliMobileCallBack(requestParams, PayConstant.BankPayModel.PAY_TYPE_ALIPAY_INLAND_MOBILE);

			if (orderIdByLong == null) {
				log.error("pc支付回调未积分,无法通过支付流水号找到订单号,requestparames:{" + requestParams + "}");
			} else {
				List<OrderItemReturnTicketDto> OrderItemReturnTicketDtoList = new ArrayList<OrderItemReturnTicketDto>();
				// long orderIdByLong = Long.parseLong(orderId);
				OrderDTO orderDto = customerOrderService.getOrderDtoByOrderId(orderIdByLong);
				List<OrderItem> orderItemList = customerOrderService.getOrderItemByOrderId(orderIdByLong);
				user = userService.findUserById(orderDto.getUserId());
				Long firstUserId = null;
				Long secondUserId = null;
				if (!StringUtils.isEmpty(user.getTjMobile())) {
					User firstTjUser = userService.findUserByMobile(user.getTjMobile());
					if (firstTjUser != null) {
						firstUserId = firstTjUser.getUserId();
						if (!StringUtils.isEmpty(firstTjUser.getTjMobile())) {
							User secondTjUser = userService.findUserByMobile(firstTjUser.getTjMobile());
							if (secondTjUser != null)
								secondUserId = secondTjUser.getUserId();
						}
					}
				}

				Order order = new Order();
				OrderDTO orderDtoNew = customerOrderService.getOrderDtoByOrderId(orderIdByLong);
				Supplier supplier = supplierManagerService.findSupplier(Long.parseLong(orderDto.getMerchantid()));

				// 红旗券+现金支付
				// if("1".equals(orderDto.getMixPayStatus())){

				Double sumReturn_hqq = 0d;
				Double sumReturn_fhed = 0d;

				order.setCompanyQy(supplier.getCompanyQy());
				order.setPayWay(Constants.SqwAccountRecordService.ALIPAY_WAY);
				order.setId(orderDto.getId());
				order.setVersion(orderDtoNew.getVersion());
				List<OrderItem> orderItemUpdates = new ArrayList<OrderItem>();
				for (OrderItem orderItem : orderItemList) {

					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "hqb", 4) != null) {
						CashierRecord cashierRecord = new CashierRecord();
						cashierRecord.setUserId(orderDto.getUserId());
						cashierRecord.setOrderNo(orderIdByLong);
						cashierRecord.setCommodityName(orderItem.getpName());
						cashierRecord.setStartDate(new Date());
						cashierRecord.setSpecDesc(orderItem.getUnit());
						cashierRecord.setAmount(orderItem.getSkuQty());
						cashierRecord.setAmountOfMoney(orderItem.getSubtotalPirce());
						cashierRecord.setType(1);
						cashierService.insertCashierRecord(cashierRecord);
					}
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "sxb", 4) != null) {
						CashierRecord cashierRecord = new CashierRecord();
						cashierRecord.setUserId(orderDto.getUserId());
						cashierRecord.setOrderNo(orderIdByLong);
						cashierRecord.setCommodityName(orderItem.getpName());
						cashierRecord.setStartDate(new Date());
						cashierRecord.setSpecDesc(orderItem.getUnit());
						cashierRecord.setAmount(orderItem.getSkuQty());
						cashierRecord.setAmountOfMoney(orderItem.getSubtotalPirce());
						cashierRecord.setType(2);
						cashierService.insertCashierRecord(cashierRecord);
					}
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "jtb", 4) != null) {
						HomeNumRecord homeNumRecord = new HomeNumRecord();
						homeNumRecord.setUserId(orderDto.getUserId().intValue());

						homeNumRecord.setProductId(orderItem.getPid().toString());
						homeNumRecord.setOrderNo(orderIdByLong);
						homeNumRecord.setCommodityName(orderItem.getpName());
						homeNumRecord.setStartDate(new Date());
						homeNumRecord.setSpecDesc(orderItem.getUnit());

						homeNumRecord.setAmountOfMoney(orderItem.getSubtotalPirce());

						homeNumRecordService.insertHomeNumRecord(homeNumRecord);
					}
					// 会员企业
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "qyb", 4) != null) {
						SupplierNumRecord supplierNumRecord = new SupplierNumRecord();
						supplierNumRecord.setUserId(orderDto.getUserId().intValue());

						supplierNumRecord.setProductId(orderItem.getPid().toString());
						supplierNumRecord.setOrderNo(orderIdByLong);
						supplierNumRecord.setCommodityName(orderItem.getpName());
						supplierNumRecord.setStartDate(new Date());
						supplierNumRecord.setSpecDesc(orderItem.getUnit());

						supplierNumRecord.setAmountOfMoney(orderItem.getSubtotalPirce());

						supplierNumRecordService.insertSupplierNumRecord(supplierNumRecord);

					}
					/*
					 * OneDividend moneyOneDividendMoneyAndHqq = dividendService
					 * .findOneDivByType(
					 * Constants.SqwAccountRecordService.HQ_MONEY_PAY,
					 * Integer.parseInt
					 * (supplier.getCompanyQy()),orderItem.getPid());
					 */
					// 红旗券返券比例
					OneDividend hqOneDividend = dividendService.findOneDivByType(
							Constants.SqwAccountRecordService.HQ_PAY, Integer.parseInt(supplier.getCompanyQy()),
							orderItem.getPid());
					// 现金返券比例
					OneDividend moneyOneDividend = dividendService.findOneDivByType(
							Constants.SqwAccountRecordService.MONEY_PAY, Integer.parseInt(supplier.getCompanyQy()),
							orderItem.getPid());
					double fq = 0d;
					double fhed = 0d;

					OrderItemReturnTicketDto orderItemReturnTicketDto = new OrderItemReturnTicketDto();
					OrderItem orderItemUpdate = new OrderItem();
					orderItemReturnTicketDto.setpNum(orderItem.getSkuQty());
					orderItemReturnTicketDto.setProductSkuId(orderItem.getSkuId());
					orderItemReturnTicketDto.setProductRegin(supplier.getCompanyRegion());
					orderItemReturnTicketDto.setRefObjType(Constants.intONE);
					orderItemReturnTicketDto.setRefObjId(orderIdByLong);
					// 判断是否是现金比例商品或幸福购商品
					if (orderItem.getCashPrice() != null
							&& orderItem.getCashPrice().compareTo(BigDecimal.valueOf(0)) == 1) {

						if ("1".equals(orderDto.getMixPayStatus())) {
							orderItemReturnTicketDto.setPrice_cash(
									orderItem.getSubtotalCashPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(
									orderItem.getSubtotalhqjPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
						} else {
							orderItemReturnTicketDto
									.setPrice_cash(orderItem.getSubtotalPirce().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(BigDecimal.valueOf(0d));
						}

						// 现金数*现金返券+红旗券数*红旗券返券比例
						fq = orderItem.getSubtotalCashPrice()
								.multiply(moneyOneDividend.getGiftHqj()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP)
								.add(orderItem.getSubtotalhqjPrice()
										.multiply(hqOneDividend.getGiftHqj()
												.multiply(BigDecimal
														.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
										.setScale(0, BigDecimal.ROUND_HALF_UP))
								.doubleValue();

						fhed = orderItem.getSubtotalCashPrice()
								.multiply(moneyOneDividend.getGiftFhed()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP)
								.add(orderItem.getSubtotalhqjPrice()
										.multiply(hqOneDividend.getGiftFhed()
												.multiply(BigDecimal
														.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
										.setScale(0, BigDecimal.ROUND_HALF_UP))
								.doubleValue();

					} else {
						if ("1".equals(orderDto.getMixPayStatus())) {
							orderItemReturnTicketDto.setPrice_cash(orderItem.getSubtotalPirce()
									.multiply(orderDto.getCashRatio()).setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(orderItem.getSubtotalPirce()
									.multiply(orderDto.getHqRatio()).setScale(0, BigDecimal.ROUND_HALF_UP));
						} else {
							orderItemReturnTicketDto
									.setPrice_cash(orderItem.getSubtotalPirce().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(BigDecimal.valueOf(0d));
						}

						fq = orderItem.getSubtotalPirce()
								.multiply(hqOneDividend.getGiftHqj()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
						fhed = orderItem.getSubtotalPirce()
								.multiply(hqOneDividend.getGiftFhed()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();

					}
					sumReturn_hqq += fq;
					sumReturn_fhed += fhed;

					OrderItemReturnTicketDtoList.add(orderItemReturnTicketDto);
					orderItemReturnTicketDto.setReturn_fhed(new BigDecimal(fhed));
					orderItemReturnTicketDto.setReturn_hqq(new BigDecimal(fq));
					orderItemUpdate.setFhed(fhed);
					orderItemUpdate.setHqj(fq);
					orderItemUpdates.add(orderItemUpdate);
				}
				// 调用积分返利服务
				sqwAccountRecordService.recordAccountByUserbuy(user.getUserId(), OrderItemReturnTicketDtoList,
						firstUserId, secondUserId);
				// 计算订单总返券和总返分红
				order.setOrderHqj(sumReturn_hqq);
				order.setOrderFhed(sumReturn_fhed);

				customerOrderService.updateOrder(order);
				customerOrderService.updateOrderItems(orderItemUpdates);
				UserOperationRecord userOperationRecord = new UserOperationRecord();
				userOperationRecord.setCreateTime(new Date());
				userOperationRecord.setOperationType("consume");
				userOperationRecord.setRemark("用户使用支付宝手机支付,已付款订单号:" + orderIdByLong);
				userOperationRecord.setUserId(user.getUserId().intValue());
				userOperationRecordService.saveUserOperationRecord(userOperationRecord);

			}
			writer.write(SUCCESS);
		} catch (Exception e) {
			log.info("支付宝SDK notifyUrl异常" + e.getMessage(), e);
			writer.write(FAIL);
		}
	}

	@RequestMapping(value = RequestContant.ALI_MOBILENOTIFY_PAY_CALLBACK, method = RequestMethod.POST)
	public void aliMobilePayCallBackNotify(@RequestParam Map<String, String> requestParams, HttpServletRequest request,
			HttpServletResponse response) {

		log.info("ali SDK notify--requestparames:{" + requestParams + "}");
		try {
			cusOrderPayService.aliMobileCallBack(requestParams, PayConstant.BankPayModel.PAY_TYPE_ALIPAY_MOBILE);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.print("success");

		} catch (Exception e) {
			log.info("支付宝SDK notifyUrl异常" + e.getMessage(), e);
		}
	}

	/************* 支付宝国内支付回调 begin ******************/

	/**
	 * 支付宝国内支付return返回
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.ALI_DIRECT_RETURN_PAY_CALLBACK)
	public String aliDirectReturn(@RequestParam Map<String, String> requestParams, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info(" aliDirectWapReturn call back execute.......");
		log.info("return ---requestparames:{" + requestParams + "}");
		// 如果返回码都为空 就是支付失败
		if (StringUtils.isBlank(requestParams.get("out_trade_no"))) {
			log.info("out-trade-no=" + requestParams.get("out_trade_no"));
			PayResult callBackError = new PayResult();
			callBackError.setMsgCode("F");
			String sid = getUUID.getSessionId(request, response);
			String orderIdUserId = null;
			try {
				orderIdUserId = memcachedClient.get("pay" + sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!"".equals(orderIdUserId) && orderIdUserId != null) {
				String[] split = orderIdUserId.split(" ");
				String userId = split[0];
				String orderId = split[1];

				Long orderOrPayId = callBackError.getOrderOrPayId();
				// 说明是android端支付的
				if (("" + orderOrPayId).equals(orderId)) {
					model.addAttribute("userId", userId);
					model.addAttribute("orderId", orderId);
					model.addAttribute("msgCode", callBackError.getMsgCode());
				}
			}

			model.addAttribute("back", callBackError);
			return ResponseContant.CUS_ORDER_PAY_RESULT;
		}

		try {
			PayResult callBack = cusOrderPayService.aliMobileCallBack(requestParams,
					PayConstant.BankPayModel.PAY_TYPE_ALIPAY_INLAND_WAP);
			if (callBack == null) {
				log.error("callback is null ");
				throw new BusinessException("call back is null ");
			}
			String sid = getUUID.getSessionId(request, response);
			log.info("---------sid----------->" + sid);
			String orderIdUserId = null;
			try {
				orderIdUserId = memcachedClient.get("pay" + sid);
				log.info("---------pay----------->" + orderIdUserId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!"".equals(orderIdUserId) && orderIdUserId != null) {
				String[] split = orderIdUserId.split(" ");
				String userId = split[0];
				String orderId = split[1];

				// 判断是否活动是否为0元购
				setActivityReturnUrl(userId, model);
				setActivityNewReturnUrl(userId, model);

				Long orderOrPayId = callBack.getOrderOrPayId();
				// 说明是android端支付的
				if (("" + orderOrPayId).equals(orderId)) {
					model.addAttribute("userId", userId);
					model.addAttribute("orderId", orderId);
					model.addAttribute("msgCode", callBack.getMsgCode());
				}
			}

			User user = getCurrentUser(request);
			// 判断是否活动是否为0元购
			setActivityReturnUrl(String.valueOf(user.getUserId()), model);
			setActivityNewReturnUrl(String.valueOf(user.getUserId()), model);

			model.addAttribute("back", callBack);
			// 根据订单号获取订单信息
			OrderDTO orderDTO = cusOrderService.findOrderByOrderId(callBack.getOrderOrPayId());
			String orderSource = orderDTO.getOrderSource();
			Short orderType = orderDTO.getOrderType();

			model.addAttribute("orderDto", orderDTO);

			if (OrderConstants.ORDER_SOURCE_TELECOM_RECHARGE.equals(orderSource)
					|| OrderConstants.ORDER_TYPE_100 == orderType) {
				model.addAttribute("receiveMobilePhone", orderDTO.getReceiveMobilePhone());
				log.info("recharge order .return recharge pay result page.");
				return ResponseContant.CUS_ORDER_PAY_RESULT_RECHARGE;
			}

		} catch (Exception e) {
			log.error("支付宝wap国内返回异常:" + e.getMessage(), e);
		}

		return ResponseContant.CUS_ORDER_PAY_RESULT;
	}

	@RequestMapping(value = RequestContant.JD_RETURN_PAY_CALLBACK)
	public String jdPayReturn(@RequestParam Map<String, String> requestParams, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("return ---requestparames:{" + requestParams + "}");
		log.info(" jdPay Return call back execute.......");
		log.info("return ---requestparames:{" + requestParams + "}");

		try {
			User user = getCurrentUser(request);
			PayResult callBack = cusOrderPayService.jdPayCallBack(requestParams,
					PayConstant.BankPayModel.PAY_TYPE_JDPAY_INTEGRAL);
			if (callBack == null) {
				log.error("callback is null ");
				throw new BusinessException("call back is null ");
			}

			// 商品信息回显
			OrderDTO findOrderByOrderId = null;
			if (callBack != null) {
				Long orderOrPayId = callBack.getOrderOrPayId();
				findOrderByOrderId = cusOrderService.findOrderByOrderId(orderOrPayId, user);
			}

			model.addAttribute("orderDto", findOrderByOrderId);
			model.addAttribute("back", callBack);
		} catch (Exception e) {
			log.error("京东支付返回异常:" + e.getMessage(), e);
		}

		return ResponseContant.CUS_ORDER_PAY_RESULT;
	}

	@RequestMapping(value = RequestContant.JD_NOTIFY_PAY_CALLBACK)
	public void jdPayNotify(@RequestParam Map<String, String> requestParams, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("jdPay wap --requestparames:{" + requestParams + "}");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			User user = null;
			PayResult payResult = cusOrderPayService.jdPayNotifyCallBack(requestParams,
					PayConstant.BankPayModel.PAY_TYPE_JDPAY_INTEGRAL, request, response);

			if (payResult != null) {
				Long orderIdByLong = payResult.getOrderOrPayId();
				// 开始返利，积分
				if (orderIdByLong == null) {
					log.error("pc支付回调未积分,无法通过支付流水号找到订单号,requestparames:{" + requestParams + "}");
				} else {
					List<OrderItemReturnTicketDto> OrderItemReturnTicketDtoList = new ArrayList<OrderItemReturnTicketDto>();
					// long orderIdByLong = Long.parseLong(orderId);
					OrderDTO orderDto = customerOrderService.getOrderDtoByOrderId(orderIdByLong);
					List<OrderItem> orderItemList = customerOrderService.getOrderItemByOrderId(orderIdByLong);
					user = userService.findUserById(orderDto.getUserId());
					Long firstUserId = null;
					Long secondUserId = null;
					if (!StringUtils.isEmpty(user.getTjMobile())) {
						User firstTjUser = userService.findUserByMobile(user.getTjMobile());
						if (firstTjUser != null) {
							firstUserId = firstTjUser.getUserId();
							if (!StringUtils.isEmpty(firstTjUser.getTjMobile())) {
								User secondTjUser = userService.findUserByMobile(firstTjUser.getTjMobile());
								if (secondTjUser != null)
									secondUserId = secondTjUser.getUserId();
							}
						}
					}

					Order order = new Order();
					OrderDTO orderDtoNew = customerOrderService.getOrderDtoByOrderId(orderIdByLong);
					Supplier supplier = supplierManagerService.findSupplier(Long.parseLong(orderDto.getMerchantid()));
					/*
					 * OneDividend moneyOneDividend = dividendService
					 * .findOneDivByType(
					 * Constants.SqwAccountRecordService.MONEY_PAY,
					 * Integer.parseInt(supplier.getCompanyQy()));
					 */

					// 红旗券+现金支付
					// if("1".equals(orderDto.getMixPayStatus())){

					Double sumReturn_hqq = 0d;
					Double sumReturn_fhed = 0d;

					order.setCompanyQy(supplier.getCompanyQy());
					order.setPayWay(Constants.SqwAccountRecordService.JDPAY_WAY);
					order.setId(orderDto.getId());
					order.setVersion(orderDtoNew.getVersion());
					List<OrderItem> orderItemUpdates = new ArrayList<OrderItem>();
					for (OrderItem orderItem : orderItemList) {

						if (productTagsService.LimitContentOrNull(orderItem.getPid(), "hqb", 4) != null) {
							CashierRecord cashierRecord = new CashierRecord();
							cashierRecord.setUserId(orderDto.getUserId());
							cashierRecord.setOrderNo(orderIdByLong);
							cashierRecord.setCommodityName(orderItem.getpName());
							cashierRecord.setStartDate(new Date());
							cashierRecord.setSpecDesc(orderItem.getUnit());
							cashierRecord.setAmount(orderItem.getSkuQty());
							cashierRecord.setAmountOfMoney(orderItem.getSubtotalPirce());
							cashierRecord.setType(1);
							cashierService.insertCashierRecord(cashierRecord);
						}
						if (productTagsService.LimitContentOrNull(orderItem.getPid(), "sxb", 4) != null) {
							CashierRecord cashierRecord = new CashierRecord();
							cashierRecord.setUserId(orderDto.getUserId());
							cashierRecord.setOrderNo(orderIdByLong);
							cashierRecord.setCommodityName(orderItem.getpName());
							cashierRecord.setStartDate(new Date());
							cashierRecord.setSpecDesc(orderItem.getUnit());
							cashierRecord.setAmount(orderItem.getSkuQty());
							cashierRecord.setAmountOfMoney(orderItem.getSubtotalPirce());
							cashierRecord.setType(2);
							cashierService.insertCashierRecord(cashierRecord);
						}
						if (productTagsService.LimitContentOrNull(orderItem.getPid(), "jtb", 4) != null) {
							HomeNumRecord homeNumRecord = new HomeNumRecord();
							homeNumRecord.setUserId(orderDto.getUserId().intValue());
							homeNumRecord.setProductId(orderItem.getPid().toString());
							homeNumRecord.setOrderNo(orderIdByLong);
							homeNumRecord.setCommodityName(orderItem.getpName());
							homeNumRecord.setStartDate(new Date());
							homeNumRecord.setSpecDesc(orderItem.getUnit());

							homeNumRecord.setAmountOfMoney(orderItem.getSubtotalPirce());

							homeNumRecordService.insertHomeNumRecord(homeNumRecord);
						}
						// 会员企业
						if (productTagsService.LimitContentOrNull(orderItem.getPid(), "qyb", 4) != null) {
							SupplierNumRecord supplierNumRecord = new SupplierNumRecord();
							supplierNumRecord.setUserId(orderDto.getUserId().intValue());

							supplierNumRecord.setProductId(orderItem.getPid().toString());
							supplierNumRecord.setOrderNo(orderIdByLong);
							supplierNumRecord.setCommodityName(orderItem.getpName());
							supplierNumRecord.setStartDate(new Date());
							supplierNumRecord.setSpecDesc(orderItem.getUnit());

							supplierNumRecord.setAmountOfMoney(orderItem.getSubtotalPirce());

							supplierNumRecordService.insertSupplierNumRecord(supplierNumRecord);

						}
						// 红旗券返券比例
						OneDividend hqOneDividend = dividendService.findOneDivByType(
								Constants.SqwAccountRecordService.HQ_PAY, Integer.parseInt(supplier.getCompanyQy()),
								orderItem.getPid());
						// 现金返券比例
						OneDividend moneyOneDividend = dividendService.findOneDivByType(
								Constants.SqwAccountRecordService.MONEY_PAY, Integer.parseInt(supplier.getCompanyQy()),
								orderItem.getPid());
						double fq = 0d;
						double fhed = 0d;

						OrderItemReturnTicketDto orderItemReturnTicketDto = new OrderItemReturnTicketDto();
						OrderItem orderItemUpdate = new OrderItem();
						orderItemReturnTicketDto.setpNum(orderItem.getSkuQty());
						orderItemReturnTicketDto.setProductSkuId(orderItem.getSkuId());
						orderItemReturnTicketDto.setProductRegin(supplier.getCompanyRegion());
						orderItemReturnTicketDto.setRefObjType(Constants.intONE);
						orderItemReturnTicketDto.setRefObjId(orderIdByLong);
						// 判断是否是现金比例商品或幸福购商品
						if (orderItem.getCashPrice() != null
								&& orderItem.getCashPrice().compareTo(BigDecimal.valueOf(0)) == 1) {
							if ("1".equals(orderDto.getMixPayStatus())) {
								orderItemReturnTicketDto.setPrice_cash(
										orderItem.getSubtotalCashPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
								orderItemReturnTicketDto.setPrice_hqq(
										orderItem.getSubtotalhqjPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
							} else {
								orderItemReturnTicketDto.setPrice_cash(
										orderItem.getSubtotalPirce().setScale(0, BigDecimal.ROUND_HALF_UP));
								orderItemReturnTicketDto.setPrice_hqq(BigDecimal.valueOf(0d));
							}

							// 现金数*现金返券+红旗券数*红旗券返券比例
							fq = orderItem.getSubtotalCashPrice()
									.multiply(moneyOneDividend.getGiftHqj()
											.multiply(BigDecimal
													.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
									.setScale(0, BigDecimal.ROUND_HALF_UP)
									.add(orderItem.getSubtotalhqjPrice()
											.multiply(hqOneDividend.getGiftHqj()
													.multiply(BigDecimal.valueOf(
															Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
											.setScale(0, BigDecimal.ROUND_HALF_UP))
									.doubleValue();

							fhed = orderItem.getSubtotalCashPrice()
									.multiply(moneyOneDividend.getGiftFhed()
											.multiply(BigDecimal
													.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
									.setScale(0, BigDecimal.ROUND_HALF_UP)
									.add(orderItem.getSubtotalhqjPrice()
											.multiply(hqOneDividend.getGiftFhed()
													.multiply(BigDecimal.valueOf(
															Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
											.setScale(0, BigDecimal.ROUND_HALF_UP))
									.doubleValue();

						} else {

							if ("1".equals(orderDto.getMixPayStatus())) {
								orderItemReturnTicketDto.setPrice_cash(orderItem.getSubtotalPirce()
										.multiply(orderDto.getCashRatio()).setScale(0, BigDecimal.ROUND_HALF_UP));
								orderItemReturnTicketDto.setPrice_hqq(orderItem.getSubtotalPirce()
										.multiply(orderDto.getHqRatio()).setScale(0, BigDecimal.ROUND_HALF_UP));
							} else {
								orderItemReturnTicketDto.setPrice_cash(
										orderItem.getSubtotalPirce().setScale(0, BigDecimal.ROUND_HALF_UP));
								orderItemReturnTicketDto.setPrice_hqq(BigDecimal.valueOf(0d));
							}

							fq = orderItem.getSubtotalPirce()
									.multiply(hqOneDividend.getGiftHqj()
											.multiply(BigDecimal
													.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
									.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
							fhed = orderItem.getSubtotalPirce()
									.multiply(hqOneDividend.getGiftFhed()
											.multiply(BigDecimal
													.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
									.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();

						}
						sumReturn_hqq += fq;
						sumReturn_fhed += fhed;

						OrderItemReturnTicketDtoList.add(orderItemReturnTicketDto);
						orderItemReturnTicketDto.setReturn_fhed(new BigDecimal(fhed));
						orderItemReturnTicketDto.setReturn_hqq(new BigDecimal(fq));
						orderItemUpdate.setFhed(fhed);
						orderItemUpdate.setHqj(fq);
						orderItemUpdates.add(orderItemUpdate);
					}
					// 调用积分返利服务
					sqwAccountRecordService.recordAccountByUserbuy(user.getUserId(), OrderItemReturnTicketDtoList,
							firstUserId, secondUserId);
					// 计算订单总返券和总返分红
					order.setOrderHqj(sumReturn_hqq);
					order.setOrderFhed(sumReturn_fhed);

					customerOrderService.updateOrder(order);
					customerOrderService.updateOrderItems(orderItemUpdates);
					UserOperationRecord userOperationRecord = new UserOperationRecord();
					userOperationRecord.setCreateTime(new Date());
					userOperationRecord.setOperationType("consume");
					userOperationRecord.setRemark("用户使用京东支付,已付款订单号:" + orderIdByLong);
					userOperationRecord.setUserId(user.getUserId().intValue());
					userOperationRecordService.saveUserOperationRecord(userOperationRecord);

				}
				writer.write("ok");
			} else {
				log.error("京东支付wap notify callback 异常");
				writer.write(FAIL);
			}
		} catch (Exception e) {
			log.error("京东支付wap异常:" + e.getMessage(), e);
			writer.write(FAIL);
		}
	}

	/**
	 * 支付宝国内支付 notify返回 检验
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = RequestContant.ALI_DIRECT_NOTIFY_PAY_CALLBACK, method = RequestMethod.POST)
	public void aliDirectNotify(@RequestParam Map<String, String> requestParams, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("aliDirectWapNotify--requestparames:{" + requestParams + "}");
		PrintWriter writer = null;
		User user = null;
		try {
			writer = response.getWriter();
			cusOrderPayService.aliMobileCallBack(requestParams, PayConstant.BankPayModel.PAY_TYPE_ALIPAY_INLAND_WAP);
			AliMobilePayResult aliMobilePayResult = (AliMobilePayResult) ConvertUtils.mapToBean(requestParams,
					AliMobilePayResult.class);
			// 开始返利，积分
			Long orderIdByLong = b2CPayRecodeService.getOrderIdByPayFlowNo(aliMobilePayResult.getOut_trade_no());
			if (orderIdByLong == null) {
				log.error("pc支付回调未积分,无法通过支付流水号找到订单号,requestparames:{" + requestParams + "}");
			} else {
				List<OrderItemReturnTicketDto> OrderItemReturnTicketDtoList = new ArrayList<OrderItemReturnTicketDto>();
				// long orderIdByLong = Long.parseLong(orderId);
				OrderDTO orderDto = customerOrderService.getOrderDtoByOrderId(orderIdByLong);
				List<OrderItem> orderItemList = customerOrderService.getOrderItemByOrderId(orderIdByLong);
				user = userService.findUserById(orderDto.getUserId());
				Long firstUserId = null;
				Long secondUserId = null;
				if (!StringUtils.isEmpty(user.getTjMobile())) {
					User firstTjUser = userService.findUserByMobile(user.getTjMobile());
					if (firstTjUser != null) {
						firstUserId = firstTjUser.getUserId();
						if (!StringUtils.isEmpty(firstTjUser.getTjMobile())) {
							User secondTjUser = userService.findUserByMobile(firstTjUser.getTjMobile());
							if (secondTjUser != null)
								secondUserId = secondTjUser.getUserId();
						}
					}
				}

				Order order = new Order();
				OrderDTO orderDtoNew = customerOrderService.getOrderDtoByOrderId(orderIdByLong);
				Supplier supplier = supplierManagerService.findSupplier(Long.parseLong(orderDto.getMerchantid()));
				/*
				 * OneDividend moneyOneDividend = dividendService
				 * .findOneDivByType(
				 * Constants.SqwAccountRecordService.MONEY_PAY,
				 * Integer.parseInt(supplier.getCompanyQy()));
				 */

				// 红旗券+现金支付
				// if("1".equals(orderDto.getMixPayStatus())){

				Double sumReturn_hqq = 0d;
				Double sumReturn_fhed = 0d;

				order.setCompanyQy(supplier.getCompanyQy());
				order.setPayWay(Constants.SqwAccountRecordService.ALIPAY_WAY);
				order.setId(orderDto.getId());
				order.setVersion(orderDtoNew.getVersion());
				List<OrderItem> orderItemUpdates = new ArrayList<OrderItem>();
				for (OrderItem orderItem : orderItemList) {

					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "hqb", 4) != null) {
						CashierRecord cashierRecord = new CashierRecord();
						cashierRecord.setUserId(orderDto.getUserId());
						cashierRecord.setOrderNo(orderIdByLong);
						cashierRecord.setCommodityName(orderItem.getpName());
						cashierRecord.setStartDate(new Date());
						cashierRecord.setSpecDesc(orderItem.getUnit());
						cashierRecord.setAmount(orderItem.getSkuQty());
						cashierRecord.setAmountOfMoney(orderItem.getSubtotalPirce());
						cashierRecord.setType(1);
						cashierService.insertCashierRecord(cashierRecord);
					}
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "sxb", 4) != null) {
						CashierRecord cashierRecord = new CashierRecord();
						cashierRecord.setUserId(orderDto.getUserId());
						cashierRecord.setOrderNo(orderIdByLong);
						cashierRecord.setCommodityName(orderItem.getpName());
						cashierRecord.setStartDate(new Date());
						cashierRecord.setSpecDesc(orderItem.getUnit());
						cashierRecord.setAmount(orderItem.getSkuQty());
						cashierRecord.setAmountOfMoney(orderItem.getSubtotalPirce());
						cashierRecord.setType(2);
						cashierService.insertCashierRecord(cashierRecord);
					}
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "jtb", 4) != null) {
						HomeNumRecord homeNumRecord = new HomeNumRecord();
						homeNumRecord.setUserId(orderDto.getUserId().intValue());
						homeNumRecord.setProductId(orderItem.getPid().toString());
						homeNumRecord.setOrderNo(orderIdByLong);
						homeNumRecord.setCommodityName(orderItem.getpName());
						homeNumRecord.setStartDate(new Date());
						homeNumRecord.setSpecDesc(orderItem.getUnit());

						homeNumRecord.setAmountOfMoney(orderItem.getSubtotalPirce());

						homeNumRecordService.insertHomeNumRecord(homeNumRecord);
					}
					// 会员企业
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "qyb", 4) != null) {
						SupplierNumRecord supplierNumRecord = new SupplierNumRecord();
						supplierNumRecord.setUserId(orderDto.getUserId().intValue());

						supplierNumRecord.setProductId(orderItem.getPid().toString());
						supplierNumRecord.setOrderNo(orderIdByLong);
						supplierNumRecord.setCommodityName(orderItem.getpName());
						supplierNumRecord.setStartDate(new Date());
						supplierNumRecord.setSpecDesc(orderItem.getUnit());

						supplierNumRecord.setAmountOfMoney(orderItem.getSubtotalPirce());

						supplierNumRecordService.insertSupplierNumRecord(supplierNumRecord);

					}
					// 红旗券返券比例
					OneDividend hqOneDividend = dividendService.findOneDivByType(
							Constants.SqwAccountRecordService.HQ_PAY, Integer.parseInt(supplier.getCompanyQy()),
							orderItem.getPid());
					// 现金返券比例
					OneDividend moneyOneDividend = dividendService.findOneDivByType(
							Constants.SqwAccountRecordService.MONEY_PAY, Integer.parseInt(supplier.getCompanyQy()),
							orderItem.getPid());
					double fq = 0d;
					double fhed = 0d;

					OrderItemReturnTicketDto orderItemReturnTicketDto = new OrderItemReturnTicketDto();
					OrderItem orderItemUpdate = new OrderItem();
					orderItemReturnTicketDto.setpNum(orderItem.getSkuQty());
					orderItemReturnTicketDto.setProductSkuId(orderItem.getSkuId());
					orderItemReturnTicketDto.setProductRegin(supplier.getCompanyRegion());
					orderItemReturnTicketDto.setRefObjType(Constants.intONE);
					orderItemReturnTicketDto.setRefObjId(orderIdByLong);
					// 判断是否是现金比例商品或幸福购商品
					if (orderItem.getCashPrice() != null
							&& orderItem.getCashPrice().compareTo(BigDecimal.valueOf(0)) == 1) {
						if ("1".equals(orderDto.getMixPayStatus())) {
							orderItemReturnTicketDto.setPrice_cash(
									orderItem.getSubtotalCashPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(
									orderItem.getSubtotalhqjPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
						} else {
							orderItemReturnTicketDto
									.setPrice_cash(orderItem.getSubtotalPirce().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(BigDecimal.valueOf(0d));
						}

						// 现金数*现金返券+红旗券数*红旗券返券比例
						fq = orderItem.getSubtotalCashPrice()
								.multiply(moneyOneDividend.getGiftHqj()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP)
								.add(orderItem.getSubtotalhqjPrice()
										.multiply(hqOneDividend.getGiftHqj()
												.multiply(BigDecimal
														.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
										.setScale(0, BigDecimal.ROUND_HALF_UP))
								.doubleValue();

						fhed = orderItem.getSubtotalCashPrice()
								.multiply(moneyOneDividend.getGiftFhed()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP)
								.add(orderItem.getSubtotalhqjPrice()
										.multiply(hqOneDividend.getGiftFhed()
												.multiply(BigDecimal
														.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
										.setScale(0, BigDecimal.ROUND_HALF_UP))
								.doubleValue();
					} else {
						if ("1".equals(orderDto.getMixPayStatus())) {
							orderItemReturnTicketDto.setPrice_cash(orderItem.getSubtotalPirce()
									.multiply(orderDto.getCashRatio()).setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(orderItem.getSubtotalPirce()
									.multiply(orderDto.getHqRatio()).setScale(0, BigDecimal.ROUND_HALF_UP));
						} else {
							orderItemReturnTicketDto
									.setPrice_cash(orderItem.getSubtotalPirce().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(BigDecimal.valueOf(0d));
						}

						fq = orderItem.getSubtotalPirce()
								.multiply(hqOneDividend.getGiftHqj()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
						fhed = orderItem.getSubtotalPirce()
								.multiply(hqOneDividend.getGiftFhed()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					sumReturn_hqq += fq;
					sumReturn_fhed += fhed;

					OrderItemReturnTicketDtoList.add(orderItemReturnTicketDto);
					orderItemReturnTicketDto.setReturn_fhed(new BigDecimal(fhed));
					orderItemReturnTicketDto.setReturn_hqq(new BigDecimal(fq));
					orderItemUpdate.setFhed(fhed);
					orderItemUpdate.setHqj(fq);
					orderItemUpdates.add(orderItemUpdate);
				}
				// 调用积分返利服务
				sqwAccountRecordService.recordAccountByUserbuy(user.getUserId(), OrderItemReturnTicketDtoList,
						firstUserId, secondUserId);
				// 计算订单总返券和总返分红
				order.setOrderHqj(sumReturn_hqq);
				order.setOrderFhed(sumReturn_fhed);
				customerOrderService.updateOrder(order);
				customerOrderService.updateOrderItems(orderItemUpdates);

			}
			UserOperationRecord userOperationRecord = new UserOperationRecord();
			userOperationRecord.setCreateTime(new Date());
			userOperationRecord.setOperationType("consume");
			userOperationRecord.setRemark("用户使用支付宝支付,已付款订单号:" + orderIdByLong);
			userOperationRecord.setUserId(user.getUserId().intValue());
			userOperationRecordService.saveUserOperationRecord(userOperationRecord);
			writer.write(SUCCESS);
		} catch (Exception e) {
			log.error("支付宝wap国内异常:" + e.getMessage(), e);
			writer.write(FAIL);
		}
	}

	/** 支付宝国内支付回调 end ***/

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = RequestContant.WEIXIN_PAY_NOTIFY, method = RequestMethod.POST)
	public void weixinAppPayNotify(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		BufferedReader reader = null;
		User user = null;
		try {
			log.info("微信支付回调接口");
			writer = response.getWriter();
			reader = request.getReader();

			String inputLine = null;
			String notityXml = "";
			while ((inputLine = reader.readLine()) != null) {
				notityXml += inputLine;
			}
			log.info("微信支付回调接口-->param[" + notityXml + "]");
			if (StringUtils.isEmpty(notityXml)) {
				throw new Exception("微信支付回调接口获取返回参数为空");
			}

			// 相应数据
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("result", notityXml);
			// 支付回调
			cusOrderPayService.aliMobileCallBack(requestParams, PayConstant.BankPayModel.PAY_TYPE_WEIXIN_APP);

			// AliMobilePayResult aliMobilePayResult = (AliMobilePayResult)
			// ConvertUtils
			// .mapToBean(requestParams, AliMobilePayResult.class);

			WeiXinPayResult weiXinPayResult = JaxbUtils.readXMLFromString(WeiXinPayResult.class,
					requestParams.get("result"));

			log.info("weiXinPayResult.getOutTradeNo:{}", weiXinPayResult.getOutTradeNo());

			// 开始返利，积分
			Long orderIdByLong = b2CPayRecodeService.getOrderIdByPayFlowNo(weiXinPayResult.getOutTradeNo());

			if (orderIdByLong == null) {
				log.error("pc支付回调未积分,无法通过支付流水号找到订单号,requestparames:{" + requestParams + "}");
			} else {
				List<OrderItemReturnTicketDto> OrderItemReturnTicketDtoList = new ArrayList<OrderItemReturnTicketDto>();
				// long orderIdByLong = Long.parseLong(orderId);
				OrderDTO orderDto = customerOrderService.getOrderDtoByOrderId(orderIdByLong);
				List<OrderItem> orderItemList = customerOrderService.getOrderItemByOrderId(orderIdByLong);
				user = userService.findUserById(orderDto.getUserId());
				Long firstUserId = null;
				Long secondUserId = null;
				if (!StringUtils.isEmpty(user.getTjMobile())) {
					User firstTjUser = userService.findUserByMobile(user.getTjMobile());
					if (firstTjUser != null) {
						firstUserId = firstTjUser.getUserId();
						if (!StringUtils.isEmpty(firstTjUser.getTjMobile())) {
							User secondTjUser = userService.findUserByMobile(firstTjUser.getTjMobile());
							if (secondTjUser != null)
								secondUserId = secondTjUser.getUserId();
						}
					}
				}

				Order order = new Order();
				OrderDTO orderDtoNew = customerOrderService.getOrderDtoByOrderId(orderIdByLong);
				Supplier supplier = supplierManagerService.findSupplier(Long.parseLong(orderDto.getMerchantid()));
				/*
				 * OneDividend moneyOneDividend = dividendService
				 * .findOneDivByType(
				 * Constants.SqwAccountRecordService.MONEY_PAY,
				 * Integer.parseInt(supplier.getCompanyQy()));
				 */

				// 红旗券+现金支付
				// if("1".equals(orderDto.getMixPayStatus())){

				Double sumReturn_hqq = 0d;
				Double sumReturn_fhed = 0d;

				order.setCompanyQy(supplier.getCompanyQy());
				order.setPayWay(Constants.SqwAccountRecordService.WEIXIN_APP_WAY);
				order.setId(orderDto.getId());
				order.setVersion(orderDtoNew.getVersion());
				List<OrderItem> orderItemUpdates = new ArrayList<OrderItem>();
				for (OrderItem orderItem : orderItemList) {

					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "hqb", 4) != null) {
						CashierRecord cashierRecord = new CashierRecord();
						cashierRecord.setUserId(orderDto.getUserId());
						cashierRecord.setOrderNo(orderIdByLong);
						cashierRecord.setCommodityName(orderItem.getpName());
						cashierRecord.setStartDate(new Date());
						cashierRecord.setSpecDesc(orderItem.getUnit());
						cashierRecord.setAmount(orderItem.getSkuQty());
						cashierRecord.setAmountOfMoney(orderItem.getSubtotalPirce());
						cashierRecord.setType(1);
						cashierService.insertCashierRecord(cashierRecord);
					}
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "sxb", 4) != null) {
						CashierRecord cashierRecord = new CashierRecord();
						cashierRecord.setUserId(orderDto.getUserId());
						cashierRecord.setOrderNo(orderIdByLong);
						cashierRecord.setCommodityName(orderItem.getpName());
						cashierRecord.setStartDate(new Date());
						cashierRecord.setSpecDesc(orderItem.getUnit());
						cashierRecord.setAmount(orderItem.getSkuQty());
						cashierRecord.setAmountOfMoney(orderItem.getSubtotalPirce());
						cashierRecord.setType(2);
						cashierService.insertCashierRecord(cashierRecord);
					}
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "jtb", 4) != null) {
						HomeNumRecord homeNumRecord = new HomeNumRecord();
						homeNumRecord.setUserId(orderDto.getUserId().intValue());
						homeNumRecord.setProductId(orderItem.getPid().toString());
						homeNumRecord.setOrderNo(orderIdByLong);
						homeNumRecord.setCommodityName(orderItem.getpName());
						homeNumRecord.setStartDate(new Date());
						homeNumRecord.setSpecDesc(orderItem.getUnit());

						homeNumRecord.setAmountOfMoney(orderItem.getSubtotalPirce());

						homeNumRecordService.insertHomeNumRecord(homeNumRecord);
					}
					// 会员企业
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "qyb", 4) != null) {
						SupplierNumRecord supplierNumRecord = new SupplierNumRecord();
						supplierNumRecord.setUserId(orderDto.getUserId().intValue());

						supplierNumRecord.setProductId(orderItem.getPid().toString());
						supplierNumRecord.setOrderNo(orderIdByLong);
						supplierNumRecord.setCommodityName(orderItem.getpName());
						supplierNumRecord.setStartDate(new Date());
						supplierNumRecord.setSpecDesc(orderItem.getUnit());

						supplierNumRecord.setAmountOfMoney(orderItem.getSubtotalPirce());

						supplierNumRecordService.insertSupplierNumRecord(supplierNumRecord);

					}
					// 红旗券返券比例
					OneDividend hqOneDividend = dividendService.findOneDivByType(
							Constants.SqwAccountRecordService.HQ_PAY, Integer.parseInt(supplier.getCompanyQy()),
							orderItem.getPid());
					// 现金返券比例
					OneDividend moneyOneDividend = dividendService.findOneDivByType(
							Constants.SqwAccountRecordService.MONEY_PAY, Integer.parseInt(supplier.getCompanyQy()),
							orderItem.getPid());
					double fq = 0d;
					double fhed = 0d;

					OrderItemReturnTicketDto orderItemReturnTicketDto = new OrderItemReturnTicketDto();
					OrderItem orderItemUpdate = new OrderItem();
					orderItemReturnTicketDto.setpNum(orderItem.getSkuQty());
					orderItemReturnTicketDto.setProductSkuId(orderItem.getSkuId());
					orderItemReturnTicketDto.setProductRegin(supplier.getCompanyRegion());
					orderItemReturnTicketDto.setRefObjType(Constants.intONE);
					orderItemReturnTicketDto.setRefObjId(orderIdByLong);
					// 判断是否是现金比例商品或幸福购商品
					if (orderItem.getCashPrice() != null
							&& orderItem.getCashPrice().compareTo(BigDecimal.valueOf(0)) == 1) {
						if ("1".equals(orderDto.getMixPayStatus())) {
							orderItemReturnTicketDto.setPrice_cash(
									orderItem.getSubtotalCashPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(
									orderItem.getSubtotalhqjPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
						} else {
							orderItemReturnTicketDto
									.setPrice_cash(orderItem.getSubtotalPirce().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(BigDecimal.valueOf(0d));
						}

						// 现金数*现金返券+红旗券数*红旗券返券比例
						fq = orderItem.getSubtotalCashPrice()
								.multiply(moneyOneDividend.getGiftHqj()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP)
								.add(orderItem.getSubtotalhqjPrice()
										.multiply(hqOneDividend.getGiftHqj()
												.multiply(BigDecimal
														.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
										.setScale(0, BigDecimal.ROUND_HALF_UP))
								.doubleValue();

						fhed = orderItem.getSubtotalCashPrice()
								.multiply(moneyOneDividend.getGiftFhed()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP)
								.add(orderItem.getSubtotalhqjPrice()
										.multiply(hqOneDividend.getGiftFhed()
												.multiply(BigDecimal
														.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
										.setScale(0, BigDecimal.ROUND_HALF_UP))
								.doubleValue();
					} else {
						if ("1".equals(orderDto.getMixPayStatus())) {
							orderItemReturnTicketDto.setPrice_cash(orderItem.getSubtotalPirce()
									.multiply(orderDto.getCashRatio()).setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(orderItem.getSubtotalPirce()
									.multiply(orderDto.getHqRatio()).setScale(0, BigDecimal.ROUND_HALF_UP));
						} else {
							orderItemReturnTicketDto
									.setPrice_cash(orderItem.getSubtotalPirce().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(BigDecimal.valueOf(0d));
						}

						fq = orderItem.getSubtotalPirce()
								.multiply(hqOneDividend.getGiftHqj()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
						fhed = orderItem.getSubtotalPirce()
								.multiply(hqOneDividend.getGiftFhed()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					sumReturn_hqq += fq;
					sumReturn_fhed += fhed;

					OrderItemReturnTicketDtoList.add(orderItemReturnTicketDto);
					orderItemReturnTicketDto.setReturn_fhed(new BigDecimal(fhed));
					orderItemReturnTicketDto.setReturn_hqq(new BigDecimal(fq));
					orderItemUpdate.setFhed(fhed);
					orderItemUpdate.setHqj(fq);
					orderItemUpdates.add(orderItemUpdate);
				}
				// 调用积分返利服务
				sqwAccountRecordService.recordAccountByUserbuy(user.getUserId(), OrderItemReturnTicketDtoList,
						firstUserId, secondUserId);
				// 计算订单总返券和总返分红
				order.setOrderHqj(sumReturn_hqq);
				order.setOrderFhed(sumReturn_fhed);
				customerOrderService.updateOrder(order);
				customerOrderService.updateOrderItems(orderItemUpdates);

			}
			UserOperationRecord userOperationRecord = new UserOperationRecord();
			userOperationRecord.setCreateTime(new Date());
			userOperationRecord.setOperationType("consume");
			userOperationRecord.setRemark("用户使用微信支付,已付款订单号:" + orderIdByLong);
			userOperationRecord.setUserId(user.getUserId().intValue());
			userOperationRecordService.saveUserOperationRecord(userOperationRecord);

			writer.write(
					"<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg></return_msg></xml>");
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = RequestContant.WEIXIN_GZH_PAY_NOTIFY, method = RequestMethod.POST)
	public void weixinGzhPayNotify(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		BufferedReader reader = null;
		User user = null;
		try {
			log.info("微信公众号支付回调接口");
			writer = response.getWriter();
			reader = request.getReader();

			String inputLine = null;
			String notityXml = "";
			while ((inputLine = reader.readLine()) != null) {
				notityXml += inputLine;
			}
			log.info("微信公众号支付回调接口-->param[" + notityXml + "]");
			if (StringUtils.isEmpty(notityXml)) {
				throw new Exception("微信支付回调接口获取返回参数为空");
			}

			// 相应数据
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("result", notityXml);
			// 支付回调
			cusOrderPayService.aliMobileCallBack(requestParams, PayConstant.BankPayModel.PAY_TYPE_WEIXIN_GZH);

			WeiXinPayResult weiXinPayResult = JaxbUtils.readXMLFromString(WeiXinPayResult.class,
					requestParams.get("result"));

			log.info("weiXinPayResult.getOutTradeNo:{}", weiXinPayResult.getOutTradeNo());

			// 开始返利，积分
			Long orderIdByLong = b2CPayRecodeService.getOrderIdByPayFlowNo(weiXinPayResult.getOutTradeNo());

			if (orderIdByLong == null) {
				log.error("pc支付回调未积分,无法通过支付流水号找到订单号,requestparames:{" + requestParams + "}");
			} else {
				List<OrderItemReturnTicketDto> OrderItemReturnTicketDtoList = new ArrayList<OrderItemReturnTicketDto>();
				// long orderIdByLong = Long.parseLong(orderId);
				OrderDTO orderDto = customerOrderService.getOrderDtoByOrderId(orderIdByLong);
				List<OrderItem> orderItemList = customerOrderService.getOrderItemByOrderId(orderIdByLong);
				user = userService.findUserById(orderDto.getUserId());
				Long firstUserId = null;
				Long secondUserId = null;
				if (!StringUtils.isEmpty(user.getTjMobile())) {
					User firstTjUser = userService.findUserByMobile(user.getTjMobile());
					if (firstTjUser != null) {
						firstUserId = firstTjUser.getUserId();
						if (!StringUtils.isEmpty(firstTjUser.getTjMobile())) {
							User secondTjUser = userService.findUserByMobile(firstTjUser.getTjMobile());
							if (secondTjUser != null)
								secondUserId = secondTjUser.getUserId();
						}
					}
				}

				Order order = new Order();
				OrderDTO orderDtoNew = customerOrderService.getOrderDtoByOrderId(orderIdByLong);
				Supplier supplier = supplierManagerService.findSupplier(Long.parseLong(orderDto.getMerchantid()));
				/*
				 * OneDividend moneyOneDividend = dividendService
				 * .findOneDivByType(
				 * Constants.SqwAccountRecordService.MONEY_PAY,
				 * Integer.parseInt(supplier.getCompanyQy()));
				 */

				// 红旗券+现金支付
				// if("1".equals(orderDto.getMixPayStatus())){

				Double sumReturn_hqq = 0d;
				Double sumReturn_fhed = 0d;

				order.setCompanyQy(supplier.getCompanyQy());
				order.setPayWay(Constants.SqwAccountRecordService.WEIXIN_APP_WAY);
				order.setId(orderDto.getId());
				order.setVersion(orderDtoNew.getVersion());
				List<OrderItem> orderItemUpdates = new ArrayList<OrderItem>();
				for (OrderItem orderItem : orderItemList) {

					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "hqb", 4) != null) {
						CashierRecord cashierRecord = new CashierRecord();
						cashierRecord.setUserId(orderDto.getUserId());
						cashierRecord.setOrderNo(orderIdByLong);
						cashierRecord.setCommodityName(orderItem.getpName());
						cashierRecord.setStartDate(new Date());
						cashierRecord.setSpecDesc(orderItem.getUnit());
						cashierRecord.setAmount(orderItem.getSkuQty());
						cashierRecord.setAmountOfMoney(orderItem.getSubtotalPirce());
						cashierRecord.setType(1);
						cashierService.insertCashierRecord(cashierRecord);
					}
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "sxb", 4) != null) {
						CashierRecord cashierRecord = new CashierRecord();
						cashierRecord.setUserId(orderDto.getUserId());
						cashierRecord.setOrderNo(orderIdByLong);
						cashierRecord.setCommodityName(orderItem.getpName());
						cashierRecord.setStartDate(new Date());
						cashierRecord.setSpecDesc(orderItem.getUnit());
						cashierRecord.setAmount(orderItem.getSkuQty());
						cashierRecord.setAmountOfMoney(orderItem.getSubtotalPirce());
						cashierRecord.setType(2);
						cashierService.insertCashierRecord(cashierRecord);
					}
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "jtb", 4) != null) {
						HomeNumRecord homeNumRecord = new HomeNumRecord();
						homeNumRecord.setUserId(orderDto.getUserId().intValue());
						homeNumRecord.setProductId(orderItem.getPid().toString());
						homeNumRecord.setOrderNo(orderIdByLong);
						homeNumRecord.setCommodityName(orderItem.getpName());
						homeNumRecord.setStartDate(new Date());
						homeNumRecord.setSpecDesc(orderItem.getUnit());

						homeNumRecord.setAmountOfMoney(orderItem.getSubtotalPirce());

						homeNumRecordService.insertHomeNumRecord(homeNumRecord);
					}
					// 会员企业
					if (productTagsService.LimitContentOrNull(orderItem.getPid(), "qyb", 4) != null) {
						SupplierNumRecord supplierNumRecord = new SupplierNumRecord();
						supplierNumRecord.setUserId(orderDto.getUserId().intValue());

						supplierNumRecord.setProductId(orderItem.getPid().toString());
						supplierNumRecord.setOrderNo(orderIdByLong);
						supplierNumRecord.setCommodityName(orderItem.getpName());
						supplierNumRecord.setStartDate(new Date());
						supplierNumRecord.setSpecDesc(orderItem.getUnit());

						supplierNumRecord.setAmountOfMoney(orderItem.getSubtotalPirce());

						supplierNumRecordService.insertSupplierNumRecord(supplierNumRecord);

					}
					// 红旗券返券比例
					OneDividend hqOneDividend = dividendService.findOneDivByType(
							Constants.SqwAccountRecordService.HQ_PAY, Integer.parseInt(supplier.getCompanyQy()),
							orderItem.getPid());
					// 现金返券比例
					OneDividend moneyOneDividend = dividendService.findOneDivByType(
							Constants.SqwAccountRecordService.MONEY_PAY, Integer.parseInt(supplier.getCompanyQy()),
							orderItem.getPid());
					double fq = 0d;
					double fhed = 0d;

					OrderItemReturnTicketDto orderItemReturnTicketDto = new OrderItemReturnTicketDto();
					OrderItem orderItemUpdate = new OrderItem();
					orderItemReturnTicketDto.setpNum(orderItem.getSkuQty());
					orderItemReturnTicketDto.setProductSkuId(orderItem.getSkuId());
					orderItemReturnTicketDto.setProductRegin(supplier.getCompanyRegion());
					orderItemReturnTicketDto.setRefObjType(Constants.intONE);
					orderItemReturnTicketDto.setRefObjId(orderIdByLong);
					// 判断是否是现金比例商品或幸福购商品
					if (orderItem.getCashPrice() != null
							&& orderItem.getCashPrice().compareTo(BigDecimal.valueOf(0)) == 1) {
						if ("1".equals(orderDto.getMixPayStatus())) {
							orderItemReturnTicketDto.setPrice_cash(
									orderItem.getSubtotalCashPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(
									orderItem.getSubtotalhqjPrice().setScale(0, BigDecimal.ROUND_HALF_UP));
						} else {
							orderItemReturnTicketDto
									.setPrice_cash(orderItem.getSubtotalPirce().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(BigDecimal.valueOf(0d));
						}
						// 现金数*现金返券+红旗券数*红旗券返券比例
						fq = orderItem.getSubtotalCashPrice()
								.multiply(moneyOneDividend.getGiftHqj()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP)
								.add(orderItem.getSubtotalhqjPrice()
										.multiply(hqOneDividend.getGiftHqj()
												.multiply(BigDecimal
														.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
										.setScale(0, BigDecimal.ROUND_HALF_UP))
								.doubleValue();

						fhed = orderItem.getSubtotalCashPrice()
								.multiply(moneyOneDividend.getGiftFhed()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP)
								.add(orderItem.getSubtotalhqjPrice()
										.multiply(hqOneDividend.getGiftFhed()
												.multiply(BigDecimal
														.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
										.setScale(0, BigDecimal.ROUND_HALF_UP))
								.doubleValue();

					} else {
						if ("1".equals(orderDto.getMixPayStatus())) {
							orderItemReturnTicketDto.setPrice_cash(orderItem.getSubtotalPirce()
									.multiply(orderDto.getCashRatio()).setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(orderItem.getSubtotalPirce()
									.multiply(orderDto.getHqRatio()).setScale(0, BigDecimal.ROUND_HALF_UP));
						} else {
							orderItemReturnTicketDto
									.setPrice_cash(orderItem.getSubtotalPirce().setScale(0, BigDecimal.ROUND_HALF_UP));
							orderItemReturnTicketDto.setPrice_hqq(BigDecimal.valueOf(0d));
						}
						fq = orderItem.getSubtotalPirce()
								.multiply(hqOneDividend.getGiftHqj()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
						fhed = orderItem.getSubtotalPirce()
								.multiply(hqOneDividend.getGiftFhed()
										.multiply(BigDecimal
												.valueOf(Constants.SqwAccountRecordService.DIVIDE_ONE_HUNDRED)))
								.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
					}
					sumReturn_hqq += fq;
					sumReturn_fhed += fhed;

					OrderItemReturnTicketDtoList.add(orderItemReturnTicketDto);
					orderItemReturnTicketDto.setReturn_fhed(new BigDecimal(fhed));
					orderItemReturnTicketDto.setReturn_hqq(new BigDecimal(fq));
					orderItemUpdate.setFhed(fhed);
					orderItemUpdate.setHqj(fq);
					orderItemUpdates.add(orderItemUpdate);
				}
				// 调用积分返利服务
				sqwAccountRecordService.recordAccountByUserbuy(user.getUserId(), OrderItemReturnTicketDtoList,
						firstUserId, secondUserId);
				// 计算订单总返券和总返分红
				order.setOrderHqj(sumReturn_hqq);
				order.setOrderFhed(sumReturn_fhed);
				customerOrderService.updateOrder(order);
				customerOrderService.updateOrderItems(orderItemUpdates);

			}
			UserOperationRecord userOperationRecord = new UserOperationRecord();
			userOperationRecord.setCreateTime(new Date());
			userOperationRecord.setOperationType("consume");
			userOperationRecord.setRemark("用户使用微信支付,已付款订单号:" + orderIdByLong);
			userOperationRecord.setUserId(user.getUserId().intValue());
			userOperationRecordService.saveUserOperationRecord(userOperationRecord);

			writer.write(
					"<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg></return_msg></xml>");
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}




	/**
	 * 银联商务支付notify
	 * @param requestParams
	 * @param response
	 */
	@RequestMapping(value = RequestContant.CHINAUMS_NOTIFY_PAY_CALLBACK)
	public void chinaumsPayNotify(@RequestParam Map<String, String> requestParams, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			String merOrderId = requestParams.get("merOrderId");        // 支付订单号
			String targetSys = requestParams.get("targetSys");			// 交易渠道	WXPay  Alipay1.0 2.0

			if (requestParams.get("status").equals(PayConstant.ChinaUMSPayOrderStatus.TRADE_SUCCESS.getCode())) {

				PayResult payResult = cusOrderPayService.chinaumsPayCallBack(new TreeMap<String, String>(requestParams), BankPayModel.PAY_TYPE_CHINAUMS_WAP);

				PayRecord payRecord = payRecordServer.findPayRecordByWaterCode(merOrderId);
				OrderDTO order = customerOrderService.findCustomerOrderInfoByOrderId(payRecord.getBillNo());

				if (payResult != null && PayConstant.PayRecordStatus.SUCCESS.equals(payResult.getPayStatus())) {
					writer.write("SUCCESS");
					writer.flush();
					writer.close();

					Order od = new Order();
					od.setId(order.getId());
					od.setVersion(order.getVersion());
					od.setPaidPrice(order.getPrice());
					if ("WXPay".equals(targetSys)){
						od.setPayWay(Constants.SqwAccountRecordService.WEIXIN_WAY);
					}else if ("Alipay".contains(targetSys)){
						od.setPayWay(Constants.SqwAccountRecordService.ALIPAY_WAY);
					}


					if (order.getOrderType() == 200) {
						// 异步向收银员发消息
						userMsgService.sendWxMsgToSupplierShouYin(Long.valueOf(order.getMerchantid()), order.getId(), order.getPrice());

						log.info("订单：{} 结算日期：{}", merOrderId, requestParams.get("settleDate"));
						String payTime = requestParams.get("payTime");

						int re = 0;
						// 未分润才去分润
						if (order.getBankQfFlag() != 1) {
							// 结算，分润
							re = cusOrderPayService.separateAccounts(order, payRecord, payTime);
						}

						// 修改c_order表的清分状态
						if (re == 0 ) {
							od.setBankQfFlag(RecordBkStatus.RECORD_BK_STATUS_EN);
						}
					} else if (order.getOrderType() == null || order.getOrderType() == 1 || order.getOrderType() == 39) {

						log.info("线上订单merOrderId: {}", merOrderId);
						//	psqwAccountRecordService.onlineConsumption(order.getId(), DateUtil.stringToDate(requestParams.get("payTime")), null);

					}

					customerOrderService.updateOrder(od);

				} else {
					writer.write("FAILED");
				}
			} else {
				writer.write("FAILED");
			}

		} catch (Exception e) {
			if (null != writer) {
				writer.write("FAILED");
			}
			log.error("银联商务的Notify，调用异常", e);
		}
	}


	/**
	 * 银联商务支付return
	 *
	 * @param requestParams
	 * @return
	 */
	@RequestMapping(value = RequestContant.CHINAUMS_RETURN_PAY_CALLBACK)
	public String chinaumsPayReturn(@RequestParam Map<String, String> requestParams, Model model) {

		log.info("银联商务支付return ：" + requestParams.toString());


		if (requestParams.get("status").equals(PayConstant.ChinaUMSPayOrderStatus.TRADE_SUCCESS.getCode())) {

			try {
				String merOrderId = requestParams.get("merOrderId");		// 支付订单号

				PayRecord payRecord = payRecordServer.findPayRecordByWaterCode(merOrderId);
				OrderDTO order = customerOrderService.findCustomerOrderInfoByOrderId(payRecord.getBillNo());
				BigDecimal hqjPrice = order.getHqjPrice();

				// 全现金
				if (hqjPrice == null || BigDecimal.ZERO.compareTo(hqjPrice) >= 0) {
					String supplierID = order.getMerchantid();
					SupplierSalesDiscount salesDiscount = supplierDiscountService.findBySupplierId(Long.valueOf(supplierID));
					BigDecimal decimal = consumeService.addCashPayUserAddDjq(order.getPrice(), salesDiscount.getSalesDiscount());

					model.addAttribute("earning", "+" + decimal);

				// 券+现金
				} else {
					model.addAttribute("earning", "-" + hqjPrice);
				}
				model.addAttribute("buyerPayAmount", new BigDecimal(requestParams.get("totalAmount")).movePointLeft(2));
				model.addAttribute("totalAmount", order.getOrderPrice().toString());
				model.addAttribute("payTime", requestParams.get("payTime"));
				model.addAttribute("orderId", order.getId());
				model.addAttribute("targetSys", requestParams.get("targetSys"));
				model.addAttribute("shopName", order.getShopName());

				return "/qrcode/paySuccess";
			} catch (Exception e) {
				log.error("银联商务 --- returnUrl --- 调用异常", e);

				// 获取订单金额
				BigDecimal totalAmount = new BigDecimal(requestParams.get("totalAmount")).movePointLeft(2);

				model.addAttribute("totalAmount", totalAmount.toString());
				model.addAttribute("err", "err");

				return "/qrcode/paySuccess";
			}
		} else {
			log.info("银联商务 --- returnUrl --- 未支付" + requestParams.toString());
			return "/qrcode/payFail";
		}


	}


	/**
	 * 线上商城银联商务支付return
	 * @param requestParams
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "chinaumsOnlinePayReturn")
	public String chinaumsOnlinePayReturn(@RequestParam Map<String, String> requestParams, Model model) {

		PayResult back;
		if (requestParams.get("status").equals(PayConstant.ChinaUMSPayOrderStatus.TRADE_SUCCESS.getCode())) {
			back = new PayResult(PayConstant.PayRecordStatus.SUCCESS);
			back.setPayStatus(PayConstant.PayRecordStatus.SUCCESS);
			back.setMsgCode(CommonConstant.PayMsg.SUCCESS);

			String merOrderId = requestParams.get("merOrderId");		// 支付订单号

			PayRecord payRecord = payRecordServer.findPayRecordByWaterCode(merOrderId);

			back.setOrderOrPayId(payRecord.getBillNo());
			back.setPayAmount(new BigDecimal(requestParams.get("totalAmount")).movePointLeft(2));

		} else {
			back = new PayResult();
			back.setPayStatus(PayConstant.PayRecordStatus.FAIL);
			back.setMsgCode(CommonConstant.PayMsg.FAIL);
		}


		back.setPayModel(PayConstant.BankPayModel.PAY_TYPE_WEIXIN_GZH);
		model.addAttribute("back", back);
		model.addAttribute("payType", PayConstant.BankPayModel.PAY_TYPE_WEIXIN_GZH);

		return ResponseContant.CUS_ORDER_PAY_RESULT;
	}


	// ////////////////银联回调/////////////////////////////////////

	/**
	 * 银联front回调
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.UNION_FRONT_PAY_CALLBACK)
	public String unionFrontCall(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("unionFrontCall  call.....");
		Map<String, String> allRequestParam = ParameterTool.getAllRequestParam(request);

		User currentUser = getCurrentUser(request);

		PayResult unionCallBack = cusOrderPayService.unionCallBack(allRequestParam, currentUser);

		// PayResult unionCallBack = new PayResult(PayRecordStatus.SUCCESS);
		// unionCallBack.setMsgCode("SUCCESS");

		// 判断是否活动是否为0元购
		setActivityReturnUrl(String.valueOf(currentUser.getUserId()), model);
		setActivityNewReturnUrl(String.valueOf(currentUser.getUserId()), model);

		model.addAttribute("back", unionCallBack);

		// 根据订单号获取订单信息
		OrderDTO orderDTO = cusOrderService.findOrderByOrderId(unionCallBack.getOrderOrPayId());
		String orderSource = orderDTO.getOrderSource();
		Short orderType = orderDTO.getOrderType();

		if (OrderConstants.ORDER_SOURCE_TELECOM_RECHARGE.equals(orderSource)
				|| OrderConstants.ORDER_TYPE_100 == orderType) {
			model.addAttribute("receiveMobilePhone", orderDTO.getReceiveMobilePhone());
			log.info("recharge order .return recharge pay result page.");
			return ResponseContant.CUS_ORDER_PAY_RESULT_RECHARGE;
		}

		return ResponseContant.CUS_ORDER_PAY_RESULT;
	}

	/**
	 * 银联后台back回调
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.UNION_BACK_PAY_CALLBACK)
	public String unionBackCall(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("unionBackCall execuyte....");
		Map<String, String> allRequestParam = ParameterTool.getAllRequestParam(request);

		User currentUser = getCurrentUser(request);

		PayResult unionCallBack = cusOrderPayService.unionCallBack(allRequestParam, currentUser);

		model.addAttribute("back", unionCallBack);

		return null;
	}

	/**
	 * E支付回调
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = RequestContant.E_BESTOAY_PAY_CALLBACK)
	public String bestoayPayBackCall(Model model, BestoayMobilePayResultUM bestoayMobilePayResultUM,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("bestoayPayBackCall execute...");
		log.info("bestoayMobilePayResultUM:" + bestoayMobilePayResultUM.toString());

		PayResult bestoayCallBack = cusOrderPayService.payCallBack(bestoayMobilePayResultUM,
				BankPayModel.PAY_TYPE_BESTOAYPAY_MOBILE_WAP);
		log.info("payResult-->" + bestoayCallBack.toString());
		// 判断是否活动是否为0元购
		/*
		 * User currentUser = getCurrentUser(request);
		 * setActivityReturnUrl(String.valueOf(currentUser.getUserId()), model);
		 */
		model.addAttribute("msgCode", bestoayCallBack.getMsgCode());
		model.addAttribute("back", bestoayCallBack);
		model.addAttribute("orderId", bestoayCallBack.getOrderOrPayId());
		log.info("bestoay pay return zhongjumall  execute end.");
		return "UPTRANSEQ_" + bestoayMobilePayResultUM.getUPTRANSEQ();
	}

	/**
	 * E支付返回商城
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.E_BESTOAY_PAY_NOTIFY)
	public String bestoayPayNotify(Model model, BestoayWapPayNotify bestoayWapPayNotify, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("bestoay pay notify zhongjumall execute...");
		log.info("bestoay pay notify zhongjumall params {}" + bestoayWapPayNotify);
		String msgCode = PayMsg.SUCCESS;
		if (BestoayNotifyResult.F.equals(bestoayWapPayNotify.getResultCode())
				|| BestoayNotifyResult.C.equals(bestoayWapPayNotify.getResultCode())) {
			msgCode = PayMsg.FAIL;
		}
		PayResult bestoayNotify = new PayResult();
		bestoayNotify.setMsgCode(msgCode);
		bestoayNotify.setOrderOrPayId(Long.valueOf(bestoayWapPayNotify.getOrderSeq()));
		bestoayNotify.setPayAmount(PriceUtils.strToBigDecimal(bestoayWapPayNotify.getOrderAmt()));
		model.addAttribute("msgCode", bestoayNotify.getMsgCode());
		model.addAttribute("back", bestoayNotify);
		model.addAttribute("userId", null);
		model.addAttribute("orderId", null);
		log.info("bestoay pay return zhongjumall  execute end.");
		return ResponseContant.CUS_ORDER_PAY_RESULT;
	}

	@RequestMapping(value = RequestContant.BOC_CROSS_PAY_CALLBACK)
	public String bocCrossPayCallback(@RequestParam Map<String, String> requestParam, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("boc cross pay callback..");
		log.info("boc cross pay callback {}", requestParam);
		PayResult payResult = cusOrderPayService.bocCrossPayCallBack(requestParam,
				BankPayModel.PAY_TYPE_BOC_B2C_CROSSBORDER_WAP);
		model.addAttribute("msgCode", payResult.getMsgCode());
		model.addAttribute("back", payResult);
		model.addAttribute("orderId", payResult.getOrderOrPayId());

		log.info("bestoay pay return zhongjumall  execute end.");
		return ResponseContant.CUS_ORDER_PAY_RESULT;

	}

	@RequestMapping(value = RequestContant.BOC_CROSS_REFUND_CALLBACK)
	public String bocCrossRefundCallback(@RequestParam Map<String, String> requestParam, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("boc cross refund callback..");
		log.info("boc cross refund callback {}", requestParam);
		cusOrderPayService.bocCrossRefundCallBack(requestParam, BankPayModel.PAY_TYPE_BOC_B2C_CROSSBORDER_WAP);
		log.info("boc cross refund return zhongjumall  execute end.");
		return null;

	}

	@RequestMapping(value = RequestContant.BOC_CROSS_CHECK_PAY_CALLBACK)
	public String bocCrossQueryCallback(@RequestParam Map<String, String> requestParam, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("boc cross query callback..");
		log.info("boc cross query callback {}", requestParam);
		QueryResult queryResult = cusOrderPayService.bocCrossQueryCallBack(requestParam,
				BankPayModel.PAY_TYPE_BOC_B2C_CROSSBORDER_WAP);
		model.addAttribute("back", queryResult);
		log.info("bestoay query return zhongjumall  execute end.");
		return null;

	}

	@RequestMapping(value = RequestContant.BOC_CROSS_ACCOUNT_CHECK_CALLBACK)
	public String bocCrossAccountCheckCallback(@RequestParam Map<String, String> requestParam, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("boc cross account check callback..");
		if (log.isDebugEnabled()) {
			log.info("boc cross account check callback {}", requestParam);
		}
		CheckResult checkResult = null;
		try {
			checkResult = cusOrderPayService.bocCrossAccountCheckCallBack(requestParam,
					BankPayModel.PAY_TYPE_BOC_B2C_CROSSBORDER_WAP);
		} catch (Exception e) {
			log.info("bocCrossAccountCheckCallback exception", e);
		}
		if (checkResult == null) {
			throw new BusinessException("boc cross account check exception--> checkResult is null");
		}
		log.info("queryResult --->" + checkResult.toString());
		log.info("boc cross account check return zhongjumall  execute end.");
		return null;

	}

	// @AuthPassport
	@RequestMapping(value = RequestContant.BOC_NET_BACK_PAY_CALLBACK)
	public String bocNetPayCallback(@RequestParam Map<String, String> requestParams, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		PayResult payResult = null;

		String sid = getUUID.getSessionId(request, response);
		String orderIdUserId = null;
		String platForm = null;
		log.info("bocNetPayCallback session id-->" + sid);
		try {
			orderIdUserId = memcachedClient.get("pay" + sid);
			platForm = memcachedClient.get("payPlatForm" + sid);
		} catch (TimeoutException e) {
			log.error("memcached timeout get pay or platForm" + e.getMessage(), e);
		} catch (InterruptedException e) {
			log.error("memcached Interrupted get pay or platForm" + e.getMessage(), e);
		} catch (MemcachedException e) {
			log.error("memcached get pay or platForm" + e.getMessage(), e);
		}
		log.info("orderIdUserId-->" + orderIdUserId);
		// 如果返回码都为空 就是支付失败
		if (StringUtils.isBlank(requestParams.get("orderNo"))) {
			log.info("orderNo=" + requestParams.get("orderNo"));
			PayResult callBackError = new PayResult();
			callBackError.setMsgCode("F");
			if (!StringUtils.isEmpty(orderIdUserId)) {
				String[] split = orderIdUserId.split(" ");
				String userId = split[0];
				String orderId = split[1];
				// setAppParam(model, orderId, userId, platForm, payResult);
				User user = new User();
				user.setUserId(Long.valueOf(userId));
				OrderDTO orderDTO = cusOrderService.findOrderByOrderId(Long.valueOf(orderId), user);
				String orderJson = JsonUtil.serializeBeanToJsonString(orderDTO);
				// 说明是android端支付的
				model.addAttribute("supplyType", orderDTO.getSupplyType());
				model.addAttribute("status", orderDTO.getStatus());
				model.addAttribute("userId", userId);
				model.addAttribute("orderId", orderId);
				model.addAttribute("platForm", platForm);
				model.addAttribute("msgCode", payResult.getMsgCode());
				model.addAttribute("order", orderJson);
			}

			model.addAttribute("back", callBackError);
			return ResponseContant.CUS_ORDER_BOC_PAY_RESULT;
		}

		payResult = cusOrderPayService.bocNetPayCallBack(requestParams, BankPayModel.PAY_TYPE_BOC_B2C_MOBILE_NETPAY);
		if (payResult == null) {
			log.error("callback is null ");
			throw new BusinessException("call back is null ");
		}

		// 商品信息回显
		// OrderDTO findOrderByOrderId=null;
		// if(callBack!=null){
		// Long orderOrPayId = callBack.getOrderOrPayId();
		// findOrderByOrderId = cusOrderService.findOrderByOrderId(orderOrPayId,
		// currentUser);
		// }
		// model.addAttribute("orderDto", findOrderByOrderId);

		// app端 --1:android2:IOS-- 支付宝支付成功后回调
		if (!StringUtils.isEmpty(orderIdUserId)) {
			String[] split = orderIdUserId.split(" ");
			String userId = split[0];
			String orderId = split[1];
			// 判断活动是否为0元购
			setActivityReturnUrl(userId, model);
			setActivityNewReturnUrl(userId, model);
			// setAppParam(model, orderId, userId, platForm, payResult);
			// Long orderOrPayId = payResult.getOrderOrPayId();
			User user = new User();
			user.setUserId(Long.valueOf(userId));
			OrderDTO orderDTO = cusOrderService.findOrderByOrderId(Long.valueOf(orderId), user);
			String orderJson = JsonUtil.serializeBeanToJsonString(orderDTO);
			// 说明是android端支付的
			model.addAttribute("supplyType", orderDTO.getSupplyType());
			model.addAttribute("status", orderDTO.getStatus());
			model.addAttribute("userId", userId);
			model.addAttribute("orderId", orderId);
			model.addAttribute("platForm", platForm);
			model.addAttribute("msgCode", payResult.getMsgCode());
			model.addAttribute("order", orderJson);
		} else {
			User user = getCurrentUser(request);
			// 判断是否活动是否为0元购
			setActivityReturnUrl(String.valueOf(user.getUserId()), model);
			setActivityNewReturnUrl(String.valueOf(user.getUserId()), model);
		}

		model.addAttribute("back", payResult);
		log.info("payResult --->" + payResult.toString());
		log.info("boc net pay callback execute end.");

		// 根据订单号获取订单信息
		OrderDTO orderDTO = cusOrderService.findOrderByOrderId(payResult.getOrderOrPayId());
		String orderSource = orderDTO.getOrderSource();
		Short orderType = orderDTO.getOrderType();

		if (OrderConstants.ORDER_SOURCE_TELECOM_RECHARGE.equals(orderSource)
				|| OrderConstants.ORDER_TYPE_100 == orderType) {
			model.addAttribute("receiveMobilePhone", orderDTO.getReceiveMobilePhone());
			log.info("recharge order .return recharge pay result page.");
			return ResponseContant.CUS_ORDER_PAY_RESULT_RECHARGE;
		}

		return ResponseContant.CUS_ORDER_BOC_PAY_RESULT;

	}

	/**
	 * TODO 中行信用卡支付回调函数
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = RequestContant.BOC_NCP_BACK_PAY_CALLBACK)
	public String bocNcpPayCallback(BocNcpResponseDto bocNcpResponseDto, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		log.info("boc ncp pay callback..");
		log.info("boc ncp pay callback {}", bocNcpResponseDto);
		PayResult payResult = cusOrderPayService.bocNcpPayCallBack(bocNcpResponseDto, BankPayModel.PAY_TYPE_NCPKJ_PAY);
		model.addAttribute("msgCode", payResult.getMsgCode());
		model.addAttribute("back", payResult);
		model.addAttribute("orderId", payResult.getOrderOrPayId());

		log.info("boc ncp pay return zhongjumall execute end.");

		// 根据订单号获取订单信息
		OrderDTO orderDTO = cusOrderService.findOrderByOrderId(payResult.getOrderOrPayId());
		String orderSource = orderDTO.getOrderSource();
		Short orderType = orderDTO.getOrderType();

		if (OrderConstants.ORDER_SOURCE_TELECOM_RECHARGE.equals(orderSource)
				|| OrderConstants.ORDER_TYPE_100 == orderType) {
			model.addAttribute("receiveMobilePhone", orderDTO.getReceiveMobilePhone());
			log.info("recharge order .return recharge pay result page.");
			return ResponseContant.CUS_ORDER_PAY_RESULT_RECHARGE;
		}

		return ResponseContant.CUS_ORDER_PAY_RESULT;
	}

	private void setAppParam(Model model, String orderId, String userId, String platForm, PayResult payResult) {
		Long orderOrPayId = payResult.getOrderOrPayId();
		// 说明是android端支付的
		if (("" + orderOrPayId).equals(orderId)) {
			model.addAttribute("userId", userId);
			model.addAttribute("orderId", orderId);
			model.addAttribute("platForm", platForm);
			model.addAttribute("msgCode", payResult.getMsgCode());
		}
	}

	/**
	 * 添加0元购回调路径
	 * 
	 * @param userId
	 * @param model
	 */
	private void setActivityReturnUrl(String userId, Model model) {
		// 判断是否活动是否为0元购
		try {
			Object activityFlag = memcachedClient.get("UserOriginZeroActivity" + userId);

			log.info("获取userId-->" + userId);
			log.info("获取活动标示--->" + activityFlag);
			// 设置买一赠二
			if (activityFlag != null && "881".equals(activityFlag)) {
				memcachedClient.delete("UserOriginZeroActivity" + userId);
				model.addAttribute("activity", "act/maiyizenger.html");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加0元购回调路径
	 * 
	 * @param userId
	 * @param model
	 */
	private void setActivityNewReturnUrl(String userId, Model model) {
		// 判断是否活动是否为翼支付活动
		try {
			// Object activityFlag =
			// memcachedClient.get("UserOriginZeroActivity" + userId);
			User user = userService.findUserById(Long.parseLong(userId));
			if (null != user) {
				log.info("获取userId-->" + userId);
				log.info("获取活动标示--->" + user.getOrigin());
				// 设置翼支付活动
				if ("884".equals(user.getOrigin())) {
					model.addAttribute("activityNew", "act/yiZhiFuActivity.html");// 具体页面需要运营提供
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
