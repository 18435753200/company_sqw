package com.mall.controller.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.constant.CommonConstant;
import com.mall.constant.OrderConstants;
import com.mall.customer.model.ReceiveAddress;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.CartActivityGroupVO;
import com.mall.customer.order.dto.CartDTO;
import com.mall.customer.order.dto.CartGroupVO;
import com.mall.customer.order.dto.CartSkuDTO;
import com.mall.customer.order.dto.CpsOrderDTO;
import com.mall.customer.order.dto.CustomerOrder;
import com.mall.customer.order.dto.OrderPromoteRelationDTO;
import com.mall.customer.order.dto.ResultCode;
import com.mall.customer.order.dto.SubOrderDTO;
import com.mall.customer.order.exception.BusinessException;
import com.mall.customer.order.po.CpsOrder;
import com.mall.dealer.product.customer.api.CustomerShopCarApi;
import com.mall.dealer.product.dto.B2cProductShopCarShowDTO;
import com.mall.utils.ConstProperteUtil;
import com.mall.utils.CookieUtil;
import com.mall.vo.CustomerOrderVO;
import com.mall.vo.CustomerRechargeOrderVO;

/**
 * @author zhengqiang.shi
 * 2015年4月1日 上午10:13:57
 */
@Service
public class OrderControllerImpl extends AbstractControllerImpl {

	private static Logger log = LoggerFactory.getLogger(OrderControllerImpl.class);
	
	@Autowired
	private CustomerOrderService customerOrderService;
	
	@Autowired
	private CustomerShopCarApi customerShopCarApi;
	
	/**
	 * 提交订单（充值）
	 * @param user
	 * @param customerRechargeOrderVO
	 * @return
	 */
	public SubOrderDTO submit(User user, CustomerRechargeOrderVO customerRechargeOrderVO) {
		
		CustomerOrder customerOrder = new CustomerOrder();
		
		// 购买商品信息
		this.setCartSkuDTO(customerRechargeOrderVO, customerOrder);
		
		// 优惠券信息
		this.setCouponsInfo(customerRechargeOrderVO, customerOrder);
		
		// 收货地址信息
		this.setAddress(customerRechargeOrderVO, customerOrder);
		
		// 其他信息
		this.setOtherInfo(user, customerOrder, customerRechargeOrderVO);
		
		SubOrderDTO subOrderDTO = null;
		
		try {
			subOrderDTO = customerOrderService.submitCustomerOrderForCombineOrder(customerOrder);
		} catch (Exception e) {
			log.error("customerOrderService.submitCustomerOrderForCombineOrder failed.message:"+e.getMessage(),e);
			String message = e.getMessage();
			
			ResultCode rc = new ResultCode();
			rc.setCode(message);
			
			subOrderDTO = new SubOrderDTO();
			subOrderDTO.setResultCodes(Arrays.asList(rc));
			
			return subOrderDTO;
		}
		
		return subOrderDTO;
	}
	
	/**
	 * 购物车提交订单
	 * @param user
	 * @param cartDTO
	 * @param receiveAddress
	 * @param customerOrderVO
	 * @return 
	 */
	public SubOrderDTO submit(User user, CartDTO cartDTO, ReceiveAddress receiveAddress, 
			CustomerOrderVO customerOrderVO, HttpServletRequest request) {

		CustomerOrder customerOrder = new CustomerOrder();
		// 优惠券信息
		this.setCouponsInfo(customerOrderVO,cartDTO);

		// 购买商品信息
		this.setCartInfo(customerOrder, cartDTO);

		// 收货地址信息
		this.setAddress(customerOrder, receiveAddress);

		// 其他信息
		this.setOtherInfo(customerOrder, user, customerOrderVO, request);

		customerOrder.setIsCart(true);
		// 提交订单
		//SubOrderDTO subOrderDTO = customerOrderService.submitCustomerOrder(customerOrder);
		SubOrderDTO subOrderDTO;
		try {
			subOrderDTO = customerOrderService.submitCustomerOrderForCombineOrder(customerOrder);
		} catch (Exception e) {
			String message = e.getMessage();

			ResultCode rc = new ResultCode();
			rc.setCode(message);

			subOrderDTO = new SubOrderDTO();
			subOrderDTO.setResultCodes(Arrays.asList(rc));

			return subOrderDTO;
		}

		if(subOrderDTO == null){
			throw new BusinessException("submit order has errors...");
		}

		return subOrderDTO;
	}
	
