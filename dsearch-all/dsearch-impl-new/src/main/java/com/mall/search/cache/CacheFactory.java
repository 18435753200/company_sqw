package com.mall.search.cache;


public class CacheFactory {
	private CacheFactory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static CacheOper getCacheOper(String prefix){
		return new CacheOper(prefix);
	}
	
}
