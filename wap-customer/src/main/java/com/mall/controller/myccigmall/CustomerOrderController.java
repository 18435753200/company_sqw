package com.mall.controller.myccigmall;

import com.alibaba.fastjson.JSON;
import com.mall.annotation.AuthPassport;
import com.mall.commons.utils.DateUtil;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.dto.HomeNumRecordDto;
import com.mall.customer.dto.OrderItemReturnTicketDto;
import com.mall.customer.dto.UserTradeDto;
import com.mall.customer.dto.activity.SupplierNumRecordDto;
import com.mall.customer.model.*;
import com.mall.customer.order.api.kdniao.KdniaoCustomerOrderService;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.common.OrderStatusConstant;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderData;
import com.mall.customer.order.dto.OrderItemData;
import com.mall.customer.order.po.KdniaoLogisticsTrack;
import com.mall.customer.order.po.Order;
import com.mall.customer.order.po.OrderItem;
import com.mall.customer.order.po.OrderLogisticsMsg;
import com.mall.customer.order.utils.PKUtils;
import com.mall.customer.order.wms.bsht.htky.response.TraceLogsListType.TraceLogsType;
import com.mall.customer.order.wms.bsht.htky.response.TraceLogsListType.TracesType;
import com.mall.customer.order.wms.dto.Result;
import com.mall.customer.order.wms.dto.SfRouteDto;
import com.mall.customer.service.*;
import com.mall.exception.BadRequestException;
import com.mall.mybatis.utility.PageBean;
import com.mall.pay.common.StringUtils;
import com.mall.service.CusOrderService;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.utils.Constants;
import com.mall.utils.JsonUtil;
import com.mall.utils.Oauth;
import com.mall.vo.ResultVo;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * 订单中心
 * 
 * @author ccigQA01
 * 
 */
@Controller
@RequestMapping(value = RequestContant.CUS_ORDER)
public class CustomerOrderController extends BaseController {
	private static final Logger log = LoggerFactory
			.getLogger(CustomerOrderController.class);
	@Autowired
	private CusOrderService cusOrderService;
	// 支付先关的服务
	@Autowired
	private CustomerOrderService customerOrderService;
	@Autowired
	private SqwAccountRecordService sqwAccountRecordService;
	@Autowired
	private CashierService cashierService;

	@Autowired
	private KdniaoCustomerOrderService kdniaoCustomerOrderService;