	/**
	 * 立即购买提交订单
	 * @param user
	 * @param cartDTO
	 * @param receiveAddress
	 * @param customerOrderVO
	 * @return 
	 */
	public SubOrderDTO submitDirectBuy(User user, CartDTO cartDTO, ReceiveAddress receiveAddress, 
			CustomerOrderVO customerOrderVO, HttpServletRequest request) {
		
		CustomerOrder customerOrder = new CustomerOrder();
		// 优惠券信息
		this.setCouponsInfo(customerOrderVO,cartDTO);
		
		// 购买商品信息
		this.setCartInfo(customerOrder, cartDTO);
		
		// 收货地址信息
		this.setAddress(customerOrder, receiveAddress);
		
		// 其他信息
		this.setOtherInfo(customerOrder, user, customerOrderVO, request);
		
		customerOrder.setIsCart(false);
		// 提交订单
		//SubOrderDTO subOrderDTO = customerOrderService.submitCustomerOrder(customerOrder);
		SubOrderDTO subOrderDTO;
		try {
			subOrderDTO = customerOrderService.submitCustomerOrderForCombineOrder(customerOrder);
		} catch (Exception e) {
			String message = e.getMessage();
			
			ResultCode rc = new ResultCode();
			rc.setCode(message);
			
			subOrderDTO = new SubOrderDTO();
			subOrderDTO.setResultCodes(Arrays.asList(rc));
			
			return subOrderDTO;
		}
		
		if(subOrderDTO == null){
			throw new BusinessException("submit order has errors...");
		}
		
		return subOrderDTO;
	}
	
	private void setOtherInfo(User user, CustomerOrder customerOrder, CustomerRechargeOrderVO customerRechargeOrderVO){
		customerOrder.setOrderSource(OrderConstants.ORDER_SOURCE_TELECOM_RECHARGE);
		customerOrder.setUserId(user.getUserId());
		customerOrder.setOrderType(OrderConstants.ORDER_TYPE_100);
		customerOrder.setOrderPlatform(Short.valueOf(customerRechargeOrderVO.getOrderPlatform()));
	}
	
	/**
	 * 组织订单其他信息
	 * @param customerOrder
	 * @param user
	 * @param customerOrderVO
	 */
	private void setOtherInfo(CustomerOrder customerOrder, User user, 
			CustomerOrderVO customerOrderVO, HttpServletRequest request) {
		if(isYueMeRequest(request)){
			customerOrder.setOrderPlatform(CommonConstant.ACCESS_MODE_YUEME.shortValue());
		}else{
			customerOrder.setOrderPlatform(CommonConstant.ACCESS_MODE_WAP.shortValue());
		}
		customerOrder.setNeedInvoice(customerOrderVO.getNeedInvoice());
		customerOrder.setInvoiceTitle(customerOrderVO.getInvoiceTitle());
		customerOrder.setInvoiceDetail(customerOrderVO.getInvoiceDetail());
		customerOrder.setUserId(user.getUserId());
		customerOrder.setCreateBy(user.getUserName());
		customerOrder.setMessage(customerOrderVO.getMessage());
		customerOrder.setCheck(customerOrderVO.getIsCheck());
		
		// 订单来源
		if(StringUtils.isNotBlank(customerOrderVO.getOrderFrom())){
			customerOrder.setOrderSource(customerOrderVO.getOrderFrom());
			
			
		}else{
			setCookieValueToOrder(customerOrder, request,CommonConstant.ORIGIN_COOKIE_NAME);
			setCookieValueToOrder(customerOrder, request,CommonConstant.COOKIE_IPTV);
		}
		
		//cpsorder
		CpsOrder cpsOrder = new CpsOrder();
		cpsOrder.setCpsChannel(customerOrderVO.getCpsChannel());
		cpsOrder.setCpsMemId(customerOrderVO.getCpsMemId());
		cpsOrder.setlType(customerOrderVO.getlType());
		cpsOrder.setmType(customerOrderVO.getmType());
		cpsOrder.setOrderSource(customerOrderVO.getOrderFrom());
		cpsOrder.setSiteType(customerOrderVO.getSiteType());
		customerOrder.setCpsOrderDTO(new CpsOrderDTO(cpsOrder));
	}

