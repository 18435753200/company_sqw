package com.mall.supplier.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class OrderItemLogistics implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7252651434278993170L;


    private Long productId;
    
    private Long supplierId;//供应商ID
    
    private Long skuId;
    
    private BigDecimal quantity;
    
    private BigDecimal price;
    
    private BigDecimal itemLogisticPrice;

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getItemLogisticPrice() {
		return itemLogisticPrice;
	}

	public void setItemLogisticPrice(BigDecimal itemLogisticPrice) {
		this.itemLogisticPrice = itemLogisticPrice;
	}
    
    
    
}
