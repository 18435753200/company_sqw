/**
 * 
 */
package com.mall.controller.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.exception.BusinessException;
/*import com.mall.promotion.api.coupon.b2c.CouponB2CService;
import com.mall.promotion.dto.coupon.MyCouponStockB2CDTO;
import com.mall.promotion.dto.drools.CouponConditonDto;
import com.mall.promotion.dto.drools.CouponConditonDtoByCartForCount;
import com.mall.promotion.dto.drools.CouponConditonDtoByCartForCount1;*/
import com.mall.vo.CustomerOrderCouponsVO;

/**
 * @author zhengqiang.shi
 * 2015年4月16日 上午10:44:39
 */
@Service
public class OrderCouponsControllerImpl extends AbstractControllerImpl{

	private static final Logger log = LoggerFactory.getLogger(OrderCouponsControllerImpl.class);
	
	/*@Autowired
	private CouponB2CService couponB2CService;*/
	
	/**
	 * 获取可用优惠券
	 * @param customerOrderCouponsVO
	 * @param userId
	 * @return
	 */
	/*public List<MyCouponStockB2CDTO> findMyCouponStockByOrder(CustomerOrderCouponsVO customerOrderCouponsVO,Long userId, Long accessMode){
		
		// 请求参数集合
		List<CouponConditonDto> couponConditonDtoList = new ArrayList<CouponConditonDto>();
		
		// 封装请求参数
		Iterator<Entry<Long, BigDecimal>> it = customerOrderCouponsVO.getFindCouponsMap().entrySet().iterator();
		while(it.hasNext()){
			Entry<Long, BigDecimal> next = it.next();
			Long skuId = next.getKey();
			BigDecimal price = next.getValue();
			
			CouponConditonDto couponConditonDto = new CouponConditonDto();
			couponConditonDto.setSkuId(skuId);
			couponConditonDto.setPrice(price);
			
			couponConditonDtoList.add(couponConditonDto);
		}
		
		// 打印请求参数
		map = new HashMap<Object, Object>();
		map.put("Method", "findMyCouponStockByOrder");
		map.put("userId", userId);
		map.put("couponConditonDtoList", couponConditonDtoList);
		print(map);
		
		// 获取可用优惠券
		return couponB2CService.findMyCouponStockByOrder(couponConditonDtoList, userId, accessMode);
		
	}*/
	
	/**
	 * 根据优惠券ID 获取优惠金额
	 * @param customerOrderCouponsVO
	 * @param userId
	 * @return
	 */
	/*public BigDecimal getDiscountCouponPrice(CustomerOrderCouponsVO customerOrderCouponsVO, Long accessMode, Long userId){
		
		// 请求参数集合
		List<CouponConditonDto> couponConditonDtoList = new ArrayList<CouponConditonDto>();
		
		// 封装请求参数
		Iterator<Entry<Long, BigDecimal>> it = customerOrderCouponsVO.getFindCouponsMap().entrySet().iterator();
		while(it.hasNext()){
			Entry<Long, BigDecimal> next = it.next();
			Long skuId = next.getKey();
			BigDecimal price = next.getValue();
			
			CouponConditonDto couponConditonDto = new CouponConditonDto();
			couponConditonDto.setSkuId(skuId);
			couponConditonDto.setPrice(price);
			couponConditonDto.setUserid(userId);
			couponConditonDtoList.add(couponConditonDto);
		}
		
		// 打印请求参数
		map = new HashMap<Object, Object>();
		map.put("Method", "getDiscountCouponPrice");
		map.put("couponstockId", customerOrderCouponsVO.getCouponstockId());
		map.put("couponConditonDtoList", couponConditonDtoList);
		print(map);
		
		// 获取可用优惠券
		return couponB2CService.getDiscountCouponPrice(couponConditonDtoList, customerOrderCouponsVO.getCouponstockId(), accessMode);
				
	}*/
	
	/*public List<CouponConditonDtoByCartForCount1> getAllCountByCart(CustomerOrderCouponsVO customerOrderCouponsVO,Long userId, Long accessMode){
		
		List<CouponConditonDtoByCartForCount> list = new ArrayList<CouponConditonDtoByCartForCount>();
		
		// 封装请求参数
		Iterator<Entry<String, String>> it = customerOrderCouponsVO.getFindCouponsQtyMap().entrySet().iterator();
		while(it.hasNext()){
			List<CouponConditonDto> clist = new ArrayList<CouponConditonDto>();
			
			Entry<String, String> next = it.next();
			String index = next.getKey();
			String skuIdPrice = next.getValue();
			
			String [] split = skuIdPrice.split(",");
			
			for(String str : split){
				String[] s = str.split("_");
				String skuId = s[0];
				String price = s[1];
				
				CouponConditonDto ccd = new CouponConditonDto();
				ccd.setSkuId(Long.valueOf(skuId));
				ccd.setPrice(new BigDecimal(price));
				
				clist.add(ccd);
			}
			
			CouponConditonDtoByCartForCount c = new CouponConditonDtoByCartForCount();
			c.setIndex(Integer.valueOf(index));
			c.setList(clist);
			list.add(c);
		}
		
		List<CouponConditonDtoByCartForCount1> allCountByCart;
		try {
			allCountByCart = couponB2CService.getAllCountByCart(list, userId, accessMode);
		} catch (Exception e) {
			log.error("coupons getAllCountByCart execute failed!"+e.getMessage(),e);
			return null;
		}
		
		return allCountByCart;
	}*/
}