	@Autowired
	private UserService userService;
	@Autowired
	private DividendService dividendService;
	@Autowired
	private HomeNumRecordService homeNumRecordService;
	@Autowired
	private SupplierManagerService supplierManagerService;
	@Autowired
	private SupplierNumRecordService supplierNumRecordService;
	/**
	 * 跳转到红旗宝页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_MY_RECIVE_MONEY)
	public String toMyReciveMoney(Model model, HttpServletRequest request,
			HttpServletResponse response) throws TimeoutException,
			InterruptedException, MemcachedException {
		log.info("toMyOrder execute....");
		try {

			CashierSettings cashierSettings = new CashierSettings();
			// 红旗券倍数
			cashierSettings.setDividendType(1);
			CashierSettings cashierSettingsFromDb = cashierService
					.findCashierSettsingsByDividendType(cashierSettings);
			cashierSettingsFromDb.setCounterFee(cashierSettingsFromDb
					.getCounterFee().divide(BigDecimal.valueOf(100)));
			model.addAttribute("cashierSettings", cashierSettingsFromDb);
			// if(cashierSettingsFromDb == null){
			// response.getResult().setResult("0");
			// response.getResult().setMessage("请先设置红旗宝全局倍数!");
			// return response;
			// }
			// Map<String,Object> map =new HashMap<String,Object>();
			// map.put("payMultiple", cashierSettingsFromDb.getRealpayMultipe()
			// == null ? 0 : cashierSettingsFromDb.getRealpayMultipe());
			// map.put("factorageMultiple",
			// cashierSettingsFromDb.getCounterFee() == null ? 0
			// :cashierSettingsFromDb.getCounterFee().divide(BigDecimal.valueOf(100)));
			// response.setData(map);
			// response.getResult().setMessage("获取红旗宝金额相应倍数成功.");
			// response.getResult().setResult("1");
		} catch (Exception e) {
			log.info(" 接口异常." + e.getMessage(), e);
		}
		return ResponseContant.CUS_MY_RECIVE_MONEY;
	}
	
	/**
	 * 跳转到生鲜宝页面
	 * 
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_MY_SHENGXIAN)
	public String toMyShengXian(Model model, HttpServletRequest request,
			HttpServletResponse response) throws TimeoutException,
			InterruptedException, MemcachedException {
		log.info("toMyOrder execute....");
		try {

			CashierSettings cashierSettings = new CashierSettings();
			// 红旗券倍数
			cashierSettings.setDividendType(2);
			CashierSettings cashierSettingsFromDb = cashierService
					.findCashierSettsingsByDividendType(cashierSettings);
			cashierSettingsFromDb.setCounterFee(cashierSettingsFromDb
					.getCounterFee().divide(BigDecimal.valueOf(100)));
			model.addAttribute("cashierSettings", cashierSettingsFromDb);
		} catch (Exception e) {
			log.info(" 接口异常." + e.getMessage(), e);
		}
		return ResponseContant.CUS_MY_SHENGXIAN;
	}

	/**
	 * 校验红旗宝参数
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_RECIVE_MONEY_SYS_PAY_VALIDATE)
	@ResponseBody
	public String reciveMoneySysPayValidate(Model model,
			HttpServletRequest request, HttpServletResponse response,
			String shopUserId, String shopRealName, String price)
			throws TimeoutException, InterruptedException, MemcachedException {

		log.info("调用红旗宝支付信息校验！shopUserId:" + shopUserId + " shopRealName:"
				+ shopRealName + " price:" + price);

		if (StringUtils.isEmpty(shopUserId)) {
			return "请输入实体店铺ID";
		}

		if (StringUtils.isEmpty(shopRealName)) {
			return "请输入真实姓名";
		}

		if (StringUtils.isEmpty(price)) {
			return "请输入结算数";
		}
		User currentUser = getCurrentUser(request);
		if (shopUserId.equals(String.valueOf(currentUser.getUserId()))) {
			return "您无法购买自己商品，请您重新输入！";
		}
		try {
			CashierRecord cashierRecord = new CashierRecord();
			cashierRecord.setUserId(Long.parseLong(shopUserId));
			cashierRecord.setType(1);
			List<CashierRecord> cashierRecords = (List<CashierRecord>) cashierService
					.selectCashierRecordByUserid(cashierRecord);
			CashierRecord cashierRecordFromDb = null;
			if (cashierRecords != null) {
				cashierRecordFromDb = cashierRecords.get(0);
			}
			CashierSettings cashierSettings = new CashierSettings();
			// 红旗券倍数
			cashierSettings.setDividendType(1);
			User shopUser = userService
					.findUserById(Long.parseLong(shopUserId));
			if (cashierRecordFromDb == null) {
				// response.getResult().setResult("0");
				// response.getResult().setMessage("商户未购买红旗宝!");
				return "商户未购买红旗宝!";
			}
			if (cashierRecordFromDb.getOrderState() != 1) {
				// response.getResult().setResult("0");
				// response.getResult().setMessage("商户购买的红旗宝已过期!");
				return "商户购买的红旗宝已过期!";
			}
			if (!shopRealName.equals(shopUser.getRealName())) {
				// response.getResult().setResult("0");
				// response.getResult().setMessage("实体店铺ID与真实姓名不一致");
				return "实体店铺ID与真实姓名不一致";
			}
			CashierSettings cashierSettingsQuery = new CashierSettings();
			// 红旗券倍数
			cashierSettingsQuery.setDividendType(1);
			CashierSettings cashierSettingsFromDb = cashierService
					.findCashierSettsingsByDividendType(cashierSettingsQuery);
			BigDecimal payPrice = new BigDecimal(price)
					.multiply(cashierSettingsFromDb.getRealpayMultipe())
					.add((new BigDecimal(price)).multiply(cashierSettingsFromDb
							.getCounterFee().multiply(BigDecimal.valueOf(0.01))))
					.setScale(0, BigDecimal.ROUND_HALF_UP);

			log.info("========调用红旗券金额校验==========userId:"
					+ currentUser.getUserId() + " payPrice:" + payPrice);
			int checkPayOrderByHqq = sqwAccountRecordService
					.checkPayOrderByHqq(currentUser.getUserId(), payPrice);
			if (checkPayOrderByHqq != 0) {
				return "当前账号红旗券不足，无法转出!";
			}
			UserTradeDto userTrade = userService.getTrade(currentUser
					.getUserId());
			log.info("userTrade.getRetCode():" + userTrade.getRetCode());
			if (!"1".equals(userTrade.getRetCode())) {
				return userTrade.getRetInfo();
			}
		} catch (Exception e) {
			log.error("系统异常:" + e);
			return "系统异常";
		}
		return "1";
	}

	/**
	 * 校验生鲜宝参数
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_SHENGXIAN_VALIDATE)
	@ResponseBody
	public String shengXianValidate(Model model, HttpServletRequest request,
			HttpServletResponse response, String shopUserId,
			String shopRealName, String price) throws TimeoutException,
			InterruptedException, MemcachedException {

		log.info("调用生鲜宝支付信息校验！shopUserId:" + shopUserId + " shopRealName:"
				+ shopRealName + " price:" + price);

		if (StringUtils.isEmpty(shopUserId)) {
			return "请输入实体店铺ID";
		}

		if (StringUtils.isEmpty(shopRealName)) {
			return "请输入真实姓名";
		}

		if (StringUtils.isEmpty(price)) {
			return "请输入结算数";
		}
		User currentUser = getCurrentUser(request);
		if (shopUserId.equals(String.valueOf(currentUser.getUserId()))) {
			return "您无法购买自己商品，请您重新输入！";
		}
		try {
			CashierRecord cashierRecord = new CashierRecord();
			cashierRecord.setUserId(Long.parseLong(shopUserId));
			cashierRecord.setType(2);
			List<CashierRecord> cashierRecords = (List<CashierRecord>) cashierService
					.selectCashierRecordByUserid(cashierRecord);
			CashierRecord cashierRecordFromDb = null;
			if (cashierRecords != null) {
				cashierRecordFromDb = cashierRecords.get(0);
			}
			CashierSettings cashierSettings = new CashierSettings();
			// 红旗券倍数
			cashierSettings.setDividendType(2);
			User shopUser = userService
					.findUserById(Long.parseLong(shopUserId));
			if (cashierRecordFromDb == null) {
				// response.getResult().setResult("0");
				// response.getResult().setMessage("商户未购买红旗宝!");
				return "商户未购买生鲜宝!";
			}
			if (cashierRecordFromDb.getOrderState() != 1) {
				// response.getResult().setResult("0");
				// response.getResult().setMessage("商户购买的红旗宝已过期!");
				return "商户购买的生鲜宝已过期!";
			}
			if (!shopRealName.equals(shopUser.getRealName())) {
				// response.getResult().setResult("0");
				// response.getResult().setMessage("实体店铺ID与真实姓名不一致");
				return "实体店铺ID与真实姓名不一致";
			}
			CashierSettings cashierSettingsQuery = new CashierSettings();
			// 红旗券倍数
			cashierSettingsQuery.setDividendType(2);
			CashierSettings cashierSettingsFromDb = cashierService
					.findCashierSettsingsByDividendType(cashierSettingsQuery);
			BigDecimal payPrice = new BigDecimal(price)
					.multiply(cashierSettingsFromDb.getRealpayMultipe())
					.add((new BigDecimal(price)).multiply(cashierSettingsFromDb
							.getCounterFee().multiply(BigDecimal.valueOf(0.01))))
					.setScale(0, BigDecimal.ROUND_HALF_UP);

			log.info("========调用红旗券金额校验==========userId:"
					+ currentUser.getUserId() + " payPrice:" + payPrice);
			int checkPayOrderByHqq = sqwAccountRecordService
					.checkPayOrderByHqq(currentUser.getUserId(), payPrice);
			if (checkPayOrderByHqq != 0) {
				return "当前账号红旗券不足，无法转出!";
			}
			UserTradeDto userTrade = userService.getTrade(currentUser
					.getUserId());
			log.info("userTrade.getRetCode():" + userTrade.getRetCode());
			if (!"1".equals(userTrade.getRetCode())) {
				return userTrade.getRetInfo();
			}
		} catch (Exception e) {
			log.error("系统异常:" + e);
			return "系统异常";
		}
		return "1";
	}

	/**
	 * 红旗宝支付
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param shopUserId
	 * @param shopRealName
	 * @param price
	 * @param pwd
	 * @param payPrice
	 * @param factoragePrice
	 * @param incomePrice
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_RECIVE_MONEY_SYS_PAY)
	@ResponseBody
	public String reciveMoneySysPay(Model model, HttpServletRequest request,
			HttpServletResponse response, String shopUserId,
			String shopRealName, String price, String pwd) {
		log.info("调用红旗宝支付信息！shopUserId:" + shopUserId + " shopRealName:"
				+ shopRealName + " price:" + price + " pwd:" + pwd);
		ResultVo resultVo = new ResultVo();

		if (StringUtils.isEmpty(shopUserId)) {
			resultVo.setRetMsg("请输入实体店铺ID");
			resultVo.setRetCode("10");
			return JsonUtil.serializeBeanToJsonString(resultVo);
		}

		if (StringUtils.isEmpty(shopRealName)) {
			resultVo.setRetMsg("请输入真实姓名");
			resultVo.setRetCode("10");
			return JsonUtil.serializeBeanToJsonString(resultVo);
		}

		if (StringUtils.isEmpty(price)) {
			resultVo.setRetMsg("请输入结算数");
			resultVo.setRetCode("10");
			return JsonUtil.serializeBeanToJsonString(resultVo);
		}

		if (StringUtils.isEmpty(pwd)) {
			resultVo.setRetMsg("请输入支付密码");
			resultVo.setRetCode("10");
			return JsonUtil.serializeBeanToJsonString(resultVo);
		}

		User currentUser = getCurrentUser(request);
		Long orderId = PKUtils.getLongOrderPrimaryKey();
		try {
			CashierRecord cashierRecord = new CashierRecord();
			cashierRecord.setUserId(Long.parseLong(shopUserId));
			cashierRecord.setType(1);
			List<CashierRecord> cashierRecords = (List<CashierRecord>) cashierService
					.selectCashierRecordByUserid(cashierRecord);
			CashierRecord cashierRecordFromDb = null;
			if (cashierRecords != null) {
				cashierRecordFromDb = cashierRecords.get(0);
			}
			UserTrade userTrade = new UserTrade();
			userTrade.setPwd(pwd);
			userTrade.setUserId(currentUser.getUserId());
			UserTradeDto userTradeDto = userService.checkTradePwd(userTrade);
			if (!"1".equals(userTradeDto.getRetCode())) {
				log.info("调用 checkTradePwd 返回信息"
						+ JSON.toJSONString(userTradeDto));
				resultVo.setRetMsg(userTradeDto.getRetInfo());
				resultVo.setRetCode(userTradeDto.getRetCode());
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
			User shopUser = userService
					.findUserById(Long.parseLong(shopUserId));
			if (cashierRecordFromDb == null) {
				resultVo.setRetMsg("商户未购买红旗宝!");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
			if (cashierRecordFromDb.getOrderState() != 1) {
				resultVo.setRetMsg("商户购买的红旗宝已过期!");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
			if (!shopRealName.equals(shopUser.getRealName())) {
				resultVo.setRetMsg("实体店铺ID与真实姓名不一致");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}

			CashierSettings cashierSettings = new CashierSettings();
			// 红旗券倍数
			cashierSettings.setDividendType(1);
			CashierSettings cashierSettingsFromDb = cashierService
					.findCashierSettsingsByDividendType(cashierSettings);
			if (cashierSettingsFromDb == null
					|| cashierSettingsFromDb.getGiftFhed() == null
					|| cashierSettingsFromDb.getGiftHqj() == null) {
				resultVo.setRetMsg("请先设置红旗宝全局倍数和返利倍数!");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
			BigDecimal payPrice = new BigDecimal(price)
					.multiply(cashierSettingsFromDb.getRealpayMultipe())
					.add(new BigDecimal(price).multiply(cashierSettingsFromDb
							.getCounterFee().multiply(BigDecimal.valueOf(0.01))))
					.setScale(0, BigDecimal.ROUND_HALF_UP);
			BigDecimal factoragePrice = new BigDecimal(price).multiply(
					cashierSettingsFromDb.getCounterFee().multiply(
							BigDecimal.valueOf(0.01))).setScale(0,
					BigDecimal.ROUND_HALF_UP);
			BigDecimal incomePrice = new BigDecimal(price).setScale(0,
					BigDecimal.ROUND_HALF_UP).add(factoragePrice);
			// 生成预订单
			OrderData orderData = new OrderData();

			orderData.setOrderId(orderId);
			orderData.setTotalQty(1);
			orderData.setOrderPrice(new BigDecimal(price).setScale(0,
					BigDecimal.ROUND_HALF_UP));
			orderData.setPrice(payPrice);
			orderData.setRealTransferprice(factoragePrice);
			orderData.setUserId(currentUser.getUserId());
			orderData.setMessage("红旗宝支付订单");
			orderData.setPaidPrice(payPrice);
			orderData.setPayWay("3");

			orderData.setCreateTime(new Date());
			orderData.setReceiveName(shopUser.getRealName());
			orderData
					.setCreateBy(currentUser.getRealName() == null ? currentUser
							.getNickName() : currentUser.getRealName());
			orderData.setLastEditTime(new Date());
			orderData
					.setLastEditBy(currentUser.getRealName() == null ? currentUser
							.getNickName() : currentUser.getRealName());
			orderData.setSaleTarget("C");
			orderData.setOrderFhed(payPrice
					.multiply(cashierSettingsFromDb.getGiftFhed())
					.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
			orderData.setOrderHqj(payPrice
					.multiply(cashierSettingsFromDb.getGiftHqj())
					.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());

			orderData.setShopIncome(incomePrice);
			orderData.setSupplyType("51");
			orderData.setMerchantid("1436");
			orderData.setCompanyQy("1");
			orderData.setReceiveAddress("红旗宝支付");
			orderData.setReceiveProvinceId(18L);
			orderData.setReceiveCityId(424L);
			orderData.setReceiveAreaId(2973L);
			orderData.setReceivePhone("11111111111");
			orderData.setDiscountPrice(BigDecimal.valueOf(0));
			List<OrderItemData> orderItemDatas = new ArrayList<OrderItemData>();

			OrderItemData orderItemData = new OrderItemData();

			orderItemData.setSkuId(147487811168071231L);
			orderItemData.setPid(5406517142632135L);
			orderItemData.setpName("红旗宝交易");
			orderItemData.setSkuNameCn("红旗宝交易");
			orderItemData.setSkuNameEn("shopTrade");
			orderItemData.setSkuQty(1);
			orderItemData.setUnit("笔");
			orderItemData.setPrice(payPrice);
			orderItemData.setSubtotalPirce(payPrice.multiply(BigDecimal
					.valueOf(orderItemData.getSkuQty())));
			orderItemData.setCreateTime(new Date());
			orderItemData
					.setCreateBy(currentUser.getRealName() == null ? currentUser
							.getNickName() : currentUser.getRealName());
			orderItemData.setLastEditTime(new Date());
			orderItemData
					.setLastEditBy(currentUser.getRealName() == null ? currentUser
							.getNickName() : currentUser.getRealName());
			orderItemData.setFhed(payPrice
					.multiply(cashierSettingsFromDb.getGiftFhed())
					.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
			orderItemData.setHqj(payPrice
					.multiply(cashierSettingsFromDb.getGiftHqj())
					.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
			orderItemDatas.add(orderItemData);
			orderData.setOrderItemDatas(orderItemDatas);
			customerOrderService.postOrderData(orderData);

			// 调用转账接口
			int transferStatus = 3;

			try {
				transferStatus = sqwAccountRecordService.transferAccountOfPOS(
						currentUser.getUserId(), payPrice,
						shopUser.getUserId(), incomePrice,
						String.valueOf(orderId),
						"红旗宝结算:" + currentUser.getUserId() + "扣除红旗券"
								+ incomePrice + "," + shopUser.getUserId()
								+ "收入红旗券" + incomePrice, 1);

			} catch (Exception e) {
				Order order = new Order();
				order.setId(orderId);
				order.setOrderPlatform((short) 3);
				order.setVersion(OrderStatusConstant.INTEGER_INIT);
				order.setStatus(OrderStatusConstant.ORDER_SYS_CANCLE);
				customerOrderService.updateOrder(order);
				log.error("支付失败:" + e);
				resultVo.setRetMsg("支付失败.");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}

			if (transferStatus == 1) {
				Order order = new Order();
				order.setId(orderId);
				order.setOrderPlatform((short) 3);
				order.setVersion(OrderStatusConstant.INTEGER_INIT);
				order.setStatus(OrderStatusConstant.ORDER_SYS_CANCLE);
				customerOrderService.updateOrder(order);

				resultVo.setRetMsg("购买人或者实体店账户不存在.");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			} else if (transferStatus == 2) {
				Order order = new Order();
				order.setId(orderId);
				order.setOrderPlatform((short) 3);
				order.setVersion(OrderStatusConstant.INTEGER_INIT);
				order.setStatus(OrderStatusConstant.ORDER_SYS_CANCLE);
				customerOrderService.updateOrder(order);

				resultVo.setRetMsg("账户余额不足.");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
			// 转账成功更新订单状态
			else if (transferStatus == 0) {
				Order order = new Order();
				order.setId(orderId);
				order.setOrderPlatform((short) 3);
				order.setVersion(OrderStatusConstant.INTEGER_INIT);
				order.setStatus(OrderStatusConstant.ORDER_CUSTOMER_RECEIPT);
				customerOrderService.updateOrder(order);

				List<OrderItemReturnTicketDto> OrderItemReturnTicketDtoList = new ArrayList<OrderItemReturnTicketDto>();
				User user = userService.findUserById(currentUser.getUserId());
				Long firstUserId = null;
				Long secondUserId = null;
				if (!StringUtils.isEmpty(user.getTjMobile())) {
					User firstTjUser = userService.findUserByMobile(user
							.getTjMobile());
					log.info("=====firstTjUser =  "
							+ JSON.toJSONString(firstTjUser));
					if (firstTjUser != null) {
						firstUserId = firstTjUser.getUserId();
						log.info("=====firstUserId =  " + firstUserId);
						if (!StringUtils.isEmpty(firstTjUser.getTjMobile())) {
							User secondTjUser = userService
									.findUserByMobile(firstTjUser.getTjMobile());
							log.info("=====secondTjUser =  "
									+ JSON.toJSONString(secondTjUser));
							if (secondTjUser != null)
								secondUserId = secondTjUser.getUserId();
						}
					}
				}
				OrderItemReturnTicketDto orderItemReturnTicketDto = new OrderItemReturnTicketDto();
				orderItemReturnTicketDto.setpNum(orderItemData.getSkuQty());
				orderItemReturnTicketDto.setProductSkuId(orderItemData
						.getSkuId());
				orderItemReturnTicketDto.setProductRegin(orderData
						.getCompanyQy());
				orderItemReturnTicketDto.setRefObjType(1);
				orderItemReturnTicketDto.setRefObjId(orderId);
				orderItemReturnTicketDto.setPrice_hqq(orderItemData.getPrice());
				orderItemReturnTicketDto.setReturn_fhed(BigDecimal
						.valueOf(orderItemData.getFhed()));
				orderItemReturnTicketDto.setReturn_hqq(BigDecimal
						.valueOf(orderItemData.getHqj()));
				OrderItemReturnTicketDtoList.add(orderItemReturnTicketDto);
				// 调用积分返利服务
				sqwAccountRecordService.recordAccountByUserbuy(
						currentUser.getUserId(), OrderItemReturnTicketDtoList,
						firstUserId, secondUserId);
			} else {
				Order order = new Order();
				order.setId(orderId);
				order.setOrderPlatform((short) 3);
				order.setVersion(OrderStatusConstant.INTEGER_INIT);
				order.setStatus(OrderStatusConstant.ORDER_SYS_CANCLE);
				customerOrderService.updateOrder(order);

				resultVo.setRetMsg("红旗宝支付失败,未知异常");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
		} catch (Exception e) {
			log.error("系统异常:" + e);
			resultVo.setRetMsg("接口异常.");
			resultVo.setRetCode("10");
			return JsonUtil.serializeBeanToJsonString(resultVo);
		}
		resultVo.setRetMsg("红旗宝成功支付");
		resultVo.setRetCode("1");
		return JsonUtil.serializeBeanToJsonString(resultVo);
	}

	/**
	 * 生鲜宝支付
	 * 
	 * 
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_SHENGXIAN_SYS_PAY)
	@ResponseBody
	public String shengXianSysPay(Model model, HttpServletRequest request,
			HttpServletResponse response, String shopUserId,
			String shopRealName, String price, String pwd) {
		log.info("调用生鲜宝支付信息！shopUserId:" + shopUserId + " shopRealName:"
				+ shopRealName + " price:" + price + " pwd:" + pwd);
		ResultVo resultVo = new ResultVo();

		if (StringUtils.isEmpty(shopUserId)) {
			resultVo.setRetMsg("请输入实体店铺ID");
			resultVo.setRetCode("10");
			return JsonUtil.serializeBeanToJsonString(resultVo);
		}

		if (StringUtils.isEmpty(shopRealName)) {
			resultVo.setRetMsg("请输入真实姓名");
			resultVo.setRetCode("10");
			return JsonUtil.serializeBeanToJsonString(resultVo);
		}

		if (StringUtils.isEmpty(price)) {
			resultVo.setRetMsg("请输入结算数");
			resultVo.setRetCode("10");
			return JsonUtil.serializeBeanToJsonString(resultVo);
		}

		if (StringUtils.isEmpty(pwd)) {
			resultVo.setRetMsg("请输入支付密码");
			resultVo.setRetCode("10");
			return JsonUtil.serializeBeanToJsonString(resultVo);
		}

		User currentUser = getCurrentUser(request);
		Long orderId = PKUtils.getLongOrderPrimaryKey();
		try {
			CashierRecord cashierRecord = new CashierRecord();
			cashierRecord.setUserId(Long.parseLong(shopUserId));
			cashierRecord.setType(2);
			List<CashierRecord> cashierRecords = (List<CashierRecord>) cashierService
					.selectCashierRecordByUserid(cashierRecord);
			CashierRecord cashierRecordFromDb = null;
			if (cashierRecords != null) {
				cashierRecordFromDb = cashierRecords.get(0);
			}
			UserTrade userTrade = new UserTrade();
			userTrade.setPwd(pwd);
			userTrade.setUserId(currentUser.getUserId());
			UserTradeDto userTradeDto = userService.checkTradePwd(userTrade);
			if (!"1".equals(userTradeDto.getRetCode())) {
				log.info("调用 checkTradePwd 返回信息"
						+ JSON.toJSONString(userTradeDto));
				resultVo.setRetMsg(userTradeDto.getRetInfo());
				resultVo.setRetCode(userTradeDto.getRetCode());
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
			User shopUser = userService
					.findUserById(Long.parseLong(shopUserId));
			if (cashierRecordFromDb == null) {
				resultVo.setRetMsg("商户未购买生鲜宝!");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
			if (cashierRecordFromDb.getOrderState() != 1) {
				resultVo.setRetMsg("商户购买的生鲜宝已过期!");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
			if (!shopRealName.equals(shopUser.getRealName())) {
				resultVo.setRetMsg("实体店铺ID与真实姓名不一致");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}

			CashierSettings cashierSettings = new CashierSettings();
			// 红旗券倍数
			cashierSettings.setDividendType(2);
			CashierSettings cashierSettingsFromDb = cashierService
					.findCashierSettsingsByDividendType(cashierSettings);
			if (cashierSettingsFromDb == null
					|| cashierSettingsFromDb.getGiftFhed() == null
					|| cashierSettingsFromDb.getGiftHqj() == null) {
				resultVo.setRetMsg("请先设置生鲜宝全局倍数和返利倍数!");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
			BigDecimal payPrice = new BigDecimal(price)
					.multiply(cashierSettingsFromDb.getRealpayMultipe())
					.add(new BigDecimal(price).multiply(cashierSettingsFromDb
							.getCounterFee().multiply(BigDecimal.valueOf(0.01))))
					.setScale(0, BigDecimal.ROUND_HALF_UP);
			BigDecimal factoragePrice = new BigDecimal(price).multiply(
					cashierSettingsFromDb.getCounterFee().multiply(
							BigDecimal.valueOf(0.01))).setScale(0,
					BigDecimal.ROUND_HALF_UP);
			BigDecimal incomePrice = new BigDecimal(price).setScale(0,
					BigDecimal.ROUND_HALF_UP).add(factoragePrice);
			// 生成预订单
			OrderData orderData = new OrderData();

			orderData.setOrderId(orderId);
			orderData.setTotalQty(1);
			orderData.setOrderPrice(new BigDecimal(price).setScale(0,
					BigDecimal.ROUND_HALF_UP));
			orderData.setPrice(payPrice);
			orderData.setRealTransferprice(factoragePrice);
			orderData.setUserId(currentUser.getUserId());
			orderData.setMessage("生鲜宝支付订单");
			orderData.setPaidPrice(payPrice);
			orderData.setPayWay("3");

			orderData.setCreateTime(new Date());
			orderData.setReceiveName(shopUser.getRealName());
			orderData
					.setCreateBy(currentUser.getRealName() == null ? currentUser
							.getNickName() : currentUser.getRealName());
			orderData.setLastEditTime(new Date());
			orderData
					.setLastEditBy(currentUser.getRealName() == null ? currentUser
							.getNickName() : currentUser.getRealName());
			orderData.setSaleTarget("C");
			orderData.setOrderFhed(payPrice
					.multiply(cashierSettingsFromDb.getGiftFhed())
					.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
			orderData.setOrderHqj(payPrice
					.multiply(cashierSettingsFromDb.getGiftHqj())
					.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());

			orderData.setShopIncome(incomePrice);
			orderData.setSupplyType("51");
			orderData.setMerchantid("1436");
			orderData.setCompanyQy("1");
			orderData.setReceiveAddress("生鲜宝支付");
			orderData.setReceiveProvinceId(18L);
			orderData.setReceiveCityId(424L);
			orderData.setReceiveAreaId(2973L);
			orderData.setReceivePhone("11111111111");
			orderData.setDiscountPrice(BigDecimal.valueOf(0));
			List<OrderItemData> orderItemDatas = new ArrayList<OrderItemData>();

			OrderItemData orderItemData = new OrderItemData();

			orderItemData.setSkuId(147487811168071232L);
			orderItemData.setPid(5406517142632135L);
			orderItemData.setpName("生鲜宝交易");
			orderItemData.setSkuNameCn("生鲜宝交易");
			orderItemData.setSkuNameEn("shopTrade");
			orderItemData.setSkuQty(1);
			orderItemData.setUnit("笔");
			orderItemData.setPrice(payPrice);
			orderItemData.setSubtotalPirce(payPrice.multiply(BigDecimal
					.valueOf(orderItemData.getSkuQty())));
			orderItemData.setCreateTime(new Date());
			orderItemData
					.setCreateBy(currentUser.getRealName() == null ? currentUser
							.getNickName() : currentUser.getRealName());
			orderItemData.setLastEditTime(new Date());
			orderItemData
					.setLastEditBy(currentUser.getRealName() == null ? currentUser
							.getNickName() : currentUser.getRealName());
			orderItemData.setFhed(payPrice
					.multiply(cashierSettingsFromDb.getGiftFhed())
					.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
			orderItemData.setHqj(payPrice
					.multiply(cashierSettingsFromDb.getGiftHqj())
					.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
			orderItemDatas.add(orderItemData);
			orderData.setOrderItemDatas(orderItemDatas);
			customerOrderService.postOrderData(orderData);

			// 调用转账接口
			int transferStatus = 3;

			try {
				transferStatus = sqwAccountRecordService.transferAccountOfPOS(
						currentUser.getUserId(), payPrice,
						shopUser.getUserId(), incomePrice,
						String.valueOf(orderId),
						"生鲜宝结算:" + currentUser.getUserId() + "扣除红旗券"
								+ incomePrice + "," + shopUser.getUserId()
								+ "收入红旗券" + incomePrice, 2);

			} catch (Exception e) {
				Order order = new Order();
				order.setId(orderId);
				order.setOrderPlatform((short) 3);
				order.setVersion(OrderStatusConstant.INTEGER_INIT);
				order.setStatus(OrderStatusConstant.ORDER_SYS_CANCLE);
				customerOrderService.updateOrder(order);
				log.error("支付失败:" + e);
				resultVo.setRetMsg("支付失败.");
				resultVo.setRetCode("10");
				return JsonUtil.serializeBeanToJsonString(resultVo);
			}

			if (transferStatus == 1) {
				resultVo.setRetMsg("购买人或者实体店账户不存在.");
				resultVo.setRetCode("10");
				Order order = new Order();
				order.setId(orderId);
				order.setOrderPlatform((short) 3);
				order.setVersion(OrderStatusConstant.INTEGER_INIT);
				order.setStatus(OrderStatusConstant.ORDER_SYS_CANCLE);
				customerOrderService.updateOrder(order);

				return JsonUtil.serializeBeanToJsonString(resultVo);
			} else if (transferStatus == 2) {
				resultVo.setRetMsg("账户余额不足.");
				resultVo.setRetCode("10");
				Order order = new Order();
				order.setId(orderId);
				order.setOrderPlatform((short) 3);
				order.setVersion(OrderStatusConstant.INTEGER_INIT);
				order.setStatus(OrderStatusConstant.ORDER_SYS_CANCLE);
				customerOrderService.updateOrder(order);

				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
			// 转账成功更新订单状态
			else if (transferStatus == 0) {
				Order order = new Order();
				order.setId(orderId);
				order.setOrderPlatform((short) 3);
				order.setVersion(OrderStatusConstant.INTEGER_INIT);
				order.setStatus(OrderStatusConstant.ORDER_CUSTOMER_RECEIPT);
				customerOrderService.updateOrder(order);

				List<OrderItemReturnTicketDto> OrderItemReturnTicketDtoList = new ArrayList<OrderItemReturnTicketDto>();
				User user = userService.findUserById(currentUser.getUserId());
				Long firstUserId = null;
				Long secondUserId = null;
				if (!StringUtils.isEmpty(user.getTjMobile())) {
					User firstTjUser = userService.findUserByMobile(user
							.getTjMobile());
					log.info("=====firstTjUser =  "
							+ JSON.toJSONString(firstTjUser));
					if (firstTjUser != null) {
						firstUserId = firstTjUser.getUserId();
						log.info("=====firstUserId =  " + firstUserId);
						if (!StringUtils.isEmpty(firstTjUser.getTjMobile())) {
							User secondTjUser = userService
									.findUserByMobile(firstTjUser.getTjMobile());
							log.info("=====secondTjUser =  "
									+ JSON.toJSONString(secondTjUser));
							if (secondTjUser != null)
								secondUserId = secondTjUser.getUserId();
						}
					}
				}
				OrderItemReturnTicketDto orderItemReturnTicketDto = new OrderItemReturnTicketDto();
				orderItemReturnTicketDto.setpNum(orderItemData.getSkuQty());
				orderItemReturnTicketDto.setProductSkuId(orderItemData
						.getSkuId());
				orderItemReturnTicketDto.setProductRegin(orderData
						.getCompanyQy());
				orderItemReturnTicketDto.setRefObjType(1);
				orderItemReturnTicketDto.setRefObjId(orderId);
				orderItemReturnTicketDto.setPrice_hqq(orderItemData.getPrice());
				orderItemReturnTicketDto.setReturn_fhed(BigDecimal
						.valueOf(orderItemData.getFhed()));
				orderItemReturnTicketDto.setReturn_hqq(BigDecimal
						.valueOf(orderItemData.getHqj()));
				OrderItemReturnTicketDtoList.add(orderItemReturnTicketDto);
				// 调用积分返利服务
				sqwAccountRecordService.recordAccountByUserbuy(
						currentUser.getUserId(), OrderItemReturnTicketDtoList,
						firstUserId, secondUserId);
			} else {
				resultVo.setRetMsg("生鲜宝支付失败,未知异常");
				resultVo.setRetCode("10");
				Order order = new Order();
				order.setId(orderId);
				order.setOrderPlatform((short) 3);
				order.setVersion(OrderStatusConstant.INTEGER_INIT);
				order.setStatus(OrderStatusConstant.ORDER_SYS_CANCLE);
				customerOrderService.updateOrder(order);

				return JsonUtil.serializeBeanToJsonString(resultVo);
			}
		} catch (Exception e) {
			log.error("系统异常:" + e);
			Order order = new Order();
			order.setId(orderId);
			order.setOrderPlatform((short) 3);
			order.setVersion(OrderStatusConstant.INTEGER_INIT);
			order.setStatus(OrderStatusConstant.ORDER_SYS_CANCLE);
			customerOrderService.updateOrder(order);

			resultVo.setRetMsg("接口异常.");
			resultVo.setRetCode("10");
			return JsonUtil.serializeBeanToJsonString(resultVo);
		}
		resultVo.setRetMsg("生鲜宝成功支付");
		resultVo.setRetCode("1");
		return JsonUtil.serializeBeanToJsonString(resultVo);
	}

	/**
	 * 跳转到红旗宝详情页
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_MY_RECIVE_MONEY_ITEM)
	public String toMyReciveMoneyItem(Model model, HttpServletRequest request,
			HttpServletResponse response) throws TimeoutException,
			InterruptedException, MemcachedException {

		log.info("调用红旗宝详情");
		User currentUser = getCurrentUser(request);
		try {
			CashierRecord cashierRecord = new CashierRecord();
			cashierRecord.setUserId(currentUser.getUserId());
			cashierRecord.setType(1);
			List<CashierRecord> cashierRecords = (List<CashierRecord>) cashierService
					.selectCashierRecordByUserid(cashierRecord);
			CashierRecord cashierRecordFromDb = null;
			if (cashierRecords != null) {
				cashierRecordFromDb = cashierRecords.get(0);
			}
			model.addAttribute("cashierRecordFromDb", cashierRecordFromDb);
			// if(cashierRecordFromDb == null){
			// response.getResult().setResult("0");
			// response.getResult().setMessage("红旗宝详情不存在!");
			// return response;
			// }
			// Map<String,Object> map =new HashMap<String,Object>();
			// map.put("orderNo", cashierRecordFromDb.getOrderNo());
			// map.put("skuName", cashierRecordFromDb.getCommodityName());
			// map.put("startTime",
			// DateUtil.date2Str(cashierRecordFromDb.getStartDate(),
			// "yyyy.MM.dd HH:mm:ss"));
			// map.put("skuQty", cashierRecordFromDb.getAmount());
			// map.put("price",cashierRecordFromDb.getAmountOfMoney());
			// map.put("endTime",
			// DateUtil.date2Str(cashierRecordFromDb.getStopDate(),
			// "yyyy.MM.dd HH:mm:ss") );
			// response.setData(map);
			// response.getResult().setMessage("获取红旗宝详情成功.");
			// response.getResult().setResult("1");
		} catch (Exception e) {
			log.info("接口异常." + e.getMessage(), e);
		}
		return ResponseContant.CUS_MY_RECIVE_MONEY_ITEM;
	}

	/**
	 * 跳转到生鲜宝详情页
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_MY_SHENGXIAN_ITEM)
	public String toMyShengXianItem(Model model, HttpServletRequest request,
			HttpServletResponse response) throws TimeoutException,
			InterruptedException, MemcachedException {

		log.info("调用生鲜宝详情");
		User currentUser = getCurrentUser(request);
		try {
			CashierRecord cashierRecord = new CashierRecord();
			cashierRecord.setUserId(currentUser.getUserId());
			cashierRecord.setType(2);
			List<CashierRecord> cashierRecords = (List<CashierRecord>) cashierService
					.selectCashierRecordByUserid(cashierRecord);
			CashierRecord cashierRecordFromDb = null;
			if (cashierRecords != null) {
				cashierRecordFromDb = cashierRecords.get(0);
			}
			model.addAttribute("cashierRecordFromDb", cashierRecordFromDb);

		} catch (Exception e) {
			log.info("接口异常." + e.getMessage(), e);
		}
		return ResponseContant.CUS_MY_SHENGXIAN_ITEM;
	}

	/**
	 * 跳转到家庭宝详情页
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_MY_JIATING_ITEM)
	public String toMyJiaTingItem(Model model, HttpServletRequest request,
			HttpServletResponse response) throws TimeoutException,
			InterruptedException, MemcachedException {

		log.info("调用家庭宝详情");
		User currentUser = getCurrentUser(request);

		// 根据用户ID查看购买的家庭号
		Long userId = currentUser.getUserId();

		int jiaTingShangJiaStates = 0;
		try {
			HomeNumRecordDto homeNumRecordDto = homeNumRecordService
					.selectRecordByUserid(userId.intValue());
			HomeNumRecord homeNumRecord = null;
			Integer homeNumStates = 0;
			if (homeNumRecordDto != null) {
				homeNumRecord = homeNumRecordDto.getHomeNumRecord();
				homeNumStates = homeNumRecordDto.getStatus();
			}
			model.addAttribute("homeNumRecord", homeNumRecord);
			model.addAttribute("homeNumStates", homeNumStates);
			jiaTingShangJiaStates = supplierManagerService
					.findSupplierStatusByUserId(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 根据用户id查看家庭号状态
		model.addAttribute("jiaTingShangJiaStates", jiaTingShangJiaStates);
		model.addAttribute("userId", userId);
		return ResponseContant.CUS_MY_JIATING_ITEM;
	}

	/**
	 * 跳转到会员企业详情页
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_MY_CUSBUS_ITEM)
	public String toMyCusBusItem(Model model, HttpServletRequest request,
			HttpServletResponse response) throws TimeoutException,
			InterruptedException, MemcachedException {

		log.info("调用会员企业详情");
		User currentUser = getCurrentUser(request);

		// 根据用户ID查看购买的会员企业号
		Long userId = currentUser.getUserId();

		int cusBusStates = 0;
		try {
			SupplierNumRecordDto supplierNumRecordDto = supplierNumRecordService
					.selectRecordByUserid(userId.intValue());

			SupplierNumRecord supplierNumRecord = null;
			Integer supplierNumStates = 0;
			if (supplierNumRecordDto != null) {
				supplierNumRecord = supplierNumRecordDto.getSupplierNumRecord();
				supplierNumStates = supplierNumRecordDto.getStatus();
			}
			model.addAttribute("supplierNumRecord", supplierNumRecord);
			//会员企业号购买状态0 没购买  1过期 2正常使用
			model.addAttribute("supplierNumStates", supplierNumStates);
			//查询会员企业号状态 0未注册或商家  1正在审核或未通过或者过期2冻结3正常使用
			cusBusStates = supplierManagerService
					.findHuiYuanSupplierStatusByUserId(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 根据用户id查看家庭号状态
		model.addAttribute("cusBusStates", cusBusStates);
		model.addAttribute("userId", userId);
		return ResponseContant.CUS_MY_CUSBUS_ITEM;
	}

	/**
	 * 跳到指定的订单
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_MY_ALL_ORDER)
	public String toMyOrder(Short status, Integer pageNow, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws TimeoutException, InterruptedException, MemcachedException {
		log.info("toMyOrder execute....");
		// start
		// FIXME jianping.gao
		// 将订单状态储存到缓存中
		// 原因：订单全部确认收货后，可以返回到当前页
		if (status != null) {
			User currentUser = getCurrentUser(request);
			this.memcachedClient.add(CommonConstant.PATTERN
					+ CommonConstant.ORDER_STATUS + currentUser.getUserId(),
					CommonConstant.MEMCACHEDAGE, status);
		}
		// end

		cusOrderService.findCustomerOrderByCustomer(status, pageNow, model,
				request, response, getCurrentUser(request));
		model.addAttribute("picUrl3", picUrl3);
		return ResponseContant.CUS_MYORDER;
	}

	/**
	 * 获取更多订单信息
	 * 
	 * @param status
	 * @param pageNow
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_FIND_MORE_ORDER)
	@ResponseBody
	public String findMoreOrder(Short status, Integer pageNow, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("toMyOrder execute....");
		PageBean<OrderDTO> pageBean = cusOrderService
				.findCustomerOrderByCustomer(status, pageNow, model, request,
						response, getCurrentUser(request));
		model.addAttribute("picUrl2", picUrl2);
		return JsonUtil.serializeBeanToJsonString(pageBean);
	}

	/**
	 * 查看订单详情
	 * 
	 * @param orderId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_ORDER_DETAIL)
	public String orderDetail(Long orderId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("orderDetail  execute....");
		if (orderId == null) {
			log.info("orderDi is  null ....");
			throw new BadRequestException("order id is null ..");
		}

		OrderDTO orderDTO = cusOrderService.findOrderByOrderId(orderId,
				getCurrentUser(request));

		model.addAttribute("orderDto", orderDTO);
		model.addAttribute("picUrl3", picUrl3);
		return ResponseContant.CUS_ORDER_DETAIL;

	}

	/**
	 * 
	 * @param orderId
	 *            订单号(B2B为payId)
	 * @param orderType
	 *            1:B2C 2:B2B
	 * @return
	 */
	public Result queryLogisticsInfoByOrderId(String orderId, String orderType) {
		log.info("==111queryLogisticsInfoByOrderId==orderId:" + orderId
				+ "orderType:" + orderType);
		if (orderId == null || orderType == null) {
			log.info("订单号或订单类型为空orderId:" + orderId + " orderType:" + orderType);
			return null;
		}

		List<KdniaoLogisticsTrack> list = null;
		try {
			log.info("kdniaoCustomerOrderService查询服务开始。");

			list = kdniaoCustomerOrderService.getKdniaoLogisticsTrack(orderId);

			log.info("kdniaoCustomerOrderService查询服务结束");
		} catch (Exception e) {
			log.info("==kdniaoCustomerOrderService查询服务异常。");
		}
		if (list != null && list.size() != 0) {
			log.info("==kdniaoCustomerOrderService==orderId:" + orderId
					+ "orderType:" + orderType + "list.size()=" + list.size());
			Result result = new Result();
			result.setResultType(99);
			result.setData(list);
			log.info("==kdniaoCustomerOrderService==true");
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 查询物流信息
	 * 
	 * @param orderId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_ORDER_TRACE)
	public String trackOrder(Long orderId, Long shipId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("trackOrder  execute....");

		if (orderId == null) {
			log.info("orderDi is  null ....");
			throw new BadRequestException("order id is null ..");
		}

		OrderDTO orderDTO = cusOrderService.findOrderByOrderId(orderId,
				getCurrentUser(request));
		String orderType = "";
		List<OrderDTO> listOrd = new ArrayList<OrderDTO>();
		if (orderDTO != null
				&& (orderDTO.getLogisticsCarriersName() == null || ""
						.equals(orderDTO.getLogisticsCarriersName()))) {
			listOrd = cusOrderService.findLogisticsByOrderid(orderId);
			if (listOrd != null && listOrd.size() != 0
					&& "11".equals(listOrd.get(0).getSupplyType())) {
				for (int i = 0; i < listOrd.size(); i++) {
					OrderDTO ordNew = new OrderDTO();
					try {
						ordNew = (OrderDTO) BeanUtils.cloneBean(orderDTO);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ordNew.setLogisticsNumber(listOrd.get(i)
							.getLogisticsNumber());
					ordNew.setLogisticsCarriersName(listOrd.get(i)
							.getLogisticsCarriersName());

					listOrd.remove(i);
					listOrd.add(i, ordNew);
				}
				orderType = "11";
			}
		}

		Result result = queryLogisticsInfoByOrderId(orderDTO.getOrderNo() + "",
				!"".equals(orderType) ? orderType : "1");

		if (result != null && result.getResultType() != null
				&& result.getResultType() == 99) {
			List<KdniaoLogisticsTrack> kdTrackList = (List<KdniaoLogisticsTrack>) result
					.getData();
			if ("11".equals(orderType)) {
				for (int i = 0; i < listOrd.size(); i++) {
					OrderDTO dto = listOrd.get(i);
					List<OrderLogisticsMsg> tempList = new ArrayList<OrderLogisticsMsg>();
					for (KdniaoLogisticsTrack kdTrack : kdTrackList) {
						if (dto.getLogisticsNumber().equals(
								kdTrack.getLogistics_number())) {
							OrderLogisticsMsg logisticsMsg = new OrderLogisticsMsg();
							logisticsMsg.setMsg(kdTrack.getLogistics_track());
							logisticsMsg.setCreateTime(kdTrack
									.getLogistics_track_time());
							tempList.add(logisticsMsg);
						}
					}
					dto.setOrderLogisticsMsgs(tempList);
					listOrd.remove(i);
					listOrd.add(i, dto);
				}
			} else {
				List<OrderLogisticsMsg> tempList = new ArrayList<OrderLogisticsMsg>();
				for (KdniaoLogisticsTrack kdTrack : kdTrackList) {
					OrderLogisticsMsg logisticsMsg = new OrderLogisticsMsg();
					logisticsMsg.setMsg(kdTrack.getLogistics_track());
					logisticsMsg.setCreateTime(kdTrack
							.getLogistics_track_time());
					tempList.add(logisticsMsg);

					orderDTO.setLogisticsCarriersName(kdTrack
							.getCarriers_name());
					orderDTO.setLogisticsNumber(kdTrack.getLogistics_number());
				}
				orderDTO.setOrderLogisticsMsgs(tempList);
				listOrd.add(orderDTO);
			}
		} else if ("11".equals(orderType)) {

		} else {
			// 国内的包裹
			String supplyType = orderDTO.getSupplyType();
			if ("1".equals(supplyType) || "21".equals(supplyType)
					|| "51".equals(supplyType)) {

				if (null != result) {

					if (null != result.getData()) {

						List<OrderLogisticsMsg> tempList = new ArrayList<OrderLogisticsMsg>();
						String waybillno = "";
						String express = "";

						// 其他
						if (result.getResultType().equals("0")
								|| result.getResultType() == 0) {

							Map<String, Object> map = (Map<String, Object>) result
									.getData();

							orderDTO.setLogisticsCarriersName(map
									.get("express").toString());
							orderDTO.setLogisticsNumber(map.get("waybillno")
									.toString());
							listOrd = new ArrayList<OrderDTO>();
							listOrd.add(orderDTO);
						}

						// 2：顺丰
						if (result.getResultType() == 2) {
							List<SfRouteDto> listSf = (List<SfRouteDto>) result
									.getData();

							for (SfRouteDto sfRouteDto : listSf) {

								OrderLogisticsMsg logisticsMsg = new OrderLogisticsMsg();

								if (null != sfRouteDto.getAcceptAddress()) {
									logisticsMsg.setMsg(sfRouteDto
											.getAcceptAddress()
											+ ""
											+ sfRouteDto.getRemark());
								} else {
									logisticsMsg.setMsg(sfRouteDto.getRemark());
								}

								String opertime = sfRouteDto.getAcceptTime();
								log.info("-----opertime:", opertime);
								Date d = null;
								d = DateUtil.toDate(opertime,
										"yyyy-MM-dd HH:mm:ss");
								if (null == d) {
									d = DateUtil.toDate(opertime,
											"yyyy-MM-dd HH:mm");
								}

								logisticsMsg.setCreateTime(d);

								tempList.add(logisticsMsg);
								waybillno = sfRouteDto.getMailno();
								// express = sfRouteDto.getExpress();

								orderDTO.setLogisticsCarriersName("顺丰快递");
								orderDTO.setLogisticsNumber(sfRouteDto
										.getMailno());
							}

							orderDTO.setOrderLogisticsMsgs(tempList);
							listOrd = new ArrayList<OrderDTO>();
							listOrd.add(orderDTO);
						}

						// 3：百世
						if (result.getResultType() == 3) {
							List<TraceLogsType> listSf = (List<TraceLogsType>) result
									.getData();

							for (TraceLogsType traceLogsType : listSf) {

								List<TracesType> traceList = traceLogsType
										.getTraces();

								for (TracesType tracesType : traceList) {

									OrderLogisticsMsg logisticsMsg = new OrderLogisticsMsg();

									logisticsMsg.setMsg(tracesType.getRemark());

									String opertime = tracesType
											.getAcceptTime();
									log.info("-----opertime:", opertime);
									Date d = null;
									d = DateUtil.toDate(opertime,
											"yyyy-MM-dd HH:mm:ss");
									if (null == d) {
										d = DateUtil.toDate(opertime,
												"yyyy-MM-dd HH:mm");
									}

									logisticsMsg.setCreateTime(d);

									tempList.add(logisticsMsg);
								}

								waybillno = traceLogsType.getMailNo();
								// express = route.getExpress();

								orderDTO.setLogisticsCarriersName("百世汇通");
								orderDTO.setLogisticsNumber(traceLogsType
										.getMailNo());
							}

							orderDTO.setOrderLogisticsMsgs(tempList);
							listOrd = new ArrayList<OrderDTO>();
							listOrd.add(orderDTO);
						}
					}
				}

				// List<B2cShipOrderDTO> shipOrderDTOs =
				// orderDTO.getShipOrderDTOs();
				// B2cShipOrderDTO dto = new B2cShipOrderDTO();
				// for (B2cShipOrderDTO b2cShipOrderDTO : shipOrderDTOs) {
				// Long id = b2cShipOrderDTO.getPackNo();
				// if (id.intValue() == shipId.intValue()) {
				// dto = b2cShipOrderDTO;
				// }
				// }
				// model.addAttribute("shipDto", dto);

			} else {
				// 海外，保税区
				List<OrderLogisticsMsg> orderLogisticsMsgs = orderDTO
						.getOrderLogisticsMsgs();
				model.addAttribute("logMsg", orderLogisticsMsgs);
				listOrd = new ArrayList<OrderDTO>();
				listOrd.add(orderDTO);
			}
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("listOrd", listOrd);
		model.addAttribute("picUrl2", picUrl2);
		return ResponseContant.CUS_ORDER_TRACKING;

	}

	/**
	 * 取消订单
	 * 
	 * @param orderId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = RequestContant.CUS_ORDER_CANCLE)
	public String cancleOrder(Long orderId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("cancleOrder  execute ....");
		User currentUser = getCurrentUser(request);
		String res = cusOrderService.cancleOrderByOrderId(orderId, currentUser,
				model, request, response);
		return res;
	}

	// @AuthPassport
	// @RequestMapping(value = RequestContant.CUS_ORDER_CANCLE_PAGE)
	// public String cancleOrderPage(Long orderId, Model model,
	// HttpServletRequest request,
	// HttpServletResponse response){
	// model.addAttribute("orderId", orderId);
	// return ResponseContant.CUS_ORDER_CANCEL_PAGE;
	// }
	//
	// @AuthPassport
	// @ResponseBody
	// @RequestMapping(value = RequestContant.CUS_ORDER_USER_CANCEL_ORDER)
	// public String userCancelOrder(Long orderId,String orderCancelReason,
	// String orderCancelDetail, Model model,
	// HttpServletRequest request, HttpServletResponse response) {
	// log.info("cancleOrder execute ....");
	// log.info("orderId={};orderCancelReason={};orderCancelDetail={}", orderId,
	// orderCancelReason, orderCancelDetail);
	// User currentUser = getCurrentUser(request);
	//
	// // TODO 0 取消失败(异常情况) 1 取消失败 2 取消中
	// Integer res = customerOrderService.userCancelOrder(orderId,
	// currentUser.getUserId(), orderCancelReason, orderCancelDetail);
	// log.info("cancleOrder result = {}", res);
	// return res + "";
	// }

	/**
	 * 删除订单(暂且不用)
	 * 
	 * @param orderId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_ORDER_DEL)
	@AuthPassport
	@ResponseBody
	@Deprecated
	public String delOrder(Long orderId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("delOrder  execute...");
		// User currentUser = getCurrentUser(request);
		// String
		// res=cusOrderService.delOrder(currentUser,orderId,request,response);
		return null;

	}

	/**
	 * 去支付
	 * 
	 * @param orderId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	
	public String toPay1(String errorMsg, Long orderId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
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
		String accountType = findOrderByOrderId.getAccountType();
		log.info("orderStatus:" + orderStatus);

		// 支付方式
		BigDecimal cashPrice = findOrderByOrderId.getCashPrice();
		BigDecimal hqjPrice = findOrderByOrderId.getHqjPrice();
		log.info("findOrderByOrderId.getCashPrice()" + cashPrice);
		log.info("findOrderByOrderId.getHqjPrice()" + hqjPrice);

		// 判断是否在家庭区为易物商品

		int homeShangPin = 0;
		String companyQy = findOrderByOrderId.getCompanyQy();
		if (companyQy != null && "10".equals(companyQy)) {
			homeShangPin = 1;
		}
		// 仅限现金支付zcxg
		/*
		 * List<OrderItem> orderItemList = customerOrderService
		 * .getOrderItemByOrderId(orderId); String RECIVE_MONEY_SHOP =
		 * Constants.SqwAccountRecordService.RECIVE_MONEY_SHOP_SKU_ID; String[]
		 * reciveArr = RECIVE_MONEY_SHOP.split(",");
		 */
		if (cashPrice.doubleValue() > 0d && hqjPrice.doubleValue() == 0d) {
			findOrderByOrderId.setMixPayStatus("1");
		}

		// 避免零支付
		int MissZeroStatus = 1;
		BigDecimal scale = null;
		BigDecimal scaleM = null;
		OneDividendRatio oneDividendRatio = null;
		try {
			oneDividendRatio = dividendService.getOneDividendRatio();
		} catch (Exception e) {

		} // 现金比例订单
		int xjbl = 0;

		// 设置现金比例的商品只能混合支付/幸福购商品只能混合支付

		if (findOrderByOrderId.getCashPrice() != null
				&& findOrderByOrderId.getHqjPrice() != null
				&& findOrderByOrderId.getCashPrice().compareTo(
						BigDecimal.valueOf(0)) == 1
				&& findOrderByOrderId.getHqjPrice().compareTo(
						BigDecimal.valueOf(0)) == 1) {
			xjbl = 1;

		} else if (oneDividendRatio != null) {
			scale = findOrderByOrderId
					.getPrice()
					.multiply(
							oneDividendRatio.getTicketRation().multiply(
									BigDecimal.valueOf(0.01)))
					.setScale(0, BigDecimal.ROUND_HALF_UP);
			scaleM = findOrderByOrderId
					.getPrice()
					.multiply(
							oneDividendRatio.getCashRation().multiply(
									BigDecimal.valueOf(0.01)))
					.setScale(0, BigDecimal.ROUND_HALF_UP);

			if (scale.longValue() == 0L || scaleM.longValue() == 0L) {
				MissZeroStatus = 0;
			}

		}
		log.info("xjbl=" + xjbl);
		/*
		 * for (OrderItem orderItem : orderItemList) { for (String skuid :
		 * reciveArr) { // System.out.println(skuid); if
		 * (String.valueOf(orderItem.getSkuId()).equals(skuid)) {
		 * 
		 * findOrderByOrderId.setMixPayStatus("1"); break; }
		 * 
		 * } }
		 */

		switch (orderStatus) {
		// 待支付状态
		case 1:

			model.addAttribute("MissZeroStatus", MissZeroStatus);

			/*
			 * model.addAttribute("hqbStatus", hqbStatus);
			 * model.addAttribute("sxbStatus", sxbStatus);
			 */
			model.addAttribute("homeShangPin", homeShangPin);
			model.addAttribute("xjbl", xjbl);

			model.addAttribute("cashPrice",
					findOrderByOrderId.getCashPrice());
			model.addAttribute("hqjPrice",
					findOrderByOrderId.getHqjPrice());
			model.addAttribute("mixPayStatus",
					findOrderByOrderId.getMixPayStatus());
			model.addAttribute("errorMsg", errorMsg);
			model.addAttribute("orderId", orderId);
			model.addAttribute("supplyType", supplyType);
			model.addAttribute("accountType", accountType);
			model.addAttribute("userName", currentUser.getUserName());
			model.addAttribute("totalPrice", findOrderByOrderId.getOrderPrice());
			model.addAttribute("tax", findOrderByOrderId.getTotalTax());
			model.addAttribute("totalQty", findOrderByOrderId.getTotalQty());
			model.addAttribute("OrderItemDTO", JsonUtil
					.serializeBeanToJsonString(findOrderByOrderId
							.getOrderItemDTOs()));
			// 中行活动新增,订单来源为中行时,只支持中行支付
			model.addAttribute("orderSource",
					findOrderByOrderId.getOrderSource());
			Integer userAccountStatus = 0;
			try {
				userAccountStatus = sqwAccountRecordService
						.getUserAccountStatus(
								currentUser.getUserId(),
								Constants.SqwAccountRecordService.HQ_BALANCE_TYPE);
			} catch (Exception e) {
				e.printStackTrace();
			}

			model.addAttribute("userAccountStatus", userAccountStatus);
			return ResponseContant.CUS_ORDER_PAY;
			// 其他状态
		default:
			return ResponseContant.REDIRECT + RequestContant.CUS_ORDER
					+ RequestContant.CUS_MY_ALL_ORDER + "?pageNow=1";
		}
	}

    @RequestMapping(value = RequestContant.CUS_ORDER_TO_PAY)
    @AuthPassport
    public String toPay(String errorMsg, Long orderId, Model model, HttpServletRequest request){

        log.info("toPay execute....");
        if (orderId == null) {
            log.error("orderId is null");
            throw new BadRequestException("call toPay errror ");
        }

        User currentUser = getCurrentUser(request);
        String openId = currentUser.getWeixin();

	/*	List<OrderItem> orderItemList = customerOrderService.getOrderItemByOrderId(orderId);
		//查询用户激活状态
		Integer userStatus=0;
		try {
			 userStatus = sqwAccountRecordService.getUserAccountStatus(currentUser.getUserId(), 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 仅现金支付功能
		
		String reciveMoneySysSkuId = Constants.SqwAccountRecordService.RECIVE_MONEY_SHOP_SKU_ID;
		String[] reciveArr = reciveMoneySysSkuId.split(",");
		
		for (OrderItem orderItem : orderItemList) {
			
			for (String skuid : reciveArr) {
				if (String.valueOf(orderItem.getSkuId())
						.equals(skuid)) {
					//红旗宝，生鲜宝，只能现金支付标识
					findOrderByOrderId.setMixPayStatus("2");
					break;
				}
			
			}
		}*/
        OrderDTO findOrderByOrderId = cusOrderService.findOrderByOrderId(orderId, currentUser);
        // 支付前检查订单状态
        String supplyType = findOrderByOrderId.getSupplyType();
        Short orderStatus = findOrderByOrderId.getStatus();
        String accountType = findOrderByOrderId.getAccountType();
        //避免零支付
        int MissZeroStatus = 1;
        BigDecimal scale = null;
        BigDecimal scaleM = null;
        OneDividendRatio oneDividendRatio = null;
        try {
            oneDividendRatio = dividendService.getOneDividendRatio();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        int hqAndMoneyPay = 0;
        int hqPay = 0;
        int moneyPay = 0;
        if (oneDividendRatio != null || oneDividendRatio.getTicketRation() != null) {
            scale = findOrderByOrderId.getPrice().multiply(oneDividendRatio.getTicketRation().multiply(BigDecimal.valueOf(0.01))).setScale(0, BigDecimal.ROUND_HALF_UP);
            //scaleM = findOrderByOrderId.getPrice().multiply(oneDividendRatio.getCashRation().multiply(BigDecimal.valueOf(0.01))).setScale(0, BigDecimal.ROUND_HALF_UP);
            scaleM = findOrderByOrderId.getPrice().subtract(scale);
			  /*if(findOrderByOrderId.getCashRatio()!=null){
				  scaleM=findOrderByOrderId.getPrice().multiply(findOrderByOrderId.getCashRatio()).setScale(0, BigDecimal.ROUND_HALF_UP);
			  }*/

            //如果有现金比例，可以选择混合支付，
            if (findOrderByOrderId.getCashPrice().doubleValue() > 0) {
                hqAndMoneyPay = 1;
                scale = findOrderByOrderId.getHqjPrice();
                scaleM = findOrderByOrderId.getCashPrice();
            }
        }
        if (scale.longValue() == 0L || scaleM.longValue() == 0L) {
            MissZeroStatus = 0;
        } else {
            model.addAttribute("scale", scale);
            model.addAttribute("scaleM", scaleM);
        }
        //String yiWu=findOrderByOrderId.getCompanyQy().equals("10")?"1":"0";
        int yiWu = findOrderByOrderId.getCompanyQy().equals("10") ? 1 : 0;
        //如果订单中cashPrice=0，为全券商品
        double orderCashPrice = findOrderByOrderId.getCashPrice().doubleValue();

        if (yiWu == 1 || (orderCashPrice == 0 && yiWu != 1)) {
            hqPay = 1;
        }

        if (orderCashPrice > 0 || (orderCashPrice == 0 && yiWu != 1)) {
            moneyPay = 1;
        }
        if (orderCashPrice > 0) {
            hqAndMoneyPay = 1;
        }
        //家庭号商品只能红旗券支付
        model.addAttribute("homeHao", findOrderByOrderId.getCompanyQy().equals("10") ? "1" : "0");

        log.info("orderStatus:" + orderStatus);

        switch (orderStatus) {
            // 待支付状态
            case 1:
                model.addAttribute("mixPayStatus", findOrderByOrderId.getMixPayStatus());
                model.addAttribute("errorMsg", errorMsg);
                model.addAttribute("orderId", orderId);
                model.addAttribute("MissZeroStatus", MissZeroStatus);
                model.addAttribute("supplyType", supplyType);
                model.addAttribute("accountType", accountType);
                model.addAttribute("openId", openId);
                model.addAttribute("payAmount", findOrderByOrderId.getPrice());
                //money,hqj决定支付方式
                model.addAttribute("money", findOrderByOrderId.getCashPrice());
                model.addAttribute("hqj", findOrderByOrderId.getHqjPrice());
                // 中行活动新增，当订单来源为中行信用卡用户时，只支持中行支付
                model.addAttribute("orderSource", findOrderByOrderId.getOrderSource());
			/*Integer userAccountStatus = 0;
			try {
				userAccountStatus = sqwAccountRecordService.getUserAccountStatus(currentUser.getUserId(),
						Constants.SqwAccountRecordService.HQ_BALANCE_TYPE);
			} catch (Exception e) {
				e.printStackTrace();
			}*/

                //model.addAttribute("userAccountStatus", flag==true?"1":userAccountStatus);
                model.addAttribute("hqAndMoneyPay", hqAndMoneyPay);
                model.addAttribute("moneyPay", moneyPay);
                model.addAttribute("hqPay", hqPay);
                return ResponseContant.CUS_ORDER_PAY;
            // 其他状态
            default:
                return ResponseContant.REDIRECT + RequestContant.CUS_ORDER
                        + RequestContant.CUS_MY_ALL_ORDER + "?pageNow=1";
        }
    }
	/**
	 * 确认收货
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_ORDER_CONFIRM)
	@ResponseBody
	public String confirmOrder(Long orderId, Long shipId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("confirmOrder execute.....");
		if (orderId == null) {
			log.error("orderId is null");
			throw new BadRequestException();
		}
		User currentUser = getCurrentUser(request);
		String confirmOrder = cusOrderService.confirmOrder(currentUser,
				orderId, shipId);
		return confirmOrder;
	}

	/**
	 * 订单全部确认收货后 弹出 “评价提示页” 内容：查看订单和立即评价
	 * 
	 * @param orderId
	 * @param request
	 * @return
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	@RequestMapping(value = RequestContant.CUS_ORDER_RECEIPT_SUCCESS
			+ "/{orderId:[\\d]+}")
	public String orderReceiptSuccess(@PathVariable("orderId") Long orderId,
			HttpServletRequest request) throws TimeoutException,
			InterruptedException, MemcachedException {
		log.debug("The order receipt is successful");
		User currentUser = getCurrentUser(request);
		if (currentUser == null) {
			log.info("用户未登陆,跳转到登陆页");
			return RequestContant.REDIRECT + RequestContant.CUSTOMER
					+ RequestContant.CUSTOMER_TO_LOGIN;
		}
		Short status = this.memcachedClient.get(CommonConstant.PATTERN
				+ currentUser.getUserId());
		OrderDTO orderDto = cusOrderService.findOrderByOrderId(orderId,
				currentUser);
		request.setAttribute("order", orderDto);
		request.setAttribute("status", status);
		return ResponseContant.CUS_ORDER_RECEIPT_SUCCESS;
	}

	/**
	 * 去支付_微信
	 * 
	 * @param orderId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = RequestContant.CUS_ORDER_TO_WEIXIN_PAY)
	@AuthPassport*/
	public String toWeiXinPay1(Long orderId, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (orderId == null) {
			log.error("orderId is null");
			throw new BadRequestException("call toWeiXinPay errror ");
		}
		/*
		 * 逻辑
		 */
		User currentUser = getCurrentUser(request);
		OrderDTO findOrderByOrderId = cusOrderService.findOrderByOrderId(
				orderId, currentUser);
		String jsapiTicket = null;
		Map<String, String> signResultMap = new HashMap<String, String>();
		String code = null;
		String openId = null;
		boolean isDisplayWeiXin = false;
		// 支付前检查订单状态
		String supplyType = findOrderByOrderId.getSupplyType();
		if (OrderStatusConstant.PRODUCT_TYPE_INLAND.equals(supplyType)
				|| OrderStatusConstant.PRODUCT_TYPE_POP.equals(supplyType)
				|| OrderStatusConstant.PRODUCT_TYPE_OVERSEAS.equals(supplyType)) {
			log.info("toWeiXinPay execute....");

			/*
			 * code =
			 * this.memcachedClient.get(CommonConstant.WEIXIN_JSAPI_CODE);
			 * openId =
			 * this.memcachedClient.get(CommonConstant.WEIXIN_JSAPI_OPENID);
			 * if(StringUtils.isEmpty(code)){
			 */
			// 微信用户授权接口所需
			code = request.getParameter("code");

			if (StringUtils.isBlank(code)) {
				log.info("用户请求的 code 没值,表示需要授权");
				// 当前访问的地址
				String currenturl = request.getRequestURL()
						+ (request.getQueryString() == null ? ""
								: ("?" + request.getQueryString()));
				log.info("用户访问 currenturl:{}", currenturl);
				// 没有调用授权接口，那么就调用它
				// 构造授权接口地址
				String redirectUrl = Oauth.getCode(
						URLEncoder.encode(currenturl, "utf-8"), false);
				// 直接跳转到目标url
				return ResponseContant.REDIRECT + redirectUrl;
			}
			this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_CODE,
					CommonConstant.WEIXIN_JSAPI_CODE_VAILED_TIME, code);
			log.info("用户请求的code 有值{}", code);
			// 看是否调用了授权接口，调用了的话在这里取得用户信息
			String result = Oauth.getToken(code);
			JSONObject jsonObject = JSONObject.fromObject(result);
			// if("40029".equals(jsonObject.getString("errcode"))){
			if (result.indexOf("40029") > 0) {
				log.info("用户请求的 code 不合法,表示需要重新授权");
				// 当前访问的地址
				String currenturl = request.getRequestURL()
						+ (request.getParameter("orderId") == null ? ""
								: ("?orderId=" + request
										.getParameter("orderId")));
				log.info("用户访问 currenturl:{}", currenturl);
				// 没有调用授权接口，那么就调用它
				// 构造授权接口地址
				String redirectUrl = Oauth.getCode(
						URLEncoder.encode(currenturl, "utf-8"), false);
				// 直接跳转到目标url
				return ResponseContant.REDIRECT + redirectUrl;
			}
			openId = jsonObject.getString("openid");
			this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_OPENID,
					CommonConstant.WEIXIN_JSAPI_OPENID_VAILED_TIME, openId);
			log.info("得到myOpenid is {}", openId);
			// }

			// 获取access_token
			// 获取jsapi_ticket
			jsapiTicket = this.memcachedClient
					.get(CommonConstant.WEIXIN_JSAPI_TICKET);
			if (StringUtils.isEmpty(jsapiTicket)) {
				String accessTokenResult = Oauth.getAccessToken();
				JSONObject accessTokenObject = JSONObject
						.fromObject(accessTokenResult);
				String accessToken = accessTokenObject
						.getString("access_token");
				log.info("得到accessToken is {}", accessToken);
				// 保存memcache
				this.memcachedClient.add(CommonConstant.WEIXIN_ACCESS_TOKEN,
						CommonConstant.WEIXIN_ACCESS_TOKEN_VAILED_TIME,
						accessToken);
				String jsapiTicketResult = Oauth.getJsapiTicket(accessToken);
				JSONObject jsapiTicketObject = JSONObject
						.fromObject(jsapiTicketResult);
				jsapiTicket = jsapiTicketObject.getString("ticket");
				log.info("得到jsapiTicket is {}", jsapiTicket);
				// 保存memcache
				this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_TICKET,
						CommonConstant.WEIXIN_JSAPI_TICKET_VAILED_TIME,
						jsapiTicket);
			}
			// 权限验证
			String url = request.getRequestURL().toString() + "?orderId="
					+ orderId + "&code=" + code + "&state=STATE";
			signResultMap = Oauth.sign(jsapiTicket, url);
			log.info("得到signResultMap is {}", signResultMap);

			isDisplayWeiXin = true;
			log.info("toWeiXinPay execute....");
		}

		Short orderStatus = findOrderByOrderId.getStatus();
		String accountType = findOrderByOrderId.getAccountType();

		log.info("orderStatus:" + orderStatus);

		// 判断是否在家庭区

		int homeShangPin = 0;
		String companyQy = findOrderByOrderId.getCompanyQy();
		if (companyQy != null && "10".equals(companyQy)) {
			homeShangPin = 1;
		}
		// 支付方式
		BigDecimal cashPrice = findOrderByOrderId.getCashPrice();
		BigDecimal hqjPrice = findOrderByOrderId.getHqjPrice();
		// 仅限现金支付zcxg

		if (cashPrice.doubleValue() > 0d && hqjPrice.doubleValue() == 0d) {
			findOrderByOrderId.setMixPayStatus("1");
		}

		// 避免零支付
		int MissZeroStatus = 1;
		BigDecimal scale = null;
		BigDecimal scaleM = null;
		OneDividendRatio oneDividendRatio = null;
		try {
			oneDividendRatio = dividendService.getOneDividendRatio();
		} catch (Exception e) {

		}
		// 现金比例商品
		int xjbl = 0;
		// 设置现金比例的商品只能混合支付/幸福购商品
		if (findOrderByOrderId.getCashPrice() != null
				&& findOrderByOrderId.getHqjPrice() != null
				&& findOrderByOrderId.getCashPrice().compareTo(
						BigDecimal.valueOf(0)) == 1
				&& findOrderByOrderId.getHqjPrice().compareTo(
						BigDecimal.valueOf(0)) == 1) {
			xjbl = 1;

		} else if (oneDividendRatio != null
				|| oneDividendRatio.getTicketRation() != null) {
			scale = findOrderByOrderId
					.getPrice()
					.multiply(
							oneDividendRatio.getTicketRation().multiply(
									BigDecimal.valueOf(0.01)))
					.setScale(0, BigDecimal.ROUND_HALF_UP);
			scaleM = findOrderByOrderId
					.getPrice()
					.multiply(
							oneDividendRatio.getCashRation().multiply(
									BigDecimal.valueOf(0.01)))
					.setScale(0, BigDecimal.ROUND_HALF_UP);

			if (scale.longValue() == 0L || scaleM.longValue() == 0L) {
				MissZeroStatus = 0;
			}
		}

		switch (orderStatus) {
		// 待支付状态
		case 1:

			model.addAttribute("MissZeroStatus", MissZeroStatus);
			model.addAttribute("mixPayStatus",
					findOrderByOrderId.getMixPayStatus());
			model.addAttribute("homeShangPin", homeShangPin);
			model.addAttribute("xjbl", xjbl);
			model.addAttribute("jsapiTicket", jsapiTicket);
			model.addAttribute("nonceStr", signResultMap.get("noncestr"));
			model.addAttribute("timestamp", signResultMap.get("timestamp"));
			model.addAttribute("signature", signResultMap.get("signature"));
			model.addAttribute("appId", signResultMap.get("appid"));
			model.addAttribute("openId", openId);
			model.addAttribute("isDisplayWeiXin", isDisplayWeiXin);
			model.addAttribute("weiXinFlag", 1);

			model.addAttribute("orderId", orderId);
			model.addAttribute("supplyType", supplyType);
			model.addAttribute("accountType", accountType);
			model.addAttribute("userName", currentUser.getUserName());
			model.addAttribute("totalPrice", findOrderByOrderId.getOrderPrice());
			model.addAttribute("tax", findOrderByOrderId.getTotalTax());
			model.addAttribute("totalQty", findOrderByOrderId.getTotalQty());
			model.addAttribute("OrderItemDTO", JsonUtil
					.serializeBeanToJsonString(findOrderByOrderId
							.getOrderItemDTOs()));
			// 中行活动新增,订单来源为中行时,只支持中行支付
			model.addAttribute("orderSource",
					findOrderByOrderId.getOrderSource());
			Integer userAccountStatus = 0;
			try {
				userAccountStatus = sqwAccountRecordService
						.getUserAccountStatus(
								currentUser.getUserId(),
								Constants.SqwAccountRecordService.HQ_BALANCE_TYPE);
			} catch (Exception e) {
				e.printStackTrace();
			}

			model.addAttribute("userAccountStatus", userAccountStatus);
			return ResponseContant.CUS_ORDER_PAY;
			// 其他状态
		default:
			return ResponseContant.REDIRECT + RequestContant.CUS_ORDER
					+ RequestContant.CUS_MY_ALL_ORDER + "?pageNow=1";
		}
	}

