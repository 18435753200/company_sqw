package com.mall.service;

import com.alibaba.fastjson.JSON;
import com.mall.architect.redis.JedisManager;
import com.mall.architect.redis.common.RedisKeyPrefix;
import com.mall.commons.utils.DateUtil;
import com.mall.constant.CommonConstant;
import com.mall.customer.constant.RecordBkStatus;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.common.OrderStatusConstant;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.po.CreateFalseOrder;
import com.mall.customer.order.po.Order;
import com.mall.customer.service.CashAccountService;
import com.mall.customer.service.DividendService;
import com.mall.customer.service.PsqwAccountRecordService;
import com.mall.customer.service.UserService;
import com.mall.customer.vo.UmsCashAccount;
import com.mall.exception.BusinessException;
import com.mall.pay.api.rpc.*;
import com.mall.pay.common.ConvertUtils;
import com.mall.pay.common.PayConstant;
import com.mall.pay.common.PayConstant.BankPayModel;
import com.mall.pay.common.PayConstant.BocNcpTxnSubType;
import com.mall.pay.common.PayConstant.PayChannel;
import com.mall.pay.dto.*;
import com.mall.pay.exception.ApiException;
import com.mall.pay.exception.PayException;
import com.mall.pay.po.BankLog;
import com.mall.pay.po.BocCrossBorder;
import com.mall.pay.po.PayMessageOnlineBank;
import com.mall.pay.po.PayRecord;
import com.mall.pay.po.alipay.AlipayResult;
import com.mall.pay.po.alipay.AlipayWapRequest;
import com.mall.pay.po.chinaums.ChinaUMSWapRequest;
import com.mall.pay.po.union.wap.PayWapOrderResponseUM;
import com.mall.supplier.enums.SupplierConstant;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierAgentType;
import com.mall.supplier.service.SupplierAgentTypeService;
import com.mall.supplier.service.SupplierDiscountService;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.utils.JsonUtil;
import com.mall.utils.Oauth;
import com.mall.utils.ParameterTool;
import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

/**
 * 支付先关的服务
 *
 * @author ccigQA01
 */
@Service
public class CusOrderPayService {
	private static final Logger log = LoggerFactory
			.getLogger(CusOrderPayService.class);

	@Autowired
	private MemcachedClient memcachedClient;

	@Autowired
	private CusOrderService cusOrderService;

	@Autowired
	private BankPayService bankPayService;

	@Autowired
	private BankRefundService bankRefundService;

	@Autowired
	private CheckPayMainService checkPayMainService;

	@Autowired
	private BankAccountCheckService bankAccountCheckService;

	@Autowired
	private BocB2CCrossBorderAgreementMainService bocB2CCrossBorderAgreementMainService;

	@Autowired
	private BankQueryService bankQueryService;

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private SupplierManagerService supplierManagerService;

	@Autowired
	private SupplierAgentTypeService supplierAgentTypeService;

	@Autowired
	private CashAccountService cashAccountService;

	@Autowired
	private PsqwAccountRecordService psqwAccountRecordService;

	@Autowired
	private SupplierDiscountService supplierDiscountService;

	@Autowired
	private UserService userService;

	@Autowired
	private DividendService dividendService;

	@Autowired
	private PayLogService payLogService;

	/**
	 * 获取支付请求信息
	 *
	 * @param orderId
	 * @param currentUser
	 * @param request
	 * @param model
	 * @return
	 */
	private PayRequest getPayRequestInfo(Long orderId, Short bankPayModel,
										 Short channelNo, User currentUser, HttpServletRequest request,
										 Model model) {
		PayRequest payRequest = new PayRequest();

		OrderDTO orderdto = cusOrderService.findOrderByOrderId(orderId,
				currentUser);

		if (orderdto == null) {
			log.error("orderDto is null");
			throw new BusinessException(
					"call  cusOrderService.findOrderByOrderId error");
		}
		payRequest.setPayId(orderdto.getPayId());
		payRequest.setOrderId(orderId);
		if (orderdto.getMixPayStatus() == null) {
			payRequest.setPayAmount(orderdto.getPrice());
		} else {
			//payRequest.setPayAmount( orderdto.getPrice().multiply(orderdto.getHqRatio()).setScale(0, BigDecimal.ROUND_HALF_UP));
			//总价-已支付红旗券( 5/5分时四舍五入和超总价)
			//payRequest.setPayAmount( orderdto.getPrice().subtract(orderdto.getPrice().multiply(orderdto.getHqRatio())).setScale(0, BigDecimal.ROUND_HALF_UP));
			if (orderdto.getCashPrice() != null && orderdto.getHqjPrice() != null && orderdto.getCashPrice().compareTo(
					BigDecimal.valueOf(0)) == 1
					&& orderdto.getHqjPrice().compareTo(
					BigDecimal.valueOf(0)) == 1) {


				payRequest.setPayAmount(orderdto.getCashPrice());
			} else {
				payRequest.setPayAmount(orderdto.getPrice().subtract(orderdto.getPrice().multiply(orderdto.getHqRatio())).setScale(0, BigDecimal.ROUND_HALF_UP));

			}
		}
		if (CommonConstant.ORDER_SOURCE_BOCBEIFEN.equals(orderdto
				.getOrderSource())
				|| CommonConstant.ORDER_SOURCE_BOCNINGBO.equals(orderdto
				.getOrderSource())) {
			payRequest.setPartnerCode(CommonConstant.ACCOUNT_TYPE_XGX);
		} else {
			payRequest.setPartnerCode(orderdto.getAccountType());
		}
		payRequest.setOrderDate(orderdto.getCreateTime());
		payRequest.setBizType(orderdto.getCustomsCode());

		// 首付 b2c是一次性付清
		Short payModel = PayConstant.PaymentType.PAYMENT_TYPE_CODE_ALL;
		payRequest.setPayModel(payModel);
		// channelNo
		// 支付平台为wap 4
		payRequest.setChannelNo(channelNo);

		payRequest.setBankPayModel(bankPayModel);

		payRequest.setRetailerId(currentUser.getUserId());

		payRequest.setIp(request.getRemoteAddr());

		payRequest.setB2C(true);

		return payRequest;
	}

	/**
	 * 获取支付对账户请求信息
	 *
	 * @param orderId
	 * @param currentUser
	 * @param request
	 * @param model
	 * @return
	 */
	private CheckRequest getPayAccountRequestInfo(Date startDate, Date endDate,
												  Short bankPayModel, Short bizType, HttpServletRequest request,
												  Model model) {
		CheckRequest checkRequest = new CheckRequest();
		checkRequest.setB2C(true);
		checkRequest.setBankPayModel(bankPayModel);
		checkRequest.setBizType(bizType);
		checkRequest.setStartDate(startDate);
		checkRequest.setEndDate(endDate);
		checkRequest.setIp(request.getRemoteAddr());

		return checkRequest;
	}

