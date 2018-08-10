/**
 * 
 */
package com.mall.controller.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.annotation.AuthPassport;
import com.mall.category.api.own.MyCategoryDispService;
import com.mall.category.po.TdCateDispPub;
import com.mall.check.OrderChecks.submitOrderChecks;
import com.mall.check.OrderChecks.submitRechargeOrderChecks;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.controller.impl.AddressControllerImpl;
import com.mall.controller.impl.CartControllerImpl;
import com.mall.controller.impl.OrderControllerImpl;
import com.mall.controller.impl.UserControllerImpl;
import com.mall.customer.model.ReceiveAddress;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.CartActivityGroupVO;
import com.mall.customer.order.dto.CartDTO;
import com.mall.customer.order.dto.CartGroupVO;
import com.mall.customer.order.dto.CartResultDTO;
import com.mall.customer.order.dto.CartSkuDTO;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderItemDTO;
import com.mall.customer.order.dto.ResultCode;
import com.mall.customer.order.dto.SubOrderDTO;
import com.mall.customer.order.po.LimitCount;
import com.mall.customer.service.SqwAccountRecordService;
import com.mall.customer.service.UserService;
import com.mall.dealer.product.api.DealerProductSkuService;
import com.mall.dealer.product.dto.DealerProductSkuForPromotionDTO;
import com.mall.dealer.product.po.BuyLimit;
import com.mall.dealer.product.po.DealerProductSku;
import com.mall.dealer.product.po.ProductTag;
import com.mall.dealer.product.po.ProductTags;
import com.mall.dealer.productTags.api.ProductTagsService;
//import com.mall.promotion.dto.coupon.MyCouponStockB2CDTO;
import com.mall.stock.dto.StockDTO;
import com.mall.stock.dto.StockVO;
import com.mall.utils.Constants;
import com.mall.utils.JsonUtil;
import com.mall.vo.CpsOrder;
import com.mall.vo.CpsOrderItem;
import com.mall.vo.CpsOrders;
import com.mall.vo.CustomerCartVO;
import com.mall.vo.CustomerOrderVO;
import com.mall.vo.CustomerRechargeOrderVO;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * @author zhengqiang.shi 2015年3月27日 下午6:45:37
 */
@Controller
@RequestMapping(value = RequestContant.ORDER)
public class OrderController extends BaseController {

	private static final Logger log = LoggerFactory
			.getLogger(CartController.class);

	@Autowired
	private OrderControllerImpl orderControllerImpl;

	@Autowired
	private CartControllerImpl cartControllerImpl;

	@Autowired
	private AddressControllerImpl addressControllerImpl;

	@Autowired
	private UserControllerImpl userControllerImpl;

	@Autowired
	private SqwAccountRecordService sqwAccountRecordService;

	@Autowired
	private DealerProductSkuService dealerProductSkuService;

	@Autowired
	private MyCategoryDispService myCategoryDispService;

	@Autowired
	private UserService userService; 
	
	@Autowired
	private ProductTagsService productTagsService;
	
	@Autowired
	private CustomerOrderService customerOrderService;

