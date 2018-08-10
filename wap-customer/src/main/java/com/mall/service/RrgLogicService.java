/**
 * 
 */
package com.mall.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderItemDTO;
import com.mall.customer.order.dto.ProductDTO;
import com.mall.utils.ValidateUtil;
import com.mall.vo.CommonConstants;


/**
 * @author jianping.gao
 *
 */
@Service
public class RrgLogicService {

	/**
	 * 
	 */
//	@Autowired
//	private SingleServiceRPC singleService;

	/**
	 * 给已收货的订单列表中添加退换货状态
	 * 
	 * @param pageResponse
	 */
//	public void setRrrStatus2OrderList(List<OrderDTO> orderDTOs, int flag) {
//		if (ValidateUtil.isEmpty(orderDTOs)) {
//			return;
//		}
//		Map<String, Map<String, Boolean>> map = new HashMap<String, Map<String, Boolean>>();
//		for (OrderDTO orderDTO : orderDTOs) {
//			long orderId = orderDTO.getOrderNo();
//			Map<String, Boolean> productMap = new HashMap<String, Boolean>();
//			if (flag == CommonConstants.ONE) {
//				List<ProductDTO> productList = orderDTO.getProductList();
//				if (ValidateUtil.isEmpty(productList)) {
//					continue;
//				}
//				for (ProductDTO productDTO : productList) {
//					productMap.put(String.valueOf(productDTO.getPid()), true);
//				}
//			}
//			if (flag == CommonConstants.TWO) {
//				List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
//				if (ValidateUtil.isEmpty(orderItemDTOs)) {
//					continue;
//				}
//				for (OrderItemDTO orderItemDTO : orderItemDTOs) {
//					productMap.put(String.valueOf(orderItemDTO.getSkuId()),
//							true);
//				}
//			}
//			map.put(String.valueOf(orderId), productMap);
//		}
//		// 设置退换货状态
//		map = singleService.findIsNotPrecessService(map);
//
//		// 将退换货状态加入 订单列表
//		for (OrderDTO orderDTO : orderDTOs) {
//
//			long orderId = orderDTO.getOrderNo();
//			Map<String, Boolean> skuMap = map.get(String.valueOf(orderId));
//			if (ValidateUtil.isEmpty(skuMap)) {
//				continue;
//			}
//			List<ProductDTO> productList = orderDTO.getProductList();
//			if (!ValidateUtil.isEmpty(productList)) {
//				for (ProductDTO productDTO : productList) {
//					if (ValidateUtil.isEmpty(productDTO)) {
//						continue;
//					}
//					if (ValidateUtil.isEmpty(skuMap.get(String
//							.valueOf(productDTO.getPid())))) {
//						continue;
//					}
//					productDTO.setRrgStatus(skuMap.get(String
//							.valueOf(productDTO.getPid())));
//				}
//			}
//			List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
//			if (!ValidateUtil.isEmpty(orderItemDTOs)) {
//				for (OrderItemDTO orderItemDTO : orderItemDTOs) {
//					if (ValidateUtil.isEmpty(orderItemDTO)) {
//						continue;
//					}
//					if (ValidateUtil.isEmpty(skuMap.get(String
//							.valueOf(orderItemDTO.getSkuId())))) {
//						continue;
//					}
//					orderItemDTO.setRrgStatus(skuMap.get(String
//							.valueOf(orderItemDTO.getSkuId())));
//				}
//			}
//
//		}
//
//	}
}
