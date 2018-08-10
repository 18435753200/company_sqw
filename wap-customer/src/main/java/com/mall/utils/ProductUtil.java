package com.mall.utils;


import org.apache.log4j.Logger;

import com.mall.architect.redis.JedisManager;
import com.mall.dealer.product.customer.api.CustomerProductApi;
import com.mall.dealer.product.dto.B2cProductShowDTO;
import com.mall.dealer.product.dto.B2cProductShowNewDTO;
import com.mall.dealer.product.po.DealerProduct;

public class ProductUtil {
    private static final String TABLE_NAME = "product";
    private static final int sconds = 3600;
    
    private static final Logger LOGGER = Logger.getLogger(ProductUtil.class);
    
    private static void saveProduct(B2cProductShowDTO product){
        // 生成key
        String key = TABLE_NAME+product.getProductId();
        // 放入缓存
        JedisManager.setObject(key, sconds, product);
        LOGGER.info("生成缓存成功prodId："+product.getProductId());
    }
    
    private static void saveProductNew(B2cProductShowNewDTO product){
        // 生成key
        String key = TABLE_NAME+product.getProductId();
        // 放入缓存
        JedisManager.setObject(key, sconds, product);
        LOGGER.info("生成缓存成功prodId："+product.getProductId());
    }
    
    public static B2cProductShowDTO getProduct(CustomerProductApi customerProductApi,Long productId){
        // 生成key
        if(productId != null && productId != 123l){
            String key = TABLE_NAME+productId;
            Object object = JedisManager.getObject(key);
            B2cProductShowDTO obj = null;
            if(object != null){
                LOGGER.info("从缓存中获取商品prodId："+productId);
                obj = (B2cProductShowDTO)object;                
                // 根据商品状态获取商品ID
                DealerProduct findB2cIstate = customerProductApi.findB2cIstate(productId);
                // 判断商品最后修改时间是否和缓存中的商品最后修改时间一致  不一致则更新缓存
                if(findB2cIstate.getOperatetime().compareTo(obj.getUpdateDate()) != 0){
                    LOGGER.info("更新缓存中的商品prodId："+productId);
                    obj = customerProductApi.findB2cProductByProdId(productId);
                    saveProduct(obj);  
                }else{
                    // 更新商品状态
                    obj.setProdType(findB2cIstate.getB2cIsTate()==1?(short)1:(short)0);
                }
            }else{
                obj = customerProductApi.findB2cProductByProdId(productId);
                saveProduct(obj);
            }
            obj.setUpdateDate(null);
            return obj;
        }else{
            return customerProductApi.findB2cProductByProdId(productId);
        }
    }
    
    public static B2cProductShowNewDTO getProductNew(CustomerProductApi customerProductApi,Long productId){
        // 生成key
        if(productId != null && productId != 123l){
            String key = TABLE_NAME+productId;
            Object object = JedisManager.getObject(key);
            B2cProductShowNewDTO obj = null;
            if(object != null){
                LOGGER.info("从缓存中获取商品prodId："+productId);
                obj = (B2cProductShowNewDTO)object;                
                // 根据商品状态获取商品ID
                DealerProduct findB2cIstate = customerProductApi.findB2cIstate(productId);
                // 判断商品最后修改时间是否和缓存中的商品最后修改时间一致  不一致则更新缓存
                if(findB2cIstate.getOperatetime().compareTo(obj.getUpdateDate()) != 0){
                    LOGGER.info("更新缓存中的商品prodId："+productId);
                    obj = customerProductApi.findB2cProductByProdIdNew(productId);
                    saveProductNew(obj);  
                }else{
                    // 更新商品状态
                    obj.setProdType(findB2cIstate.getB2cIsTate()==1?(short)1:(short)0);
                }
            }else{
                obj = customerProductApi.findB2cProductByProdIdNew(productId);
                saveProductNew(obj);
            }
            obj.setUpdateDate(null);
            return obj;
        }else{
            return customerProductApi.findB2cProductByProdIdNew(productId);
        }
    }
}
