package com.mall.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.alibaba.dubbo.container.page.Page;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerCartService;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.dealer.product.dto.DealerProductSkuForPromotionDTO;
import com.mall.dsearch.vo.PlainProduct;
import com.mall.mybatis.utility.PageBean;
import com.mall.pay.common.StringUtils;
import com.mall.platform.model.PlatformActivityProduct;
/*import com.mall.promotion.api.coupon.b2c.CouponB2CService;
import com.mall.promotion.dto.activity.PromotionPriceDto;
import com.mall.promotion.dto.coupon.MyCouponStockB2CDTO;*/
import com.mall.utils.Constants;
import com.mall.wap.proxy.RemoteServiceSingleton;

import net.rubyeye.xmemcached.MemcachedClient;

/**
 * 优惠券的服务
 * 
 * @author ccigQA01
 * 
 */
@Service
public class CusCouponService {
	private static final Logger log = LoggerFactory
			.getLogger(CusCouponService.class);

	@Autowired
	private MemcachedClient memcachedClient;
	
	//private CouponB2CService couponB2CService;

	 
	/**
	 *获取优惠券服务
	 * 
	 * @return
	 */
	/*private CouponB2CService getCouponB2CService() {
		return RemoteServiceSingleton.getInstance().getCouponB2CService();
	}*/

	/**
	 * 获取优惠券
	 * @param type
	 * @param currentUser 
	 * @param model
	 * @param request
	 * @param response
	 */
/*	public List<MyCouponStockB2CDTO>  getCounponByType(String type, User currentUser, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		couponB2CService = getCouponB2CService();
//		userid
//		identification  
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("userid", currentUser.getUserId());
		if(StringUtils.isEmpty(type)){
			map.put("identification", 0L);
		}else{
		map.put("identification", Long.parseLong(type));
		}
		List<MyCouponStockB2CDTO> coupons = couponB2CService.findMyCouponStockByUserid(map);
//		List<MyCouponStockB2CDTO> coupons =new ArrayList<MyCouponStockB2CDTO>();
//		for (int i = 0; i < 10; i++) {
//			MyCouponStockB2CDTO b2cdto=new MyCouponStockB2CDTO();
//			b2cdto.setCouponstockid((long) i);
//			b2cdto.setCouponType((long) new Random().nextInt(3));
//			b2cdto.setEndTime(new Date());
//			b2cdto.setOrderLimitPrice(new BigDecimal(100*i));
//			b2cdto.setPrice(new BigDecimal(i*50));
//			b2cdto.setRuleid((long) i);
//			b2cdto.setStartTime(new Date());
//			b2cdto.setUseType((long) new Random().nextInt(4));
//			List<String> limitName=new ArrayList<String>();
//			if(i%2==0){
//				limitName.add("全场");
//			}
//			else if(i%3==0){
//				limitName.add("品牌");
//			}else{
//				limitName.add("sku");
//			}
////						0 全场 1.类目 2.品牌 3.sku
//			b2cdto.setLimitName(limitName);
//			coupons.add(b2cdto);
//		}
		model.addAttribute("coupons", coupons);
		model.addAttribute("type", type);
		return coupons;
	}*/
	
