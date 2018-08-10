/**
 * 
 */
package com.mall.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.mall.check.CartChecks.addCartCheck;
import com.mall.check.CartChecks.calculateCheck;
import com.mall.check.CartChecks.deleteCartCheck;
import com.mall.check.CartChecks.directBuyCheck;
import com.mall.check.CartChecks.settlementCheck;
import com.mall.check.CartChecks.updateCartCheck;


/**
 * @author zhengqiang.shi
 * 2015年3月27日 下午2:29:12
 */
public class CustomerCartVO implements Serializable{

	private static final long serialVersionUID = 4523107618421619814L;

	// 商品规格ID
	private Long skuId;
	
	// 购买数量
	private BigDecimal number;

	// 商品规格ID集合
	private List<Long> skuIdList;
	
	//入驻区域（字典：1 自营、2 孵化、3 高新）
	private String companyRegion;
	
	// 优惠券，格式为productType_couponId;productType_couponId
	private String promotion;
	
	@NotNull(message="{CCIGMALL-100000.filed}",groups={addCartCheck.class,updateCartCheck.class,directBuyCheck.class})
	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	
	@NotNull(message="{CCIGMALL-100000.filed}",groups={addCartCheck.class,updateCartCheck.class,directBuyCheck.class})
	@Range(min=1,max=1073741823,message="{CCIGMALL-100001.filed}",groups={addCartCheck.class,updateCartCheck.class,directBuyCheck.class})
	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	@NotNull(message="{CCIGMALL-100000.filed}",groups={settlementCheck.class,deleteCartCheck.class})
	@Size(min=1,message="{CCIGMALL-100001.filed}",groups={settlementCheck.class,deleteCartCheck.class})
	public List<Long> getSkuIdList() {
		return skuIdList;
	}

	public void setSkuIdList(List<Long> skuIdList) {
		this.skuIdList = skuIdList;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public String getCompanyRegion() {
		return companyRegion;
	}

	public void setCompanyRegion(String companyRegion) {
		this.companyRegion = companyRegion;
	}
	
}
