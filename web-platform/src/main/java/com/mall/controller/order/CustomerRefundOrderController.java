package com.mall.controller.order;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.B2cShipOrderDTO;
import com.mall.customer.order.dto.B2cShipOrderItemDTO;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderItemDTO;
import com.mall.customer.order.dto.ProductDTO;
import com.mall.customer.service.UserService;
import com.mall.mybatis.utility.PageBean;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;

/**.
 * @Title CustomerRefundOrderController
 * @Description: 海外直邮订单海关审核失败订单.
 * @author Guofy
 * @date 2015年6月23日17:24:24
 */
@Controller
@RequestMapping(value = "/customerRefundOrder")
public class CustomerRefundOrderController extends BaseController {

	/**.
	 * LOGGER日志打印.
	 */
	private static final Log LOGGER = LogFactory.getLogger(CustomerRefundOrderController.class);
	
	/**.
	 * getCustomerRefundOrderList
	 * @return String
	 */
	@RequestMapping(value = "/getCustomerRefundOrderList",method = RequestMethod.GET)
	public String getCustomerRefundOrderList(){
		LOGGER.info("start to execute method <getCustomerOrderList>!!!!");
		
		LOGGER.info("end<> to execute method <getCustomerOrderList>!!!!");
		return "/dealerseller/B2Corder/B2Corder_refund_list";
	}
	
	
	/**.
	 * @Title getCustomerRefundOrderPageBeanByCondition
	 * @Description: 等待退款的海外直邮订单条件查询列表.
	 * @author Guofy
	 * @param page 
	 * @param startTime
	 * @param endTime
	 * @param orderId
	 * @param model
	 * @param receiveName
	 * @param userName
	 * @param pName
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/getCustomerRefundOrderPageBeanByCondition")
	public String getCustomerRefundOrderPageBeanByCondition(Integer page,String startTime,String endTime,Long orderId,Model model,
			String receiveName,String userName,String pName,Short status){
		LOGGER.info("start to execute method <getCustomerRefundOrderPageBeanByCondition>!!!!");
		LOGGER.info("订单编号orderId:"+orderId);
		LOGGER.info("当前是第"+page+"页!");
		LOGGER.info("订单状态码status:"+status);
		LOGGER.info("商品名称:"+pName);
		LOGGER.info("起始时间:"+startTime);
		LOGGER.info("结束时间:"+endTime);
		LOGGER.info("收货人receiveName:"+receiveName);
		LOGGER.info("用户名userName:"+userName);
		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		OrderDTO orderDTO = new OrderDTO();
		if(!Common.isEmpty(pName)){
			orderDTO.setpName(pName);
		}
		if(null != orderId){
			orderDTO.setId(orderId);
			//orderDTO.getSupplyType()  1.国内   11.保税区  12.
		}
		if(!Common.isEmpty(startTime)){
			orderDTO.setStartDate(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			orderDTO.setEndDate(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(receiveName)){
			orderDTO.setReceiveName(receiveName);
		}
		if(!Common.isEmpty(userName)){
			List<Long> userIds = new ArrayList<Long>();
			try {
				UserService userService = RemoteServiceSingleton.getInstance().getUserService();
				userIds = userService.findUserIdsByUserName(userName);
				if(userIds.size()<1){
					userIds.add(0l);
				}
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("UserService<findUserIdsByUserName()根据用户用户名模糊查询ID的集合> is failed!!!!"+e.getMessage(),e);
			}
			orderDTO.setUserIds(userIds);
		}
		if(null != status){
			orderDTO.setStatus(status);
		}
		if(page!=null&&page!=0){  //判断条件中是否带着分页码
			pageBean.setPage(page);
		}else {
			pageBean.setPage(1);//没有分页码的话就检索第一页
		}
		pageBean.setParameter(orderDTO);
		pageBean.setPageSize(Constants.PAGESIZE);
		try{
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			pageBean = customerOrderService.findWaitRefundOrders(pageBean);
			if(null != pageBean){
				List<OrderDTO> orderList = pageBean.getResult();
				if(null != orderList && orderList.size()>0){
					for (OrderDTO orderDTO2 : orderList) {
						List<ProductDTO> productList = orderDTO2.getProductList();
						if(null != productList && productList.size()>0){
							for (ProductDTO productDTO : productList) {
								String productImgUrl = productDTO.getImgUrl();
								if(!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")){
									productImgUrl = Constants.P1 + productImgUrl;
									productDTO.setImgUrl(productImgUrl);
								}
							}
						}
					}
				}	
			}
		}catch (Exception e) {
			LOGGER.error("CustomerOrderService<findWaitRefundOrders()获取海关审核失败的订单> is failed!!!!"+e.getMessage(),e);
		}
		model.addAttribute("pageBean", pageBean);
		LOGGER.info("end<> to execute method <getCustomerRefundOrderPageBeanByCondition>!!!!");
		return "/dealerseller/B2Corder/B2Corder_refund_list_model";
	}	
	
	
	
	/**
	 * @Description: 等待退款的海外直邮订单条件查询列表.
	 * @Title getCustomerRefundOrderDetailsById
	 * @author Guofy
	 * @param  Long orderId
	 * @param  Model model
	 * @return String
	 */
	@RequestMapping(value = "/getCustomerRefundOrderDetailsById")
	public String getCustomerRefundOrderDetailsById(Long orderId,Model model){
		LOGGER.info("start to execute method <getCustomerRefundOrderDetailsById>!!!!");
		LOGGER.info("订单号orderId:"+orderId);
		OrderDTO orderDTO = new OrderDTO();
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			orderDTO = customerOrderService.findCustomerOrderInfoByOrderId(orderId);
			if(null != orderDTO){
				List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
				if(null != orderItemDTOs && orderItemDTOs.size()>0){
					for (OrderItemDTO orderItemDTO : orderItemDTOs) {
						String skuImageUrl = orderItemDTO.getImgUrl();
						if(!Common.isEmpty(skuImageUrl)){
							if(!skuImageUrl.startsWith("http") || !skuImageUrl.startsWith("Http")){
								skuImageUrl=Constants.P1+skuImageUrl;
								orderItemDTO.setImgUrl(skuImageUrl);
							}
						}	
					}
				}
				if(orderDTO.getSupplyType().equals("1")){
					List<B2cShipOrderDTO> shipOrderDTOs = orderDTO.getShipOrderDTOs();
					if(null != shipOrderDTOs && shipOrderDTOs.size()>0){
						for (B2cShipOrderDTO b2cShipOrderDTO : shipOrderDTOs) {
							if(null != b2cShipOrderDTO){
								List<B2cShipOrderItemDTO> shipOrderItems = b2cShipOrderDTO.getShipItemDtoList();
								if(null != shipOrderItems && shipOrderItems.size()>0){
									for (B2cShipOrderItemDTO b2cShipOrderItemDTO : shipOrderItems) {
										String imageUrl = b2cShipOrderItemDTO.getProductUrl();
										if(!Common.isEmpty(imageUrl)){
											imageUrl = Constants.P1+imageUrl;
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
			LOGGER.error("CustomerOrderService<findCustomerOrderInfoByOrderId()> 根据订单id获取订单详情 is failed!!!!"+e.getMessage(),e);
		}
		if(null != orderDTO){
			if(null != orderDTO.getUserId()){
				try {
					LOGGER.info("此订单用户ID:"+orderDTO.getUserId());
					UserService userService = RemoteServiceSingleton.getInstance().getUserService();
					User user = userService.findUserById(orderDTO.getUserId());	
					model.addAttribute("user", user);
				} catch (Exception e) {
					// TODO: handle exception
					LOGGER.error("UserService<findUserById()根据用户ID查询该用户的信息> is failed!!!!"+e.getMessage(),e);
				}	
			}
		}
		model.addAttribute("orderDTO", orderDTO);
		LOGGER.info("end<> to execute method <getCustomerRefundOrderDetailsById>!!!!");
		return "/dealerseller/B2Corder/B2Corder_refund_details";	
	}
	
	
	/**
	 * @Description: 订单退款操作.
	 * @Title goRefundOrderByOrderId
	 * @author Guofy
	 * @param Long orderId
	 * @return String
	 */
	@RequestMapping(value = "/goRefundOrderByOrderId")
	@ResponseBody
	public String goRefundOrderByOrderId(Long orderId){
		LOGGER.info("start to execute method <goRefundOrderByOrderId订单退款操作>!!!!");
		LOGGER.info("订单ID:"+orderId);
		String userName = getCurrentUser().getUsername();
		boolean flag = false;
		String result = "";
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			flag = customerOrderService.customerOrderRefund(orderId,userName);
			if(flag){
				result = "订单正在退款成功!";
			}else{
				result = "订单退款操作失败!";
			}
		} catch (Exception e) {
			// TODO: handle exception
			result = "订单退款操作失败!";
			LOGGER.error("customerOrderService<customerOrderRefund()根据订单ID对此订单退款!> is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <goRefundOrderByOrderId订单退款操作>!!!!");
		return result;
	}	
}