	private void setCookieValueToOrder(CustomerOrder customerOrder,HttpServletRequest request, String cookieName) {
			
		Cookie cookie = CookieUtil.getCookie(request, cookieName);
		if(cookie != null){
			String cookieValue = cookie.getValue();
			log.info("----- >> setOtherInfo cookieValue:"+cookieValue);
			if(StringUtils.isNotBlank(cookieValue)){
				customerOrder.setOrderSource(cookieValue);
			}
		}
	}

	/**
	 * 组织购物车购买商品信息
	 * @param customerOrder
	 * @param cartDTO
	 */
	private void setCartInfo(CustomerOrder customerOrder,CartDTO cartDTO){
		
		List<CartSkuDTO> skuList = new ArrayList<CartSkuDTO>();
		
		List<CartGroupVO> cartGroupVOList  = cartDTO.getCartGroupVOList();
		for (CartGroupVO cartGroupVO : cartGroupVOList) {
			List<CartActivityGroupVO> activityGroupList = cartGroupVO.getActivityGroupList();
			for (int i = 0; i < activityGroupList.size(); i++) {
				skuList.addAll(activityGroupList.get(i).getSkuList());
			}
		}
		
		customerOrder.setCartSkuDTOs(skuList);
	}
	
	/**
	 * 组织收货地址信息
	 * @param customerOrder
	 * @param receiveAddress
	 */
	private void setAddress(CustomerOrder customerOrder,ReceiveAddress receiveAddress){
		customerOrder.setAddressId(receiveAddress.getAddressId());
		customerOrder.setReceiveName(receiveAddress.getContactor());
		customerOrder.setReceiveMobilePhone(receiveAddress.getMobile());
		
		Long provinceId = receiveAddress.getProvinceId();
		Long cityId = receiveAddress.getCityId();
		Long areaId = receiveAddress.getAreaId();
		
		//String address = baseDataServiceRpc.getProvinceCityCountyName(provinceId, cityId, areaId);
		
		customerOrder.setReceiveProvinceId(provinceId);
		customerOrder.setReceiveCityId(cityId);
		customerOrder.setReceiveAreaId(areaId);
		customerOrder.setReceiveAddress(receiveAddress.getProvinceCityCountyName() + " " + receiveAddress.getAddress());
	}
	
	private void setAddress(CustomerRechargeOrderVO customerRechargeOrderVO, CustomerOrder customerOrder){
		customerOrder.setAddressId(0L);
		customerOrder.setReceiveMobilePhone(customerRechargeOrderVO.getPhoneNumber());
		customerOrder.setReceiveProvinceId(0L);
		customerOrder.setReceiveCityId(0L);
		customerOrder.setReceiveAreaId(0L);
	}
	
	private void setCartSkuDTO(CustomerRechargeOrderVO customerRechargeOrderVO, CustomerOrder customerOrder){
	
		Map<Long, B2cProductShopCarShowDTO> shopCarMap = customerShopCarApi.findShopCarDtoBySkuIds(Arrays.asList(customerRechargeOrderVO.getSkuId()));
		
		B2cProductShopCarShowDTO b2cProductShopCarShowDTO = shopCarMap.get(customerRechargeOrderVO.getSkuId());
		
		CartSkuDTO cartSkuDTO = new CartSkuDTO();
		cartSkuDTO.setPid(b2cProductShopCarShowDTO.getProductId());
		cartSkuDTO.setSkuId(customerRechargeOrderVO.getSkuId());
		cartSkuDTO.setQty(1);
		cartSkuDTO.setProductType(String.valueOf(b2cProductShopCarShowDTO.getSupply()));
		cartSkuDTO.setPrice(b2cProductShopCarShowDTO.getPrice());
		cartSkuDTO.setOrderType((short)100);
		
		customerOrder.setCartSkuDTOs(Arrays.asList(cartSkuDTO));
	}
	
	private void setCouponsInfo(CustomerRechargeOrderVO customerRechargeOrderVO, CustomerOrder customerOrder){
		
		Long couponsId = customerRechargeOrderVO.getCouponsId();
		Short couponsType = customerRechargeOrderVO.getCouponsType();
		if(couponsId == null || couponsType == null){
			log.info("电信充值订单 未使用优惠券。");
			return;
		}
		
		List<CartSkuDTO> cartSkuDTOs = customerOrder.getCartSkuDTOs();
		
		CartSkuDTO cartSkuDTO = cartSkuDTOs.get(0);
		
		List<OrderPromoteRelationDTO> oprList = new ArrayList<OrderPromoteRelationDTO>();
		OrderPromoteRelationDTO opr = new OrderPromoteRelationDTO();
		opr.setActiveCouponId(couponsId);
		opr.setType(couponsType);
		
		oprList.add(opr);
		
		cartSkuDTO.setPromoteRelationDTOs(oprList);
		
	}
	
