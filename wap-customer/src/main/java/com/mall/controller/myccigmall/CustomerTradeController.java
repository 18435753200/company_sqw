/**
 * 
 */
package com.mall.controller.myccigmall;

import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mall.annotation.AuthPassport;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;
import com.mall.pay.common.StringUtils;
import com.mall.service.TradeService;
import com.mall.utils.Constants;
import com.mall.utils.SendSMSUtil;
import com.mall.utils.SensitiveInfoUtils;
import com.mall.utils.VerifyCodeUtils;
import com.mall.utils.getUUID;
import com.mall.vo.TradeVO;

import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * @author cyy
 */
@Controller
@RequestMapping(value = RequestContant.CUS_TRADE)
public class CustomerTradeController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(CustomerTradeController.class);

	@Autowired
	private TradeService tradeService;

	/**
	 * 显示支付密码设置页面
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_TRADE_TO_SETTING, method = RequestMethod.GET)
	public String toSetting(HttpServletRequest request, Model model) {
		String uri = request.getParameter("uri");
		request.setAttribute("uri", uri);
		try {
			log.info("toSetting info start...");
			User userInfo = getCurrentUser(request);
			if (userInfo == null) {
				log.info("userinfo  is null");
				return RequestContant.CUSTOMER_TO_LOGIN;
			}
			String mobile = userInfo.getMobile();
			if(mobile.length()!=11){
				model.addAttribute("userId", userInfo.getUserId());
				return "/qrcode/updateMobile";
			}
			mobile = SensitiveInfoUtils.phoneNumHyposensitize(mobile);
			request.setAttribute("mobile", mobile);
			TradeVO tradeVO = tradeService.getTrade(userInfo);
			if ("0".equals(tradeVO.getRetCode())) {
				String returnUrl = request.getParameter("returnUrl");
				String orderId = request.getParameter("orderId");
				String act = request.getParameter("act");
				request.setAttribute("returnUrl", returnUrl);
				request.setAttribute("orderId", orderId);
				request.setAttribute("act", act);
				return ResponseContant.CUS_TRADE_SETTING;
			} else {
				return ResponseContant.CUS_TRADE_UPDATE_FORM;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 支付密码发送短信验证码
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_SNED_TRADE_CAPTCHA, method = RequestMethod.GET)
	@ResponseBody
	public String sendTradeCaptcha(HttpServletRequest request, Model model) {
		log.info("sendTradeCaptcha info start...");
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			log.info("userinfo  is null");
			return "501"; // 请先登入
		}
		try {
			String LoseEfficacy = memcachedClient.get("trade_captcha_LoseEfficacy" + userInfo.getMobile());
			if (LoseEfficacy != null && "LoseEfficacy".equals(LoseEfficacy)) {
				log.info("发送短信验证码过于频繁");
				return "发送短信验证码过于频繁";
			}
		} catch (TimeoutException e1) {
			log.error("获取 memcached验证码超时", e1);
		} catch (InterruptedException e1) {
			log.error("获取 memcached验证码失败", e1);
		} catch (MemcachedException e1) {
			log.error("获取 memcached验证码失败", e1);
		}
		Integer value = SendSMSUtil.sendTradeMessage(userInfo.getMobile());// 发送短信,返回值为验证码的值
		if (null != value) {
			log.info("验证码=" + value);
			try {
				memcachedClient.set("trade_captcha_mobileCode" + userInfo.getMobile(), Constants.MESSAGE_OUT_TIME,
						value);
				memcachedClient.set("trade_captcha_LoseEfficacy" + userInfo.getMobile(), Constants.LOSE_EFFICACY_TIME,
						"LoseEfficacy");
			} catch (Exception e) {
				log.error("验证码缓存失败+", e);
				return "验证码发送失败!";
			}
			return "success";
		} else {
			return "验证码发送失败!";
		}
	}

	/**
	 * 设置支付密码
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_SET_TRADE, method = RequestMethod.POST)
	@ResponseBody
	public String setTrade(HttpServletRequest request, Model model) {
		log.info("setTrade start...");
		User userInfo = getCurrentUser(request);
		TradeVO trade = null;
		if (userInfo == null) {
			log.info("userinfo  is null");
			trade = new TradeVO();
			trade.setRetCode("501");
			trade.setRetInfo("请先登入");
		} else {
			trade = tradeService.setTrade(request, userInfo);
		}
		return JSON.toJSONString(trade);
	}

	/**
	 * 找回密码页面
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_TRADE_RETRIEVE, method = RequestMethod.GET)
	public String toRetrieve(HttpServletRequest request, Model model) {
		log.info("toSetting info start...");
		String uri = request.getParameter("uri");
		request.setAttribute("uri", uri);
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			log.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		String mobile = userInfo.getMobile();
		mobile = SensitiveInfoUtils.phoneNumHyposensitize(mobile);
		request.setAttribute("mobile", mobile);
		return ResponseContant.CUS_TRADE_RETRIEVE;
	}

	@RequestMapping(value = "/getImageCaptcha")
	public void getImageCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(5);

		String sid = getUUID.getSessionId(request, response);
		memcachedClient.set("trade_image_captcha" + sid, 3600 * 24, verifyCode.toLowerCase());
		// 生成图片
		int w = 230, h = 80;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}

	/**
	 * 校验图片验证码
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "validImageCaptcha")
	@ResponseBody
	public String validImageCaptcha(HttpServletRequest request, HttpServletResponse response) {
		String sid = getUUID.getSessionId(request, response);
		String imageCaptcha = request.getParameter("imageCaptcha");
		if (StringUtils.isEmpty(imageCaptcha)) {
			return "501";// 验证码为空
		}
		String trade_image_captcha = null;
		try {
			trade_image_captcha = memcachedClient.get("trade_image_captcha" + sid);
		} catch (Exception e) {
			log.info("memcache running error.." + e, e);
			return "503";
		}
		// 比对传入的验证码和缓存中的验证码是否一致
		if (!imageCaptcha.equalsIgnoreCase(trade_image_captcha)) {
			try {
				memcachedClient.delete("trade_image_captcha" + sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "502";
		}
		return "200";
	}
}
