/**
 * 
 */
package com.mall.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.constant.CommonConstant;
import com.mall.customer.order.api.rpc.CustomerCartExtraService;
import com.mall.customer.order.api.rpc.CustomerCartService;
import com.mall.customer.order.common.CartConstant;
import com.mall.customer.order.dto.CartAddRequest;
import com.mall.customer.order.dto.CartChangeSelectRequest;
import com.mall.customer.order.dto.CartDTO;
import com.mall.customer.order.dto.CartDirectBuyRequest;
import com.mall.customer.order.dto.CartGetCountRequest;
import com.mall.customer.order.dto.CartGetRequest;
import com.mall.customer.order.dto.CartRemoveRequest;
import com.mall.customer.order.dto.CartResultDTO;
import com.mall.customer.order.dto.CartSelectRequest;
import com.mall.customer.order.dto.CartUpdateQtyRequest;
import com.mall.utils.CookieUtil;
import com.mall.utils.StringUtil;
import com.mall.vo.CustomerCartVO;

/**
 * @author zhengqiang.shi 2015年3月23日 上午11:15:44
 */
@Service
public class CartControllerImpl extends AbstractControllerImpl {

	private static final Logger log = LoggerFactory
			.getLogger(CartControllerImpl.class);

	@Autowired
	private CustomerCartService customerCartService;
	
	@Autowired
	private CustomerCartExtraService CustomerCartExtraServiceImpl; 

	/**
	 * 添加购物车实现
	 * 
	 * @param userId
	 * @param skuId
	 * @param qty
	 * @param request
	 * @param response
	 */
	public String addSku(Long userId, Long skuId, Integer qty,
			HttpServletRequest request, HttpServletResponse response) {

		String cookieValue = CookieUtil.getCookieValue(request,
				CommonConstant.KEY);
		map = new HashMap<Object, Object>();
		map.put("Method", "addSku");
		map.put("userId", userId);
		map.put("cookieValue", cookieValue);
		map.put("skuId", skuId);
		map.put("qty", qty);
		print(map);

		// 用户未登录并且无Cookie的情况
		if (isNoKeyForCart(userId, cookieValue)) {
			cookieValue = UUID.randomUUID().toString();
			CookieUtil.setCookie(response, CommonConstant.KEY, cookieValue,
					CommonConstant.OUT_TIME_COOKIE);
		}

		Map<Long, Integer> skuIdQtyMap = new HashMap<Long, Integer>();
		skuIdQtyMap.put(skuId, qty);

		
		CartAddRequest cartAddRequest = null;
		
		// 是否为悦me渠道 10001
		if (isYueMeRequest(request)) {
			// userId = userChannelDifferent(userId);
			cartAddRequest = new CartAddRequest(userId, cookieValue, skuIdQtyMap, CommonConstant.ACCESS_MODE_YUEME);
		}
		else {
			cartAddRequest = new CartAddRequest(userId, cookieValue, skuIdQtyMap, CommonConstant.ACCESS_MODE_WAP);
		}

		String result = customerCartService.addSku(cartAddRequest);

		log.info("end to call service addSku,return value:" + result);

		if (StringUtils.isBlank(result)) {
			log.info("result为空，添加失败");
			return CartConstant.CART_ADD_FAILED;
		}else{
			// 购物车添加成功
			if (CartConstant.CART_ADD_SUCCESS.equals(result)) {
				log.info("购物车添加成功");
				return result;
			}
			
			// 超出库存
			if (CartConstant.CART_EXCESS_INVENTORY.equals(result)) {
				log.info("超出库存");
				return result;
			}
			// 购物车商品数量超过100件
			if (CartConstant.CART_ITEM_GT_100.equals(result)) {
				log.info("购物车商品数量超过100件");
				return result;
			}
			
			// 购物车添加失败
			if (CartConstant.CART_ADD_FAILED.equals(result)) {
				log.info("购物车添加失败");
				return result;
			}
			
			return CartConstant.CART_ADD_FAILED;
		}
	}

