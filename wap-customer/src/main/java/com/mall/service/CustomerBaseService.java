package com.mall.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mall.constant.CommonConstant;
import com.mall.customer.model.User;
import com.mall.customer.service.UserService;
/*import com.mall.promotion.api.activity.b2creg.PlatformUserRegActivityService;
import com.mall.promotion.dto.b2c.UserTriggerDto;*/
import com.mall.utils.CookieTool;
import com.mall.utils.getUUID;

public class CustomerBaseService {
	
	protected static final int ORIGIN_LKT = 910;//领克特
	protected static final int ORIGIN_YQF = 911;//亿起发
	protected static final int ORIGIN_WXGZH = 912;//微信公众号
	
	private static final Logger log = LoggerFactory.getLogger(CustomerBaseService.class);

	@Autowired
	protected MemcachedClient memcachedClient;
	@Autowired
	protected UserService userService;
	/*@Autowired
	protected PlatformUserRegActivityService activityService;*/

	/**
	 * 封装优惠券请求对象
	 * @param user 用户
	 * @param origin 来源
	 * @return
	 */
	/*protected UserTriggerDto getUserTriggerDto(User user, long origin) {
		UserTriggerDto userTriggerDto = new UserTriggerDto();
		userTriggerDto.setUserid(user.getUserId());
		userTriggerDto.setChannelCode(origin);
		return userTriggerDto;
	}*/
	
	public String login(HttpServletRequest request, HttpServletResponse response,User user){
		
		String sid = getUUID.getSessionId(request, response);
		
		log.info("start to set cookie....");
		CookieTool.setCookie(response, CommonConstant.SESSION_ID, sid,
				CommonConstant.MAX_AGE_ONE_MONTH);
		log.info("set cookie finished.");

		try {
			log.info("start to set memcached....");
			memcachedClient.setOpTimeout(5000L);
			memcachedClient.set(CommonConstant.CUSTOMER_LOGIN + sid,CommonConstant.MEMCACHEDAGE, user);
					
			log.info("set memcached finished.");
		} catch (Exception e) {
			log.info("set memcached has error." + e.getMessage(), e);
			return "500";
		}
		
		return "200";
	}
}