	//列表页显示促销价       根据商品ID获取第一个单品的折扣
/*	public Map<Long,BigDecimal> getPromotionPriceByPids(List<Long> productIds,Map<Long,Long> proSku, List<PlainProduct> items){
		//couponB2CService = getCouponB2CService();
		//根据商品id获取单品id
		List<DealerProductSkuForPromotionDTO> proInfos = RemoteServiceSingleton.getInstance().getDealerProductSkuService().findProSkuByPids(productIds);
		List<Long> skuIds = new ArrayList<Long>();
		Map<Long,List<DealerProductSkuForPromotionDTO>> proMap = new HashMap<Long, List<DealerProductSkuForPromotionDTO>>();
		for(DealerProductSkuForPromotionDTO dealerProductSkuForPromotionDTO : proInfos){
			Long productId = dealerProductSkuForPromotionDTO.getProductId();
			if(null == proMap.get(productId)){
				List<DealerProductSkuForPromotionDTO> listBak = new ArrayList<DealerProductSkuForPromotionDTO>(); 
				listBak.add(dealerProductSkuForPromotionDTO);
				proMap.put(productId, listBak);
			}else{
				proMap.get(productId).add(dealerProductSkuForPromotionDTO);
			}
		}
		Set<Long> keySet = proMap.keySet();
		for (int i = 0;i < productIds.size();i++) {
			List<DealerProductSkuForPromotionDTO> list = proMap.get(productIds.get(i));
			for(DealerProductSkuForPromotionDTO dealerProductSkuForPromotionDTO : list){
				if(items.get(i).getUnit_price().compareTo(dealerProductSkuForPromotionDTO.getUnitPrice()) == 0){
					Long skuId = dealerProductSkuForPromotionDTO.getSkuId();
					skuIds.add(skuId);
					proSku.put(productIds.get(i), skuId);
					break;
				}
			}
		}
		// 根据单品获取单品的优惠价格
		List<PromotionPriceDto> promotionPriceDtos = RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().getPromotionPriceBySkuId(skuIds, Constants.B2C_FLAG, Constants.ACCESSMODE_WAP);
		Map<Long,BigDecimal> priceMap = new HashMap<Long,BigDecimal>();
		for (PromotionPriceDto promotionPriceDto : promotionPriceDtos) {
			priceMap.put(promotionPriceDto.getSkuId(), promotionPriceDto.getDiscountPrice());
		}
		return priceMap;
	}*/
	
	//列表页显示促销价       根据商品ID获取第一个单品的折扣
	public Map<Long,BigDecimal> getPromotionPriceByPids1(List<Long> productIds,Map<Long,Long> proSku, List<PlatformActivityProduct> items){
		//根据商品id获取单品id
		List<DealerProductSkuForPromotionDTO> proInfos = RemoteServiceSingleton.getInstance().getDealerProductSkuService().findProSkuByPids(productIds);
		List<Long> skuIds = new ArrayList<Long>();
		Map<Long,List<DealerProductSkuForPromotionDTO>> proMap = new HashMap<Long, List<DealerProductSkuForPromotionDTO>>();
		Map<Long,BigDecimal> priceMap = new HashMap<Long,BigDecimal>();
		if(null != proInfos){
			for(DealerProductSkuForPromotionDTO dealerProductSkuForPromotionDTO : proInfos){
				Long productId = dealerProductSkuForPromotionDTO.getProductId();
				if(null == proMap.get(productId)){
					List<DealerProductSkuForPromotionDTO> listBak = new ArrayList<DealerProductSkuForPromotionDTO>(); 
					listBak.add(dealerProductSkuForPromotionDTO);
					proMap.put(productId, listBak);
				}else{
					proMap.get(productId).add(dealerProductSkuForPromotionDTO);
				}
			}
			Set<Long> keySet = proMap.keySet();
			for (int i = 0;i < productIds.size();i++) {
				List<DealerProductSkuForPromotionDTO> list = proMap.get(productIds.get(i));
				for(DealerProductSkuForPromotionDTO dealerProductSkuForPromotionDTO : list){
					if(null != items.get(i).getProductPrice()){
						if(items.get(i).getProductPrice().compareTo(dealerProductSkuForPromotionDTO.getUnitPrice()) == 0){
							Long skuId = dealerProductSkuForPromotionDTO.getSkuId();
							skuIds.add(skuId);
							log.info("skuskusku======"+skuId);
							proSku.put(productIds.get(i), skuId);
							break;
						}
					}
				}
			}
			log.info("封装单品参数：skuIds------" + skuIds);
			// 根据单品获取单品的优惠价格
			log.info("调用促销价格方法开始");
			/*List<PromotionPriceDto> promotionPriceDtos = RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().getPromotionPriceBySkuId(skuIds, Constants.B2C_FLAG, Constants.ACCESSMODE_WAP);
			
			for (PromotionPriceDto promotionPriceDto : promotionPriceDtos) {
				priceMap.put(promotionPriceDto.getSkuId(), promotionPriceDto.getDiscountPrice());
			}*/
			log.info("调用促销价格方法结束");
		}
		return priceMap;
	}
	