	/**
	 * 获取购物车商品数量
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	public Integer getCountQty(Long userId, HttpServletRequest request) {
		String cookieValue = CookieUtil.getCookieValue(request,
				CommonConstant.KEY);
		map = new HashMap<Object, Object>();
		map.put("Method", "getCountQty");
		map.put("userId", userId);
		map.put("cookieValue", cookieValue);
		print(map);

		if (isNoKeyForCart(userId, cookieValue)) {
			log.info("userId and cookieValue both is null,return item number:0");
			return 0;
		}

		CartGetCountRequest cartGetCountRequest = null;
		
		// 是否为悦me渠道 10001
		if (isYueMeRequest(request)) {
//			userId = userChannelDifferent(userId);
			cartGetCountRequest = new CartGetCountRequest(userId, cookieValue, CommonConstant.ACCESS_MODE_YUEME);
		}
		else {
			cartGetCountRequest = new CartGetCountRequest(userId, cookieValue, CommonConstant.ACCESS_MODE_WAP);
		}

		return customerCartService.getCountQty(cartGetCountRequest);
	}

	/**
	 * 进入购物车首页
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @return
	 */
	public CartDTO getCart(Long userId, HttpServletRequest request,
			HttpServletResponse response) {
		String cookieValue = CookieUtil.getCookieValue(request,
				CommonConstant.KEY);

		Map<Long, String> skuAndactivityInfoMap = new HashMap<Long, String>();
		String skuAndactivityInfo = request.getParameter("skuAndactivityInfo");

		if (!StringUtils.isBlank(skuAndactivityInfo)) {
			String[] skuAndactivityInfos = skuAndactivityInfo.split(",");
			String skuId = skuAndactivityInfos[0];
			String mainRuleId = skuAndactivityInfos[1];
			String mainRuleTerm = skuAndactivityInfos[2];
			String mainRuleName = skuAndactivityInfos[3];
			if (!StringUtils.isBlank(skuId) && !StringUtils.isBlank(mainRuleId)
					&& !StringUtils.isBlank(mainRuleTerm)
					&& !StringUtils.isBlank(mainRuleName)) {
				StringBuffer sb = new StringBuffer();
				sb.append(mainRuleId).append(",");
				sb.append(mainRuleTerm).append(",");
				sb.append(mainRuleName).append(",");
				skuAndactivityInfoMap.put(Long.valueOf(skuId), sb.toString());
			}
		}
		map = new HashMap<Object, Object>();
		map.put("Method", "getCart");
		map.put("userId", userId);
		map.put("cookieValue", cookieValue);
		map.put("skuAndactivityInfoMap", skuAndactivityInfoMap);
		print(map);

		if (isNoKeyForCart(userId, cookieValue)) {
			log.info("cart is null.because userId and cookieValue is null.");
			return null;
		}

		CartGetRequest cartGetRequest = null;
		if (isYueMeRequest(request)) {
//			userId = userChannelDifferent(userId);
			cartGetRequest = new CartGetRequest(userId, cookieValue, CommonConstant.ACCESS_MODE_YUEME, skuAndactivityInfoMap);
		}
		else {
			cartGetRequest = new CartGetRequest(userId, cookieValue, CommonConstant.ACCESS_MODE_WAP, skuAndactivityInfoMap);
		}

		
		CartDTO cartDTO = customerCartService.getCart(cartGetRequest);

		if (userId != null && !StringUtils.isBlank(cookieValue)) {
			log.info("购物车信息合并完成，删除用户cookie.");
			CookieUtil.deleteCookie(response, new Cookie(CommonConstant.KEY,
					null));
		}

		return cartDTO;
	}

	/**
	 * 购物车删除商品
	 * 
	 * @param userId
	 * @param skuId
	 * @param request
	 * @return
	 */
	public String deleteSkus(Long userId, List<Long> skuIdList,
			HttpServletRequest request) {
		String cookieValue = CookieUtil.getCookieValue(request,
				CommonConstant.KEY);
		map = new HashMap<Object, Object>();
		map.put("Method", "deleteSkus");
		map.put("userId", userId);
		map.put("cookieValue", cookieValue);
		print(map);

		if (isNoKeyForCart(userId, cookieValue)) {
			log.info("userId and cookieValue is null.need login.");
			return "503";
		}
		CartRemoveRequest cartRemoveRequest = null;

		// 是否为悦me渠道 10001
		if (isYueMeRequest(request)) {
//			userId = userChannelDifferent(userId);
			cartRemoveRequest = new CartRemoveRequest(userId, cookieValue, skuIdList, false, CommonConstant.ACCESS_MODE_YUEME);
		}
		else {
			cartRemoveRequest = new CartRemoveRequest(userId, cookieValue, skuIdList, false, CommonConstant.ACCESS_MODE_WAP);
		}

		customerCartService.deleteSkus(cartRemoveRequest);

		return "200";
	}

