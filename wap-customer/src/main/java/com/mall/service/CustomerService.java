package com.mall.service;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.constant.AliServiceWindow;
import com.mall.constant.ThirdAccountConstant;
import com.mall.utils.*;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.codec.digest.DigestUtils;
import org.omg.CORBA.TRANSACTION_REQUIRED;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com._21cn.open.openapi.common.OauthRequestUtil;
import com.alibaba.fastjson.JSON;
import com.mall.constant.CommonConstant;
import com.mall.constant.ResponseContant;
import com.mall.customer.model.User;
import com.mall.customer.model.UserOperationRecord;
import com.mall.customer.model.WxUser;
import com.mall.customer.order.common.OrderStatusConstant;
import com.mall.customer.service.ShareUserService;
import com.mall.customer.service.UserAppOriginService;
import com.mall.customer.service.UserOperationRecordService;
import com.mall.customer.service.UserService;
import com.mall.exception.BadRequestException;
import com.mall.exception.BusinessException;
import com.mall.pay.common.StringUtils;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.service.SupplierManagerService;
/*import com.mall.promotion.api.activity.b2creg.PlatformUserRegActivityService;
 import com.mall.promotion.api.activity.web.GradCouponsApi;
 import com.mall.promotion.dto.b2c.UserTriggerDto;*/
import com.mall.vo.ProtectionVO;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * 用户相关服务
 * 
 * @author ccigQA01
 * 
 */
@Service
public class CustomerService {
	private static final String YUE_ME = "yueme";// 悦me
	private static final String ORIGIN_YUE_ME = "908";// 来源悦me

	private static final Logger log = LoggerFactory
			.getLogger(CustomerService.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ShareUserService shareUserService;

	/*
	 * @Autowired private PlatformUserRegActivityService
	 * platformUserRegActivityService;
	 * 
	 * @Autowired private GradCouponsApi redPacketActivityService;
	 */
	@Autowired
	private MemcachedClient memcachedClient;

	@Autowired
	private UserAppOriginService userAppOriginService;

	@Autowired
	private UserOperationRecordService userOperationRecordService;
	@Autowired
	private SupplierManagerService supplierManagerService;

    @Autowired
    private AliServiceWindow aliServiceWindow;



	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	private static final String ORIGIN1 = "1";// 微信
	private static final String ORIGIN2 = "2";// 微博
	private static final String ORIGIN3 = "3";// 中国银行
	private static final String ORIGIN4 = "4";// 流量宝
	private static final String ORIGIN_WEIPIAO = "906";// 微票

	public String readPacketByFocusMedia_480(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String mobile = request.getParameter("mobile");
		if (StringUtils.isBlank(mobile)) {
			throw new BadRequestException(
					"readPacketByFocusMedia_480 mobile can not be blank ");
		}
		User user = new User();
		user.setMobile(mobile);
		User findUser = userService.findUser(user);
		if (findUser != null) {// 用户已注册
			return "-2";// 已领取
		} else {
			return "20";
		}
	}

	public String readPacketByFocusMedia(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer in) {
		String mobile = request.getParameter("mobile");
		if (StringUtils.isBlank(mobile)) {
			throw new BadRequestException("mobile can not be blank ");
		}

		// Integer flg =
		// redPacketActivityService.gradCouponsByUserId(Long.parseLong(mobile));
		log.info("giveCouponForLaShou by request parameter is mobile : "
				+ mobile + " in : " + in);
		Integer flg = 504;
		try {
			// flg =
			// platformUserRegActivityService.giveCouponForLaShou(Long.parseLong(mobile),
			// in);
		} catch (Exception e) {
			log.info("platformUserRegActivityService.giveCouponForLaShou exception");
		}
		log.info("red packet return parameter : " + flg);
		if (flg == null) {
			return "504";
		}
		switch (flg) {
		case 20:
			log.info("get 20 red packet");
			return "20";
		case 100:
			log.info("get 100 red packet");
			return "100";
		case 0:
			log.info("red packet is empty");
			return "0";
		case -2:
			log.info("red packet has been accepted");
			return "-2";
		default:
			log.info("red packet return parameter : " + flg + " and mobile : "
					+ mobile);
			return "504";// 领取失败
		}

	}

	/**
	 * 微票发红包
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	public String getRedPacket(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String mobile = request.getParameter("mobile");
		String verificationCode = request.getParameter("verificationCode");
		String captcha = request.getParameter("captcha");
		log.info("getRdePacket parameter mobile:" + mobile
				+ " verificationCode:" + verificationCode);
		if (StringUtils.isBlank(mobile)) {
			throw new BadRequestException("mobile can not be blank ");
		}
		if (StringUtils.isBlank(verificationCode)) {
			throw new BadRequestException("verificationCode can not be blank ");
		}
		if (StringUtils.isEmpty(captcha)) {
			log.info("getRedPacket captcha is null and parameter mobile is: "
					+ mobile);
			throw new BadRequestException("getRegCodeActivity captcha is null ");
		}
		String sid = getUUID.getSessionId(request, response);
		Integer msgCode = null;
		String memMobile = null;
		String piccode_regist = null;
		try {
			msgCode = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE
					+ mobile);
			memMobile = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE
					+ mobile + "mobile");
			piccode_regist = memcachedClient.get("piccode_regist" + sid);
		} catch (Exception e) {
			throw new BusinessException("memcache running error.." + e);
		}
		// 输入时的电话和 缓存中的电话不一致
		if (!mobile.equals(memMobile)) {
			return "501";// 用户名和验证码不匹配
		}
		if (msgCode != null && !"".equals(msgCode)
				&& !verificationCode.equals(msgCode.toString())) {
			return "500";// 验证码错误
		}

		if (!captcha.equalsIgnoreCase(piccode_regist)) {
			return "502";
		}

		try {
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE + mobile
					+ "mobile");
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE + mobile);
			memcachedClient.delete("piccode_regist" + sid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 验证手机号是否已经注册
		User user = new User();
		user.setMobile(mobile);
		User findUser = userService.findUser(user);
		if (findUser != null) {// 用户已注册
			// UserTriggerDto userTriggerDto = getUserTriggerDto(findUser);
			boolean microTicket = true;// platformUserRegActivityService.microTicketActivity(userTriggerDto,
										// false);
			log.info("existing user:" + mobile + " and send red packet "
					+ microTicket);

			login(request, response, findUser);

			if (microTicket) {
				return "20";
			} else {
				return "503";// 已经领取过
			}
		} else {
			User autoUser = userService.autoRegist(mobile, ORIGIN_WEIPIAO);
			// UserTriggerDto userTriggerDto = getUserTriggerDto(autoUser);
			boolean microTicket = true; // platformUserRegActivityService.microTicketActivity(userTriggerDto,
										// true);
			log.info("the user dose not exist:" + mobile
					+ " and send red packet " + microTicket);

			login(request, response, autoUser);

			return "480";
		}
	}

	/**
	 * 第三方来源注册送券
	 * 
	 * @param request
	 * @param response
	 * @param origin
	 * @param mobile
	 * @param verificationCode
	 * @param password
	 * @return
	 */
	public String registCoupons(HttpServletRequest request,
			HttpServletResponse response, String origin, String mobile,
			String verificationCode, String password) {
		// 验证码是否匹配
		String sid = getUUID.getSessionId(request, response);
		Integer msgCode = null;
		String memMobile = null;
		try {
			msgCode = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE
					+ mobile);
			memMobile = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE
					+ mobile + "mobile");
		} catch (Exception e) {
			log.info("memcache running error.." + e, e);
			throw new BusinessException("memcache running error.." + e);
		}

