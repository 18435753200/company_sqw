package com.mall.util;
/**
 * 字符串工具类
 * @author DOUBLELL
 *
 */
public class StringUtils {

	//字符串不为null
	public static boolean isNotEmpty(String s) {
		return s != null && s.trim().length() >= 1
				&& !s.trim().equalsIgnoreCase("null");
	}
	
	public static boolean isEmpty(String string){
		boolean result = false;
		if(null == string){
			return true;
		}
		if("".equals(string.trim())){
			return true;
		}
		return result;
	}
}
