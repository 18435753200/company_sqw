package com.mall.service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mall.constant.CommonConstant;
import com.mall.customer.model.User;
import com.mall.exception.BadRequestException;
import com.mall.exception.BusinessException;
import com.mall.pay.common.StringUtils;
//import com.mall.promotion.dto.b2c.UserTriggerDto;
import com.mall.utils.getUUID;

/**
 * 用戶活動service
 * @author Administrator
 *
 */
@Service
public class CustomerActivityService extends CustomerBaseService{

	private static final Logger log = LoggerFactory.getLogger(CustomerActivityService.class);
	/**
	 * 领克特/亿起发/微信公众号
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String getRedPacketThr(HttpServletRequest request, HttpServletResponse response, Model model) {
		/*String mobile = request.getParameter("mobile");
		String verificationCode = request.getParameter("verificationCode");
		int in = Integer.parseInt(request.getParameter("origin"));
		
		log.info("getRedPacketThr parameter mobile:" + mobile + " verificationCode:" + verificationCode );
		if(StringUtils.isBlank(mobile)) {
			throw new BadRequestException("mobile can not be blank ");
		}
		if(StringUtils.isBlank(verificationCode)) {
			throw new BadRequestException("verificationCode can not be blank ");
		}
	
		int origin = 0;
		switch (in) {
		case ORIGIN_LKT:
			origin = ORIGIN_LKT;
			break;
		case ORIGIN_YQF:
			origin = ORIGIN_YQF;
			break;
		case ORIGIN_WXGZH:
			origin = ORIGIN_WXGZH;
			break;
		default:
			throw new BadRequestException("getRedPacketThr request parameter in error ");
		}
		
		String sid = getUUID.getSessionId(request, response);
		Integer msgCode = null;
		String memMobile = null;
		try {
			msgCode = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE + mobile);
			memMobile = memcachedClient.get(CommonConstant.SEND_REG_MESSAGE + mobile + "mobile");
		} catch (Exception e) {
			throw new BusinessException("memcache running error.." + e);
		}
		
		if (msgCode != null && !"".equals(msgCode)  && !verificationCode.equals(msgCode.toString())) {
			return "500";//验证码错误
		}
		
		// 输入时的电话和 缓存中的电话不一致
		if (!mobile.equals(memMobile)) {
			return "501";//用户名和验证码不匹配
		}
		
		try {
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE +mobile + "mobile");
			memcachedClient.delete(CommonConstant.SEND_REG_MESSAGE + mobile);
			memcachedClient.delete("piccode_regist" + sid);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		//验证手机号是否已经注册
		User user = new User();
		user.setMobile(mobile);
		User findUser = userService.findUser(user);
		Integer microTicket;
		if (findUser != null) {//用户已注册
			UserTriggerDto userTriggerDto = getUserTriggerDto(findUser,origin);
			log.info("the user exist and activityService.microTicketActivity() begin");
			try {
				microTicket = activityService.microTicketActivity(userTriggerDto, false);
			} catch (Exception e) {
				log.info("activityService.microTicketActivity exception");
				return "504";
			}
			log.info("existing user:"+ mobile +" and send red packet " + microTicket);
			
			login(request, response, findUser);
			
		} else {
			User autoUser = userService.autoRegist(mobile, String.valueOf(origin));
			UserTriggerDto userTriggerDto = getUserTriggerDto(autoUser, origin);
			log.info("the user dose not exist and activityService.microTicketActivity() begin");
			try {
				microTicket = activityService.microTicketActivity(userTriggerDto, true);
				log.info("the user dose not exist:" +mobile+" and send red packet " + microTicket);
			} catch (Exception e) {
				log.info("activityService.microTicketActivity exception");
				return "504";
			}
			
			login(request, response, autoUser);
			
		}
		
		if(microTicket == 20) {
			return "20";
		} else if(microTicket == 0) {
			return "0";//已领光
		} else if(microTicket == -1) {
			return "503";//已经领取过
		} else {
			return "504";
		}
		*/
		return "";
	}
	
}