	@RequestMapping(value = RequestContant.CUS_ORDER_TO_WEIXIN_PAY)
	@AuthPassport
	public String toWeiXinPay(Long orderId, Model model, HttpServletRequest request, String openId) throws Exception {

		if (orderId == null) {
			log.error("orderId is null");
			throw new BadRequestException("call toWeiXinPay errror ");
		}
		/*
		 * 逻辑
		 */
		User currentUser = getCurrentUser(request);
		OrderDTO findOrderByOrderId = cusOrderService.findOrderByOrderId(orderId, currentUser);

		String jsapiTicket = null;
		Map<String, String> signResultMap = new HashMap<String, String>();
		String code = null;
		boolean isDisplayWeiXin = false;
		// 支付前检查订单状态
		String supplyType = findOrderByOrderId.getSupplyType();
		if (OrderStatusConstant.PRODUCT_TYPE_INLAND.equals(supplyType)
				|| OrderStatusConstant.PRODUCT_TYPE_POP.equals(supplyType)
				|| OrderStatusConstant.PRODUCT_TYPE_OVERSEAS.equals(supplyType)) {
			log.info("线上订单去支付页时，获取openID");

			// 若当前登陆账号有openID，则直接取
			if (StringUtils.isEmpty(currentUser.getWeixin())) {
				openId = currentUser.getWeixin();

			} else {
				// 支付宝打开时
				if (request.getHeader("User-Agent").contains("AlipayClient")) {
					if (StringUtils.isEmpty(openId)) {
						return ResponseContant.REDIRECT + "/customer/thirdAuthorize?openType=alipay&isHandleUser=false&rurl=" + request.getRequestURI();
					}
				}else if (request.getHeader("User-Agent").contains("MicroMessenger")){

					// 微信用户授权接口所需
					code = request.getParameter("code");
					log.info("code值為", code);
					if (StringUtils.isBlank(code)) {
						log.info("用户请求的 code 没值,表示需要授权");
						// 当前访问的地址
						String currenturl = request.getRequestURL()
								+ (request.getQueryString() == null ? ""
								: ("?" + request.getQueryString()));
						log.info("用户访问 currenturl:{}", currenturl);
						// 没有调用授权接口，那么就调用它
						// 构造授权接口地址
						String redirectUrl = Oauth.getCode(
								URLEncoder.encode(currenturl, "utf-8"), false);
						// 直接跳转到目标url
						return ResponseContant.REDIRECT + redirectUrl;
					}
					log.info("用户请求的code 有值{}", code);
					// 看是否调用了授权接口，调用了的话在这里取得用户信息
					String result = Oauth.getToken(code);
					JSONObject jsonObject = JSONObject.fromObject(result);
					// if("40029".equals(jsonObject.getString("errcode"))){
					if (result.indexOf("40029") > 0) {
						log.info("用户请求的 code 不合法,表示需要重新授权");
						// 当前访问的地址
						String currenturl = request.getRequestURL()
								+ (request.getParameter("orderId") == null ? ""
								: ("?orderId=" + request
								.getParameter("orderId")));
						log.info("用户访问 currenturl:{}", currenturl);
						// 没有调用授权接口，那么就调用它
						// 构造授权接口地址
						String redirectUrl = Oauth.getCode(
								URLEncoder.encode(currenturl, "utf-8"), false);
						// 直接跳转到目标url
						return ResponseContant.REDIRECT + redirectUrl;
					}
					openId = jsonObject.getString("openid");
					log.info("得到myOpenid is {}", openId);

					// 获取access_token
					// 获取jsapi_ticket
					jsapiTicket = this.memcachedClient
							.get(CommonConstant.WEIXIN_JSAPI_TICKET);
					if (StringUtils.isEmpty(jsapiTicket)) {
						String accessTokenResult = Oauth.getAccessToken();
						JSONObject accessTokenObject = JSONObject
								.fromObject(accessTokenResult);
						String accessToken = accessTokenObject
								.getString("access_token");
						log.info("得到accessToken is {}", accessToken);
						// 保存memcache
						this.memcachedClient.add(CommonConstant.WEIXIN_ACCESS_TOKEN,
								CommonConstant.WEIXIN_ACCESS_TOKEN_VAILED_TIME,
								accessToken);
						String jsapiTicketResult = Oauth.getJsapiTicket(accessToken);
						JSONObject jsapiTicketObject = JSONObject
								.fromObject(jsapiTicketResult);
						jsapiTicket = jsapiTicketObject.getString("ticket");
						log.info("得到jsapiTicket is {}", jsapiTicket);
						// 保存memcache
						this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_TICKET,
								CommonConstant.WEIXIN_JSAPI_TICKET_VAILED_TIME,
								jsapiTicket);
					}
					// 权限验证
					String url = request.getRequestURL().toString() + "?orderId="
							+ orderId + "&code=" + code + "&state=STATE";
					signResultMap = Oauth.sign(jsapiTicket, url);
					log.info("得到signResultMap is {}", signResultMap);

					isDisplayWeiXin = true;
					log.info("toWeiXinPay execute....");
				}
			}
		}