	/**
	 * 购物车更改商品购买数量
	 * 
	 * @param userId
	 * @param skuId
	 * @param qty
	 * @param request
	 * @return
	 */
	public CartResultDTO updateQty(Long userId, Long skuId, Integer qty,
			HttpServletRequest request) {
		String cookieValue = CookieUtil.getCookieValue(request,
				CommonConstant.KEY);
		map = new HashMap<Object, Object>();
		map.put("Method", "updateQty");
		map.put("userId", userId);
		map.put("cookieValue", cookieValue);
		map.put("skuId", skuId);
		map.put("qty", qty);
		print(map);

		if (isNoKeyForCart(userId, cookieValue)) {
			return null;
		}

		CartUpdateQtyRequest cartUpdateQtyRequest = null;
		
		// 是否为悦me渠道 10001
		if (isYueMeRequest(request)) {
//			userId = userChannelDifferent(userId);
			cartUpdateQtyRequest = new CartUpdateQtyRequest(userId, cookieValue, skuId, qty, CommonConstant.ACCESS_MODE_YUEME, false);
		}
		else {
			cartUpdateQtyRequest = new CartUpdateQtyRequest(userId, cookieValue, skuId, qty, CommonConstant.ACCESS_MODE_WAP, false);
		}
		
		return customerCartService.updateQty(cartUpdateQtyRequest);
	}

	/**
	 * 查询购物车选中sku信息
	 *
	 * @param userId
	 * @param skuIdList
	 * @param request
	 * @return
	 */
	public CartDTO select(Long userId, List<Long> skuIdList, HttpServletRequest request, HttpServletResponse response) {
		String cookieValue = CookieUtil.getCookieValue(request, CommonConstant.KEY);

		if (isNoKeyForCart(userId, cookieValue)) {
			return null;
		}


		Long accessMode = CommonConstant.ACCESS_MODE_WAP;

		// 是否为悦me渠道 10001
		if (isYueMeRequest(request)) {
			accessMode = CommonConstant.ACCESS_MODE_YUEME;
		}

		CartSelectRequest cartSelectRequest = new CartSelectRequest(userId, cookieValue, skuIdList, accessMode);

		CartDTO cartDTO = customerCartService.select(cartSelectRequest);

		String promotion = request.getParameter("promotion");

		// 计算优惠券的优惠
		cartDTO = CustomerCartExtraServiceImpl.setPromotionInfo4Settlement(cartDTO, userId, accessMode, promotion);
		log.info("cartDTO={}", JSONSerializer.toJSON(cartDTO));

		if (userId != null && !StringUtils.isBlank(cookieValue)) {
			log.info("购物车信息合并完成，删除用户cookie.");
			CookieUtil.deleteCookie(response, new Cookie(CommonConstant.KEY, null));
		}

		return cartDTO;
	}
	/**
	 * 查询立即购买sku信息
	 * 
	 * @param userId
	 * @param skuIdList
	 * @param request
	 * @return
	 */
	public CartDTO selectDirectBuy(Long userId, List<Long> skuIdList,
			HttpServletRequest request, HttpServletResponse response) {
		String cookieValue = CookieUtil.getCookieValue(request,
				CommonConstant.KEY);
		map = new HashMap<Object, Object>();
		map.put("Method", "select");
		map.put("userId", userId);
		map.put("cookieValue", cookieValue);
		map.put("skuId", skuIdList);
		print(map);
		
		if (isNoKeyForCart(userId, cookieValue)) {
			return null;
		}
		
		CartSelectRequest cartSelectRequest = null;
		
		Long accessMode = CommonConstant.ACCESS_MODE_WAP;
		
		// 是否为悦me渠道 10001
		if (isYueMeRequest(request)) {
			accessMode = CommonConstant.ACCESS_MODE_YUEME;
		}
		
		cartSelectRequest = new CartSelectRequest(userId, cookieValue, skuIdList, accessMode);
		
		CartDTO cartDTO = customerCartService.selectDirectBuy(cartSelectRequest);
		
		String promotion = request.getParameter("promotion");
		
		// 计算优惠券的优惠
		cartDTO = CustomerCartExtraServiceImpl.setPromotionInfo4Settlement(cartDTO, userId, accessMode, promotion);
		log.info("cartDTO={}", JSONSerializer.toJSON(cartDTO));
		
		if (userId != null && !StringUtils.isBlank(cookieValue)) {
			log.info("购物车信息合并完成，删除用户cookie.");
			CookieUtil.deleteCookie(response, new Cookie(CommonConstant.KEY, null));
		}
		
		return cartDTO;
	}

