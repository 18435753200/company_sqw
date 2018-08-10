package com.mall.controller.qrCode;

import com.mall.constant.CommonConstant;
import com.mall.constant.ResponseContant;
import com.mall.constant.ThirdAccountConstant;
import com.mall.controller.base.BaseController;
import com.mall.controller.impl.CartControllerImpl;
import com.mall.controller.myccigmall.CustomerOrderPayController;
import com.mall.customer.model.BizRcode;
import com.mall.customer.model.CashAccount;
import com.mall.customer.model.User;
import com.mall.customer.model.WxUser;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.CartDTO;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.po.CreateFalseOrder;
import com.mall.customer.service.CashAccountService;
import com.mall.customer.service.PsqwAccountRecordService;
import com.mall.customer.service.UserService;
import com.mall.exception.BadRequestException;
import com.mall.pay.common.StringUtils;
import com.mall.pay.dto.PayResult;
import com.mall.service.CusOrderPayService;
import com.mall.service.CusOrderService;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierSalesDiscount;
import com.mall.supplier.service.SupplierDiscountService;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.utils.JsonUtil;
import com.mall.utils.Oauth;
import com.mall.utils.getUUID;
import com.mall.vo.WeiXinGZHVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/Qr")
public class QrCodeContorller extends BaseController {
	private static final Logger log = LoggerFactory
			.getLogger(CustomerOrderPayController.class);
	// MP对应的type值
	public static final Integer SUPPLIERTYPEMP = 1650;
	// 支付先关的服务
	@Autowired
	private CusOrderPayService cusOrderPayService;
	// 订单相关服务
	@Autowired
	private CusOrderService cusOrderService;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomerOrderService customerOrderService;
	@Autowired
	private PsqwAccountRecordService psqwAccountRecordService;
	@Autowired
	private SupplierDiscountService supplierDiscountService;
	@Autowired
	private CartControllerImpl cartControllerImpl;
	@Autowired
	private SupplierManagerService supplierManagerService;
	@Autowired
	private CashAccountService cashAccountService;


	/**
	 * 不是微信浏览器,提示使用微信
	 */
	@RequestMapping("/isNotWx")
	public String isNotWx(Model model) {
		model.addAttribute("message", "请使用微信或支付宝打开");
		return "/qrcode/notVisit";
	}
	@RequestMapping("/isNoWx")
	public String isNoWx(Model model) {
		model.addAttribute("message", "请使用微信扫描二维码");
		return "/qrcode/notVisit";
	}

	/**
	 * 扫码跳转分享页面(注册C)
	 */
	@RequestMapping("/toCregister")
	public String toCregister(Model model, String rcid, String openId) {
		// 获取分享人二维码信息
		if (rcid != null && !"".equals(rcid)) {
			BizRcode getMallUser = userService.findBzRcodeByRcodeid(rcid);
			if (getMallUser.getBizid() != null
					&& !"".equals(getMallUser.getBizid())
					&& getMallUser.getRcodetype() == 0) {
				User user = getMallUser == null ? null : userService
						.findUserById(Long.parseLong(getMallUser.getBizid()));
				model.addAttribute("userId", user.getUserId());
				model.addAttribute("mobile", user.getMobile());
				model.addAttribute("openId", openId);
			}
		}
		return "/qrcode/c_register";
	}

	/**
	 * 是用微信扫码
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/isWxScan")
	public String isWxScan(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isWeiXin = request.getParameter("isWeiXin");
		if ("true".equals(isWeiXin)) {
			String code = null;
			String openId = null;
			// 微信用户授权接口所需
			code = request.getParameter("code");
			// code = "asdfghj";
			if (StringUtils.isBlank(code)) {
				log.info("用户请求的 code 没值,需要授权");
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
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject
					.fromObject(result);
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
			String sid = getUUID.getSessionId(request, response);
			// 跳转到登陆前的链接地址
			String beforeUrl = memcachedClient.get("beforUrl" + sid);
			return ResponseContant.REDIRECT + beforeUrl + "&openId=" + openId;
		}
		return null;
	}

	/**
	 * 跳转二维码页面 rcodeType字段 0：拓展用户，1：拓展商家，2：收款，3：礼品券商城，4：折扣券商城，5：现金商城
	 * 
	 * @return
	 */
	@RequestMapping("/qrCode")
	public String qrCode(Model model, HttpServletRequest request,
			Integer rcodeType) {
		User user = getCurrentUser(request);
		if (user == null || "".equals(user)) {
			return ResponseContant.LOGIN;
		} /*
		 * else{
		 * if(user.getSupplierId()=="0"||"0".equals(user.getSupplierId())){
		 * return "该用户不是商家"; } }
		 */
		model.addAttribute("rcodeType", rcodeType);
		model.addAttribute("userId", user.getUserId());
		model.addAttribute("sId", user.getSupplierId());
		return "/qrcode/qrCode";
	}

