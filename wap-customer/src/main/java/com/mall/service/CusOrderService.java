package com.mall.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.mall.constant.CommonConstant;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.po.OrderLogisticsMsg;
import com.mall.customer.service.SqwAccountRecordService;
import com.mall.exception.BusinessException;
import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.api.rpc.RetailerShipOrderService;
import com.mall.utils.CookieUtil;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * 订单的服务
 * 
 * @author ccigQA01
 * 
 */
@Service
public class CusOrderService {
	private static final Logger log = LoggerFactory.getLogger(CusOrderService.class);

	@Autowired
	private MemcachedClient memcachedClient;
	
	/**
	 * 订单服务
	 */
	private CustomerOrderService customerOrderService;
	
	/**
	 * 订单物流服务
	 */
	private RetailerShipOrderService retailerShipOrderService;
	
	@Autowired
	private SqwAccountRecordService sqwAccountRecordService;

	@Autowired
	private CusAddrService cusAddrService;

	/**
	 * 查询订单
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	public PageBean<OrderDTO> findCustomerOrderByCustomer(Short status, Integer pageNow, Model model,
			HttpServletRequest request, HttpServletResponse response, User user) {

		log.info("findCustomerOrderByCustomer execute....");

		customerOrderService = getCustomerOrderService();

		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		if (pageNow == null || pageNow == 0) {
			pageNow = 1;
			log.info("pageNow is null ");
		}
		pageBean.setPage(pageNow);
		pageBean.setPageSize(CommonConstant.PAGE_SIZE);

		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUserId(user.getUserId());

		if (StringUtils.isEmpty(status)) {
			orderDTO.setStatus(null);
		} else {
			List<Short> statusList = new ArrayList<Short>();
			switch (status) {
			// 待收货
			case 81:
				statusList.add((short) 21);
				statusList.add((short) 31);
				statusList.add((short) 41);
				statusList.add((short) 81);
				orderDTO.setStatusList(statusList);
				break;
			// 其他状态
			default:
				orderDTO.setStatus(status);
				break;
			}
		}

		String isEvaluate = request.getParameter("isEvaluate");
		if (org.apache.commons.lang3.StringUtils.isNotBlank(isEvaluate)) {
			orderDTO.setIsEvaluate(Integer.parseInt(isEvaluate));
		}
		pageBean.setParameter(orderDTO);
		pageBean = customerOrderService.findCustomerOrdersByCustomer(pageBean);

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("status", status);
		model.addAttribute("pageNow", pageNow);
		log.info("end ..findCustomerOrderByCustomer execute....");
		return pageBean;
	}
	/**
	 * 查询订单
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	public PageBean<OrderDTO> findSupplierOrderByCustomer(Short status, Integer pageNow, Model model,
			HttpServletRequest request, HttpServletResponse response, User user) {

		log.info("findCustomerOrderByCustomer execute....");

		customerOrderService = getCustomerOrderService();

		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		if (pageNow == null || pageNow == 0) {
			pageNow = 1;
			log.info("pageNow is null ");
		}
		pageBean.setPage(pageNow);
		pageBean.setPageSize(CommonConstant.PAGE_SIZE);

		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setMerchantid(user.getSupplierId());
		orderDTO.setOrderType((short)200);
		if (StringUtils.isEmpty(status)) {
			orderDTO.setStatus(null);
		} else {
				orderDTO.setStatus(status);
		}

		String isEvaluate = request.getParameter("isEvaluate");
		if (org.apache.commons.lang3.StringUtils.isNotBlank(isEvaluate)) {
			orderDTO.setIsEvaluate(Integer.parseInt(isEvaluate));
		}
		pageBean.setParameter(orderDTO);
		pageBean = customerOrderService.findCustomerOrdersByCustomer(pageBean);

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("status", status);
		model.addAttribute("pageNow", pageNow);
		log.info("end ..findCustomerOrderByCustomer execute....");
		return pageBean;
	}

	public PageBean<OrderDTO> findCustomerOrderByCustomer(Short status, Integer pageNow, Model model,
			HttpServletRequest request, HttpServletResponse response, User user, String supplyTypeNot) {

		log.info("findCustomerOrderByCustomer execute....");

		customerOrderService = getCustomerOrderService();

		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		if (pageNow == null || pageNow == 0) {
			pageNow = 1;
			log.info("pageNow is null ");
		}
		pageBean.setPage(pageNow);
		pageBean.setPageSize(CommonConstant.PAGE_SIZE);

		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setSupplyTypeNot(supplyTypeNot);
		orderDTO.setUserId(user.getUserId());

		if (StringUtils.isEmpty(status)) {
			orderDTO.setStatus(null);
		} else {
			List<Short> statusList = new ArrayList<Short>();
			switch (status) {
			// 待收货
			case 81:
				statusList.add((short) 21);
				statusList.add((short) 31);
				statusList.add((short) 41);
				statusList.add((short) 81);
				orderDTO.setStatusList(statusList);
				break;
			// 其他状态
			default:
				orderDTO.setStatus(status);
				break;
			}
		}
		// 订单来源
		String cookieValue = CookieUtil.getCookieValue(request, CommonConstant.ORDER_FORM_KEY);

		// print log
		log.info("从cookie中获取订单来源." + cookieValue);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(cookieValue)) {
			orderDTO.setOrderSource(cookieValue);
		}
		pageBean.setParameter(orderDTO);
		pageBean = customerOrderService.findCustomerOrdersByCustomer(pageBean);

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("status", status);
		model.addAttribute("pageNow", pageNow);
		log.info("end ..findCustomerOrderByCustomer execute....");
		return pageBean;
	}

	/**
	 * 获取订单服务
	 * 
	 * @return
	 */
	private CustomerOrderService getCustomerOrderService() {
		return RemoteServiceSingleton.getInstance().getCustomerOrderService();
	}

