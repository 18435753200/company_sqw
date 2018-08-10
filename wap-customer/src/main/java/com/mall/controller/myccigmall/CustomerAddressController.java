package com.mall.controller.myccigmall;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.annotation.AuthPassport;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentCounty;
import com.mall.category.po.AgentProvince;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.controller.impl.AddressControllerImpl;
import com.mall.customer.model.ReceiveAddress;
import com.mall.customer.model.User;
import com.mall.exception.BadRequestException;
import com.mall.service.CusAddrService;
import com.mall.utils.JsonUtil;

/**
 * 用户地址的管理
 * 
 * @author ccigQA01
 * 
 */
@Controller
@RequestMapping(value = RequestContant.CUS_ADDRESS)
public class CustomerAddressController extends BaseController {
	private static final Logger log = LoggerFactory
			.getLogger(CustomerAddressController.class);
	@Autowired
	private CusAddrService cusAddrService;
	@Autowired
	private AddressControllerImpl addressControllerImpl;

	/**
	 * 获取所有收货地址
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_FIND_ALL_ADDRESS)
	public String listAllAddress(Model model, String referenceAddressId,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("listAllAddress execute...");
		User currentUser = getCurrentUser(request);
		
		List<ReceiveAddress> addresses = addressControllerImpl.getReceiveAddressesByUserId(currentUser.getUserId());
		
		model.addAttribute("addresses", addresses);
		model.addAttribute("referenceAddressId", referenceAddressId);
		return ResponseContant.CUS_ADDRESS_LIST;
	}

	/**
	 * 跳转到收货地址添加
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@Deprecated
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_TO_ADD_ADDRESS)
	public String toAddAddress(ReceiveAddress receiveAddress, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("addAddress execute.....");
		return ResponseContant.CUS_ADDRESS_ADD;
	}

	/**
	 * 收货地址添加
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_ADD_ADDRESS)
	@ResponseBody
	public String addAddress(ReceiveAddress receiveAddress, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("addAddress execute.....");
		User currentUser = getCurrentUser(request);
		
		List<ReceiveAddress> addresses = addressControllerImpl.getReceiveAddressesByUserId(currentUser.getUserId());
		 
		if(addresses != null && addresses.size() > 9){
			return "moreAddress";
		}
		String res = cusAddrService.addAddress(currentUser, receiveAddress,
				model);
		return res;
	}

	/**
	 * 修改收货人地址
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_UPDATE_ADDRESS)
	@ResponseBody
	public String updateAddress(ReceiveAddress receiveAddress, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("updateAddress execute...");
		User currentUser = getCurrentUser(request);
		if(receiveAddress.getAddressId() == null){
			log.error("addressId is null");
			   throw new BadRequestException("addAddress faile");
		}
		String res = cusAddrService.updateAddress(receiveAddress, currentUser,
				model);
		return res;
	}

	/**
	 * 删除收货地址
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_DELETE_ADDRESS)
	@ResponseBody
	public String deleteAddress(Long addressId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("deleteAddress execute....");
		if(addressId == null) {
			log.error("addrsssId is null");
			throw new BadRequestException("deleteAddress fail");
		}
		User currentUser = getCurrentUser(request);
		String res = cusAddrService.delteAddress(currentUser, addressId, model);
		return res;
	}

	/**
	 * \ 设置为默认地址
	 * 
	 * @param addresssId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_DEFAULT_ADDRESS)
	@ResponseBody
	public String defaultAddress(Long addresssId,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("defaultAddress execute....");
		if(addresssId == null) {
			log.error("addrsssId is defaultAddress");
			throw new BadRequestException("defaultAddress fail");
		}
		String res = cusAddrService.defaultAddress(addresssId, model);

		return res;
	}

	
	/**
	 *根据id获取地址
	 * 
	 * @param addressId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_GET_ADDRESS_BY_ID)
	//@ResponseBody
	public String getAddressById(Long addressId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("getAddressById execute....");
		if(addressId == null) {
			log.error("addrsssId is defaultAddress");
			throw new BadRequestException("getAddressById fail");
		}
		
		ReceiveAddress add = addressControllerImpl.getReceiveAddressByAddressId(addressId );
		
		/*String mobile = add.getMobile();
		add.setMobile(mobile.replaceAll("(\\d{3})(\\d{4})(\\d{3})", "$1****$3"));*/
		