	/**
	 * 商户中心收款跳转二维码页面 rcodeType字段 0：拓展用户，1：拓展商家，2：收款，3：礼品券商城，4：折扣券商城，5：现金商城
	 * 
	 * @return
	 */
	@RequestMapping("/shqrCode")
	public String shqrCode(Model model, HttpServletRequest request,
			Integer rcodeType) {
		User user = getCurrentUser(request);
		if (user == null || "".equals(user)) {
			return ResponseContant.LOGIN;
		} /*
		 * else{
		 * if(user.getSupplierId()=="0"||"0".equals(user.getSupplierId())){
		 * return "该用户不是商家"; } }
		 */
		model.addAttribute("rcodeType", 2);
		model.addAttribute("userId", user.getUserId());
		model.addAttribute("sId", user.getSupplierId());
		return "/qrcode/shqrCode";
	}

	/**
	 * 二维码页面回调
	 * 
	 * @param bizid
	 *            ,rcodeType 用户Id,二维码类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createSelectQr")
	public BizRcode createSelectQr(String bizid, Integer rcodeType) {
		BizRcode bizRcode = new BizRcode();
		if (!"".equals(rcodeType) && null != rcodeType) {
			// 0：拓展用户，1：拓展商家，2：收款，3：礼品券商城，4：折扣券商城，5：现金商城
			if (rcodeType == 0 || rcodeType.equals(0)) {
				bizRcode = userService.myRcodeByFortoUser(bizid);
			} else if (rcodeType == 1 || rcodeType.equals(1)) {
				bizRcode = userService.myRcodeByFortoM(bizid);
			} else if (rcodeType == 2 || rcodeType.equals(2)) {
				bizRcode = userService.myRcodeByFortoPay(bizid);
			} else if (rcodeType == 3 || rcodeType.equals(3)) {
				bizRcode = userService.myRcodeByFortoPresent(bizid);
			} else if (rcodeType == 4 || rcodeType.equals(4)) {
				bizRcode = userService.myRcodeByFortoDiscount(bizid);
			} else if (rcodeType == 5 || rcodeType.equals(5)) {
				bizRcode = userService.myRcodeByFortoCash(bizid);
			} else if (rcodeType == 6 || rcodeType.equals(6)) {
				bizRcode = userService.getAddCashierRcordBySupplierId(Long.valueOf(bizid));
			}
		}
		return bizRcode;
	}

	/**
	 * 扫码跳转商品页面
	 */
	@RequestMapping("/toMall")
	public String toMall(Model model, String rcid, WxUser wxUser,
			String wxUserId, BigDecimal getBalance, Boolean isLogin,
			HttpServletRequest request, HttpServletResponse response,
			Boolean register) {
		// 获取商户信息
		BizRcode getUser = userService.findBzRcodeByRcodeid(rcid);
		// 获取当前登录用户ID
		User user = getCurrentUser(request);
		Long userId = user == null ? null : user.getUserId();

		// 获取购物车商品等信息
		CartDTO cartDTO = cartControllerImpl.getCart(userId, request, response);
		return "/qrcode/qrCodeCart";
	}

