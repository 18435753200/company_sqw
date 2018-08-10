package com.mall.wap.proxy;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.api.rpc.CategoryDispService;
import com.mall.comment.api.rpc.CommentService;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.service.ReceiveAddressService;
import com.mall.customer.service.UserService;
import com.mall.dealer.product.api.DealerProductSkuService;
import com.mall.dealer.product.customer.api.CustomerProductApi;
import com.mall.dealer.product.customer.api.CustomerPromotionApi;
import com.mall.dealer.product.customer.api.SearchHotWordApi;
import com.mall.dsearch.api.HotService;
import com.mall.dsearch.api.SearchService;
import com.mall.dsearch.api.SuggestionService;
import com.mall.pay.api.rpc.BankPayService;
import com.mall.platform.service.PlatformActivityService;
import com.mall.retailer.order.api.rpc.RetailerShipOrderService;
import com.mall.stock.api.rpc.StockCustomerService;
import com.mall.stock.api.rpc.StockService;

/**
 * 代理
 * 
 * @author yuanxiayang
 *
 */
public class RemoteServiceSingleton {

	private static final Logger LOGGER = Logger
			.getLogger(RemoteServiceSingleton.class);

	private static ApplicationContext context;

	static class RemoteServiceSingletonHolder {
		static RemoteServiceSingleton instance = new RemoteServiceSingleton();
		static {
			context = new ClassPathXmlApplicationContext(
					new String[] { "remoting.xml" });
		}
	}

	public static RemoteServiceSingleton getInstance() {
		return RemoteServiceSingletonHolder.instance;
	}

	/************************ 请修改以下方法 **************************/
	// 0元购发券接口
	/*	@SuppressWarnings("finally")
		public UserActivityApi getZeroPayActivity() {
			UserActivityApi userActivityApi = null;
			try {
				userActivityApi = (UserActivityApi) context
						.getBean("zeroPayActivity");
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				return userActivityApi;
			}
		}*/