	//列表页显示促销价       根据商品ID获取第一个单品的折扣
	public Map<Long,BigDecimal> getPromotionPriceByPids2(List<Long> productIds,Map<Long,Long> proSku, List<PlainProduct> items,String type){
		//根据商品id获取单品id
		Map<Long,BigDecimal> priceMap = new HashMap<Long,BigDecimal>();
		List<DealerProductSkuForPromotionDTO> proInfos = RemoteServiceSingleton.getInstance().getDealerProductSkuService().findProSkuByPids(productIds);
		List<Long> skuIds = new ArrayList<Long>();
		Map<Long,List<DealerProductSkuForPromotionDTO>> proMap = new HashMap<Long, List<DealerProductSkuForPromotionDTO>>();
		if(null != proInfos){
			for(DealerProductSkuForPromotionDTO dealerProductSkuForPromotionDTO : proInfos){
				Long productId = dealerProductSkuForPromotionDTO.getProductId();
				log.info("productid---------------------" + productId);
				if(null == proMap.get(productId)){
					List<DealerProductSkuForPromotionDTO> listBak = new ArrayList<DealerProductSkuForPromotionDTO>(); 
					listBak.add(dealerProductSkuForPromotionDTO);
					proMap.put(productId, listBak);
				}else{
					proMap.get(productId).add(dealerProductSkuForPromotionDTO);
				}
			}
			log.info("开始封装商品——————单品");
			for (int i = 0;i < productIds.size();i++) {
				List<DealerProductSkuForPromotionDTO> list = proMap.get(productIds.get(i));
				if(null != list){
					for(DealerProductSkuForPromotionDTO dealerProductSkuForPromotionDTO : list){
						if(null != items.get(i).getUnit_price()){
							if(items.get(i).getUnit_price().compareTo(dealerProductSkuForPromotionDTO.getUnitPrice()) == 0){
								Long skuId = dealerProductSkuForPromotionDTO.getSkuId();
								skuIds.add(skuId);
								proSku.put(productIds.get(i), skuId);
								break;
							}
						}
					}
				}
			}
			log.info("skuSIZE" + skuIds.size());
			// 根据单品获取单品的优惠价格
		/*	if(skuIds.size() > 0){
				List<PromotionPriceDto> promotionPriceDtos = null;
				if("2".equals(type)){
					log.info("WAP首页begin");
					promotionPriceDtos = RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().getPromotionPriceBySkuId(skuIds, Constants.B2C_FLAG, Constants.ACCESSMODE_WAP);
					log.info("WAP首页end");
				}else if("1".equals(type)){
					log.info("PC首页begin");
					promotionPriceDtos = RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().getPromotionPriceBySkuId(skuIds, Constants.B2C_FLAG, Constants.ACCESSMODE_PC);
					log.info("PC首页end");
				}else if("3".equals(type)){
					log.info("APP首页begin");
					promotionPriceDtos = RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().getPromotionPriceBySkuId(skuIds, Constants.B2C_FLAG, Constants.ACCESSMODE_APP);
					log.info("APP首页end");
				}
				
				for (PromotionPriceDto promotionPriceDto : promotionPriceDtos) {
					priceMap.put(promotionPriceDto.getSkuId(), promotionPriceDto.getDiscountPrice());
				}
			}*/
		}
		return priceMap;
	}

}