    /**
     * 扫码跳转付款页面
	 * @param rcid	二维码id
     */
    @RequestMapping("/toPay")
    public String toPay(Model model, String rcid, HttpServletRequest request) {

        // 获取二维码信息
        BizRcode rcode = userService.findBzRcodeByRcodeid(rcid);
        User currentUser = getCurrentUser(request);
        if (currentUser == null) {
            if (rcode != null) {
                if (StringUtils.isNotEmpty(rcode.getRcodetxt()) && StringUtils.isNotEmpty(rcode.getBizid())) {
                    String agent = request.getHeader("User-Agent");

                    if (agent.contains("MicroMessenger")) {

                        return ResponseContant.REDIRECT + "/customer/userToPay?isWeiXin=true&isUser=true";
                    } else if (agent.contains("AlipayClient")) {

                        return ResponseContant.REDIRECT + "/customer/thirdAuthorize?openType=alipay";
                    }

                    return ResponseContant.REDIRECT + "/Qr/isNotWx";  // 消费支付登陆
                } else if (StringUtils.isNotEmpty(rcode.getRcodetxt()) && StringUtils.isEmpty(rcode.getBizid())) {

                    return ResponseContant.LOGIN; //入驻商家登陆
                }
                return ResponseContant.MPORMALL; //mp 激活登陆
            }
        } else {
            if (rcode != null) {
                if (StringUtils.isNotEmpty(rcode.getRcodetxt()) && StringUtils.isNotEmpty(rcode.getBizid())) {
                    // 消费支付登陆
                    if (StringUtils.isEmpty(currentUser.getWeixin())) {
						String agent = request.getHeader("User-Agent");

						if (agent.contains("MicroMessenger")) {

							return ResponseContant.REDIRECT + "/customer/userToPay?isWeiXin=true&isUser=true";
						} else if (agent.contains("AlipayClient")) {

							return ResponseContant.REDIRECT + "/customer/thirdAuthorize?openType=alipay";
						}

						return ResponseContant.REDIRECT + "/Qr/isNotWx";
                    }
                }
            }
        }
        if (rcode != null) {
            // 商家入住页面
            if (rcode.getBizid() == null || "".equals(rcode.getBizid())) {
                //二维码绑定MP页面
                if (rcode.getRcodetxt() == null || "".equals(rcode.getRcodetxt())) {
                    if (currentUser.getType() != SUPPLIERTYPEMP) {
                        model.addAttribute("message", "众聚猫市场合作伙伴,请用市场合作伙伴帐号登录激活二维码!");
                        return "/qrcode/jhewm";
                    }
                    if (currentUser.getSupplierId() != null && !"".equals(currentUser.getSupplierId()) && Long.parseLong(currentUser.getSupplierId()) > 0) {
                        Supplier supplier = supplierManagerService.findSupplier(Long
                                .parseLong(currentUser.getSupplierId()));
                        if (supplier != null) {
                            if (!(supplier.getActiveStatus() == 1 && supplier.getStatus() == 1)) {
                                model.addAttribute("message", "您的众聚猫市场合作伙伴账号,正在审核！");
                                return "/qrcode/notVisit";
                            }
                            model.addAttribute("rcode", rcode);
                            return "/qrcode/jhewm";
                        } else {
                            model.addAttribute("message", "众聚猫市场合作伙伴,请用市场合作伙伴帐号登录激活二维码！");
                            return "/qrcode/notVisit";
                        }
                    } else {
                        model.addAttribute("message", "众聚猫市场合作伙伴,请用市场合作伙伴帐号登录激活二维码！");
                        return "/qrcode/notVisit";
                    }
                } else {
                    if (currentUser.getSupplierId() != null) {
                        if (Long.parseLong(currentUser.getSupplierId()) > 0) {
                            model.addAttribute("message", "您不能入驻为众聚猫合作商家!");
                            return "/qrcode/sjrz";
                        }

                        List<Supplier> supplierList = supplierManagerService
                                .findSuppliersByUserIdAndSupplierId(
                                        currentUser.getUserId(), 1);
                        if (supplierList != null && supplierList.size() >= 1) {
                            model.addAttribute("message", "您已经是众聚猫合作商家！");
                            return "/qrcode/sjrz";
                        }
                    }
                    model.addAttribute("rcode", rcode);
                    return "/qrcode/sjrz";
                }
            }
        } else {
            model.addAttribute("message", "二维码无效！");
            return "/qrcode/notVisit";
        }
        Supplier supplier = supplierManagerService.findSupplier(Long
                .parseLong(rcode.getBizid()));
        List<CashAccount> ruwangList = cashAccountService
                .findCashAccountListByUserId(supplier.getUserId().intValue());
        if (supplier.getStatus() == 5) {
            model.addAttribute("message", "该商家已被冻结！");
            return "/qrcode/notVisit";
        }
        if (supplier.getWithdrawStatus() != 1) {
            model.addAttribute("message", "当前二维码暂不可使用！");
            return "/qrcode/notVisit";
        }
        if (ruwangList.size() < 1) {
            model.addAttribute("message", "当前商户未入网！");
            return "/qrcode/notVisit";
        }
        // 二维码id
        model.addAttribute("rcid", rcid);
        if (supplier.getActiveStatus() != 1 || supplier.getStatus() != 1) {
            model.addAttribute("message", "当前商户未通过审核");
            return "/qrcode/notVisit";
        }

        model.addAttribute("userId", currentUser.getUserId());
        model.addAttribute("supplier", supplier);
        model.addAttribute("openId", currentUser.getWeixin());
		model.addAttribute("qrCodeId", rcid);

		log.info("扫描二维码ID：{},userid: {}", rcid, currentUser.getUserId());

        //登录类型
        if (currentUser.getloginType() == 1) {
            model.addAttribute("payType", ThirdAccountConstant.ACCOUNT_WEIXIN);
        } else if (currentUser.getloginType() == 2) {
            model.addAttribute("payType", ThirdAccountConstant.ACCOUNT_ALIPAY);
        }

        return "/qrcode/pay_jp";
    }

