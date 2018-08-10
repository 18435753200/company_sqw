/**
 * 
 */
package com.mall.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mall.annotation.AuthPassport;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.customer.model.User;
import com.mall.pay.common.StringUtils;
import com.mall.utils.CookieTool;
import com.mall.utils.getUUID;

/**
 * @author zhengqiang.shi 2015年3月23日 上午9:46:04
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory
			.getLogger(AuthInterceptor.class);
	@Autowired
	private MemcachedClient memcachedClient;

	// 购物车相关地址
	private static List<String> cartUrlList = new ArrayList<String>();
	
	// 不作为登录回跳的请求地址
	private static List<String> historyOrNot = new ArrayList<String>(30);

	// 开始执行时间
	private long ExecuteStartTime = 0l;

	private String executeMethod;

	// 静态文件服务器
	@Value("${staticFile_s}")
	protected String staticFile_s;

	public AuthInterceptor() {
		if (cartUrlList.isEmpty()) {
			log.info("init cartUrlList...");
			cartUrlList.add("/cart/update");
			cartUrlList.add("/cart/delete");
			cartUrlList.add("/cart/calcute");
		}
		
		if(historyOrNot.isEmpty()){
			log.info("init historyOrNot...");
			//前往领取红包页面
			historyOrNot.add("toGetRedPacket");
			//前往欢购注册页面
			historyOrNot.add("toRegistHappy");
			//前往输入验证码
			historyOrNot.add("toInputCode");
			//获取验证码
			historyOrNot.add("getImage");
			// 银行回调
			historyOrNot.add("payResultBack");
			// 退出
			historyOrNot.add("toLogin");
			// 登录
			historyOrNot.add("login");
			// 支付
			historyOrNot.add("payInfo");
			// 跳转修改密码页
			historyOrNot.add("toGetpass");
			// 校验找回密码验证码
			historyOrNot.add("validCode");
			// 校验注册验证码
			historyOrNot.add("validRegCode");
			historyOrNot.add("renZhengValidRegCode");
			// 提交密码
			historyOrNot.add("newPass");
			// 保存用户
			historyOrNot.add("saveCustomer");
			historyOrNot.add("renZhengsaveCustomer");
			// 获取注册验证码
			historyOrNot.add("toRegGetCode");
			// 获取找回密码验证码
			historyOrNot.add("toGetCode");
			// 跳转注册
			historyOrNot.add("toRegister");
			// 注册跳转设置密码页
			historyOrNot.add("toSetPassword");
			// 检验用户名是否存在
			historyOrNot.add("checkUserRealName");
			// 消费者扫码支付登录
			historyOrNot.add("/customer/userToPay");
			//mp或商家扫码登录
			historyOrNot.add("/customer/mpOrMallLogin");
			// 流量宝
			historyOrNot.add("/customerActivity/toRegistFlowRate");
			// 微信扫码注册
			historyOrNot.add("/Qr/isWxScan");

            // 支付宝扫码登陆
            historyOrNot.add("/customer/thirdAuthorize");
			
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		// 开始执行时间
		ExecuteStartTime = System.currentTimeMillis();
		// 执行方法名称
		executeMethod = request.getRequestURL().toString();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();
		
		// 登录地址
		//https:
		String loginUrl = "https://m.zhongjumall.com/customer/toLogin";
		
//		String loginUrl = basePath + RequestContant.CUSTOMER
//				+ RequestContant.CUSTOMER_TO_LOGIN;
		log.debug("request url:" + request.getRequestURI());
		
		Cookie logoutcookie = CookieTool.getCookie(request, "logout");
		if (logoutcookie != null) {
			String sid = getUUID.getSessionId(request, response);
			memcachedClient.delete(CommonConstant.CUSTOMER_LOGIN + sid);
			String value = logoutcookie.getValue();
			CookieTool.setCookie(response, "logout", value, 0);//立即删除cookie
		}

		historyBack(request, response, basePath);
		
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			AuthPassport authPassport = ((HandlerMethod) handler)
					.getMethodAnnotation(AuthPassport.class);
			if (authPassport == null || !authPassport.validate()) {
				log.debug("This url don't need to log in to access.");
				return true;
			} else {
				log.debug("This url need to log in to access.");
				User user = getCurrentUser(request, response);
				if (user == null) {
					log.debug("The user is not logged in");
                     
					if (isAjaxRequest(request)) {
						log.debug("ajax request return <needLogin>");
						PrintWriter writer = response.getWriter();
						writer.write("{\"status\":\"needLogin\"}");
						writer.flush();
						return false;
					} else {
						log.debug("normal request send redirect to index");
						response.sendRedirect(loginUrl);
						return false;
					}
				}else{
					Cookie cookie = CookieTool.getCookie(request, CommonConstant.SESSION_ID);
					if (cookie != null && !StringUtils.isEmpty(cookie.getValue())) {
						String sid = cookie.getValue();

						memcachedClient.setOpTimeout(5000L);
						memcachedClient.set(CommonConstant.CUSTOMER_LOGIN + sid, CommonConstant.MAX_AGE_ONE_MONTH, user);

						CookieTool.setCookie(response, CommonConstant.SESSION_ID, sid, CommonConstant.MAX_AGE_ONE_MONTH);
					}
				}
				
				log.info("The user has the login");
				return true;
			}
		}

		return true;
	}

	/**
	 * 缓存登录前路径
	 * 
	 * @param request
	 */
	private void historyBack(HttpServletRequest request, HttpServletResponse response, String basePath) {

		// HttpSession session = request.getSession();
		// String sid=session.getId();
		String sid = getUUID.getSessionId(request, response);

		// 获取无参的请求路径
		String url = request.getRequestURI();
		
		// 获取请求参数
		String parames = request.getQueryString();

		try {
			
			String beforUrl = "";
			// 登录回调地址
			String returnUrl = returnUrl(request, url);
			if(StringUtils.isNotBlank(returnUrl)){
				beforUrl = returnUrl;
				memcachedClient.set("beforUrl" + sid, CommonConstant.MEMCACHEDAGE,
						beforUrl);
				
				return;
			}

			// 不作为登陆成功回跳的路径
			if (isExistStr(historyOrNot, url)) {
				log.debug("====>>请求路径为<" + url + ">，不作为登录成功回跳的路径 。");
				return;
			}
			
			// 购物车
			if (isExistStr(cartUrlList, url)) {
				// 购物车首页地址作为回跳地址
				String cartIndex = basePath + RequestContant.CART
						+ RequestContant.CART_INDEX;
				beforUrl = cartIndex;
			}

			if (StringUtils.isEmpty(parames)) {
				beforUrl = url;
			} else {
				beforUrl = url + "?" + parames;
			}

			if (url.indexOf("/customer/logout") != -1) {
				log.debug("请求地址为/customer/logout ，删除memcached ,key:beforeUrl"
						+ sid);
				memcachedClient.delete("beforUrl" + sid);
				return;
			}
			log.debug("beforeUrl is  ----:" + beforUrl);
			
			// 判断为是否为ajax请求,不为ajax请求存入请求地址
			if(isAjaxRequest(request)){
				log.info("判断为是否为ajax请求,不为ajax请求存入请求地址 .request is ajax .");
				return;
			}
			memcachedClient.set("beforUrl" + sid, CommonConstant.MEMCACHEDAGE,
					beforUrl);

		} catch (Exception e) {
			log.info(
					"execute method <historyBack> has errors.message:"
							+ e.getMessage(), e);
		}

	}

	private String returnUrl(HttpServletRequest request,String url){
		
		String returnUrl = "";
		
		if(url.indexOf("/customer/toLogin") != -1){
			returnUrl = request.getParameter("returnUrl");
			log.info("return url:{}",returnUrl);
		}
		
		return returnUrl;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// super.postHandle(request, response, handler, modelAndView);
		User user = getCurrentUser(request, response);
		if (user != null) {
			request.setAttribute("exitUser", user);
		}

		// 静态文件服务器
		request.setAttribute("staticFile_s", staticFile_s);

	}

	private User getCurrentUser(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie cookie = CookieTool.getCookie(request, CommonConstant.SESSION_ID);
		if (cookie != null && !StringUtils.isEmpty(cookie.getValue())) {
			String sid = cookie.getValue();
			User user = null;
			try {
				user = memcachedClient.get(CommonConstant.CUSTOMER_LOGIN + sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (user != null) {
				return user;
			}
		}
		return null;
	}

	/**
	 * 在过滤器返回给前端前一步执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long executeEndTime = System.currentTimeMillis();
		log.debug("===>> <" + executeMethod + ">的执行时间为："
				+ (executeEndTime - ExecuteStartTime) + "毫秒。");
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	}

	/**
	 * 是否 ajax 请求
	 * 
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
				.getHeader("X-Requested-With") != null && request.getHeader(
				"X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			return false;
		}

		return true;
	}

	private boolean isExistStr(List<String> listUrl, String url) {
		for (String string : listUrl) {
			if (url.indexOf(string) != -1) {
				return true;
			}
		}

		return false;
	}
}