		Short orderStatus = findOrderByOrderId.getStatus();
		String accountType = findOrderByOrderId.getAccountType();

		log.info("orderStatus:" + orderStatus);


		//查询用户激活状态
		Integer userStatus = 0;
		try {
			userStatus = sqwAccountRecordService.getUserAccountStatus(currentUser.getUserId(), 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 仅现金支付功能
		/*
		String reciveMoneySysSkuId = Constants.SqwAccountRecordService.RECIVE_MONEY_SHOP_SKU_ID;
		String[] reciveArr = reciveMoneySysSkuId.split(",");

		for (OrderItem orderItem : orderItemList) {

			for (String skuid : reciveArr) {
				if (String.valueOf(orderItem.getSkuId())
						.equals(skuid)) {
					//红旗宝，生鲜宝，只能现金支付标识
					findOrderByOrderId.setMixPayStatus("2");
					break;
				}

			}
		}*/

		// 支付前检查订单状态
		// 避免零支付
		int MissZeroStatus = 1;
		BigDecimal scale = null;
		BigDecimal scaleM = null;
		OneDividendRatio oneDividendRatio = null;
		try {
			oneDividendRatio = dividendService.getOneDividendRatio();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		int hqAndMoneyPay = 0;
		int hqPay = 0;
		int moneyPay = 0;
		if (oneDividendRatio != null || oneDividendRatio.getTicketRation() != null) {
			scale = findOrderByOrderId.getPrice().multiply(oneDividendRatio.getTicketRation().multiply(BigDecimal.valueOf(0.01))).setScale(0, BigDecimal.ROUND_HALF_UP);
			//scaleM = findOrderByOrderId.getPrice().multiply(oneDividendRatio.getCashRation().multiply(BigDecimal.valueOf(0.01))).setScale(0, BigDecimal.ROUND_HALF_UP);
			scaleM = findOrderByOrderId.getPrice().subtract(scale);
			  /*if(findOrderByOrderId.getCashRatio()!=null){
				  scaleM=findOrderByOrderId.getPrice().multiply(findOrderByOrderId.getCashRatio()).setScale(0, BigDecimal.ROUND_HALF_UP);
			  }*/

			//如果有现金比例，可以选择混合支付，
			if (findOrderByOrderId.getCashPrice().doubleValue() > 0) {
				hqAndMoneyPay = 1;
				scale = findOrderByOrderId.getHqjPrice();
				scaleM = findOrderByOrderId.getCashPrice();
			}
		}
		if (scale.longValue() == 0L || scaleM.longValue() == 0L) {
			MissZeroStatus = 0;
		} else {
			model.addAttribute("scale", scale);
			model.addAttribute("scaleM", scaleM);
		}
		//String yiWu=findOrderByOrderId.getCompanyQy().equals("10")?"1":"0";
		int yiWu = findOrderByOrderId.getCompanyQy().equals("10") ? 1 : 0;
		//如果订单中cashPrice=0，为全券商品
		double orderCashPrice = findOrderByOrderId.getCashPrice().doubleValue();

		if (yiWu == 1 || (orderCashPrice == 0 && yiWu != 1)) {
			hqPay = 1;
		}

		if (orderCashPrice > 0 || (orderCashPrice == 0 && yiWu != 1)) {
			moneyPay = 1;
		}
		if (orderCashPrice > 0) {
			hqAndMoneyPay = 1;
		}


		switch (orderStatus) {
			// 待支付状态
			case 1:

				model.addAttribute("MissZeroStatus", MissZeroStatus);
				model.addAttribute("mixPayStatus", findOrderByOrderId.getMixPayStatus());
				model.addAttribute("hqPay", hqPay);
				model.addAttribute("moneyPay", moneyPay);
				model.addAttribute("hqAndMoneyPay", hqAndMoneyPay);
				model.addAttribute("hqj", findOrderByOrderId.getHqjPrice());

				model.addAttribute("jsapiTicket", jsapiTicket);
				model.addAttribute("nonceStr", signResultMap.get("noncestr"));
				model.addAttribute("timestamp", signResultMap.get("timestamp"));
				model.addAttribute("signature", signResultMap.get("signature"));
				model.addAttribute("appId", signResultMap.get("appid"));
				model.addAttribute("openId", openId);
				model.addAttribute("isDisplayWeiXin", isDisplayWeiXin);
				model.addAttribute("weiXinFlag", 1);

				model.addAttribute("orderId", orderId);
				model.addAttribute("supplyType", supplyType);
				model.addAttribute("accountType", accountType);
				model.addAttribute("userName", currentUser.getUserName());
				model.addAttribute("totalPrice", findOrderByOrderId.getOrderPrice());
				model.addAttribute("tax", findOrderByOrderId.getTotalTax());
				model.addAttribute("totalQty", findOrderByOrderId.getTotalQty());
				model.addAttribute("OrderItemDTO", JsonUtil.serializeBeanToJsonString(findOrderByOrderId.getOrderItemDTOs()));
				// 中行活动新增,订单来源为中行时,只支持中行支付
				model.addAttribute("orderSource", findOrderByOrderId.getOrderSource());
				Integer userAccountStatus = 0;
				try {
					userAccountStatus = sqwAccountRecordService.getUserAccountStatus(currentUser.getUserId(), Constants.SqwAccountRecordService.HQ_BALANCE_TYPE);

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

				model.addAttribute("userAccountStatus", userAccountStatus);
				return ResponseContant.CUS_ORDER_PAY;

			default:
				return ResponseContant.REDIRECT + RequestContant.CUS_ORDER + RequestContant.CUS_MY_ALL_ORDER + "?pageNow=1";
		}
	}



	@RequestMapping(value = "/invoiceInfo")
	public String goInvoiceInfo() {
		return "/order/invoice/invoice_info";
	}
	/**
	 * 跳到指定的订单
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_MY_ALL_SUPPLIER_ORDER)
	public String toMySupplierOrder(Short status, Integer pageNow, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws TimeoutException, InterruptedException, MemcachedException {
		log.info("toMyOrder execute....");
		// start
		// FIXME jianping.gao
		// 将订单状态储存到缓存中
		// 原因：订单全部确认收货后，可以返回到当前页
		if (status != null) {
			User currentUser = getCurrentUser(request);
			this.memcachedClient.add(CommonConstant.PATTERN
					+ CommonConstant.ORDER_STATUS + currentUser.getUserId(),
					CommonConstant.MEMCACHEDAGE, status);
		}
		// end

		cusOrderService.findSupplierOrderByCustomer(status, pageNow, model,
				request, response, getCurrentUser(request));
		model.addAttribute("picUrl3", picUrl3);
		return ResponseContant.CUS_MYSUPPLIERORDER;
	}
}