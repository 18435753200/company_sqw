/**
 * 
 */
package com.mall.controller.myccigmall;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;

/**
 * 联系客服
 * @author jianping.gao
 *
 */
@Controller
@RequestMapping(value = RequestContant.CUS_KEFU)
public class ContactKefuController extends BaseController{
	/**
	 * define log
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(ContactKefuController.class);
	
	/**
	 * 联系客服
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_KEFU_CONTACT)
	public String contactKefu(HttpServletRequest request){
		LOG.info("联系客服");
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOG.info("用户未登陆,跳转到登陆页");
			return RequestContant.REDIRECT+RequestContant.CUSTOMER+RequestContant.CUSTOMER_TO_LOGIN;
		}
		return ResponseContant.CUS_MYCCIG+ResponseContant.CUS_KEFU+ResponseContant.CUS_KEFU_CONTACT;
	}
	
}
