package com.mall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.mall.annotation.AuthPassport;
import com.mall.constant.*;
import com.mall.controller.base.BaseController;
import com.mall.controller.impl.UserControllerImpl;
import com.mall.customer.dto.HomeNumRecordDto;
import com.mall.customer.dto.activity.SupplierNumRecordDto;
import com.mall.customer.model.CashierRecord;
import com.mall.customer.model.SqwUserAccountBalance;
import com.mall.customer.model.User;
import com.mall.customer.model.WxUser;
import com.mall.customer.service.*;
import com.mall.exception.BadRequestException;
import com.mall.pay.common.StringUtils;
import com.mall.service.CustomerService;
import com.mall.service.WealthService;
import com.mall.utils.*;
import com.mall.vo.WealthVO;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * 用户
 * 
 * @author ccigQA01
 */
@Controller
@RequestMapping(value = RequestContant.CUSTOMER)
public class CustomerController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserControllerImpl userControllerImpl;

	@Autowired
	private SqwAccountRecordService sqwAccountRecordService;

	@Autowired
	private CashierService cashierService;

	@Autowired
	private UserService userService;
	@Autowired
	private WealthService wealthService;

	@Autowired
	private HomeNumRecordService homeNumRecordService;

	@Autowired
	private SupplierNumRecordService supplierNumRecordService;

	@Autowired
	private PsqwAccountRecordService psqwAccountRecordService;


	@Autowired
	private AliServiceWindow aliServiceWindow;

	private static final String ORIGIN1 = "1";// 微信
	private static final String ORIGIN2 = "2";// 微博
	private static final String ORIGIN3 = "3";// 中国银行
	private static final String ORIGIN4 = "4";// 流量宝
	
	private static final String LOW_PAGE = "https://m.zhongjumall.com/act/xinrendiduan.html";// 低端
	private static final String CENTER_PAGE = "https://m.zhongjumall.com/act/xinrenzhongduan.html";// 中端
	private static final String HIGH_PAGE = "https://m.zhongjumall.com/act/xinrengaoduan.html";// 高端
	
	/**
	 * 用户协议
	 */
	@RequestMapping("/notes")
	public String notes() {
		return "customer/register_notes";

	}

	/**
	 * 去活动页面注册发券
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param origin
	 * @return
	 */
	@RequestMapping(value = "toRegistCoupons")
	public String toRegistCoupons(Model model, HttpServletRequest request, HttpServletResponse response,
			String origin) {
		customerService.validateOrigin(model, origin);
		return "/customer/registCoupons";
	}

	/**
	 * 活动注册获取注册请求码 需要验证用户是否存在
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getRegistCode")
	@ResponseBody
	public String getRegistCode(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("getRegCode execute..");
		String res = customerService.getRegCodeActivity(model, request, response, true);
		return res;
	}

	/**
	 * 活动注册获取注册请求码 不需要验证用户是否存在
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getCode")
	@ResponseBody
	public String getCode(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("request getCode execute..");
		String res = customerService.getRegCodeActivity(model, request, response, false);
		return res;
	}

	/**
	 * 去子活动页面
	 * 
	 * @param request
	 * @param response
	 * @param origin
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toSubActivityPage")
	public String toSubActivityPage(HttpServletRequest request, HttpServletResponse response, String origin,
			Model model) {
		customerService.validateOrigin(model, origin);
		if (origin.equals(ORIGIN1)) {// 微信中端
			return ResponseContant.REDIRECT + CENTER_PAGE;
		} else if (origin.equals(ORIGIN2)) {// 微博中端
			return ResponseContant.REDIRECT + CENTER_PAGE;
		} else if (origin.equals(ORIGIN3)) {// 中国银行高端
			return ResponseContant.REDIRECT + HIGH_PAGE;
		} else {// 流量宝低端
			return ResponseContant.REDIRECT + LOW_PAGE;
		}
	}

	/**
	 * 去总活动页
	 * 
	 * @param request
	 * @param response
	 * @param origin
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toTotalActivityPage")
	public String toTotalActivityPage(HttpServletRequest request, HttpServletResponse response, String origin,
			Model model) {
		customerService.validateOrigin(model, origin);
		return "/customer/totalActivityPage";
	}

	/**
	 * 第三方来源注册送券
	 * 
	 * @param request
	 * @param response
	 * @param origin
	 *            来源
	 * @param mobile
	 *            手机
	 * @param verificationCode
	 *            验证码
	 * @param password
	 *            密码
	 * @return
	 */
	@RequestMapping(value = "/registCoupons")
	@ResponseBody
	public String registCoupons(HttpServletRequest request, HttpServletResponse response, String origin, String mobile,
			String verificationCode, String password) {
		// 验证手机、验证码、密码是否为空
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(verificationCode)) {
			log.info("bad request,username or password or verificationCode is empty..");
			throw new BadRequestException("login fail");
		}

		String registCoupons = customerService.registCoupons(request, response, origin, mobile, verificationCode,
				password);
		return registCoupons;
		/*
		 * if(!"200".equals(registCoupons)){//登陆不成功 log.info(
		 * "login has error,user login end."); return registCoupons; } String
		 * sid = getUUID.getSessionId(request, response); String beforeUrl = "";
		 * try { beforeUrl = memcachedClient.get("beforUrl" + sid); } catch
		 * (Exception e) { log.info("set memcached has error."
		 * +e.getMessage(),e); return "500"; }
		 * 
		 * if(StringUtils.isEmpty(beforeUrl)){ log.info(
		 * "login success,user login end."); return registCoupons; }else{
		 * log.info("login success,return beforeUrl, user login end."); return
		 * beforeUrl; }
		 * 
		 * return null;
		 */
	}

	@RequestMapping(value = "/getImage")
	public void getImage(HttpServletRequest request, HttpServletResponse response) throws Exception {

		BufferedImage img = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		Random r = new Random();
		Color c = new Color(200, 150, 255);
		g.setColor(c);
		g.fillRect(0, 0, 68, 22);
		StringBuffer sb = new StringBuffer();
		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		int index, len = ch.length;
		for (int i = 0; i < 4; i++) {
			index = r.nextInt(len);
			g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt(255)));
			g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
			g.drawString("" + ch[index], (i * 15) + 3, 18);
			sb.append(ch[index]);
		}
		// request.getSession().setAttribute("piccode", sb.toString());
		String sid = getUUID.getSessionId(request, response);
		memcachedClient.set("piccode" + sid, 3600 * 24, sb.toString());
		ImageIO.write(img, "JPG", response.getOutputStream());
	}

	@RequestMapping(value = "/getImageRegist")
	public void getImageRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

		String sid = getUUID.getSessionId(request, response);
		memcachedClient.set("piccode_regist" + sid, 3600 * 24, verifyCode.toLowerCase());
		// 生成图片
		int w = 230, h = 80;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}

	/**
	 * 跳转注册输入验证码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_TO_INPUT_CODE)
	public String toInputCode(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("toInputCode execute....");
		String mobile = (String) request.getParameter("mobile");
		model.addAttribute("mobile", mobile);
		String str1 = mobile.substring(0, 3);
		String str2 = mobile.substring(7);
		String str3 = "+86" + str1 + "****" + str2;
		model.addAttribute("mobilestr", str3);
		customerService.toLoginPage(request, response, model);
		log.info("toInputCode end..");
		return ResponseContant.INPUT_CODE;
	}

	/**
	 * 跳转登录页面
	 * 
	 * @return
	 */
	/*
	 * @RequestMapping(value = RequestContant.CUSTOMER_TO_LOGIN) public String
	 * toLogin(Model model, HttpServletRequest request, HttpServletResponse
	 * response) throws Exception { log.info("toLogin execute....");
	 * 
	 * 
	 * customerService.toLoginPage(request, response, model); String productId =
	 * request.getParameter("productId"); if (StringUtils.isNotEmpty(productId))
	 * { request.setAttribute("productId", productId); }
	 * 
	 * // 微信浏览器 String isWeiXin = request.getParameter("isWeiXin"); if
	 * ("true".equals(isWeiXin)) { String code = null; String openId = null; //
	 * 微信用户授权接口所需 code = request.getParameter("code"); //code = "asdfghj"; if
	 * (StringUtils.isBlank(code)) { log.info("用户请求的 code 没值,需要授权"); // 当前访问的地址
	 * String currenturl = request.getRequestURL() + (request.getQueryString()
	 * == null ? "" : ("?" + request.getQueryString()));
	 * 
	 * log.info("用户访问 currenturl:{}", currenturl); // 没有调用授权接口，那么就调用它 //
	 * 构造授权接口地址 String redirectUrl = Oauth.getCode(
	 * URLEncoder.encode(currenturl, "utf-8"), false); // 直接跳转到目标url
	 * 
	 * return ResponseContant.REDIRECT + redirectUrl; }
	 * this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_CODE,
	 * CommonConstant.WEIXIN_JSAPI_CODE_VAILED_TIME, code);
	 * 
	 * log.info("用户请求的code 有值{}", code); // 看是否调用了授权接口，调用了的话在这里取得用户信息 String
	 * result = Oauth.getToken(code); net.sf.json.JSONObject jsonObject =
	 * net.sf.json.JSONObject .fromObject(result);
	 * 
	 * if (result.indexOf("40029") > 0) { log.info("用户请求的 code 不合法,表示需要重新授权");
	 * // 当前访问的地址 String currenturl = request.getRequestURL() +
	 * (request.getParameter("orderId") == null ? "" : ("?orderId=" + request
	 * .getParameter("orderId")));
	 * 
	 * log.info("用户访问 currenturl:{}", currenturl); // 没有调用授权接口，那么就调用它 //
	 * 构造授权接口地址 String redirectUrl = Oauth.getCode(
	 * URLEncoder.encode(currenturl, "utf-8"), false); // 直接跳转到目标url return
	 * ResponseContant.REDIRECT + redirectUrl; } openId =
	 * jsonObject.getString("openid");
	 * this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_OPENID,
	 * CommonConstant.WEIXIN_JSAPI_OPENID_VAILED_TIME, openId); log.info(
	 * "得到myOpenid is {}", openId);
	 * 
	 * //openId = "aaaaaaaaaa"; // 根据openId查询用户信息 WxUser wxUser =
	 * userService.findWxUserByOpenId(openId); //WxUser wxUser = new WxUser();
	 * //wxUser.setUserId(20366l); if (null != wxUser) { // 登陆
	 * log.info("已绑定直接登陆"); String loginresult =
	 * customerService.checkWxLogin(wxUser, request, response);
	 * 
	 * if ("506".equals(loginresult)) {
	 * 
	 * // 登陆失败账号被冻结跳转到冻结提示页面 log.info("账号冻结"); return ResponseContant.FREEZE;
	 * 
	 * }
	 * 
	 * // 登陆成功跳转 String sid = getUUID.getSessionId(request, response); String
	 * beforeUrl = ""; try { // 跳转到登陆前的链接地址 beforeUrl =
	 * memcachedClient.get("beforUrl" + sid); log.info("beforeUrl2 is"
	 * +beforeUrl); //本地测试需要截取 //beforeUrl = beforeUrl.substring(13);
	 * //log.info("beforeUrl is"+beforeUrl); } catch (Exception e) { log.info(
	 * "set memcached has error." + e.getMessage(), e); beforeUrl = ""; }
	 * 
	 * if (StringUtils.isEmpty(beforeUrl)) { log.info(
	 * "wxlogin success,user login end."); beforeUrl =
	 * "http://m.zhongjumall.com"; } else { log.info(
	 * "wxlogin success,return beforeUrl, user wxlogin end."); } return
	 * ResponseContant.REDIRECT + beforeUrl;
	 * 
	 * } else { // 未绑定跳转登陆页登陆并绑定 request.setAttribute("openId", openId); return
	 * ResponseContant.LOGIN; }
	 * 
	 * }
	 * 
	 * 
	 * 
	 * return ResponseContant.LOGIN; }
	 */
	/**
	 * 注解跳转登录页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 * @time 2018.4.19
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_TO_LOGIN)
	public String toLogin(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("toLogin execute....");
		customerService.toLoginPage(request, response, model);
		String productId = request.getParameter("productId");
		if (StringUtils.isNotEmpty(productId)) {
			request.setAttribute("productId", productId);
		}
		// 微信浏览器
		String isWeiXin = request.getParameter("isWeiXin");

		if ("true".equals(isWeiXin) || request.getHeader("User-Agent").contains("MicroMessenger")) {
			String code = null;
			String openId = null;
			// 微信用户授权接口所需
			code = request.getParameter("code");
			// code = "asdfghj";
			if (StringUtils.isBlank(code)) {
				log.info("用户请求的 code 没值,需要授权");
				// 当前访问的地址
				String currenturl = request.getRequestURL()
						+ (request.getQueryString() == null ? "" : ("?" + request.getQueryString()));
				log.info("用户访问 currenturl:{}", currenturl);
				// 没有调用授权接口，那么就调用它
				// 构造授权接口地址
				String redirectUrl = Oauth.getCode(URLEncoder.encode(currenturl, "utf-8"), false);
				// 直接跳转到目标url
				return ResponseContant.REDIRECT + redirectUrl;
			}
			this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_CODE, CommonConstant.WEIXIN_JSAPI_CODE_VAILED_TIME,
					code);
			log.info("用户请求的code 有值{}", code);
			// 看是否调用了授权接口，调用了的话在这里取得用户信息
			String result = Oauth.getToken(code);
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result);
			if (result.indexOf("40029") > 0) {
				log.info("用户请求的 code 不合法,表示需要重新授权");
				// 当前访问的地址
				String currenturl = request.getRequestURL() + (request.getParameter("orderId") == null ? ""
						: ("?orderId=" + request.getParameter("orderId")));

				log.info("用户访问 currenturl:{}", currenturl);
				// 没有调用授权接口，那么就调用它
				// 构造授权接口地址
				String redirectUrl = Oauth.getCode(URLEncoder.encode(currenturl, "utf-8"), false);
				// 直接跳转到目标url
				return ResponseContant.REDIRECT + redirectUrl;
			}
			openId = jsonObject.getString("openid");
			this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_OPENID, CommonConstant.WEIXIN_JSAPI_OPENID_VAILED_TIME,
					openId);
			log.info("得到myOpenid is {}", openId);

			// openId = "aaaaaaaaaa";
			// 根据openId查询用户信息
			WxUser wxUser = userService.findWxUserByOpenId(openId);
			// WxUser wxUser = new WxUser();
			// wxUser.setUserId(20366l);
			String beforeUrl = "";
			if (null != wxUser) {
				// 登陆
				log.info("已绑定直接登陆");
				String loginresult = customerService.checkWxLogin(wxUser, request, response);
				if ("506".equals(loginresult)) {
					// 登陆失败账号被冻结跳转到冻结提示页面
					log.info("账号冻结");
					return ResponseContant.FREEZE;
				}
				// 登陆成功跳转
				String sid = getUUID.getSessionId(request, response);

				try {
					// 跳转到登陆前的链接地址
					beforeUrl = memcachedClient.get("beforUrl" + sid);
					log.info("beforeUrl2 is" + beforeUrl);
					// 本地测试需要截取
					// beforeUrl = beforeUrl.substring(13);
					// log.info("beforeUrl is"+beforeUrl);
				} catch (Exception e) {
					log.info("set memcached has error." + e.getMessage(), e);
					beforeUrl = "";
				}
				
				if (StringUtils.isEmpty(beforeUrl)) {
					log.info("wxlogin success,user login end.");
					beforeUrl = "https://m.zhongjumall.com";
				} else {
					log.info("wxlogin success,return beforeUrl, user wxlogin end.");
				}
				return ResponseContant.REDIRECT + beforeUrl;
			} else {
				// 未绑定跳转登陆页登陆并绑定
				request.setAttribute("openId", openId);
				return ResponseContant.PAYREG;
			}
		} else if (request.getHeader("User-Agent").contains("AlipayClient")) {

			return ResponseContant.REDIRECT + "/customer/thirdAuthorize?openType=alipay&isHandleUser=false&rurl=" + request.getRequestURI();
		}
		return ResponseContant.LOGIN;
	}
	
	/**
	 * mp跳转登录页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 * @time 2018.6.21
	 */
	@RequestMapping("/mpOrMallLogin")
	public String mpOrMallLogin(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("toLogin execute....");
		customerService.toLoginPage(request, response, model);
		String productId = request.getParameter("productId");
		if (StringUtils.isNotEmpty(productId)) {
			request.setAttribute("productId", productId);
		}
		
		// 微信浏览器
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
						+ (request.getQueryString() == null ? "" : ("?" + request.getQueryString()));
				log.info("用户访问 currenturl:{}", currenturl);
				// 没有调用授权接口，那么就调用它
				// 构造授权接口地址
				String redirectUrl = Oauth.getCode(URLEncoder.encode(currenturl, "utf-8"), false);
				// 直接跳转到目标url
				return ResponseContant.REDIRECT + redirectUrl;
			}
			
			this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_CODE, CommonConstant.WEIXIN_JSAPI_CODE_VAILED_TIME,
					code);
			
			log.info("用户请求的code 有值{}", code);
			// 看是否调用了授权接口，调用了的话在这里取得用户信息
			String result = Oauth.getToken(code);
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result);
			if (result.indexOf("40029") > 0) {
				log.info("用户请求的 code 不合法,表示需要重新授权");
				// 当前访问的地址
				String currenturl = request.getRequestURL() + (request.getParameter("orderId") == null ? ""
						: ("?orderId=" + request.getParameter("orderId")));
				log.info("用户访问 currenturl:{}", currenturl);
				// 没有调用授权接口，那么就调用它
				// 构造授权接口地址
				String redirectUrl = Oauth.getCode(URLEncoder.encode(currenturl, "utf-8"), false);
				// 直接跳转到目标url
				return ResponseContant.REDIRECT + redirectUrl;
			}
			
			openId = jsonObject.getString("openid");
			this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_OPENID, CommonConstant.WEIXIN_JSAPI_OPENID_VAILED_TIME,
					openId);
			log.info("得到myOpenid is {}", openId);
			
			// openId = "aaaaaaaaaa";
			// 根据openId查询用户信息
			WxUser wxUser = userService.findWxUserByOpenId(openId);
			
			String beforeUrl = "";
			if (null != wxUser) {
				// 登陆
				log.info("已绑定直接登陆");
				String loginresult = customerService.checkWxLogin(wxUser, request, response);
				if ("506".equals(loginresult)) {
					// 登陆失败账号被冻结跳转到冻结提示页面
					log.info("账号冻结");
					return ResponseContant.FREEZE;
				}
				
				// 登陆成功跳转
				String sid = getUUID.getSessionId(request, response);
				try {
					// 跳转到登陆前的链接地址
					beforeUrl = memcachedClient.get("beforUrl" + sid);
					log.info("beforeUrl2 is" + beforeUrl);
					// 本地测试需要截取
					// beforeUrl = beforeUrl.substring(13);
					// log.info("beforeUrl is"+beforeUrl);
				} catch (Exception e) {
					log.info("set memcached has error." + e.getMessage(), e);
					beforeUrl = "";
				}
				
				if (StringUtils.isEmpty(beforeUrl)) {
					log.info("wxlogin success,user login end.");
					beforeUrl = "https://m.zhongjumall.com";
				} else {
					log.info("wxlogin success,return beforeUrl, user wxlogin end.");
				}
				
				return ResponseContant.REDIRECT + beforeUrl;
			} else {
				//未绑定跳转登陆页登陆
				request.setAttribute("openId", openId);
				return ResponseContant.MPORMALL;
			}
		}
		return ResponseContant.LOGIN;
	}
	
	/**
	 * 消费者扫码付款
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 * @time 2018.06.13
	 */
	@RequestMapping("userToPay")
	public String userToPay(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("toLogin execute....");
		customerService.toLoginPage(request, response, model);
		String productId = request.getParameter("productId");
		if (StringUtils.isNotEmpty(productId)) {
			request.setAttribute("productId", productId);
		}
		// 微信浏览器
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
						+ (request.getQueryString() == null ? "" : ("?" + request.getQueryString()));
				log.info("用户访问 currenturl:{}", currenturl);
				// 没有调用授权接口，那么就调用它
				// 构造授权接口地址
				String redirectUrl = Oauth.getCode(URLEncoder.encode(currenturl, "utf-8"), false);
				// 直接跳转到目标url
				return ResponseContant.REDIRECT + redirectUrl;
			}

			this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_CODE, CommonConstant.WEIXIN_JSAPI_CODE_VAILED_TIME,
					code);

			log.info("微信code  "+request.getRequestURL()
					+ (request.getQueryString() == null ? "" : ("?" + request.getQueryString())));

			log.info("用户请求的code 有值{}", code);
			//看是否调用了授权接口，调用了的话在这里取得用户信息
			String result = Oauth.getToken(code);
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result);
			if (result.indexOf("40029") > 0) {
				log.info("用户请求的 code 不合法,表示需要重新授权");
				// 当前访问的地址
				String currenturl = request.getRequestURL()
						+ (request.getQueryString() == null ? "" : ("?" + request.getQueryString()));

				log.info("用户访问 currenturl:{}", currenturl);
				// 没有调用授权接口，那么就调用它
				// 构造授权接口地址
				String redirectUrl = Oauth.getCode(URLEncoder.encode(currenturl, "utf-8"), false);
				// 直接跳转到目标url
				return ResponseContant.REDIRECT + redirectUrl;
			}
			
			openId = jsonObject.getString("openid");
			this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_OPENID, CommonConstant.WEIXIN_JSAPI_OPENID_VAILED_TIME,
					openId);
			log.info("得到myOpenid is {}", openId);
			// openId = "aaaaaaaaaa";
			// 根据openId查询用户信息
			WxUser wxUser = userService.findWxUserByOpenId(openId);
			// WxUser wxUser = new WxUser();
			// wxUser.setUserId(20366l);
			String beforeUrl = "";
			if (null != wxUser) {
				// 登陆
				log.info("已绑定直接登陆");
				String loginresult = customerService.checkWxLogin(wxUser, request, response);
				if ("506".equals(loginresult)) {
					// 登陆失败账号被冻结跳转到冻结提示页面
					log.info("账号冻结");
					return ResponseContant.FREEZE;
				}
				// 登陆成功跳转
				String sid = getUUID.getSessionId(request, response);
				
				try {
					// 跳转到登陆前的链接地址
					beforeUrl = memcachedClient.get("beforUrl" + sid);
					log.info("beforeUrl2 is" + beforeUrl);
					// 本地测试需要截取
					// beforeUrl = beforeUrl.substring(13);
					// log.info("beforeUrl is"+beforeUrl);
				} catch (Exception e) {
					log.info("set memcached has error." + e.getMessage(), e);
					beforeUrl = "";
				}
				
				if (StringUtils.isEmpty(beforeUrl)) {
					log.info("wxlogin success,user login end.");
					beforeUrl = "https://m.zhongjumall.com";
				} else {
					log.info("wxlogin success,return beforeUrl, user wxlogin end.");
				}
				return ResponseContant.REDIRECT + beforeUrl +"&isWeiXin="+isWeiXin;
			} else {
				// 未绑定且没有帐号，默认注册一个帐号
				request.setAttribute("openId", openId);
				customerService.userOpenThirdSaveCustomer(ThirdAccountConstant.PREFIX_WEIXIN, request, response);
				wxUser = userService.findWxUserByOpenId(openId);
				// WxUser wxUser = new WxUser();
				// wxUser.setUserId(20366l);
				if (null != wxUser) {
					// 登陆
					log.info("已绑定直接登陆");
					String loginresult = customerService.checkWxLogin(wxUser, request, response);
					if ("506".equals(loginresult)) {
						// 登陆失败账号被冻结跳转到冻结提示页面
						log.info("账号冻结");
						return ResponseContant.FREEZE;
					}
					// 登陆成功跳转
					String sid = getUUID.getSessionId(request, response);
					try {
						beforeUrl = memcachedClient.get("beforUrl" + sid);
						log.info("beforeUrl2 is" + beforeUrl);
						// 本地测试需要截取
						// beforeUrl = beforeUrl.substring(13);
						// log.info("beforeUrl is"+beforeUrl);
					} catch (Exception e) {
						log.info("set memcached has error." + e.getMessage(), e);
						beforeUrl = ""; 
					}
					if (StringUtils.isEmpty(beforeUrl)) {
						log.info("wxlogin success,user login end.");
						beforeUrl = "https://m.zhongjumall.com";
					} else {
						log.info("wxlogin success,return beforeUrl, user wxlogin end.");
					}
					return ResponseContant.REDIRECT + beforeUrl + "&isWeiXin="+isWeiXin;
				}
			}
		}


		return ResponseContant.LOGIN;
	}

	/**
	 * 第三方账户授权。
	 * @param model
	 * @param request
	 * @param response
	 * @param openType 		第三方账户类型，alipay weixin
	 * @param rurl	 		自定义重定向的url。未指定需要处理用户时按照memcached跳转。未指定也不处理用户则走默认url。重定向时会把openId带到url后。
	 * @param isHandleUser	是否要处理用户体系，添加绑定等操作。默认为处理。
	 * @return
	 */
	@RequestMapping("thirdAuthorize")
	public String thirdAuthorize(Model model, HttpServletRequest request, HttpServletResponse response, String openType, String rurl, Boolean isHandleUser) {
		customerService.toLoginPage(request, response, model);

		if (isHandleUser == null){
			isHandleUser = Boolean.TRUE;
		}

		String url = null;
		String openId = null;
        String weixinCode = request.getParameter("code");
        String alipayCode = request.getParameter("auth_code");

		try {
			// 未授权
			if (StringUtils.isEmpty(alipayCode)) {
				// 支付宝
				if (ThirdAccountConstant.ACCOUNT_ALIPAY.equalsIgnoreCase(openType)) {
					String currenturl = request.getRequestURL()	+ (request.getQueryString() == null ? "" : ("?" + request.getQueryString()));
					log.info("支付宝授权redirect_uri地址：" + currenturl);

					currenturl = URLEncoder.encode(currenturl, "utf-8");
					String redirectUrl = String.format(aliServiceWindow.getAlipayAuthorizeCode(), aliServiceWindow.getAppId(), "auth_base", currenturl);

                    return ResponseContant.REDIRECT + redirectUrl;
				}
			}


			if (ThirdAccountConstant.ACCOUNT_ALIPAY.equalsIgnoreCase(openType) && StringUtils.isNotEmpty(alipayCode)) {
				log.info("支付宝授权成功， url：" + request.getRequestURL() + (request.getQueryString() == null ? "" : ("?" + request.getQueryString())));

				AlipayClient alipayClient = new DefaultAlipayClient(aliServiceWindow.getAlipayGateway(),
						aliServiceWindow.getAppId(), aliServiceWindow.getAppPrivateKey(), "json", "UTF-8",
						aliServiceWindow.getAlipayPublicKey(), "RSA2");

				// 获取支付宝userID accessToken
				AlipaySystemOauthTokenRequest aliRequest = new AlipaySystemOauthTokenRequest();
				aliRequest.setCode(alipayCode);
				aliRequest.setGrantType(aliServiceWindow.getAlipayGrantType());
				AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(aliRequest);
				openId = oauthTokenResponse.getUserId();
				String accessToken = oauthTokenResponse.getAccessToken();


				log.info(String.format("支付宝获取 userId: %s accessToken：%s refreshAccessToken：%s", oauthTokenResponse.getUserId(),oauthTokenResponse.getAccessToken(),oauthTokenResponse.getRefreshToken()));


				this.memcachedClient.add(ThirdAccountConstant.ACCESS_TOKEN_ALIPAY, ThirdAccountConstant.AUTH_CODE_VAILED_TIME, accessToken);

				// 是否处理账户
				if (isHandleUser) {
					url = customerService.checkThirdAccount(openType, openId, request, response);
				}
			}else{
                log.error("thirdAuthorize授权登陆失败！openType: " + openType + " alipayCode: " + alipayCode + " weixinCode: " + weixinCode);
            }


            if (StringUtils.isNotEmpty(rurl)) {
				if (!rurl.contains("?")) {
					return ResponseContant.REDIRECT + rurl + "?openId=" + openId;
				} else {
					return ResponseContant.REDIRECT + rurl + "&openId=" + openId;
				}
            }

            if (StringUtils.isNotEmpty(url)){
				return url;
			}

            return ResponseContant.LOGIN;
		} catch (UnsupportedEncodingException e) {
			log.error("URLEncoder.encode失败", e);
			return ResponseContant.LOGIN;
		} catch (Exception e) {
			log.error("第三方授权失败", e);
			return ResponseContant.LOGIN;
		}

	}
	/**
	 * 登录
	 * 
	 * @return
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_LOGIN, method = RequestMethod.POST)
	@ResponseBody
	public String login(String userName, String password, String captcha, String openId, Model model,String isWeiXin,
			HttpServletRequest request, HttpServletResponse response) {
		log.info(" user login begin..");
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			log.info("bad request,username or password is empty..");
			throw new BadRequestException("login fail");
		}
		log.info("openId=", openId);
		// 验证密码
		String result = customerService.checkLogin(userName, password, captcha, openId, request, response);

		if (!"200".equals(result)) {// 登陆不成功
			if ("501".equals(result)) {// 用户名或密码错误，需要记录登陆次数
				try {
					String errorCount = memcachedClient.get("codeErrorCount" + userName);
					if (errorCount == null) {
						memcachedClient.set("codeErrorCount" + userName, 3600 * 24, "1");
					} else {
						int parseInt = Integer.parseInt(errorCount);
						parseInt++;
						memcachedClient.set("codeErrorCount" + userName, 3600 * 24, String.valueOf(parseInt));
					}
					memcachedClient.get("codeErrorCount" + userName);

				} catch (TimeoutException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (MemcachedException e) {
					e.printStackTrace();
				}

			}
			log.info("login has error,user login end.");
			return result;
		}
		if ("200".equals(result)) {
			try {
				memcachedClient.delete("codeErrorCount" + userName);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		String sid = getUUID.getSessionId(request, response);
		String beforeUrl = "";
		try {
			// 跳转到登陆前的链接地址
			beforeUrl = memcachedClient.get("beforUrl" + sid);
		} catch (Exception e) {
			log.info("set memcached has error." + e.getMessage(), e);
			return "500";
		}

		// 登陆成功， 只跳首页
		// beforeUrl = "http://m.zhongjumall.com";
		// 本地测试
		// beforeUrl = "http://localhost:8080/wap-customer/customer/toMy";

		if (StringUtils.isEmpty(beforeUrl)) {
			// 获取当前登录人
			User user = getCurrentUser(request);
			try {
				memcachedClient.set("userInfo", 3600 * 24, String.valueOf(user));
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (MemcachedException e) {
				e.printStackTrace();
			}
			log.info("login success,user login end.");
			return result;
		} else {
			log.info("login success,return beforeUrl, user login end.");
			return beforeUrl;
		}
	}

	/**
	 * 校验 手机验证码（找回密码）
	 * 
	 * @param model
	 * @param username
	 * @param msgCode
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_VALID_CODE)
	@ResponseBody
	public String validCode(Model model, String username, String msgCode, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("validCode execute..");
		if (StringUtils.isBlank(username) || StringUtils.isBlank(msgCode)) {
			log.error("username or msgCode is null");
			throw new BadRequestException("validCode faile");
		}
		try {
			// 手机验证码20分钟过期
			memcachedClient.set("uservaldcode_" + username, 20 * 60 * 1000, String.valueOf(msgCode));
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 判断其 从个人中心修改密码 还是 登录界面
		String from = request.getParameter("from");
		if ("cus".equals(from)) {
			User currentUser = getCurrentUser(request);
			username = currentUser.getUserName();
			model.addAttribute("username", currentUser.getUserName());
			model.addAttribute("mobile", currentUser.getMobile());
		}

		model.addAttribute("from", from);
		String res = customerService.validMsgCode(username, msgCode, model, request, response);
		model.addAttribute("username", username);
		/*
		 * if ("success".equals(res)) { return ResponseContant.FORGOT_PASS_NEW;
		 * } return ResponseContant.FORGOT_PASS_ACCOUNT;
		 */
		return res;
	}

	/**
	 * 找回密码
	 * 
	 * @param model
	 * @param username
	 * @param pass
	 * @param passConfirm
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_NEW_PASS)
	// @ResponseBody
	public String newPass(Model model, String username, String pass, String passConfirm, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("newPass execute..");
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(pass) || StringUtils.isEmpty(passConfirm)) {
			log.error("username is null or pass or passconfirm is null");
			throw new BadRequestException("newPass fail");
		}
		String res = "";
		try {
			String ifOk = memcachedClient.get("uservaldcode_" + username);
			if (StringUtils.isNotEmpty(ifOk)) {
				res = customerService.newPassWord(username, pass, passConfirm, request, response);
				if (StringUtils.isNotEmpty(res) && res.indexOf("modifyOk") == 0) {
					memcachedClient.delete("uservaldcode_" + username);
				}
			}
		} catch (TimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MemcachedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 删除 修改密码权限token
		String sid = getUUID.getSessionId(request, response);
		customerService.deleteSMStokenForSession(sid);

		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		try {
			response.getWriter().write(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置新密码
	 * 
	 * @param model
	 * @param oldpass
	 * @param username
	 * @param pass
	 * @param passConfirm
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_NEW_OLD_PASS)
	// @ResponseBody
	public String newOldPass(Model model, String username, String pass, String oldpass, String passConfirm,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("newPass execute..");
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(pass) || StringUtils.isEmpty(passConfirm)
				|| StringUtils.isEmpty(oldpass)) {
			log.error("username is null or pass or passconfirm or oldpass is null");
			throw new BadRequestException("newPass fail");
		}
		boolean result = customerService.checkOldPass(oldpass, username);
		String res = null;
		if (result) {
			res = customerService.newPassWord(username, pass, passConfirm, request, response);
		} else {
			res = "旧密码错误";
		}
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		try {
			response.getWriter().write(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 登出
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUSTOMER_LOGOUT)
	public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("logout execute..");
		customerService.logout(request, response);
		// model.addAttribute("quit", "yes");

		// https
		return "/customer/logout";

		/*
		 * return ResponseContant.REDIRECT + RequestContant.CUSTOMER +
		 * RequestContant.CUSTOMER_TO_LOGIN;
		 */
	}

	/**
	 * 跳转请求
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_TO_REGISTER)
	public String toRegister(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("toRegister execute..");
		String res = customerService.toRegisterPage(model, request, response);
		log.info("toRegister end..");
		return res;
	}

	/**
	 * 获取注册请求码
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_GET_REG_CODE)
	@ResponseBody
	public String getRegCode(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("getRegCode execute..");
		String res = customerService.getRegCode(model, request, response);
		return res;
	}

	@RequestMapping(value = "/getRepeattRegCode")
	@ResponseBody
	public String getRepeattRegCode(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("getRepeattRegCode execute..");
		String res = customerService.getRepeattRegCode(model, request, response);
		return res;
	}

	/**
	 * 校验登录注册的验证码
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_VALID_REG_CODE)
	@ResponseBody
	public String validRegCode(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("validRegCode execute..");
		String res = customerService.validRegCode(request, response);
		log.info("validRegCode execute finished.return :" + res);
		return res;

	}
	/**
	 * 认证校验登录注册的验证码
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/renZhengValidRegCode")
	@ResponseBody
	public String renZhengValidRegCode(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("validRegCode execute..");
		String res = customerService.renZhengValidRegCode(request, response);
		log.info("validRegCode execute finished.return :" + res);
		return res;
		
	}

	/**
	 * 注册时，跳转设置密码页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_TO_REGISTER_SET_PASSWORD)
	public String setPassword4Register(Model model, String mobile, String origin) {
		log.info("mobile:{},origin:{}", mobile, origin);
		if (StringUtils.isEmpty(mobile)) {
			log.info("setPassword4Register时 mobile为空，跳转注册页。");
			return RequestContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_REGISTER;
		}

		model.addAttribute("mobile", mobile);
		if (origin.trim() != "" && origin != null) {
			model.addAttribute("origin", origin);
		}
		return ResponseContant.REG_PASS;

	}

	/**
	 * 保存用户
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_SAVE_CUS)
	@ResponseBody
	public String saveCustomer(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("saveCustomer execute..");
		String res = customerService.saveCustomer(model, request, response);
		Map<String, Object> data = new HashMap<String, Object>();
		if ("success".equals(res)) {
			data.put("retCode", "success");
			data.put("itemId", "6997724463485144");
		} else {
			data.put("retCode", "error");
		}
		return com.alibaba.fastjson.JSON.toJSONString(data);
	}
	
	/**
	 * 保存用户
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws MemcachedException 
	 * @throws InterruptedException 
	 * @throws TimeoutException 
	 */
	@RequestMapping("/renZhengsaveCustomer")
	@ResponseBody
	public String renZhengsaveCustomer(Model model, HttpServletRequest request, HttpServletResponse response) throws TimeoutException, InterruptedException, MemcachedException{
		log.info("saveCustomer execute..");
		String res = customerService.renZhengsaveCustomer(model, request, response);
		
		String mobile = request.getParameter("mobile");
		User newUser = customerService.getUserByUserName(mobile);
		// 设置用户为微信登陆
		newUser.setloginType(1);
		
		String loginval = customerService.login(request, response, newUser);
		if(loginval != null && loginval.trim().equals("200")){
			return com.alibaba.fastjson.JSON.toJSONString(res);
		}else{
			//不能登陆 例如用户冻结
			return "https://m.zhongjumall.com";
		}
	}
	
	@RequestMapping(value = "/valideCodeSame")
	@ResponseBody
	public String valideCodeSame(Model model, HttpServletRequest request, HttpServletResponse response) {
		String captcha = request.getParameter("captcha");
		if (StringUtils.isEmpty(captcha)) {
			log.info("captcha is null ");
			throw new BadRequestException("captcha is null ");
		}
		String sid = getUUID.getSessionId(request, response);
		String piccode_regist = null;
		try {
			piccode_regist = memcachedClient.get("piccode_regist" + sid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!captcha.equalsIgnoreCase(piccode_regist)) {
			return "error";
		}
		return "success";
	}

	/**
	 * 保存用户 隐形注册
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = RequestContant.CUSTOMER_SAVE_INVISIBLE)
	public String saveCustomerInvisible(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("saveCustomerInvisible execute..");

		// 检查用户是否存在
		String mobile = request.getParameter("mobile");
		log.info("start to check mobile isexist..");
		User user = customerService.getUserByUserName(mobile);

		// 用户已存在
		if (user != null) {
			log.info("用户已存在 直接登录");
			// 登录
			log.info("start to login...");
			String login = customerService.login(request, response, user);
			log.info("customerService.login,return:" + login);
			return "200";
		} else { // 用户不存在
			log.info("用户不存在");
			// 生成8位随机密码
			log.info("start to random pass...");
			String password = CommonUtil.randomPassword(8);
			request.setAttribute("pass", password);
			log.info("random pass end. password:" + password);

			// 保存用户
			String res = customerService.saveCustomer(model, request, response);
			log.info("saveCustomerInvisible end. return:" + res);

			// 注册成功
			if ("success".equals(res)) {
				log.info("注册成功，发送 系统生成的密码 给用户,mobile:" + mobile + "\tpassword:" + password);
				String sendPassword = SendSMSUtil.sendPassword(mobile, password);
				log.info("SendSMSUtil.sendPassword return :" + sendPassword);

				// 登录
				log.info("start to login...");
				User newUser = customerService.getUserByUserName(mobile);
				String login = customerService.login(request, response, newUser);
				log.info("customerService.login,return:" + login);

				return "200";
			}
		}
		return "500";
	}

	/**
	 * 跳转到我的个人中心
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_TO_MYCCIG)
	public String toMyCcig(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			Object object1 = memcachedClient.get("userInfo");
			Object object2 = memcachedClient.get("beforUrl");
			System.out.println(object1);
			System.out.println(object2);
		} catch (TimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MemcachedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		log.info("toMyCcig  execute....");
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			log.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		try {
			// 查询用户状态
			Integer state = sqwAccountRecordService.getUserAccountStatus(userInfo.getUserId(), 1);
			// 查询用户是否商家小号6.7
			User user2 = userService.findUserById(userInfo.getUserId());
			Integer shangJiaXiaoHao = 0;
			if ("1".equals(user2.getSupplierFlag())) {
				shangJiaXiaoHao = 1;
			}
			// 查询用户是否购买家庭号6.7
			HomeNumRecordDto homeNumRecordDto = homeNumRecordService
					.selectRecordByUserid(userInfo.getUserId().intValue());
			Integer jiaTingStatus = homeNumRecordDto.getStatus();// 0 没购买 1过期
																	// 2正常使用
			// 查询用户是否购买会员企业号7.27
			SupplierNumRecordDto supplierNumRecordDto = supplierNumRecordService
					.selectRecordByUserid(userInfo.getUserId().intValue());
			Integer cusBusStatus = supplierNumRecordDto.getStatus();// 0 没购买 1过期
																	// 2正常使用
			// 查询红旗宝
			CashierRecord cashierRecord = new CashierRecord();
			cashierRecord.setUserId(userInfo.getUserId());
			cashierRecord.setType(1);
			List<CashierRecord> cashierRecords = (List<CashierRecord>) cashierService
					.selectCashierRecordByUserid(cashierRecord);
			CashierRecord cashierRecordFromDb = null;
			if (cashierRecords != null) {
				cashierRecordFromDb = cashierRecords.get(0);
			}
			// 查询生鲜宝
			cashierRecord.setType(2);
			List<CashierRecord> cashierRecords2 = (List<CashierRecord>) cashierService
					.selectCashierRecordByUserid(cashierRecord);
			CashierRecord shengXian = null;
			if (cashierRecords2 != null) {
				shengXian = cashierRecords2.get(0);
			}
			// 拥有红旗宝
			int reciveMoneyState = 2;
			if (cashierRecordFromDb == null)
				reciveMoneyState = 1;// 未购买
			else if (cashierRecordFromDb.getOrderState() == 2)
				reciveMoneyState = 3;// 已过期
			// 拥有生鲜宝
			int shengXianState = 2;
			if (shengXian == null)
				shengXianState = 1;// 未购买
			else if (shengXian.getOrderState() == 2)
				shengXianState = 3;// 已过期
			// 查询有效平台通知
			Integer noticeStatus = userService.getStatus();
			// 2017-1-2 by lipeng
			User user = userService.findUserById(userInfo.getUserId());
			// * 2017-3-2 by zhangcuo
			WealthVO wealth = wealthService.info(userInfo);
			model.addAttribute("wealth", wealth);
			log.info("toMyCcig  wealth....");
			// *
			model.addAttribute("userStars", user.getStars());
			// 2017-1-2 by lipeng
			model.addAttribute("userMobileLength", user.getMobile().length());
			model.addAttribute("state", state);
			model.addAttribute("shangJiaXiaoHao", shangJiaXiaoHao);
			model.addAttribute("cusBusStatus", cusBusStatus);
			model.addAttribute("jiaTingStatus", jiaTingStatus);
			model.addAttribute("noticeStatus", noticeStatus);
			model.addAttribute("reciveMoneyState", reciveMoneyState);
			model.addAttribute("shengXianState", shengXianState);
		} catch (Exception e) {
			log.error("cusInfo error", e);
		}
		return ResponseContant.CUS_USER_MAIN;
	}

	/**
	 * 检查当前用户是否实名认证
	 * 
	 * @param request
	 * @return
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = RequestContant.CUSTOMER_CHECK_REAL_NAME)
	public String checkUserRealName(HttpServletRequest request) {
		// 获取当前用户
		User user = getCurrentUser(request);
		Long userId = user.getUserId();

		// 检查是否实名认证
		boolean isRealName = userControllerImpl.checkUserRealName(userId);

		// true or false
		return String.valueOf(isRealName);
	}

	@RequestMapping(value = "/toLoginMessage")
	@ResponseBody
	public String toLoginMessage(Model model, HttpServletRequest request, HttpServletResponse response,
			String callback) {
		String res = customerService.toLoginMessage(model, request, response);
		log.info("toLoginMessage return value is : " + res);
		if (org.apache.commons.lang3.StringUtils.isBlank(callback)) {
			return res;
		}
		return callback + "('" + res + "')";
	}

	/**
	 * 记录分享用户的cookie
	 * 
	 * @param request
	 * @param response
	 * @param callback
	 * @return
	 */
	@RequestMapping(value = "/recordShareUserIdCookie")
	@ResponseBody
	public String recordShareUserIdInCookie(HttpServletRequest request, HttpServletResponse response, String callback) {

		User user = this.getCurrentUser(request);

		String res = customerService.recordShareUserIdInCookie(request, response, user);

		if (StringUtils.isBlank(callback)) {
			return res;
		}
		return callback + "('" + res + "')";
	}

	/**
	 * 活动发送手机验证码(不判断是否注册) jsonp
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getRegistCodeJsonp", produces = "application/jsonp; charset=utf-8")
	@ResponseBody
	public String getRegistCodeJsonp(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("getRegCode execute..");
		String res = customerService.getRegCodeActivityUC(model, request, response);
		return "jsoncallbackUCcode('" + res + "')";
	}

	/**
	 * IPTV抢卷活动发送手机验证码(不判断是否注册) jsonp
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getIPTVRegistCodeJsonp", produces = "application/jsonp; charset=utf-8")
	@ResponseBody
	public String getIPTVRegistCodeJsonp(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("getIPTVRegCode execute..");
		String res = customerService.getIPTVRegCodeActivityUC(model, request, response);
		return "jsoncallbackUCcode('" + res + "')";
	}

	/**
	 * 活动验证手机和验证码是否一致并记录用户来源 jsonp访问
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "registCodeJsonp", produces = "application/jsonp; charset=utf-8")
	@ResponseBody
	public String registCodeJsonp(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("getRegCode execute..");
		String res = customerService.registCodeJsonpUC(model, request, response, true);
		return "jsoncallbackUCregisterCode('" + res + "')";
	}

	// -------------------------------------------------------------
	// 找回密码------------------------------------------------ begin

	/**
	 * 找回密码>跳转到到【找回密码】页面 /customer/toGetpass
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_TO_GETPASS)
	public String toGetPass(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("toGetPass execute..");
		// 判断其 从个人中心修改密码 还是 登录界面
		String from = request.getParameter("from");
		if ("cus".equals(from)) {
			User currentUser = getCurrentUser(request);
			if (currentUser == null) {
				return ResponseContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_LOGIN;
			}
			model.addAttribute("username", currentUser.getUserName());
			model.addAttribute("mobile", currentUser.getMobile());
		}

		model.addAttribute("from", from);

		return ResponseContant.FORGOT_PASS_ACCOUNT;
	}

	/**
	 * 找回密码>获取图形验证码 :/customer/getImageFindPwd
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author YANHONGYUAN 2016年8月18日14:46:38
	 */
	@RequestMapping(value = "/getImageFindPwd")
	public void getImageFindPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

		String sid = getUUID.getSessionId(request, response);
		// piccode_change_pwd:找回密码，3600*24=1day
		memcachedClient.set("piccode_find_pwd" + sid, 3600 * 24, verifyCode.toLowerCase());
		// 生成图片
		int w = 230, h = 80;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}

	/**
	 * 找回密码>验证图形验证码 validFindPwdCode
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/validFindPwdCode")
	@ResponseBody
	public String validFindPwdCode(Model model, HttpServletRequest request, HttpServletResponse response) {
		// 验证图形验证码
		Map<String, Object> msgMap = customerService.validFindPwdCode(request, response);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		return JSONObject.toJSONString(msgMap);
	}

	/**
	 * 找回密码>获取短信验证码（找回密码）
	 * 
	 * @param model
	 * @param username
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_GET_CODE)
	@ResponseBody
	public String getCode(String username, String token, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("getCode execute..");
		if (StringUtils.isEmpty(username)) {
			log.error("user name is null");
			throw new BadRequestException("getCode   Faile");
		}
		// 如果参数为空则跳转到,图形验证码 页面
		if (org.apache.commons.lang3.StringUtils.isEmpty(username)) {
			log.error("user name is null");
			throw new BadRequestException("getCode   Faile");
		} else if (org.apache.commons.lang3.StringUtils.isEmpty(token)) {
			//
			log.error("sms token is null");
			throw new BadRequestException("getCode   Faile");
		} else {
			String sid = getUUID.getSessionId(request, response);

			if (!token.equals(customerService.getSMStokenVal(sid, username))) {
				// 传入token与 参数新生成的token进行比较,不一样则表示 串改了 token值或者 手机号码
				log.error("sms token or username is errer");
				throw new BadRequestException("getCode   Faile");
			} else if (!token.equals(customerService.getSMStokenForSession(sid))) {
				// 传入的token与缓存中的token比较,如果不一样则 表示 非法token
				//
				log.error("sms token is errer");
				throw new BadRequestException("getCode   Faile");
			}
		}

		// 判断是否可以发送验证码
		String res = customerService.sendCheckItentiyCode(model, username, request, response);

		return res;
	}

	/**
	 * /toInputPassCode 跳转找回密码输入验证码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUSTOMER_TO_INPUT_PASS_CODE)
	public String toInputPassCode(String username, String token, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("toInputPassCode execute....");
		// 如果参数为空则跳转到,图形验证码 页面
		if (org.apache.commons.lang3.StringUtils.isEmpty(username)) {
			return ResponseContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_GETPASS;
		} else if (org.apache.commons.lang3.StringUtils.isEmpty(token)) {
			//
			return ResponseContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_GETPASS;
		} else {
			String sid = getUUID.getSessionId(request, response);

			if (!token.equals(customerService.getSMStokenVal(sid, username))) {
				// 传入token与 参数新生成的token进行比较,不一样则表示 串改了 token值或者 手机号码
				return ResponseContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_GETPASS;
			} else if (!token.equals(customerService.getSMStokenForSession(sid))) {
				// 传入的token与缓存中的token比较,如果不一样则 表示 非法token
				return ResponseContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_GETPASS;
			}
		}

		model.addAttribute("username", username);
		String str1 = username.substring(0, 3);
		String str2 = username.substring(7);
		String str3 = "+86" + str1 + "****" + str2;
		model.addAttribute("mobilestr", str3);
		log.info("toInputPassCode end..");
		return ResponseContant.INPUT_CODE_PASSWORD;
	}

	/**
	 * 冻结页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/freeze")
	public String freeze() {
		return "/customer/freeze";
	}

	// -------------------------------------------------------------
	// 找回密码------------------------------------------------ begin

	/**
	 * \ 查询代金券余额
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDjqBalance")
	@ResponseBody
	public SqwUserAccountBalance getDjqBalance(HttpServletRequest request) {
		User userInfo = getCurrentUser(request);
		SqwUserAccountBalance balance = psqwAccountRecordService.selectDjqBalanceByUser(userInfo.getUserId());
		return balance;
	}

	/**
	 * 自动注册的账户修改个人手机号
	 */
	@AuthPassport
	@RequestMapping("/toUpdateMobile")
	public String toUpdateMobile(Model model,HttpServletRequest request){
		User userInfo = getCurrentUser(request);
		model.addAttribute("userId", userInfo.getUserId());
		return "/qrcode/updateMobile";
	}
	/**
	 * 修改个人手机号
	 * @throws MemcachedException 
	 * @throws InterruptedException 
	 * @throws TimeoutException 
	 */
	@AuthPassport
	@RequestMapping("/updateMobile")
	@ResponseBody
	public String updateMobile(Model model,Long userId,String mobile,HttpServletRequest request,HttpServletResponse response) throws TimeoutException, InterruptedException, MemcachedException{
		User currentUser = getCurrentUser(request);
		currentUser.setMobile(mobile);
		userService.updateMobileByUserId(userId, mobile);
		String sid = getUUID.getSessionId(request, response);
		CookieTool.setCookie(response, CommonConstant.SESSION_ID, sid, CommonConstant.MAX_AGE_ONE_MONTH);
		memcachedClient.setOpTimeout(5000L);
		memcachedClient.set(CommonConstant.CUSTOMER_LOGIN + sid, CommonConstant.MEMCACHEDAGE,currentUser);
		return "200";
	}
}