		if (msgCode != null && !"".equals(msgCode)
				&& !verificationCode.equals(msgCode.toString())) {
			return "500";// 验证码错误
		}
		// 输入时的电话和 缓存中的电话不一致
		if (!mobile.equals(memMobile)) {
			return "501";// 用户名和验证码不匹配
		}
		// 验证手机号是否已经注册
		boolean exitusername = isUserExist(mobile);
		if (exitusername) {
			log.info("mobile has already exist");
			return "505";// 用户已注册
		}
		// 校验成功
		try {
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE + sid);
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE_LOSE);
			memcachedClient.setOpTimeout(5000L);
			memcachedClient.set("register" + sid,
					CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE, mobile);
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}
		log.info("校验成功  mobile :" + mobile);

		// 注册并发券（保存用户）

		// 获取cookie中的值
		// String origin = CookieUtil.getCookieValue(request, "channel");
		// 获取cookie
		// Cookie cookie = CookieUtil.getCookie(request, "channel");
		// log.info("cookie = " + cookie + "value = " + origin);

		String username = ProduceUserName.produce(mobile);
		// des解密
		password = Des.strDec(password, mobile);
		if (StringUtils.isEmpty(mobile)) {
			log.info("mobile is null ");
			throw new BadRequestException("user mobile  is null ");
		}
		if (StringUtils.isEmpty(password)) {
			log.info("pass is null ");
			throw new BadRequestException("pass is null ");
		}

		String sessionUsername = null;
		try {
			sessionUsername = memcachedClient.get("register" + sid);
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}
		if (sessionUsername != null && sessionUsername.equals(mobile)) {
			// 这个用户名和密码是一样的
			User user = new User();
			user.setUserName(username);
			user.setPassword(password);
			user.setMobile(mobile);
			user.setNickName(username);
			user.setRegType((byte) 0);
			user.setOrigin(origin);

			/*
			 * if (origin != null && origin != "") { user.setOrigin(origin); }
			 * else { user.setOrigin(null); }
			 */
			userService = getUserService();
			int saveUser = 0;
			try {
				saveUser = userService.saveUser(user);
			} catch (Exception e) {
				log.error("userservice is null" + e, e);
				throw new BusinessException("usersercie rruninng error....");
			}
			if (saveUser == 0) {
				return "504";// 注册失败
			}
			/*
			 * if("883".equals(origin)){ log.info("注册成功，赠送牛币开始..."); // 注册成功送牛币
			 * giveNiuBi(user.getMobile(),
			 * 1,OrderStatusConstant.ORDER_PLATFORM_WAP);
			 * log.info("注册成功，赠送牛币结束。"); }
			 */

		} else {
			return "504";// 注册失败
		}
		// 立即删除指定cookie
		// CookieUtil.deleteCookieNow(response, cookie);
		// return "success";

		// 登陆
		log.info("start to call userService.login ....");
		User user = userService.login(mobile, password);
		log.info("end to call userService.login .");

		if (user == null) {
			log.info("user is null , username or password not correct,userName:"
					+ mobile);
			return "502";// 用户名或密码正确
		} else {
			// MD5加密
			String mdPath = CipherUtil.generatePassword(password.trim());
			if (!user.getPassword().trim().equals(mdPath)) {
				log.info("----------------------password not the same");
				return "502";// 用户名或密码正确
			}

			// 存入cookie
			log.info("start to set cookie....");
			CookieTool.setCookie(response, CommonConstant.SESSION_ID, sid,
					CommonConstant.MAX_AGE_ONE_MONTH);
			log.info("set cookie finished.");

			// 存入缓存
			try {
				log.info("start to set memcached....");
				memcachedClient.setOpTimeout(5000L);
				memcachedClient.set(CommonConstant.CUSTOMER_LOGIN + sid,
						CommonConstant.MEMCACHEDAGE, user);
				log.info("set memcached finished.");
			} catch (Exception e) {
				log.info("set memcached has error." + e.getMessage(), e);
				return "503";// 系统繁忙，请稍后重试
			}
			return "200";// 登陆成功
		}

	}

	/**
	 * 送牛币
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static void giveNiuBi(String mobile, int clientType, Short platform) {

		String requestURL = "https://open.e.189.cn/api/oauth2/llb/grantCoin.do";
		String uuid = String.valueOf(System.currentTimeMillis())
				+ String.valueOf(new Random().nextInt(9999) + 10000);
		String clientId = "8015484906";
		String version = "1.0";
		String timeStamp = String.valueOf(System.currentTimeMillis());
		String authKey = "KeZ1ddNtohBYoaIVifeU5p3vifSzoS8T";

		String taskId;
		int coin;

		if (clientType == 1) {
			coin = 50;
			taskId = "100002901";
		} else {
			coin = 100;
			// taskId = "100002902";
			taskId = "100003163";
		}

		String clienType = "20200";
		if (platform.equals(OrderStatusConstant.ORDER_PLATFORM_PC)) {
			clienType = "10020";
		}
		if (platform.equals(OrderStatusConstant.ORDER_PLATFORM_ANDROID)) {
			clienType = "30100";
		}
		if (platform.equals(OrderStatusConstant.ORDER_PLATFORM_WAP)) {
			clienType = "10010";
		}
		if (platform.equals(OrderStatusConstant.ORDER_PLATFORM_IOS)) {
			clienType = "30300";
		}

		Map<String, String> requestData = new TreeMap<String, String>();

		// 公有参数
		requestData.put("clientId", clientId);
		requestData.put("version", version);
		requestData.put("clientType", clienType);
		requestData.put("timeStamp", timeStamp);// 加上时间戳

		// 私有参数，赠送流量币
		requestData.put("taskId", taskId);
		requestData.put("mobile", mobile);
		requestData.put("coin", String.valueOf(coin));
		requestData.put("uuid", uuid);

		OauthRequestUtil.initComParam(requestData, clientId, version);

		String result = OauthRequestUtil.signPostOpenAPIServer(requestURL,
				requestData, authKey);

		log.info("OauthRequestUtil.signPostOpenAPIServer return :{}", result);

	}

	/**
	 *
	 */
	@Transactional
	public String checkThirdAccount(String openType, String openId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WxUser wx = new WxUser();
		wx.setOpenId(openId);
		wx.setThirdPlatForm(ThirdAccountConstant.convertConstant(openType));
		WxUser wxUser = userService.findWxUser(wx);
		String beforeUrl = "";
		if (null != wxUser) {
			// 登陆
			log.info("已绑定直接登陆");
			String loginresult = checkWxLogin(wxUser, request, response);
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
			return ResponseContant.REDIRECT + beforeUrl + "&openType=" + openType;
		}
		// 未绑定且没有帐号，默认注册一个帐号
		request.setAttribute("openId", openId);

		// 创建消费者
		this.userOpenThirdSaveCustomer(ThirdAccountConstant.convertConstant(openType), request, response);

		wxUser = userService.findWxUser(wx);
		if (null != wxUser) {
			// 登陆
			log.info("已绑定直接登陆");
			String loginresult = checkWxLogin(wxUser, request, response);
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
			return ResponseContant.REDIRECT + beforeUrl + "&openType=" + openType;
		}

		log.error("新建用户wxUser为null");
		return null;
	}



	/**
	 * 检验账户密码登录是否成功
	 * 
	 * @param userName
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */

	public String checkLogin(String userName, String password, String captcha,
			String openId, HttpServletRequest request,
			HttpServletResponse response) {
		String errorCount = null;

		try {
			errorCount = memcachedClient.get("codeErrorCount" + userName);
			if (errorCount != null) {
				int errorCountval = Integer.parseInt(errorCount);
				if (errorCountval > 3 || errorCountval == 3) {// 登陆错误超过三次
					if (captcha != null && "".equals(captcha)) {// 不存在验证码
						log.info("captcha does not exist .");
						return "504";
					}
					String sid = getUUID.getSessionId(request, response);
					String picCode = memcachedClient.get("piccode" + sid);

					if (captcha != null && !"".equals(captcha)
							&& !captcha.equalsIgnoreCase(picCode)) {// 验证码不正确
						log.info("captcha error.login .");
						log.info("login captcha=" + captcha);
						log.info("session captcha=" + picCode);
						return "503";
					}
				}
			}
		} catch (TimeoutException e2) {
			e2.printStackTrace();
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		} catch (MemcachedException e2) {
			e2.printStackTrace();
		}

		log.info("------------password decode ------------");
		log.info("userName:{}", userName);
		String pass = Des.strDec(password, "13800138001");
		log.info("pass:{}", pass);
		log.info("start to call userService.login ....");

		User user = userService.login(userName, pass);

		if (user != null) {
			Supplier supplier = supplierManagerService.findSupplier(Long
					.parseLong(user.getSupplierId()));
			if (supplier != null) {
				user.setType(supplier.getType());
			} else {
				user.setType(0);
			}
			user.setloginType(0);
		}
		log.info("end to call userService.login .");

		// String mdPath = CipherUtil.generatePassword(password.trim());
		String mdPath = CipherUtil.generatePassword(pass);
		if (user == null || !user.getPassword().trim().equals(mdPath)) {
			log.info("user is null , username or password not correct,userName:"
					+ userName);
			return "501";
		} else {
			// 用户被冻结
			if (user.getFreezeStatus() == 1) {
				return "506";
			}

			// String mdPath = CipherUtil.generatePassword(pass.trim());
			// if (!user.getPassword().trim().equals(mdPath)) {
			// log.info("----------------------password not the same");
			// return "501";
			// }

			String sid = getUUID.getSessionId(request, response);

			log.info("start to set cookie....");
			CookieTool.setCookie(response, CommonConstant.SESSION_ID, sid,
					CommonConstant.MAX_AGE_ONE_MONTH);
			log.info("set cookie finished.");

			try {
				// 登录成功后删除登录图形验证码
				memcachedClient.delete("piccode" + sid);
				log.info("start to set memcached....");
				memcachedClient.setOpTimeout(5000L);
				memcachedClient.set(CommonConstant.CUSTOMER_LOGIN + sid,
						CommonConstant.MEMCACHEDAGE, user);
				log.info("set memcached finished.");
			} catch (Exception e) {
				log.info("set memcached has error." + e.getMessage(), e);
				return "500";
			}

			String regIp = request.getHeader("X-Real-IP");
			log.info("用户ip:" + regIp);
			if (StringUtils.isEmpty(regIp)) {
				regIp = request.getRemoteAddr();
			}
			/*
			 * // 调用安全登入接口 ProtectionVO pr = LoginProtection.securityLogin(user,
			 * regIp, "4", "2", "1", null); if(pr != null &&
			 * pr.getCode().equals(0)){ log.info("调用安全登入接口返回信息:" +
			 * JSON.toJSONString(pr)); switch(pr.getLevel()){ case 0:
			 * log.info("调用安全登入接口返回level:正常用户"); break; case 1: case 2:
			 * log.warn(String.format("调用安全登入接口返回level:%s可疑用户,",
			 * pr.getLevel())); break; case 3: case 4:
			 * log.warn(String.format("调用安全登入接口返回level:%s恶意用户,不允许该用户登入",
			 * pr.getLevel())); return "505"; } }
			 */

			try {
				UserOperationRecord userOperationRecord = new UserOperationRecord();
				userOperationRecord.setCreateTime(new Date());
				userOperationRecord.setOperationType("login");
				userOperationRecord.setRemark("用户登入");
				userOperationRecord.setUserId(user.getUserId().intValue());
				userOperationRecordService
						.saveUserOperationRecord(userOperationRecord);
			} catch (Exception e) {
				log.error("记录用户登入操作异常：" + e.getMessage(), e);
			}
			// 记录分享信息
			this.recordShareUser4Login(request, response, user);
			// 绑定微信openid
			if (openId != null && !"".equals(openId)) {
				WxUser wxUser = new WxUser();
				wxUser.setUserId(user.getUserId());
				wxUser.setUserMobile(user.getMobile());
				wxUser.setThirdPlatForm("wx");
				wxUser.setOpenType(Oauth.APPID);
				wxUser.setOpenId(openId);
				// wxUser.setOpenUnionId(openUnionId);
				wxUser.setStatus((byte) 1);
				wxUser.setCreateDateTime(new Date());
				wxUser.setLastUpdateDateTime(new Date());
				userService.saveOpenId(wxUser);
			}

			return "200";
		}
	}

	/**
	 * 检验账户登录是否成功
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	public String checkWxLogin(WxUser wxUser, HttpServletRequest request, HttpServletResponse response) {

		if (wxUser == null) {
			return "507";
		}

		Long userId = wxUser.getUserId();
		User user = userService.findUserById(userId);
		if (user != null) {
			long userspidval = Long.parseLong(user.getSupplierId());
			if (userspidval > 0) {
				Supplier supplier = supplierManagerService
						.findSupplier(userspidval);
				if (supplier != null) {
					user.setType(supplier.getType());
				} else {
					user.setType(0);
				}
			} else {
				user.setType(0);
			}

			if (ThirdAccountConstant.PREFIX_WEIXIN.equalsIgnoreCase(wxUser.getThirdPlatForm())) {
				user.setloginType(1);
			} else if (ThirdAccountConstant.PREFIX_ALIPAY.equalsIgnoreCase(wxUser.getThirdPlatForm())) {
				user.setloginType(2);
			} else {
				user.setloginType(0);
			}

			user.setWeixin(wxUser.getOpenId());

			// 用户被冻结
			if (user.getFreezeStatus() == 1) {
				return "506";
			}
		}

		String sid = getUUID.getSessionId(request, response);
		
		log.info("start to set cookie....");
		CookieTool.setCookie(response, CommonConstant.SESSION_ID, sid, CommonConstant.MAX_AGE_ONE_MONTH);
		log.info("set cookie finished.");
		try {
			log.info("start to set memcached....");
			memcachedClient.setOpTimeout(5000L);
			memcachedClient.set(CommonConstant.CUSTOMER_LOGIN + sid, CommonConstant.MAX_AGE_ONE_MONTH, user);
			log.info("set memcached finished.");
		} catch (Exception e) {
			log.info("set memcached has error." + e.getMessage(), e);
		}
		
		try {
			UserOperationRecord userOperationRecord = new UserOperationRecord();
			userOperationRecord.setCreateTime(new Date());
			userOperationRecord.setOperationType("login");
			userOperationRecord.setUserId(userId.intValue());

			if (ThirdAccountConstant.PREFIX_WEIXIN.equalsIgnoreCase(wxUser.getThirdPlatForm())) {
				userOperationRecord.setRemark("用户微信登入");
			} else if (ThirdAccountConstant.PREFIX_ALIPAY.equalsIgnoreCase(wxUser.getThirdPlatForm())) {
				userOperationRecord.setRemark("用户支付宝登入");
			} else {
				userOperationRecord.setRemark("用户普通登入");
			}
			userOperationRecordService.saveUserOperationRecord(userOperationRecord);
			log.info("记录用户登入成功");
		} catch (Exception e) {
			log.error("记录用户登入操作异常：" + e.getMessage(), e);
		}
		
		// 更新用户登陆时间
		wxUser.setLastUpdateDateTime(new Date());
		userService.updateWxUser(wxUser);
		log.info("更新微信用户登陆时间成功");
		return "200";
	}
	
	public String login(HttpServletRequest request,
			HttpServletResponse response, User user) {
		
		if (user != null) {
			long userSupIdVal = Long.parseLong(user.getSupplierId());
			if (userSupIdVal > 0) {
				Supplier supplier = supplierManagerService
						.findSupplier(userSupIdVal);
				if (supplier != null) {
					user.setType(supplier.getType());
				} else {
					user.setType(0);
				}
			} else {
				user.setType(0);
			}
		}
		
		if (user.getFreezeStatus() == 1) {
			return "506";
		}
		
		String sid = getUUID.getSessionId(request, response);
		log.info("start to set cookie....");
		CookieTool.setCookie(response, CommonConstant.SESSION_ID, sid,
				CommonConstant.MAX_AGE_ONE_MONTH);
		log.info("set cookie finished.");
		try {
			log.info("start to set memcached....");
			memcachedClient.setOpTimeout(5000L);
			memcachedClient.set(CommonConstant.CUSTOMER_LOGIN + sid,
					CommonConstant.MEMCACHEDAGE, user);
			log.info("set memcached finished.");
		} catch (Exception e) {
			log.info("set memcached has error." + e.getMessage(), e);
			return "500";
		}
		return "200";
	}

	/**
	 * 发送验证码(找回密码)
	 * 
	 * @param model
	 * @param username
	 * @param request
	 * @return
	 */
	public String sendCheckItentiyCode(Model model, String username,
			HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(username)) {
			log.info(" username  is null ");
			return "userNameNull"; // 用户名或者手机号码为i空
		}
		User exitUser = getUserByUserName(username);
		if (exitUser == null) {
			log.info("user is null ");
			return "userNotExist"; // 用户不存在
		}
		String mobile = exitUser.getMobile();
		if (StringUtils.isEmpty(mobile)) {
			log.info("mobile is null  ");
			return "mobileIsNull"; // 用户手机号为空
		}
		String sid = getUUID.getSessionId(request, response);
		String ifMessageLose = "";
		try {
			ifMessageLose = memcachedClient
					.get(CommonConstant.SEND_MESSAGE_LOSE + sid
							+ exitUser.getUserName());
		} catch (Exception e) {
			log.info("memcacheClient get  error" + e, e);
		}
		if (!StringUtils.isEmpty(ifMessageLose)
				&& "messageLose".equals(ifMessageLose)) {

			log.info("send message too frequent。。" + mobile);
			return "repeatSend"; // 已经发送过验证码 请勿重复发送
		}
		Integer mobileCodeNew = SendSMSUtil.sendMessage(mobile);
		if (mobileCodeNew != null) {
			try {
				log.info("username :" + username + " ----mobile:" + mobile
						+ "---mobilecode:" + mobileCodeNew);

				// 缓存用户名，验证时 比对
				memcachedClient.setOpTimeout(5000L);
				memcachedClient.set(
						CommonConstant.SEND_MESSAGE + exitUser.getUserName()
								+ "username", CommonConstant.MEMCACHEDAGE,
						username);
				// 缓存验证码
				memcachedClient.set(
						CommonConstant.SEND_MESSAGE + exitUser.getUserName(),
						CommonConstant.MEMCACHEDAGE, mobileCodeNew);

				// 缓存是否过期的标识
				memcachedClient
						.set(CommonConstant.SEND_MESSAGE_LOSE
								+ exitUser.getUserName(),
								CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE,
								"messageLose");

			} catch (Exception e) {
				log.info("send message error...." + username);
			}
			return "success"; // 发送成功
		}
		return "error";
	}

	/**
	 * 发送验证码(修改手机)
	 * 
	 * @param model
	 * @param username
	 * @param request
	 * @return
	 */
	public String sendCheckItentiyCodeMobile(Model model, String newMobile,
			String username, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(username)) {
			log.info(" username  is null ");
			return "userNameNull"; // 用户名或者手机号码为i空
		}
		User exitUser = getUserByUserName(username);
		if (exitUser == null) {
			log.info("user is null ");
			return "userNotExist"; // 用户不存在
		}
		String mobile = newMobile;
		if (StringUtils.isEmpty(mobile)) {
			log.info("mobile is null  ");
			return "mobileIsNull"; // 用户手机号为空
		}
		if (isUserExist(mobile)) {
			log.info("mobile is exist");
			return "mobileisexist";// 用户手机号已被注册
		}
		// String sid = request.getSession().getId();
		String sid = getUUID.getSessionId(request, response);
		String ifMessageLose = "";
		try {
			ifMessageLose = memcachedClient
					.get(CommonConstant.SEND_MESSAGE_LOSE + sid
							+ exitUser.getUserName());
		} catch (Exception e) {
			log.info("memcacheClient get  error" + e, e);
		}
		if (!StringUtils.isEmpty(ifMessageLose)
				&& "messageLose".equals(ifMessageLose)) {
			log.info("send message too frequent。。" + mobile);
			return "repeatSend"; // 已经发送过验证码 请勿重复发送
		}
		// Integer mobileCodeNew = SendSMS.sendMessage(mobile);
		Integer mobileCodeNew = SendSMSUtil.sendMessage(mobile);
		if (mobileCodeNew != null) {
			try {
				log.info("username :" + username + " ----mobile:" + mobile
						+ "---mobilecode:" + mobileCodeNew);
				// 缓存用户名，验证时 比对
				memcachedClient.set(
						CommonConstant.SEND_MESSAGE + exitUser.getUserName()
								+ "username", CommonConstant.MEMCACHEDAGE,
						username);
				// 缓存验证码
				memcachedClient.setOpTimeout(5000L);
				memcachedClient.set(
						CommonConstant.SEND_MESSAGE + exitUser.getUserName(),
						CommonConstant.MEMCACHEDAGE, mobileCodeNew);
				// 缓存是否过期的标识
				memcachedClient.setOpTimeout(5000L);
				memcachedClient
						.set(CommonConstant.SEND_MESSAGE_LOSE
								+ exitUser.getUserName(),
								CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE,
								"messageLose");
			} catch (Exception e) {
				log.info("send message error...." + username);
			}
			return "success"; // 发送成功
		}
		return "error";
	}

	/**
	 * 校验验证码（找回密码）
	 * 
	 * @param username
	 * @param msgCode
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String validMsgCode(String username, String msgCode, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("validMsgCode execute....");
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(msgCode)) {
			log.info("username or msgCode is null");
			model.addAttribute("msg", "用户名或者验证码为空");
			return "msgCodeNull"; // 用户名 或者 手机验证嘛为空
		}
		User exitUser = getUserByUserName(username);
		if (exitUser == null) {
			log.info("existUser is null");
			model.addAttribute("msg", "不存在当前账户的用户");
			return "existUserNull"; // 不存在当前账户的用户
		}
		String mobile = exitUser.getMobile();
		if (StringUtils.isEmpty(mobile)) {
			log.info("moblie  is null");
			model.addAttribute("msg", "手机号不存在");
			return "mobileNull"; // 手机号不存在
		}
		// String sid = request.getSession().getId();
		String sid = getUUID.getSessionId(request, response);
		Integer memMsgCode = null;
		String memUsername = null;
		try {
			// 获取缓存的用户名
			memUsername = memcachedClient.get(CommonConstant.SEND_MESSAGE
					+ exitUser.getUserName() + "username");
			// 获取缓存的验证码
			memMsgCode = memcachedClient.get(CommonConstant.SEND_MESSAGE
					+ exitUser.getUserName());

		} catch (Exception e) {
			log.info("memcacheClient running error..." + e, e);
		} finally {
			if (memMsgCode == null || memUsername == null) {
				model.addAttribute("msg", "验证码已过期，请重新获取。");
				return "505";
			}
		}
		// 在此输入的用户名和缓存的用户名不符
		if (!username.equals(memUsername)) {
			model.addAttribute("msg", "用户名和验证码不符");
			return "validNull"; // 验证失败
		}

		// 验证码输入不正确
		int msgCodeInt;
		try {
			msgCodeInt = Integer.parseInt(msgCode);
		} catch (Exception e) {
			model.addAttribute("msg", "验证码输入不正确");
			return "500";
		}
		if (memMsgCode.intValue() != msgCodeInt) {
			model.addAttribute("msg", "验证码输入不正确");
			return "501";
		}

		// 确实验证码正确
		try {
			CookieTool.setCookie(response, CommonConstant.SESSION_ID, sid,
					CommonConstant.MAX_AGE_ONE_MONTH);
			memcachedClient.setOpTimeout(5000L);
			memcachedClient.set("newpass" + sid, CommonConstant.MEMCACHEDAGE,
					exitUser);
			/*
			 * memcachedClient.delete(CommonConstant.SEND_MESSAGE + sid +
			 * exitUser.getUserName());
			 */
			memcachedClient.delete(CommonConstant.SEND_MESSAGE + sid
					+ exitUser.getUserName() + "username");
			memcachedClient.delete(CommonConstant.SEND_MESSAGE + sid
					+ exitUser.getUserName());
			memcachedClient.delete(CommonConstant.SEND_MESSAGE_LOSE + sid
					+ exitUser.getUserName());
		} catch (Exception e) {
			log.info("memcacheClient running error..." + e, e);
		}
		model.addAttribute("msg", "校验成功");
		return "success"; // 验证成功
	}

	/**
	 * 修改密码
	 * 
	 * @param username
	 * @param pass
	 * @param passConfirm
	 * @param request
	 * @return
	 */
	public String newPassWord(String username, String pass, String passConfirm,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("username :" + username);
		User user = getUserByUserName(username);
		if (user == null) {
			return "用户名不存在";
		} else {
			username = user.getUserName();
		}
		String contextPath = request.getContextPath();
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(pass)
				|| StringUtils.isEmpty(passConfirm)) {
			log.info("  pass is null");
			return "密码为空";
		}
		if (!pass.equals(passConfirm)) {
			log.info("pass and confirm not equal...");
			return "两次输入不一样";
		}
		// sid="";
		// Cookie cookie = CookieTool.getCookie(request,
		// CommonConstant.SESSION_ID);
		// sid=cookie!=null?cookie.getValue():"";
		// String sid = request.getSession().getId();
		String sid = getUUID.getSessionId(request, response);
		if (!StringUtils.isEmpty(sid)) {
			log.info("sid：{" + sid + "}");
			User exitUser = null;
			try {
				exitUser = memcachedClient.get("newpass" + sid);
			} catch (Exception e1) {
				log.error("memecha running errror :" + e1, e1);
			}
			if (exitUser == null || !username.equals(exitUser.getUserName())) {
				log.info("请求异常或当前的页面已经过期  username" + username
						+ "--exitUsername" + exitUser.getUserName());
				throw new BadRequestException("请求异常或当前的页面已经过期");
			}

			boolean ifUpdate = false;
			try {
				ifUpdate = RemoteServiceSingleton
						.getInstance()
						.getUserService()
						.modifyPassword(exitUser.getUserId(),
								exitUser.getPassword(), pass);
				memcachedClient.delete("newpass" + sid);
				if (ifUpdate) {
					try {
						UserOperationRecord userOperationRecord = new UserOperationRecord();
						userOperationRecord.setCreateTime(new Date());
						userOperationRecord.setOperationType("modifyPassowrd");
						userOperationRecord.setRemark("修改密码");
						userOperationRecord.setUserId(exitUser.getUserId()
								.intValue());
						userOperationRecordService
								.saveUserOperationRecord(userOperationRecord);
					} catch (Exception e) {
						log.error("记录用户修改操作异常：" + e.getMessage(), e);
					}
				}
			} catch (Exception e) {
				log.info("username");
				throw new BusinessException(" call userService error...", e);
			}
			return ifUpdate ? "modifyOk=" + contextPath + "/customer/logout"
					: "modifyError";
		}
		return "error";
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookie = CookieTool
				.getCookie(request, CommonConstant.SESSION_ID);

		if (cookie != null && !StringUtils.isEmpty(cookie.getValue())) {
			String sid = cookie.getValue();
			try {
				try {
					User user = memcachedClient
							.get(CommonConstant.CUSTOMER_LOGIN + sid);
					UserOperationRecord userOperationRecord = new UserOperationRecord();
					userOperationRecord.setCreateTime(new Date());
					userOperationRecord.setOperationType("loginOut");
					userOperationRecord.setRemark("用户登出");
					userOperationRecord.setUserId(user.getUserId().intValue());
					userOperationRecordService
							.saveUserOperationRecord(userOperationRecord);
				} catch (Exception e) {
					log.error("记录用户登入操作异常：" + e.getMessage(), e);
				}

				memcachedClient.delete(CommonConstant.CUSTOMER_LOGIN + sid);
			} catch (Exception e) {
				log.error("memecha runing error.." + e, e);
			}
		}
		CookieTool.setCookie(response, "logout", "logout",
				CommonConstant.MAX_AGE_ONE_MONTH);
		return "";
	}

	/**
	 * 跳转登录界面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	public String toLoginPage(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		/*
		 * String url = request.getParameter("returnUrl");
		 * log.info("history url "+url);
		 */
		Cookie cookie = CookieTool.getCookie(request, "logout");
		if (cookie != null) {
			// 是切换用户 需要跳到主页
			CookieUtil.deleteCookie(response, cookie);
			// model.addAttribute("returnUrl", "");
		} else {
			/* model.addAttribute("returnUrl", url); */
		}
		return "";
	}

	/**
	 * 跳转注册页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String toRegisterPage(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookie = CookieUtil.getCookie(request,
				CommonConstant.ORIGIN_COOKIE_NAME);
		if (cookie != null) {
			String res = null;
			String cookieValue = cookie.getValue();
			log.info("user origin cookie value is : " + cookieValue);
			int parseInt = Integer.parseInt(cookieValue);
			switch (parseInt) {
			case 8:// 欢购一赠二
				res = ResponseContant.REDIRECT
						+ "/customerActivity/toRegistHappy";
				CookieUtil.setCookie(response,
						CommonConstant.ORIGIN_COOKIE_NAME, cookieValue, 0);// 立即删除cookie
				break;
			case 903:// 中行
				res = ResponseContant.REG_ACCOUNT;
				break;
			}

			return res;
		}

		String returnUrl = request.getParameter("returnUrl");
		if (returnUrl != null) {
			model.addAttribute("returnUrl", returnUrl);
		}
		String openId = request.getParameter("openId");
		if (openId != null) {
			model.addAttribute("openId", openId);
		}
		String origin = null;
		try {
			origin = request.getParameter("origin");
		} catch (Exception e) {
			log.info("普通用户注册", e);
		}
		if (origin.trim() != "" && origin != null) {
			model.addAttribute("origin", origin);
		}
		String toPay = request.getParameter("toPay");
		if (toPay != null) {
			model.addAttribute("toPay", toPay);
		}
		return ResponseContant.REG_ACCOUNT;
	}

	/**
	 * 获取注册的验证码(注册)
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String getRegCode(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		String openId = request.getParameter("openId");
		String renzheng = request.getParameter("renzheng");
		if (mobile == null || "".equals(mobile)) {
			log.info("bad register mobile can not be null");
			throw new BadRequestException("mobile can not be null");
		}

		// 获取传过来的验证码
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
		// 比对传入的验证码和缓存中的验证码是否一致
		if (!captcha.equalsIgnoreCase(piccode_regist)) {
			try {
				memcachedClient.delete("piccode_regist" + sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "captchaerror";
		}

		boolean existUser = false;
		try {
			existUser = isUserExist(mobile);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (existUser) {
			if (renzheng == null || "".equals(renzheng)) {
				return "mobileExist";
			}
		}
		String ifMessageLose = "";
		try {
			ifMessageLose = memcachedClient
					.get(CommonConstant.SEND_REG_MESSAGE_LOSE + sid);
		} catch (Exception e) {
			log.info("memcacheClient get  error" + e, e);
			return "";
		}
		if (!StringUtils.isEmpty(ifMessageLose)
				&& "messageLose".equals(ifMessageLose)) {
			log.info("send message too frequent。。");
			return "repeatSend"; // 已经发送过验证码 请勿重复发送
		}
		Integer msgCode = SendSMSUtil.sendMessage(mobile);

		if (msgCode != null) {
			try {
				log.info("mobile :" + mobile + "---msgCode:" + msgCode);
				// 缓存电话
				memcachedClient.setOpTimeout(5000L);
				memcachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile
						+ "mobile", CommonConstant.MEMCACHEDAGE, mobile);

				// 缓存验证码
				memcachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile,
						CommonConstant.MEMCACHEDAGE, msgCode);

				memcachedClient
						.set(CommonConstant.SEND_REG_MESSAGE_LOSE + sid,
								CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE,
								"messageLose");

			} catch (Exception e) {
				log.info("send message error...." + mobile);
			}

			return "success"; // 发送成功
		}

		return "error";
	}

	/**
	 * 活动注册 获取注册手机验证码
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param userValidate
	 *            是否校验用户手机号已注册，true校验， false不校验
	 * @return
	 */
	public String getRegCodeActivity(Model model, HttpServletRequest request,
			HttpServletResponse response, boolean userValidate) {
		String mobile = request.getParameter("mobile");
		String origin = request.getParameter("origin");// 来源不同，短信内容可以不同
		log.info("getRegCodeActivity parameter origin:" + origin);
		if (StringUtils.isBlank(mobile)) {
			log.info("bad register mobile can not be blank");
			throw new BadRequestException("电话不能为空");
		}

		if (userValidate) {
			boolean existUser = false;
			try {
				existUser = isUserExist(mobile);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (existUser) {
				return "mobileExist";
			}
		}
		String sid = getUUID.getSessionId(request, response);

		if (ORIGIN_WEIPIAO.equals(origin)) { // 微票
			String captcha = request.getParameter("captcha");
			if (StringUtils.isEmpty(captcha)) {
				log.info("getRegCodeActivity captcha is null and parameter mobile is: "
						+ mobile);
				throw new BadRequestException(
						"getRegCodeActivity captcha is null ");
			}
			String piccode_regist = null;
			try {
				piccode_regist = memcachedClient.get("piccode_regist" + sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 比对传入的验证码和缓存中的验证码是否一致
			if (!captcha.equalsIgnoreCase(piccode_regist)) {
				try {
					memcachedClient.delete("piccode_regist" + sid);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "captchaError";
			}
		}

		String ifMessageLose = "";
		try {
			ifMessageLose = memcachedClient
					.get(CommonConstant.SEND_REG_MESSAGE_LOSE + sid);
		} catch (Exception e) {
			log.info("memcacheClient get  error" + e, e);
			return "";
		}
		if (!StringUtils.isEmpty(ifMessageLose)
				&& "messageLose".equals(ifMessageLose)) {
			log.info("send message too frequent。。");
			return "repeatSend"; // 已经发送过验证码 请勿重复发送
		}
		Integer msgCode = SendSMSUtil.sendMessage(mobile, origin);
		if (msgCode != null) {
			try {
				log.info("mobile :" + mobile + "---msgCode:" + msgCode);
				memcachedClient.setOpTimeout(5000L);
				// 缓存电话
				memcachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile
						+ "mobile", CommonConstant.MEMCACHEDAGE, mobile);
				// 缓存验证码
				memcachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile,
						CommonConstant.MEMCACHEDAGE, msgCode);
				// 缓存指定字符串60秒
				memcachedClient
						.set(CommonConstant.SEND_REG_MESSAGE_LOSE + sid,
								CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE,
								"messageLose");
			} catch (Exception e) {
				log.info("send message error...." + mobile);
			}
			return "success"; // 发送成功
		}

		return "error";
	}

	public String getRepeattRegCode(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		if (mobile == null || "".equals(mobile)) {
			log.info("bad register mobile can not be null");
			throw new BadRequestException("电话不能为空");
		}

		Integer cout = null;
		try {
			cout = memcachedClient.get("captch_cout" + mobile);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if (cout == null) {
			try {
				memcachedClient.set("captch_cout" + mobile, 60 * 4, 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (cout > 3) {
				log.info("cout > 3.................." + cout);
				return "coutFull";
			}
			cout += 1;
			try {
				memcachedClient.set("captch_cout" + mobile, 60 * 4, cout);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String sid = getUUID.getSessionId(request, response);
		boolean existUser = false;
		try {
			existUser = isUserExist(mobile);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (existUser) {
			return "mobileExist";
		}
		String ifMessageLose = "";
		try {
			ifMessageLose = memcachedClient
					.get(CommonConstant.SEND_REG_MESSAGE_LOSE + sid);
		} catch (Exception e) {
			log.info("memcacheClient get  error" + e, e);
			return "";
		}
		if (!StringUtils.isEmpty(ifMessageLose)
				&& "messageLose".equals(ifMessageLose)) {
			log.info("send message too frequent。。");
			return "repeatSend"; // 已经发送过验证码 请勿重复发送
		}
		/* Integer msgCode = SendSMS.sendMessage(mobile); */
		Integer msgCode = SendSMSUtil.sendMessage(mobile);
		if (msgCode != null) {
			try {
				log.info("mobile :" + mobile + "---msgCode:" + msgCode);
				// 缓存电话
				memcachedClient.setOpTimeout(5000L);
				memcachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile
						+ "mobile", CommonConstant.MEMCACHEDAGE, mobile);
				// 缓存验证码
				memcachedClient.setOpTimeout(5000L);
				memcachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile,
						CommonConstant.MEMCACHEDAGE, msgCode);
				memcachedClient.setOpTimeout(5000L);
				memcachedClient
						.set(CommonConstant.SEND_REG_MESSAGE_LOSE + sid,
								CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE,
								"messageLose");
			} catch (Exception e) {
				log.info("send message error...." + mobile);
			}
			return "success"; // 发送成功
		}

		return "error";
	}

	/**
	 * 校验注册的验证码（注册）
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String validRegCode(HttpServletRequest request,
			HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		String openId = request.getParameter("openId");
		// String msgReqCode = request.getParameter("msgReqCode");
		String captcha = request.getParameter("captcha");
		// String tjMobile = request.getParameter("tjMobile");
		if (captcha == null || "".equals(captcha)) {
			log.info("bad request vailidateRegCode captcha....");
			throw new BadRequestException("请求错误请填写图形验证码");
		}

		if (mobile == null || "".equals(mobile)) {
			log.info("bad request username....");
			throw new BadRequestException("请求错误，请填写手机号");
		}
		// if (tjMobile == null || "".equals(tjMobile)) {
		// log.info("bad request msgReqCode....");
		// throw new BadRequestException("请求错误请填写手机邀请码");
		// }
		// if (msgReqCode == null || "".equals(msgReqCode)) {
		// log.info("bad request msgReqCode....");
		// throw new BadRequestException("请求错误请填写手机验证码");
		// }

		boolean exitusername = isUserExist(mobile);
		if (exitusername) {
			log.info("mobile has already exist");
			return "505";
		}
		// String isShare = request.getParameter("isShare");
		// log.info("start decrypt tjMobile =: " + tjMobile);
		// if (StringUtils.isNotEmpty(isShare) &&
		// StringUtils.isNotEmpty(tjMobile)) {
		// try {
		// String tmpMobile = tjMobile;
		// tjMobile = AESUtil.decrypt(URLDecoder.decode(tmpMobile, "UTF-8"));
		// log.info("URLDecoder decrypt tjMobile =: " + tjMobile);
		// if(StringUtils.isEmpty(tjMobile)){
		// tjMobile = AESUtil.decrypt(tmpMobile);
		// log.info(" decrypt tjMobile =: " + tjMobile);
		// }
		// } catch (Exception e) {
		// log.error("decrypt fail", e);
		// throw new RuntimeException("decrypt fail ");
		// }
		// }
		// log.info("end decrypt tjMobile =: " + tjMobile);
		// boolean isTjMobile = isTJUserExists(tjMobile);
		// if (!isTjMobile) {
		// log.info("tjmobile has not exist");
		// return "506";
		// }
		String sid = getUUID.getSessionId(request, response);
		Integer msgCode = null;
		// String memMobile = null;
		String piccode_regist = null;
		try {
			piccode_regist = memcachedClient.get("piccode_regist" + sid);

			msgCode = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE
					+ mobile);

			// memMobile = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE +
			// mobile + "mobile");

		} catch (Exception e) {
			log.info("memcache running error.." + e, e);
			throw new BusinessException("memcache running error.." + e);
		}
		// 比对传入的验证码和缓存中的验证码是否一致
		if (!captcha.equalsIgnoreCase(piccode_regist)) {
			try {
				memcachedClient.delete("piccode_regist" + sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "502";
		}
		// 输入时的电话和 缓存中的电话不一致
		// if (!mobile.equals(memMobile)) {
		// return "501";
		// }

		// if (!msgReqCode.equals(msgCode + "")) {
		// return "500";
		// }

		// 校验成功
		try {
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE + sid);
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE_LOSE);
			memcachedClient.delete("piccode_regist" + sid);
			memcachedClient.setOpTimeout(5000L);
			memcachedClient.set("register" + sid,
					CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE, mobile);
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}
		log.info("校验成功  mobile :" + mobile);
		return "200";
	}

	/**
	 * 认证校验注册的验证码（注册）
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String renZhengValidRegCode(HttpServletRequest request,
			HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		String openId = request.getParameter("openId");
		// String msgReqCode = request.getParameter("msgReqCode");
		String captcha = request.getParameter("captcha");
		// String tjMobile = request.getParameter("tjMobile");
		if (captcha == null || "".equals(captcha)) {
			log.info("bad request vailidateRegCode captcha....");
			throw new BadRequestException("请求错误请填写图形验证码");
		}

		if (mobile == null || "".equals(mobile)) {
			log.info("bad request username....");
			throw new BadRequestException("请求错误，请填写手机号");
		}

		// boolean exitusername = isUserExist(mobile);

		String sid = getUUID.getSessionId(request, response);
		Integer msgCode = null;
		String piccode_regist = null;
		try {
			piccode_regist = memcachedClient.get("piccode_regist" + sid);

			msgCode = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE
					+ mobile);

		} catch (Exception e) {
			log.info("memcache running error.." + e, e);
			throw new BusinessException("memcache running error.." + e);
		}
		// 比对传入的验证码和缓存中的验证码是否一致
		if (!captcha.equalsIgnoreCase(piccode_regist)) {
			try {
				memcachedClient.delete("piccode_regist" + sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "502";
		}

		// 校验成功
		try {
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE + sid);
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE_LOSE);
			memcachedClient.delete("piccode_regist" + sid);
			memcachedClient.setOpTimeout(5000L);
			memcachedClient.set("register" + sid,
					CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE, mobile);
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}
		log.info("校验成功  mobile :" + mobile);
		return "200";
	}

	/**
	 * 保存用户信息
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String saveCustomer(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		String openId = request.getParameter("openId");
		// String tjMobile = request.getParameter("tjMobile");
		// String isShare = request.getParameter("isShare");
		// log.info("start decrypt tjMobile =: " + tjMobile);
		Map<String, Object> data = new HashMap<String, Object>();
		// if (StringUtils.isNotEmpty(isShare) &&
		// StringUtils.isNotEmpty(tjMobile)) {
		// try {
		// String tmpMobile = tjMobile;
		// tjMobile = AESUtil.decrypt(URLDecoder.decode(tmpMobile, "UTF-8"));
		// log.info("URLDecoder decrypt tjMobile =: " + tjMobile);
		// if(StringUtils.isEmpty(tjMobile)){
		// tjMobile = AESUtil.decrypt(tmpMobile);
		// log.info(" decrypt tjMobile =: " + tjMobile);
		// }
		// } catch (Exception e) {
		// log.error("decrypt fail", e);
		// throw new RuntimeException("decrypt fail ");
		// }
		// }
		// log.info("end decrypt tjMobile =: " + tjMobile);
		// 获取cookie中的值,电信活动
		String origin = CookieUtil.getCookieValue(request, "channel");
		log.info("getCookieValue channel origin value is : " + origin);
		if (StringUtils.isBlank(origin)) {
			origin = CookieUtil.getCookieValue(request,
					CommonConstant.ORIGIN_COOKIE_NAME);// 中航
			log.info("getCookieValue origin value is : " + origin);
		}
		if (StringUtils.isBlank(origin)) {
			origin = getChannelOrigin(request);
			log.info("getChannelOrigin origin value is : " + origin);// 通用活动cookie获取用户来源
		}
		// 获取用户渠道cookie中的值
		String originEasyPay = CookieUtil.getCookieValue(request, "c_r_");
		log.info("c_r_ cookie value is : " + originEasyPay);
		// 获取cookie
		Cookie cookie = CookieUtil.getCookie(request, "channel");
		log.info("cookie = " + cookie + "value = " + origin);
		String sid = getUUID.getSessionId(request, response);
		try {
			if (origin == null || "".equals(origin)) {// 获取缓存的origin的值
				origin = memcachedClient.get("userOrigin" + sid);
				memcachedClient.delete("userOrigin" + sid);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String username = ProduceUserName.produce(mobile);
		String pass = request.getParameter("pass");
		pass = Des.strDec(pass, "13800138001");
		if (StringUtils.isEmpty(mobile)) {
			log.info("mobile is null ");
			throw new BadRequestException("user mobile  is null ");
		}
		if (StringUtils.isEmpty(pass)) {
			log.info("pass is null ");
			throw new BadRequestException("pass is null ");
		}

		// String sid = request.getSession().getId();
		String sessionUsername = null;
		try {
			sessionUsername = memcachedClient.get("register" + sid);
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}
		if (sessionUsername != null && sessionUsername.equals(mobile)) {
			// 这个用户名和密码是一样的
			User user = new User();
			user.setUserName(username);
			user.setPassword(pass);
			user.setMobile(mobile);
			user.setNickName(username);
			user.setRegType((byte) 0);
			// user.setTjMobile(tjMobile);
			// user.setSupplierId(userService.findUserByMobile(tjMobile).getSupplierId());
			if (origin != null && origin != "") {
				user.setOrigin(origin);
			} else if (originEasyPay != null && YUE_ME.equals(originEasyPay)) {// 来源悦me
				user.setOrigin(ORIGIN_YUE_ME);
			} else {
				user.setOrigin(null);
			}
			int saveUser = 0;
			try {
				saveUser = userService.saveUser(user);
				// 绑定微信openid
				if (openId != null && !"".equals(openId)) {
					WxUser wxUser = new WxUser();
					// 通过手机号查询userId
					User findUserByMobile = userService
							.findUserByMobile(mobile);
					wxUser.setUserId(findUserByMobile.getUserId());
					wxUser.setUserMobile(user.getMobile());
					wxUser.setThirdPlatForm("wx");
					wxUser.setOpenType(Oauth.APPID);
					wxUser.setOpenId(openId);
					// User.setOpenUnionId(openUnionId);
					wxUser.setStatus((byte) 1);
					wxUser.setCreateDateTime(new Date());
					wxUser.setLastUpdateDateTime(new Date());
					userService.saveOpenId(wxUser);
				}
			} catch (Exception e) {
				log.error("userservice is null" + e, e);
				throw new BusinessException("usersercie rruninng error....");
			}
			if (saveUser == 0) {
				data.put("retCode", "error");
				return "error";
			}
		} else {
			return "error";
		}
		// 立即删除指定cookie
		CookieUtil.deleteCookieNow(response, cookie);

		// 设置分享用户
		User newUser = userService.findUserByMobile(mobile);
		recordShareUser4Register(request, response, newUser);

		return "success";
	}
	
	/**
	 * 认证保存用户信息
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	public String renZhengsaveCustomer(Model model, HttpServletRequest request,
			HttpServletResponse response) throws TimeoutException,
			InterruptedException, MemcachedException {
		String mobile = request.getParameter("mobile");
		String openId = request.getParameter("openId");
		
		Map<String, Object> data = new HashMap<String, Object>();
		// 获取cookie中的值,电信活动
		String origin = CookieUtil.getCookieValue(request, "channel");
		log.info("getCookieValue channel origin value is : " + origin);
		if (StringUtils.isBlank(origin)) {
			origin = CookieUtil.getCookieValue(request,
					CommonConstant.ORIGIN_COOKIE_NAME);// 中航
			log.info("getCookieValue origin value is : " + origin);
		}
		if (StringUtils.isBlank(origin)) {
			origin = getChannelOrigin(request);
			log.info("getChannelOrigin origin value is : " + origin);// 通用活动cookie获取用户来源
		}
		// 获取用户渠道cookie中的值
		String originEasyPay = CookieUtil.getCookieValue(request, "c_r_");
		log.info("c_r_ cookie value is : " + originEasyPay);
		// 获取cookie
		Cookie cookie = CookieUtil.getCookie(request, "channel");
		log.info("cookie = " + cookie + "value = " + origin);
		String sid = getUUID.getSessionId(request, response);
		try {
			if (origin == null || "".equals(origin)) {// 获取缓存的origin的值
				origin = memcachedClient.get("userOrigin" + sid);
				memcachedClient.delete("userOrigin" + sid);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		String beforeUrl = memcachedClient.get("beforUrl" + sid);
		log.info("beforeUrl2 is" + beforeUrl);

		String username = ProduceUserName.produce(mobile);
		String pass = request.getParameter("pass");
		pass = Des.strDec(pass, "13800138001");
		if (StringUtils.isEmpty(mobile)) {
			log.info("mobile is null ");
			throw new BadRequestException("user mobile  is null ");
		}
		if (StringUtils.isEmpty(pass)) {
			log.info("pass is null ");
			throw new BadRequestException("pass is null ");
		}
		boolean exitusername = isUserExist(mobile);

		// 这个用户名和密码是一样的
		User user = new User();
		user.setUserName(username);
		user.setPassword(pass);
		user.setMobile(mobile);
		user.setNickName(username);
		user.setRegType((byte) 0);
		if (origin != null && origin != "") {
			user.setOrigin(origin);
		} else if (originEasyPay != null && YUE_ME.equals(originEasyPay)) {// 来源悦me
			user.setOrigin(ORIGIN_YUE_ME);
		} else {
			user.setOrigin(null);
		}
		int saveUser = 0;
		try {
			if (!exitusername) {
				saveUser = userService.saveUser(user);
			}

			User findUserByMobile = userService.findUserByMobile(mobile);
			// 绑定微信openid
			if (openId != null && !"".equals(openId)
					&& findUserByMobile != null) {
				WxUser wxUser = new WxUser();
				// 通过手机号查询userId

				wxUser.setUserId(findUserByMobile.getUserId());
				wxUser.setUserMobile(user.getMobile());
				wxUser.setThirdPlatForm("wx");
				wxUser.setOpenType(Oauth.APPID);
				wxUser.setOpenId(openId);
				// User.setOpenUnionId(openUnionId);
				wxUser.setStatus((byte) 1);
				wxUser.setCreateDateTime(new Date());
				wxUser.setLastUpdateDateTime(new Date());
				userService.saveOpenId(wxUser);

				// 立即删除指定cookie
				CookieUtil.deleteCookieNow(response, cookie);
				// 设置分享用户
				User newUser = findUserByMobile;
				recordShareUser4Register(request, response, newUser);
			}
		} catch (Exception e) {
			log.error("userservice is null" + e, e);
			throw new BusinessException("usersercie rruninng error....");
		}
		return beforeUrl;
	}
	
	/**
	 * 自动注册保存用户信息
	 * 
	 * @param openType
	 * @param request
	 * @param response
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void userOpenThirdSaveCustomer(String openType, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String openId = (String) request.getAttribute("openId");

        Objects.requireNonNull(openType, "openType不可为null");
        Objects.requireNonNull(openId, "openId不可为null");

		// 获取cookie中的值
		String origin = CookieUtil.getCookieValue(request, "channel");
		log.info("getCookieValue channel origin value is : " + origin);
		if (StringUtils.isBlank(origin)) {
			origin = CookieUtil.getCookieValue(request, CommonConstant.ORIGIN_COOKIE_NAME);// 中航
			log.info("getCookieValue origin value is : " + origin);
		}
		if (StringUtils.isBlank(origin)) {
			origin = getChannelOrigin(request);
			log.info("getChannelOrigin origin value is : " + origin);// 通用活动cookie获取用户来源
		}

		// 获取用户渠道cookie中的值
		String originEasyPay = CookieUtil.getCookieValue(request, "c_r_");
		log.info("c_r_ cookie value is : " + originEasyPay);
		// 获取cookie
		Cookie cookie = CookieUtil.getCookie(request, "channel");
		log.info("cookie = " + cookie + "value = " + origin);
		String sid = getUUID.getSessionId(request, response);

		if (origin == null || "".equals(origin)) {// 获取缓存的origin的值
			origin = memcachedClient.get("userOrigin" + sid);
			memcachedClient.delete("userOrigin" + sid);
		}
		// 拼接
		String mobile = openType + "_" + openId;
		// 通过手机号查询userId
		User findUserByMobile = userService.findUserByMobile(mobile);

		if (findUserByMobile == null || findUserByMobile.getUserId() == null || findUserByMobile.getUserId() == 0l) {

			User user = new User();
			user.setUserName(openId);
			user.setPassword("57506EF410FA337E7FC6B4A873597FA4");
			user.setMobile(mobile);
			if(openType.equals(ThirdAccountConstant.PREFIX_WEIXIN)){
				user.setNickName("众聚猫微信会员");
			}else if(openType.equals(ThirdAccountConstant.PREFIX_ALIPAY)){
				user.setNickName("众聚猫支付宝会员");
			}
			user.setRegType((byte) 0);
			user.setOrigin(origin);
			if (StringUtils.isEmpty(origin) && YUE_ME.equals(originEasyPay)) {// 来源悦me
				user.setOrigin(ORIGIN_YUE_ME);
			}

			int saveUser = userService.saveUser(user);
			if (saveUser == 0) {
				throw new Exception("添加众聚猫会员失败");
			}
		}

		User newUser = userService.findUserByMobile(mobile);
		if (!"".equals(openId) && newUser != null) {
			Date date = new Date();
			WxUser wxUser = new WxUser();
			wxUser.setUserId(newUser.getUserId());
			wxUser.setUserMobile(newUser.getMobile());
			wxUser.setThirdPlatForm(openType);
			wxUser.setOpenId(openId);
			wxUser.setStatus((byte) 1);
			wxUser.setCreateDateTime(date);
			wxUser.setLastUpdateDateTime(date);
            if (ThirdAccountConstant.PREFIX_ALIPAY.equals(openType)) {
                wxUser.setOpenType(aliServiceWindow.getAppId());
            } else if (ThirdAccountConstant.PREFIX_WEIXIN.equals(openType)) {
                wxUser.setOpenType(Oauth.APPID);
            }
            userService.saveOpenId(wxUser);
		}
		// 立即删除指定cookie
		CookieUtil.deleteCookieNow(response, cookie);
		// 设置分享用户
		recordShareUser4Register(request, response, newUser);
	}

	/**
	 * 核对旧密码
	 * 
	 * @param oldpass
	 * @param username
	 * @return 正确返回true 错误返回false
	 */
	public boolean checkOldPass(String oldpass, String username) {
		String password = CipherUtil.generatePassword(oldpass);
		User user = new User();
		user.setPassword(password);
		user.setUserName(username);
		User res = userService.findUser(user);
		if (res != null) {
			return true;
		}
		return false;
	}

	/**
	 * 根据用户名获取用户
	 * 
	 * @param username
	 * @return
	 */
	public User getUserByUserName(String username) {
		log.info("getUserByUserName  execute....");
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		userService = RemoteServiceSingleton.getInstance().getUserService();
		User user = new User();
		/*
		 * String myreg = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$";
		 * if (username.matches(myreg)) { user.setMobile(username); } else {
		 * user.setUserName(username); }
		 */
		user.setMobile(username);
		User exitUser = userService.findUser(user);
		return exitUser;
	}

	/**
	 * 更具用户名获取用户
	 * 
	 * @param username
	 * @return
	 */
	private User getUserByName(String username) {
		log.info("getUserByName  execute....");
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		userService = RemoteServiceSingleton.getInstance().getUserService();
		User user = new User();
		user.setUserName(username);
		User exitUser = userService.findUser(user);
		return exitUser;
	}

	public User findUserById(Long userId) {
		userService = RemoteServiceSingleton.getInstance().getUserService();
		User user = userService.findUserById(userId);
		return user;
	}

	/**
	 * 检查手机号是否存在
	 * 
	 * @param mobile
	 * @return 返回true：手机号已存在，返回false：手机号不存在。
	 */
	private boolean isUserExist(String mobile) {
		userService = getUserService();
		User user = new User();
		user.setUserName(mobile);
		user.setMobile(mobile);
		user.setNickName(mobile);
		String exists = userService.isUserExists(user);
		if (!exists.equals("false")) {
			return true;
		}
		return false;
	}

	/**
	 * 获取userservice服务接口
	 * 
	 * @return
	 */
	private UserService getUserService() {
		userService = RemoteServiceSingleton.getInstance().getUserService();
		return userService;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	public String updateUser(User user, HttpServletRequest request,
			HttpServletResponse response) {
		userService = getUserService();
		int updateRes = 0;
		try {
			updateRes = userService.updateUserWithUserId(user);
		} catch (Exception e) {
			log.error("call userserve error.." + e, e);
			String message = e.getMessage();
			return "updateErrorMsg:" + message;
		}
		if (updateRes == 1) {
			User exitUser = getUserByName(user.getUserName());
			exitUser.setloginType(user.getloginType());
			exitUser.setType(user.getType());
			exitUser.setWeixin(user.getWeixin());
			// String sid = request.getSession().getId();
			String sid = getUUID.getSessionId(request, response);
			CookieTool.setCookie(response, CommonConstant.SESSION_ID, sid,
					CommonConstant.MAX_AGE_ONE_MONTH);
			try {
				memcachedClient.setOpTimeout(5000L);
				memcachedClient.set(CommonConstant.CUSTOMER_LOGIN + sid,
						CommonConstant.MAX_AGE_ONE_MONTH, exitUser);
			} catch (Exception e) {
				log.info("memcacheClient running error...." + e.getMessage(), e);
				throw new BusinessException("memcach running erroir......" + e,
						e); // 系统异常
			}
		}
		return updateRes == 1 ? "updateSuccess" : "updateError";
	}

	/**
	 * 实名认证
	 * 
	 * @param currentUser
	 * @param model
	 * @param request
	 * @param response
	 * @param currentPage
	 * @return
	 */
	public String verifyInfo(User currentUser, Model model,
			HttpServletRequest request, HttpServletResponse response,
			String currentPage) {
		userService = getUserService();
		int updateUserWithUserId = 0;
		try {
			updateUserWithUserId = userService
					.updateUserWithUserId(currentUser);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (updateUserWithUserId == 1) {
			User exitUser = getUserByName(currentUser.getUserName());
			exitUser.setloginType(currentUser.getloginType());
			exitUser.setType(currentUser.getType());
			exitUser.setWeixin(currentUser.getWeixin());
			// String sid = request.getSession().getId();
			String sid = getUUID.getSessionId(request, response);
			CookieTool.setCookie(response, CommonConstant.SESSION_ID, sid,
					CommonConstant.MAX_AGE_ONE_MONTH);
			try {
				memcachedClient.setOpTimeout(5000L);
				memcachedClient.set(CommonConstant.CUSTOMER_LOGIN + sid,
						CommonConstant.MAX_AGE_ONE_MONTH, exitUser);
			} catch (Exception e) {
				log.info("memcacheClient running error...." + e.getMessage(), e);
				throw new BusinessException("memcach running erroir......" + e,
						e); // 系统异常
			}
		}

		if (!StringUtils.isBlank(currentPage)) {
			return updateUserWithUserId == 1 ? currentPage : "error";
		} else {
			return updateUserWithUserId == 1 ? "ok" : "error";
		}
	}

	public void validateOrigin(Model model, String origin) {
		if (origin != null && !"".equals(origin)) {
			if (origin.equals(ORIGIN1))
				model.addAttribute("origin", origin);
			else if (origin.equals(ORIGIN2))
				model.addAttribute("origin", origin);
			else if (origin.equals(ORIGIN3))
				model.addAttribute("origin", origin);
			else if (origin.equals(ORIGIN4))
				model.addAttribute("origin", origin);
			else {
				throw new BadRequestException("origin ont in 1234");
			}
		} else {
			throw new BadRequestException("origin is null");
		}
	}

	/*
	 * private UserTriggerDto getUserTriggerDto(User user) { UserTriggerDto
	 * userTriggerDto = new UserTriggerDto();
	 * userTriggerDto.setUserid(user.getUserId()); Long orig =
	 * Long.valueOf(ORIGIN_WEIPIAO); userTriggerDto.setChannelCode(orig); return
	 * userTriggerDto; }
	 */

	public String toLoginMessage(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String sid = getUUID.getSessionId(request, response);
		// 获取缓中的用户信息
		try {
			User user = memcachedClient
					.get(CommonConstant.CUSTOMER_LOGIN + sid);
			if (user != null) {
				String userName = user.getUserName();
				return userName;
			} else {
				return "userLogout";
			}
		} catch (Exception e) {
			log.info("get memcachedClient user error");
		}
		return "error";
	}

	private String getChannelOrigin(HttpServletRequest request) {
		Cookie cookie = CookieUtil.getCookie(request,
				Constants.USERORIGIN_COOKIES_KEY);
		if (cookie != null) {
			String origin = cookie.getValue();
			return ChannelOrigin.getOrigin(origin);
		}
		return null;
	}

	/**
	 * 登陆记录分享者ID
	 * 
	 * @param request
	 * @param response
	 */
	public void recordShareUser4Login(HttpServletRequest request,
			HttpServletResponse response, User user) {

		Cookie cookie = CookieUtil.getCookie(request,
				CommonConstant.SHARE_USER_ID);
		if (null == cookie) {
			return;
		}

		String shareUserId = cookie.getValue();
		if (StringUtils.isEmpty(shareUserId)) {
			return;
		}

		shareUserService.saveShareUser(user.getUserId(),
				Long.valueOf(shareUserId), 1L);
	}

	/**
	 * 注册记录分享者ID
	 * 
	 * @param request
	 * @param response
	 */
	public void recordShareUser4Register(HttpServletRequest request,
			HttpServletResponse response, User user) {

		Cookie cookie = CookieUtil.getCookie(request,
				CommonConstant.SHARE_USER_ID);
		if (null == cookie) {
			return;
		}

		String shareUserId = cookie.getValue();
		if (StringUtils.isEmpty(shareUserId)) {
			return;
		}

		shareUserService.saveShareUser(user.getUserId(),
				Long.valueOf(shareUserId), 0L);
	}

	/**
	 * 记录分享者Cookie
	 * 
	 * @param request
	 * @param response
	 */
	public String recordShareUserIdInCookie(HttpServletRequest request,
			HttpServletResponse response, User user) {
		String shareUserId = request.getParameter("shareUserId");
		if (StringUtils.isEmpty(shareUserId)) {
			log.info("share user id is null.");
			return "failure";
		}
		CookieUtil.setCookie(response, CommonConstant.SHARE_USER_ID,
				shareUserId, CommonConstant.MAX_AGE_ONE_DAY);

		if (null != user && null != user.getUserId()) {
			try {
				shareUserService.saveShareUser(user.getUserId(),
						Long.valueOf(shareUserId), 1L);
			} catch (Exception e) {
				log.info("racord share user ralatin failed.");
				log.info("{}", e);
			}
		}

		return "success";
	}

	/**
	 * 活动发送手机验证码 不验证是否注册
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String getRegCodeActivityUC(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		String origin = request.getParameter("origin");// 来源不同，短信内容可以不同
		log.info("getRegCodeActivity parameter origin:" + origin);
		if (StringUtils.isBlank(mobile)) {
			log.info("bad register mobile can not be blank");
			throw new BadRequestException("电话不能为空");
		}

		// 获取传过来的验证码
		String captcha = request.getParameter("captcha");
		if (StringUtils.isEmpty(captcha)) {
			log.info("captcha is null ");
			return "captchaerror";
		}
		String sid = getUUID.getSessionId(request, response);
		String piccode_regist = null;
		try {
			piccode_regist = memcachedClient.get("piccode_regist" + sid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 比对传入的验证码和缓存中的验证码是否一致
		if (!captcha.equalsIgnoreCase(piccode_regist)) {
			try {
				memcachedClient.delete("piccode_regist" + sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "captchaerror";
		}

		String ifMessageLose = "";
		try {
			ifMessageLose = memcachedClient
					.get(CommonConstant.SEND_REG_MESSAGE_LOSE + sid);
		} catch (Exception e) {
			log.info("memcacheClient get  error" + e, e);
			return "";
		}
		if (!StringUtils.isEmpty(ifMessageLose)
				&& "messageLose".equals(ifMessageLose)) {
			log.info("send message too frequent。。");
			return "repeatSend"; // 已经发送过验证码 请勿重复发送
		}
		Integer msgCode = SendSMSUtil.sendMessage(mobile, origin);
		if (msgCode != null) {
			try {
				log.info("mobile :" + mobile + "---msgCode:" + msgCode);
				memcachedClient.setOpTimeout(5000L);
				// 缓存电话
				memcachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile
						+ "mobile", CommonConstant.MEMCACHEDAGE, mobile);
				// 缓存验证码
				memcachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile,
						CommonConstant.MEMCACHEDAGE, msgCode);
				// 缓存指定字符串60秒
				memcachedClient
						.set(CommonConstant.SEND_REG_MESSAGE_LOSE + sid,
								CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE,
								"messageLose");
			} catch (Exception e) {
				log.info("send message error...." + mobile);
			}
			return "success"; // 发送成功
		}

		return "error";
	}

	/**
	 * 活动发送手机验证码 不验证是否注册 无图片验证码
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String getIPTVRegCodeActivityUC(Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		String origin = request.getParameter("origin");// 来源不同，短信内容可以不同
		log.info("getRegCodeActivity parameter origin:" + origin);
		if (StringUtils.isBlank(mobile)) {
			log.info("bad register mobile can not be blank");
			throw new BadRequestException("电话不能为空");
		}

		// 获取传过来的验证码
		String captcha = request.getParameter("captcha");
		if (StringUtils.isEmpty(captcha)) {
			log.info("captcha is null ");
			// return "captchaerror";
		}
		String sid = getUUID.getSessionId(request, response);
		/*
		 * String piccode_regist = null; try { piccode_regist =
		 * memcachedClient.get("piccode_regist" + sid); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		// 比对传入的验证码和缓存中的验证码是否一致
		/*
		 * if (!captcha.equalsIgnoreCase(piccode_regist)) { try {
		 * memcachedClient.delete("piccode_regist" + sid); } catch (Exception e)
		 * { e.printStackTrace(); } return "captchaerror"; }
		 */

		String ifMessageLose = "";
		try {
			ifMessageLose = memcachedClient
					.get(CommonConstant.SEND_REG_MESSAGE_LOSE + sid);
		} catch (Exception e) {
			log.info("memcacheClient get  error" + e, e);
			return "";
		}
		if (!StringUtils.isEmpty(ifMessageLose)
				&& "messageLose".equals(ifMessageLose)) {
			log.info("send message too frequent。。");
			return "repeatSend"; // 已经发送过验证码 请勿重复发送
		}
		Integer msgCode = SendSMSUtil.sendMessage(mobile, origin);
		if (msgCode != null) {
			try {
				log.info("mobile :" + mobile + "---msgCode:" + msgCode);
				memcachedClient.setOpTimeout(5000L);
				// 缓存电话
				memcachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile
						+ "mobile", CommonConstant.MEMCACHEDAGE, mobile);
				// 缓存验证码
				memcachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile,
						CommonConstant.MEMCACHEDAGE, msgCode);
				// 缓存指定字符串60秒
				memcachedClient
						.set(CommonConstant.SEND_REG_MESSAGE_LOSE + sid,
								CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE,
								"messageLose");
			} catch (Exception e) {
				log.info("send message error...." + mobile);
			}
			return "success"; // 发送成功
		}

		return "error";
	}

	/**
	 * 记录来源 验证手机号
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param b
	 * @return
	 */
	public String registCodeJsonpUC(Model model, HttpServletRequest request,
			HttpServletResponse response, boolean b) {
		String mobile = request.getParameter("mobile");
		String msgReqCode = request.getParameter("code");
		String origin = request.getParameter("origin");// 来源不同，短信内容可以不同

		if (mobile == null || "".equals(mobile)) {
			log.info("bad request username....");
			throw new BadRequestException("请求错误，请填写手机号");
		}
		if (msgReqCode == null || "".equals(msgReqCode)) {
			log.info("bad request msgReqCode....");
			throw new BadRequestException("请求错误请填写手机验证码");
		}

		String sid = getUUID.getSessionId(request, response);
		Integer msgCode = null;
		String memMobile = null;
		try {

			msgCode = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE
					+ mobile);

			memMobile = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE
					+ mobile + "mobile");

		} catch (Exception e) {
			log.info("memcache running error.." + e, e);
			throw new BusinessException("memcache running error.." + e);
		}

		// 输入时的电话和 缓存中的电话不一致
		if (!mobile.equals(memMobile)) {
			return "501";
		}

		if (!msgReqCode.equals(msgCode + "")) {
			return "500";
		}

		// 校验成功
		try {
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE + sid);
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE_LOSE);
			memcachedClient.delete("piccode_regist" + sid);
			memcachedClient.setOpTimeout(5000L);
			memcachedClient.set("register" + sid,
					CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE, mobile);
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}

		// 验证成功记录来源
		if (origin != null)
			userAppOriginService.addUserAppOrigin(mobile,
					Integer.valueOf(origin));

		log.info("校验成功  mobile :" + mobile);
		return "200";
	}

	/**
	 * 找回密码>服务:验证图形验证码
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, Object> validFindPwdCode(HttpServletRequest request,
			HttpServletResponse response) {
		//
		Map<String, Object> msgMap = new HashMap<String, Object>();
		// 手机号
		String mobile = request.getParameter("mobile");
		// 图形验证码值
		String captcha = request.getParameter("captcha");
		if (org.apache.commons.lang3.StringUtils.isEmpty(mobile)) {
			msgMap.put("msg", "请填写手机号");
		} else if (org.apache.commons.lang3.StringUtils.isEmpty(captcha)) {
			msgMap.put("msg", "请填写图形验证码");
		} else if (!isUserExist(mobile)) {
			msgMap.put("msg", "未注册用户");
		} else {
			// 找回密码验证码key = "piccode_find_pwd" + sid
			String sid = getUUID.getSessionId(request, response);
			String val_key = "piccode_find_pwd" + sid;
			String captcha_eq = null;
			try {
				captcha_eq = memcachedClient.get(val_key);
			} catch (Exception e) {
				log.info("memcache running error.." + e, e);
				throw new BusinessException("memcache running error.." + e);
			}
			// 比对传入的验证码和缓存中的验证码是否一致
			if (!captcha.equalsIgnoreCase(captcha_eq)) {
				// equalsIgnoreCase 不考虑大小写
				try {
					memcachedClient.delete(val_key);
				} catch (Exception e) {
					log.error("memcachedClient.delete 异常!", e);
				}
				// 提示验证码错误
				msgMap.put("msg", "验证码错误");
				;
			} else {
				// 校验成功
				try {
					memcachedClient.delete(val_key);
					// 设置本次操作等待时间5s
					memcachedClient.setOpTimeout(5000L);
				} catch (Exception e) {
					log.error("memcache running error...." + e, e);
					throw new BusinessException("memcache running error...."
							+ e);
				}
				log.info("校验成功  mobile :" + mobile);

				// 设置短信权限
				String token = setAndGetSMStokenForSession(sid, mobile);
				if (!org.apache.commons.lang3.StringUtils.isEmpty(token)) {
					// put isOk = true
					msgMap.put("isOk", "true");
				}
				msgMap.put("token", token);
			}
		}
		return msgMap;
	}

	/**
	 * 获取发送手机短信token对应的key
	 * 
	 * @param sessionid
	 * @return
	 */
	private String getSMStokenForSessionKey(String sessionid) {
		return "piccode_find_pwd_token" + sessionid;
	}

	/**
	 * 设置并获取获取手机短信令牌
	 * 
	 * @param sessionid
	 * @param userid
	 *            用户唯一标示
	 * @param var
	 * @return
	 */
	private String setAndGetSMStokenForSession(String sessionid, String userid) {
		String token = null;
		try {
			token = getSMStokenVal(sessionid, userid);
			// piccode_change_pwd:找回密码，3600*24=1day
			memcachedClient.set(getSMStokenForSessionKey(sessionid), 3600 * 24,
					token);
		} catch (Exception e) {
			log.error("memcachedClient.delete 异常!", e);
		}
		return token;
	}

	/**
	 * 删除手机短信令牌
	 * 
	 * @param seesionid
	 * @param id
	 * @param var
	 * @return
	 */
	public void deleteSMStokenForSession(String sessionid) {
		// 校验成功
		try {
			memcachedClient.delete(getSMStokenForSessionKey(sessionid));
			// 设置本次操作等待时间5s
			memcachedClient.setOpTimeout(5000L);
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}
	}

	/**
	 * 获取session中的token
	 * 
	 * @param seesionid
	 * @return
	 */
	public String getSMStokenForSession(String sessionid) {
		String token = null;
		// 获取token值
		try {
			token = memcachedClient.get(getSMStokenForSessionKey(sessionid));
			// 设置本次操作等待时间5s
			memcachedClient.setOpTimeout(5000L);
		} catch (Exception e) {
			log.error("memcache running error...." + e, e);
			throw new BusinessException("memcache running error...." + e);
		}
		return token;
	}

	/**
	 * 获取手机短信令牌,验证时候可以通过 该方法与 旧的token比较
	 * 
	 * @param sessionid
	 * @param userid
	 * @return
	 */
	public String getSMStokenVal(String sessionid, String userid) {
		// 加入混淆字符,smstokenval_,再进行2次加密
		return DigestUtils.md5Hex("smstokenval_"
				+ DigestUtils.md5Hex(sessionid + "_" + userid));
	}

	/**
	 * 检查手机号是否存在
	 * 
	 * @param mobile
	 * @return 返回true：手机号已存在，返回false：手机号不存在。
	 */
	private boolean isTJUserExists(String mobile) {
		userService = getUserService();
		User user = new User();
		user.setMobile(mobile);
		String exists = userService.isTJUserExists(user);
		if (!exists.equals("false")) {
			return true;
		}
		return false;
	}

	/**
	 * 检查openid是否存在
	 * 
	 * @param mobile
	 * @return 返回true：手机号已存在，返回false：手机号不存在。
	 */
	public boolean isOpenIdExists(String openId) {
		userService = getUserService();
		WxUser wxUser = new WxUser();
		wxUser.setOpenId(openId);
		String exists = userService.isOpenIdExists(wxUser);
		if (!exists.equals("false")) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		String pwd1 = CipherUtil.generatePassword("123456q".trim());
		String pwd2 = CipherUtil.generatePassword("123456q");
		System.out.println(CipherUtil.generatePassword("123456q"));
		System.err.println(pwd1);
		System.err.println(pwd2);
	}
}
