package com.mall.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemtime")
public class TimeSystemController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(TimeSystemController.class);

	@RequestMapping("/systemMillis")
	@ResponseBody
	public String getSystemTime(HttpServletRequest request,
			HttpServletResponse response,String callback){

	   String json = null;
	   logger.info("当前系统时间-----------------"+System.currentTimeMillis());
	   Long systemcurrTime = System.currentTimeMillis();
	   json = JSONObject.fromObject(systemcurrTime).toString();
	   if(org.apache.commons.lang3.StringUtils.isBlank(callback)){
			return json;
	   }
	   //返回jsonp 格式的返回值
	   return callback+"("+json+")";
    }
	
}
