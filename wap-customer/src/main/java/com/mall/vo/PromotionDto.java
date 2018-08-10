package com.mall.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情页促销使用
 * @author yanlei
 *
 */
public class PromotionDto implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // skuId
    private String skuId ;
    // 满减
    private List<FullReduction> fullReductions = new ArrayList<FullReduction>();
    // 满增
    private List<WithIncreasing> withIncreasings = new ArrayList<WithIncreasing>();
    // 直降
    private List<PriceDown> priceDowns = new ArrayList<PriceDown>();
    // 满返
    private List<Fullback> fullbacks = new ArrayList<Fullback>();
    // 买增
    private List<Buy> buys= new ArrayList<Buy>();
   
    // 系统时间
    private String newDate = "";

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public List<FullReduction> getFullReductions() {
		return fullReductions;
	}

	public void setFullReductions(List<FullReduction> fullReductions) {
		this.fullReductions = fullReductions;
	}

	public List<WithIncreasing> getWithIncreasings() {
		return withIncreasings;
	}

	public void setWithIncreasings(List<WithIncreasing> withIncreasings) {
		this.withIncreasings = withIncreasings;
	}

	public List<PriceDown> getPriceDowns() {
		return priceDowns;
	}

	public void setPriceDowns(List<PriceDown> priceDowns) {
		this.priceDowns = priceDowns;
	}

	public List<Fullback> getFullbacks() {
		return fullbacks;
	}

	public void setFullbacks(List<Fullback> fullbacks) {
		this.fullbacks = fullbacks;
	}

	public List<Buy> getBuys() {
		return buys;
	}

	public void setBuys(List<Buy> buys) {
		this.buys = buys;
	}

	public String getNewDate() {
		return newDate;
	}

	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}
    
    
}
