package com.mall.controller.order;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentCounty;
import com.mall.category.po.AgentProvince;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.SqwQiyeAccountRecode;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerOrderExportSevice;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.B2cShipOrderDTO;
import com.mall.customer.order.dto.B2cShipOrderItemDTO;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderItemDTO;
import com.mall.customer.order.dto.ProductDTO;
import com.mall.customer.order.po.Order;
import com.mall.customer.service.UserService;
import com.mall.dealer.product.po.LogisticTemp;
import com.mall.dealer.product.retailer.api.LogisticTempService;
import com.mall.merchant.proxy.RemoteServiceSingleton;
import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.api.rpc.ShipOrderB2CService;
import com.mall.retailer.order.common.RetailerConstant;
import com.mall.retailer.order.dto.ShipOrderDTO;
import com.mall.retailer.order.dto.ShipOrderItemDTO;
import com.mall.supplier.model.LogisticTplWay;
import com.mall.supplier.model.LogisticTplWayfee;
import com.mall.supplier.model.LogisticTplWayfeeAddress;
import com.mall.supplier.model.LogisticTplWaynonefee;
import com.mall.supplier.model.SGeneralLogisticTpl;
import com.mall.supplier.model.SuplierAreaRegion;
import com.mall.supplier.model.SuplierAreaTpl;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierBrandDTO;
import com.mall.supplier.model.SupplierLogisticTpl;
import com.mall.supplier.model.SupplierPartnerArea;
import com.mall.supplier.model.SupplierUser;
import com.mall.supplier.service.GeneralLogisTplService;
import com.mall.supplier.service.SupplierLogisticService;
import com.mall.utils.Common;
import com.mall.utils.Constants;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	private Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	/**
	 * 退款订单列表
	 */
	@RequestMapping(value = "/getCustomerRefundOrderList", method = RequestMethod.GET)
	public String getCustomerRefundOrderList() {
		LOGGER.info("start to execute method <getCustomerOrderList>!!!!");

		LOGGER.info("end<> to execute method <getCustomerOrderList>!!!!");

		return getLanguage() + "/poporder/B2Corder_refund_list";
	}

	/**
	 * 根据条件查询的退款订单列表
	 */
	@RequestMapping(value = "/getCustomerRefundOrderPageBeanByCondition")
	public String getCustomerRefundOrderPageBeanByCondition(Integer page, String startTime, String endTime,
			Long orderId, Model model, String receiveName, String userName, String pName, Short status) {
		LOGGER.info("start to execute method <getCustomerRefundOrderPageBeanByCondition>!!!!");
		LOGGER.info("订单编号orderId:" + orderId);
		LOGGER.info("当前是第" + page + "页!");
		LOGGER.info("订单状态码status:" + status);
		LOGGER.info("商品名称:" + pName);
		LOGGER.info("起始时间:" + startTime);
		LOGGER.info("结束时间:" + endTime);
		LOGGER.info("收货人receiveName:" + receiveName);
		LOGGER.info("用户名userName:" + userName);
		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		OrderDTO orderDTO = new OrderDTO();
		if (!Common.isEmpty(pName)) {
			orderDTO.setpName(pName);
		}
		if (null != orderId) {
			orderDTO.setId(orderId);
			// orderDTO.getSupplyType() 1.国内 11.保税区 12.
		}
		if (!Common.isEmpty(startTime)) {
			orderDTO.setStartDate(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if (!Common.isEmpty(endTime)) {
			orderDTO.setEndDate(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if (!Common.isEmpty(receiveName)) {
			orderDTO.setReceiveName(receiveName);
		}
		if (!Common.isEmpty(userName)) {
			List<Long> userIds = new ArrayList<Long>();
			try {
				UserService userService = RemoteServiceSingleton.getInstance().getUserService();
				userIds = userService.findUserIdsByUserName(userName);
				if (userIds.size() < 1) {
					userIds.add(0l);
				}
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("UserService<findUserIdsByUserName()根据用户用户名模糊查询ID的集合> is failed!!!!" + e.getMessage(), e);
			}
			orderDTO.setUserIds(userIds);
		}
		if (null != status) {
			orderDTO.setStatus(status);
		}
		if (page != null && page != 0) { // 判断条件中是否带着分页码
			pageBean.setPage(page);
		} else {
			pageBean.setPage(1);// 没有分页码的话就检索第一页
		}
		pageBean.setParameter(orderDTO);
		pageBean.setPageSize(Constants.PAGESIZE);
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			pageBean = customerOrderService.findWaitRefundOrders(pageBean);
			if (null != pageBean) {
				List<OrderDTO> orderList = pageBean.getResult();
				if (null != orderList && orderList.size() > 0) {
					for (OrderDTO orderDTO2 : orderList) {
						List<ProductDTO> productList = orderDTO2.getProductList();
						if (null != productList && productList.size() > 0) {
							for (ProductDTO productDTO : productList) {
								String productImgUrl = productDTO.getImgUrl();
								if (!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")) {
									productImgUrl = Constants.P1 + productImgUrl;
									productDTO.setImgUrl(productImgUrl);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findWaitRefundOrders()获取海关审核失败的订单> is failed!!!!" + e.getMessage(), e);
		}
		model.addAttribute("pageBean", pageBean);
		LOGGER.info("end<> to execute method <getCustomerRefundOrderPageBeanByCondition>!!!!");
		return getLanguage() + "/poporder/B2Corder_refund_list_model";
	}

	/**
	 * @Description: 订单退款操作.
	 * @Title goRefundOrderByOrderId
	 * @author Guofy
	 * @param Long
	 *            orderId
	 * @return String
	 */
	@RequestMapping(value = "/goRefundOrderByOrderId")
	@ResponseBody
	public String goRefundOrderByOrderId(Long orderId) {
		LOGGER.info("start to execute method <goRefundOrderByOrderId订单退款操作>!!!!");
		LOGGER.info("订单ID:" + orderId);

		boolean flag = false;
		String result = "";
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			flag = customerOrderService.customerOrderRefund(orderId, null);
			if (flag) {
				result = "订单正在退款成功!";
			} else {
				result = "订单退款操作失败!";
			}
		} catch (Exception e) {
			// TODO: handle exception
			result = "订单退款操作失败!";
			LOGGER.error("customerOrderService<customerOrderRefund()根据订单ID对此订单退款!> is failed!!!!" + e.getMessage(), e);
		}
		LOGGER.info("end<> to execute method <goRefundOrderByOrderId订单退款操作>!!!!");
		return result;
	}

	/**
	 * 不良退款订单
	 */
	@RequestMapping(value = "/refundOrderByOrderId")
	@ResponseBody
	public String refundOrderByOrderId(Long orderId) {
		LOGGER.info("start to execute method <refundOrderByOrderId订单退款操作>!!!!");
		LOGGER.info("订单ID:" + orderId);
		String result = "";
		boolean flag = false;
		try {
			flag = RemoteServiceSingleton.getInstance().getCustomerOrderService().customerOrderRefund2(orderId, null);
			if (flag) {
				result = "订单退款成功!";
			} else {
				result = "订单退款失败！";

			}
		} catch (Exception e) {
			result = "订单退款失败！";
			logger.error("customerOrderService<customerOrderRefund2()根据订单ID对此订单退款!> is failed!!!!" + e.getMessage(), e);
		}
		return result;

	}
	
	@RequestMapping(value = "/getAreaOrder", method = RequestMethod.GET)
	public String getAreaOrder(Model model) {

		LOGGER.info("start to execute method <getLogistic()获取所有物流商>!!!!");
		Object json = "[]";
		try {
			LogisticTempService logisticTempService = RemoteServiceSingleton.getInstance().getlogisticTempService();
			List<LogisticTemp> logisticTemps = logisticTempService.getProviders();

			model.addAttribute("logisticTemps", logisticTemps);
		} catch (Exception e) {
			LOGGER.error("LogisticTempService<getProviders()<获取所有物流商服务>> is failed!!!!" + e.getMessage(), e);
		}
		LOGGER.info("物流商：" + json.toString());
		LOGGER.info("end<> to execute method <getLogistic()获取所有物流商>!!!!");

		model.addAttribute("language", getLanguage().substring(1));

		return getLanguage() + "/poporder/orderList2";
	}

	/**
	 * 转向订单页.
	 * 
	 * @return String 地址
	 */
	@RequestMapping(value = "/getOrder", method = RequestMethod.GET)
	public String getOrder(Model model) {

		LOGGER.info("start to execute method <getLogistic()获取所有物流商>!!!!");
		Object json = "[]";
		try {
			LogisticTempService logisticTempService = RemoteServiceSingleton.getInstance().getlogisticTempService();
			List<LogisticTemp> logisticTemps = logisticTempService.getProviders();

			model.addAttribute("logisticTemps", logisticTemps);
		} catch (Exception e) {
			LOGGER.error("LogisticTempService<getProviders()<获取所有物流商服务>> is failed!!!!" + e.getMessage(), e);
		}
		LOGGER.info("物流商：" + json.toString());
		LOGGER.info("end<> to execute method <getLogistic()获取所有物流商>!!!!");

		model.addAttribute("language", getLanguage().substring(1));

		return getLanguage() + "/poporder/orderList";
	}

	/**
	 * . 根据条件搜索订单列表.
	 * 
	 * @Title getCustomerOrderPageBeanByCondition
	 * @param Integer
	 *            page
	 * @param String
	 *            pName
	 * @param Short
	 *            status
	 * @param String
	 *            startTime
	 * @param String
	 *            endTime
	 * @param Long
	 *            orderId
	 * @param String
	 *            receiveName
	 * @param String
	 *            userName
	 * @param Model
	 *            model
	 * @return String
	 */
	@RequestMapping(value = "/getCustomerOrderPageBeanByCondition")
	public String getCustomerOrderPageBeanByCondition(Integer page, String pName, Short status, String startTime,
			String endTime, Long orderId, String receiveName, String userName, Model model, Integer payType,
			Integer shipType, String statusList, String pageType, Short orderPlatform, Short orderType,
			String companyQy) {
		// 11.宁波海外直邮 12.宁波保税区发货 13.郑州海外直邮 14.郑州保税区发货 1.国内发货 21.韩国直邮
		LOGGER.info("start to execute method <getCustomerOrderPageBeanByCondition>!!!!");
		LOGGER.info("订单编号orderId:" + orderId);
		LOGGER.info("当前是第" + page + "页!");
		LOGGER.info("订单状态码status:" + status);
		LOGGER.info("商品名称:" + pName);
		LOGGER.info("起始时间:" + startTime);
		LOGGER.info("结束时间:" + endTime);
		LOGGER.info("收货人receiveName:" + receiveName);
		LOGGER.info("用户名userName:" + userName);
		LOGGER.info("支付方式payType:" + payType);
		LOGGER.info("配送方式shipType:" + shipType);
		LOGGER.info("状态列表:" + statusList);
		LOGGER.info("平台类型:" + orderPlatform);
		LOGGER.info("订单类型" + orderType);

		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		OrderDTO orderDTO = new OrderDTO();
		if (!Common.isEmpty(statusList)) {
			List<Short> statusAll = new ArrayList<Short>();
			String[] statusArray = statusList.split(",");
			for (String statusSimple : statusArray) {
				try {
					statusAll.add(Short.parseShort(statusSimple));
				} catch (NumberFormatException e) {
					LOGGER.error("getCustomerOrderPageBeanByCondition is failed!!!!数字转换异常  status:" + status, e);
				}
			}
			orderDTO.setStatusList(statusAll);
		}
		if (!Common.isEmpty(pName)) {
			orderDTO.setpName(pName);
		}
		if (!Common.isEmpty(startTime)) {
			startTime += " 00:00:00";
			orderDTO.setStartDate(Common.stringToDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if (!Common.isEmpty(endTime)) {
			endTime += " 23:59:59";
			orderDTO.setEndDate(Common.stringToDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if (null != companyQy) {
			if (companyQy.equals("8")) {
				short a = 39;
				orderDTO.setOrderType(a);
			} else {
				orderDTO.setCompanyQy(companyQy);
			}
		}
		if (!Common.isEmpty(receiveName)) {
			orderDTO.setReceiveName(receiveName);
		}
		if (!Common.isEmpty(userName)) {
			List<Long> userIds = new ArrayList<Long>();
			try {
				UserService userService = RemoteServiceSingleton.getInstance().getUserService();
				userIds = userService.findUserIdsByUserName(userName);
				if (userIds.size() < 1) {
					userIds.add(0l);
				}
			} catch (Exception e) {
				LOGGER.error("UserService<findUserIdsByUserName()根据用户用户名模糊查询ID的集合> is failed!!!!" + e.getMessage(), e);
			}
			orderDTO.setUserIds(userIds);
		}
		if (null != orderId) {
			orderDTO.setId(orderId);
			// orderDTO.getSupplyType() 1.国内 11.保税区 12.
		}
		if (null != status) {
			orderDTO.setStatus(status);
		}
		if (null != payType) {
			orderDTO.setFivePayType(payType);
		}
		if (null != shipType) {
			orderDTO.setFiveShipType(shipType);
		}
		if (null != orderPlatform) {
			orderDTO.setOrderPlatform(orderPlatform);
		}
		if (null != orderType) {
			orderDTO.setOrderType(orderType);
		}

		if (page != null && page != 0) { // 判断条件中是否带着分页码
			pageBean.setPage(page);
		} else {
			pageBean.setPage(1);// 没有分页码的话就检索第一页
		}

		orderDTO.setSupplyType("51");
		orderDTO.setType(0);
		if (null != getCurrentSupplierId()) {
			// 只查询当前商家的订单
			orderDTO.setMerchantid(String.valueOf(getCurrentSupplierId()));
		}
		pageBean.setParameter(orderDTO);
		pageBean.setPageSize(Constants.PAGESIZE);
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			pageBean = customerOrderService.findCustomerOrdersByWofe(pageBean);
			if (null != pageBean) {
				List<OrderDTO> orderList = pageBean.getResult();

				if (null != orderList && orderList.size() > 0) {
					for (OrderDTO orderDTO2 : orderList) {

						if (orderDTO2.getReceiveName() != null) {
							orderDTO2
									.setReceiveName(orderDTO2.getReceiveName().replaceAll("'", "").replaceAll(" ", ""));
						}
						if (orderDTO2.getReceiveAddress() != null) {
							orderDTO2.setReceiveAddress(
									orderDTO2.getReceiveAddress().replaceAll("'", "").replaceAll("\n", ""));
						}

						if (orderDTO2.getSupplyType().equals("11") || orderDTO2.getSupplyType().equals("12")) {
							String portType = orderDTO2.getPortType();
							if (!Common.isEmpty(portType)) {
								String portTypeStr = portType.substring(0, 1);
								if (portTypeStr.equals("2")) {
									orderDTO2.setPortType("guofeiyan");
								}
							}
						}
						List<ProductDTO> productList = orderDTO2.getProductList();
						if (null != productList && productList.size() > 0) {
							for (ProductDTO productDTO : productList) {
								String productImgUrl = productDTO.getImgUrl();
								if (!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")) {
									productImgUrl = Constants.P1 + productImgUrl;
									productDTO.setImgUrl(productImgUrl);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrdersByWofe()> is failed!!!!" + e.getMessage(), e);
		}

		if (statusList != null) {
			model.addAttribute("statusList", statusList);
		}
		if (pageType != null) {
			model.addAttribute("pageType", pageType);
		}

		model.addAttribute("pageBean", pageBean);

		LOGGER.info("end<> to execute method <getCustomerOrderPageBeanByCondition>!!!!");
		return getLanguage() + "/poporder/orderItem";
	}

	
	
	@RequestMapping(value = "/getCustomerOrderPageBeanByConditionArea")
	public String getCustomerOrderPageBeanByConditionArea(Integer page, String pName, Short status, String startTime,
			String endTime, Long orderId, String receiveName, String userName, Model model, Integer payType,
			Integer shipType, String statusList, String pageType, Short orderPlatform, Short orderType,
			String companyQy) {
		// 11.宁波海外直邮 12.宁波保税区发货 13.郑州海外直邮 14.郑州保税区发货 1.国内发货 21.韩国直邮
		LOGGER.info("start to execute method <getCustomerOrderPageBeanByCondition>!!!!");
		LOGGER.info("订单编号orderId:" + orderId);
		LOGGER.info("当前是第" + page + "页!");
		LOGGER.info("订单状态码status:" + status);
		LOGGER.info("商品名称:" + pName);
		LOGGER.info("起始时间:" + startTime);
		LOGGER.info("结束时间:" + endTime);
		LOGGER.info("收货人receiveName:" + receiveName);
		LOGGER.info("用户名userName:" + userName);
		LOGGER.info("支付方式payType:" + payType);
		LOGGER.info("配送方式shipType:" + shipType);
		LOGGER.info("状态列表:" + statusList);
		LOGGER.info("平台类型:" + orderPlatform);
		LOGGER.info("订单类型" + orderType);
		
		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		OrderDTO orderDTO = new OrderDTO();
		if (!Common.isEmpty(statusList)) {
			List<Short> statusAll = new ArrayList<Short>();
			String[] statusArray = statusList.split(",");
			for (String statusSimple : statusArray) {
				try {
					statusAll.add(Short.parseShort(statusSimple));
				} catch (NumberFormatException e) {
					LOGGER.error("getCustomerOrderPageBeanByCondition is failed!!!!数字转换异常  status:" + status, e);
				}
			}
			orderDTO.setStatusList(statusAll);
		}
		if (!Common.isEmpty(pName)) {
			orderDTO.setpName(pName);
		}
		if (!Common.isEmpty(startTime)) {
			startTime += " 00:00:00";
			orderDTO.setStartDate(Common.stringToDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if (!Common.isEmpty(endTime)) {
			endTime += " 23:59:59";
			orderDTO.setEndDate(Common.stringToDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		if (null != companyQy) {
			if (companyQy.equals("8")) {
				short a = 39;
				orderDTO.setOrderType(a);
			} else {
				orderDTO.setCompanyQy(companyQy);
			}
		}
		if (!Common.isEmpty(receiveName)) {
			orderDTO.setReceiveName(receiveName);
		}
		if (!Common.isEmpty(userName)) {
			List<Long> userIds = new ArrayList<Long>();
			try {
				UserService userService = RemoteServiceSingleton.getInstance().getUserService();
				userIds = userService.findUserIdsByUserName(userName);
				if (userIds.size() < 1) {
					userIds.add(0l);
				}
			} catch (Exception e) {
				LOGGER.error("UserService<findUserIdsByUserName()根据用户用户名模糊查询ID的集合> is failed!!!!" + e.getMessage(), e);
			}
			orderDTO.setUserIds(userIds);
		}
		if (null != orderId) {
			orderDTO.setId(orderId);
			// orderDTO.getSupplyType() 1.国内 11.保税区 12.
		}
		if (null != status) {
			orderDTO.setStatus(status);
		}
		if (null != payType) {
			orderDTO.setFivePayType(payType);
		}
		if (null != shipType) {
			orderDTO.setFiveShipType(shipType);
		}
		if (null != orderPlatform) {
			orderDTO.setOrderPlatform(orderPlatform);
		}
		if (null != orderType) {
			orderDTO.setOrderType(orderType);
		}

		if (page != null && page != 0) { // 判断条件中是否带着分页码
			pageBean.setPage(page);
		} else {
			pageBean.setPage(1);// 没有分页码的话就检索第一页
		}

		orderDTO.setSupplyType("51");
		orderDTO.setType(0);
	/*	if (null != getCurrentSupplierId()) {
			// 只查询当前商家的订单
			orderDTO.setMerchantid(String.valueOf(getCurrentSupplierId()));
		}*/
		int a=0;
		if(getSupplier().getOrganizationType()==11){
			a=1;
		}
		if(getSupplier().getOrganizationType()==12){
			a=2;
		}
		 List<SupplierPartnerArea> area = RemoteServiceSingleton.getInstance().getSupplierManagerService().findPartnerArea(getCurrentSupplierId(), a);
		Long countyId = area.get(0).getCountyId();
		AgentCounty ac = null;
		/*try {
			ac=RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getCountyById(countyId.intValue());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		orderDTO.setAreaName(ac.getCountyname());*/
		orderDTO.setReceiveAreaId(countyId);
		pageBean.setParameter(orderDTO);
		pageBean.setPageSize(Constants.PAGESIZE);
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			pageBean = customerOrderService.findCustomerOrdersByWofe(pageBean);
			if (null != pageBean) {
				List<OrderDTO> orderList = pageBean.getResult();

				if (null != orderList && orderList.size() > 0) {
					for (OrderDTO orderDTO2 : orderList) {

						if (orderDTO2.getReceiveName() != null) {
							orderDTO2
									.setReceiveName(orderDTO2.getReceiveName().replaceAll("'", "").replaceAll(" ", ""));
						}
						if (orderDTO2.getReceiveAddress() != null) {
							orderDTO2.setReceiveAddress(
									orderDTO2.getReceiveAddress().replaceAll("'", "").replaceAll("\n", ""));
						}

						if (orderDTO2.getSupplyType().equals("11") || orderDTO2.getSupplyType().equals("12")) {
							String portType = orderDTO2.getPortType();
							if (!Common.isEmpty(portType)) {
								String portTypeStr = portType.substring(0, 1);
								if (portTypeStr.equals("2")) {
									orderDTO2.setPortType("guofeiyan");
								}
							}
						}
						List<ProductDTO> productList = orderDTO2.getProductList();
						if (null != productList && productList.size() > 0) {
							for (ProductDTO productDTO : productList) {
								String productImgUrl = productDTO.getImgUrl();
								if (!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")) {
									productImgUrl = Constants.P1 + productImgUrl;
									productDTO.setImgUrl(productImgUrl);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrdersByWofe()> is failed!!!!" + e.getMessage(), e);
		}

		if (statusList != null) {
			model.addAttribute("statusList", statusList);
		}
		if (pageType != null) {
			model.addAttribute("pageType", pageType);
		}

		model.addAttribute("pageBean", pageBean);

		LOGGER.info("end<> to execute method <getCustomerOrderPageBeanByCondition>!!!!");
		return getLanguage() + "/poporder/orderItem";
	}
	/**
	 * . 根据订单的ID查询订单的详情.
	 * 
	 * @Title getCustomerOrderDetailsById
	 * @param Long
	 *            orderId
	 * @param Model
	 *            model
	 * @return String
	 */
	@RequestMapping(value = "/getCustomerOrderDetailsById")
	public String getCustomerOrderDetailsById(Long orderId, Model model) {
		LOGGER.info("start to execute method <getCustomerOrderDetailsById>!!!!");
		LOGGER.info("订单号orderId:" + orderId);
		OrderDTO orderDTO = new OrderDTO();
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			orderDTO = customerOrderService.findCustomerOrderInfoByOrderId(orderId);
			if (null != orderDTO) {
				List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
				if (null != orderItemDTOs && orderItemDTOs.size() > 0) {
					for (OrderItemDTO orderItemDTO : orderItemDTOs) {
						String skuImageUrl = orderItemDTO.getImgUrl();
						if (!Common.isEmpty(skuImageUrl)) {
							if (!skuImageUrl.startsWith("http") || !skuImageUrl.startsWith("Http")) {
								skuImageUrl = Constants.P1 + skuImageUrl;
								orderItemDTO.setImgUrl(skuImageUrl);
							}
						}
					}
				}
				if (orderDTO.getSupplyType().equals("51")) {
					List<B2cShipOrderDTO> shipOrderDTOs = orderDTO.getShipOrderDTOs();
					if (null != shipOrderDTOs && shipOrderDTOs.size() > 0) {
						for (B2cShipOrderDTO b2cShipOrderDTO : shipOrderDTOs) {
							if (null != b2cShipOrderDTO) {
								List<B2cShipOrderItemDTO> shipOrderItems = b2cShipOrderDTO.getShipItemDtoList();
								if (null != shipOrderItems && shipOrderItems.size() > 0) {
									for (B2cShipOrderItemDTO b2cShipOrderItemDTO : shipOrderItems) {
										String imageUrl = b2cShipOrderItemDTO.getProductUrl();
										if (!Common.isEmpty(imageUrl)) {
											imageUrl = Constants.P1 + imageUrl;
											b2cShipOrderItemDTO.setProductUrl(imageUrl);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrderInfoByOrderId()> is failed!!!!" + e.getMessage(), e);
		}

		if (null != orderDTO) {
			if (null != orderDTO.getUserId()) {
				try {
					LOGGER.info("此订单用户ID:" + orderDTO.getUserId());
					UserService userService = RemoteServiceSingleton.getInstance().getUserService();
					User user = userService.findUserById(orderDTO.getUserId());
					model.addAttribute("user", user);
				} catch (Exception e) {
					LOGGER.error("UserService<findUserById()根据用户ID查询该用户的信息> is failed!!!!" + e.getMessage(), e);
				}
			}
		}
		model.addAttribute("orderDTO", orderDTO);
		LOGGER.info("end<> to execute method <getCustomerOrderDetailsById>!!!!");
		return getLanguage() + "/poporder/orderDetails";
	}

	/**
	 * 分单发货页面
	 */
	@RequestMapping(value = "/getCustomerOrderFenDanById")
	public String getCustomerOrderFenDanById(Long orderId, Model model) {
		LOGGER.info("start to execute method <getCustomerOrderDetailsById>!!!!");
		LOGGER.info("订单号orderId:" + orderId);
		OrderDTO orderDTO = new OrderDTO();
		LOGGER.info("start to execute method <getLogistic()获取所有物流商>!!!!");

		try {
			Object json = "[]";

			LogisticTempService logisticTempService = RemoteServiceSingleton.getInstance().getlogisticTempService();
			List<LogisticTemp> logisticTemps = logisticTempService.getProviders();

			model.addAttribute("logisticTemps", logisticTemps);

			LOGGER.info("物流商：" + json.toString());
			LOGGER.info("end<> to execute method <getLogistic()获取所有物流商>!!!!");

			model.addAttribute("language", getLanguage().substring(1));
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			orderDTO = customerOrderService.findCustomerOrderInfoByOrderId(orderId);
			if (null != orderDTO) {
				List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
				if (null != orderItemDTOs && orderItemDTOs.size() > 0) {
					for (OrderItemDTO orderItemDTO : orderItemDTOs) {
						String skuImageUrl = orderItemDTO.getImgUrl();
						if (!Common.isEmpty(skuImageUrl)) {
							if (!skuImageUrl.startsWith("http") || !skuImageUrl.startsWith("Http")) {
								skuImageUrl = Constants.P1 + skuImageUrl;
								orderItemDTO.setImgUrl(skuImageUrl);
							}
						}
					}
				}
				if (orderDTO.getSupplyType().equals("51")) {
					List<B2cShipOrderDTO> shipOrderDTOs = orderDTO.getShipOrderDTOs();
					if (null != shipOrderDTOs && shipOrderDTOs.size() > 0) {
						for (B2cShipOrderDTO b2cShipOrderDTO : shipOrderDTOs) {
							if (null != b2cShipOrderDTO) {
								List<B2cShipOrderItemDTO> shipOrderItems = b2cShipOrderDTO.getShipItemDtoList();
								if (null != shipOrderItems && shipOrderItems.size() > 0) {
									for (B2cShipOrderItemDTO b2cShipOrderItemDTO : shipOrderItems) {
										String imageUrl = b2cShipOrderItemDTO.getProductUrl();
										if (!Common.isEmpty(imageUrl)) {
											imageUrl = Constants.P1 + imageUrl;
											b2cShipOrderItemDTO.setProductUrl(imageUrl);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrderInfoByOrderId()> is failed!!!!" + e.getMessage(), e);
		}

		if (null != orderDTO) {
			if (null != orderDTO.getUserId()) {
				try {
					LOGGER.info("此订单用户ID:" + orderDTO.getUserId());
					UserService userService = RemoteServiceSingleton.getInstance().getUserService();
					User user = userService.findUserById(orderDTO.getUserId());
					model.addAttribute("user", user);
				} catch (Exception e) {
					LOGGER.error("UserService<findUserById()根据用户ID查询该用户的信息> is failed!!!!" + e.getMessage(), e);
				}
			}
		}
		model.addAttribute("orderDTO", orderDTO);
		LOGGER.info("end<> to execute method <getCustomerOrderDetailsById>!!!!");
		return getLanguage() + "/poporder/orderFenDan";
	}

	/**
	 * 配送
	 */
	@RequestMapping(value = "/getCustomerOrderPeiSongById")
	public String getCustomerOrderPeiSongById(Long orderId, Model model) {
		LOGGER.info("start to execute method <getCustomerOrderDetailsById>!!!!");
		LOGGER.info("订单号orderId:" + orderId);
		OrderDTO orderDTO = new OrderDTO();
		LOGGER.info("start to execute method <getLogistic()获取所有物流商>!!!!");

		try {
			Object json = "[]";

			LogisticTempService logisticTempService = RemoteServiceSingleton.getInstance().getlogisticTempService();
			List<LogisticTemp> logisticTemps = logisticTempService.getProviders();

			model.addAttribute("logisticTemps", logisticTemps);

			LOGGER.info("物流商：" + json.toString());
			LOGGER.info("end<> to execute method <getLogistic()获取所有物流商>!!!!");

			model.addAttribute("language", getLanguage().substring(1));
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			orderDTO = customerOrderService.findCustomerOrderInfoByOrderId(orderId);
			if (null != orderDTO) {
				List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
				if (null != orderItemDTOs && orderItemDTOs.size() > 0) {
					for (OrderItemDTO orderItemDTO : orderItemDTOs) {
						String skuImageUrl = orderItemDTO.getImgUrl();
						if (!Common.isEmpty(skuImageUrl)) {
							if (!skuImageUrl.startsWith("http") || !skuImageUrl.startsWith("Http")) {
								skuImageUrl = Constants.P1 + skuImageUrl;
								orderItemDTO.setImgUrl(skuImageUrl);
							}
						}
					}
				}
				if (orderDTO.getSupplyType().equals("51")) {
					List<B2cShipOrderDTO> shipOrderDTOs = orderDTO.getShipOrderDTOs();
					if (null != shipOrderDTOs && shipOrderDTOs.size() > 0) {
						for (B2cShipOrderDTO b2cShipOrderDTO : shipOrderDTOs) {
							if (null != b2cShipOrderDTO) {
								List<B2cShipOrderItemDTO> shipOrderItems = b2cShipOrderDTO.getShipItemDtoList();
								if (null != shipOrderItems && shipOrderItems.size() > 0) {
									for (B2cShipOrderItemDTO b2cShipOrderItemDTO : shipOrderItems) {
										String imageUrl = b2cShipOrderItemDTO.getProductUrl();
										if (!Common.isEmpty(imageUrl)) {
											imageUrl = Constants.P1 + imageUrl;
											b2cShipOrderItemDTO.setProductUrl(imageUrl);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrderInfoByOrderId()> is failed!!!!" + e.getMessage(), e);
		}

		if (null != orderDTO) {
			if (null != orderDTO.getUserId()) {
				try {
					LOGGER.info("此订单用户ID:" + orderDTO.getUserId());
					UserService userService = RemoteServiceSingleton.getInstance().getUserService();
					User user = userService.findUserById(orderDTO.getUserId());
					model.addAttribute("user", user);
				} catch (Exception e) {
					LOGGER.error("UserService<findUserById()根据用户ID查询该用户的信息> is failed!!!!" + e.getMessage(), e);
				}
			}
		}
		model.addAttribute("orderDTO", orderDTO);
		LOGGER.info("end<> to execute method <getCustomerOrderDetailsById>!!!!");
		return getLanguage() + "/poporder/orderPeiSong";
	}

	/**
	 * 配送完毕
	 */
	@RequestMapping("updateStatusPeisong")
	public String updateStatusPeisong(Long oid, Model model) {
		CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
		customerOrderService.customerOrderDelivery(oid, "sys");
		OrderDTO orderDTO = new OrderDTO();
		LOGGER.info("start to execute method <getLogistic()获取所有物流商>!!!!");

		try {
			Object json = "[]";

			LogisticTempService logisticTempService = RemoteServiceSingleton.getInstance().getlogisticTempService();
			List<LogisticTemp> logisticTemps = logisticTempService.getProviders();

			model.addAttribute("logisticTemps", logisticTemps);

			LOGGER.info("物流商：" + json.toString());
			LOGGER.info("end<> to execute method <getLogistic()获取所有物流商>!!!!");

			model.addAttribute("language", getLanguage().substring(1));

			orderDTO = customerOrderService.findCustomerOrderInfoByOrderId(oid);
			if (null != orderDTO) {
				List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
				if (null != orderItemDTOs && orderItemDTOs.size() > 0) {
					for (OrderItemDTO orderItemDTO : orderItemDTOs) {
						String skuImageUrl = orderItemDTO.getImgUrl();
						if (!Common.isEmpty(skuImageUrl)) {
							if (!skuImageUrl.startsWith("http") || !skuImageUrl.startsWith("Http")) {
								skuImageUrl = Constants.P1 + skuImageUrl;
								orderItemDTO.setImgUrl(skuImageUrl);
							}
						}
					}
				}
				if (orderDTO.getSupplyType().equals("51")) {
					List<B2cShipOrderDTO> shipOrderDTOs = orderDTO.getShipOrderDTOs();
					if (null != shipOrderDTOs && shipOrderDTOs.size() > 0) {
						for (B2cShipOrderDTO b2cShipOrderDTO : shipOrderDTOs) {
							if (null != b2cShipOrderDTO) {
								List<B2cShipOrderItemDTO> shipOrderItems = b2cShipOrderDTO.getShipItemDtoList();
								if (null != shipOrderItems && shipOrderItems.size() > 0) {
									for (B2cShipOrderItemDTO b2cShipOrderItemDTO : shipOrderItems) {
										String imageUrl = b2cShipOrderItemDTO.getProductUrl();
										if (!Common.isEmpty(imageUrl)) {
											imageUrl = Constants.P1 + imageUrl;
											b2cShipOrderItemDTO.setProductUrl(imageUrl);
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrderInfoByOrderId()> is failed!!!!" + e.getMessage(), e);
		}

		if (null != orderDTO) {
			if (null != orderDTO.getUserId()) {
				try {
					LOGGER.info("此订单用户ID:" + orderDTO.getUserId());
					UserService userService = RemoteServiceSingleton.getInstance().getUserService();
					User user = userService.findUserById(orderDTO.getUserId());
					model.addAttribute("user", user);
				} catch (Exception e) {
					LOGGER.error("UserService<findUserById()根据用户ID查询该用户的信息> is failed!!!!" + e.getMessage(), e);
				}
			}
		}
		model.addAttribute("orderDTO", orderDTO);
		return getLanguage() + "/poporder/orderPeiSong";
	}

	/**
	 * . wofe导出C订单.
	 * 
	 * @Title exportCustomerOrderExcel
	 * @param Long
	 *            orderId
	 * @param Model
	 *            model
	 * @return void
	 */
	@RequestMapping(value = "/exportCustomerOrderExcel")
	public void exportCustomerOrderExcel(HttpServletResponse response, String pName, Short status, String startTime,
			String endTime, Long orderId, String receiveName, String userName, Integer shipType, Integer payType,
			String statusList, Short orderPlatform, Short orderType, String companyQy) {
		// 判断是否是特卖商品
		LOGGER.info("start to execute method <exportCustomerOrderExcel>!!!!");
		LOGGER.info("订单编号orderId:" + orderId);
		LOGGER.info("订单状态码status:" + status);
		LOGGER.info("商品名称:" + pName);
		LOGGER.info("起始时间:" + startTime);
		LOGGER.info("结束时间:" + endTime);
		LOGGER.info("收货人receiveName:" + receiveName);
		LOGGER.info("用户名userName:" + userName);
		LOGGER.info("配送方式:" + shipType);
		LOGGER.info("支付方式:" + payType);
		LOGGER.info("状态列表:" + statusList);
		LOGGER.info("下单平台：" + orderPlatform);

		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		OrderDTO orderDTO = new OrderDTO();
		if (!Common.isEmpty(pName)) {
			orderDTO.setpName(pName);
		}
		if (!Common.isEmpty(startTime)) {
			startTime += " 00:00:00";
			orderDTO.setStartDate(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));

		}
		if (!Common.isEmpty(endTime)) {
			endTime += " 23:59:59";
			orderDTO.setEndDate(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if (null != companyQy) {
			if (companyQy.equals("8")) {
				short a = 39;
				orderDTO.setOrderType(a);
			} else {
				orderDTO.setCompanyQy(companyQy);
			}
		}
		if (!Common.isEmpty(receiveName)) {
			orderDTO.setReceiveName(receiveName);
		}
		if (!Common.isEmpty(statusList)) {
			List<Short> statusAll = new ArrayList<Short>();
			String[] statusArray = statusList.split(",");
			for (String statusSimple : statusArray) {
				try {
					statusAll.add(Short.parseShort(statusSimple));
				} catch (NumberFormatException e) {
					LOGGER.error("getCustomerOrderPageBeanByCondition is failed!!!!数字转换异常  status:" + status, e);
				}
			}
			orderDTO.setStatusList(statusAll);
		}
		if (!Common.isEmpty(userName)) {
			List<Long> userIds = new ArrayList<Long>();
			try {
				UserService userService = RemoteServiceSingleton.getInstance().getUserService();
				userIds = userService.findUserIdsByUserName(userName);
				if (userIds.size() < 1) {
					userIds.add(0l);
				}
			} catch (Exception e) {
				LOGGER.error("UserService<findUserIdsByUserName()根据用户用户名模糊查询ID的集合> is failed!!!!" + e.getMessage(), e);
			}
			orderDTO.setUserIds(userIds);
		}
		if (null != orderId) {
			orderDTO.setId(orderId);
			// orderDTO.getSupplyType() 1.国内 11.保税区 12.
		}
		if (null != status) {
			orderDTO.setStatus(status);
		}
		if (null != shipType) {
			orderDTO.setFiveShipType(shipType);
		}
		if (null != payType) {
			orderDTO.setFivePayType(payType);
		}
		if (null != orderPlatform) {
			orderDTO.setOrderPlatform(orderPlatform);
		}
		if (null != orderType) {
			orderDTO.setOrderType(orderType);
		}

		orderDTO.setSupplyType("51");
		if (null != getCurrentSupplierId()) {
			// 只查询当前商家的订单
			orderDTO.setMerchantid(String.valueOf(getCurrentSupplierId()));
		}

		pageBean.setParameter(orderDTO);
		pageBean.setPageSize(Constants.MAXPAGESIZE);
		HSSFWorkbook book = new HSSFWorkbook();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		HSSFSheet sheet = book.createSheet("customerOrderList");

		// 另一个字体样式
		HSSFFont columnHeadFont = book.createFont();
		columnHeadFont.setFontName("Times New Roman");
		columnHeadFont.setFontHeightInPoints((short) 12);
		// columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// 列头的样式
		HSSFCellStyle columnHeadStyle = book.createCellStyle();
		columnHeadStyle.setFont(columnHeadFont);
		columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		columnHeadStyle.setLocked(true);
		columnHeadStyle.setWrapText(true);
		columnHeadStyle.setBorderTop((short) 1);
		columnHeadStyle.setTopBorderColor(HSSFColor.BLACK.index);
		columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
		columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色
		columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
		columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色
		columnHeadStyle.setBorderRight((short) 1);// 边框的大小
		columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
		columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
		columnHeadStyle.setFillForegroundColor(HSSFColor.BLUE.index);// 设置单元格的背景颜色．
		columnHeadStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		columnHeadStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);
		// sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 9000);
		sheet.setColumnWidth(9, 5000);
		sheet.setColumnWidth(10, 5000);
		sheet.setColumnWidth(11, 7000);
		sheet.setColumnWidth(12, 7000);
		sheet.setColumnWidth(13, 3000);
		sheet.setColumnWidth(14, 3000);
		sheet.setColumnWidth(15, 3000);
		sheet.setColumnWidth(16, 3000);
		sheet.setColumnWidth(17, 3000);
		sheet.setColumnWidth(18, 6000);
		sheet.setColumnWidth(19, 3000);
		sheet.setColumnWidth(20, 3000);
		sheet.setColumnWidth(21, 6000);
		sheet.setColumnWidth(22, 6000);
		sheet.setColumnWidth(23, 6000);
		sheet.setColumnWidth(24, 6000);
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCell cell = null;
		String[] strtitle = { "订单号", "买家", "买家ID", "买家姓名", "货源种类", "收货人", "联系电话", "省", "市", "区", "收货地址", "商品名称", "商品ID",
				"国际条形码", "规格", "单价(元)", "数量", "小计(元)", "交易状态", "配送方式", "支付方式", "平台来源", "订单来源", "下单时间", "付款时间", "实付金额",
				"实付红旗劵", "实付现金" };
		for (int i = 0; i < strtitle.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(strtitle[i]);
			cell.setCellStyle(columnHeadStyle);
		}
		try {
			CustomerOrderExportSevice customerOrderExportSevice = RemoteServiceSingleton.getInstance()
					.getCustomerOrderExportService();
			// 返回数据总数
			int resultCount = customerOrderExportSevice.getCustomerOrdersCount(orderDTO);
			System.out.println("*************订单总数" + resultCount);
			int pageSize = 999;
			int totalPages = 0;
			if (resultCount > pageSize) {
				pageBean.setPageSize(pageSize);
				totalPages = resultCount % pageSize == 0 ? resultCount / pageSize : resultCount / pageSize + 1;
			} else {
				totalPages = 1;
			}
			List<OrderDTO> orderDTOs = null;
			for (int i = 0; i < totalPages; i++) {
				pageBean.setPage(i + 1);
				System.out.println("pageBean*************" + pageBean);
				pageBean = customerOrderExportSevice.exportCustomerOrders(pageBean);
				System.out.println("pageBean*************" + pageBean);
				if (i > 0) {
					orderDTOs.addAll(pageBean.getResult());
				} else
					orderDTOs = pageBean.getResult();
			}

			LOGGER.info("获取list.size()(1:合并2:rpc)------------" + orderDTOs.size() + "   =========     " + resultCount);
			if (null != orderDTOs && orderDTOs.size() > 0) {
				int r = 1;
				for (OrderDTO orderDTO2 : orderDTOs) {
					String supplyType_temp = "";
					switch (Integer.parseInt(orderDTO2.getSupplyType())) {
					case 1:
						supplyType_temp = "国内发货";
						break;
					case 11:
						supplyType_temp = "海外直邮";
						break;
					case 12:
						supplyType_temp = "保税区发货";
						break;
					case 21:
						supplyType_temp = "韩国直邮";
						break;
					case 50:
						supplyType_temp = "海外预售";
						break;
					case 51:
						supplyType_temp = "第三方国际发货(POP)";
						break;
					}
					String statu = "";
					boolean fail = false;
					switch (Integer.parseInt(orderDTO2.getStatus() + "")) {
					case 1:
						statu = "待支付";
						break;
					case 21:
						statu = "已支付";
						break;
					case 31:
						statu = "待海关审核";
						break;
					case 41:
						statu = "待发货";
						break;
					case 67:
						statu = "海关审核订单失败(待退款)";
						fail = true;
						break;
					case 68:
						statu = "海关支付单审核失败";
						fail = true;
						break;
					case 69:
						statu = "海关物流单审核失败";
						fail = true;
						break;
					case 70:
						statu = "海关审核失败";
						fail = true;
						break;
					case 79:
						statu = "已退款";
						break;
					case 81:
						statu = "待收货";
						break;
					case 91:
						statu = "已收货";
						break;
					case 99:
						statu = "用户取消";
						break;
					case 100:
						statu = "自动取消";
						break;
					}

					String shipType_ = "";
					switch (orderDTO2.getShipType()) {
					case 0:
						shipType_ = "普通";
						break;
					case 1:
						shipType_ = "自提";
						break;
					}

					String payType_ = "";
					switch (orderDTO2.getPayType()) {
					case 0:
						payType_ = "线上";
						break;
					case 1:
						payType_ = "货到付款";
						break;
					}

					String orderPlatform_ = "";
					if (orderDTO2.getOrderPlatform() != null) {
						switch (orderDTO2.getOrderPlatform()) {
						case 1:
							orderPlatform_ = "PC";
							break;
						case 2:
							orderPlatform_ = "ANDROID";
							break;
						case 3:
							orderPlatform_ = "WAP";
							break;
						case 4:
							orderPlatform_ = "IOS";
							break;
						case 6:
							orderPlatform_ = "5S";
							break;
						}
					}
					List<OrderItemDTO> orderItemDTOs = orderDTO2.getOrderItemDTOs();
					UserService userService = RemoteServiceSingleton.getInstance().getUserService();
					if (null != orderItemDTOs && orderItemDTOs.size() > 0) {
						for (OrderItemDTO orderItemDTO : orderItemDTOs) {

							User user = userService.findUserById(orderDTO2.getUserId());
							row = sheet.createRow(r++);
							if (null != orderDTO2) {
								row.createCell(Constants.SHORT0)
										.setCellValue(null != orderDTO2.getId() ? orderDTO2.getId() + "" : "无");
								row.createCell(Constants.SHORT1).setCellValue(
										null != orderDTO2.getUserName() ? orderDTO2.getUserName() + "" : "无");
								row.createCell(Constants.SHORT2)
										.setCellValue(null != orderDTO2.getUserId() ? orderDTO2.getUserId() + "" : "无");
								row.createCell(Constants.SHORT5).setCellValue(
										null != orderDTO2.getReceiveName() ? orderDTO2.getReceiveName() + "" : "无");
								row.createCell(Constants.SHORT6).setCellValue(null != orderDTO2.getReceiveMobilePhone()
										? orderDTO2.getReceiveMobilePhone() + "" : "无");
								row.createCell(Constants.SHORT7).setCellValue(
										null != orderDTO2.getProvinceName() ? orderDTO2.getProvinceName() + "" : "无");
								row.createCell(Constants.SHORT8).setCellValue(
										null != orderDTO2.getCityName() ? orderDTO2.getCityName() + "" : "无");
								row.createCell(Constants.SHORT9).setCellValue(
										null != orderDTO2.getAreaName() ? orderDTO2.getAreaName() + "" : "无");
								row.createCell(Constants.SHORT10).setCellValue(null != orderDTO2.getReceiveAddress()
										? orderDTO2.getReceiveAddress() + "" : "无");
								row.createCell(Constants.SHORT22).setCellValue(
										null != orderDTO2.getOrderSource() ? orderDTO2.getOrderSource() + "" : "无");
								row.createCell(Constants.SHORT23).setCellValue(null != orderDTO2.getCreateTime()
										? simpleDateFormat.format(orderDTO2.getCreateTime()) + "" : "无");
								row.createCell(Constants.SHORT24).setCellValue(null != orderDTO2.getPayTime()
										? simpleDateFormat.format(orderDTO2.getPayTime()) + "" : "无");
								// if(orderDTO2.getPayTime() != null){
								// row.createCell(Constants.SHORT25).setCellValue(simpleDateFormat.format(orderDTO2.getPayTime()));
								// }
								// else{
								// row.createCell(Constants.SHORT25).setCellValue("");
								// }
							}

							if (null != user) {
								row.createCell(Constants.SHORT3)
										.setCellValue(null != user.getRealName() ? user.getRealName() : "无");// 买家真实名称
								// row.createCell(Constants.SHORT4).setCellValue(null!=user.getIdCard()?user.getIdCard():"无");//买家身份证号码
							} else {
								row.createCell(Constants.SHORT3).setCellValue("无");// 买家真实名称
								// row.createCell(Constants.SHORT4).setCellValue("无");//买家身份证号码
							}

							row.createCell(Constants.SHORT4).setCellValue(supplyType_temp);

							row.createCell(Constants.SHORT11)
									.setCellValue(null != orderItemDTO.getpName() ? orderItemDTO.getpName() : "无");
							row.createCell(Constants.SHORT12)
									.setCellValue(null != orderItemDTO.getPid() ? orderItemDTO.getPid() + "" : "无");
							row.createCell(Constants.SHORT13)
									.setCellValue(null != orderItemDTO.getSkuCode() ? orderItemDTO.getSkuCode() : "无");
							row.createCell(Constants.SHORT14).setCellValue(
									null != orderItemDTO.getSkuNameCn() ? orderItemDTO.getSkuNameCn() : "无");
							row.createCell(Constants.SHORT15)
									.setCellValue(null != orderItemDTO.getPrice() ? orderItemDTO.getPrice() + "" : "无");
							row.createCell(Constants.SHORT16)
									.setCellValue(null != orderItemDTO.getSkuQty() ? orderItemDTO.getSkuQty() : 0);
							row.createCell(Constants.SHORT17).setCellValue(null != orderItemDTO.getPrice()
									? orderItemDTO.getPrice().multiply(new BigDecimal(orderItemDTO.getSkuQty())) + ""
									: "无");
							row.createCell(Constants.SHORT18).setCellValue(statu);
							row.createCell(Constants.SHORT19).setCellValue(shipType_);
							row.createCell(Constants.SHORT20).setCellValue(payType_);
							row.createCell(Constants.SHORT21).setCellValue(orderPlatform_);
							row.createCell(Constants.SHORT25).setCellValue(
									null != orderDTO2.getPaidPrice() ? orderDTO2.getPaidPrice() + "" : 0 + "");
							int intValue = 0;
							if (orderDTO2.getPaidPrice() != null) {
								intValue = orderDTO2.getPaidPrice().intValue();
							}
							int parseInt = -1;
							if (orderDTO2.getPayWay() != null) {
								parseInt = Integer.parseInt(orderDTO2.getPayWay());
							}
							int intValue2 = orderDTO2.getCashPrice().intValue();
							int intValue3 = orderDTO2.getHqjPrice().intValue();
							int parseInt2 = 0;
							if (orderDTO2.getMixPayStatus() != null) {
								parseInt2 = Integer.parseInt(orderDTO2.getMixPayStatus());
							}
							if (parseInt2 == 1 && intValue > 0) {
								if (intValue2 > 0 && intValue3 > 0 && orderDTO2.getOrderType() == 39) {

									BigDecimal decimal = orderDTO2.getCashPrice();
									BigDecimal multiply = orderDTO2.getHqjPrice();
									row.createCell(Constants.SHORT26).setCellValue(decimal + "");
									row.createCell(Constants.SHORT27).setCellValue(multiply + "");
								}
							}

							// if(fail){
							// row.createCell(Constants.SHORT26).setCellValue(simpleDateFormat.format(orderDTO2.getLastEditTime()));
							// }
							// else{
							// row.createCell(Constants.SHORT26).setCellValue("");
							// }
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.info("exportCustomerOrders is failed" + e.getMessage(), e);
		}
		try {
			String filename = "customer-order" + simpleDateFormat.format(new Date());
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setCharacterEncoding("UTF-8");
			filename = java.net.URLEncoder.encode(filename, "gb2312");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes("UTF-8"), "GBK") + ".xls");
			response.setContentType("application/msexcel");// 定义输出类型
			book.write(os);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			LOGGER.info("数据下载异常.稍后再试。。。");
		}
		LOGGER.info("end<> to execute method <exportCustomerOrderExcel>!!!!");
	}

	/**
	 * .
	 * 
	 * @Title：getStockWofeType
	 * @Description:补录物流时异步加载所有物流商.
	 * @param response
	 */
	@RequestMapping(value = "/getLogistic")
	public void getLogistic(HttpServletResponse response) {
		LOGGER.info("start to execute method <getLogistic()获取所有物流商>!!!!");
		Object json = "[]";
		try {
			LogisticTempService logisticTempService = RemoteServiceSingleton.getInstance().getlogisticTempService();
			List<LogisticTemp> logisticTemps = logisticTempService.getProviders();
			json = JSONArray.toJSON(logisticTemps);

			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			LOGGER.error("LogisticTempService<getProviders()<获取所有物流商服务>> is failed!!!!" + e.getMessage(), e);
		}
		LOGGER.info("物流商：" + json.toString());
		LOGGER.info("end<> to execute method <getLogistic()获取所有物流商>!!!!");
	}

	/**
	 * . WOFE补录过郑州海关的订单的物流<补录C订单物流>.
	 * 
	 * @title updateOrderLogisticsById
	 * @DATE 2015年8月17日14:36:25
	 * @param Long
	 *            orderId
	 * @param String
	 *            logisticsCarriersName
	 * @param String
	 *            logisticsNumber
	 * @param Long
	 *            logisticsCarriersId
	 * @return String
	 */
	@RequestMapping(value = "/updateOrderLogisticsById")
	@ResponseBody
	public String updateOrderLogisticsById(Long orderId, String logisticsCarriersName, String logisticsNumber,
			Long logisticsCarriersId) {
		LOGGER.info("start to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		LOGGER.info("订单编号:" + orderId);
		LOGGER.info("物流商名称:" + logisticsCarriersName);
		LOGGER.info("物流商编号:" + logisticsCarriersId);
		LOGGER.info("物流商编码:" + logisticsNumber);
		boolean flag = true;
		String result = "";
		try {
			// TODO 设置物流单号需要测试，校验是否可用
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();

			// 校验物流单号是否已经存在
			/*
			 * boolean isExist =
			 * customerOrderService.exitsLogistics(logisticsNumber,
			 * logisticsCarriersId);
			 */
			/*
			 * if (isExist) { return "订单物流信息录入失败，物流单号已经存在。"; }
			 */

			// 获取订单信息
			OrderDTO orderDTO = customerOrderService.findCustomerOrderInfoByOrderId(orderId);
			List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();

			ShipOrderDTO dto = new ShipOrderDTO();
			dto.setLogisticsCarriersId(logisticsCarriersId);
			dto.setLogisticsCarriersName(logisticsCarriersName);
			dto.setLogisticsNumber(logisticsNumber);
			dto.setIsFuture(RetailerConstant.IS_FUTURES_NO);
			dto.setPayId(orderDTO.getPayId());
			dto.setShipperId(logisticsCarriersId);
			dto.setWarehouseCode(Long.valueOf(orderDTO.getMerchantid()));
			dto.setRetailerId(0L);
			dto.setOwnerType((short) 0);
			dto.setReceiveAddress(orderDTO.getReceiveAddress());
			dto.setReceiveName(orderDTO.getReceiveName());
			dto.setReceivePhone(orderDTO.getReceivePhone());
			dto.setReceiveMobilePhone(orderDTO.getReceiveMobilePhone());
			dto.setReceiveProvinceId(orderDTO.getReceiveProvinceId());
			dto.setReceiveCityId(orderDTO.getReceiveCityId());
			dto.setReceiveAreaId(orderDTO.getReceiveAreaId());
			dto.setMessage(orderDTO.getMessage());
			dto.setCreateBy("sys");
			dto.setLastEditBy("sys");
			dto.setUserId(orderDTO.getUserId());
			dto.setOrderType(2);// b2c
			dto.setOrderId(orderDTO.getId());
			dto.setStatus((short) 2);
			int qty = 0;
			BigDecimal pirce = new BigDecimal(0);
			List<ShipOrderItemDTO> itemList = new ArrayList<ShipOrderItemDTO>();
			for (OrderItemDTO tmp : orderItemDTOs) {
				ShipOrderItemDTO item = new ShipOrderItemDTO();
				item.setPid(tmp.getPid());
				item.setSkuId(tmp.getSkuId());
				item.setpName(tmp.getpName());
				item.setSkuName(tmp.getSkuNameCn());
				item.setQty(tmp.getSkuQty());
				item.setPirce(tmp.getPrice());
				itemList.add(item);
				qty += tmp.getSkuQty();
				pirce = pirce.add(tmp.getPrice().multiply(new BigDecimal(item.getQty())));
			}
			dto.setPrice(pirce);
			dto.setQty(qty);
			dto.setShipItemList(itemList);

			ShipOrderB2CService shipOrderB2CService = RemoteServiceSingleton.getInstance().getShipOrderB2CService();
			shipOrderB2CService.insertShipOrderpop(dto);

			// 保存物流信息到订单表
			customerOrderService.modifylogisticsInfoByOrderId(orderId, logisticsNumber, logisticsCarriersId,
					logisticsCarriersName);

			// 修改订单状态，扣减库存
			customerOrderService.customerOrderDelivery(orderId, "sys");

		} catch (Exception e) {
			flag = false;
			LOGGER.error("customerOrderService.updateOrderLogisticsById()通过订单号修改物流信息 is failed!!!!" + e.getMessage(),
					e);
		}
		if (flag) {
			result = "1";
		} else {
			result = "订单物流信息录入失败!";
		}
		LOGGER.info("end<> to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		return result;
	}

	/**
	 * 
	 * @param orderId
	 * @param logisticsCarriersName
	 *            provider_id 物流商id
	 * @param logisticsNumber
	 *            物流单号
	 * @return
	 */
	@RequestMapping(value = "/updateOrderLogisticsById2")
	@ResponseBody
	public String updateOrderLogisticsById2(Long orderId, Long[] logisticsCarriersName, String[] logisticsNumber) {
		LOGGER.info("start to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		LOGGER.info("订单编号:" + orderId);
		LOGGER.info("物流商编号:" + logisticsCarriersName);
		LOGGER.info("物流商编码:" + logisticsNumber);
		boolean flag = true;
		String result = "";

		try {
			// TODO 设置物流单号需要测试，校验是否可用
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();

			// 校验物流单号是否已经存在
			/*
			 * boolean isExist =
			 * customerOrderService.exitsLogistics(logisticsNumber,
			 * logisticsCarriersId);
			 */
			/*
			 * if (isExist) { return "订单物流信息录入失败，物流单号已经存在。"; }
			 */

			// 获取订单信息
			OrderDTO orderDTO = customerOrderService.findCustomerOrderInfoByOrderId(orderId);
			List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
			ShipOrderB2CService shipOrderB2CService = RemoteServiceSingleton.getInstance().getShipOrderB2CService();
			LogisticTempService logisticTempService = RemoteServiceSingleton.getInstance().getlogisticTempService();

			for (int i = 0; i < logisticsCarriersName.length; i++) {
				ShipOrderDTO dto = new ShipOrderDTO();
				dto.setLogisticsCarriersId((logisticsCarriersName[i]));
				dto.setLogisticsCarriersName(
						logisticTempService.getProviderByPvId(logisticsCarriersName[i]).getProviderName());
				dto.setLogisticsNumber(logisticsNumber[i]);
				dto.setIsFuture(RetailerConstant.IS_FUTURES_NO);
				dto.setPayId(orderDTO.getPayId());
				dto.setShipperId(orderId);
				dto.setWarehouseCode(Long.valueOf(orderDTO.getMerchantid()));
				dto.setRetailerId(0L);
				dto.setOwnerType((short) 0);
				dto.setReceiveAddress(orderDTO.getReceiveAddress());
				dto.setReceiveName(orderDTO.getReceiveName());
				dto.setReceivePhone(orderDTO.getReceivePhone());
				dto.setReceiveMobilePhone(orderDTO.getReceiveMobilePhone());
				dto.setReceiveProvinceId(orderDTO.getReceiveProvinceId());
				dto.setReceiveCityId(orderDTO.getReceiveCityId());
				dto.setReceiveAreaId(orderDTO.getReceiveAreaId());
				dto.setMessage(orderDTO.getMessage());
				dto.setCreateBy("sys");
				dto.setLastEditBy("sys");
				dto.setUserId(orderDTO.getUserId());
				dto.setOrderType(2);// b2c
				dto.setOrderId(orderDTO.getId());
				dto.setStatus((short) 2);
				int qty = 0;
				BigDecimal pirce = new BigDecimal(0);
				List<ShipOrderItemDTO> itemList = new ArrayList<ShipOrderItemDTO>();
				List<ShipOrderItemDTO> itemList2 = new ArrayList<ShipOrderItemDTO>();
				for (OrderItemDTO tmp : orderItemDTOs) {
					ShipOrderItemDTO item = new ShipOrderItemDTO();
					item.setPid(tmp.getPid());
					item.setSkuId(tmp.getSkuId());
					item.setpName(tmp.getpName());
					item.setSkuName(tmp.getSkuNameCn());
					item.setQty(tmp.getSkuQty());
					item.setPirce(tmp.getPrice());
					itemList.add(item);
					qty += tmp.getSkuQty();
					pirce = pirce.add(tmp.getPrice().multiply(new BigDecimal(item.getQty())));
				}
				dto.setPrice(pirce);
				dto.setQty(qty);
				itemList2.add(itemList.get(i));
				dto.setShipItemList(itemList2);
				shipOrderB2CService.insertShipOrderpop(dto);
			}

			// 保存物流信息到订单表
			customerOrderService.modifylogisticsInfoByOrderId(orderId, logisticsNumber[0], logisticsCarriersName[0],
					logisticTempService.getProviderByPvId(logisticsCarriersName[0]).getProviderName());

			// 修改订单状态，扣减库存
			customerOrderService.customerOrderDelivery(orderId, "sys");

		} catch (Exception e) {
			flag = false;
			LOGGER.error("customerOrderService.updateOrderLogisticsById()通过订单号修改物流信息 is failed!!!!" + e.getMessage(),
					e);
		}
		if (flag) {
			result = "1";
		} else {
			result = "订单物流信息录入失败!";
		}
		LOGGER.info("end<> to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		return result;
	}

	/**
	 * 配送
	 * 
	 * @param orderId
	 * @param logisticsCarriersName
	 * @param logisticsNumber
	 * @return
	 */
	@RequestMapping(value = "/updateOrderLogisticsById3")
	@ResponseBody
	public String updateOrderLogisticsById3(Long orderId, Long[] logisticsCarriersName, String[] logisticsNumber,
			String[] qty) {
		LOGGER.info("start to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		LOGGER.info("订单编号:" + orderId);
		LOGGER.info("物流商编号:" + logisticsCarriersName);
		LOGGER.info("物流商编码:" + logisticsNumber);
		boolean flag = true;
		String result = "";

		try {
			// TODO 设置物流单号需要测试，校验是否可用
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();

			// 校验物流单号是否已经存在
			/*
			 * boolean isExist =
			 * customerOrderService.exitsLogistics(logisticsNumber,
			 * logisticsCarriersId);
			 */
			/*
			 * if (isExist) { return "订单物流信息录入失败，物流单号已经存在。"; }
			 */

			// 获取订单信息
			OrderDTO orderDTO = customerOrderService.findCustomerOrderInfoByOrderId(orderId);
			List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
			ShipOrderB2CService shipOrderB2CService = RemoteServiceSingleton.getInstance().getShipOrderB2CService();
			LogisticTempService logisticTempService = RemoteServiceSingleton.getInstance().getlogisticTempService();
			List<String> logisticsNumberList = new ArrayList<String>();
			List<Long> logisticsCarriersNameList = new ArrayList<Long>();
			for (int i = 0; i < logisticsCarriersName.length; i++) {
				if (logisticsNumber[i] != "" && logisticsNumber[i] != null) {
					logisticsNumberList.add(logisticsNumber[i]);
					logisticsCarriersNameList.add(logisticsCarriersName[i]);
				}
			}

			for (int i = 0; i < logisticsNumberList.size(); i++) {
				ShipOrderDTO dto = new ShipOrderDTO();
				dto.setLogisticsCarriersId((logisticsCarriersName[i]));
				dto.setLogisticsCarriersName(
						logisticTempService.getProviderByPvId(logisticsCarriersName[i]).getProviderName());
				dto.setLogisticsNumber(logisticsNumber[i]);
				dto.setIsFuture(RetailerConstant.IS_FUTURES_NO);
				dto.setPayId(orderDTO.getPayId());
				dto.setShipperId(orderId);
				dto.setWarehouseCode(Long.valueOf(orderDTO.getMerchantid()));
				dto.setRetailerId(0L);
				dto.setOwnerType((short) 0);
				dto.setReceiveAddress(orderDTO.getReceiveAddress());
				dto.setReceiveName(orderDTO.getReceiveName());
				dto.setReceivePhone(orderDTO.getReceivePhone());
				dto.setReceiveMobilePhone(orderDTO.getReceiveMobilePhone());
				dto.setReceiveProvinceId(orderDTO.getReceiveProvinceId());
				dto.setReceiveCityId(orderDTO.getReceiveCityId());
				dto.setReceiveAreaId(orderDTO.getReceiveAreaId());
				dto.setMessage(orderDTO.getMessage());
				dto.setCreateBy("sys");
				dto.setLastEditBy("sys");
				dto.setUserId(orderDTO.getUserId());
				dto.setOrderType(2);// b2c
				dto.setOrderId(orderDTO.getId());
				dto.setStatus((short) 2);
				int qty2 = 0;
				BigDecimal pirce = new BigDecimal(0);
				List<ShipOrderItemDTO> itemList = new ArrayList<ShipOrderItemDTO>();
				/*
				 * for (OrderItemDTO tmp : orderItemDTOs) { ShipOrderItemDTO
				 * item = new ShipOrderItemDTO(); item.setPid(tmp.getPid());
				 * item.setSkuId(tmp.getSkuId()); item.setpName(tmp.getpName());
				 * item.setSkuName(tmp.getSkuNameCn());
				 * item.setQty(tmp.getSkuQty()); item.setPirce(tmp.getPrice());
				 * itemList.add(item); qty2 += tmp.getSkuQty(); pirce =
				 * pirce.add(tmp.getPrice().multiply(new
				 * BigDecimal(item.getQty()))); }
				 */
				for (int a = 0; a < qty.length; a++) {
					ShipOrderItemDTO item = new ShipOrderItemDTO();
					item.setPid(orderItemDTOs.get(a).getPid());
					item.setSkuId(orderItemDTOs.get(a).getSkuId());
					item.setpName(orderItemDTOs.get(a).getpName());
					item.setSkuName(orderItemDTOs.get(a).getSkuNameCn());
					item.setQty(Integer.parseInt(qty[a]));
					item.setPirce(orderItemDTOs.get(a).getPrice());
					itemList.add(item);
					qty2 += orderItemDTOs.get(a).getSkuQty();
					pirce = pirce.add(
							orderItemDTOs.get(a).getPrice().multiply(new BigDecimal(orderItemDTOs.get(a).getSkuQty())));
				}
				dto.setPrice(pirce);
				dto.setQty(qty2);
				dto.setShipItemList(itemList);
				shipOrderB2CService.insertShipOrderpop(dto);
			}

			// 保存物流信息到订单表
			customerOrderService.modifylogisticsInfoByOrderId(orderId, logisticsNumber[0], logisticsCarriersName[0],
					logisticTempService.getProviderByPvId(logisticsCarriersName[0]).getProviderName());

			// 修改订单状态，扣减库存
			customerOrderService.customerOrderDelivery(orderId, "sys");

		} catch (Exception e) {
			flag = false;
			LOGGER.error("customerOrderService.updateOrderLogisticsById()通过订单号修改物流信息 is failed!!!!" + e.getMessage(),
					e);
		}
		if (flag) {
			result = "1";
		} else {
			result = "订单物流信息录入失败!";
		}
		LOGGER.info("end<> to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		return result;
	}

	/**
	 * 跳转订单结算页面
	 */
	@RequestMapping(value = "/balanceOrderList")
	public String getProductPage(Model model) {

		if (Constants.MANUFACTURER_TRADERS.equals(getSupplierType()) || Constants.TRADERS.equals(getSupplierType())) {
			List<Supplier> subSuppliers = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.getSubSuppliersByPid(getCurrentSupplierId());

			model.addAttribute("subSuppliers", subSuppliers);
		}

		return "/zh/product/orderBalance";

	}

	/**
	 * <p>
	 * 结算订单数据
	 * </p>
	 * 
	 * @param model
	 * @return
	 * @auth:zw
	 */
	@RequestMapping(value = "/getBalanceOrderList", method = RequestMethod.POST)
	public String getBalanceOrderList(Model model, PageBean<SqwQiyeAccountRecode> pageBean, String accountId,
			Integer page, String type, String fromDate, String toDate, String oid) {

		Long currentSupplierId = getCurrentSupplierId(); // 获取当前登录用户的supplierId
		Map<String, Object> map = new HashMap<String, Object>();
		if (currentSupplierId != null) {
			map.put("qyId", currentSupplierId);
		}
		map.put("accountId", 1); // 默认传1
		if (!StringUtils.isEmpty(type)) {
			map.put("accountRecordStatus", Integer.valueOf(type));
		}
		if (StringUtils.isNotBlank(fromDate)) {
			map.put("fromDate", fromDate + " 00:00:00");
		}
		if (StringUtils.isNotBlank(toDate)) {
			map.put("toDate", toDate + " 23:59:59");
		}
		if (StringUtils.isNotBlank(oid)) {
			map.put("refObjId", oid);
		}
		pageBean.setParameter(map);
		if (null != page && page != 0) {
			pageBean.setPage(page);
		} else {
			pageBean.setPage(1);
		}
		pageBean.setOrder("desc");
		pageBean.setSortFields("id");
		PageBean<SqwQiyeAccountRecode> balancePage = new PageBean<SqwQiyeAccountRecode>();
		try {
			balancePage = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
					.getQiyeAccountBalanceDetailsPageList(pageBean);
		} catch (Exception e) {
			logger.error("获取结算订单异常：e:", e);
			e.printStackTrace();
		}
		if (type.equals("1")) { // 未结算
			try {
				BigDecimal noHqq = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
						.getQiyeAccountBalanceInvalid(currentSupplierId, 1);
				model.addAttribute("noHqq", noHqq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (type.equals("2")) { // 已结算
			try {
				BigDecimal yesHqq = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
						.getQiyeAccountRecordBalance(currentSupplierId, 1);
				model.addAttribute("yesHqq", yesHqq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("type", type);
		model.addAttribute("pb2", balancePage);
		model.addAttribute("balancePage", balancePage);
		return "/zh/product/modelPage/balanceOrder_model";
	}

	/**
	 * 导出结算订单
	 * 
	 * @param response
	 * @param startTime
	 * @param endTime
	 */
	@RequestMapping(value = "/exportExcel")
	public void exportCustomerOrderExcel(HttpServletResponse response, String startTime, String endTime, String oid,
			String type) {

		LOGGER.info("起始时间:" + startTime);
		LOGGER.info("结束时间:" + endTime);
		LOGGER.info("订单id:" + oid);
		LOGGER.info("订单类型:" + type);

		Long currentSupplierId = getCurrentSupplierId(); // 获取当前登录用户的supplierId

		Map<String, Object> map = new HashMap<String, Object>();
		SqwQiyeAccountRecode orderDTO = new SqwQiyeAccountRecode();

		if (!StringUtils.isEmpty(type)) {
			map.put("accountRecordStatus", Integer.valueOf(type));
		}
		if (currentSupplierId != null) {
			map.put("qyId", currentSupplierId);
		}
		map.put("accountId", 1); // 默认传1
		if (!Common.isEmpty(startTime)) {

			map.put("fromDate", startTime + " 00:00:00");

		}
		if (!Common.isEmpty(endTime)) {

			map.put("toDate", endTime + " 00:00:00");
		}
		if (!Common.isEmpty(oid)) {
			map.put("refObjId", oid);
		}

		PageBean<SqwQiyeAccountRecode> balancePage = new PageBean<SqwQiyeAccountRecode>();
		balancePage.setParameter(map);
		balancePage.setPageSize(Constants.MAXPAGESIZE);

		HSSFWorkbook book = new HSSFWorkbook();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		HSSFSheet sheet = book.createSheet("customerOrderList");

		// 另一个字体样式
		HSSFFont columnHeadFont = book.createFont();
		columnHeadFont.setFontName("Times New Roman");
		columnHeadFont.setFontHeightInPoints((short) 12);
		// columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// 列头的样式
		HSSFCellStyle columnHeadStyle = book.createCellStyle();
		columnHeadStyle.setFont(columnHeadFont);
		columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		columnHeadStyle.setLocked(true);
		columnHeadStyle.setWrapText(true);
		columnHeadStyle.setBorderTop((short) 1);
		columnHeadStyle.setTopBorderColor(HSSFColor.BLACK.index);
		columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
		columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色
		columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
		columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色
		columnHeadStyle.setBorderRight((short) 1);// 边框的大小
		columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
		columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
		columnHeadStyle.setFillForegroundColor(HSSFColor.BLUE.index);// 设置单元格的背景颜色．
		columnHeadStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		columnHeadStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		sheet.setColumnWidth(0, 7000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 4000);
		// sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);

		HSSFRow row = sheet.createRow((int) 0);
		HSSFCell cell = null;
		String[] strtitle = { "日期", "订单编号", "支付方式", "应结算红旗券", "欠款扣账", "扣减平台交易费", "订单类型" };
		for (int i = 0; i < strtitle.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(strtitle[i]);
			cell.setCellStyle(columnHeadStyle);
		}

		try {

			int r = 1;

			balancePage = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
					.getQiyeAccountBalanceDetailsPageList(balancePage);
			List<SqwQiyeAccountRecode> list = balancePage.getResult();

			for (SqwQiyeAccountRecode sqwQiyeAccountRecode : list) {

				row = sheet.createRow(r++);

				row.createCell(Constants.SHORT0).setCellValue(null != sqwQiyeAccountRecode.getOperatorTime()
						? simpleDateFormat.format(sqwQiyeAccountRecode.getOperatorTime()) + "" : "无");
				row.createCell(Constants.SHORT1).setCellValue(
						null != sqwQiyeAccountRecode.getRefObjId() ? sqwQiyeAccountRecode.getRefObjId() + "" : "无");
				row.createCell(Constants.SHORT2).setCellValue("红旗劵");
				row.createCell(Constants.SHORT3).setCellValue(
						null != sqwQiyeAccountRecode.getEarning() ? sqwQiyeAccountRecode.getEarning() + "" : "无");
				row.createCell(Constants.SHORT4).setCellValue(
						null != sqwQiyeAccountRecode.getPayFenqi() ? sqwQiyeAccountRecode.getPayFenqi() + "" : "无");
				row.createCell(Constants.SHORT5).setCellValue(
						null != sqwQiyeAccountRecode.getPayDealfee() ? sqwQiyeAccountRecode.getPayDealfee() + "" : "无");
				row.createCell(Constants.SHORT6)
						.setCellValue(null != sqwQiyeAccountRecode.getValidTime() ? "结算订单" : "未结算订单");

			}

		} catch (Exception e) {
			LOGGER.info("exportCustomerOrders is failed" + e.getMessage(), e);
		}
		try {
			String filename = "customer-order" + simpleDateFormat.format(new Date());
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setCharacterEncoding("UTF-8");
			filename = java.net.URLEncoder.encode(filename, "gb2312");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes("UTF-8"), "GBK") + ".xls");
			response.setContentType("application/msexcel");// 定义输出类型
			book.write(os);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			LOGGER.info("数据下载异常.稍后再试。。。");
		}
		LOGGER.info("end<> to execute method <exportCustomerOrderExcel>!!!!");
	}

	@RequestMapping(value = "/updateOrderLogisticsById4")
	@ResponseBody
	public String updateOrderLogisticsById4(Long orderId, String logisticsCarriersName, String logisticsNumber,
			Long logisticsCarriersId, String qqty) {
		LOGGER.info("start to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		LOGGER.info("订单编号:" + orderId);
		LOGGER.info("物流商名称:" + logisticsCarriersName);
		LOGGER.info("物流商编号:" + logisticsCarriersId);
		LOGGER.info("物流商编码:" + logisticsNumber);
		boolean flag = true;
		String result = "";
		try {
			// TODO 设置物流单号需要测试，校验是否可用
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();

			// 校验物流单号是否已经存在
			/*
			 * boolean isExist =
			 * customerOrderService.exitsLogistics(logisticsNumber,
			 * logisticsCarriersId);
			 */
			/*
			 * if (isExist) { return "订单物流信息录入失败，物流单号已经存在。"; }
			 */

			// 获取订单信息
			OrderDTO orderDTO = customerOrderService.findCustomerOrderInfoByOrderId(orderId);
			List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();

			ShipOrderDTO dto = new ShipOrderDTO();
			dto.setLogisticsCarriersId(logisticsCarriersId);
			dto.setLogisticsCarriersName(logisticsCarriersName);
			dto.setLogisticsNumber(logisticsNumber);
			dto.setIsFuture(RetailerConstant.IS_FUTURES_NO);
			dto.setPayId(orderDTO.getPayId());
			dto.setShipperId(logisticsCarriersId);
			dto.setWarehouseCode(Long.valueOf(orderDTO.getMerchantid()));
			dto.setRetailerId(0L);
			dto.setOwnerType((short) 0);
			dto.setReceiveAddress(orderDTO.getReceiveAddress());
			dto.setReceiveName(orderDTO.getReceiveName());
			dto.setReceivePhone(orderDTO.getReceivePhone());
			dto.setReceiveMobilePhone(orderDTO.getReceiveMobilePhone());
			dto.setReceiveProvinceId(orderDTO.getReceiveProvinceId());
			dto.setReceiveCityId(orderDTO.getReceiveCityId());
			dto.setReceiveAreaId(orderDTO.getReceiveAreaId());
			dto.setMessage(orderDTO.getMessage());
			dto.setCreateBy("sys");
			dto.setLastEditBy("sys");
			dto.setUserId(orderDTO.getUserId());
			dto.setOrderType(2);// b2c
			dto.setOrderId(orderDTO.getId());
			dto.setStatus((short) 2);
			int qty = 0;
			BigDecimal pirce = new BigDecimal(0);
			List<ShipOrderItemDTO> itemList = new ArrayList<ShipOrderItemDTO>();
			for (OrderItemDTO tmp : orderItemDTOs) {
				ShipOrderItemDTO item = new ShipOrderItemDTO();
				item.setPid(tmp.getPid());
				item.setSkuId(tmp.getSkuId());
				item.setpName(tmp.getpName());
				item.setSkuName(tmp.getSkuNameCn());
				item.setQty(tmp.getSkuQty());
				item.setPirce(tmp.getPrice());
				itemList.add(item);
				qty += tmp.getSkuQty();
				pirce = pirce.add(tmp.getPrice().multiply(new BigDecimal(item.getQty())));
			}
			dto.setPrice(pirce);
			dto.setQty(qty);
			dto.setShipItemList(itemList);

			ShipOrderB2CService shipOrderB2CService = RemoteServiceSingleton.getInstance().getShipOrderB2CService();
			shipOrderB2CService.insertShipOrderpop(dto);

			// 保存物流信息到订单表
			// customerOrderService.modifylogisticsInfoByOrderId(orderId,
			// logisticsNumber, logisticsCarriersId,
			// logisticsCarriersName);

			// 修改订单状态，扣减库存
			// customerOrderService.customerOrderDelivery(orderId, "sys");

		} catch (Exception e) {
			flag = false;
			LOGGER.error("customerOrderService.updateOrderLogisticsById()通过订单号修改物流信息 is failed!!!!" + e.getMessage(),
					e);
		}
		if (flag) {
			result = "1";
		} else {
			result = "订单物流信息录入失败!";
		}
		LOGGER.info("end<> to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		return result;
	}

	@RequestMapping(value = "/getLogisticPage", method = RequestMethod.GET)
	public String getLogisticPage() {
		return getLanguage() + "/logistic/logistic";
	}

	@RequestMapping(value = "/getLogisticModel", method = RequestMethod.POST)
	public String getLogisticModel(Model model, PageBean<SupplierLogisticTpl> pageBean) {
		SupplierLogisticService logisticService = RemoteServiceSingleton.getInstance().getSupplierLogisticService();
		SupplierLogisticTpl tpl = new SupplierLogisticTpl();
		tpl.setSupplierId(getCurrentSupplierId());
		pageBean.setSortFields("id");
		pageBean.setOrder("desc");
		pageBean.setParameter(tpl);
		List<SupplierLogisticTpl> tpl2 = logisticService.findAllLogisticTpl(pageBean);
		PageBean<SupplierLogisticTpl> pageList = new PageBean<SupplierLogisticTpl>();
		pageList.setResult(tpl2);
		model.addAttribute("pb", pageList);
		return getLanguage() + "/logistic/logisticModel";
	}

	@RequestMapping(value = "/getLogisticPageTpl", method = RequestMethod.GET)
	public ModelAndView getLogisticPageTpl(HttpServletRequest request, HttpServletResponse response,
			SuplierAreaTpl tpl) {
		Map<String, Object> map = new HashMap<String, Object>();
		// ArrayList list = new ArrayList();
		List<AgentProvince> allProvices = null;
		List<AgentCity> allCity = null;
		List<AgentCounty> allCounty = null;
		List<SuplierAreaTpl> list = null;
		map.put("editable", false);
		try {
			list = RemoteServiceSingleton.getInstance().getSupplierRoleManagerService()
					.selectMouldsBySupplierIds(getSupplierIds());

		} catch (Exception e) {
			e.getMessage();
		}

		try {
			allProvices = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllProvices();
			allCity = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllCity();
			allCounty = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllCounty();
			/*
			 * list.add(allProvices); list.add(allCity); list.add(allCounty);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Integer> limitCountyStrList = new ArrayList<Integer>();
		List<Integer> limitCityStrList = new ArrayList<Integer>();
		List<Integer> limitProvinceStrList = new ArrayList<Integer>();
		if (null != tpl.getId()) {
			List<SuplierAreaRegion> limitCounty = RemoteServiceSingleton.getInstance().getSupplierRoleManagerService()
					.findAgentCountyByMould(tpl.getId());

			if (null != limitCounty && limitCounty.size() > 0) {
				for (SuplierAreaRegion area : limitCounty) {
					limitCountyStrList.add(area.getCountyId());
					limitCityStrList.add(area.getCityId());
					limitProvinceStrList.add(area.getProvinceId());
				}
			}
			map.put("roleId", tpl.getId());
		}
		if (allProvices != null && !allProvices.isEmpty()) {
			List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
			/*
			 * Map<String, Object> map0 = new LinkedHashMap<String, Object>();
			 * map0.put("id", 133); map0.put("name", "全国"); map0.put("pId",
			 * "root"); map0.put("squeuces", 0); datalist.add(map0);
			 */
			Map<String, Object> map2 = null;
			Map<String, Object> map3 = null;
			Map<String, Object> map4 = null;
			for (int i = 0; i < allProvices.size(); i++) {
				map2 = new LinkedHashMap<String, Object>();
				map2.put("id", allProvices.get(i).getProvinceid());
				map2.put("name",
						null == allProvices.get(i).getProvincename() ? "" : allProvices.get(i).getProvincename());
				map2.put("pId", "root");
				map2.put("squeuces", i);
				/*
				 * if(limitCountyStrList.size()>0){ map2.put("checked", true); }
				 */
				if (limitProvinceStrList.contains(allProvices.get(i).getProvinceid())) {
					map2.put("checked", true);
				}
				datalist.add(map2);
			}
	/*		for (int i = 0; i < allCity.size(); i++) {
				map3 = new LinkedHashMap<String, Object>();
				map3.put("id", allCity.get(i).getCityid() + 1000);
				map3.put("name", null == allCity.get(i).getCityname() ? "" : allCity.get(i).getCityname());
				map3.put("pId", allCity.get(i).getProvinceid());
				map3.put("squeuces", i + allProvices.size());
				if (limitCityStrList.contains(allCity.get(i).getCityid())) {
					map3.put("checked", true);
				}
				datalist.add(map3);
			}

			for (int i = 0; i < allCounty.size(); i++) {
				map4 = new LinkedHashMap<String, Object>();
				map4.put("id", allCounty.get(i).getCountyid() + 10000);
				map4.put("name", null == allCounty.get(i).getCountyname() ? "" : allCounty.get(i).getCountyname());
				map4.put("pId", allCounty.get(i).getCityid() + 1000);
				map4.put("squeuces", i + allProvices.size() + allCity.size());
				if (limitCountyStrList.contains(allCounty.get(i).getCountyid())) {
					map4.put("checked", true);
				}
				datalist.add(map4);
			}*/
			map.put("tree", JSON.toJSONString(datalist));
			/* System.out.println(JSON.toJSONString(datalist)); */
			map.put("roleList", list);
		}

		return new ModelAndView(getLanguage() + "/logistic/logisticPageTpl-new", map);

	}

	@RequestMapping(value = "/addLogisTpl", method = RequestMethod.POST)
	@ResponseBody
	public String addLogisTpl(SupplierLogisticTpl tpl, int[] startNum, BigDecimal[] startPrice, int[] addNum,
			BigDecimal[] addPrice, LogisticTplWay way, Long[] menus, Long[] menus2, Long[] menus3,LogisticTplWaynonefee nofee) {
		// System.out.println(wayfee.toString());
		LogisticTplWayfee fee = new LogisticTplWayfee();
		SupplierUser user = getCurrentUser();
		tpl.setCreateBy(user.getLoginName());
		tpl.setSupplierId(user.getSupplierId());
		tpl.setCreateTime(new Date());
		tpl.setLogisticTplType(1);
		tpl.setState(1);
		SupplierLogisticService logisticService = RemoteServiceSingleton.getInstance().getSupplierLogisticService();
		BaseDataServiceRpc baseDataServiceRpc = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc();
		int i = logisticService.saveSupplierLogisticTpl(tpl);
		Long tplid = logisticService.findLogisticTplByName(tpl.getLogisticTplName()).getId();
		way.setLogisticTplId(tplid);
		way.setCreateDatetime(new Date());
		way.setState(1);
		int b = 0;
		int y = 0;
		int p = 0;
		if (way.getLogisticWay() == 1) {
			way.setLogisticWayName("快递");
			y = logisticService.saveLogisticTplWay(way);
			for (int a = 0; a < startNum.length - 4; a++) {
				fee.setStartNum(startNum[a]);
				fee.setStartPrice(startPrice[a]);
				fee.setAddNum(addNum[a]);
				fee.setAddPrice(addPrice[a]);
				fee.setLogisticWayId(logisticService.findLogisticTplWayById(tplid).getId());
				fee.setCreateDatetime(new Date());
				if (a == 0) {
					fee.setType(1);
				} else {
					fee.setType(2);
				}
				b = logisticService.saveLogisticTplWayfee(fee);
				if (a != 0) {
					if (menus != null && menus.length > 0) {
						for (int n = 0; n < menus.length; n++) {
							if (menus[n] > 10000 && menus[n] < 14000) {
								long cid = 0;
								long pid = 0;
								try {
									cid = baseDataServiceRpc.getCountyById(menus[n].intValue() - 10000).getCityid();
									pid = baseDataServiceRpc.getCountyById(menus[n].intValue() - 10000).getProvinceid();
								} catch (Exception e) {
									e.printStackTrace();
								}
								LogisticTplWayfeeAddress address = new LogisticTplWayfeeAddress();
								address.setLogisticProvinceid(pid);
								address.setLogisticCityid(cid);
								address.setLogisticAreaId(menus[n] - 10000);
								address.setCreateDatetime(new Date());
								address.setState(1);
								List<LogisticTplWayfee> wayfee = logisticService.findLogisticTplWayfeeByLogisticWayId(
										logisticService.findLogisticTplWayById(tplid).getId());
								address.setLogisticTplWayfeeId(wayfee.get(wayfee.size() - 1).getId());
								p = logisticService.saveLogisticTplWayfeeAddress(address);

							}
						}
					}
					if (menus2 != null && menus2.length > 0) {
						for (int m = 0; m < menus2.length; m++) {
							if (menus2[m] > 10000 && menus2[m] < 14000) {
								long cid = 0;
								long pid = 0;
								try {
									cid = baseDataServiceRpc.getCountyById(menus2[m].intValue() - 10000).getCityid();
									pid = baseDataServiceRpc.getCountyById(menus2[m].intValue() - 10000)
											.getProvinceid();
								} catch (Exception e) {
									e.printStackTrace();
								}
								LogisticTplWayfeeAddress address = new LogisticTplWayfeeAddress();
								address.setLogisticProvinceid(pid);
								address.setLogisticCityid(cid);
								address.setLogisticAreaId(menus2[m] - 10000);
								address.setCreateDatetime(new Date());
								address.setState(1);
								List<LogisticTplWayfee> wayfee = logisticService.findLogisticTplWayfeeByLogisticWayId(
										logisticService.findLogisticTplWayById(tplid).getId());
								address.setLogisticTplWayfeeId(wayfee.get(wayfee.size() - 1).getId());
								p = logisticService.saveLogisticTplWayfeeAddress(address);

							}
						}
					}
					if (menus3 != null && menus3.length > 0) {
						for (int m = 0; m < menus3.length; m++) {
							if (menus3[m] > 10000 && menus3[m] < 14000) {
								long cid = 0;
								long pid = 0;
								try {
									cid = baseDataServiceRpc.getCountyById(menus3[m].intValue() - 10000).getCityid();
									pid = baseDataServiceRpc.getCountyById(menus3[m].intValue() - 10000)
											.getProvinceid();
								} catch (Exception e) {
									e.printStackTrace();
								}
								LogisticTplWayfeeAddress address = new LogisticTplWayfeeAddress();
								address.setLogisticProvinceid(pid);
								address.setLogisticCityid(cid);
								address.setLogisticAreaId(menus3[m] - 10000);
								address.setCreateDatetime(new Date());
								address.setState(1);
								List<LogisticTplWayfee> wayfee = logisticService.findLogisticTplWayfeeByLogisticWayId(
										logisticService.findLogisticTplWayById(tplid).getId());
								address.setLogisticTplWayfeeId(wayfee.get(wayfee.size() - 1).getId());
								p = logisticService.saveLogisticTplWayfeeAddress(address);

							}
						}
					}

				}
			}
		} else if (way.getLogisticWay() == 2) {
			way.setLogisticWayName("EMS");
			y = logisticService.saveLogisticTplWay(way);
			for (int a = 0; a < startNum.length - 4; a++) {
				fee.setStartNum(startNum[a + 2]);
				fee.setStartPrice(startPrice[a + 2]);
				fee.setAddNum(addNum[a + 2]);
				fee.setAddPrice(addPrice[a + 2]);
				fee.setLogisticWayId(logisticService.findLogisticTplWayById(tplid).getId());
				fee.setCreateDatetime(new Date());
				if (a == 0) {
					fee.setType(1);
				} else {
					fee.setType(2);
				}
				b = logisticService.saveLogisticTplWayfee(fee);
				if (a != 0) {
					if (menus != null && menus.length > 0) {
						for (int n = 0; n < menus.length; n++) {
							if (menus[n] > 10000 && menus[n] < 14000) {
								long cid = 0;
								long pid = 0;
								try {
									cid = baseDataServiceRpc.getCountyById(menus[n].intValue() - 10000).getCityid();
									pid = baseDataServiceRpc.getCountyById(menus[n].intValue() - 10000).getProvinceid();
								} catch (Exception e) {
									e.printStackTrace();
								}
								LogisticTplWayfeeAddress address = new LogisticTplWayfeeAddress();
								address.setLogisticProvinceid(pid);
								address.setLogisticCityid(cid);
								address.setLogisticAreaId(menus[n] - 10000);
								address.setCreateDatetime(new Date());
								address.setState(1);
								List<LogisticTplWayfee> wayfee = logisticService.findLogisticTplWayfeeByLogisticWayId(
										logisticService.findLogisticTplWayById(tplid).getId());
								address.setLogisticTplWayfeeId(wayfee.get(wayfee.size() - 1).getId());
								p = logisticService.saveLogisticTplWayfeeAddress(address);

							}
						}
					}
					if (menus2 != null && menus2.length > 0) {
						for (int m = 0; m < menus2.length; m++) {
							if (menus2[m] > 10000 && menus2[m] < 14000) {
								long cid = 0;
								long pid = 0;
								try {
									cid = baseDataServiceRpc.getCountyById(menus2[m].intValue() - 10000).getCityid();
									pid = baseDataServiceRpc.getCountyById(menus2[m].intValue() - 10000)
											.getProvinceid();
								} catch (Exception e) {
									e.printStackTrace();
								}
								LogisticTplWayfeeAddress address = new LogisticTplWayfeeAddress();
								address.setLogisticProvinceid(pid);
								address.setLogisticCityid(cid);
								address.setLogisticAreaId(menus2[m] - 10000);
								address.setCreateDatetime(new Date());
								address.setState(1);
								List<LogisticTplWayfee> wayfee = logisticService.findLogisticTplWayfeeByLogisticWayId(
										logisticService.findLogisticTplWayById(tplid).getId());
								address.setLogisticTplWayfeeId(wayfee.get(wayfee.size() - 1).getId());
								p = logisticService.saveLogisticTplWayfeeAddress(address);

							}
						}
					}
					if (menus3 != null && menus3.length > 0) {
						for (int m = 0; m < menus3.length; m++) {
							if (menus3[m] > 10000 && menus3[m] < 14000) {
								long cid = 0;
								long pid = 0;
								try {
									cid = baseDataServiceRpc.getCountyById(menus3[m].intValue() - 10000).getCityid();
									pid = baseDataServiceRpc.getCountyById(menus3[m].intValue() - 10000)
											.getProvinceid();
								} catch (Exception e) {
									e.printStackTrace();
								}
								LogisticTplWayfeeAddress address = new LogisticTplWayfeeAddress();
								address.setLogisticProvinceid(pid);
								address.setLogisticCityid(cid);
								address.setLogisticAreaId(menus3[m] - 10000);
								address.setCreateDatetime(new Date());
								address.setState(1);
								List<LogisticTplWayfee> wayfee = logisticService.findLogisticTplWayfeeByLogisticWayId(
										logisticService.findLogisticTplWayById(tplid).getId());
								address.setLogisticTplWayfeeId(wayfee.get(wayfee.size() - 1).getId());
								p = logisticService.saveLogisticTplWayfeeAddress(address);

							}
						}
					}

				}
			}
		} else {
			way.setLogisticWayName("平邮");
			y = logisticService.saveLogisticTplWay(way);
			for (int a = 0; a < startNum.length - 4; a++) {
				fee.setStartNum(startNum[a + 4]);
				fee.setStartPrice(startPrice[a + 4]);
				fee.setAddNum(addNum[a + 4]);
				fee.setAddPrice(addPrice[a + 4]);
				fee.setLogisticWayId(logisticService.findLogisticTplWayById(tplid).getId());
				fee.setCreateDatetime(new Date());
				if (a == 0) {
					fee.setType(1);
				} else {
					fee.setType(2);
				}
				b = logisticService.saveLogisticTplWayfee(fee);
				if (a != 0) {
					if (menus != null && menus.length > 0) {
						for (int n = 0; n < menus.length; n++) {
							if (menus[n] > 10000 && menus[n] < 14000) {
								long cid = 0;
								long pid = 0;
								try {
									cid = baseDataServiceRpc.getCountyById(menus[n].intValue() - 10000).getCityid();
									pid = baseDataServiceRpc.getCountyById(menus[n].intValue() - 10000).getProvinceid();
								} catch (Exception e) {
									e.printStackTrace();
								}
								LogisticTplWayfeeAddress address = new LogisticTplWayfeeAddress();
								address.setLogisticProvinceid(pid);
								address.setLogisticCityid(cid);
								address.setLogisticAreaId(menus[n] - 10000);
								address.setCreateDatetime(new Date());
								address.setState(1);
								List<LogisticTplWayfee> wayfee = logisticService.findLogisticTplWayfeeByLogisticWayId(
										logisticService.findLogisticTplWayById(tplid).getId());
								address.setLogisticTplWayfeeId(wayfee.get(wayfee.size() - 1).getId());
								p = logisticService.saveLogisticTplWayfeeAddress(address);

							}
						}
					}
					if (menus2 != null && menus2.length > 0) {
						for (int m = 0; m < menus2.length; m++) {
							if (menus2[m] > 10000 && menus2[m] < 14000) {
								long cid = 0;
								long pid = 0;
								try {
									cid = baseDataServiceRpc.getCountyById(menus2[m].intValue() - 10000).getCityid();
									pid = baseDataServiceRpc.getCountyById(menus2[m].intValue() - 10000)
											.getProvinceid();
								} catch (Exception e) {
									e.printStackTrace();
								}
								LogisticTplWayfeeAddress address = new LogisticTplWayfeeAddress();
								address.setLogisticProvinceid(pid);
								address.setLogisticCityid(cid);
								address.setLogisticAreaId(menus2[m] - 10000);
								address.setCreateDatetime(new Date());
								address.setState(1);
								List<LogisticTplWayfee> wayfee = logisticService.findLogisticTplWayfeeByLogisticWayId(
										logisticService.findLogisticTplWayById(tplid).getId());
								address.setLogisticTplWayfeeId(wayfee.get(wayfee.size() - 1).getId());
								p = logisticService.saveLogisticTplWayfeeAddress(address);
  
							}
						}
					}
					if (menus3 != null && menus3.length > 0) {
						for (int m = 0; m < menus3.length; m++) {
							if (menus3[m] > 10000 && menus3[m] < 14000) {
								long cid = 0;
								long pid = 0;
								try {
									cid = baseDataServiceRpc.getCountyById(menus3[m].intValue() - 10000).getCityid();
									pid = baseDataServiceRpc.getCountyById(menus3[m].intValue() - 10000)
											.getProvinceid();
								} catch (Exception e) {
									e.printStackTrace();
								}
								LogisticTplWayfeeAddress address = new LogisticTplWayfeeAddress();
								address.setLogisticProvinceid(pid);
								address.setLogisticCityid(cid);
								address.setLogisticAreaId(menus3[m] - 10000);
								address.setCreateDatetime(new Date());
								address.setState(1);
								List<LogisticTplWayfee> wayfee = logisticService.findLogisticTplWayfeeByLogisticWayId(
										logisticService.findLogisticTplWayById(tplid).getId());
								address.setLogisticTplWayfeeId(wayfee.get(wayfee.size() - 1).getId());
								p = logisticService.saveLogisticTplWayfeeAddress(address);

							}
						}
					}

				}
			}
		}

		if (i > 0 && y > 0 && b > 0 && p > 0) {
			return JSON.toJSONString(1);
		} else {
			return JSON.toJSONString(0);
		}
	}
	/**
	 * 添加通用模板
	 */
	@Autowired
	private GeneralLogisTplService generalLogisTplService;
	@RequestMapping(value="/addGeneralLogisTpl",method=RequestMethod.POST)
	public ModelAndView addGeneralLogisTpl(ModelAndView modelAndView,SGeneralLogisticTpl sGeneralLogisticTpl){
		Long supplierId = getCurrentSupplierId();
		if (!supplierId.equals(null) && !supplierId.equals("")) {
			sGeneralLogisticTpl.setTplType(2);
		}else {
			sGeneralLogisticTpl.setTplType(1);
		}
		String nonefeeProvinceId = sGeneralLogisticTpl.getNonefeeProvinceId();
		if (nonefeeProvinceId!=null&&nonefeeProvinceId!="") {
			String[] nonefeeProvinceIds = nonefeeProvinceId.split(",,");
			 for (int i = 0; i < nonefeeProvinceIds.length; i++) {
				 nonefeeProvinceId = nonefeeProvinceIds[nonefeeProvinceIds.length-1];
			}
			 sGeneralLogisticTpl.setNonefeeProvinceId(nonefeeProvinceId);
		}
		sGeneralLogisticTpl.setStatus(1);
		sGeneralLogisticTpl.setSupplierId(supplierId);
		generalLogisTplService.addGeneralLogisTpl(sGeneralLogisticTpl);
		return new ModelAndView("redirect:/order/getGeneralLogisPage");
	}
	@RequestMapping(value = "/getGeneralLogisPage", method = RequestMethod.GET)
	public String getGeneralLogisPage() {
		return getLanguage() + "/logistic/logistic";
	}
	/**
	 * 查询通用模板
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/findAllGeneralLogisTpl",method=RequestMethod.POST)
	public String findAllGeneralLogisTpl(Model model,Integer page,Integer pageSize){
		SGeneralLogisticTpl tpl = new SGeneralLogisticTpl();
		tpl.setStatus(1);
		tpl.setSupplierId(getCurrentSupplierId());
        if (page == null) {
        	page = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		PageBean<SGeneralLogisticTpl> findAllGeneralLogisTpl = generalLogisTplService.findAllGeneralLogisTpl(tpl, page, pageSize);
		model.addAttribute("pb",findAllGeneralLogisTpl);
		return getLanguage() + "/logistic/logisticModel";
	}
	/**
	 * 删除模板
	 * @param modelAndView
	 * @param logisticTempId
	 * @return 
	 */
	@RequestMapping(value="/deleteGeneralLogisTpl",method=RequestMethod.POST)
	@ResponseBody
	public void deleteGeneralLogisTpl(Model model,Long logisticTempId,Long supplierId){
		Long currentSupplierId = getCurrentSupplierId();
		int msg;
		if (supplierId.equals(currentSupplierId) && !currentSupplierId.equals(null) && !currentSupplierId.equals("")) {
			generalLogisTplService.deleteGeneralLogisTpl(logisticTempId);
			msg = 1;
		}else{
			msg = 0;
		}
		JSON.toJSON(msg);
		//return new ModelAndView("redirect:/order/getGeneralLogisPage");
	}
	/**
	 * 跳转物流模板修改界面 回显数据
	 * @param logisticTempId
	 * @return 
	 */
	@RequestMapping(value="/toEditGeneralLogisTplUI",method=RequestMethod.GET)
	public ModelAndView toEditUI(Model model,Long logisticTempId){
		SGeneralLogisticTpl generalLogisticTpl = generalLogisTplService.findGeneralLogisticTplById(logisticTempId);
		model.addAttribute("generalLogisticTpl", generalLogisticTpl);
		String nonefeeProvinceId = generalLogisticTpl.getNonefeeProvinceId();
		String[] split = null;
		if (nonefeeProvinceId != null && nonefeeProvinceId != "") {
			split = nonefeeProvinceId.split(",");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// ArrayList list = new ArrayList();
		List<AgentProvince> allProvices = null;
		List<AgentCity> allCity = null;
		List<AgentCounty> allCounty = null;
		List<SuplierAreaTpl> list = null;
		map.put("editable", false);

		try {
			allProvices = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllProvices();
			/*
			 * list.add(allProvices); list.add(allCity); list.add(allCounty);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Integer> limitProvinceStrList = new ArrayList<Integer>();
		if (nonefeeProvinceId != null && nonefeeProvinceId != "") {
			
			for (int i = 0; i < split.length; i++) {
				limitProvinceStrList.add(Integer.valueOf(split[i]));
			}
		}
		if (allProvices != null && !allProvices.isEmpty()) {
			List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
			/*
			 * Map<String, Object> map0 = new LinkedHashMap<String, Object>();
			 * map0.put("id", 133); map0.put("name", "全国"); map0.put("pId",
			 * "root"); map0.put("squeuces", 0); datalist.add(map0);
			 */
			Map<String, Object> map2 = null;
			Map<String, Object> map3 = null;
			Map<String, Object> map4 = null;
			for (int i = 0; i < allProvices.size(); i++) {
				map2 = new LinkedHashMap<String, Object>();
				map2.put("id", allProvices.get(i).getProvinceid());
				map2.put("name",
						null == allProvices.get(i).getProvincename() ? "" : allProvices.get(i).getProvincename());
				map2.put("pId", "root");
				map2.put("squeuces", i);
				/*
				 * if(limitCountyStrList.size()>0){ map2.put("checked", true); }
				 */
				if (limitProvinceStrList.contains(allProvices.get(i).getProvinceid())) {
					map2.put("checked", true);
				}
				datalist.add(map2);
			}
			map.put("tree", JSON.toJSONString(datalist));
			/* System.out.println(JSON.toJSONString(datalist)); */
			map.put("roleList", list);
		}

		return new ModelAndView(getLanguage() + "/logistic/updatelogisticPageTpl-new", map);

	
		
	}
	/**
	 * 修改物流模板
	 * @param modelAndView
	 * @param sGeneralLogisticTpl
	 * @return
	 */
	@RequestMapping(value="/updateGeneralLogisTpl",method=RequestMethod.POST)
	public ModelAndView deleteGeneralLogisTpl(ModelAndView modelAndView,SGeneralLogisticTpl sGeneralLogisticTpl){
		Long supplierId = getCurrentSupplierId();
		sGeneralLogisticTpl.setSupplierId(supplierId);
		generalLogisTplService.updateGeneralLogisTpl(sGeneralLogisticTpl);
		return new ModelAndView("redirect:/order/getGeneralLogisPage");
	}
	/**
	 * 查询物流模板--ajax
	 */
	/*@RequestMapping(value="/findGeneralLogisTplByAjax",method=RequestMethod.POST)
	@ResponseBody
	public String findGeneralLogisTplByAjax(){
		Long supplierId = getCurrentSupplierId();
		List<SGeneralLogisticTpl> glt= generalLogisTplService.findGeneralLogisTplByAjax(supplierId);
		return	JSONArray.toJSONString(glt);
		
	}*/
}