	/**
	 * 组织使用优惠券信息
	 * @param cartDTO
	 */
	private void setCouponsInfo(CustomerOrderVO customerOrderVO,CartDTO cartDTO){
		
		// 优惠券Map
		Map<Long, String> orderPromoteRelationsMap = customerOrderVO.getOrderPromoteRelationsMap();

		if(orderPromoteRelationsMap != null && !orderPromoteRelationsMap.isEmpty()){
			
			List<CartGroupVO> cartGroupVOList = cartDTO.getCartGroupVOList();
			for(CartGroupVO cartGroupVO : cartGroupVOList){
				List<CartActivityGroupVO> activityGroupList = cartGroupVO.getActivityGroupList();
				for (CartActivityGroupVO cartActivityGroupVO : activityGroupList) {
				
					for(CartSkuDTO cartSkuDTO : cartActivityGroupVO.getSkuList()){
						
						Iterator<Entry<Long, String>> it = orderPromoteRelationsMap.entrySet().iterator();
						List<OrderPromoteRelationDTO> oprList = new ArrayList<OrderPromoteRelationDTO>();
						
						while(it.hasNext()){
							Entry<Long, String> next = it.next();
							Long skuId = next.getKey();
							String couponsData = next.getValue();
							if(cartSkuDTO.getSkuId().equals(skuId)){
								
								String[] data = couponsData.split("_");
								Long couponsId = Long.valueOf(data[0]);
								Short couponsType = Short.valueOf(data[1]);
								
								OrderPromoteRelationDTO opr = new OrderPromoteRelationDTO();
								opr.setActiveCouponId(couponsId);
								opr.setType(couponsType);
								
								oprList.add(opr);
							}
						}
						if(!oprList.isEmpty()){
							cartSkuDTO.setPromoteRelationDTOs(oprList);
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * 完善订单信息 主要是通过cookie完善 订单来源 
	 * @param customerOrderVO
	 * @param req
	 */
	public void complateOder(CustomerOrderVO customerOrderVO,HttpServletRequest req){
		// 订单来源
		String cookieValue = CookieUtil.getCookieValue(req, CommonConstant.ORDER_FORM_KEY);
		//cps 取 cookie
		String cpsCookieValue = CookieUtil.getCookieValue(req, CommonConstant.CPS_COOKIE_KEY);
		
		// print log
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("method", "从cookie中获取订单来源.");
		map.put("returnValue", cookieValue);
		print(map);
		
		// 订单来源
		if(StringUtils.isNotBlank(cookieValue)){
			customerOrderVO.setOrderFrom(cookieValue);
		}
		
		//cps 不为空 时 设置cps信息 zhanglk 2015-11-04
		if(StringUtils.isNotBlank(cpsCookieValue)){
			String arr[] = cpsCookieValue.split("\\|");
			if(arr != null && arr.length >0 ){
				if(ConstProperteUtil.getInstance().findSrcLkt().equals(arr[0]) && arr.length == 5){//领克特
					customerOrderVO.setOrderFrom(arr[0]);
					customerOrderVO.setCpsMemId(arr[1]);
					customerOrderVO.setCpsChannel("wap");//wap端为wap
					customerOrderVO.setmType("2");//广告投放方式 2 为cps
					customerOrderVO.setlType(arr[4]);
					customerOrderVO.setSiteType("b2c");//广告发布站点类型 b2c端
					customerOrderVO.setCpsCookie(cpsCookieValue);//取出cookie值
				}else if(ConstProperteUtil.getInstance().findCpsSrcYqf().equals(arr[0]) && arr.length == 4){//亿起发
					customerOrderVO.setOrderFrom(arr[0]);
					customerOrderVO.setCpsMemId(arr[1]);
					customerOrderVO.setCpsChannel("wap");//wap端为wap
					customerOrderVO.setmType("2");//广告投放方式 2 为cps
					customerOrderVO.setlType(arr[2]);
					customerOrderVO.setSiteType("b2c");//广告发布站点类型 b2c端
					customerOrderVO.setCpsCookie(cpsCookieValue);//取出cookie值
				}
			}
		}
	}
}
