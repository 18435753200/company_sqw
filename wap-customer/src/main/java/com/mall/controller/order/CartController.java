/**
 * 
 */
package com.mall.controller.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mall.annotation.AuthPassport;
import com.mall.category.api.own.MyCategoryDispService;
import com.mall.category.po.TdCateDispPub;
import com.mall.check.CartChecks.addCartCheck;
import com.mall.check.CartChecks.deleteCartCheck;
import com.mall.check.CartChecks.directBuyCheck;
import com.mall.check.CartChecks.settlementCheck;
import com.mall.check.CartChecks.updateCartCheck;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.controller.impl.AddressControllerImpl;
import com.mall.controller.impl.CartControllerImpl;
import com.mall.controller.impl.UserControllerImpl;
import com.mall.customer.dto.HomeNumRecordDto;
import com.mall.customer.dto.activity.SupplierNumRecordDto;
import com.mall.customer.model.HomeNumRecord;
import com.mall.customer.model.ReceiveAddress;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.common.CartConstant;
import com.mall.customer.order.dto.CartActivityGroupVO;
import com.mall.customer.order.dto.CartDTO;
import com.mall.customer.order.dto.CartGroupVO;
import com.mall.customer.order.dto.CartResultDTO;
import com.mall.customer.order.dto.CartSkuDTO;
import com.mall.customer.order.po.LimitCount;
import com.mall.customer.service.HomeNumRecordService;
import com.mall.customer.service.SqwAccountRecordService;
import com.mall.customer.service.SupplierNumRecordService;
import com.mall.customer.service.UserService;
import com.mall.dealer.product.api.DealerProductSkuService;
import com.mall.dealer.product.dto.DealerProductSkuForPromotionDTO;
import com.mall.dealer.product.po.BuyLimit;
import com.mall.dealer.product.po.DealerProduct;
import com.mall.dealer.product.po.DealerProductSku;
import com.mall.dealer.product.po.ProductTag;
import com.mall.dealer.product.po.ProductTags;
import com.mall.dealer.productTags.api.ProductTagsService;
import com.mall.stock.dto.StockDTO;
import com.mall.stock.dto.StockVO;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.utils.Constants;
import com.mall.utils.JsonUtil;
import com.mall.vo.CustomerCartVO;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * @author zhengqiang.shi 2015年3月23日 上午11:04:27
 */
@Controller
@RequestMapping(value = RequestContant.CART)
public class CartController extends BaseController {

	private static final Logger log = LoggerFactory
			.getLogger(CartController.class);

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

	@Autowired
	private SupplierManagerService supplierManagerService;

	@Autowired
	private HomeNumRecordService homeNumRecordService;

	@Autowired
	private SupplierNumRecordService supplierNumRecordService;

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

