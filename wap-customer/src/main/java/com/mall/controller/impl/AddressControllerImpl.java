/**
 * 
 */
package com.mall.controller.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.architect.redis.JedisManager;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.constant.CommonConstant;
import com.mall.customer.model.ReceiveAddress;
import com.mall.customer.order.common.CartConstant;
import com.mall.customer.service.ReceiveAddressService;
import com.mall.utils.Constants;
import com.mall.utils.CookieUtil;

/**
 * @author zhengqiang.shi
 * 2015年3月25日 下午7:04:44
 */
@Service
public class AddressControllerImpl extends AbstractControllerImpl {
	
	private static Logger log = LoggerFactory.getLogger(AddressControllerImpl.class);
	
	@Autowired
	private ReceiveAddressService receiveAddressService;
	
	@Autowired
	private BaseDataServiceRpc baseDataServiceRpc;
	
	/**
	 * 根据用户ID获取收货地址列表
	 * @param userId
	 * @return
	 */
	public List<ReceiveAddress> getReceiveAddressesByUserId(Long userId){
		map = new HashMap<Object, Object>();
		map.put("Method", "getReceiveAddressesByUserId");
		map.put("userId", userId);
		print(map);
		List<ReceiveAddress> receiveAddressList = receiveAddressService.getReceiveAddressesByUserId(userId);
		setReceiveAddressProvCityCountName(receiveAddressList);
		return receiveAddressList;
	}

	/**
	 * 根据地址ID获取收货地址
	 * @param addressId
	 * @return
	 */
	public ReceiveAddress getReceiveAddressByAddressId(Long addressId){
		map = new HashMap<Object, Object>();
		map.put("Method", "getReceiveAddressByAddressId");
		map.put("addressId", addressId);
		print(map);
		ReceiveAddress address = receiveAddressService.getReceiveAddressByAddressId(addressId);
		setReceiveAddressProvCityCountName(address);
		return address;
	}
	
	/**
	 * 设置省市区名称
	 * @param receiveAddress
	 */
	public void setProvinceCityCountyName(ReceiveAddress receiveAddress){
		Long provinceId = receiveAddress.getProvinceId();
		Long cityId = receiveAddress.getCityId();
		Long areaId = receiveAddress.getAreaId();
		
		String address = getProvinceCityCountyName(provinceId, cityId, areaId);
		
		String currentAddress = receiveAddress.getAddress();
		receiveAddress.setAddress(address+" "+currentAddress);
	}
	
	/**
	 * 获取省市区名称根据省市区ID
	 * @param provinceId
	 * @param cityId
	 * @param areaId
	 * @return
	 */
	public String getProvinceCityCountyName(Long provinceId,Long cityId,Long areaId){
		return baseDataServiceRpc.getProvinceCityCountyName(provinceId, cityId, areaId);
	}
	
	/**
	 * 收货省市区信息放到redis
	 * @param response
	 * @param receiveAddress
	 * @param userId
	 * @param key
	 */
	public void setAreaToCookieAndRedis(HttpServletRequest request,HttpServletResponse response,
			Long provinceId,Long cityId,Long areaId,Long userId){
		
		String areaName = getProvinceCityCountyName(provinceId, cityId, areaId);
		
		StringBuffer sb = new StringBuffer();
		sb.append(provinceId).append(",");
		sb.append(cityId).append(",");
		sb.append(areaId).append(",");
		sb.append(areaName);
		String value = sb.toString();
		
		String areaKey = CartConstant.AREAKEY;
		if(userId != null){
			// 判读是否悦me渠道 10001
			if (isYueMeRequest(request)) {
				areaKey += userChannelDifferent(userId, CommonConstant.ACCESS_MODE_YUEME);
			}
			else {
				areaKey += userChannelDifferent(userId, CommonConstant.ACCESS_MODE_WAP);
			}
		}else{
			String key = CookieUtil.getCookieValue(request, CommonConstant.KEY);
			if(StringUtils.isBlank(key)){
				key = UUID.randomUUID().toString();
				CookieUtil.setCookie(response, CommonConstant.KEY, key, CommonConstant.OUT_TIME_COOKIE);
			}
			
			areaKey += key;
		}
		
		log.info("start to set area to cookie,value:"+value);
		CookieUtil.setCookie(response, Constants.AREAIDKEY,URLEncoder.encode(value),CommonConstant.OUT_TIME_COOKIE);
		log.info("set area to cookie finished ");
		
		log.info("start to set area to redis,key:"+areaKey+",value:"+value);
		JedisManager.setString(areaKey, value, CommonConstant.OUT_TIME_COOKIE);
		log.info("set area to redis finished ");
		
		
	}
	
	private String userChannelDifferent(Long userId, Long accessMode) {
		StringBuffer sb = new StringBuffer();
		if (userId != null) {
			sb.append(userId.toString());
		}
		
		if (null != accessMode && accessMode > 4) {
			sb.append("_");
			sb.append(accessMode);
		}
		return (null == sb) ? "" : sb.toString();
	}
	/**
	 * 设置省市区信息
	 * @param addressList
	 */
	private void setReceiveAddressProvCityCountName(List<ReceiveAddress> addressList) {
		if (null == addressList || addressList.size() <= 0) {
			return;
		}
		
		for (ReceiveAddress address : addressList) {
			setReceiveAddressProvCityCountName(address);
		}
	}
	
	/**
	 * 设置省市区信息
	 * @param address
	 */
	private void setReceiveAddressProvCityCountName(ReceiveAddress address) {
		if (null == address) {
			return;
		}
		
		address.setProvinceCityCountyName(this.getProvinceCityCountyName(address.getProvinceId(), address.getCityId(), address.getAreaId()));
	}
}
