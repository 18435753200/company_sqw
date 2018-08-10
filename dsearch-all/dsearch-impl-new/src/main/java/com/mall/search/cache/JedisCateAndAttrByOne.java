package com.mall.search.cache;

import com.mall.search.service.impl.ProductAttrServiceImpl;
import com.mall.search.service.impl.ProductCatalogServiceImpl;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * 类目 和 属性 缓存服务
 * @author Doublell
 *
 */
public class JedisCateAndAttrByOne {
	private static final Logger LOGGER = Logger.getLogger(JedisCateAndAttrByOne.class);
	private static ProductAttrServiceImpl cacheManamger_pa;
	private static ProductCatalogServiceImpl cacheManamger_pc;

	public static void init(ApplicationContext context){
		cacheManamger_pa = (ProductAttrServiceImpl) context.getBean("productAttrServiceImpl");
		cacheManamger_pc = (ProductCatalogServiceImpl) context.getBean("productCatalogServiceImpl");
	}
	private JedisCateAndAttrByOne(){}
    public static void startCaches(String param){
      if(param.contains("cate")){
		  param=param.replace("cate_","");
		  cacheManamger_pc.initCatalogCache(param);
	  }else if(param.contains("pb")){
		  param=param.replace("pb_","");
		  cacheManamger_pa.initProductAttrCache(param);
      }
    }
    
    /**
     * 查询是否配匹 类目 品牌
     * @param param
     * @return
     */
    public static String getCateBrand(String param ,boolean isB2C){
        if(param.contains("cname")){
        	param=param.replace("cname_","");
        	if(isB2C){
        		return cacheManamger_pc.getCategoryByKeyword(param,"b2c");
        	}else{
        		return cacheManamger_pc.getCategoryByKeyword(param,"b2b");
        	}
    		
  	  }else if(param.contains("brandname")){
  		  param=param.replace("brandname_","");
  		  return cacheManamger_pc.getBrandByKeyword(param);
      }
        
      return "";
    }
}
