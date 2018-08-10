package com.mall.controller.pay;


import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.constant.RequestContant;
import com.mall.controller.base.BaseController;
import com.mall.service.CusOrderPayService;
import com.mall.service.CusOrderService;

/**
 * 支付相关
 * 
 * @author ccigQA01
 * 
 */
@Controller
@RequestMapping(value = RequestContant.BOC_CROSS_BORDER)
public class CustomerPaySignCallbackController extends BaseController {
	private static final Logger log = LoggerFactory
			.getLogger(CustomerPaySignCallbackController.class);
	// 支付先关的服务
	@Autowired
	private CusOrderPayService cusOrderPayService;
	//订单相关服务
	@Autowired
	private CusOrderService cusOrderService;
	
	
	
	/**
     * 支付宝支付return返回
     * 
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = RequestContant.SIGNING_CALLBACK)
    public String singingCallback(String msg, Model model, HttpServletRequest request, HttpServletResponse response) {
    	log.info("signing callback...");
    	log.info("msg-->" + msg);
    	cusOrderPayService.bocCrossSignCallBack(msg);
    	log.info("signing callback end...");
    	return null;

    }
    
    @RequestMapping(value = RequestContant.AUTH_SINGING_CALLBACK)
    public String authCallback(String msg, Model model, HttpServletRequest request, HttpServletResponse response) {
    	log.info("signing auth callback...");
    	log.info("msg-->" + msg);
    	try {
			cusOrderPayService.authCallback(msg);
		} catch (ParseException e) {
			log.info("boc cross signing auth exception: ",e);
		}
    	log.info("auth callback...");
    	return null;

    }
    

}
