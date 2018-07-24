package com.mall.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 对于Map集合操作的工具类
 * @author zcc
 *
 */
public class MapUtils {
	
	/**
	 * 对Map集合进行反转的工具类
	 */

	public static Map<String, String> reverseMap(Map<String, String> oldMap){
		
		List<String> l=new ArrayList<String>();
		Set<Entry<String, String>> set = oldMap.entrySet();
		Iterator<Entry<String, String>> it = set.iterator();
		while(it.hasNext()){
			String key = it.next().getKey();
			Object value = oldMap.get(key);
			l.add(key+":"+value);
		}
		Map<String, String> newMap=new LinkedHashMap<String, String>();
		Collections.reverse(l);
		for(String str:l){
			String[] split = str.split(":");
			newMap.put(split[0], split[1]);
		}
		
		return newMap;
	}
}