	/**
	 * 查询折扣后需支付的券与现金 入参：商家Id
	 */
	@ResponseBody
	@RequestMapping("/selectDiscount")
	public Map<String, Object> selectDiscount(Long shopUserId, BigDecimal price) {
		BigDecimal hqjPrice = new BigDecimal(0);
		BigDecimal cashPrice = new BigDecimal(0);
		SupplierSalesDiscount supplierDiscount = supplierDiscountService
				.findBySupplierId(shopUserId);
		if (!"".equals(supplierDiscount) && supplierDiscount != null) {
			BigDecimal salesDiscount = supplierDiscount.getSalesDiscount();
			cashPrice = price.multiply(salesDiscount);
			hqjPrice = price.subtract(cashPrice);

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hqjPrice", hqjPrice);
		map.put("cashPrice", cashPrice);
		return map;
	}

	/**
	 * 生成订单
	 */
	@ResponseBody
	@RequestMapping("/createOrder")
	public void createOrder(CreateFalseOrder createFalseOrder, Long orderId,
			String openId, String nonceStr, String timestamp, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		// 获取当前登录用户ID
		User user = getCurrentUser(request);
		Long userId = user == null ? null : user.getUserId();
		if (userId != null && !"".equals(userId)) {
			createFalseOrder.setUserId(userId);
		}

		createFalseOrder.setOrderId(orderId);
		createFalseOrder.setType(45);
		Short status = 1;
		createFalseOrder.setStatus(status);
		createFalseOrder.setPayPrice(createFalseOrder.getPrice());
		try {
			customerOrderService.createFalseOrder(createFalseOrder);
		} catch (Exception e) {
			System.out.println("生成订单出错了");
			e.printStackTrace();
		}
		// 券加现金付
		if (createFalseOrder.getHqjPrice() == new BigDecimal(0)) {
			createFalseOrder.getShopUserId();
			try {
				psqwAccountRecordService.payOrderByCertificate(createFalseOrder
						.getUserId(), createFalseOrder.getShopUserId()
						.intValue(), orderId, 46, createFalseOrder
						.getHqjPrice());
			} catch (Exception e) {
				System.out.println("支付券出错了");
				e.printStackTrace();
			}

			// customerOrderPayController.toWeiXinGZHPay(orderId, openId,
			// nonceStr, timestamp, model, request, response);
		}

		log.info("toPay execute....");
		if (orderId == null) {
			log.error("orderId is null");
			throw new BadRequestException("call toPay errror ");
		}

		/*
		 * 逻辑
		 */
		if (user != null && user.getloginType() == 1) {

			OrderDTO findOrderByOrderId = cusOrderService.findOrderByOrderId(
					orderId, user);

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
				PayResult pay = cusOrderPayService.weiXinGZHPay(orderId,
						openId, nonceStr, timestamp,
						CommonConstant.PAY_WAP_CHANNELNO, user, request,
						response, model, CommonConstant.WEIXIN_GZH_PAY);
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

	}

}
