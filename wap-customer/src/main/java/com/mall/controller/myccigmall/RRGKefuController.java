/**
 * 
 */
package com.mall.controller.myccigmall;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mall.annotation.AuthPassport;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderItemDTO;
import com.mall.customer.order.dto.ProductDTO;
//import com.mall.kefu.api.SingleServiceRPC;
//import com.mall.kefu.dto.OrderRrgDTO;
//import com.mall.kefu.dto.ResponseDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.service.CusOrderService;
import com.mall.service.RrgLogicService;
import com.mall.utils.DateUtil;
import com.mall.vo.CommonConstants;
import com.mall.vo.enums.AddressTypeEnum;
import com.mall.vo.enums.ClientEnum;
import com.mall.vo.enums.ImageTypeEnum;
import com.mall.vo.enums.OriginEnum;
import com.mall.vo.enums.PatternEnum;
import com.mall.vo.enums.ServiceStatusEmun;
import com.mall.vo.enums.ServiceTypeEmun;

/**
 * 退换货 RRG (return replace goods)
 * 
 * 
 * @author jianping.gao
 *
 */
@Controller
@RequestMapping(value = RequestContant.CUS_KEFU)
public class RRGKefuController extends BaseController {
	/**
	 * define log
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(RRGKefuController.class);

	/**
	 * 查询订单信息
	 * 
	 */
	@Autowired
	private CusOrderService cusOrderService;

	/**
	 * 提交服务单
	 * 
	 */
//	@Autowired
//	private SingleServiceRPC singleServiceRPC;

	/**
	 * 
	 */
//	@Autowired
//	private RrgLogicService rrgLogicService;

	/**
	 * 退换货 申请 单个商品 first page
	 * 
	 * @param request
	 * @return
	 */
//	@AuthPassport
//	@RequestMapping(value = RequestContant.CUS_KEFU_RRG
//			+ "/{orderId:[\\d]+}-{skuId:[\\d]+}-{no:[1|2|3]+}", method = {
//			RequestMethod.POST, RequestMethod.GET })
//	public String repairRRG(HttpServletRequest request,
//			@PathVariable("orderId") Long orderId,
//			@PathVariable("skuId") Long skuId, @PathVariable("no") int no,
//			OrderRrgDTO orderRrg, Model model) {
//
//		LOG.debug("退换货申请 单个订单");
//		orderRrg.setOrderId(orderId);
//		orderRrg.setSkuId(skuId);
//		User userInfo = getCurrentUser(request);
//		OrderDTO orderDto = cusOrderService.findOrderByOrderId(orderId,
//				userInfo);
//		model.addAttribute("orderRrg", orderRrg);
//		model.addAttribute("orderDto", orderDto);
//		model.addAttribute("picUrl2", picUrl2);
//		model.addAttribute("skuId", skuId);
//
//		if (CommonConstant.NUMBER_1 == no) {
//			return ResponseContant.CUS_MYCCIG + ResponseContant.CUS_KEFU
//					+ ResponseContant.CUS_KEFU_RRG
//					+ ResponseContant.CUS_KEFU_APPLY_FIRST;
//		}
//		if (CommonConstant.NUMBER_2 == no) {
//			model.addAttribute("orderRrg", orderRrg);
//			return ResponseContant.CUS_MYCCIG + ResponseContant.CUS_KEFU
//					+ ResponseContant.CUS_KEFU_RRG
//					+ ResponseContant.CUS_KEFU_APPLY_SECOND;
//		}
//
//		if (CommonConstant.NUMBER_3 == no) {
//			int count = 0;
//			for (OrderItemDTO orderItemDTO : orderDto.getOrderItemDTOs()) {
//				if (!String.valueOf(skuId).equals(
//						String.valueOf(orderItemDTO.getSkuId()))) {
//					count++;
//					continue;
//				}
//				if (orderItemDTO.getSkuQty() < (orderItemDTO.getRrgQty() + Integer
//						.parseInt(orderRrg.getApplyRrgQty()))) {
//					return ResponseContant.ERROR_500;
//				}
//			}
//
//			// 参数异常，订单中没有对应的规格编号
//			if (count == orderDto.getOrderItemDTOs().size()) {
//				return ResponseContant.ERROR_500;
//			}
//
//			orderRrg.setClient(ClientEnum.WAP);
//			orderRrg.setPattern(PatternEnum.B2C);
//			orderRrg.setOrigin(OriginEnum.CLIENT);
//			orderRrg.setServiceStatus(ServiceStatusEmun.APPLY);
//			orderRrg.setB2cUser(userInfo);
//			orderRrg.setPayId(orderDto.getPayId());
//
//			orderRrg.setServiceType(ServiceTypeEmun.getEmunByIndex(orderRrg
//					.getApplyType()));              
//			orderRrg.setAddressTypeEnum(AddressTypeEnum.CONSIGNOR);
//			orderRrg.setImageTypeEnum(ImageTypeEnum.IMAGE);
//
//			// SingleServiceRPC singleServiceRPC = RemoteServiceSingleton
//			// .getInstance().getSingleServiceRPC();
//
//			try {
//				ResponseDTO responseDTO = singleServiceRPC
//						.saveSingleService(orderRrg);
//				if (responseDTO.getCode().equals(
//						CommonConstants.RESPONSE_STATUS_ERROR)) {
//					return ResponseContant.ERROR_500;
//				}
//
//				LOG.info("serviceNo:" + responseDTO.getServiceId());
//				model.addAttribute("serviceNo", responseDTO.getServiceId());
//			} catch (Exception e) {
//				LOG.error("saveSingleService is fail!{}", e);
//				return ResponseContant.ERROR_500;
//			}
//
//			return ResponseContant.CUS_MYCCIG + ResponseContant.CUS_KEFU
//					+ ResponseContant.CUS_KEFU_RRG
//					+ ResponseContant.CUS_KEFU_APPLY_SUCCESS;
//		}
//		return ResponseContant.ERROR_500;
//
//	}

