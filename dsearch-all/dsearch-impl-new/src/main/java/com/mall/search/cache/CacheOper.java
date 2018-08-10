package com.mall.search.cache;

import com.alibaba.dubbo.common.json.ParseException;
import com.mall.architect.redis.JedisManager;

public class CacheOper {
	private String prefix;
    public CacheOper( String prefix) {
		this.prefix = prefix;
	}
    
    public void put(String key, Object value) {
    	JedisManager.setObject(prefix+key,0 , value);
    }
    
    public void put(String key, Object value,int expiredTime) {
    	
    	JedisManager.setObject(prefix+key, expiredTime, value);
    }

    public Object get(String key) throws ParseException {
    	return JedisManager.getObject(prefix+key);
    }

    public void delete(String key){
    	JedisManager.delObject(prefix+key);
    }
}
