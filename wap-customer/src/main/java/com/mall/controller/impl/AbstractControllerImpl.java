/**
 * 
 */
package com.mall.controller.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.constant.CommonConstant;
import com.mall.utils.CookieUtil;

/**
 * @author zhengqiang.shi
 * 2015年3月25日 下午6:12:40
 */
public abstract class AbstractControllerImpl {

	private static final Logger log = LoggerFactory.getLogger(AbstractControllerImpl.class);
	
	Map<Object, Object> map;
	
	public void print(Map<Object, Object> map) {
		log.info("request params {}",map);
	}
	
	protected boolean isYueMeRequest(HttpServletRequest request) {
		String cookieValue = CookieUtil.getCookieValue(request,
				CommonConstant.ORDER_FORM_KEY);

		if (StringUtils.isNotBlank(cookieValue)
				&& CommonConstant.CHANNEL_FROM_VALUE_YUEME.equals(cookieValue.trim())) {
			return true;
		}

		return false;
	}
}
