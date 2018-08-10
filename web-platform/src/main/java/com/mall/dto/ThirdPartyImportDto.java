package com.mall.dto;

import java.io.Serializable;

/**
 * 第三方订单导入bean
 * @author A
 *
 */
public class ThirdPartyImportDto implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**订单**/
    private String orderId;
    
    /**SKUID**/
    private Long skuId;
    /**SKU数量**/
	private String skuNum;
	 /**SKU价格**/
	private String skuPrice;
	
    /**订单总价**/
    private String price;
    /**订单类型-订单来源名称**/
    private String orderTypeName;

    /**接收人**/
    private String peceive_name;
    /**接收人电话**/
    private String peceive_mobile_phone;
    /**接收人地址**/
	private String peceive_address;
	/**快递备注**/
	private String remark;
	
	/**运费**/
	private String transfer_price;
	
	/**是否需要发票**/
	private String needInvoice;
	/**发票抬头**/
	private String invoiceTitle;
	/**发票明细**/
	private String invoiceDetail;
   

	
	public ThirdPartyImportDto() {
		
	}


	public ThirdPartyImportDto(String orderId, Long skuId,
			String skuNum, String skuPrice, String price,
			String orderTypeName, String peceive_name,
			String peceive_mobile_phone, String peceive_address, String remark,
			String transfer_price, String needInvoice, String invoiceTitle,
			String invoiceDetail) {
		super();
		this.orderId = orderId;
		this.skuId = skuId;
		this.skuNum = skuNum;
		this.skuPrice = skuPrice;
		this.price = price;
		this.orderTypeName = orderTypeName;
		this.peceive_name = peceive_name;
		this.peceive_mobile_phone = peceive_mobile_phone;
		this.peceive_address = peceive_address;
		this.remark = remark;
		this.transfer_price = transfer_price;
		this.needInvoice = needInvoice;
		this.invoiceTitle = invoiceTitle;
		this.invoiceDetail = invoiceDetail;
	}




	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public Long getSkuId() {
		return skuId;
	}



	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}



	public String getSkuNum() {
		return skuNum;
	}



	public void setSkuNum(String skuNum) {
		this.skuNum = skuNum;
	}



	public String getSkuPrice() {
		return skuPrice;
	}



	public void setSkuPrice(String skuPrice) {
		this.skuPrice = skuPrice;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	public String getOrderTypeName() {
		return orderTypeName;
	}



	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}



	public String getPeceive_name() {
		return peceive_name;
	}



	public void setPeceive_name(String peceive_name) {
		this.peceive_name = peceive_name;
	}



	public String getPeceive_mobile_phone() {
		return peceive_mobile_phone;
	}



	public void setPeceive_mobile_phone(String peceive_mobile_phone) {
		this.peceive_mobile_phone = peceive_mobile_phone;
	}



	public String getPeceive_address() {
		return peceive_address;
	}



	public void setPeceive_address(String peceive_address) {
		this.peceive_address = peceive_address;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getTransfer_price() {
		return transfer_price;
	}



	public void setTransfer_price(String transfer_price) {
		this.transfer_price = transfer_price;
	}



	public String getNeedInvoice() {
		return needInvoice;
	}



	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}



	public String getInvoiceTitle() {
		return invoiceTitle;
	}



	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}



	public String getInvoiceDetail() {
		return invoiceDetail;
	}



	public void setInvoiceDetail(String invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}


	@Override
	public String toString() {
		return "ThirdPartyImportDto [orderId=" + orderId  + ", skuId=" + skuId + ", skuNum=" + skuNum
				+ ", skuPrice=" + skuPrice + ", price=" + price
				+ ", orderTypeName=" + orderTypeName + ", peceive_name="
				+ peceive_name + ", peceive_mobile_phone="
				+ peceive_mobile_phone + ", peceive_address=" + peceive_address
				+ ", remark=" + remark + ", transfer_price=" + transfer_price
				+ ", needInvoice=" + needInvoice + ", invoiceTitle="
				+ invoiceTitle + ", invoiceDetail=" + invoiceDetail + "]";
	}

	
	
	
}
