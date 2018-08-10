package com.mall.controller.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.redis.JedisManager;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;
import com.mall.customer.service.UserService;
import com.mall.dealer.product.po.DealerProduct;
import com.mall.exception.BadRequestException;
import com.mall.exception.BusinessException;
import com.mall.pay.common.StringUtils;
/*import com.mall.promotion.api.activity.web.GradCouponsApi;
import com.mall.promotion.po.coupon.CouponRule;*/
import com.mall.service.CustomerActivityService;
import com.mall.service.CustomerService;
import com.mall.stock.dto.StockDTO;
import com.mall.stock.dto.StockVO;
import com.mall.utils.CookieUtil;
import com.mall.utils.DateFormatUtil;
import com.mall.utils.StringUtil;
import com.mall.utils.VerifyCodeUtils;
import com.mall.utils.getUUID;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * 用户注册活动类
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/customerActivity")
public class CustomerActivityController extends BaseController{
	private static final Logger log = LoggerFactory.getLogger(CustomerActivityController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerActivityService customerActivityService;
	/*@Autowired
	private GradCouponsApi redPacketActivityService;*/
	private static final String ORIGIN_ZERO = "881";//0元购
	private static final String ORIGIN_HAPPY = "8";//欢购买一赠二
	private static final String ORIGIN_FLOWRATE = "883";//流量宝
	//private static final String ORIGIN_EASYPAYMENT = "884";//易支付
	private static final String ORIGIN_FOCUSMEDIA = "907";//拉钩

	private int[] arr = {101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,
			126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150};
	
	/**
	 * 领克特/亿起发/微信公众号前往领取红包
	 * @param model
	 * @param request
	 * @param response
	 * @param in 910/911/912 -----> 领克特/亿起发/微信公众号
	 * @return
	 */
	@RequestMapping("/toRedPacketThr")
	public String toRedPacketThr(Model model, HttpServletRequest request, HttpServletResponse response, Integer in) {
		log.info("toRedPacketThr request parameter in: " + in);
		
		if(!Arrays.asList(910,911,912).contains(in)){
			throw new BadRequestException("toRedPacketThr request parameter error " + in);
		}
		
		// page view
		String url = "/customer/activity/redPacketThr";
		// set cookie
		setCookie4Thr(in, response);
		
		model.addAttribute("in", in);
		
		return url;
	}
	
	
	@RequestMapping("/redPacketThr")
	@ResponseBody
	public String getRedPacketThr(Model model, HttpServletRequest request, HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		String res = customerActivityService.getRedPacketThr(request, response, model);
		log.info("mobile: " + mobile + "customerActivityService getRedPacket execut end and return:" + res);
		return res;
	}
	
	
	/**
	 * 拉钩100元红包入口
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toRedPacketLaGou")
	public String toRedPacketLaGou(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		model.addAttribute("in", 100);
		
		return  "/customer/activity/laGou";
	}
	
	/**
	 * 拉钩获取红包入口
	 * @param model
	 * @param request
	 * @param response
	 * @param in 入口  20元红包入口
	 * @return
	 */
	@RequestMapping("/toRedPacketByLaGou")
	public String toRedPacketByLaGou(Model model, HttpServletRequest request, HttpServletResponse response, Integer in) {
		
		model.addAttribute("in", in);
		switch (in) {
		case 20:
			return  "/customer/activity/laGou";
	
		default:
			throw new BadRequestException("toRedPacketByLaGou request parameter Exception");
		}
	}
	
	
	@RequestMapping("/readPacketByLaGou")
	@ResponseBody
	public String readPacketByLaGou(Model model, HttpServletRequest request, HttpServletResponse response) {
		String in = request.getParameter("in");
		String sid = getUUID.getSessionId(request, response);
		if("20".equals(in) || "100".equals(in)) {
			
			try {
				memcachedClient.set("userOrigin" + sid, 3600*24, ORIGIN_FOCUSMEDIA);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			
			//获取传过来的验证码
			String captcha = request.getParameter("captcha");
			if (StringUtils.isEmpty(captcha)) {
				log.info("captcha is null ");
				throw new BadRequestException("captcha is null ");
			}
			String piccode_regist = null;
			try {
				piccode_regist = memcachedClient.get("piccode_regist" + sid);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			//比对传入的验证码和缓存中的验证码是否一致
			if(!captcha.equalsIgnoreCase(piccode_regist)) {
				try {
					memcachedClient.delete("piccode_regist" + sid);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				return "502";
			}
			
			String res = customerService.readPacketByFocusMedia(request, response, model,Integer.parseInt(in));
			
			return res;
		} else {
			throw new BadRequestException("readPacketByFocusMedia request parameter exception");
		}
		
	}
	
	/**
	 * 微票 前往获取红包页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toGetRedPacket")
	public String toGetRedPacket(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("to get red packet execute");
		return "customer/activity/microTicket";
	}
	
	/**
	 * 微票 获取红包 注册
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getRedPacket")
	@ResponseBody
	public String getRedPacket(Model model, HttpServletRequest request, HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		String res = customerService.getRedPacket(request, response, model);
		log.info("mobile: " + mobile + "customerService getRedPacket execut end and return:" + res);
		return res;
	}

	/**
	 * 前往燃气推广页
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toNatgas")
	public String toNatgas(Model model, HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		if(StringUtils.isBlank(code)) {
			throw new BadRequestException("request toNatgas code is null ");
		}
		model.addAttribute("code", code);
		return "customer/activity/natgas";
	}
	
	@RequestMapping("/natgasDownLoad")
	public String natgasDownLoad(Model model, HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		String mobile = request.getParameter("mobile");
		String isIos = request.getParameter("isIos");
		if(StringUtils.isBlank(code)) {
			throw new BadRequestException("request natgasDownLoad code is null ");
		}
		if(StringUtils.isBlank(mobile)) {
			throw new BadRequestException("request natgasDownLoad mobile is null ");
		}
		if(StringUtils.isBlank(isIos)) {
			throw new BadRequestException("request natgasDownLoad isIos is null ");
		}
		int parseInt = Integer.parseInt(code);
		boolean flag = false;
		for (int i = 0; i < arr.length; i++) {
			if(parseInt == arr[i]) {
				flag = true;
				break;
			}
		}
		log.info("request natgasDownLoad mobile : " + mobile + " code : " + code + " flag : " + flag + " isIos : " + isIos);
		if(flag) {
			log.info("request natgasDownLoad flag is true");
			try {
				memcachedClient.set("natgasActivity" + mobile, 60*60*24, code);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			return RequestContant.REDIRECT + "http://www.zhongjumall.com/app/download/b2client/downloadPage";
		} else {
			throw new BadRequestException("request natgasDownLoad flag is false");
		}
	}
	
	/**
	 * 0元购前往注册页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toRegistZero")
	public String toRegistZero(Model model, HttpServletRequest request, HttpServletResponse response, String skuId, String number, String pid) {
		validateParamaterByZero(skuId, number);
		validatePid(pid);
		model.addAttribute("skuId", skuId);
		model.addAttribute("number", number);
		model.addAttribute("pid", pid);
		String sid = "";
		sid = CookieUtil.getCookieValue(request, CommonConstant.SESSION_ID);
		if(sid == null) {
			sid = (String) request.getAttribute("cookieValueStr");
		}
		log.info("cookieValueStr is : " + sid);
		try {
			User user = memcachedClient.get(CommonConstant.CUSTOMER_LOGIN + sid);
			if(user != null) {
				
				//判断库存
				List<StockDTO> list = new ArrayList<StockDTO>();
				StockDTO stockDTO = new StockDTO();
				stockDTO.setSkuId(Long.parseLong(skuId));
				stockDTO.setSupplyType("1");
				list.add(stockDTO);
				
				StockVO allStockForArea = RemoteServiceSingleton.getInstance().getStockCustomerService().getAllStockForArea(list, (long)18, (long)424, (long)2977);
				Map<Long, StockDTO> skuStockMap2 = allStockForArea.getSkuStockMap();
				for(Entry<Long, StockDTO> entry : skuStockMap2.entrySet()) {
					Integer qty = entry.getValue().getSpotQty();
					if(qty == 0) {
						return "customer/activity/zeroSkuMessage";
					}
					log.info("sku count is" + qty);
				}
				
				//判断商品上下架
				long longPid = Long.parseLong(pid);
				DealerProduct findB2cIstate = RemoteServiceSingleton.getInstance().getCustomerProductApi().findB2cIstate(longPid);
				Short b2cIsTate = findB2cIstate.getB2cIsTate();
				log.info("product state is" + b2cIsTate);
				if(b2cIsTate != 1) {//非上架
					return "customer/activity/zeroSkuMessage";
				}
				
				//将标示存入缓存，到支付成功页面可依据标示返回活动页
				memcachedClient.set("UserOriginZeroActivity" + user.getUserId(), 2*24*60*60, ORIGIN_ZERO);
				return RequestContant.REDIRECT + "/cart/directBuy";
			}
 		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/customer/activity/registZero";
	}

	
	/**
	 * 0元购注册
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/registZero")
	@ResponseBody
	public String registZero(Model model, HttpServletRequest request, HttpServletResponse response, String mobile, String verificationCode, String password, String skuId, String pid) {
		//验证手机、验证码、密码是否为空
		validateArguments(mobile, verificationCode, password);
		if(skuId == null || "".equals(skuId)) {
			throw new BadRequestException("skuId is null");
		}
		validatePid(pid);
		String coupons = "";
		String skuCount = "";
		String registCoupons = customerService.registCoupons(request, response, ORIGIN_ZERO, mobile, verificationCode, password);
		log.info("registCoupons is" + registCoupons);
		if("200".equals(registCoupons)) {//登陆成功发券
			log.info("going to coupons");
			String sid = getUUID.getSessionId(request, response);
			try {
				User user = memcachedClient.get(CommonConstant.CUSTOMER_LOGIN + sid);
				if(user != null) {
					log.info("registZero coupons execute start **********************************");
					/*String zeroPayActivity = RemoteServiceSingleton.getInstance().getZeroPayActivity().zeroPayActivity(user.getUserId());
					log.info("registZero coupons execute end **********************************");
					log.info("coupons content  is" + zeroPayActivity);
					if(zeroPayActivity != null && zeroPayActivity.equals("0元购优惠券数量不足")) {
						coupons =  "506";
					}*/
				} else {
					log.info("user is null");
					throw new BusinessException("user is null");
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (MemcachedException e) {
				e.printStackTrace();
			}
			
			
			List<StockDTO> list = new ArrayList<StockDTO>();
			StockDTO stockDTO = new StockDTO();
			stockDTO.setSkuId(Long.parseLong(skuId));
			stockDTO.setSupplyType("1");
			list.add(stockDTO);
			
			StockVO allStockForArea = RemoteServiceSingleton.getInstance().getStockCustomerService().getAllStockForArea(list, (long)18, (long)424, (long)2977);
			Map<Long, StockDTO> skuStockMap2 = allStockForArea.getSkuStockMap();
			for(Entry<Long, StockDTO> entry : skuStockMap2.entrySet()) {
				Integer qty = entry.getValue().getSpotQty();
				if(qty == 0) {
					skuCount = "507";
				}
				log.info("sku count is" + qty);
			}
			
			//判断商品上下架
			long longPid = Long.parseLong(pid);
			DealerProduct findB2cIstate = RemoteServiceSingleton.getInstance().getCustomerProductApi().findB2cIstate(longPid);
			Short b2cIsTate = findB2cIstate.getB2cIsTate();
			log.info("product state is" + b2cIsTate);
			if(b2cIsTate != 1) {//非上架
				skuCount = "507";
			}
			
			if("506".equals(coupons) && "507".equals(skuCount)) {
				registCoupons = skuCount;//优惠券和库存都为空走 库存提示页面
			} else if("506".equals(coupons)){//优惠券为空
				registCoupons = coupons;
			} else if("507".equals(skuCount)) {//库存为空
				registCoupons = skuCount;
			}
		}
		String sid = getUUID.getSessionId(request, response);
			User user;
			try {
				user = memcachedClient.get(CommonConstant.CUSTOMER_LOGIN + sid);
				if(user != null) {
					if(registCoupons.equals("506") || registCoupons.equals("200")) {
						memcachedClient.set("UserOriginZeroActivity" + user.getUserId(), 2*24*60*60, ORIGIN_ZERO);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		return registCoupons;
	}
	
	@RequestMapping(value = "/message")
	public String message(Model model, HttpServletRequest request, HttpServletResponse response, String skuId, String number) {
		//验证手机、验证码、密码是否为空
		validateParamaterByZero(skuId, number);
		model.addAttribute("skuId", skuId);
		model.addAttribute("number", number);
		return "customer/activity/zeroMessage";
	}
	
	@RequestMapping(value = "/skuMessage")
	public String skuMessage(Model model, HttpServletRequest request, HttpServletResponse response, String skuId, String number) {
		//验证手机、验证码、密码是否为空
		validateParamaterByZero(skuId, number);
		model.addAttribute("skuId", skuId);
		model.addAttribute("number", number);
		return "customer/activity/zeroSkuMessage";
	}
	
	
	@RequestMapping(value = "/happySkuMessage")
	public String happySkuMessage(Model model, HttpServletRequest request, HttpServletResponse response, String skuId, String number) {
		//验证手机、验证码、密码是否为空
		validateParamaterByZero(skuId, number);
		model.addAttribute("skuId", skuId);
		model.addAttribute("number", number);
		return "customer/activity/happySkuMessage";
	}
	
	/**
	 * 欢购前往注册页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toRegistHappy")
	public String toRegistHappy(Model model, HttpServletRequest request, HttpServletResponse response, String skuId, String number, String pid) {
		return "/customer/activity/registHappy";
	}
	
	/**
	 * 欢购注册
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/registHappy")
	@ResponseBody
	public String registHappy(Model model, HttpServletRequest request, HttpServletResponse response, String mobile, String verificationCode, String password, String skuId, String pid) {
	
		String registCoupons = customerService.registCoupons(request, response, ORIGIN_HAPPY, mobile, verificationCode, password);
		
		if(!"200".equals(registCoupons)){
			log.info("customerService.registCoupons has error:"+registCoupons);
			return registCoupons;
		}
		
		String sid = getUUID.getSessionId(request, response);
		String beforeUrl = "";
		try {
			beforeUrl = memcachedClient.get("beforUrl" + sid);
		} catch (Exception e) {
			log.info("set memcached has error."+e.getMessage(),e);
		}
		
		if(StringUtils.isEmpty(beforeUrl)){
			log.info("registHappy success,user registHappy end.");
			return registCoupons;
		}else{
			log.info("registHappy success,return beforeUrl, user registHappy end.");
			return beforeUrl;
		}
	}
	
	/**
	 * 流量宝去输入手机页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toInputMobileFlowRate")
	public String toInputMobileFlowRate(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		return "/customer/activity/inputMobileFlowRate";
	}
	
	/**
	 * 流量宝去下载页面
	 * @param model
	 * @param request
	 * @param response
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/toDownLoadFlowRate")
	public String toDownLoadFlowRate(Model model, HttpServletRequest request, HttpServletResponse response, String mobile) {
		log.info("mobile is : " + mobile);
		if(mobile == null || "".equals(mobile)) {
			log.info("mobile  is null");
			throw new BadRequestException("mobile  is null");
		}

		try {
			//放入缓存48小时
			memcachedClient.set("flowRate"+mobile, 3600*48, mobile);
			memcachedClient.set("flowRateDate"+mobile, 3600*48, new Date().getTime());
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}
		model.addAttribute("mobile", mobile);
		return "/customer/activity/downLoadFlowRate";
	}
	
	
	/**
	 * 友宝 wap页输入手机号
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toInputMobile")
	public String toInputMobile(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		return "/customer/activity/friendStop";
		
		//return "/customer/activity/friendInputMobile";
	}
	
	
	
	
	@RequestMapping(value = "/toEasyPayment")
	public String toEasyPayment(Model model, HttpServletRequest request, HttpServletResponse response) {
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		
		return "redirect:/customer/toRegister?origin=&returnUrl=";
	}

	/**
	 * 校验是否为空
	 * @param mobile 手机
	 * @param verificationCode 验证码
	 * @param password 密码
	 */
	private void validateArguments(String mobile, String verificationCode, String password) {
			
		if(StringUtils.isEmpty(mobile) ||StringUtils.isEmpty(password) || StringUtils.isEmpty(verificationCode)){
			log.info("bad request,username or password or verificationCode is empty..");
			throw new BadRequestException("login fail");
		}
	}
	
	private void validateParamaterByZero(String skuId, String number) {
		if(skuId == null || "".equals(skuId)) {
			throw new BadRequestException("skuId  is null ");
		}
		if(number == null || "".equals(number) || "0".equals(number)) {
			throw new BadRequestException("number  error ");
		}
	}
	
	private void validatePid(String pid) {
		if(pid == null || "".equals(pid)) {
			throw new BadRequestException("productId  is null ");
		}
	}
	
	@RequestMapping(value = "/testFriend")
	public String testFriend(Model model, HttpServletRequest request, HttpServletResponse response) {

		return null;
	}
	
//	@RequestMapping(value = "/giveNiuBi")
//	@ResponseBody
//	public String giveNiuBi(Model model, HttpServletRequest request, HttpServletResponse response,String mobile,Short platform) {
//
//		log.info("订单调用，开始送牛币。。。");
//		if(StringUtils.isBlank(mobile) || platform == null){
//			log.info("订单调用，请求参数不正确！return failed.");
//			return "failed";
//		}
//		
//		CustomerService.giveNiuBi(mobile, 2, platform);
//		log.info("订单调用结束。");
//		
//		return "success";
//	}
	
	/**
	 * 活动存入cookie
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/originCookie")
	@ResponseBody
	public String originCookie(Model model, HttpServletRequest request, HttpServletResponse response, String callback) {
		String origin = request.getParameter("origin");
		log.info("originCookie request origin value is : " + origin);
		log.info("originCookie request callback value is : " + callback);
		if(origin == null || "".equals(origin)) {
			throw new BadRequestException("origin is null");
		}
		int parseInt = Integer.parseInt(origin);
		switch (parseInt) {
		case 8:
			CookieUtil.setCookie(response, CommonConstant.ORIGIN_COOKIE_NAME, origin, 60*60*24);
			break;
		case 903:
			CookieUtil.setCookie(response, CommonConstant.ORIGIN_COOKIE_NAME, origin, 60*60*24);
			break;
		default:
			throw new BadRequestException("originCookie request parameter error");
		}
		if(StringUtils.isBlank(callback)) {
			return "success";
		} else {
			return callback+"('" +"success"+ "')"; 
		}
	}
	
	@RequestMapping(value = "/toRedPacket")
	public String toRedPacket(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		log.info("----->> to red packet .");
		String wapType=request.getParameter("type");
		model.addAttribute("wapType", wapType);
		return "/customer/activity/receivePacket";
	}
	
	/**f
	 * 通过手机号进行红包发放
	 * @param model
	 * @param request
	 * @param response
	 * @param mobile
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/redPacketActivity")
	public String redPacketActivity(Model model, HttpServletRequest request, HttpServletResponse response, String mobile,String imgCode) {
		log.info("mobile is : " + mobile);
		/*if(StringUtils.isBlank(mobile)) {
			log.info("mobile  is null");
			throw new BadRequestException("mobile  is null");
		}
		if(StringUtils.isBlank(imgCode)) {
			log.info("imgCode  is null");
			throw new BadRequestException("imgCode  is null");
		}
		//如果判断结果大于0，则表示时间未到10点
		if(DateFormatUtil.getDifferenceByTen()>0){
			log.info("---------红包活动----------->>>>红包开抢时间未到");
			return "502";
		}
		try {
			//获取客户端地址
			String clentIp=StringUtil.getIpAddr(request);
			log.info("---------红包活动----------->>>>本地客户端IP===="+clentIp);
			String cachePhone=memcachedClient.get("redPacket"+mobile);
			String sid = getUUID.getSessionId(request, response);
			String cacheImg=memcachedClient.get("red_regist"+sid);
			if(StringUtils.isBlank(cacheImg)){
				throw new BadRequestException("cacheImg is null");
			}
			if(!cacheImg.toLowerCase().equals(imgCode.trim().toLowerCase())){
				log.info("---------红包活动----------->>>>验证码输入错误====");
				return "500";
			}
			//判断该手机号或者该IP是否已经领取过红包
			if(!StringUtils.isBlank(cachePhone) ||(StringUtils.isBlank(cacheIP))){
				//已经领取过红包，返回页面并给与提示 、弹出提示
				log.info("---------红包活动----------->>>>该手机号已经在缓存中存在===="+cachePhone);
				//log.info("---------红包活动----------->>>>该IP地址已经在缓存中存在==="+cacheIP);
				log.info("---------红包活动----------->>>>该手机号或者该IP是否已经领取过红包");
				return "-2";
			}else{
				//调用红包接口 返回值为用户抢到的红包金额或者已经抢完了的提示信息 -1:未登录，0:红包已抢光，明天再来，-2:今天已领过，明天再来
				Integer flg = redPacketActivityService.gradCouponsByUserId(Long.parseLong(mobile));
				log.info("----------红包活动---------->>>>接口返回数据为：==="+flg);
				switch (flg) {
				case 0:
					log.info("----------红包活动---------->>>>0:红包已抢光");
					return "0";
				case -2:
					log.info("----------红包活动---------->>>>-2:今天已领过，明天再来");
					return "-2";
				default:
					//将手机号放入缓存中，便于该手机号再次领取红包时进行判断 ，该手机号和IP在系统缓存中存放的时间为00:00清空
					memcachedClient.set("redPacket"+mobile, DateFormatUtil.getDifference(), mobile);
					memcachedClient.set("clentIp"+clentIp, DateFormatUtil.getDifference(), clentIp);
					log.info("----------红包活动---------->>>>成功抢到"+flg+"红包成功");
					return flg.toString();
				}
			}
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}*/
		return "0";
	}
	
	/**
	 * 前往力美活动页
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/toForceBeauty")
	public String toForceBeauty(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("toForceBeauty execute ..................");
		return "/customer/activity/forceBeauty";
	}
	
	/**
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/toForceBeautyDownLoad")
	public String toForceBeautyDownLoad(Model model, HttpServletRequest request, HttpServletResponse response, String mobile) {
		if(mobile == null || "".equals(mobile)) {
			log.info("mobile  is null");
			throw new BadRequestException("mobile  is null");
		}

		try {
			//放入缓存48小时
			memcachedClient.set("forceBeautyActivity"+mobile, 3600*48, mobile);
		//	memcachedClient.set("forceBeautyActivityDate"+mobile, 3600*48, new Date().getTime());
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}
		model.addAttribute("mobile", mobile);
		return "/customer/activity/forceBeautyDownLoad";
	}
	
	@RequestMapping(value="/getImageRegist")
	public void getImageRegist(HttpServletRequest request, HttpServletResponse response) throws Exception{
	    response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
          
        //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(5);  
     
        String sid = getUUID.getSessionId(request, response);
		memcachedClient.set("red_regist"+sid,60*10, verifyCode.toLowerCase());
        //生成图片  
        int w = 230, h = 80;  
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);  
	}
	
	private void setCookie4Thr(Integer in, HttpServletResponse response){
		String key = "c_r_";
		String value = "";
		
		switch (in) {
		case 910:
			value = "lingkete";
			break;
		case 911:
			value = "yiqifa";
			break;
		case 912:
			value = "wxpublicNumber";
			break;
		}
		
		CookieUtil.setCookie(response, key, value, CommonConstant.ORIGIN_MAX_AGE);
	}
	
	/**f
	 * 通过手机号进行红包发放
	 * @param model
	 * @param request
	 * @param response
	 * @param mobile
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/redPacketActivityPC")
	public String redPacketActivityPC(Model model, HttpServletRequest request, HttpServletResponse response, String mobile,String code) {
		log.info("mobile is : " + mobile);
		if(StringUtils.isBlank(mobile)) {
			log.info("mobile  is null");
			throw new BadRequestException("mobile  is null");
		}
		if(StringUtils.isBlank(code)) {
			log.info("code  is null");
			throw new BadRequestException("code  is null");
		}
		//如果判断结果大于0，则表示时间未到10点
		if(DateFormatUtil.getDifferenceByTen()>0){
			log.info("---------红包活动----------->>>>红包开抢时间未到");
			return "-502";
		}
		try {
			//获取客户端地址
			/*String clentIp=StringUtil.getIpAddr(request);
			log.info("---------红包活动----------->>>>本地客户端IP===="+clentIp);
			String cachePhone=memcachedClient.get("redPacket"+mobile);
			String sid = getUUID.getSessionId(request, response);
			//判断手机号码和手机验证码
			Integer msgCode = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE
					+ mobile);
			String memMobile = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE
					+ mobile + "mobile");
			if(!mobile.equals(memMobile)||!code.equals(msgCode + "")){
				log.info("---------红包活动----------->>>>该手机号对应验证码错误===="+cachePhone);
				return "-500";//验证码错误
			}*/
			
			
			//判断该手机号或者该IP是否已经领取过红包
			//if(!StringUtils.isBlank(cachePhone)/* ||(StringUtils.isBlank(cacheIP))*/){
				//已经领取过红包，返回页面并给与提示 、弹出提示
			//	log.info("---------红包活动----------->>>>该手机号已经在缓存中存在===="+cachePhone);
				//log.info("---------红包活动----------->>>>该IP地址已经在缓存中存在==="+cacheIP);
			//	log.info("---------红包活动----------->>>>该手机号或者该IP是否已经领取过红包");
			//	return "-2";
			//}else{
				//调用红包接口 返回值为用户抢到的红包金额或者已经抢完了的提示信息 -1:未登录，0:红包已抢光，明天再来，-2:今天已领过，明天再来
			    //删除取卷缓存
			   /* String userKey = "gotCoupons";
				JedisManager.delObject(userKey);
				//取卷
				Integer flg = redPacketActivityService.gradCouponsByUserId(Long.parseLong(mobile));
				log.info("----------红包活动---------->>>>接口返回数据为：==="+flg);
				switch (flg) {
				case 0:
					log.info("----------红包活动---------->>>>0:红包已抢光");
					return "0";
				//case -2:
				//	log.info("----------红包活动---------->>>>-2:今天已领过，明天再来");
				//	return "-2";
				default:
					//将手机号放入缓存中，便于该手机号再次领取红包时进行判断 ，该手机号和IP在系统缓存中存放的时间为00:00清空
					//memcachedClient.set("redPacket"+mobile, DateFormatUtil.getDifference(), mobile);
					//memcachedClient.set("clentIp"+clentIp, DateFormatUtil.getDifference(), clentIp);
					log.info("----------红包活动---------->>>>成功抢到"+flg+"红包成功");
					return flg.toString();
				}*/
			//}
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}

		return "0";
	}
	
	
}
