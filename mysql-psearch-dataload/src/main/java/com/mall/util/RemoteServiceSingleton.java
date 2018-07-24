package com.mall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mall.dealer.productTags.api.ProductTagsService;
//import com.mall.promotion.api.activity.productInfo.ProductByPromotionInfoService;

/**
 * @author doublell 接口代理.
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

    //加载容器上下文
	static class RemoteServiceSingletonHolder {

		static RemoteServiceSingleton instance = new RemoteServiceSingleton();
		static {
			context = new ClassPathXmlApplicationContext(new String[] { "remoting.xml" });
		}
	}

	/**
	 * @return RemoteServiceSingleton 接口代理实例（单例）
	 */
	public static RemoteServiceSingleton getInstance() {
		return RemoteServiceSingletonHolder.instance;
	}


	/************************ 请修改以下方法 **************************/
	
	//促销接口
	/*@SuppressWarnings("finally")
	public ProductByPromotionInfoService getProductByPromotionInfoService(){
		ProductByPromotionInfoService productByPromotionInfoService = null;
		try {
			productByPromotionInfoService = (ProductByPromotionInfoService) context.getBean("productByPromotionInfoService");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally{
			return productByPromotionInfoService;
		}
	}*/
	
	//标签接口
		@SuppressWarnings("finally")
		public ProductTagsService getProductTagsService(){
			ProductTagsService productTagsService = null;
			try {
				productTagsService = (ProductTagsService) context.getBean("productTagsService");
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			} finally{
				return productTagsService;
			}
		}
}
