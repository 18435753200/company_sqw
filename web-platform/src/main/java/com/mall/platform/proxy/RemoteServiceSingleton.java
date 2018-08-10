package com.mall.platform.proxy;

import com.mall.authority.client.AuthorityClient;
import com.mall.authority.client.impl.AuthorityClientImpl;
import com.mall.category.api.own.MyBrandService;
import com.mall.category.api.own.MyCategoryDispService;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.api.rpc.BrandServiceRpc;
import com.mall.category.api.rpc.CategoryDispService;
import com.mall.category.api.rpc.CategoryServiceRpc;
import com.mall.comment.api.rpc.CommentService;
import com.mall.customer.order.api.rpc.*;
import com.mall.customer.service.*;
//import com.mall.dealer.order.api.rpc.DealerOrderService;
import com.mall.dealer.product.api.*;
import com.mall.dealer.product.customer.api.CustomerProductApi;
import com.mall.dealer.product.customer.api.CustomerPromotionApi;
import com.mall.dealer.product.customer.api.SearchHotWordApi;
import com.mall.dealer.product.retailer.api.LogisticTempService;
import com.mall.dealer.product.retailer.api.PlatformLogisticTempService;
import com.mall.dealer.product.retailer.api.RetailerProductService;
//import com.mall.dealer.service.DealerFileService;
//import com.mall.dealer.service.DealerService;
//import com.mall.dealer.service.UserManagerService;
//import com.mall.external.service.RechargeService;
//import com.mall.financial.api.own.MyFinancStockService;
import com.mall.platform.service.*;
/*import com.mall.promotion.api.activity.coupon.ActivityBundlingCouponApi;
import com.mall.promotion.api.activity.user.UserActivityApi;
import com.mall.promotion.api.activity.web.*;
import com.mall.promotion.api.coupon.user.UserCouponRuleApi;
import com.mall.promotion.api.coupon.web.CouponApi;
import com.mall.promotion.api.coupon.web.CouponRuleApi;
import com.mall.promotion.api.droolsApi.CollectSendCouponInfoApi;*/
import com.mall.retailer.order.api.rpc.RetailerOrderExportService;
import com.mall.retailer.order.api.rpc.RetailerOrderService;
import com.mall.retailer.order.api.rpc.RetailerOrderpayService;
import com.mall.retailer.order.api.rpc.RetailerShipOrderService;
//import com.mall.retailer.service.ConfirmOrderAndPayService;
//import com.mall.retailer.service.RetailerManagerService;
//import com.mall.retailer.service.RetailerUserManagerService;
import com.mall.stock.api.rpc.*;
//import com.mall.supplier.order.api.rpc.*;
import com.mall.supplier.product.api.SupplierProductOrderService;
import com.mall.supplier.product.api.SupplierProductService;
import com.mall.supplier.service.*;
//import com.mall.wms.api.OrderSCancelService;
//import com.mall.wms.api.OutOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhouzb 接口代理.
 * 
 */
public class RemoteServiceSingleton {

	/**
	 * LOGGER.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteServiceSingleton.class);

	/**
	 * context ClassPathXmlApplicationContext.
	 */
	private static ApplicationContext context;

	/**
	 * @author zhouzb
	 * 
	 */
	static class RemoteServiceSingletonHolder {
		/**
		 * 
		 */
		static RemoteServiceSingleton instance = new RemoteServiceSingleton();
		static {
			context = new ClassPathXmlApplicationContext(new String[] { "remoting.xml" });
		}
	}

	/**
	 * @return RemoteServiceSingleton 接口代理实例
	 */
	public static RemoteServiceSingleton getInstance() {
		return RemoteServiceSingletonHolder.instance;
	}

	/**
	 * . （买家/卖家）采购订单接口(dealerOrderService)
	 * 
	 * @return DealerOrderService
	 */

	/*public DealerOrderService getDealerOrderService() {
		DealerOrderService dealerOrderService = null;
		try {
			dealerOrderService = (DealerOrderService) context.getBean("dealerOrderService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return dealerOrderService;
	}*/
	