	// 商品接口
	@SuppressWarnings("finally")
	public CustomerProductApi getCustomerProductApi() {
		CustomerProductApi customerProductApi = null;
		try {
			customerProductApi = (CustomerProductApi) context
					.getBean("customerProductApi");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return customerProductApi;
		}
	}
	// 商品接口1
	@SuppressWarnings("finally")
	public DealerProductSkuService getDealerProductSkuService() {
		DealerProductSkuService dealerProductSkuService = null;
		try {
			dealerProductSkuService = (DealerProductSkuService) context
					.getBean("dealerProductSkuService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return dealerProductSkuService;
		}
	}
	// 商品接口2
	@SuppressWarnings("finally")
	public CustomerPromotionApi getCustomerPromotionApi() {
		CustomerPromotionApi customerPromotionApi = null;
		try {
			customerPromotionApi = (CustomerPromotionApi) context
					.getBean("customerPromotionApi");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return customerPromotionApi;
		}
	}
	/*@SuppressWarnings("finally")
	public ProductByPromotionInfoService getProductByPromotionInfoService() {
		ProductByPromotionInfoService productByPromotionInfoService = null;
		try {
			productByPromotionInfoService = (ProductByPromotionInfoService) context.getBean("productByPromotionInfoService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return productByPromotionInfoService;
		}
	}*/
	// 热门搜索接口
	@SuppressWarnings("finally")
	public SearchHotWordApi getSearchHotWordApi() {
		SearchHotWordApi searchHotWordApi = null;
		try {
			searchHotWordApi = (SearchHotWordApi) context
					.getBean("searchHotWordApi");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return searchHotWordApi;
		}
	}

	// 获取原产地
	@SuppressWarnings("finally")
	public BaseDataServiceRpc getBaseDataServiceRpcApi() {
		BaseDataServiceRpc baseDataServiceRpc = null;
		try {
			baseDataServiceRpc = (BaseDataServiceRpc) context
					.getBean("baseDataServiceRpc");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return baseDataServiceRpc;
		}
	}

	// 类目接口
	@SuppressWarnings("finally")
	public CategoryDispService getCategoryDispApi() {
		CategoryDispService categoryDispService = null;
		try {
			categoryDispService = (CategoryDispService) context
					.getBean("categoryDispService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return categoryDispService;
		}
	}

	// 搜索接口
	@SuppressWarnings("finally")
	public SearchService getSearchApi() {
		SearchService searchService = null;
		try {
			searchService = (SearchService) context.getBean("searchService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return searchService;
		}
	}

	// 评价接口
	@SuppressWarnings("finally")
	public CommentService getCommentApi() {
		CommentService commentService = null;
		try {
			commentService = (CommentService) context.getBean("commentService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return commentService;
		}
	}

	// cms接口
//	@SuppressWarnings("finally")
//	public CustomerCmsService getCmsServiceApi() {
//		CustomerCmsService customerCmsService = null;
//		try {
//			customerCmsService = (CustomerCmsService) context
//					.getBean("cmsService");
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//		} finally {
//			return customerCmsService;
//		}
//	}

	// 用户接口接口
	@SuppressWarnings("finally")
	public UserService getUserService() {
		UserService userService = null;
		try {
			userService = (UserService) context.getBean("userService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return userService;
		}
	}

	// 订单接口
	@SuppressWarnings("finally")
	public CustomerOrderService getCustomerOrderService() {
		CustomerOrderService customerOrderService = null;
		try {
			customerOrderService = (CustomerOrderService) context
					.getBean("customerOrderService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return customerOrderService;
		}
	}

	// 地址接口
	@SuppressWarnings("finally")
	public ReceiveAddressService getReceiveAddressService() {
		ReceiveAddressService receiveAddressService = null;
		try {
			receiveAddressService = (ReceiveAddressService) context
					.getBean("receiveAddressService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return receiveAddressService;
		}
	}

	// 搜索关键字提示
	@SuppressWarnings("finally")
	public SuggestionService getSuggestionApi() {
		SuggestionService suggestionService = null;
		try {
			suggestionService = (SuggestionService) context
					.getBean("suggestionService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return suggestionService;
		}
	}
	
	// 搜索关键字提示
	@SuppressWarnings("finally")
	public HotService getHotService() {
		HotService hotService = null;
		try {
			hotService = (HotService) context
					.getBean("hotService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return hotService;
		}
	}

	// 促销相关服务
/*	@SuppressWarnings("finally")
	public CouponB2CService getCouponB2CService() {
		CouponB2CService couponB2CService = null;
		try {
			couponB2CService = (CouponB2CService) context
					.getBean("couponB2CService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return couponB2CService;
		}
	}*/
	
	/*@SuppressWarnings("finally")
	public UserCouponRuleApi getUserCouponRuleApi() {
		UserCouponRuleApi userCouponRuleApi = null;
		try {
			userCouponRuleApi = (UserCouponRuleApi) context
					.getBean("userCouponRuleApi");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return userCouponRuleApi;
		}
	}*/

	// 库存
	@SuppressWarnings("finally")
	public StockCustomerService getStockCustomerService() {
		StockCustomerService stockCustomerService = null;
		try {
			stockCustomerService = (StockCustomerService) context
					.getBean("stockCustomerService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return stockCustomerService;
		}
	}
	// 库存赠品
	@SuppressWarnings("finally")
	public StockService getStockGiftCustomerService() {
		StockService stockService = null;
		try {
			stockService = (StockService) context
					.getBean("stockGiftCustomerService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return stockService;
		}
	}
	
	

	// 促销信息
	/*@SuppressWarnings("finally")
	public RuleEngineApi getRuleEngineApi() {
		RuleEngineApi ruleEngineApi = null;
		try {
			ruleEngineApi = (RuleEngineApi) context.getBean("ruleEngineApi");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return ruleEngineApi;
		}
	}*/

	// 支付相关服务
	@SuppressWarnings("finally")
	public BankPayService getBankPayService() {
		BankPayService bankPayService = null;
		try {
			bankPayService = (BankPayService) context.getBean("bankPayService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return bankPayService;
		}
	}

	// 订单物流包裹信息
	@SuppressWarnings("finally")
	public RetailerShipOrderService getRetailerShipOrderService() {
		RetailerShipOrderService retailerShipOrderService = null;
		try {
			retailerShipOrderService = (RetailerShipOrderService) context
					.getBean("retailerShipOrderService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return retailerShipOrderService;
		}
	}

	// 订单物流包裹信息
//	@SuppressWarnings("finally")
//	public SingleServiceRPC getSingleServiceRPC() {
//		SingleServiceRPC singleServiceRPC = null;
//		try {
//			singleServiceRPC = (SingleServiceRPC) context
//					.getBean("singleServiceRPC");
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//		} finally {
//			return singleServiceRPC;
//		}
//	}
	
	public PlatformActivityService getPlatformActivityService() {
		PlatformActivityService platformActivityService = null;
		try {
			platformActivityService = (PlatformActivityService) context.getBean("platformActivityService");
		} catch (Exception e) {
			LOGGER.error("调用APP活动PlatformActivityService的bean错误:  msg:" + e.getMessage(), e);
		}
		return platformActivityService;
    }
	
	

}
