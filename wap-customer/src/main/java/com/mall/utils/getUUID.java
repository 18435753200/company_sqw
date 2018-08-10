package com.mall.utils;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.constant.CommonConstant;

public class getUUID implements java.io.Serializable{
	
	private static final long serialVersionUID = 9076880758712320146L;
	private static Logger logger = LoggerFactory.getLogger(getUUID.class);
	
	public static String getSessionId(HttpServletRequest request,HttpServletResponse response){
		//检查cookie中是否含有sessionID的值
		String sessionId = "";
		sessionId = CookieUtil.getCookieValue(request, CommonConstant.SESSION_ID);
		
		if(StringUtils.isBlank(sessionId)){
			sessionId = UUID.randomUUID().toString();
			CookieUtil.setCookie(response, CommonConstant.SESSION_ID, sessionId, CommonConstant.MAX_AGE_ONE_MONTH);
			//将生成的cookieValue 放入request，以便没有响应时可以使用
			request.setAttribute("cookieValueStr", sessionId);
		}
		logger.info("=====================================");
		logger.info(sessionId);
		logger.info("=====================================");
		return sessionId;
	}
}