	/**
	 * 判断用户星级是否符合星级限定
	 * 
	 * @param customerCartVO
	 * @param userId
	 * @return
	 */
	private boolean LimitStarsBuy(List<Long> skuIds, Long userId) {
		User finduser = userService.findUserById(userId);
		String Ustar = finduser.getStars().toString();
		int UserStar = Integer.parseInt(Ustar);
		String Ustars = null;
		switch (UserStar) {
		case 0:
			Ustars = "tag_star_0";
			break;
		case 1:
			Ustars = "tag_star_1";
			break;
		case 2:
			Ustars = "tag_star_2";
			break;
		case 3:
			Ustars = "tag_star_3";
			break;
		case 4:
			Ustars = "tag_star_4";
			break;
		case 5:
			Ustars = "tag_star_5";
			break;

		default:
			break;
		}
		// 2.获取商品的星级限制 findSkuBySkuIds(skuId<list>)

		List<DealerProductSku> buySkuList = dealerProductSkuService
				.findSkuBySkuIds(skuIds);
		Long productid = buySkuList.get(0).getProductid();
		// 调用service查询商品限定星级
		ProductTag pt = new ProductTag();
		pt.setProductId(productid);
		pt.setTagType(1);
		List<ProductTags> tagType = productTagsService
				.selectByProductIdAndTagType(pt);
		if (tagType.size() < 1 || tagType == null) {
			return true;
		}
		// 3.判断是否符合本星级购买
		for (ProductTags p : tagType) {
			if (p.getTagCode().equals(Ustars)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 获取单个sku库存
	 */
	private Map<Long, Long> getStock(List<Long> skuIds) {
		List<StockDTO> stockDtos = new ArrayList<StockDTO>();
		for (Long skuId : skuIds) {
			StockDTO skuDto = new StockDTO();
			skuDto.setSkuId(skuId);
			// 封装B2C商品类型 supply取值：1表示国内发货，11表示海外直邮，12表示国内保税区发货
			skuDto.setSupplyType(51 + "");
			stockDtos.add(skuDto);
		}
		Map<Long, Long> skuStockNumMap = new HashMap<Long, Long>();
		StockVO allStock = RemoteServiceSingleton.getInstance()
				.getStockCustomerService().getAllStock(stockDtos);
		Map<Long, StockDTO> skuStockMap = allStock.getSkuStockMap();
		for (Long skuId : skuIds) {
			skuStockNumMap.put(skuId, skuStockMap.get(skuId).getSpotQty() + 0l);
		}
		return skuStockNumMap;

	}

	/**
	 * 获取当前sku购物车内数量
	 * 
	 * @param userId
	 * @param req
	 * @param res
	 * @param skuId
	 * @return
	 */
	private int getCartSkuIDQty(Long userId, HttpServletRequest req,
			HttpServletResponse res, Long skuId) {
		CartDTO cartDTO = cartControllerImpl.getCart(userId, req, res);
		int numCart = 0;
		if (cartDTO != null) {

			List<CartSkuDTO> CartSkuList = cartDTO.getSkuList();
			for (CartSkuDTO c : CartSkuList) {
				if (c.getSkuId().equals(skuId)) {
					numCart = c.getQty();
				}
			}
		}
		return numCart;
	}

	/**
	 * 限购时间数量提示
	 */
	private CartResultDTO LimitProductNum(List<Long> skuIds, int number,
			Long userId) {
		CartResultDTO cartresultDTO = new CartResultDTO();

		

		//Map<Long, Long> stock = getStock(skuIds);

		List<DealerProductSku> buySkuList = dealerProductSkuService
				.findSkuBySkuIds(skuIds);

		Long productid = buySkuList.get(0).getProductid();

		BuyLimit buyLimitContent = productTagsService
				.LimitContentByProudctId(productid);
		if (buyLimitContent != null) {
			// 2.获取该商品的限定时间和数量
			// 限定商品数量
			if (buyLimitContent != null) {

				Integer limitN = buyLimitContent.getMaxNumber();

				Long presentTime = System.currentTimeMillis();// 当前时间
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date beginTime = buyLimitContent.getStartTime();
				Date endTime = buyLimitContent.getEndTime();
				// 开始时间
				Long begin = beginTime.getTime();
				// 结束时间
				Long end = endTime.getTime();
				// 开始时间结束时间userid productid查用户在规定时间内买了多少
				LimitCount limitCount = new LimitCount();
				limitCount.setPid(productid);
				limitCount.setStartTime(beginTime);
				limitCount.setEndTime(endTime);
				limitCount.setUserId(userId);

				Integer num_already = customerOrderService
						.limitBuyCount(limitCount);
				if (num_already == null) {
					num_already = 0;
				}
				// 可购买的数量
				long CanBuyNum = limitN - num_already;

				if (number > CanBuyNum) {
					cartresultDTO.setResultMsg("50010");
					return cartresultDTO;
				}

			}
		}
		cartresultDTO.setResultMsg("200");
		return cartresultDTO;
	}

	/**
	 * 提交订单（充值）
	 * 
	 * @param customerTelecomOrderVO
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.ORDER_SUBMIT_RECHARGE)
	@ResponseBody
	public String submitTelecomOrder(
			@Validated({ submitRechargeOrderChecks.class }) CustomerRechargeOrderVO customerTelecomOrderVO,
			BindingResult br, HttpServletRequest request,
			HttpServletResponse response, String callback) throws IOException {

		// 请求参数检查
		checkRequest(br);

		// 获取当前用户
		User user = getCurrentUser(request);

		// 返回Map
		Map<String, String> messageMap = new HashMap<String, String>();

		// 保存订单
		SubOrderDTO subOrderDTO = orderControllerImpl.submit(user,
				customerTelecomOrderVO);

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		String jsonStr = JsonUtil.serializeBeanToJsonString(subOrderDTO);
		messageMap.put("subOrderDTO", jsonStr);

		List<ResultCode> resultCodeList = subOrderDTO.getResultCodes();
		// 正常情况
		if (resultCodeList == null || resultCodeList.isEmpty()) {
			messageMap.put("status", "200");
		}

		String returnJson = JsonUtil.serializeBeanToJsonString(messageMap);
		log.info("submit telecom order end.return:{}", returnJson);

		if (StringUtils.isBlank(callback)) {
			response.getWriter().write(returnJson);
		} else {
			response.getWriter().write(callback + "(" + returnJson + ")");
		}

		return null;
	}

	/**
	 * 购物车结算提交订单
	 *
	 * @param req
	 * @param customerOrderVO
	 * @param br
	 * @return
	 * @throws IOException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.ORDER_SUBMIT)
	@ResponseBody
	public Map submit(HttpServletRequest req, @Validated({submitOrderChecks.class}) CustomerOrderVO customerOrderVO, BindingResult br, HttpServletResponse response) throws IOException {

		// 请求参数检查
		checkRequest(br);

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user.getUserId();

		// 返回Map
		Map<String, String> messageMap = new HashMap<String, String>(8);

		// set area to cookie
		ReceiveAddress receiveAddress = addressControllerImpl.getReceiveAddressByAddressId(customerOrderVO.getAddressId());
		Long provinceId = receiveAddress.getProvinceId();

		if (Arrays.asList(13L, 23L).contains(provinceId) && (customerOrderVO.getSkuIdList().contains(146321460598471345L)
				|| customerOrderVO.getSkuIdList().contains(
				146321460812300776L)
				|| customerOrderVO.getSkuIdList().contains(
				146321460955948113L)
				|| customerOrderVO.getSkuIdList().contains(
				146321713611557827L)
				|| customerOrderVO.getSkuIdList().contains(
				146434856129798889L)
				|| customerOrderVO.getSkuIdList().contains(
				146434856223330378L)
				|| customerOrderVO.getSkuIdList().contains(
				146434856304179288L)
				|| customerOrderVO.getSkuIdList().contains(
				146434856407453915L)
				|| customerOrderVO.getSkuIdList().contains(
				146434856509696990L)
				|| customerOrderVO.getSkuIdList().contains(
				146434856614561650L)
				|| customerOrderVO.getSkuIdList().contains(
				146434857127554781L)
				|| customerOrderVO.getSkuIdList().contains(
				146513854073540099L)
				|| customerOrderVO.getSkuIdList().contains(
				146521111749611619L)
				|| customerOrderVO.getSkuIdList().contains(
				146822370007703758L)
				|| customerOrderVO.getSkuIdList().contains(
				146822417774698057L)
				|| customerOrderVO.getSkuIdList().contains(
				146778837887663417L)
				|| customerOrderVO.getSkuIdList().contains(
				146485941942585235L)
				|| customerOrderVO.getSkuIdList().contains(
				146778926973336561L) || customerOrderVO
				.getSkuIdList().contains(146822397583168657L))) {

			messageMap.put("status", "506");
			return messageMap;
		}

		log.info("customerOrderVO= {}", JSONSerializer.toJSON(customerOrderVO));


		// 获取需要结算的信息
		CartDTO cartDTO = cartControllerImpl.select(userId, customerOrderVO.getSkuIdList(), req, response);

		if (cartDTO == null) {
			log.error("获取需要结算的信息为空！返回404！");
			messageMap.put("status", "404");
			return messageMap;
		}
		//提交订单后不支付返回再提交订单问题解决
		List<CartSkuDTO> CartSkuList = cartDTO.getSkuList();
		ArrayList<Long> skuIds = new ArrayList<Long>();
		HashMap<Long, Integer> hmPqty = new HashMap<Long, Integer>();
		for (CartSkuDTO c : CartSkuList) {
			Long pid = c.getPid();
			if (hmPqty.get(pid) == null) {
				hmPqty.put(pid, c.getQty());
			} else {
				hmPqty.put(pid, c.getQty() + hmPqty.get(pid));
			}

		}
		for (CartSkuDTO c : CartSkuList) {
			Long pid = c.getPid();
			skuIds.add(c.getSkuId());
			//红旗宝和激活分区
			String checkFenQu = checkFenQu(skuIds, userId);
			//未激活用户购买非激活区
			if (checkFenQu.equals("40000")) {
				messageMap.put("status", "507");
				return messageMap;

				//激活用户购买激活区
			} else if (checkFenQu.equals("41000")) {
				messageMap.put("status", "508");
				return messageMap;
			}

			// 限购判断
			CartResultDTO limitProductNum = LimitProductNum(skuIds, hmPqty.get(pid), userId);

			if (limitProductNum.getResultMsg() != "200") {
				messageMap.put("status", "509");
				return messageMap;
			}
			// 星级判断
			boolean limitStarsBuy = LimitStarsBuy(skuIds, userId);
			if (!limitStarsBuy) {
				messageMap.put("status", "510");
				return messageMap;
			}
			skuIds.clear();
		}


		// 完善订单信息
		orderControllerImpl.complateOder(customerOrderVO, req);

		// 提交订单
		SubOrderDTO subOrderDTO = orderControllerImpl.submit(user, cartDTO, receiveAddress, customerOrderVO, req);

		// 正常情况
		List<ResultCode> resultCodeList = subOrderDTO.getResultCodes();
		List<OrderDTO> orderDTOs = subOrderDTO.getOrderDTOs();

		if (resultCodeList == null || resultCodeList.isEmpty()) {
			messageMap.put("status", "200");
			messageMap.put("subOrderDTO", JsonUtil.serializeBeanToJsonString(subOrderDTO));

			// 循环取出cps需要的订单信息数据 zhanglk 2015-11-04
			if (orderDTOs != null && orderDTOs.size() > 0 && customerOrderVO.getCpsCookie() != null && customerOrderVO.getCpsCookie().length() > 0) {

				log.info("订单提交成功，拼接cps订单数据信息开始，共计：" + orderDTOs.size() + "个订单。");
				messageMap.put("cpsCookieValue", customerOrderVO.getCpsCookie());// cps

				// cookie值
				List<CpsOrder> cpsOrderList = new ArrayList<CpsOrder>();// 订单列表
				List<CpsOrderItem> cpsOrderItems = null;// 订单明细列表
				CpsOrder cpsOrder = null;// cps 订单
				CpsOrderItem cpsOrderItem = null;// 订单明细
				CpsOrders cpsOrders = new CpsOrders();// cps 所有订单
				SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (int i = 0; i < orderDTOs.size(); i++) {
					cpsOrderItems = new ArrayList<CpsOrderItem>();
					cpsOrder = new CpsOrder();
					cpsOrder.setCampaignId(customerOrderVO.getlType());
					cpsOrder.setFare("");// 该字段未取实际值，赋予空值
					cpsOrder.setFavorable("");// 该字段未取实际值，赋予空值
					cpsOrder.setFavorableCode("");// 该字段未取实际值，赋予空值
					cpsOrder.setFeedback(customerOrderVO.getCpsMemId());// 亿起发对应wi
					// 领克特对应
					// a_id
					cpsOrder.setOrderNo(orderDTOs.get(i).getId().toString());// 订单号
					cpsOrder.setOrderstatus(orderDTOs.get(i).getStatus().toString());// 订单状态
					cpsOrder.setOrderTime(URLEncoder.encode(time.format(orderDTOs.get(i).getCreateTime()), "utf-8"));// 下单时间

					cpsOrder.setPaymentStatus(orderDTOs.get(i).getStatus().toString());// 支付状态,与订单状态公用
					cpsOrder.setPaymentType("");// 支付方式 空值
					cpsOrder.setUpdateTime(URLEncoder.encode(time.format(orderDTOs.get(i).getCreateTime()), "utf-8"));// 更新时间
					cpsOrder.setTotalPrice(orderDTOs.get(i).getPrice().toString());// 订单应付金额

					for (OrderItemDTO item : orderDTOs.get(i).getOrderItemDTOs()) {
						cpsOrderItem = new CpsOrderItem();
						cpsOrderItem.setAmount(item.getSkuQty().toString());// 商品数量
						// 按1算，单价按折扣后总金额算
						cpsOrderItem.setCategory(item.getCategoryId());// 商品分类
						cpsOrderItem.setCommissionType("basic");// 佣金类型 统一为basic
						// 固定值
						cpsOrderItem.setProductNo(item.getPid().toString());
						cpsOrderItem.setName(URLEncoder.encode(item.getpName(), "utf-8"));// 商品名称
						cpsOrderItem.setOrderNo(item.getOrderId().toString());// 订单ID
						// 计算商品 价格 一种商品金额小计 减去 优惠金额
						cpsOrderItem.setPrice(item.getPrice().subtract(item.getDiscountPrice().divide(new BigDecimal(item.getSkuQty()), 2, BigDecimal.ROUND_HALF_UP)).doubleValue() + "");

						log.info("订单号{}的item.getPrice()={} item.getDiscountPrice()={} item.getSkuQty()={}", orderDTOs.get(i).getId(), item.getPrice(), item.getDiscountPrice(), item.getSkuQty());

						if (!item.getSubtotalPirce().equals(new BigDecimal(0))) {
							log.info("==================非赠品订单{}====================", orderDTOs.get(i).getId());
							cpsOrderItems.add(cpsOrderItem);
						}
					}
					cpsOrder.setOrderItems(cpsOrderItems);

					cpsOrderList.add(cpsOrder);
				}
				cpsOrders.setOrders(cpsOrderList);
				String cpsOrdersStr = JsonUtil
						.serializeBeanToJsonString(cpsOrders);
				messageMap.put("cpsOrdersInfo", cpsOrdersStr);// cps 订单数据信息
				log.info("拼接cps订单数据信息完成：" + cpsOrdersStr);
			}

			return messageMap;
		}

		// 购买的商品属异常情况 返回错误集合
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/text;charset=UTF-8");
		String jsonStr = JsonUtil.serializeBeanToJsonString(subOrderDTO);

		response.getWriter().write(jsonStr);
		return null;
	}

	/**
	 * 立即购买提交订单
	 * 
	 * @param req
	 * @param customerOrderVO
	 * @param br
	 * @return
	 * @throws IOException
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.ORDER_SUBMIT_DIRECTBUY)
	@ResponseBody 
	public String submitDirectBuy(
			HttpServletRequest req,
			@Validated({ submitOrderChecks.class }) CustomerOrderVO customerOrderVO,
			BindingResult br, HttpServletResponse response) throws IOException {

		// 请求参数检查
		checkRequest(br);

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user.getUserId();

		// 返回Map
		Map<String, String> messageMap = new HashMap<String, String>();

		// set area to cookie
		ReceiveAddress receiveAddress = addressControllerImpl
				.getReceiveAddressByAddressId(customerOrderVO.getAddressId());
		Long provinceId = receiveAddress.getProvinceId();

	
		log.info("customerOrderVO={}", JSONSerializer.toJSON(customerOrderVO));

		
		
		
		CartDTO cartDTO;
		// 获取需要结算的信息

		cartDTO = cartControllerImpl.selectDirectBuy(userId,
				customerOrderVO.getSkuIdList(), req, response);

		if (cartDTO == null) {
			log.error("获取需要结算的信息为空！返回404！");
			messageMap.put("status", "404");
			return JsonUtil.serializeBeanToJsonString(messageMap);
		}
		//提交订单后不支付返回再提交订单问题解决
		List<CartSkuDTO> CartSkuList = cartDTO.getSkuList();
				ArrayList<Long> skuIds = new ArrayList<Long>();
				HashMap<Long, Integer> hmPqty = new HashMap<Long, Integer>();
				for (CartSkuDTO c : CartSkuList) {
					Long pid = c.getPid();
					if (hmPqty.get(pid) == null) {
						hmPqty.put(pid, c.getQty());
					} else {
						hmPqty.put(pid, c.getQty() + hmPqty.get(pid));
					}

				}
				for (CartSkuDTO c : CartSkuList) {
					Long pid = c.getPid();
					skuIds.add(c.getSkuId());
					//红旗宝和激活分区
					String checkFenQu = checkFenQu(skuIds, userId);
					if(checkFenQu.equals("40000")){//未激活用户购买非激活区
						messageMap.put("status", "507");
						return JsonUtil.serializeBeanToJsonString(messageMap);
					}else if(checkFenQu.equals("41000")){//激活用户购买激活区
						messageMap.put("status", "508");
						return JsonUtil.serializeBeanToJsonString(messageMap);
					}
					
					// 限购判断
					CartResultDTO limitProductNum = LimitProductNum(skuIds,
							hmPqty.get(pid), userId);

					if (limitProductNum.getResultMsg() != "200") {
						messageMap.put("status", "509");
						return JsonUtil.serializeBeanToJsonString(messageMap);
					}
					// 星级判断
					boolean limitStarsBuy = LimitStarsBuy(skuIds, userId);
					if (!limitStarsBuy) {
						messageMap.put("status", "510");
						return JsonUtil.serializeBeanToJsonString(messageMap);
					}
					skuIds.clear();
				}
		// 完善订单信息
		orderControllerImpl.complateOder(customerOrderVO, req);

		// 提交订单
		SubOrderDTO subOrderDTO = orderControllerImpl.submitDirectBuy(user, cartDTO,
				receiveAddress, customerOrderVO, req);

		// 正常情况
		List<ResultCode> resultCodeList = subOrderDTO.getResultCodes();
		List<OrderDTO> orderDTOs = subOrderDTO.getOrderDTOs();
		if (resultCodeList == null || resultCodeList.isEmpty()) {
			messageMap.put("status", "200");

			messageMap.put("subOrderDTO",
					JsonUtil.serializeBeanToJsonString(subOrderDTO));

			// 循环取出cps需要的订单信息数据 zhanglk 2015-11-04
			if (orderDTOs != null && orderDTOs.size() > 0
					&& customerOrderVO.getCpsCookie() != null
					&& customerOrderVO.getCpsCookie().length() > 0) {
				log.info("订单提交成功，拼接cps订单数据信息开始，共计：" + orderDTOs.size() + "个订单。");
				messageMap
						.put("cpsCookieValue", customerOrderVO.getCpsCookie());// cps
				// cookie值
				List<CpsOrder> cpsOrderList = new ArrayList<CpsOrder>();// 订单列表
				List<CpsOrderItem> cpsOrderItems = null;// 订单明细列表
				CpsOrder cpsOrder = null;// cps 订单
				CpsOrderItem cpsOrderItem = null;// 订单明细
				CpsOrders cpsOrders = new CpsOrders();// cps 所有订单
				SimpleDateFormat time = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				for (int i = 0; i < orderDTOs.size(); i++) {
					cpsOrderItems = new ArrayList<CpsOrderItem>();
					cpsOrder = new CpsOrder();
					cpsOrder.setCampaignId(customerOrderVO.getlType());
					cpsOrder.setFare("");// 该字段未取实际值，赋予空值
					cpsOrder.setFavorable("");// 该字段未取实际值，赋予空值
					cpsOrder.setFavorableCode("");// 该字段未取实际值，赋予空值
					cpsOrder.setFeedback(customerOrderVO.getCpsMemId());// 亿起发对应wi
					// 领克特对应
					// a_id
					cpsOrder.setOrderNo(orderDTOs.get(i).getId() + "");// 订单号
					cpsOrder.setOrderstatus(orderDTOs.get(i).getStatus() + "");// 订单状态
					cpsOrder.setOrderTime(URLEncoder.encode(
							time.format(orderDTOs.get(i).getCreateTime()),
							"utf-8"));// 下单时间
					cpsOrder.setPaymentStatus(orderDTOs.get(i).getStatus() + "");// 支付状态,与订单状态公用
					cpsOrder.setPaymentType("");// 支付方式 空值
					cpsOrder.setUpdateTime(URLEncoder.encode(
							time.format(orderDTOs.get(i).getCreateTime()),
							"utf-8"));// 更新时间
					cpsOrder.setTotalPrice(orderDTOs.get(i).getPrice() + "");// 订单应付金额

					for (OrderItemDTO item : orderDTOs.get(i)
							.getOrderItemDTOs()) {
						cpsOrderItem = new CpsOrderItem();
						cpsOrderItem.setAmount(item.getSkuQty() + "");// 商品数量
						// 按1算，单价按折扣后总金额算
						cpsOrderItem.setCategory(item.getCategoryId());// 商品分类
						cpsOrderItem.setCommissionType("basic");// 佣金类型 统一为basic
						// 固定值
						cpsOrderItem.setProductNo(item.getPid() + "");
						cpsOrderItem.setName(URLEncoder.encode(item.getpName(),
								"utf-8"));// 商品名称
						cpsOrderItem.setOrderNo(item.getOrderId() + "");// 订单ID
						// 计算商品 价格 一种商品金额小计 减去 优惠金额
						cpsOrderItem
								.setPrice(item
										.getPrice()
										.subtract(
												item.getDiscountPrice()
														.divide(new BigDecimal(
																item.getSkuQty()),
																2,
																BigDecimal.ROUND_HALF_UP))
										.doubleValue()
										+ "");
						log.info("**************订单号" + orderDTOs.get(i).getId()
								+ "的item.getPrice()=" + item.getPrice()
								+ ";item.getDiscountPrice()="
								+ item.getDiscountPrice()
								+ ";item.getSkuQty()=" + item.getSkuQty()
								+ "*****************");
						if (!item.getSubtotalPirce().equals(new BigDecimal(0))) {
							log.info("==================非赠品订单"
									+ orderDTOs.get(i).getId()
									+ "====================");
							cpsOrderItems.add(cpsOrderItem);
						}
					}
					cpsOrder.setOrderItems(cpsOrderItems);

					cpsOrderList.add(cpsOrder);
				}
				cpsOrders.setOrders(cpsOrderList);
				String cpsOrdersStr = JsonUtil
						.serializeBeanToJsonString(cpsOrders);
				messageMap.put("cpsOrdersInfo", cpsOrdersStr);// cps 订单数据信息
				log.info("拼接cps订单数据信息完成：" + cpsOrdersStr);
			}

			return JsonUtil.serializeBeanToJsonString(messageMap);
		}

		// 购买的商品属异常情况 返回错误集合
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/text;charset=UTF-8");
		String jsonStr = JsonUtil.serializeBeanToJsonString(subOrderDTO);

		response.getWriter().write(jsonStr);

		return null;

	}

	private boolean checkPrice(CartDTO cartDTO) {
		List<CartGroupVO> cartGroupVOList = cartDTO.getCartGroupVOList();

		BigDecimal sumPrice = new BigDecimal(0); // 总价
		for (CartGroupVO cartGroupVO : cartGroupVOList) {
			int groupProductType = cartGroupVO.getGroupProductType();
			switch (groupProductType) {
			// case 11:
			case 12:
			case 21:
				// 校验单价
				List<CartActivityGroupVO> activityGroupList = cartGroupVO
						.getActivityGroupList();
				for (CartActivityGroupVO cartActivityGroupVO : activityGroupList) {
					List<CartSkuDTO> skuList = cartActivityGroupVO.getSkuList();

					for (CartSkuDTO cartSkuDTO : skuList) {

						if (!cartSkuDTO.getIsSelect()
								|| cartSkuDTO.getIsSoldOut() == 0) {
							continue;
						}

						// 参加活动后的小计价格
						BigDecimal subTotalPrice = cartSkuDTO
								.getSubTotalPrice();
						sumPrice = sumPrice.add(subTotalPrice);
					}
				}

				break;
			default:
				continue;
			}
		}

		// sumPrice 单笔订单总价不可以超过2000元
		if (sumPrice.compareTo(new BigDecimal(2000)) > 0) {
			log.info("check price return false,sumPrice:" + sumPrice);
			return false;
		}

		return true;
	}

	@RequestMapping("/getOrderSum")
	@ResponseBody
	public String getOrderSums(HttpServletRequest req, HttpServletResponse resp) {
		User user = getCurrentUser(req);
		String backData = "";
		int promotionsum = 0;
		if (null == user.getUserId()) {
			return null;
		}

		Map<String, Long> prosum = new HashMap<String, Long>();
		prosum.put("identification", 0L);
		prosum.put("userid", user.getUserId());
		try {
			// 订单接口
			Map<String, Integer> map = RemoteServiceSingleton.getInstance()
					.getCustomerOrderService()
					.getQtyByUserId(user.getUserId(), null);
			// 优惠券接口
			/*List<MyCouponStockB2CDTO> prosumList = RemoteServiceSingleton
					.getInstance().getCouponB2CService()
					.findMyCouponStockByUserid(prosum);
			if (null != prosumList && prosumList.size() != 0) {
				promotionsum = prosumList.size();
			}*/
			if (map != null && !map.isEmpty()) {
				map.put("prosum", promotionsum);
				JSONArray jsonarray = JSONArray.fromObject(map);
				backData = jsonarray.toString();
			}
		} catch (Exception e) {
			log.info("调用 getCustomerOrderService/getCouponB2CService 服务失败",
					e.getMessage());
		}
		return backData;
	}

private String checkFenQu(List<Long> skuList, Long userId) {
	List<DealerProductSkuForPromotionDTO> dealerProductSkuForPromotionDTOList = dealerProductSkuService
			.findProSkuByIdsForPromotion(skuList);
	if (dealerProductSkuForPromotionDTOList != null
			|| dealerProductSkuForPromotionDTOList.size() > 0) {
	DealerProductSkuForPromotionDTO dealerProductSkuForPromotionDTO = dealerProductSkuForPromotionDTOList
			.get(0);
	List<TdCateDispPub> queryTdCateDispPubListQd = null;
	try {
		queryTdCateDispPubListQd = myCategoryDispService
				.queryTdCateDispPubListQd();
	} catch (Exception e1) {
		log.error("获取展示类目和发布类目失败,user:" + userId, e1);
	}
	// 任何用户都能买红旗宝
/*	if (productTagsService.LimitContentOrNull(dealerProductSkuForPromotionDTO.getProductId(),"hqb",4)==null&&productTagsService.LimitContentOrNull(dealerProductSkuForPromotionDTO.getProductId(),"sxb",4)==null) {
		Boolean buyStatus = true;
		if (queryTdCateDispPubListQd != null
				&& queryTdCateDispPubListQd.size() > 0) {
			for (TdCateDispPub tdCateDispPub : queryTdCateDispPubListQd) {
				if (tdCateDispPub.getCatePubId().equals(
						dealerProductSkuForPromotionDTO.getCatePubId())) {
					buyStatus = false;
				}
			}
		}
		Integer userAccountStatus = 0;
		try {
			userAccountStatus = sqwAccountRecordService
					.getUserAccountStatus(
							userId,
							Constants.SqwAccountRecordService.HQ_BALANCE_TYPE);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 未激活用户购买非激活区产品时提示不能购买
					if (buyStatus) { // 不是激活区
						if (userAccountStatus != Constants.TWO&&productTagsService.LimitContentOrNull(dealerProductSkuForPromotionDTO.getProductId(), "tag_star_0", 1) == null) {
							return "40000";
						} 
					} else { // 激活区
						if (userAccountStatus == Constants.TWO) {
							return "41000";
						}
					}
		
		}*/
	return "hqb";
	}
	return null;
}
}