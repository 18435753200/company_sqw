/*package com.mall.controller.promotions;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.promotion.dto.activity.OrderAttendActivityDto;
import com.mall.platform.proxy.RemoteServiceSingleton;

@Controller
@RequestMapping(value = "/view")
public class ViewOrderAttendActivityDetailController {
	private static final Logger logger = LoggerFactory.getLogger(ViewOrderAttendActivityDetailController.class);
	
	@RequestMapping(value = "/detail")
	@ResponseBody
	public String viewDetail(HttpServletRequest request){
		String str = "";
		try{
			Long orderId = Long.parseLong(request.getParameter("orderId"));
			logger.info("orderId-------------" + orderId);
			OrderAttendActivityDto findActivityByOrderId = RemoteServiceSingleton.getInstance().getActiveProductRuleApi().findActivityByOrderId(orderId);
			str = JSONArray.fromObject(findActivityByOrderId).toString();
			logger.info(str);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return str;
	}
	
	@RequestMapping(value = "/toDetail")
	public String toDetail(HttpServletRequest request){
		return "promotions/activity/orderActivityDetail";
	}
	
}
*/