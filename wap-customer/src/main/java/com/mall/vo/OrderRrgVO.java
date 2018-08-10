/**
 * 
 */
package com.mall.vo;

import java.io.Serializable;

import com.mall.customer.model.User;
import com.mall.vo.enums.ClientEnum;
import com.mall.vo.enums.OriginEnum;
import com.mall.vo.enums.PatternEnum;
import com.mall.vo.enums.ServiceStatusEmun;
import com.mall.vo.enums.ServiceTypeEmun;

/**
 * @author jianping.gao
 *
 */
public class OrderRrgVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6983422917261924495L;

	// 退换货的类型
	private String applyType;

	// 退换货的数量
	private String applyRrgQty;

	// 描述
	private String desc;

	// 图片路径
	private String[] url;

	// 检测报告 1：有 2：没有
	private String credential;

	// 商品返回方式1：上门取货 2：快递至众聚猫
	private String backProductType;

	// 省id
	private String provinceId;
	// 市id
	private String cityId;
	// 县区id
	private String countyId;
	// 详细地址
	private String address;
	// 收货人的名称
	private String consignor;
	// 收货人的手机
	private String phone;

	// 客户端
	private ClientEnum client;
	//
	private OriginEnum origin;
	//
	private PatternEnum pattern;
	//
	private ServiceTypeEmun serviceType;
	//
	private ServiceStatusEmun serviceStatus;

	private User user;

	private long payId;

	private long orderId;

	private long skuId;

	/**
	 * @return the client
	 */
	public ClientEnum getClient() {
		return ClientEnum.WAP;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(ClientEnum client) {
		this.client = client;
	}

	/**
	 * @return the origin
	 */
	public OriginEnum getOrigin() {
		return OriginEnum.CLIENT;
	}

	/**
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(OriginEnum origin) {
		this.origin = origin;
	}

	/**
	 * @return the pattern
	 */
	public PatternEnum getPattern() {
		return PatternEnum.B2C;
	}

	/**
	 * @param pattern
	 *            the pattern to set
	 */
	public void setPattern(PatternEnum pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the serviceType
	 */
	public ServiceTypeEmun getServiceType() {
		return this.serviceType;
	}

	/**
	 * @param serviceType
	 *            the serviceType to set
	 */
	public void setServiceType(ServiceTypeEmun serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the serviceStatus
	 */
	public ServiceStatusEmun getServiceStatus() {
		return ServiceStatusEmun.APPLY;
	}

	/**
	 * @param serviceStatus
	 *            the serviceStatus to set
	 */
	public void setServiceStatus(ServiceStatusEmun serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the payId
	 */
	public long getPayId() {
		return payId;
	}

	/**
	 * @param payId
	 *            the payId to set
	 */
	public void setPayId(long payId) {
		this.payId = payId;
	}

	/**
	 * @return the orderId
	 */
	public long getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the skuId
	 */
	public long getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId
	 *            the skuId to set
	 */
	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId
	 *            the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the countyId
	 */
	public String getCountyId() {
		return countyId;
	}

	/**
	 * @param countyId
	 *            the countyId to set
	 */
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the consignor
	 */
	public String getConsignor() {
		return consignor;
	}

	/**
	 * @param consignor
	 *            the consignor to set
	 */
	public void setConsignor(String consignor) {
		this.consignor = consignor;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the applyType
	 */
	public String getApplyType() {
		return applyType;
	}

	/**
	 * @param applyType
	 *            the applyType to set
	 */
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	/**
	 * @return the applyRrgQty
	 */
	public String getApplyRrgQty() {
		return applyRrgQty;
	}

	/**
	 * @param applyRrgQty
	 *            the applyRrgQty to set
	 */
	public void setApplyRrgQty(String applyRrgQty) {
		this.applyRrgQty = applyRrgQty;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the url
	 */
	public String[] getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String[] url) {
		this.url = url;
	}

	/**
	 * @return the credential
	 */
	public String getCredential() {
		return credential;
	}

	/**
	 * @param credential
	 *            the credential to set
	 */
	public void setCredential(String credential) {
		this.credential = credential;
	}

	/**
	 * @return the backProductType
	 */
	public String getBackProductType() {
		return backProductType;
	}

	/**
	 * @param backProductType
	 *            the backProductType to set
	 */
	public void setBackProductType(String backProductType) {
		this.backProductType = backProductType;
	}

}