	/**
	 * 获取单个订单信息
	 * 
	 * @param orderId
	 * @param currentUser
	 * @param model
	 * @return
	 */
	public OrderDTO findOrderByOrderId(Long orderId, User currentUser) {
		// TODO Auto-generated method stub
		customerOrderService = getCustomerOrderService();
		OrderDTO findCustomerOrderInfoByOrderId = null;

		log.info("orderId:{" + orderId + "}");

		try {
			findCustomerOrderInfoByOrderId = customerOrderService.findCustomerOrderInfoByOrderId(orderId,
					currentUser.getUserId());

		} catch (Exception e) {

			log.error("call customerOrderService.findCustomerOrderInfoByOrderId error ");
			throw new BusinessException(" call  customerOrderService.findCustomerOrderInfoByOrderId error");
		}

		return findCustomerOrderInfoByOrderId;
	}

	/**
	 * 获取单个订单信息
	 * 
	 * @param orderId
	 * @param currentUser
	 * @param model
	 * @return
	 */
	public OrderDTO findOrderByOrderId(Long orderId) {
		// TODO Auto-generated method stub
		customerOrderService = getCustomerOrderService();
		OrderDTO orderDTO = null;

		log.info("orderId:{" + orderId + "}");

		try {
			orderDTO = customerOrderService.findTaokeCustomerOrderInfoByOrderId(orderId);

		} catch (Exception e) {

			log.error("call customerOrderService.findTaokeCustomerOrderInfoByOrderId error ");
			throw new BusinessException(" call  customerOrderService.findTaokeCustomerOrderInfoByOrderId error");
		}

		return orderDTO;
	}

	/**
	 * 获取物流信息
	 * 
	 * @param orderId
	 * @param user
	 */
	public List<OrderLogisticsMsg> getTraceOrderByOrderId(Long orderId, User user) {
		customerOrderService = getCustomerOrderService();
		return customerOrderService.findOrderLogisticsMsgsByOrderId(orderId, user.getUserId());
	}

	/**
	 * 取消订单
	 * 
	 * @param orderId
	 * @param currentUser
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String cancleOrderByOrderId(Long orderId, User currentUser, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		customerOrderService = getCustomerOrderService();
		log.info("orderId:{ " + orderId + " } userId:{" + currentUser.getUserId() + "  }operator:{ "
				+ currentUser.getUserName());
		boolean cancelCustomerOrderByOrderId = customerOrderService.cancelCustomerOrderByOrderId(orderId,
				currentUser.getUserId(), currentUser.getUserName());

		return cancelCustomerOrderByOrderId ? "ok" : "error";
	}

	/**
	 * 删除订单
	 * 
	 * @param currentUser
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 */
	public String delOrder(User currentUser, Long orderId, HttpServletRequest request, HttpServletResponse response) {
		customerOrderService = getCustomerOrderService();
		// customerOrderService.

		return null;
	}