	public CartDTO changeSelect(Long userId, List<Long> skuIdList,
			HttpServletRequest request, HttpServletResponse response) {
		String cookieValue = CookieUtil.getCookieValue(request,
				CommonConstant.KEY);
		map = new HashMap<Object, Object>();
		map.put("Method", "changeSelect");
		map.put("userId", userId);
		map.put("cookieValue", cookieValue);
		map.put("skuIdList", skuIdList);
		print(map);

		if (isNoKeyForCart(userId, cookieValue)) {
			log.info("userId and cookieValue both was null.");
			return null;
		}

		CartChangeSelectRequest cartChangeSelectRequest = null;
		
		// 是否为悦me渠道 10001
		if (isYueMeRequest(request)) {
//			userId = userChannelDifferent(userId);
			cartChangeSelectRequest = new CartChangeSelectRequest(userId, cookieValue, skuIdList, CommonConstant.ACCESS_MODE_YUEME);
		}
		else {
			cartChangeSelectRequest = new CartChangeSelectRequest(userId, cookieValue, skuIdList, CommonConstant.ACCESS_MODE_WAP);
		}

		CartDTO cartDTO = customerCartService
				.changeSelect(cartChangeSelectRequest);
		if (userId != null && !StringUtils.isBlank(cookieValue)) {
			log.info("购物车信息合并完成，删除用户cookie.");
			CookieUtil.deleteCookie(response, new Cookie(CommonConstant.KEY,
					null));
		}

		return cartDTO;
	}

	/**
	 * 商品详情页直接购买
	 * 
	 * @return
	 */
	public CartDTO directBuy(Long userId, CustomerCartVO customerCartVO,
			HttpServletRequest request) {
		String cookieValue = CookieUtil.getCookieValue(request,
				CommonConstant.KEY);
		Long skuId = customerCartVO.getSkuId();
		Integer qty = customerCartVO.getNumber().intValue();

		map = new HashMap<Object, Object>();
		map.put("Method", "directBuy");
		map.put("userId", userId);
		map.put("cookieValue", cookieValue);
		map.put("skuId", skuId);
		map.put("qty", qty);
		print(map);

		CartDirectBuyRequest cartDirectBuyRequest = null;
		
		Long accessMode = CommonConstant.ACCESS_MODE_WAP;
		
		// 是否为悦me渠道 10001
		if (isYueMeRequest(request)) {
			accessMode = CommonConstant.ACCESS_MODE_YUEME;
		}

		cartDirectBuyRequest = new CartDirectBuyRequest(userId, cookieValue, skuId, qty, accessMode);

		CartDTO cartDto = customerCartService.directBuy(cartDirectBuyRequest);
		// 计算优惠券的优惠
		cartDto = CustomerCartExtraServiceImpl.setPromotionInfo4Settlement(cartDto, userId, accessMode, null);
		log.info("cartDTO={}", JSONSerializer.toJSON(cartDto));
		return cartDto;
	}

//	private Long userChannelDifferent(Long userId) {
//		if (userId != null) {
//			StringBuffer sb = new StringBuffer(userId.toString());
//			sb.append(10001);
//
//			userId = Long.valueOf(sb.toString());
//			
//			return userId;
//		}
//		return null;
//	}

	/**
	 * 用户未登录并且无Cookie的情况
	 * 
	 * @param userId
	 * @param cookieValue
	 * @return
	 */
	private boolean isNoKeyForCart(Long userId, String cookieValue) {
		if (userId == null && StringUtils.isBlank(cookieValue)) {
			return true;
		}

		return false;
	}
}