	public DealerProductSkuService getDealerProductSkuService() {
		DealerProductSkuService dealerProductSkuService = null;
		try {
			dealerProductSkuService = (DealerProductSkuService) context.getBean("dealerProductSkuService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return dealerProductSkuService;
	}

	/**
	 * . （买家）经销商接口(dealerService)
	 * 
	 * @return DealerService
	 */

	/*public DealerService getDealerService() {
		DealerService dealerService = null;
		try {
			dealerService = (DealerService) context.getBean("dealerService");
		} catch (Exception e) {
			LOGGER.error("调用经销商接口bean错误:  msg:" + e.getMessage(), e);
		}
		return dealerService;
	}*/

	/**
	 * . （
	 * 
	 * @return DealerService
	 */

	/*public UserManagerService getUserManagerService() {
		UserManagerService userManagerService = null;
		try {
			userManagerService = (UserManagerService) context.getBean("userManagerService");
		} catch (Exception e) {
			LOGGER.error("调用经销商接口bean错误:  msg:" + e.getMessage(), e);
		}
		return userManagerService;
	}*/

	/**
	 * . （买家）经销商接口(dealerService)
	 * 
	 * @return DealerService
	 */
	/*@SuppressWarnings("finally")
	public DealerFileService getDealerFileService() {
		DealerFileService dealerFileService = null;
		try {
			dealerFileService = (DealerFileService) context.getBean("dealerFileService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return dealerFileService;
		}
	}*/

	/**
	 * . (商品展示类目使用查询categoryDispService)
	 * 
	 * @return CategoryDispService
	 */

	public CategoryDispService getCategoryDispService() {
		CategoryDispService categoryDispService = null;
		try {
			categoryDispService = (CategoryDispService) context.getBean("categoryDispService");
		} catch (Exception e) {
			LOGGER.error("调用商品展示类目接口bean错误:  msg:" + e.getMessage(), e);
		}
		return categoryDispService;
	}
	
	public MyCategoryDispService getMyCategoryDispService() {
		MyCategoryDispService myCategoryDispService = null;
		try {
			myCategoryDispService = (MyCategoryDispService) context.getBean("myCategoryDispService");
		} catch (Exception e) {
			LOGGER.error("调用商品展示类目接口bean错误:  msg:" + e.getMessage(), e);
		}
		return myCategoryDispService;
	}

	/**
	 * . (商品发布类目使用模板categoryServiceRpc)
	 * 
	 * @return CategoryServiceRpc
	 */

	public CategoryServiceRpc getCategoryServiceRpc() {
		CategoryServiceRpc categoryServiceRpc = null;
		try {
			categoryServiceRpc = (CategoryServiceRpc) context.getBean("categoryServiceRpc");
		} catch (Exception e) {
			LOGGER.error("调用商品发布类目接口bean错误:  msg:" + e.getMessage(), e);
		}
		return categoryServiceRpc;
	}

	/**
	 * . (商品信息dealerProductService)
	 * 
	 * @return DealerProductService
	 */

	public DealerProductService getDealerProductService() {
		DealerProductService dealerProductService = null;
		try {
			dealerProductService = (DealerProductService) context.getBean("dealerProductService");
		} catch (Exception e) {
			LOGGER.error("调用商品信息接口bean错误:  msg:" + e.getMessage(), e);
		}
		return dealerProductService;
	}
	
	/**
	 * . (商品信息dealerProductService)
	 * 
	 * @return DealerProductService
	 */

	public DealerProductTopicService getDealerProductTopicService() {
		DealerProductTopicService dealerProductTopicService = null;
		try {
			dealerProductTopicService = (DealerProductTopicService) context.getBean("dealerProductTopicService");
		} catch (Exception e) {
			LOGGER.error("调用商品信息接口bean错误:  msg:" + e.getMessage(), e);
		}
		return dealerProductTopicService;
	}
	
	/**
	 * . (热词信息dealerProductService)
	 * 
	 * @return DealerProductService
	 */

	public SearchHotWordApi getSearchHotWordApi() {
		SearchHotWordApi searchHotWordApi = null;
		try {
			searchHotWordApi = (SearchHotWordApi) context.getBean("searchHotWordApi");
		} catch (Exception e) {
			LOGGER.error("调用商品信息接口bean错误:  msg:" + e.getMessage(), e);
		}
		return searchHotWordApi;
	}

	/**
	 * . (baseDataServiceRpc)
	 * 
	 * @return BaseDataServiceRpc
	 */

	public BaseDataServiceRpc getBaseDataServiceRpc() {
		BaseDataServiceRpc baseDataServiceRpc = null;
		try {
			baseDataServiceRpc = (BaseDataServiceRpc) context.getBean("baseDataServiceRpc");
		} catch (Exception e) {
			LOGGER.error("调用基础信息接口bean错误:  msg:" + e.getMessage(), e);
		}
		return baseDataServiceRpc;
	}

	/**
	 * . (supplierManagerService)
	 * 
	 * @return SupplierManagerService
	 */

	public SupplierManagerService getSupplierManagerService() {
		SupplierManagerService supplierManagerService = null;
		try {
			supplierManagerService = (SupplierManagerService) context.getBean("supplierManagerService");
		} catch (Exception e) {
			LOGGER.error("调用供应商商户接口bean错误:  msg:" + e.getMessage(), e);
		}
		return supplierManagerService;
	}

	public SupplierRegionService getSupplierRegionService() {
		SupplierRegionService supplierRegionService = null;
		try {
			supplierRegionService = (SupplierRegionService) context.getBean("supplierRegionService");
		} catch (Exception e) {
			LOGGER.error("调用供应商商户接口bean错误:  msg:" + e.getMessage(), e);
		}
		return supplierRegionService;
	}

	public SupplierUserManagerService getSupplierUserManagerService() {
		SupplierUserManagerService supplierUserManagerService = null;
		try {
			supplierUserManagerService = (SupplierUserManagerService) context.getBean("supplierUserManagerService");
		} catch (Exception e) {
			LOGGER.error("调用供应商用户接口bean错误:  msg:" + e.getMessage(), e);
		}
		return supplierUserManagerService;
	}

	/**
	 * . (买家)代理经销商列表(dealerProductAuthorizeService)
	 * 
	 * @return DealerProductAuthorizeService
	 */

	public DealerProductAuthorizeService getDealerProductAuthorizeService() {
		DealerProductAuthorizeService dealerProductAuthorizeService = null;
		try {
			dealerProductAuthorizeService = (DealerProductAuthorizeService) context.getBean("dealerProductAuthorizeService");
		} catch (Exception e) {
			LOGGER.error("调用经销商代理接口bean错误:  msg:" + e.getMessage(), e);
		}
		return dealerProductAuthorizeService;
	}

	/**
	 * . (平台用户管理)
	 * 
	 * @return PlatformUserManagerService
	 */

	public PlatformUserManagerService getPlatformUserManagerService() {
		PlatformUserManagerService platformUserManagerService = null;
		try {
			platformUserManagerService = (PlatformUserManagerService) context.getBean("platformUserManagerService");
		} catch (Exception e) {
			LOGGER.error("调用平台信息接口bean错误:  msg:" + e.getMessage(), e);
		}
		return platformUserManagerService;
	}

	/**
	 * . (平台商户管理)
	 * 
	 * @return PlatformManagerService
	 */

	public PlatformManagerService getPlatformManagerService() {
		PlatformManagerService platformManagerService = null;
		try {
			platformManagerService = (PlatformManagerService) context.getBean("platformManagerService");
		} catch (Exception e) {
			LOGGER.error("调用平台商户管理接口bean错误:  msg:" + e.getMessage(), e);
		}
		return platformManagerService;
	}

	/**
	 * . (平台权限管理)
	 * 
	 * @return PlatformRoleManagerService
	 */

	public PlatformRoleManagerService getPlatformRoleManagerService() {
		PlatformRoleManagerService platformRoleManagerService = null;
		try {
			platformRoleManagerService = (PlatformRoleManagerService) context.getBean("platformRoleManagerService");
		} catch (Exception e) {
			LOGGER.error("调用平台权限接口bean错误:  msg:" + e.getMessage(), e);
		}
		return platformRoleManagerService;
	}

	/**
	 * . (订单详情获取零售商详细接口)
	 * 
	 * @return RetailerManagerService
	 */

	/*public RetailerManagerService getRetailerManagerService() {
		RetailerManagerService retailerManagerService = null;
		try {
			retailerManagerService = (RetailerManagerService) context.getBean("retailerManagerService");
		} catch (Exception e) {
			LOGGER.error("调用订单详情获取零售商详细接口bean错误:  msg:" + e.getMessage(), e);
		}
		return retailerManagerService;
	}

	*//**
	 * . (订单详情获取零售商详细接口)
	 * 
	 * @return RetailerManagerService
	 *//*

	public RetailerUserManagerService getRetailerUserManagerService() {
		RetailerUserManagerService retailerUserManagerService = null;
		try {
			retailerUserManagerService = (RetailerUserManagerService) context.getBean("retailerUserManagerService");
		} catch (Exception e) {
			LOGGER.error("调用零售商用户bean错误:  msg:" + e.getMessage(), e);
		}
		return retailerUserManagerService;
	}*/

	/**
	 * . (平台 用户反馈)
	 * 
	 * @return PlatformComplaintService
	 */

	public PlatformComplaintService getPlatformComplaintService() {
		PlatformComplaintService platformComplaintService = null;
		try {
			platformComplaintService = (PlatformComplaintService) context.getBean("platformComplaintService");
		} catch (Exception e) {
			LOGGER.error("调用平台反馈接口bean错误:  msg:" + e.getMessage(), e);
		}
		return platformComplaintService;
	}

	/**
	 * . 零售商服务
	 * 
	 * @return ConfirmOrderAndPayService
	 */

	/*public ConfirmOrderAndPayService getConfirmOrderAndPayService() {
		ConfirmOrderAndPayService confirmOrderAndPayService = null;
		try {
			confirmOrderAndPayService = (ConfirmOrderAndPayService) context.getBean("confirmOrderAndPayService");
		} catch (Exception e) {
			LOGGER.error("调用零售商接口bean错误:  msg:" + e.getMessage(), e);
		}
		return confirmOrderAndPayService;
	}*/

	/**
	 * . 零售商服务
	 * 
	 * @return RetailerOrderpayService
	 */

	public RetailerOrderpayService getRetailerOrderpayService() {
		RetailerOrderpayService retailerOrderpayService = null;
		try {
			retailerOrderpayService = (RetailerOrderpayService) context.getBean("retailerOrderpayService");
		} catch (Exception e) {
			LOGGER.error("调用零售商接口bean错误:  msg:" + e.getMessage(), e);
		}
		return retailerOrderpayService;
	}

	/**
	 * . 现货订单分单
	 * 
	 * @return StockWofeService
	 */

	public StockWofeService getStockWofeService() {
		StockWofeService stockWofeService = null;
		try {
			stockWofeService = (StockWofeService) context.getBean("stockWofeService");
		} catch (Exception e) {
			LOGGER.error("调用订单分单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return stockWofeService;
	}

	/**
	 * @return StockDealerService
	 */

	public StockDealerService getStockDealerService() {
		StockDealerService stockDealerService = null;
		try {
			stockDealerService = (StockDealerService) context.getBean("stockDealerService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return stockDealerService;

	}

	/**
	 * 零售商订单接口
	 * 
	 * @return RetailerOrderService
	 */
	public RetailerOrderService getRetailerOrderService() {
		RetailerOrderService retailerOrderService = null;
		try {
			retailerOrderService = (RetailerOrderService) context.getBean("retailerOrderService");

		} catch (Exception e) {
			LOGGER.error("RetailerOrderService bean is not found" + e.getMessage(), e);
		}
		return retailerOrderService;

	}

	/**
	 * 供应商商品.
	 * 
	 * @return SupplierProductOrderService
	 */
	public SupplierProductOrderService getSupplierProductOrderService() {

		SupplierProductOrderService supplierProductOrderService = null;

		try {
			supplierProductOrderService = (SupplierProductOrderService) context.getBean("supplierProductOrderService");

		} catch (Exception e) {
			LOGGER.error("SupplierProductOrderService bean is not found" + e.getMessage(), e);
		}

		return supplierProductOrderService;

	}

	/**
	 * 供应商商品.
	 * 
	 * @return SupplierProductOrderService
	 */
	public SupplierProductService getSupplierProductService() {

		SupplierProductService supplierProductService = null;

		try {
			supplierProductService = (SupplierProductService) context.getBean("supplierProductService");

		} catch (Exception e) {
			LOGGER.error("SupplierProductOrderService bean is not found" + e.getMessage(), e);
		}

		return supplierProductService;

	}

	/**
	 * 经销商商品.
	 * 
	 * @return DealerProductOrderService
	 */
	public DealerProductOrderService getDealerProductOrderService() {

		DealerProductOrderService dealerProductOrderService = null;

		try {
			dealerProductOrderService = (DealerProductOrderService) context.getBean("dealerProductOrderService");

		} catch (Exception e) {
			LOGGER.error("dealerProductOrderService bean is not found" + e.getMessage(), e);
		}

		return dealerProductOrderService;

	}

	/**
	 * 注册送券
	 * 
	 * @return
	 */
	/*public UserActivityApi getUserActivityApi() {
		UserActivityApi userActivityApi = null;
		try {
			userActivityApi = (UserActivityApi) context.getBean("userActivityApi");
		} catch (Exception e) {
			LOGGER.error("调用注册送券接口bean错误:  msg:" + e.getMessage(), e);
		}
		return userActivityApi;
	}

	*//**
	 * 活动
	 * 
	 * @return
	 *//*
	public ActiveMasterApi getActiveMasterApi() {
		ActiveMasterApi activeMasterApi = null;
		try {
			activeMasterApi = (ActiveMasterApi) context.getBean("activeMasterApi");
		} catch (Exception e) {
			LOGGER.error("调用活动接口bean错误:  msg:" + e.getMessage(), e);
		}
		return activeMasterApi;
	}

	*//**
	 * 平台规则
	 * 
	 * @return
	 *//*
	public PlatformRuleApi getPlatformRuleApi() {
		PlatformRuleApi platformRuleApi = null;
		try {
			platformRuleApi = (PlatformRuleApi) context.getBean("platformRuleApi");
		} catch (Exception e) {
			LOGGER.error("调用平台规则接口bean错误:  msg:" + e.getMessage(), e);
		}
		return platformRuleApi;
	}

	*//**
	 * 赠品
	 * 
	 * @return
	 *//*
	public ActiveGiftApi getActiveGiftApi() {
		ActiveGiftApi activeGiftApi = null;
		try {
			activeGiftApi = (ActiveGiftApi) context.getBean("activeGiftApi");
		} catch (Exception e) {
			LOGGER.error("调用活动赠品接口bean错误:  msg:" + e.getMessage(), e);
		}
		return activeGiftApi;
	}

	*//**
	 * 券服务
	 * 
	 * @return
	 *//*
	public UserCouponRuleApi getUserCouponRuleApi() {
		UserCouponRuleApi userCouponRuleApi = null;
		try {
			userCouponRuleApi = (UserCouponRuleApi) context.getBean("userCouponRuleApi");
		} catch (Exception e) {
			LOGGER.error("调用券规则接口bean错误:  msg:" + e.getMessage(), e);
		}
		return userCouponRuleApi;
	}

	*//**
	 * 优惠券
	 * 
	 * @return CouponService
	 *//*
	public CouponApi getCouponService() {
		CouponApi couponApi = null;
		try {
			couponApi = (CouponApi) context.getBean("couponApi");
		} catch (Exception e) {
			LOGGER.error("CouponApi bean is not found" + e.getMessage(), e);
		}
		return couponApi;
	}

	*//**
	 * 优惠券使用规则
	 * 
	 * @return CouponRuleService
	 *//*
	public CouponRuleApi getCouponRuleService() {
		CouponRuleApi couponRuleApi = null;
		try {
			couponRuleApi = (CouponRuleApi) context.getBean("couponRuleApi");
		} catch (Exception e) {
			LOGGER.error("CouponRuleApi bean is not found" + e.getMessage(), e);
		}
		return couponRuleApi;
	}*/

	/**
	 * 销售管理
	 * 
	 * @return platformSaleManagerService
	 */
	public PlatformSaleManagerService getPlatformSaleManagerService() {
		PlatformSaleManagerService platformSaleManagerService = null;
		try {
			platformSaleManagerService = (PlatformSaleManagerService) context.getBean("platformSaleManagerService");
		} catch (Exception e) {
			LOGGER.error("platformSaleManagerService bean is not found" + e.getMessage(), e);
		}
		return platformSaleManagerService;
	}

	/**
	 * 销售渠道
	 * 
	 * @return platformSaleManagerService
	 */
	public PlatformChannelManagerService getChannelManagerService() {
		PlatformChannelManagerService platformChannelManagerService = null;
		try {
			platformChannelManagerService = (PlatformChannelManagerService) context.getBean("platformChannelManagerService");
		} catch (Exception e) {
			LOGGER.error("platformSaleManagerService bean is not found" + e.getMessage(), e);
		}
		return platformChannelManagerService;
	}

	/**
	 * 活动接口
	 * 
	 * @return ActivityBundlingCouponApi
	 */
	/*public ActivityBundlingCouponApi getActivityBundlingCouponApi() {
		ActivityBundlingCouponApi activityBundlingCouponApi = null;
		try {
			activityBundlingCouponApi = (ActivityBundlingCouponApi) context.getBean("activityBundlingCouponApi");
		} catch (Exception e) {
			LOGGER.error("activityBundlingCouponApi bean is not found" + e.getMessage(), e);
		}
		return activityBundlingCouponApi;
	}
*/
	/**
	 * 品牌审核.
	 * 
	 * @return ActivityBundlingCouponApi
	 */
	public SupplierBrandManagerService getSupplierBrandManagerService() {
		SupplierBrandManagerService supplierBrandManagerService = null;
		try {
			supplierBrandManagerService = (SupplierBrandManagerService) context.getBean("supplierBrandManagerService");
		} catch (Exception e) {
			LOGGER.error("supplierBrandManagerService bean is not found" + e.getMessage(), e);
		}
		return supplierBrandManagerService;
	}

	public StockSupplyService getStockSupplyService() {
		StockSupplyService stockSupplyService = null;

		try {
			stockSupplyService = (StockSupplyService) context.getBean("stockSupplyService");
		} catch (Exception e) {
			LOGGER.error("StockSupplyService bean is not found" + e.getMessage(), e);
		}
		return stockSupplyService;
	}

	/**
	 * 评价管理.
	 * 
	 * @return
	 */
	@SuppressWarnings("finally")
	public CommentService getCommentService() {
		CommentService retailerComment = null;
		try {
			retailerComment = (CommentService) context.getBean("retailerComment");
		} catch (Exception e) {
			LOGGER.error("retailerComment bean is not found" + e.getMessage(), e);
		} finally {
			return retailerComment;
		}
	}

	/**
	 * 品牌审核.
	 * 
	 * @return ActivityBundlingCouponApi
	 */
	public BrandServiceRpc getBrandServiceRpc() {
		BrandServiceRpc brandServiceRpc = null;
		try {
			brandServiceRpc = (BrandServiceRpc) context.getBean("brandServiceRpc");
		} catch (Exception e) {
			LOGGER.error("brandServiceRpc bean is not found" + e.getMessage(), e);
		}
		return brandServiceRpc;
	}

	/**
	 * 商品活动规则
	 * 
	 * @return
	 */
	/*public ActiveProductRuleApi getActiveProductRuleApi() {
		ActiveProductRuleApi activeProductRuleApi = null;
		try {
			activeProductRuleApi = (ActiveProductRuleApi) context.getBean("activeProductRuleApi");
		} catch (BeansException e) {
			LOGGER.error("activeProductRuleApi bean is not found" + e.getMessage(), e);
		}
		return activeProductRuleApi;
	}

	*//**
	 * 商品活动规则范围
	 * 
	 * @return
	 *//*
	public ActiveProductConditionDetailApi getActiveProductConditionDetailApi() {
		ActiveProductConditionDetailApi activeProductConditionDetailApi = null;
		try {
			activeProductConditionDetailApi = (ActiveProductConditionDetailApi) context.getBean("activeProductConditionDetailApi");
		} catch (BeansException e) {
			LOGGER.error("activeProductConditionDetailApi bean is not found" + e.getMessage(), e);
		}
		return activeProductConditionDetailApi;
	}*/

	/**
	 * 规则条件细节
	 * 
	 * @return
	 */
	/*public ActiveProductRuleDetailApi getActiveProductRuleDetailApi() {
		ActiveProductRuleDetailApi activeProductRuleDetailApi = null;
		try {
			activeProductRuleDetailApi = (ActiveProductRuleDetailApi) context.getBean("activeProductRuleDetailApi");
		} catch (BeansException e) {
			LOGGER.error("activeProductRuleDetailApi bean is not found" + e.getMessage(), e);
		}
		return activeProductRuleDetailApi;
	}*/

	/**
	 * 获取品牌接口
	 * 
	 * @return
	 */
	public MyBrandService getMyBrandService() {
		MyBrandService myBrandService = null;
		try {
			myBrandService = (MyBrandService) context.getBean("myBrandService");
		} catch (BeansException e) {
			LOGGER.error("myBrandService bean is not found" + e.getMessage(), e);
		}
		return myBrandService;
	}

	/**
	 * 物流
	 * 
	 * @return
	 */
	public LogisticTempService getLogisticTempService() {
		LogisticTempService logisticTempService = null;
		try {
			logisticTempService = (LogisticTempService) context.getBean("logisticTempService");
		} catch (Exception e) {
			LOGGER.error("logisticTempService bean is not found" + e.getMessage(), e);
		}
		return logisticTempService;

	}

	/**
	 * 物流-平台自定义运费模板
	 * 
	 * @return
	 */
	public PlatformLogisticTempService getPlatformLogisticTempService() {
		PlatformLogisticTempService platformLogisticTempService = null;
		try {
			platformLogisticTempService = (PlatformLogisticTempService) context.getBean("platformLogisticTempService");
		} catch (Exception e) {
			LOGGER.error("platformLogisticTempService bean is not found" + e.getMessage(), e);
		}
		return platformLogisticTempService;

	}

	/**
	 * 物流-平台自定义运费模板
	 * 
	 * @return
	 */
	public DealerProductExportExcelService getDealerProductExportExcelService() {
		DealerProductExportExcelService dealerProductExportExcelService = null;
		try {
			dealerProductExportExcelService = (DealerProductExportExcelService) context.getBean("dealerProductExportExcelService");
		} catch (Exception e) {
			LOGGER.error("platformLogisticTempService bean is not found" + e.getMessage(), e);
		}
		return dealerProductExportExcelService;

	}

	/**
	 * B2C-促销
	 * 
	 * @return
	 */
	public CustomerPromotionApi getCustomerPromotionService() {
		CustomerPromotionApi customerPromotionApi = null;
		try {
			customerPromotionApi = (CustomerPromotionApi) context.getBean("customerPromotionApi");
		} catch (Exception e) {
			LOGGER.error("customerPromotionApi bean is not found" + e.getMessage(), e);
		}
		return customerPromotionApi;

	}

	// 经销商现货发货单
	@SuppressWarnings("finally")
	public RetailerShipOrderService getretailerShipOrderService() {
		RetailerShipOrderService retailerShipOrderService = null;
		try {
			retailerShipOrderService = (RetailerShipOrderService) context.getBean("retailerShipOrderService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return retailerShipOrderService;
		}
	}

	// 采购单
	/*@SuppressWarnings("finally")
	public PlatFormPChaseOrderService getPlatFormPChaseOrderService() {
		PlatFormPChaseOrderService platFormPChaseOrderService = null;
		try {
			platFormPChaseOrderService = (PlatFormPChaseOrderService) context.getBean("platFormPChaseOrderService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return platFormPChaseOrderService;
		}
	}*/

	// 转运库存
	@SuppressWarnings("finally")
	public StockCustomerService getStockCustomerService() {
		StockCustomerService stockCustomerService = null;
		try {
			stockCustomerService = (StockCustomerService) context.getBean("stockCustomerService");
		} catch (Exception e) {
			LOGGER.error("装运仓库代理接口异常" + e.getMessage(), e);
		} finally {

			return stockCustomerService;

		}
	}

	// 海外转运仓集货装运
	@SuppressWarnings("finally")
	public ContainerLoadPlanSevice getContainerLoadPlanSevice() {
		ContainerLoadPlanSevice containerLoadPlanSevice = null;
		try {
			containerLoadPlanSevice = (ContainerLoadPlanSevice) context.getBean("containerLoadPlanSevice");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// TODO: handle exception
		} finally {
			return containerLoadPlanSevice;
		}
	}

	// B2C订单接口服务
	@SuppressWarnings("finally")
	public CustomerOrderService getCustomerOrderService() {
		CustomerOrderService customerOrderService = null;
		try {
			customerOrderService = (CustomerOrderService) context.getBean("customerOrderService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// TODO: handle exception
		} finally {
			return customerOrderService;
		}
	}

	// B2C用户接口服务
	@SuppressWarnings("finally")
	public UserService getUserService() {
		UserService userService = null;
		try {
			userService = (UserService) context.getBean("userService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// TODO: handle exception
		} finally {
			return userService;
		}
	}

	// 订单导出接口服务
	@SuppressWarnings("finally")
	public RetailerOrderExportService getRetailerOrderExportService() {
		RetailerOrderExportService retailerOrderExportService = null;
		try {
			retailerOrderExportService = (RetailerOrderExportService) context.getBean("retailerOrderExportService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// TODO: handle exception
		} finally {
			return retailerOrderExportService;
		}
	}

	// 库存服务
	@SuppressWarnings("finally")
	public StockService getStockService() {
		StockService stockService = null;
		try {
			stockService = (StockService) context.getBean("stockService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			// TODO: handle exception
		} finally {
			return stockService;
		}
	}

	// 库房服务
	public WarehouseService getWarehouseService() {
		WarehouseService warehouseService = null;
		try {
			warehouseService = (WarehouseService) context.getBean("warehouseService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return warehouseService;
	}

	// 基础数据设置
	/*public InfrastructureService getInfrastructureService() {
		InfrastructureService infrastructureService = null;
		try {
			infrastructureService = (InfrastructureService) context.getBean("infrastructureService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return infrastructureService;
	}

	public PChaseOrderServiceRPC getPChaseOrderServiceRPC() {
		PChaseOrderServiceRPC pChaseOrderServiceRPC = null;
		try {
			pChaseOrderServiceRPC = (PChaseOrderServiceRPC) context.getBean("pChaseOrderServiceRPC");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return pChaseOrderServiceRPC;
	}
	
	public PChaseVirtualOrderServiceRPC getPChaseVirtualOrderServiceRPC() {
		PChaseVirtualOrderServiceRPC pChaseVirtualOrderServiceRPC = null;
		try {
			pChaseVirtualOrderServiceRPC = (PChaseVirtualOrderServiceRPC) context.getBean("pChaseVirtualOrderServiceRPC");
		} catch (Exception e) {
			LOGGER.error("调用特卖采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return pChaseVirtualOrderServiceRPC;
	}

	public ShipOrderService getShipOrderService() {
		ShipOrderService shipOrderService = null;
		try {
			shipOrderService = (ShipOrderService) context.getBean("shipOrderService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return shipOrderService;
	}

	public ShipOrderItemService getShipOrderItemService() {
		ShipOrderItemService shipOrderItemService = null;
		try {
			shipOrderItemService = (ShipOrderItemService) context.getBean("shipOrderItemService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return shipOrderItemService;
	}

	public StockInOrderItemService getStockInOrderItemService() {
		StockInOrderItemService stockInOrderItemService = null;
		try {
			stockInOrderItemService = (StockInOrderItemService) context.getBean("stockInOrderItemService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return stockInOrderItemService;
	}

	public StockInOrderService getStockInOrderService() {
		StockInOrderService stockInOrderService = null;
		try {
			stockInOrderService = (StockInOrderService) context.getBean("stockInOrderService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return stockInOrderService;
	}

	public StockOutOrderService getStockOutOrderService() {
		StockOutOrderService stockOutOrderService = null;
		try {
			stockOutOrderService = (StockOutOrderService) context.getBean("stockOutOrderService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return stockOutOrderService;
	}

	public StockOutOrderItemService getStockOutOrderItemService() {
		StockOutOrderItemService stockOutOrderItemService = null;
		try {
			stockOutOrderItemService = (StockOutOrderItemService) context.getBean("stockOutOrderItemService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return stockOutOrderItemService;
	}

	*//**
	 * 入库通知单
	 * 
	 * @return
	 *//*
	public NotificationOrderProcessService getNotificationOrderProcessService() {
		NotificationOrderProcessService notificationOrderProcessService = null;
		try {
			notificationOrderProcessService = (NotificationOrderProcessService) context.getBean("notificationOrderProcessService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return notificationOrderProcessService;
	}

	*//**
	 * 出库通知单
	 * 
	 * @return
	 *//*
	public NotificationOutOrderProcessService getNotificationOutOrderProcessService() {
		NotificationOutOrderProcessService notificationOutOrderProcessService = null;
		try {
			notificationOutOrderProcessService = (NotificationOutOrderProcessService) context.getBean("notificationOutOrderProcessService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return notificationOutOrderProcessService;
	}

	*//**
	 * 出库通知单
	 * 
	 * @return
	 *//*
	public NotificationOutOrderExportService getNotificationOutOrderExportService() {
		NotificationOutOrderExportService notificationOutOrderExportService = null;
		try {
			notificationOutOrderExportService = (NotificationOutOrderExportService) context.getBean("notificationOutOrderExportService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return notificationOutOrderExportService;
	}

	public PurchaseCostService getPurchaseCostService() {
		PurchaseCostService purchaseCostService = null;
		try {
			purchaseCostService = (PurchaseCostService) context.getBean("purchaseCostService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return purchaseCostService;
	}

	public PurchaseRegService getPurchaseRegService() {
		PurchaseRegService purchaseRegService = null;
		try {
			purchaseRegService = (PurchaseRegService) context.getBean("purchaseRegService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return purchaseRegService;
	}

	public StockOutOrderExcelService getStockOutOrderExcelService() {
		StockOutOrderExcelService stockOutOrderExcelService = null;
		try {
			stockOutOrderExcelService = (StockOutOrderExcelService) context.getBean("stockOutOrderExcelService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return stockOutOrderExcelService;
	}

	public StockDetilService getStockDetilService() {
		StockDetilService stockDetilService = null;
		try {
			stockDetilService = (StockDetilService) context.getBean("stockDetilService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return stockDetilService;
	}*/

	public Object getAppService(String beanId) {
		Object object = null;
		try {
			object = context.getBean(beanId);
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return object;
	}

	/**
	 * B2C订单导出接口.
	 */
	public CustomerOrderExportSevice getCustomerOrderExportService() {
		CustomerOrderExportSevice customerOrderExportService = null;
		try {
			customerOrderExportService = (CustomerOrderExportSevice) context.getBean("customerOrderExportService");
		} catch (Exception e) {
			LOGGER.error("调用采购订单接口bean错误:  msg:" + e.getMessage(), e);
		}
		return customerOrderExportService;
	}
	
	/**
	 * 获取物流商接口
	 * @return
	 */
	@SuppressWarnings("finally")
	public LogisticTempService getlogisticTempService(){
		LogisticTempService logisticTempService = null;
		try {
			logisticTempService = (LogisticTempService) context.
					getBean("logisticTempService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}finally{
		return logisticTempService;
		}
	}

	/**
	 * 财务-库存明细
	 * 
	 * @return
	 */
	/*public MyFinancStockService getFinancStockService() {
		MyFinancStockService financStockService = null;
		try {
			financStockService = (MyFinancStockService) context.getBean("financStockService");
		} catch (Exception e) {
			LOGGER.error("调用财务库存接口bean错误:  msg:" + e.getMessage(), e);
		}
		return financStockService;
	}

	*//**
	 * @Description： 商品调拨接口调用
	 * @author: QIJJ
	 * @since: 2015-7-3 下午2:33:07
	 *//*
	public TransferOrderRpcService getTransferOrderService() {
		TransferOrderRpcService transferOrderRpcService = null;
		try {
			transferOrderRpcService = (TransferOrderRpcService) context.getBean("transferOrderRpcService");
		} catch (Exception e) {
			LOGGER.error("调用商品调拨接口bean错误:  msg:" + e.getMessage(), e);
		}

		return transferOrderRpcService;
	}

	*//**
	 * @Description： 商品调拨详细接口调用
	 * @author: QIJJ
	 * @since: 2015-7-8 下午4:18:05
	 *//*
	public TransferItemRpcService getTransferItemRpcService() {
		TransferItemRpcService transferItemRpcService = null;
		try {
			transferItemRpcService = (TransferItemRpcService) context.getBean("transferItemRpcService");
		} catch (Exception e) {
			LOGGER.error("调用商品调拨详细接口bean错误:  msg:" + e.getMessage(), e);
		}

		return transferItemRpcService;
	}

	*//**
	 * @Description： 分摊明细接口调用
	 * @author: QIJJ
	 * @since: 2015-7-16 下午5:41:41
	 *//*
	public SkuAllocationService getSkuAllocationService() {
		SkuAllocationService skuAllocationService = null;
		try {
			skuAllocationService = (SkuAllocationService) context.getBean("skuAllocationService");
		} catch (Exception e) {
			LOGGER.error("分摊详细接口bean错误:  msg:" + e.getMessage(), e);
		}

		return skuAllocationService;
	}

	*//**
	 * @Description： 库房错误信息接口调用
	 * @author: QIJJ
	 * @since: 2015-7-31 上午11:22:30
	 *//*
	public StockOutErrorService getStockOutErrorService() {
		StockOutErrorService stockOutErrorService = null;
		try {
			stockOutErrorService = (StockOutErrorService) context.getBean("stockOutErrorService");
		} catch (Exception e) {
			LOGGER.error("库房错误信息接口bean错误:  msg:" + e.getMessage(), e);
		}

		return stockOutErrorService;
	}

	*//**
	 * @Description： 物流错误信息接口调用
	 * @author: QIJJ
	 * @since: 2015-7-31 上午11:22:30
	 *//*
	public ShipQtyErrorService getShipQtyErrorService() {
		ShipQtyErrorService shipQtyErrorService = null;
		try {
			shipQtyErrorService = (ShipQtyErrorService) context.getBean("shipQtyErrorService");
		} catch (Exception e) {
			LOGGER.error("物流错误信息接口bean错误:  msg:" + e.getMessage(), e);
		}

		return shipQtyErrorService;
	}

	*//**
	 * @Description： WMS推送错误信息接口调用
	 * @author: QIJJ
	 * @since: 2015-7-31 上午11:22:30
	 *//*
	public LogisticsThtxMessageService getLogisticsThtxMessageService() {
		LogisticsThtxMessageService logisticsThtxMessageService = null;
		try {
			logisticsThtxMessageService = (LogisticsThtxMessageService) context.getBean("logisticsThtxMessageService");
		} catch (Exception e) {
			LOGGER.error("WMS推送错误信息接口bean错误:  msg:" + e.getMessage(), e);
		}

		return logisticsThtxMessageService;
	}*/
	/**
	 * 
	 * @return
	 */
	public DealerProductMixService getDealerProductMixService() {
		DealerProductMixService dealerProductMixService = null;
		try {
			dealerProductMixService = (DealerProductMixService) context.getBean("dealerProductMixService");
		} catch (Exception e) {
			LOGGER.error("调用商品DealerProductMixService的bean错误:  msg:" + e.getMessage(), e);
		}

		return dealerProductMixService;
	}
	/**
	 * 返创建权限客户端
	 * @return
	 */
    public AuthorityClient getAuthorityClient(){
    	return  new AuthorityClientImpl();
    }
	/**
	 * 
	 * @return
	 */
	/*public CollectSendCouponInfoApi getCollectSendCouponInfoApi() {
		CollectSendCouponInfoApi collectSendCouponInfoApi = null;
		try {
			collectSendCouponInfoApi = (CollectSendCouponInfoApi) context.getBean("collectSendCouponInfoApi");
		} catch (Exception e) {
			LOGGER.error("调用促销CollectSendCouponInfoApi的bean错误:  msg:" + e.getMessage(), e);
		}

		return collectSendCouponInfoApi;
	}*/
	
	/**
	 * 
	 * @return
	 */
	public PlatformActivityService getPlatformActivityService() {
		PlatformActivityService platformActivityService = null;
		try {
			platformActivityService = (PlatformActivityService) context.getBean("platformActivityService");
		} catch (Exception e) {
			LOGGER.error("调用APP活动PlatformActivityService的bean错误:  msg:" + e.getMessage(), e);
		}

		return platformActivityService;
    }
	/**
	 * 
	 * @return
	 */
	public PlatformActivityConfigService getPlatformActivityConfigService() {
		PlatformActivityConfigService platformActivityConfigService = null;
		try {
			platformActivityConfigService = (PlatformActivityConfigService) context.getBean("platformActivityConfigService");
		} catch (Exception e) {
			LOGGER.error("调用APP活动platformActivityConfigService的bean错误:  msg:" + e.getMessage(), e);
		}

		return platformActivityConfigService;
    }

	@SuppressWarnings("finally")
	public RetailerProductService getretailerProductService() {
		RetailerProductService retailerProductService = null;
		try {
			retailerProductService = (RetailerProductService) context
					.getBean("retailerProductService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return retailerProductService;
		}
	}
	
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
	//B2C秒杀活动服务
	@SuppressWarnings("finally")
	public CustomerSecondKillService getcustomerSecondKillService() {
		CustomerSecondKillService customerSecondKillService = null;
		try {
			customerSecondKillService = (CustomerSecondKillService) context
					.getBean("customerSecondKillService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return customerSecondKillService;
		}
	}
	//订单取消接口
	/*@SuppressWarnings("finally")
	public OrderSCancelService getOrderSCancelService() {
		OrderSCancelService orderSCancelService = null;
		try {
			orderSCancelService = (OrderSCancelService) context
					.getBean("orderSCancelService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return orderSCancelService;
		}
	}
	//订单出库接口
	@SuppressWarnings("finally")
	public OutOrderService getOutOrderService() {
		OutOrderService outOrderService = null;
		try {
			outOrderService = (OutOrderService) context
					.getBean("outOrderService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return outOrderService;
		}
	}*/
	
	public DealerProductAuditService getDealerProductAuditService() {
		DealerProductAuditService dealerProductAuditService = null;
		try {
			dealerProductAuditService = (DealerProductAuditService) context
					.getBean("dealerProductAuditService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return dealerProductAuditService;
		}
	}
	
	@SuppressWarnings("finally")
	public SupplierStoreService getSupplierStoreService() {
		SupplierStoreService supplierStoreService = null;
		try {
			supplierStoreService = (SupplierStoreService) context
					.getBean("supplierStoreService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return supplierStoreService;
		}
	}
	
	/*@SuppressWarnings("finally")
	public AdjFinishService getAdjFinishService() {
		AdjFinishService adjFinishService = null;
		try {
			adjFinishService = (AdjFinishService) context
					.getBean("adjFinishService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return adjFinishService;
		}
	}
	
	@SuppressWarnings("finally")
	public MoveFinishPOService getMoveFinishPOService() {
		MoveFinishPOService moveFinishPOService = null;
		try {
			moveFinishPOService = (MoveFinishPOService) context
					.getBean("moveFinishPOService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return moveFinishPOService;
		}
	}*/
	
/*	@SuppressWarnings("finally")
	public RechargeService getRechargeService() {
		RechargeService rechargeReOrderService = null;
		try {
			rechargeReOrderService = (RechargeService) context
					.getBean("rechargeReOrderService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			return rechargeReOrderService;
		}
	}*/

	public MemberStarService getMemberStarService() {
		MemberStarService memberStarService = null;
		try{
			memberStarService = (MemberStarService) context.getBean("memberStarService");
		}catch (Exception e){
			LOGGER.error("调用会员星级服务接口bean错误：msg:"+e.getMessage(),e);
		}finally {
			return memberStarService;
		}
	}

	public SupplierTypeService getSupplierTypeService() {
		SupplierTypeService supplierTypeService = null;
		try{
			supplierTypeService = (SupplierTypeService) context.getBean("supplierTypeService");
		}catch (Exception e){
			LOGGER.error("调用收银系统服务接口bean错误：msg:"+e.getMessage(),e);
		}finally {
			return supplierTypeService;
		}
	}

	public CashierService getCashierService() {
		CashierService cashierService = null;
		try{
			cashierService = (CashierService) context.getBean("cashierService");
		}catch (Exception e){
			LOGGER.error("调用收银系统服务接口bean错误：msg:"+e.getMessage(),e);
		}finally {
			return cashierService;
		}
	}

	@SuppressWarnings("finally")
	public DividendService getDividendService() {
		DividendService dividendService = null;
		try {
			dividendService = (DividendService) context.getBean("dividendService");
		} catch (Exception e) {
			LOGGER.error("调用分红设置接口bean错误:  msg:" + e.getMessage(), e);
		} finally {
			return dividendService;
		}
	}
	@SuppressWarnings("finally")
	public FhCyclePlanService getFhCyclePlanService() {
		FhCyclePlanService fhCyclePlanService = null;
		try {
			fhCyclePlanService = (FhCyclePlanService) context.getBean("fhCyclePlanService");
		} catch (Exception e) {
			LOGGER.error("调用周期分红设置接口bean错误:  msg:" + e.getMessage(), e);
		} finally {
			return fhCyclePlanService;
		}
	}
	
	@SuppressWarnings("finally")
	public SupplierOperateLogService getSupplierOperateLogService() {
		SupplierOperateLogService supplierOperateLogService = null;
		try {
			supplierOperateLogService = (SupplierOperateLogService) context.getBean("supplierOperateLogService");
		} catch (Exception e) {
			LOGGER.error("调用记录操作日志bean错误:  msg:" + e.getMessage(), e);
		} finally {
			return supplierOperateLogService;
		}
	}
	
	@SuppressWarnings("finally")
	public UserStanceService getUserStanceService() {
		UserStanceService userStanceService = null;
		try {
			userStanceService = (UserStanceService) context.getBean("userStanceService");
		} catch (Exception e) {
			LOGGER.error("调用记录占位idbean错误:  msg:" + e.getMessage(), e);
		} finally {
			return userStanceService;
		}
	}
	
	//
	@SuppressWarnings("finally")
	public SqwAccountRecordService getSqwAccountRecordService() {
		SqwAccountRecordService sqwAccountRecordService = null;
		try {
			sqwAccountRecordService = (SqwAccountRecordService) context.getBean("sqwAccountRecordService");
		} catch (Exception e) {
			LOGGER.error("获取用户结算或未结算订单接口服务:  msg:" + e.getMessage(), e);
		} finally {
			return sqwAccountRecordService;
		}
	}
	
	@SuppressWarnings("finally")
	public TradeFeeService getTradeFeeService() {
		TradeFeeService tradeFeeService = null;
		try {
			tradeFeeService = (TradeFeeService) context.getBean("tradeFeeService");
		} catch (Exception e) {
			LOGGER.error("调用平台交易费设置接口bean错误:  msg:" + e.getMessage(), e);
		} finally {
			return tradeFeeService;
		}
	}
	
}
