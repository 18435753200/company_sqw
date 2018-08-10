package com.mall.controller.external;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.mall.external.dto.AccsegmentDTO;
//import com.mall.external.dto.RechargeProductDTO;
//import com.mall.external.service.RechargeService;

//@Controller
//@RequestMapping("/recharge")
public class RechargeController {
	
//	private Logger log = LoggerFactory.getLogger(RechargeController.class);
//
//	@Autowired
//	private RechargeService rechargeService;
//	
//	@RequestMapping("/accsegment")
//	@ResponseBody
//	public String accsegment(String mobile, String callback) {
//	
//		if (StringUtils.isEmpty(mobile)) {
//			return "";
//		}
//		
//		log.info("mobile={}", mobile);
//		
//		AccsegmentDTO accsegmentDTO = new AccsegmentDTO();
//		accsegmentDTO.setMobilenum(mobile);
//		String ret = "";
//		try {
//			ret = rechargeService.accsegment(accsegmentDTO);
//		} catch (Exception e) {
//			log.error("{}", e);
//		}
//		log.info("return value={}", ret);
//		
//		try {
//			ret = URLEncoder.encode(ret, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
//		if (StringUtils.isNotEmpty(callback)) {
//			return callback + "('" + ret + "')";
//		}
//		
//		return ret;
//	}
//	
//	@RequestMapping("/products")
//	@ResponseBody
//	public String productsByRecharge(String mobile) throws Exception {
//	
//		rechargeService.productsByRecharge();
//		
//		return "success";
//	}
//	
//	@RequestMapping("/getProductByMobileNum")
//	@ResponseBody
//	public String getProductByMobileNum(String mobile) throws Exception {
//		if (StringUtils.isEmpty(mobile)) {
//			throw new Exception("手机号码不能为空");
//		}
//		
//		log.info("mobile={}", mobile);
//		RechargeProductDTO dto = rechargeService.getProductByMobileNum(mobile);
//		log.info("{}", dto);
//		
//		return JSONSerializer.toJSON(dto).toString();
//	}
}
