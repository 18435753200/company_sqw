package com.mall.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderLogisticsFee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2818425121198244491L;

	private Long orderId;
	
	List<OrderItemLogistics> orderItems;
	
	private BigDecimal orderLogisticsPrice;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<OrderItemLogistics> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemLogistics> orderItems) {
		this.orderItems = orderItems;
	}

	public BigDecimal getOrderLogisticsPrice() {
		return orderLogisticsPrice;
	}

	public void setOrderLogisticsPrice(BigDecimal orderLogisticsPrice) {
		this.orderLogisticsPrice = orderLogisticsPrice;
	}
	
	
}