		// Map<Long, Long> stock = getStock(skuIds);

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
	 * 添加购物车
	 * 
	 * @param model
	 * @param skuId
	 *            ：购买的规格
	 * @param number
	 *            ：购买数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = RequestContant.CART_ADD)
	public String add(
			@Validated({ addCartCheck.class }) CustomerCartVO customerCartVO,
			BindingResult br, HttpServletRequest req, HttpServletResponse res,
			String callback) {

		log.info("start to add sku...");

		// 请求参数验证
		checkRequest(br);

		// 获取当前登录用户ID
		User user = getCurrentUser(req);

		Long userId = user == null ? null : user.getUserId();

		if (userId == null) {
			// 无用户登录
			return "10000";
		}

		List<Long> skuList = new ArrayList<Long>();
		skuList.add(customerCartVO.getSkuId());
		List<DealerProductSkuForPromotionDTO> dealerProductSkuForPromotionDTOList = dealerProductSkuService
				.findProSkuByIdsForPromotion(skuList);
		if (dealerProductSkuForPromotionDTOList == null
				|| dealerProductSkuForPromotionDTOList.size() < 1) {
			log.error("sku异常无法找到相应的产品id，sku:{}", customerCartVO.getSkuId());
			// 系统异常
			return "30000";
		}
		DealerProductSkuForPromotionDTO dealerProductSkuForPromotionDTO = dealerProductSkuForPromotionDTOList
				.get(0);
		// 企业小号不能购买企业自身商品
		User user2 = userService.findUserById(userId);
		if (String.valueOf(dealerProductSkuForPromotionDTO.getSupplierId())
				.equals(user.getSupplierId())
				&& ("1").equals(user2.getSupplierFlag()))
			return "60000";
		log.info("FIRST_BUY_PRODUCT_ID:{}",
				dealerProductSkuForPromotionDTO.getCatePubId());

		List<TdCateDispPub> queryTdCateDispPubListQd = null;
		try {
			queryTdCateDispPubListQd = myCategoryDispService
					.queryTdCateDispPubListQd();
		} catch (Exception e1) {
			log.error("获取展示类目和发布类目失败,user:" + userId, e1);
		}
		Long productid = dealerProductSkuForPromotionDTO.getProductId();
		// 企业小号不能购买家庭号6.7会员企业号不能购买家庭号7.31
		ProductTags jtbtag = productTagsService.LimitContentOrNull(productid,
				"jtb", 4);
		SupplierNumRecordDto supplierNumRecordDto;
		try {
			supplierNumRecordDto = supplierNumRecordService
					.selectRecordByUserid(userId.intValue());
			if (jtbtag != null
					&& (("1").equals(user2.getSupplierFlag()) || supplierNumRecordDto
							.getSupplierNumRecord() != null)) {
				return "40040";
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// 家庭号不能购买自己发布的商品
		Supplier supplier = supplierManagerService
				.findSupplier(dealerProductSkuForPromotionDTO.getSupplierId());

		if (supplier.getUserId() != null && supplier.getUserId().equals(userId))
			return "60001";
		// 会员企业不能购买自己发布的商品

		// 企业小号和购买过家庭号的不能再购买会员企业号7.31
		ProductTags cusbustag = productTagsService.LimitContentOrNull(
				productid, "qyb", 4);
		try {
			HomeNumRecordDto homeNumRecordDto = homeNumRecordService
					.selectRecordByUserid(userId.intValue());
			if (cusbustag != null
					&& (("1").equals(user2.getSupplierFlag()) || homeNumRecordDto
							.getHomeNumRecord() != null)) {

				return "40050";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		// 添加购物车星级限制
		boolean limitStarsBuy = LimitStarsBuy(skuList, userId);
		if (limitStarsBuy != true) {
			return "40010";
		}
		// 添加购物车限购
		/*
		 * List<DealerProductSku> buySkuList = dealerProductSkuService
		 * .findSkuBySkuIds(skuList); Long productid =
		 * buySkuList.get(0).getProductid();
		 */
		// 获取购物车内单个商品多个sku数量
		CartDTO cartDTO = cartControllerImpl.getCart(userId, req, res);
		int numCart = 0;
		if (cartDTO != null) {

			List<CartSkuDTO> CartSkuList = cartDTO.getSkuList();
			for (CartSkuDTO c : CartSkuList) {
				if (c.getPid().equals(productid)) {
					numCart += c.getQty();
				}
			}
		}
		// 商品详情页数量+购物车内数量
		int num = customerCartVO.getNumber().intValue() + numCart;
		System.out
				.println(customerCartVO.getNumber().intValue() + "" + numCart);

		CartResultDTO limitProductNum = LimitProductNum(skuList, num, userId);

		if (limitProductNum.getResultMsg() != "200") {
			return "40020";
		}

		// 添加购物车
		int number = customerCartVO.getNumber().intValue();
		String addSkuResult = cartControllerImpl.addSku(userId,
				customerCartVO.getSkuId(), number, req, res);

		if (org.apache.commons.lang3.StringUtils.isBlank(callback)) {
			log.info("end to add sku. return addSkuResult code：" + addSkuResult
					+ ".<normal>");
			return addSkuResult;
		} else {
			log.info("end to add sku. return addSkuResult code：" + addSkuResult
					+ ".<jsonp>");
			return callback + "(" + addSkuResult + ")";
		}
	}

	/**
	 * 获取购物车商品总数量
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = RequestContant.CART_QTY)
	public String getCartQty(HttpServletRequest req) {

		log.info("start to get cart qty ...");

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user == null ? null : user.getUserId();

		// 获取购物车商品数量
		Integer count = cartControllerImpl.getCountQty(userId, req);

		log.info("end to get cart qty. current count:" + count);

		// 返回购物车商品数量
		return String.valueOf(count);
	}

	/**
	 * 获取购物车数据
	 * 
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value = RequestContant.CART_INDEX, method = RequestMethod.POST)
	public String index(Model model, HttpServletRequest req,
			HttpServletResponse response) {

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user == null ? null : user.getUserId();

		log.info("go to cart index...,userId:" + userId);

		// 获取购物车商品等信息
		CartDTO cartDTO = cartControllerImpl.getCart(userId, req, response);

		// 设置图片地址全路径
		setImgUrl4CartDTO(cartDTO);
		if (null != user) {
			model.addAttribute("username", user.getUserName());
		}
		model.addAttribute("cartDTO", cartDTO);
		log.info("go to cart index end,return cart index view.");

		return ResponseContant.CART_MODEL_VIEW;
	}

	/**
	 * 进入购物车首页
	 * 
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(value = RequestContant.CART_INDEX, method = RequestMethod.GET)
	public String indexGet(Model model, HttpServletRequest req,
			HttpServletResponse response) {
		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user == null ? null : user.getUserId();

		log.info("go to cart index...,userId:" + userId);

		// 获取购物车商品等信息
		CartDTO cartDTO = cartControllerImpl.getCart(userId, req, response);

		// 设置图片地址全路径
		setImgUrl4CartDTO(cartDTO);
		if (null != user) {
			model.addAttribute("username", user.getUserName());
		}
		model.addAttribute("cartDTO", cartDTO);
		log.info("go to cart index end,return cart index view.");
		return ResponseContant.CART_INDEX;
	}

	/**
	 * 购物车删除商品
	 * 
	 * @param skuId
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = RequestContant.CART_DELETE, method = RequestMethod.POST)
	public String delete(
			@Validated({ deleteCartCheck.class }) CustomerCartVO customerCartVO,
			BindingResult br, HttpServletRequest req) {

		// 请求参数验证
		checkRequest(br);

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user == null ? null : user.getUserId();

		// 删除购物车商品
		String resultCode = cartControllerImpl.deleteSkus(userId,
				customerCartVO.getSkuIdList(), req);

		// userId和cookieValue同时为空时，需要登录
		if ("503".equals(resultCode)) {
			Map<String, String> loginMsg = new HashMap<String, String>();
			loginMsg.put("status", "needLogin");
			return JSON.toJSONString(loginMsg);
		}

		log.info("skuIds:{}, has deleted.return 200 code.",
				customerCartVO.getSkuIdList());

		return resultCode;
	}

	/**
	 * 购物车更改商品购买数量
	 * 
	 * @param model
	 * @param skuId
	 * @param number
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = RequestContant.CART_UPDATE, method = RequestMethod.POST)
	public String update(
			@Validated({ updateCartCheck.class }) CustomerCartVO customerCartVO,
			BindingResult br, HttpServletRequest req,
			HttpServletResponse response) {

		log.info("start to update sku qty");

		// 请求参数验证
		checkRequest(br);

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user == null ? null : user.getUserId();

		// 获取点击+-后的数量
		int number = customerCartVO.getNumber().intValue();
		// 获取购物车内单个商品多个sku总数量
		ArrayList<Long> skuIdList = new ArrayList<Long>();
		Long skuId = customerCartVO.getSkuId();
		skuIdList.add(skuId);

		List<DealerProductSku> buySkuList = dealerProductSkuService
				.findSkuBySkuIds(skuIdList);
		Long productid = buySkuList.get(0).getProductid();

		CartDTO cartDTO = cartControllerImpl.getCart(userId, req, response);
		int numCart = 0;
		if (cartDTO != null) {
			List<CartSkuDTO> CartSkuList = cartDTO.getSkuList();
			for (CartSkuDTO c : CartSkuList) {
				if (c.getPid().equals(productid)) {
					numCart += c.getQty();
				}
			}
		}
		// 获取当前sku购物车内数量
		int cartSkuIDQty = getCartSkuIDQty(userId, req, response, skuId);
		if (number > cartSkuIDQty) {
			// 判断是否符合限定数量

			CartResultDTO limitProductNum = LimitProductNum(skuIdList, numCart
					- cartSkuIDQty + number, userId);
			if (limitProductNum.getResultMsg() != "200") {
				String jsonString = JsonUtil
						.serializeBeanToJsonString(limitProductNum);
				return jsonString;
			}
		}
		// 执行更新操作
		CartResultDTO cartresultDTO = cartControllerImpl.updateQty(userId,
				customerCartVO.getSkuId(), number, req);
		if (cartresultDTO == null) {
			Map<String, String> loginMsg = new HashMap<String, String>();
			loginMsg.put("status", "needLogin");

			return JSON.toJSONString(loginMsg);
		}

		// 设置图片地址全路径
		// setImgUrl4CartResultDTO(cartresultDTO);

		String jsonString = JsonUtil.serializeBeanToJsonString(cartresultDTO);
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(jsonString);
		} catch (IOException e) {
			log.info("出现错误！消息：" + e.getMessage(), e);
		} finally {
			writer.flush();
			writer.close();
		}

		log.info("end to update sku qty , return jsonStr:" + jsonString);

		// 返回cartDTO json字符串
		return null;
	}

	/**
	 * 购物车计算金额
	 * 
	 * @param customerCartVO
	 * @param br
	 * @param req
	 * @return
	 */
	/*
	 * @RequestMapping(value=RequestContant.CART_CALCULATE) public String
	 * calculate(Model model,@Validated({calculateCheck.class}) CustomerCartVO
	 * customerCartVO,BindingResult br, HttpServletRequest req,
	 * HttpServletResponse response){
	 * 
	 * // 检查请求参数 checkRequest(br);
	 * 
	 * // 获取当前登录用户ID User user = getCurrentUser(req); Long userId = user == null
	 * ? null : user.getUserId();
	 * 
	 * // 计算购物车 cartControllerImpl.select(userId, customerCartVO.getSkuIdList(),
	 * req,response);
	 * 
	 * CartDTO cartDTO = cartControllerImpl.getCart(userId, req, response);
	 * 
	 * if(cartDTO == null){ Map<String, String> loginMsg = new HashMap<String,
	 * String>(); loginMsg.put("status", "needLogin");
	 * 
	 * return JSON.toJSONString(loginMsg); }
	 * 
	 * // 设置图片地址全路径 setImgUrl4CartDTO(cartDTO);
	 * 
	 * model.addAttribute("cartDTO", cartDTO);
	 * 
	 * return ResponseContant.CART_MODEL_VIEW; }
	 */

	/**
	 * 改变选中状态 并返回全部购物车信息
	 * 
	 * @param model
	 * @param customerCartVO
	 * @param br
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CART_CHANGE_SELECT)
	public String changeSelect(Model model, CustomerCartVO customerCartVO,
			BindingResult br, HttpServletRequest req,
			HttpServletResponse response) {

		// 检查请求参数
		checkRequest(br);

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user == null ? null : user.getUserId();

		// 计算购物车
		CartDTO cartDTO = cartControllerImpl.changeSelect(userId,
				customerCartVO.getSkuIdList(), req, response);

		if (cartDTO == null) {
			Map<String, String> loginMsg = new HashMap<String, String>();
			loginMsg.put("status", "needLogin");

			return JSON.toJSONString(loginMsg);
		}

		// 设置图片地址全路径
		setImgUrl4CartDTO(cartDTO);

		model.addAttribute("cartDTO", cartDTO);

		return ResponseContant.CART_MODEL_VIEW;
	}

	/**
	 * 获取需要结算的信息
	 * 
	 * @param model
	 * @param customerCartVO
	 * @param br
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CART_CALCULATE)
	@ResponseBody
	public String select(CustomerCartVO customerCartVO, BindingResult br,
			HttpServletRequest req, HttpServletResponse response) {

		// 检查请求参数
		checkRequest(br);

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user == null ? null : user.getUserId();

		// 计算购物车
		CartDTO cartDTO = cartControllerImpl.select(userId,
				customerCartVO.getSkuIdList(), req, response);

		if (cartDTO == null) {
			Map<String, String> loginMsg = new HashMap<String, String>();
			loginMsg.put("status", "needLogin");

			return JSON.toJSONString(loginMsg);
		}

		// 设置图片地址全路径
		setImgUrl4CartDTO(cartDTO);

		return JsonUtil.serializeBeanToJsonString(cartDTO);
	}

	/**
	 * 结算
	 * 
	 * @param model
	 * @param customerCartVO
	 * @param br
	 * @param req
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CART_SETTLEMENT)
	public String settlement(
			Model model,
			@Validated({ settlementCheck.class }) CustomerCartVO customerCartVO,
			BindingResult br, HttpServletRequest req,
			HttpServletResponse response) {

		// 检查请求参数
		checkRequest(br);

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user == null ? null : user.getUserId();

		// 获取需要结算的信息
		long ExecuteStartTime = System.currentTimeMillis();
		CartDTO cartDTO = cartControllerImpl.select(userId,	customerCartVO.getSkuIdList(), req, response);
		log.info("cartDTO=" + cartDTO);
		long ExecuteEndTime = System.currentTimeMillis();
		log.info("cartControllerImpl.select 的执行时间："
				+ (ExecuteEndTime - ExecuteStartTime) + "毫秒。");
		if (cartDTO == null) {
			log.error("获取需要结算的信息为空！返回购物车首页！");
			return RequestContant.REDIRECT + RequestContant.CART
					+ RequestContant.CART_INDEX;
		}

		// 检查当前用户是否实名认证
		/*
		 * if (!userControllerImpl.checkUserRealName(userId, cartDTO)) {
		 * log.info("[" + user.getUserName() +
		 * "]未进行实名认证，不能结算。跳转实名认证页[settlement]"); String cartIndexUrl =
		 * req.getContextPath() + RequestContant.CART +
		 * RequestContant.CART_INDEX; return RequestContant.REDIRECT +
		 * RequestContant.CUS_INFO + RequestContant.CUS_TO_VERIFY +
		 * "?currentPage=" + cartIndexUrl; }
		 */

		if (!checkBuyQty(cartDTO)) {
			log.info("checkBuyQty return false.返回购物车！");
			return RequestContant.REDIRECT + RequestContant.CART
					+ RequestContant.CART_INDEX;
		}

		if (!checkPrice(cartDTO)) {
			log.info("checkPrice return false.返回购物车！");
			return RequestContant.REDIRECT + RequestContant.CART
					+ RequestContant.CART_INDEX;
		}

		// 设置图片地址全路径
		setImgUrl4CartDTO(cartDTO);

		model.addAttribute("cartDTO", cartDTO);
		model.addAttribute("laiyuanflag", 2);

		// 获取收货地址信息
		ExecuteStartTime = System.currentTimeMillis();
		List<ReceiveAddress> receiveAddressList = addressControllerImpl
				.getReceiveAddressesByUserId(userId);
		ExecuteEndTime = System.currentTimeMillis();
		log.info("addressControllerImpl.getReceiveAddressesByUserId 的执行时间："
				+ (ExecuteEndTime - ExecuteStartTime) + "毫秒。");
		if (receiveAddressList != null && !receiveAddressList.isEmpty()) {
			ReceiveAddress receiveAddress = receiveAddressList.get(0);

			/*
			 * String mobile = receiveAddress.getMobile();
			 * receiveAddress.setMobile
			 * (mobile.replaceAll("(\\d{3})(\\d{4})(\\d{3})", "$1****$3"));
			 */

			model.addAttribute("receiveAddress", receiveAddress);

		}
		model.addAttribute("username", user.getUserName());

		// 返回订单确认页
		return ResponseContant.CONFIRM_ORDER;
	}

	/**
	 * 直接购买
	 * 
	 * @param model
	 * @param customerCartVO
	 * @param br
	 * @param req
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.DIRECT_BUY)
	public String directBudy(Model model,
			@Validated({ directBuyCheck.class }) CustomerCartVO customerCartVO,
			BindingResult br, HttpServletRequest req) {

		// 检查请求参数
		checkRequest(br);

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user == null ? null : user.getUserId();

		log.info("customerCartVO={}", JSONSerializer.toJSON(customerCartVO));
		// 修改地址栏返回错误页面
		ArrayList<Long> skuIds = new ArrayList<Long>();
		skuIds.add(customerCartVO.getSkuId());

		// 红旗宝和激活分区
		String checkFenQu = checkFenQu(skuIds, userId);
		if (checkFenQu.equals("40000")) {// 未激活用户购买非激活区
			return ResponseContant.ERROR_404;
		} else if (checkFenQu.equals("41000")) {// 激活用户购买激活区
			return ResponseContant.ERROR_404;
		}

		// 限购判断
		CartResultDTO limitProductNum = LimitProductNum(skuIds, customerCartVO
				.getNumber().intValue(), userId);
		if (limitProductNum.getResultMsg() != "200") {

			return ResponseContant.ERROR_404;
		}
		// 星级判断
		boolean limitStarsBuy = LimitStarsBuy(skuIds, userId);
		if (!limitStarsBuy) {
			return ResponseContant.ERROR_404;
		}
		skuIds.clear();

		// 获取需要结算的信息
		CartDTO cartDTO = cartControllerImpl.directBuy(userId, customerCartVO,
				req);
		log.info("cartDTO=" + cartDTO);
		if (cartDTO == null) {
			log.error("directBuy获取需要结算的信息为空！返回购物车首页！");
			return RequestContant.REDIRECT + RequestContant.CART
					+ RequestContant.CART_INDEX;
		}

		if (!checkBuyQty(cartDTO)) {
			log.info("checkBuyQty return false.返回购物车！");
			return RequestContant.REDIRECT + RequestContant.CART
					+ RequestContant.CART_INDEX;
		}

		if (!checkPrice(cartDTO)) {
			log.info("checkPrice return false.返回购物车！");
			return RequestContant.REDIRECT + RequestContant.CART
					+ RequestContant.CART_INDEX;
		}

		// 设置图片地址全路径
		setImgUrl4CartDTO(cartDTO);

		model.addAttribute("cartDTO", cartDTO);
		model.addAttribute("laiyuanflag", 1);

		// 获取收货地址信息
		List<ReceiveAddress> receiveAddressList = addressControllerImpl
				.getReceiveAddressesByUserId(userId);
		if (receiveAddressList != null && !receiveAddressList.isEmpty()) {
			ReceiveAddress receiveAddress = receiveAddressList.get(0);
			model.addAttribute("receiveAddress", receiveAddress);
		}

		// 返回订单确认页
		return ResponseContant.CONFIRM_ORDER;
	}

	@ResponseBody
	@AuthPassport
	@RequestMapping(value = RequestContant.CHECK_PRICE)
	public String checkPrice(
			Model model,
			@Validated({ settlementCheck.class }) CustomerCartVO customerCartVO,
			BindingResult br, HttpServletRequest req,
			HttpServletResponse response) {

		// 检查请求参数
		checkRequest(br);

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user == null ? null : user.getUserId();

		// 获取需要结算的信息
		CartDTO cartDTO = cartControllerImpl.select(userId,
				customerCartVO.getSkuIdList(), req, response);

		if (cartDTO == null) {
			log.info("checkPrice cartDTO is null.");
			return "CCIGMALL-40000";
		}

		if (!checkBuyQty(cartDTO)) {
			return "CCIGMALL-30000";
		}

		if (!checkPrice(cartDTO)) {
			return "CCIGMALL-50000";
		}
		List<CartSkuDTO> CartSkuList = cartDTO.getSkuList();

		// 限购
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
			// 红旗宝和激活分区
			String checkFenQu = checkFenQu(skuIds, userId);
			if (checkFenQu.equals("40000")) {// 未激活用户购买非激活区
				return "CCIGMALL-40100";
			} else if (checkFenQu.equals("41000")) {// 激活用户购买激活区
				return "CCIGMALL-41000";
			}

			// 限购判断
			CartResultDTO limitProductNum = LimitProductNum(skuIds,
					hmPqty.get(pid), userId);

			if (limitProductNum.getResultMsg() != "200") {
				return "CCIGMALL-50010";
			}
			// 星级判断
			boolean limitStarsBuy = LimitStarsBuy(skuIds, userId);
			if (!limitStarsBuy) {
				return "CCIGMALL-40010";
			}
			skuIds.clear();
		}

		return "CCIGMALL-20000";
	}

	@ResponseBody
	@RequestMapping(value = RequestContant.CHECK_USER_ACCOUNT_BALANCE)
	public String checkUserAccountBalance(Model model, HttpServletRequest req,
			String skuId, BigDecimal number, HttpServletResponse response) {

		// 获取当前登录用户ID
		User user = getCurrentUser(req);
		Long userId = user == null ? null : user.getUserId();
		if (userId == null) {
			// 无用户登录
			return "10000";
		}
		List<Long> skuList = new ArrayList<Long>();
		skuList.add(skuId == null ? 0L : Long.parseLong(skuId));
		List<DealerProductSkuForPromotionDTO> dealerProductSkuForPromotionDTOList = dealerProductSkuService
				.findProSkuByIdsForPromotion(skuList);
		if (dealerProductSkuForPromotionDTOList == null
				|| dealerProductSkuForPromotionDTOList.size() < 1) {
			log.error("sku异常无法找到相应的产品id，sku:{}", skuId);
			// 系统异常
			return "30000";
		}

		DealerProductSkuForPromotionDTO dealerProductSkuForPromotionDTO = dealerProductSkuForPromotionDTOList
				.get(0);
		// 企业小号不能购买企业自身商品
		User user2 = userService.findUserById(userId);
		if (String.valueOf(dealerProductSkuForPromotionDTO.getSupplierId())
				.equals(user.getSupplierId())
				&& ("1").equals(user2.getSupplierFlag()))
			return "60000";
		log.info("FIRST_BUY_PRODUCT_ID:{}",
				dealerProductSkuForPromotionDTO.getCatePubId());
		List<TdCateDispPub> queryTdCateDispPubListQd = null;
		try {
			queryTdCateDispPubListQd = myCategoryDispService
					.queryTdCateDispPubListQd();
		} catch (Exception e1) {
			log.error("获取展示类目和发布类目失败,user:" + userId, e1);
		}
		Long productid = dealerProductSkuForPromotionDTO.getProductId();
		// 企业小号不能购买家庭号6.7会员企业号不能购买家庭号7.31
		ProductTags jtbtag = productTagsService.LimitContentOrNull(productid,
				"jtb", 4);
		SupplierNumRecordDto supplierNumRecordDto;
		try {
			supplierNumRecordDto = supplierNumRecordService
					.selectRecordByUserid(userId.intValue());
			if (jtbtag != null
					&& (("1").equals(user2.getSupplierFlag()) || supplierNumRecordDto
							.getSupplierNumRecord() != null)) {
				return "40040";
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// 家庭号不能购买自己发布的商品
		Supplier supplier = supplierManagerService
				.findSupplier(dealerProductSkuForPromotionDTO.getSupplierId());

		if (supplier.getUserId() != null && supplier.getUserId().equals(userId))
			return "60001";
		// 会员企业不能购买自己发布的商品

		// 企业小号和购买过家庭号的不能再购买会员企业号7.31
		ProductTags cusbustag = productTagsService.LimitContentOrNull(
				productid, "qyb", 4); 
		try {
			HomeNumRecordDto homeNumRecordDto = homeNumRecordService
					.selectRecordByUserid(userId.intValue());
			if (cusbustag != null
					&& (("1").equals(user2.getSupplierFlag()) || homeNumRecordDto
							.getHomeNumRecord() != null)) {

				return "40050";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 任何用户都能买红旗宝
	/*	ProductTags hqblimit = productTagsService.LimitContentOrNull(
				dealerProductSkuForPromotionDTO.getProductId(), "hqb", 4);
		ProductTags sxblimit = productTagsService.LimitContentOrNull(
				dealerProductSkuForPromotionDTO.getProductId(), "sxb", 4);
		if (hqblimit == null && sxblimit == null) {
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
				if (userAccountStatus != Constants.TWO
						&& productTagsService.LimitContentOrNull(productid,
								"jhb", 4) == null) {
					return "40000";
				}
			} else { // 激活区
				if (userAccountStatus == Constants.TWO) {
					return "41000";
				}
			}
		}*/
		// 星级限定立即购买
		boolean limitStarsBuy = LimitStarsBuy(skuList, userId);
		if (limitStarsBuy != true) {
			return "40010";
		}
		// 立即购买限购
		if (number == null) {
			number = new BigDecimal(1);
		}
		int num = number.intValue();
		CartResultDTO limitProductNum = LimitProductNum(skuList, num, userId);
		if (limitProductNum.getResultMsg() != "200") {
			return "40020";
		}

		return "20000";
	}

	private boolean checkBuyQty(CartDTO cartDTO) {

		List<CartGroupVO> cartGroupVOList = cartDTO.getCartGroupVOList();
		for (CartGroupVO cartGroupVO : cartGroupVOList) {

			List<CartActivityGroupVO> activityGroupList = cartGroupVO
					.getActivityGroupList();
			for (CartActivityGroupVO cartActivityGroupVO : activityGroupList) {

				List<CartSkuDTO> skuList = cartActivityGroupVO.getSkuList();

				for (CartSkuDTO cartSkuDTO : skuList) {
					Integer qty = cartSkuDTO.getQty();

					if (qty == null || qty.intValue() <= 0) {
						return false;
					}
				}
			}
		}

		return true;

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

				// 折扣价
				BigDecimal discountPrice = cartGroupVO.getDiscountPrice();

				// 小计（活动后 + 运费）
				BigDecimal sumPrice2 = cartGroupVO.getSumPrice();

				// 运费
				BigDecimal transferPrice = cartGroupVO.getTransferPrice();

				// 税费
				BigDecimal sumTax = cartGroupVO.getSumTax();

				// 商品金额 = 小计+折扣价-运费-税费
				sumPrice = sumPrice2.add(discountPrice).subtract(transferPrice)
						.subtract(sumTax);

				// sumPrice 单笔订单总价不可以超过2000元
				if (sumPrice.compareTo(new BigDecimal(2000)) > 0) {
					log.info("check price return false,sumPrice:" + sumPrice);
					return false;
				}
				break;
			default:
				continue;
			}
		}

		return true;
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
		/*	if (productTagsService.LimitContentOrNull(
					dealerProductSkuForPromotionDTO.getProductId(), "hqb", 4) == null
					&& productTagsService.LimitContentOrNull(
							dealerProductSkuForPromotionDTO.getProductId(),
							"sxb", 4) == null) {
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
					if (userAccountStatus != Constants.TWO
							&& productTagsService.LimitContentOrNull(
									dealerProductSkuForPromotionDTO
											.getProductId(), "jhb", 4) == null) {
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