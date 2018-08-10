package com.mall.controller.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentCounty;
import com.mall.category.po.AgentProvince;
import com.mall.controller.base.BaseController;
import com.mall.wap.proxy.RemoteServiceSingleton;


@Controller
@RequestMapping(value = "/address")
public class AddressController extends BaseController {

	/**
	 * . Accounting LOGGER
	 */

	private static final Logger LOGGER = Logger
			.getLogger(AddressController.class);

	
	@RequestMapping(value="/getProvice",method=RequestMethod.POST)
	@ResponseBody
	public String getProvice(HttpServletRequest request,HttpServletResponse response,Model model){
		LOGGER.info("获取省份信息 ");
		List<AgentProvince> allProvince = null;
		//初始化加载省份信息
		try {
			allProvince = RemoteServiceSingleton.getInstance().getBaseDataServiceRpcApi().getAllProvices();
		} catch (Exception e) {
			LOGGER.error("获取省份信息过程异常"+e.getMessage(),e);
		}
		LOGGER.info("========城市信息===========   "+JSONObject.toJSONString(allProvince));
		return JSONObject.toJSONString(allProvince);
	}
	
	@RequestMapping(value="/getCity",method=RequestMethod.POST)
	@ResponseBody
	public String getCityByProvice( HttpServletRequest request,HttpServletResponse response, Model model,Integer provinceId){
		LOGGER.info("获取城市信息 ");
		List<AgentCity> citiesByProvinceId = null;
		//初始化加载省份信息
		try {
			citiesByProvinceId = RemoteServiceSingleton.getInstance().getBaseDataServiceRpcApi().getCitiesByProvinceId(provinceId);
		} catch (Exception e) {
			LOGGER.error("获取城市信息过程异常"+e.getMessage(),e);
		}
		LOGGER.info("========城市信息===========   "+JSONObject.toJSONString(citiesByProvinceId));
		return JSONObject.toJSONString(citiesByProvinceId);
	}
	
	/**
	 * 
	* @Title: register .
	* @Description: TODO(进入注册页面) 
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping(value="/getArea",method=RequestMethod.POST)
	@ResponseBody
	public String getAreaByCity(HttpServletRequest request,HttpServletResponse response, Model model , Integer cityId ){
		LOGGER.info("获取区域信息");
		List<AgentCounty> countiesByCityId = null;
		//初始化加载省份信息
		try {
			countiesByCityId = RemoteServiceSingleton.getInstance().getBaseDataServiceRpcApi().getCountiesByCityId(cityId);
		} catch (Exception e) {
			LOGGER.error("获取区域信息过程异常"+e.getMessage(),e);
		}
		return JSONObject.toJSONString(countiesByCityId);
	}
	
}