	// /////////////支付宝支付相关/////////////////////

	/**
	 * 支付宝支付
	 *
	 * @param orderId
	 * @param currentUser
	 * @param request
	 * @param response
	 * @param model
	 * @param channelNo   平台号 android 1 wap4
	 * @param act         aliPay-国际支付 aliPayDirect-国内
	 * @return 返回 带有加密过参数的url
	 */
	public String aliPayInfo(Long orderId, Short channelNo, User currentUser,
							 HttpServletRequest request, HttpServletResponse response,
							 Model model, String act) {
		log.info("aliPayInfo execute.... orderId = " + orderId);

		// 支付平台---支付宝的---9
		Short bankPayModel = CommonConstant.PAY_TYPE_ALIPAY_WAP;
		if (CommonConstant.ALIPAY_DIRECT.equals(act)) {
			bankPayModel = PayConstant.BankPayModel.PAY_TYPE_ALIPAY_INLAND_WAP;
		}
		// 获取支付请求信息
		PayRequest payRequest = getPayRequestInfo(orderId, bankPayModel,
				channelNo, currentUser, request, model);
		// 获取发送到支付宝的请求信息
		PayResult pay = bankPayService.pay(payRequest);
		if (CommonConstant.ALIPAY.equals(act)
				&& (pay == null || pay.getRequestBody() == null)) {
			log.error("pay is null");
			throw new BusinessException("call bankPayService.pay error");
		}
		if (CommonConstant.ALIPAY_DIRECT.equals(act)
				&& (pay == null || pay.getRequestParams() == null)) {
			log.error("pay or pay.getRequestParams is null");
			throw new BusinessException("call bankPayService.pay error");
		}
		if (CommonConstant.ALIPAY_DIRECT.equals(act)) {
			model.addAttribute("payType",
					PayConstant.BankPayModel.PAY_TYPE_ALIPAY_INLAND_WAP);
		} else {
			model.addAttribute("payType", CommonConstant.PAY_TYPE_ALIPAY_WAP);
		}
		String serializeBeanToJsonString = JsonUtil
				.serializeBeanToJsonString(pay);
		log.info("payInfo:    " + serializeBeanToJsonString);

		// FIXME 此处写死 ，后期改变
		// requestBody.setNotify_url("http://127.0.0.1:8080/wap-customer/orderPay/aLiPay/notify/wap/payResultBack");
		// requestBody.setReturn_url("http://127.0.0.1:8080/wap-customer/orderPay/aLiPay/return/wap/payResultBack");

		String redirectUrl = "";// 带参数的url
		String requestUrl = ""; // 没有参数的url
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (CommonConstant.ALIPAY_DIRECT.equals(act)) {
				Map<String, String> mapTemp = pay.getRequestParams();
				if (mapTemp != null) {
					map.putAll(mapTemp);
				} else {
					log.info("alipay_direct:pay.getRequestParams()=" + mapTemp);
				}
				requestUrl = pay.getAction();
			} else {
				AlipayWapRequest requestBody = (AlipayWapRequest) pay
						.getRequestBody();
				map = ParameterTool.convertRequestToMap(requestBody);
				// requestUrl = requestBody.getRequestUrl();
				requestUrl = pay.getAction();
			}
			requestUrl = requestUrl.replace("?_input_charset=utf-8", "");
			String parames = ParameterTool.mapToUrl(map);
			redirectUrl = ParameterTool.getRedirectUrl(requestUrl, parames);
			log.info("requestBodymap" + map);
		} catch (Exception e) {
			log.error("getRedirectUrl error" + e);
			throw new BusinessException("call getRedirectUrl error " + e);
		}
		log.info("redirectUrl is :" + redirectUrl);
		return redirectUrl;
	}

    /**
     * 银联商务支付 异步响应
     *
     * @param requestParams
     * @param bankPayModel
     * @return
     */
    public PayResult chinaumsPayCallBack(Map<String, String> requestParams,
                                   Short bankPayModel) {
        PayRequest payRequest = new PayRequest();
        payRequest.setRequestParams(requestParams);
        payRequest.setBankPayModel(bankPayModel);
        payRequest.setChannelNo(PayChannel.WAY_WAP);

        log.info("银联商务支付 异步响应信息---->" + payRequest.toString());
        PayResult payCallback = bankPayService.payCallback(payRequest);
        return payCallback;
    }

	/**
	 * @param requestParams
	 * @param bankPayModel
	 * @return
	 * @author windyluo
	 */

	public PayResult jdPayCallBack(Map<String, String> requestParams,
								   Short bankPayModel) {
		PayRequest payRequest = new PayRequest();
		payRequest.setRequestParams(requestParams);
		payRequest.setBankPayModel(bankPayModel);
		payRequest.setChannelNo(PayChannel.WAY_WAP);

		log.info("京东支付 异步响应信息---->" + payRequest.toString());
		PayResult payCallback = bankPayService.payCallback(payRequest);
		return payCallback;
	}

	/**
	 * 京东异步通知回调
	 *
	 * @param requestParams
	 * @param bankPayModel
	 * @return
	 */
	public PayResult jdPayNotifyCallBack(Map<String, String> requestParams,
										 Short bankPayModel, HttpServletRequest request,
										 HttpServletResponse response) {
		PayResult payCallback = null;
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) request.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			Map<String, String> requestPs = new HashMap<String, String>();
			requestPs.put("NotifyCallBackBody", sb.toString());
			PayRequest payRequest = new PayRequest();
			payRequest.setRequestParams(requestPs);
			payRequest.setBankPayModel(bankPayModel);
			payRequest.setChannelNo(PayChannel.WAY_WAP);
			log.info("京东支付 异步响应信息---->" + payRequest.toString());
			payCallback = bankPayService.payCallback(payRequest);
		} catch (IOException e) {

		}
		return payCallback;
	}

	/**
	 * 红旗券支付
	 *
	 * @param orderId
	 * @param currentUser
	 * @param request
	 * @param response
	 * @param model
	 * @param channelNo   平台号 android 1 wap4
	 * @param act         aliPay-国际支付 aliPayDirect-国内
	 * @return 返回 带有加密过参数的url
	 */
	public PayResult hqPayInfo(Long orderId, Short channelNo, User currentUser,
							   HttpServletRequest request, HttpServletResponse response,
							   Model model, String act) {
		log.info("aliPayInfo execute.... orderId = " + orderId);

		// 红旗券
		Short bankPayModel = PayConstant.BankPayModel.PAY_TYPE_HQ_INTEGRAL;
		model.addAttribute("payType", bankPayModel);
		// 获取支付请求信息
		PayRequest payRequest = getPayRequestInfo(orderId, bankPayModel,
				channelNo, currentUser, request, model);
		// 获取发送到支付宝的请求信息
		PayResult pay = bankPayService.pay(payRequest);
		String serializeBeanToJsonString = JsonUtil
				.serializeBeanToJsonString(pay);
		log.info("payInfo:    " + serializeBeanToJsonString);
		return pay;
	}

	/**
	 * 翼支付
	 *
	 * @param orderId
	 * @param channelNo
	 * @param currentUser
	 * @param request
	 * @param response
	 * @param model
	 * @param act
	 * @return
	 */
	public PayResult bestoayPay(Long orderId, Short channelNo,
								User currentUser, HttpServletRequest request,
								HttpServletResponse response, Model model, String act) {
		log.info("bestoayPay execute.... orderId = " + orderId);
		// 支付平台---翼支付---41
		Short bankPayModel = PayConstant.BankPayModel.PAY_TYPE_BESTOAYPAY_MOBILE_WAP;
		// 获取支付请求信息
		PayRequest payRequest = getPayRequestInfo(orderId, bankPayModel,
				channelNo, currentUser, request, model);
		payRequest.setUserMobile(currentUser.getMobile());
		// 获取发送翼支付的请求信息
		PayResult pay = bankPayService.pay(payRequest);
		return pay;
	}

	/**
	 * 红旗券支付添加callback方法 添加service 后台服务 对信息进行处理
	 *
	 * @param parameterMap
	 * @param currentUser
	 * @return
	 */
	public PayResult HqPayCallBack(Map<String, String> requestParams,
								   Short bankPayModel) {
		PayRequest payRequest = new PayRequest();
		payRequest.setRequestParams(requestParams);
		payRequest.setBankPayModel(bankPayModel);
		log.info("红旗券支付响应信息---->" + payRequest.toString());
		PayResult payCallback = bankPayService.payCallback(payRequest);

		return payCallback;
	}

	/**
	 * 微信公从号支付
	 *
	 * @param orderId
	 * @param channelNo
	 * @param currentUser
	 * @param request
	 * @param response
	 * @param model
	 * @param act
	 * @return
	 */
	public PayResult weiXinGZHPay(Long orderId, String openId, String nonceStr,
								  String timestamp, Short channelNo, User currentUser,
								  HttpServletRequest request, HttpServletResponse response,
								  Model model, String act) {
		log.info("weiXinGZHPay execute.... orderId = " + orderId);
		// 支付平台---微信公从号支付
		Short bankPayModel = PayConstant.BankPayModel.PAY_TYPE_WEIXIN_GZH;
		// 获取支付请求信息
		PayRequest payRequest = getPayRequestInfo(orderId, bankPayModel,
				channelNo, currentUser, request, model);
		payRequest.setAuthCode(openId);
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("nonceStr", nonceStr);
		requestParams.put("timestamp", timestamp);
		payRequest.setRequestParams(requestParams);
		// 获取发送翼支付的请求信息
		PayResult pay = bankPayService.pay(payRequest);
		return pay;
	}

	/**
	 * 后台服务 对信息进行处理
	 *
	 * @param parameterMap
	 * @param currentUser
	 * @return
	 */
	public PayResult aliCallBack(Map<String, String> parameterMap,
								 User currentUser) {
		PayRequest payRequest = new PayRequest();

		AlipayResult alipayResult = new AlipayResult();
		try {
			alipayResult = (AlipayResult) ConvertUtils.mapToBean(parameterMap,
					AlipayResult.class);
		} catch (Exception e) {
			log.error(" map  to bean errror" + e);
			e.printStackTrace();
		}
		payRequest.setBaseBodyDto(alipayResult);
		// payRequest.setRetailerId(currentUser.getUserId());

		payRequest.setBankPayModel(CommonConstant.PAY_TYPE_ALIPAY_WAP);
		PayResult payCallback = bankPayService.payCallback(payRequest);

		return payCallback;
	}

	/**
	 * 添加支付宝手机端插件回调路径 添加对应常量 添加callback方法 添加service 后台服务 对信息进行处理
	 *
	 * @param parameterMap
	 * @param currentUser
	 * @return
	 */
	public PayResult aliMobileCallBack(Map<String, String> requestParams,
									   Short bankPayModel) {
		PayRequest payRequest = new PayRequest();
		payRequest.setRequestParams(requestParams);
		payRequest.setBankPayModel(bankPayModel);
		log.info("支付宝SDK 异步响应信息---->" + payRequest.toString());
		PayResult payCallback = bankPayService.payCallback(payRequest);

		return payCallback;
	}

	// ///////////////银联支付相关///////////////////////

	/**
	 * 银联支付
	 *
	 * @param orderId
	 * @param currentUser
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	public PayResult unionWapPayInfo(Long orderId, Short channelNo,
									 User currentUser, HttpServletRequest request,
									 HttpServletResponse response, Model model) {

		if (orderId == null) {
			return null;
		}

		// 支付平台 ---wap ----5
		Short bankPayModel = CommonConstant.PAY_TYPE_UNION_WAPTPAY;
		PayRequest payRequest = getPayRequestInfo(orderId, bankPayModel,
				channelNo, currentUser, request, model);
		// 为b2c
		payRequest.setB2C(true);
		// 获取银联支付需要的请求信息.
		PayResult pay = null;
		try {
			pay = bankPayService.pay(payRequest);
		} catch (Exception e) {
			log.error("call pay failed", e.getMessage(), e);
			throw new BusinessException();
		}

		return pay;
	}

	public PayResult jdPayInfo(Long orderId, Short channelNo, User currentUser,
							   HttpServletRequest request, HttpServletResponse response,
							   Model model) {
		log.info("jdPayInfo execute.... orderId = " + orderId);

		Short bankPayModel = PayConstant.BankPayModel.PAY_TYPE_JDPAY_INTEGRAL;

		// 获取支付请求信息
		PayRequest payRequest = getPayRequestInfo(orderId, bankPayModel,
				channelNo, currentUser, request, model);
		// 获取发送到京东的请求信息
		PayResult pay = bankPayService.pay(payRequest);
		String serializeBeanToJsonString = JsonUtil
				.serializeBeanToJsonString(pay);
		log.info("payInfo:    " + serializeBeanToJsonString);
		return pay;
	}

	/**
	 * 银联wap支付回调
	 *
	 * @param allRequestParam
	 * @param currentUser
	 * @return
	 */
	public PayResult unionCallBack(Map<String, String> allRequestParam,
								   User currentUser) {
		log.info("cal  unionCallBack ...");
		PayRequest payRequest = new PayRequest();
		PayWapOrderResponseUM payWapOrderResponseUM = null;
		try {
			log.info("银联手机callback 信息:   ----->" + allRequestParam.toString());
			payWapOrderResponseUM = (PayWapOrderResponseUM) ConvertUtils
					.mapToBean(allRequestParam, PayWapOrderResponseUM.class);
			payRequest.setBankPayModel(CommonConstant.PAY_TYPE_UNION_WAPTPAY);
			payRequest.setBaseBodyDto(payWapOrderResponseUM);
		} catch (Exception e) {
			log.error("maptobean error ", e);
			throw new BusinessException("ConvertUtils.mapToBean ", e);
		}
		PayResult payCallback = bankPayService.payCallback(payRequest);

		return payCallback;

	}

	/**
	 * 支付回调
	 *
	 * @param payWapOrderResponseUM
	 * @param payType
	 * @return
	 */
	public PayResult payCallBack(BaseBodyDto baseBodyDto, short payType) {
		PayRequest payRequest = new PayRequest();
		payRequest.setBankPayModel(payType);
		payRequest.setBaseBodyDto(baseBodyDto);

		PayResult payCallback = bankPayService.payCallback(payRequest);

		return payCallback;

	}

	/**
	 * 中行跨境支付回调
	 *
	 * @param allRequestParam
	 * @param currentUser
	 * @return
	 */
	public PayResult bocCrossPayCallBack(Map<String, String> requestParams,
										 Short bankPayModel) {
		log.info("cal  bocCross CallBack ...");
		PayRequest payRequest = new PayRequest();
		payRequest.setRequestParams(requestParams);
		payRequest.setBankPayModel(bankPayModel);
		log.info("中行跨境 异步响应信息---->" + payRequest.toString());
		PayResult payCallback = bankPayService.payCallback(payRequest);

		return payCallback;
	}

	/**
	 * 中行跨境支付回调
	 *
	 * @param allRequestParam
	 * @param currentUser
	 * @return
	 */
	public QueryResult bocCrossQueryCallBack(Map<String, String> requestParams,
											 Short bankPayModel) {
		log.info("cal  bocCross query CallBack ...");
		QueryRequest queryRequest = new QueryRequest();
		queryRequest.setBankPayModel(bankPayModel);
		queryRequest.setRequestParams(requestParams);
		log.info("中行跨境 异步响应信息---->" + queryRequest.toString());
		QueryResult queryResult = bankQueryService.callBack(queryRequest);
		return queryResult;
	}

	/**
	 * 中行跨境退款回调
	 *
	 * @param allRequestParam
	 * @param currentUser
	 * @return
	 */
	public RefundResult bocCrossRefundCallBack(
			Map<String, String> requestParams, Short bankPayModel) {
		log.info("cal  bocCross CallBack ...");
		RefundRequest refundRequest = new RefundRequest();
		refundRequest.setRequestParams(requestParams);
		refundRequest.setBankPayModel(bankPayModel);
		log.info("中行跨境退款 异步响应信息---->" + refundRequest.toString());
		RefundResult callBack = bankRefundService.callBack(refundRequest);

		return callBack;
	}

	/**
	 * 中行签约检查
	 */
	public BocCrossBorder findBoccSignByUserIdDefualt(User currUser) {
		List<BocCrossBorder> list = bocB2CCrossBorderAgreementMainService
				.selectByUserId(currUser.getUserId());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;

	}

	/**
	 * 中行签约检查
	 */
	public List<BocCrossBorder> findBoccSignByUserId(User currUser) {
		return bocB2CCrossBorderAgreementMainService.selectByUserId(currUser
				.getUserId());

	}

	public BocCrossBorder bocCrossSign(String DRACC_NO, String DBT_NAME,
									   String DBT_CODE, String DBT_PHONE, User currentUser) {
		return bocB2CCrossBorderAgreementMainService.sendSigningDataToBOC(
				PayConstant.BoccActType.QY, PayConstant.BoccCodeType.SFZ,
				DBT_CODE, DBT_NAME, DBT_PHONE, DRACC_NO,
				PayConstant.PayCurrency.PAY_TYPE_DRACC_CUR,
				currentUser.getUserId());
	}

	public void bocCrossSignCallBack(String xml) {
		bocB2CCrossBorderAgreementMainService.callBackForSigning(xml);
	}

	public void checkAuthCode(String cTrxSerno, String checkCode)
			throws ParseException {
		bocB2CCrossBorderAgreementMainService.sendSigningBocCheckData(
				cTrxSerno, checkCode);
	}

	public void authCallback(String xml) throws ParseException {
		bocB2CCrossBorderAgreementMainService.callBackForCheck(xml);
	}

	public String checkSignState(String trxSerno) throws ParseException {
		return bocB2CCrossBorderAgreementMainService.checkSignStatus(trxSerno);
	}

	/**
	 * 支付宝支付
	 *
	 * @param orderId
	 * @param currentUser
	 * @param request
	 * @param response
	 * @param model
	 * @param channelNo   平台号 android 1 wap4
	 * @param act         aliPay-国际支付 aliPayDirect-国内
	 * @return 返回 带有加密过参数的url
	 */
	public PayResult boccPay(Long orderId, Short channelNo, User currentUser,
							 HttpServletRequest request, HttpServletResponse response,
							 Model model, String signNo) {

		if (orderId == null) {
			return null;
		}

		// 支付平台 ---wap ----13
		Short bankPayModel = BankPayModel.PAY_TYPE_BOC_B2C_CROSSBORDER_WAP;
		PayRequest payRequest = getPayRequestInfo(orderId, bankPayModel,
				channelNo, currentUser, request, model);
		// 为b2c
		payRequest.setB2C(true);
		payRequest.setSignNo(signNo);
		// 获取银联支付需要的请求信息.
		PayResult pay = null;
		try {
			pay = bankPayService.pay(payRequest);
		} catch (Exception e) {
			log.error("call pay failed", e.getMessage(), e);
			throw new BusinessException();
		} finally {
			if (pay == null) {
				throw new BusinessException(
						"bankPayService.pay(payRequest) return null,throw exception.");
			}
		}

		return pay;
	}

	/**
	 * 中行B2C移动在线支付
	 *
	 * @param orderId
	 * @param channelNo
	 * @param currentUser
	 * @param request
	 * @param response
	 * @param model
	 * @param act
	 * @return
	 */
	public String bocB2CMobileNetPay(Long orderId, Short channelNo,
									 User currentUser, HttpServletRequest request,
									 HttpServletResponse response, Model model, String act) {
		log.info("bocB2CMobileNetPay execute.... orderId = " + orderId);

		// 支付平台------15
		Short bankPayModel = BankPayModel.PAY_TYPE_BOC_B2C_MOBILE_NETPAY;
		// 获取支付请求信息
		PayRequest payRequest = getPayRequestInfo(orderId, bankPayModel,
				channelNo, currentUser, request, model);
		// 获取发送到支付宝的请求信息
		PayResult pay = bankPayService.pay(payRequest);
		if (pay == null) {
			throw new PayException("pay result is null--->orderId=" + orderId);
		}
		if (StringUtils.isEmpty(pay.getAction())) {
			throw new PayException("return url is null--->orderId=" + orderId);
		}
		String serializeBeanToJsonString = JsonUtil
				.serializeBeanToJsonString(pay);
		log.info("payInfo:    " + serializeBeanToJsonString);

		String redirectUrl = "";// 带参数的url
		String requestUrl = ""; // 没有参数的url
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BocMobileNetBody requestBody = (BocMobileNetBody) pay
					.getRequestBody();
			String signature = URLDecoder.decode(requestBody.getSignData(),
					"utf-8");
			requestBody.setSignData(signature);
			map = ParameterTool.convertRequestToMap(requestBody);
			requestUrl = pay.getAction();
			String parames = ParameterTool.mapToUrl(map);

			redirectUrl = ParameterTool.getRedirectUrl(requestUrl, parames);
			log.info("requestBodymap" + map);
		} catch (Exception e) {
			log.error("getRedirectUrl error" + e);
			throw new BusinessException("call getRedirectUrl error " + e);
		}
		log.info("redirectUrl is :" + redirectUrl);
		return redirectUrl;
	}

	/**
	 * 中行信用卡在线支付:快捷\分期 TODO
	 *
	 * @param orderId
	 * @param channelNo
	 * @param currentUser
	 * @param request
	 * @param response
	 * @param model
	 * @param act
	 * @return
	 */
	public PayResult ncpPay(Long orderId, Short channelNo, User currentUser,
							HttpServletRequest request, HttpServletResponse response,
							Model model, String bizType, String planId, String planNum) {
		log.info("boc ncp pay execute.... orderId = " + orderId);

		// 支付平台------16
		Short bankPayModel = null;
		if (BocNcpTxnSubType.PLAN.equals(bizType)) {
			bankPayModel = BankPayModel.PAY_TYPE_NCPFQ_PAY;
		} else {
			bankPayModel = BankPayModel.PAY_TYPE_NCPKJ_PAY;
		}
		// 获取支付请求信息
		PayRequest payRequest = getPayRequestInfo(orderId, bankPayModel,
				channelNo, currentUser, request, model);
		payRequest.setBizType(bizType);
		payRequest.setPlanId(planId);
		payRequest.setPlanNum(planNum);
		log.info("=====" + payRequest.toString() + "=====");
		// 获取发送到中行信用卡的请求信息
		PayResult pay = null;
		try {
			pay = bankPayService.pay(payRequest);
		} catch (Exception e) {
			log.info("=====调用支付接口失败====");
			e.printStackTrace();
		}
		if (pay == null) {
			throw new PayException("pay result is null--->orderId=" + orderId);
		}
		if (StringUtils.isEmpty(pay.getAction())) {
			throw new PayException("return url is null--->orderId=" + orderId);
		}

		return pay;
	}

	/**
	 * 微信WAP支付
	 *
	 * @param orderId
	 * @param channelNo
	 * @param currentUser
	 * @param request
	 * @param response
	 * @param model
	 * @param act
	 * @return
	 */
	public String weiXinWapPay(Long orderId, Short channelNo, User currentUser,
							   HttpServletRequest request, HttpServletResponse response,
							   Model model, String act) {
		log.info("weiXinWapPay execute.... orderId = " + orderId);

		// 支付平台------15
		Short bankPayModel = BankPayModel.PAY_TYPE_WEIXIN_WAP;
		// 获取支付请求信息
		PayRequest payRequest = getPayRequestInfo(orderId, bankPayModel,
				channelNo, currentUser, request, model);
		// 获取发送到支付宝的请求信息
		PayResult pay = bankPayService.pay(payRequest);
		if (pay == null) {
			throw new PayException("pay result is null--->orderId=" + orderId);
		}
		if (StringUtils.isEmpty(pay.getAction())) {
			throw new PayException("return url is null--->orderId=" + orderId);
		}
		String serializeBeanToJsonString = JsonUtil
				.serializeBeanToJsonString(pay);
		log.info("payInfo:    " + serializeBeanToJsonString);

		String redirectUrl = "";// 带参数的url
		String requestUrl = ""; // 没有参数的url
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, String> parasMap = pay.getRequestParams();
			String signature = URLDecoder.decode((String) parasMap.get("sign"),
					"utf-8");
			requestUrl = pay.getAction();
			String parames = ParameterTool.mapToUrl(map);

			redirectUrl = ParameterTool.getRedirectUrl(requestUrl, parames);
			log.info("requestBodymap" + map);
		} catch (Exception e) {
			log.error("getRedirectUrl error" + e);
			throw new BusinessException("call getRedirectUrl error " + e);
		}
		/*
		 * String redirectUrl =
		 * "weixin://wap/pay?appid%3Dwx2b029c08a6232582%26noncestr%3D3371f53d494c7e66168e2ed028932b21%26package%3DWAP%26prepayid%3Dwx201601241227578e2e5ec4bc0960725793%26timestamp%3D1453609677%26sign%3DC6791CE57B701B01B67EA8CF51415E6F"
		 * ; log.info("redirectUrl is :" + redirectUrl);
		 */
		return redirectUrl;
	}

	/**
	 * 中行信用卡支付回调
	 *
	 * @param allRequestParam
	 * @param currentUser
	 * @return
	 */
	public PayResult bocNcpPayCallBack(BocNcpResponseDto bocNcpResponseDto,
									   Short bankPayModel) {
		log.info("=== boc ncp CallBack ...");
		PayRequest payRequest = new PayRequest();
		payRequest.setBaseBodyDto(bocNcpResponseDto);
		payRequest.setBankPayModel(bankPayModel);
		log.info("中行信用卡异步响应信息---->" + payRequest.toString());
		PayResult payCallback = bankPayService.payCallback(payRequest);

		return payCallback;
	}

	/**
	 * 中行网银支付回调
	 *
	 * @param allRequestParam
	 * @param currentUser
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	public PayResult bocNetPayCallBack(Map<String, String> requestParams,
									   Short bankPayModel) {
		log.info("cal  boc net CallBack ...");
		PayRequest payRequest = new PayRequest();
		PayMessageOnlineBank payMessageOnlineBank = new PayMessageOnlineBank();
		paramConvert(requestParams, payMessageOnlineBank);
		payRequest.setBaseBodyDto(payMessageOnlineBank);
		payRequest.setBankPayModel(bankPayModel);
		log.info("中行网银 异步响应信息---->" + payRequest.toString());
		PayResult payCallback = bankPayService.payCallback(payRequest);

		return payCallback;
	}

	/**
	 * 支付对账
	 *
	 * @param orderId
	 * @param channelNo
	 * @param currentUser
	 * @param request
	 * @param response
	 * @param model
	 * @param act
	 * @return
	 */
	public CheckResult accountCheck(Date startDate, Date endDate,
									Short bankPayModel, Short bizType, String act,
									HttpServletRequest request, HttpServletResponse response,
									Model model) {

		// 支付平台------15
		// Short bankPayModel = BankPayModel.PAY_TYPE_BOC_B2C_MOBILE_NETPAY;
		// 获取支付对账请求信息
		CheckRequest checkRequest = getPayAccountRequestInfo(startDate,
				endDate, bankPayModel, bizType, request, model);
		// 获取发送到支付宝的请求信息
		CheckResult result = null;
		try {
			result = bankAccountCheckService.check(checkRequest);
		} catch (ApiException e) {
			log.error("pay account check exception -->" + e.getMessage(), e);
		}
		if (result == null) {
			throw new PayException("accountCheck result is null");
		}

		String serializeBeanToJsonString = JsonUtil
				.serializeBeanToJsonString(result);
		log.info("accountCheckInfo:    " + serializeBeanToJsonString);

		return result;
	}

	/**
	 * 支付对账
	 *
	 * @param allRequestParam
	 * @param currentUser
	 * @return
	 * @throws Exception
	 */
	public CheckResult bocCrossAccountCheckCallBack(
			Map<String, String> requestParams, Short bankPayModel)
			throws Exception {
		log.info("cal  bocCrossAccountCheckCallBack CallBack ...");
		CheckRequest checkRequest = new CheckRequest();
		checkRequest.setRequestParams(requestParams);
		checkRequest.setBankPayModel(bankPayModel);
		log.info("中行跨境 异步响应信息---->" + checkRequest.toString());
		CheckResult checkResult = bankAccountCheckService
				.checkCallback(checkRequest);

		return checkResult;
	}

	public QueryResult queryWeiXinResult(String payFlowNo) {
		QueryResult result = new QueryResult();
		try {
			result = checkPayMainService.checkPay(payFlowNo);
		} catch (Exception e) {
			log.error("查询微信支付异常,流水号-->" + payFlowNo, e);
		}
		return result;
	}

	/**
	 * 参数转换
	 *
	 * @param onlineBankInfo
	 * @param payMessageOnlineBank
	 */
	private void paramConvert(Map<String, String> requestParams,
							  PayMessageOnlineBank payMessageOnlineBank) {
		payMessageOnlineBank.setMerchantno(requestParams.get("merchantNo"));
		payMessageOnlineBank.setOrderid(requestParams.get("orderNo"));
		payMessageOnlineBank.setOrderseq(requestParams.get("orderSeq"));
		payMessageOnlineBank.setCardtyp(requestParams.get("cardTyp"));
		payMessageOnlineBank.setPaytime(DateUtil.strToDate(
				requestParams.get("payTime"), "yyyyMMddHHmmss"));
		payMessageOnlineBank.setOrderstatus(requestParams.get("orderStatus"));
		payMessageOnlineBank.setPayamount(new BigDecimal(requestParams
				.get("payAmount")));
		payMessageOnlineBank.setAcctno(requestParams.get("acctNo"));
		payMessageOnlineBank.setHoldername(requestParams.get("holderName"));
		payMessageOnlineBank.setIbknum(requestParams.get("ibknum"));
		payMessageOnlineBank.setOrderip(requestParams.get("orderIp"));
		payMessageOnlineBank.setOrderrefer(requestParams.get("orderRefer"));
		payMessageOnlineBank.setBanktranseq(requestParams.get("bankTranSeq"));
		payMessageOnlineBank.setReturnactflag(requestParams
				.get("returnActFlag"));
		payMessageOnlineBank.setPhonenum(requestParams.get("phoneNum"));
		payMessageOnlineBank.setSigndataback(requestParams.get("signData"));
	}

	@SuppressWarnings("unused")
	public boolean doAuth(HttpServletRequest request,
						  HttpServletResponse response, String currenturl)
			throws UnsupportedEncodingException, IOException, Exception {
		// 微信用户授权接口所需
		String code = request.getParameter("code");

		if (StringUtils.isBlank(code)) {
			log.info("用户请求的 code 没值,表示需要授权");
			// 没有调用授权接口，那么就调用它
			// 构造授权接口地址
			log.info("currenturl:" + currenturl);
			String redirectUrl = Oauth.getCode(
					URLEncoder.encode(currenturl, "utf-8"), true);
			// 直接跳转到目标url
			response.sendRedirect(redirectUrl);
			return false;
		} else {
			log.info("用户请求的code 有值{}", code);
			// 看是否调用了授权接口，调用了的话在这里取得用户信息
			JSONObject jsonObject = JSONObject.fromObject(Oauth.getToken(code));
			String openid = jsonObject.getString("openid");
			log.info("得到myOpenid is {}", openid);
			if (StringUtils.isNotBlank(openid)) {
				log.info("--->>拦截器 隐形注册...");
				response.sendRedirect(currenturl);
				return false;
			}
			return true;
		}
	}



	/**
	 * 银联商务下单
	 *
	 * @param createFalseOrder
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public PayResult chinaumsOrder(CreateFalseOrder createFalseOrder, HttpServletRequest request) throws Exception {

		BigDecimal price = createFalseOrder.getPrice();
		BigDecimal payPrice = createFalseOrder.getPayPrice();
		BigDecimal hqjPrice = createFalseOrder.getHqjPrice();
		Long supplierId = createFalseOrder.getSupplierId();
		String ip = null;
		String mixPayStatus = null;
		boolean isVoucher = false;
		// 取IP
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}

		// 判断 券+现金true or 全现金false
		if (hqjPrice.compareTo(BigDecimal.ZERO) > 0) {
			mixPayStatus = "1";
			isVoucher = true;
		}

		Supplier supplier = supplierManagerService.findSupplier(supplierId);        	// 收款商户
		if (supplier.getWithdrawStatus() != 1) {
			PayResult payResult = new PayResult(PayConstant.PayRecordStatus.UNKNOWN);
			payResult.setMsg("当前二维码暂不可使用");
			return payResult;
		}
		Supplier relationSupplier = userService.findSupplierByPopRelation(createFalseOrder.getUserId(), (byte) 2);	// 关系商户
		// 取商家折扣，并且向左移动一位小数点		例：7.0 转为 0.7
		BigDecimal supplierSalesDiscount = supplierDiscountService.findBySupplierId(supplierId).getSalesDiscount().movePointLeft(1);


		// 1 --------------------- 下单 ---------------------
        Order order = customerOrderService.createOfflineScanPayOrderWithRcodeId(payPrice, hqjPrice, supplierId, supplier.getName(),
                createFalseOrder.getUserId(), OrderStatusConstant.ORDER_PLATFORM_WAP, ip, supplier.getNameJC(), mixPayStatus, createFalseOrder.getQrCodeId());

		// 2 --------------------- 券+现金时扣券 ---------------------
		if (isVoucher) {
			psqwAccountRecordService.payOrderByVoucher(createFalseOrder.getUserId(), order.getId(), hqjPrice, 1, "券+现金支付时 --- 扣券");
		}


		// 3 --------------------  以下为计算各方收款金额  ----------------
		//   --------------------  获取MP，各级代理(不包括收款商户)  ----------------
		Map<String, Supplier> supplierMap = this.queryAllParentSupplier(supplierId);
		// 消费者有商户关系时，添加到里面，便于计算分润金额
		if (relationSupplier != null) {
			supplierMap.put(SupplierConstant.AGENT_CODE_M, relationSupplier);
		} else {
			supplierMap.put(SupplierConstant.AGENT_CODE_M, supplier);
		}


		// 4 --------------- 计算分润金额(仅为 5% 的部分)，当消费者没有商家关系时，返回的mShareProfit没有supplierId mid ----------------
		GroupProfitDto groupProfit = this.divideProfit(supplierMap, price);

		// 5 -------------------  计算商户与平台的现金收入 ----------------
		/*
		 * 设置收款商户金额，此时只有折扣价（订单总金额 * 折扣）
		 */
		User user = userService.findUserBySupplierId(supplierId);
		String mid = cashAccountService.findUmsByUserId(user.getUserId().intValue()).getUserAappid();
		BigDecimal mCash = price.multiply(supplierSalesDiscount).setScale(2, BigDecimal.ROUND_DOWN);
		groupProfit.setmCashProfit(supplierId, mid, SupplierConstant.AGENT_CODE_M, mCash, Boolean.TRUE);

		/*
		 * 设置平台资金沉淀，支付金额-各方分润-商户结算= 平台资金沉淀
		 */
		BigDecimal sysCash = this.sysCash(groupProfit, payPrice);
		groupProfit.setSysCashProfit(null, null, SupplierConstant.AGENT_CODE_SYS, sysCash, Boolean.TRUE);


		log.info("订单id: {} , 各方分润{}", order.getId(), groupProfit.toString());


		// 4 --------------------  银联商务下单  -----------------------------
		Map<String, String> map = new TreeMap<String, String>();
		map.put("msgType", createFalseOrder.getPayType());
		map.put("subOpenId", createFalseOrder.getOpenId());
		map.put("attachedData", hqjPrice.toString());
		map.put("shopName", supplier.getNameJC());
		map.put("supplierId", supplierId.toString());

		PayRequest payRequest = new PayRequest();
		payRequest.setBankPayModel(BankPayModel.PAY_TYPE_CHINAUMS_WAP);        // 银联商务wap支付
		payRequest.setB2C(true);                                        // b2c
		payRequest.setPayAmount(payPrice);                                // 实际支付
		payRequest.setIp(ip);                                            // IP
		payRequest.setOrderId(order.getId());                            // 订单ID
		payRequest.setRetailerId(createFalseOrder.getUserId());                // 消费者ID
		payRequest.setRequestParams(map);                                // 支付参数
		payRequest.setChannelNo(PayChannel.WAY_WAP);                    // 支付渠道
		payRequest.setOrderDate(new Date());                            // 支付时间
		payRequest.setGroupProfit(groupProfit);							// supplierId mid totalAmount
		payRequest.setPayModel(PayConstant.PaymentType.PAYMENT_TYPE_CODE_ALL);

		// 银联商务下单
		return bankPayService.pay(payRequest);
	}




	/**
	 * 计算平台应得金额(资金沉淀)
	 * @param groupProfit
	 * @param payPrice	支付金额-各方分润-商户结算
	 * @return
	 */
	public BigDecimal sysCash(GroupProfitDto groupProfit, final BigDecimal payPrice) {
		BigDecimal pay = payPrice;	// 支付金额

		for (GroupProfitDto.Organization organization : groupProfit.toCollection()){

			if (organization == null) {
				continue;
			}

			pay = pay.subtract(organization.getTotalAmount());
		}

		return pay;
	}


	/**
	 * 对各级代理及商户分润
	 *
	 * @param supplierMap
	 * @param price
	 * @return
	 */
	public GroupProfitDto divideProfit(Map<String, Supplier> supplierMap, BigDecimal price) throws Exception {
		GroupProfitDto subOrders = new GroupProfitDto();

		for (Supplier supplier : supplierMap.values()) {
			if (supplier == null) {
				continue;
			}

			String type = supplier.getType().toString();	// agentCode 例：1601 商户为12或1660
			Long supplierId = supplier.getSupplierId();

			// 商户type目前为12  !!!!!!!!!!!!
			if ("12".equals(type)){
				type = SupplierConstant.AGENT_CODE_M;
			}

			SupplierAgentType supplierAgentType = supplierAgentTypeService.findByCode(Integer.valueOf(type)); //代理类型
			// 判断分润比例是否为0，为0不计算
			if (supplierAgentType == null || supplierAgentType.getStatus() == 0 || supplierAgentType.getShareBenefit().compareTo(BigDecimal.ZERO) == 0) {
				continue;
			}
			// supplier中userid有可能为null，是因为审核商户通过后才会有user信息
			User user = userService.findUserBySupplierId(supplierId);
			// 查询绑定的银联商务账户 ------- 以后要改成支持多账户
            UmsCashAccount umsCashAccount = cashAccountService.findUmsByUserId(user.getUserId().intValue());
			BigDecimal profit = price.multiply(supplierAgentType.getShareBenefit().movePointLeft(2));		// 1.5 to 0.015
			profit = profit.setScale(2, BigDecimal.ROUND_DOWN);

			boolean normal = true;
			// 判断冻结与否
			if ("5".equals(supplier.getStatus())) {
				normal = false;
			}

			// 判断入网，没有mid说明没入网
            String mid = null;
            if (umsCashAccount != null && StringUtils.isNotEmpty(umsCashAccount.getUserAappid())) {
                mid = umsCashAccount.getUserAappid();
            } else {
                normal = false;
            }

			/*
			 * 设置各方分润
			 */
			if (SupplierConstant.AGENT_CODE_M.equals(type) | "12".equals(type)){
				subOrders.setmShareProfit(supplierId, mid, type, profit, normal);
			}else if (SupplierConstant.AGENT_CODE_MP.equals(type)) {
				subOrders.setMpProfit(supplierId, mid, type, profit, normal);
			} else if (SupplierConstant.AGENT_CODE_COUNTY.equals(type)) {
				subOrders.setCountyProfit(supplierId, mid, type, profit, normal);
			} else if (SupplierConstant.AGENT_CODE_CITY.equals(type)) {
				subOrders.setCityProfit(supplierId, mid, type, profit, normal);
			} else if (SupplierConstant.AGENT_CODE_PROVINCE.equals(type)) {
				subOrders.setProvinceProfit(supplierId, mid, type, profit, normal);
			} else if (SupplierConstant.AGENT_CODE_COUNTRY.equals(type)) {
				subOrders.setCountryProfit(supplierId, mid, type, profit, normal);
			} else if (SupplierConstant.AGENT_CODE_SYS.equals(type)) {
				subOrders.setSysShareProfit(supplierId, mid, type, profit, normal);
			} else {
				throw new Exception("商户及代理信息异常");
			}
		}

/*
		// 没有商户时，说明消费者没有与商家的关系。此时只计算分润金额
		if (subOrders.getMShareProfit() == null) {
			// 根据1660去查SupplierAgentType
			SupplierAgentType supplierAgentType = supplierAgentTypeService.findByCode(Integer.valueOf(SupplierConstant.AGENT_CODE_M));
			if (supplierAgentType != null && supplierAgentType.getStatus() != 0 && supplierAgentType.getShareBenefit().compareTo(BigDecimal.ZERO) != 0) {

				BigDecimal mShare = price.multiply(supplierAgentType.getShareBenefit().movePointLeft(2));
				mShare = mShare.setScale(2, BigDecimal.ROUND_DOWN);

				subOrders.setmShareProfit(null, null, SupplierConstant.AGENT_CODE_M, mShare, false);
			}
		}
*/

		return subOrders;
	}

	/**
	 * 查询supplierId的所有上级
	 *
	 * @param supplierId
	 * @return
	 */
	public Map<String, Supplier> queryAllParentSupplier(Long supplierId) {
		Map<String, Supplier> map = new HashMap<String, Supplier>();
		Supplier supplier;

		do {
			// 查上级代理商
			supplier = supplierManagerService.findParentSupplier(supplierId);

			if (supplier == null) {
				break;
			}
			supplierId = supplier.getSupplierId();

			map.put(supplier.getType().toString(), supplier);

		} while (supplier.getParentId() != 0);

		return map;
	}

	/**
	 * 调用分账接口，只有线下订单才分账
	 * @param order
	 * @param payRecord
	 * @throws Exception
	 */
	public int separateAccounts(OrderDTO order,PayRecord payRecord,String payTyime) {
		String payWaterCode = payRecord.getPayWaterCode();
		BigDecimal totalAmount = payRecord.getOrderAmount();

		Long orderId = order.getId();

		GroupProfitDto groupProfitDto = this.getProfit(payWaterCode);
		if (groupProfitDto == null) {
			throw new PayException("银联商务 --- callBack --- 未找到分账记录");
		}

		log.info("银联订单分账 payWaterCode: " + payWaterCode);
		// 获取使用的代金券数量
		BigDecimal hqjPrice = order.getHqjPrice();
		Long supplierId = groupProfitDto.getMCashProfit().getSupplierId();
		Long customerUserId = payRecord.getMerchantNo();
		Date orderTime = DateUtil.stringToDate(payTyime);


		// 结算，分润
		int re = psqwAccountRecordService.offlineConsumption(supplierId, customerUserId, orderId, totalAmount,
				totalAmount, hqjPrice, groupProfitDto, RecordBkStatus.RECORD_BK_STATUS_EN, orderTime);
		return re;
	}




	/**
	 * 获取分账记录
	 *
	 * @param payWaterCode
	 * @return
	 */
	public GroupProfitDto getProfit(String payWaterCode){
		GroupProfitDto groupProfitDto = null;
		String profitJson = JedisManager.getString(RedisKeyPrefix.DIVISIONACCOUNT_SUBORDERS + payWaterCode);

		if (StringUtils.isNotEmpty(profitJson)) {
			groupProfitDto = JSON.parseObject(profitJson, GroupProfitDto.class);

			// 将单位元转为分,并且设置精度
			for (ChinaUMSWapRequest.SubOrders su : groupProfitDto.toCollection()) {
				if (su == null) {
					continue;
				}
				su.setTotalAmount(su.getTotalAmount().movePointLeft(2));
			}
			return groupProfitDto;
		}

		BankLog bankLog = new BankLog();
		bankLog.setPayFlowNo(payWaterCode);
		bankLog.setContentType("request");
		bankLog.setOperateType(PayConstant.BankLogType.PAY);
		List<BankLog> bankLogs = payLogService.query(bankLog);

		if (null != bankLogs) {
			String content = bankLogs.get(0).getContent();

			if (StringUtils.isBlank(content)) {
				return null;
			}

			net.sf.json.JSONObject request = JSON.parseObject(content, net.sf.json.JSONObject.class);

			Object groupProfit = request.get("groupProfit");
			if (groupProfit == null) {
				return null;
			}
			groupProfitDto = JSON.parseObject(groupProfit.toString(), GroupProfitDto.class);
		}

		// 将单位元转为分,并且设置精度
		for (ChinaUMSWapRequest.SubOrders su : groupProfitDto.toCollection()) {
			if (su == null) {
				continue;
			}
			su.setTotalAmount(su.getTotalAmount().movePointLeft(2));
		}

		return groupProfitDto;
	}


}
