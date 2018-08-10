/**
 * 
 */
package com.mall.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mall.check.OrderChecks.orderFindCouponsChecks;
import com.mall.check.OrderChecks.orderFindCouponsQtyChecks;
import com.mall.check.OrderChecks.orderGetDiscountCouponPriceChecks;

/**
 * @author zhengqiang.shi
 * 2015年4月16日 上午11:06:15
 */
public class CustomerOrderCouponsVO implements Serializable{

	private static final long serialVersionUID = -130210202068925438L;
	
	// key:skuId,value:price
	private Map<Long, BigDecimal> findCouponsMap = new HashMap<Long, BigDecimal>();

	// key:index,value:skuId_price,skuId_price
	private Map<String, String> findCouponsQtyMap = new HashMap<String, String>();
	
	// 优惠券ID
	private Long couponstockId;
	
	@NotNull(message="{CCIGMALL-100000.filed}",groups={orderFindCouponsChecks.class,orderGetDiscountCouponPriceChecks.class})
	@Size(min=1,message="{CCIGMALL-100001.filed}",groups={orderFindCouponsChecks.class,orderGetDiscountCouponPriceChecks.class})
	public Map<Long, BigDecimal> getFindCouponsMap() {
		return findCouponsMap;
	}

	public void setFindCouponsMap(Map<Long, BigDecimal> findCouponsMap) {
		this.findCouponsMap = findCouponsMap;
	}

	@NotNull(message="{CCIGMALL-100000.filed}",groups={orderFindCouponsQtyChecks.class})
	@Size(min=1,message="{CCIGMALL-100001.filed}",groups={orderFindCouponsQtyChecks.class})
	public Map<String, String> getFindCouponsQtyMap() {
		return findCouponsQtyMap;
	}

	public void setFindCouponsQtyMap(Map<String, String> findCouponsQtyMap) {
		this.findCouponsQtyMap = findCouponsQtyMap;
	}

	@NotNull(message="{CCIGMALL-100000.filed}",groups={orderGetDiscountCouponPriceChecks.class})
	public Long getCouponstockId() {
		return couponstockId;
	}

	public void setCouponstockId(Long couponstockId) {
		this.couponstockId = couponstockId;
	}
	
}