	/**
	 * 确认订单
	 * 
	 * @param currentUser
	 * @param orderId
	 * @return
	 */
	public String confirmOrder(User currentUser, Long orderId, Long shipId) {

		log.info("userId:{" + currentUser.getUserId() + "},orderId:{" + orderId + "},shipId:{" + shipId + "}");

		customerOrderService = getCustomerOrderService();
		// customerOrderService.customerOrderPaySuccess(orderId, userId,
		// orderSeqNo, payMentNo, payWay, op)
		OrderDTO orderDto = findOrderByOrderId(orderId, currentUser);
		boolean customerOrderReceipt = false;

		if (orderDto == null) {
			log.error("orderDto is  null ");
			return "error";
		}

		String supplyType = orderDto.getSupplyType();
		// 国内物流 调包裹的接口
		if ("1".equals(supplyType) || "21".equals(supplyType)) {
			retailerShipOrderService = getRetailerShipOrderService();
			Short status = 3;
			try {
				retailerShipOrderService.updateRetailerShipStatus(shipId, orderDto.getPayId(), status,
						currentUser.getUserName());
			} catch (Exception e) {
				log.error("call  shipOrderB2CService.updateShipOrderStatus error", e);
				e.printStackTrace();
			}
			customerOrderReceipt = true;
		} else {
			// 其他的 11海外12保税区 调用订单的接口
			try {
				customerOrderReceipt = customerOrderService.customerOrderReceipt(orderId, currentUser.getUserId(),
						currentUser.getUserName());
			} catch (Exception e) {
				log.error("call customerOrderService.customerOrderReceipt error", e);
				e.printStackTrace();
			}
		}
		/*
		 * // FIXME jianping.gao // 订单内的全部包裹已收货，跳转到评价提示页 orderDto =
		 * findOrderByOrderId(orderId, currentUser); if
		 * (CommonConstant.ORDERT_STATUS_FINISHED == orderDto.getStatus()) {
		 * return String.valueOf(CommonConstant.ORDERT_STATUS_FINISHED); }
		 */

		// 企业返积分
		try {
			if(customerOrderReceipt){
				sqwAccountRecordService.recordeQiyeHqq(Long.parseLong(orderDto.getMerchantid()), orderId,orderDto.getPrice());
			}
		} catch (Exception e) {
			log.error("cusInfo error", e);
		}
		return customerOrderReceipt ? "ok" : "error";
	}

	/**
	 * @return 获取订单物流包裹服务
	 */
	private RetailerShipOrderService getRetailerShipOrderService() {
		return RemoteServiceSingleton.getInstance().getRetailerShipOrderService();
	}

	/**
	 * 修改订单
	 * 
	 * @param orderId
	 * @param currentUser
	 * @return
	 */
	public boolean updateOrder(Long orderId, User currentUser) {
		customerOrderService = getCustomerOrderService();

		boolean updateOrderEvaluate = false;
		try {
			updateOrderEvaluate = customerOrderService.updateOrderEvaluate(orderId, currentUser.getUserId(),
					currentUser.getUserName());
		} catch (Exception e) {
			log.error("call  customerOrderService.updateOrderEvaluate error", e);
			e.printStackTrace();
		}

		return updateOrderEvaluate;
	}

	/**
	 * 查询物流公司与物流单号
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OrderDTO> findLogisticsByOrderid(Long orderId) {
		customerOrderService = getCustomerOrderService();
		List<OrderDTO> list = null;
		log.info("orderId:{" + orderId + "}");

		try {
			list = customerOrderService.findCustomerOrderLogisticsInfoByOrderId(orderId);

		} catch (Exception e) {

			log.error("call customerOrderService.findCustomerOrderLogisticsInfoByOrderId error ");
			throw new BusinessException(" call  customerOrderService.findCustomerOrderLogisticsInfoByOrderId error");
		}

		return list;
	}
}