		String addr = JsonUtil.serializeBeanToJsonString(add);
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
 		try {
			response.getWriter().write(addr);
		} catch (IOException e) {
			e.printStackTrace();
		}
 		return null;
	}

	/**
	 * 跳转到地址修改界面
	 * 
	 * @param addressId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_TO_UPDATE_ADDRESS)
	//@ResponseBody
	public String toUpdateAddress(Long addressId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("toUpdateAddress execute....");
		if(addressId == null) {
			log.error("addrsssId is defaultAddress");
			throw new BadRequestException("toUpdateAddress fail");
		}
		
		ReceiveAddress add = addressControllerImpl.getReceiveAddressByAddressId(addressId );
		
		String addr = JsonUtil.serializeBeanToJsonString(add);
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
 		try {
			response.getWriter().write(addr);
		} catch (IOException e) {
			e.printStackTrace();
		}
 		return null;
	}

	
	/**
	 * 个人中心获取所有收货人地址
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_FIND_ALL_MY_ADDRESS)
	public String listAllMyAddress(Model model, String referenceType,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("listAllAddress execute...");
		User currentUser = getCurrentUser(request);
		List<ReceiveAddress> addresses = addressControllerImpl.getReceiveAddressesByUserId(currentUser.getUserId());
		
		model.addAttribute("addresses", addresses);
		model.addAttribute("referenceType", referenceType);
		return ResponseContant.CUS_ADDRESS_MY_LIST;
	}

	/**
	 *个人中心跳转到添加地址 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@Deprecated
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_TO_ADD_MY_ADDRESS)
	public String toAddMyAddress(ReceiveAddress receiveAddress, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("addAddress execute.....");
		
		return ResponseContant.CUS_ADDRESS_MY_ADD;
	}
	
	/**
	 * 个人中心跳转到地址修改界面
	 * @param addressId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@Deprecated
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_TO_UPDATE_MY_ADDRESS)
	public String toUpdateMyAddress(Long addressId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("toUpdateAddress execute....");
		ReceiveAddress add = addressControllerImpl.getReceiveAddressByAddressId(addressId );
		
		model.addAttribute("addr", add);
		return ResponseContant.CUS_ADDRESS_MY_UPDATE;
	}
	

	/**
	 * 获取城市
	 * @param addressId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_GET_PROVINCE)
	@ResponseBody
	public String getAllProvince( Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("getAllProvince execute....");
		 
 	 List<AgentProvince> prov = cusAddrService.getProvince();
 		String pro = JsonUtil.serializeBeanToJsonString(prov);
 		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
 		try {
			response.getWriter().write(pro);
		} catch (IOException e) {
			e.printStackTrace();
		}
 		return null;	
	}

	/**
	 * 获取城市
	 * @param addressId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_GET_CITY)
	@ResponseBody
	public String getAllCity(Integer proId,Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("getAllCity execute....");
		if(proId == null) {
			log.error("proId is null");
			throw new BadRequestException("getAllCity fail");
		}
		
 	    List<AgentCity> city = cusAddrService.getCity(proId);
 		String ci = JsonUtil.serializeBeanToJsonString(city);
 		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
 		try {
			response.getWriter().write(ci);
		} catch (IOException e) {
			e.printStackTrace();
		}
 		return null;
 		
	}
	/**
	 * 获取乡镇
	 * @param addressId
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_GET_COUNTRY)
	@ResponseBody
	public String getAllCountry(Integer ciId,Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("getAllCountry execute....");
		 
		if(ciId == null) {
			log.error("ciId is null");
			throw new BadRequestException("getAllCountry fail");
		}
		
 		List<AgentCounty> couns = cusAddrService.getCountry(ciId);
 		String cou = JsonUtil.serializeBeanToJsonString(couns);
 		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
 		try {
			response.getWriter().write(cou);
		} catch (IOException e) {
			e.printStackTrace();
		}
 		return null;
	}
	
}
