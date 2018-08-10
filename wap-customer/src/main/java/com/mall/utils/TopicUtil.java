package com.mall.utils;


import java.util.Date;

import org.apache.log4j.Logger;

import com.mall.architect.redis.JedisManager;

public class TopicUtil {
    private static final String TABLE_NAME = "tip";
    
    private static final Logger LOGGER = Logger.getLogger(TopicUtil.class);
    
    public static void saveTopic(String val){
        // 生成key
        String key = TABLE_NAME+val;
        String obj = getTopic(val);
        if(obj==null){
        	Date now = new Date();
        	int sconds= 86400-now.getHours()*3600-now.getMinutes()*60-now.getSeconds();
        	// 放入缓存
            JedisManager.setObject(key, sconds, val);
            LOGGER.info("生成缓存成功key："+val);
        }
    }
    
    public static String getTopic(String val){
    	// 生成key
        String key = TABLE_NAME+val;
    	String object = (String)JedisManager.getObject(key);
        return object;
    }
}