	/**
	 * 所有 已收货订单 申请退换货列表
	 * 
	 * @param request
	 * @return
	 */
//	@AuthPassport
//	@RequestMapping(value = RequestContant.CUS_KEFU_RRG + RequestContant.LIST
//			+ "/{no:[\\d]+}")
//	public String repairRRGALL(HttpServletRequest request,
//			@PathVariable("no") Integer no, Model model,
//			HttpServletResponse response) {
//		LOG.debug("退换货申请 所有的  第" + no + "页");
//		User userInfo = getCurrentUser(request);
//		PageBean<OrderDTO> pageBean = cusOrderService
//				.findCustomerOrderByCustomer(
//						CommonConstant.ORDERT_STATUS_FINISHED, no, model,
//						request, response, userInfo, "51");
//		rrgLogicService.setRrrStatus2OrderList(pageBean.getResult(),
//				CommonConstants.ONE);
//		
//		// 已收货7天的订单不可申请退换货
//		filterOrders(pageBean);
//		
//		model.addAttribute("repairType", CommonConstant.NUMBER_1);
//		model.addAttribute("pageBean", pageBean);
//		model.addAttribute("picUrl2", picUrl2);
//		if (no == 1) {
//			return ResponseContant.CUS_MYCCIG + ResponseContant.CUS_KEFU
//					+ ResponseContant.CUS_KEFU_RRG
//					+ ResponseContant.CUS_KEFU_REPAIR;
//		} else {
//			return ResponseContant.CUS_MYCCIG + ResponseContant.CUS_KEFU
//					+ ResponseContant.CUS_KEFU_RRG
//					+ ResponseContant.CUS_KEFU_RETURN_LIST;
//		}
//	}

	private void filterOrders(PageBean<OrderDTO> pageBean) {
		List<OrderDTO> newResult = new ArrayList<OrderDTO>();
		
		if(pageBean == null || pageBean.getResult() == null){
			return;
		}
		
		List<OrderDTO> result = pageBean.getResult();
		for (OrderDTO orderDTO : result) {
			
			if ("11".equals(orderDTO.getSupplyType())) {
				continue;
			}
			
			
			List<ProductDTO> productList = orderDTO.getProductList();
			
			int i = 0;
			for (ProductDTO productDTO : productList) {
				
				Integer productType = productDTO.getProductType();
				boolean rrgStatus = productDTO.isRrgStatus();
				
				// 已申请的商品数量
				if(0 == productType && !rrgStatus){
					i ++;
				}
			}
			
			Date lastEditTime = orderDTO.getLastEditTime();
			int intervalDays = DateUtil.getIntervalDays(new Date(), lastEditTime);
			if(intervalDays < 7){
				newResult.add(orderDTO);
			}else{
				// 当前订单有申请过的商品
				if(i > 0){
					newResult.add(orderDTO);
				}
			}
		}
		
		pageBean.setResult(newResult);
	}

	/**
	 * 获取单独的订单信息
	 * 
	 * @param request
	 * @param orderId
	 * @param model
	 * @param response
	 * @return
	 */
//	@AuthPassport
//	@RequestMapping(value = RequestContant.CUS_KEFU_RRG
//			+ RequestContant.SINGLE_ORDER + "/{orderId:[\\d]+}")
//	public String RepairRPGByOrderId(HttpServletRequest request,
//			@PathVariable("orderId") Long orderId, Model model,
//			HttpServletResponse response) {
//		User userInfo = getCurrentUser(request);
//		OrderDTO ordreDTO = cusOrderService.findOrderByOrderId(orderId,
//				userInfo);
//		List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
//		orderDTOs.add(ordreDTO);
//		rrgLogicService.setRrrStatus2OrderList(orderDTOs, CommonConstants.TWO);
//
//		model.addAttribute("repairType", CommonConstant.NUMBER_2);
//		model.addAttribute("orderDto", ordreDTO);
//		model.addAttribute("picUrl2", picUrl2);
//		return ResponseContant.CUS_MYCCIG + ResponseContant.CUS_KEFU
//				+ ResponseContant.CUS_KEFU_RRG
//				+ ResponseContant.CUS_KEFU_REPAIR;
//	}

}
