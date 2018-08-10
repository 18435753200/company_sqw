package com.mall.utils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author jianping.gao
 *
 */
public class ValidateUtil {

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isServiceId(String str) {
		String patternStr = "/^FW\\d{18}$/";
		return regularMatches(patternStr, str);
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPayId(String str) {
		String patternStr = "\\d{18}";
		return regularMatches(patternStr, str);
	}
	
	/**
	 * @param str
	 * @return
	 */
	public static boolean isDigit(String str) {
		String patternStr = "\\d+";
		return regularMatches(patternStr, str);
	}

	public static boolean regularMatches(String regular, String value) {
		boolean returnValue = false;
		if (value != null) {
			Pattern p = Pattern.compile(regular);
			if (p.matcher(value).matches()) {
				returnValue = true;
			}
		}
		return returnValue;
	}

	/**
	 * String List Map Array 
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(Object object) {
		boolean result = false;
		if (isNull(object)) {
			return true;
		} else {
			if (object instanceof String) {
				return "".equals(((String) object).trim()) ? true : false;
			} else if (object instanceof Object[]) {
				return ((Object[]) object).length > 0 ? false : true;
			} else if (object instanceof List) {
				return ((List) object).isEmpty();
			} else if (object instanceof Map) {
				return ((Map) object).isEmpty();
			}
		}
		return result;
	}
	
	public static boolean isNull(Object object) {
		return object == null ? true : false;
	}
}

