package com.mall.supplier.util;


/**
 *
 *string工具类
 *
 *
 */
public final class StringUtils extends org.apache.commons.lang.StringUtils {
	private static final String EMPTY_STR = "";
	private static final String[] NULL_STR_ARRAY = {"null"};

	/**
	 * 字符编码转换
	 * @param s 需转码的字符串
	 * @param charset 目标字符串编码
	 * @return String
	 */
	public static String changeCharset(String str, String charset){
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0;i < str.length();i++) {
	    	char c = str.charAt(i);
	    	if (c >= 0 && c <= 255) {
	    		sb.append(c);
	    	} else {
	    		byte[] b;
	    		try { 
	    			b = Character.toString(c).getBytes(charset);
	    		} catch (Exception ex) {
	    			b = new byte[0];
	            }
	    		for (int j = 0; j < b.length; j++) {
	    			int k = b[j];
	    			if (k < 0) k += 256;
	    			sb.append("%" + Integer.toHexString(k).toUpperCase());
	    		}
	    	}
	    }
	    return sb.toString();
	}
	
	/**
	 *       
	 * @Description：判断是否为null
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (str == null) {
			return true;
		}
		if (NULL_STR_ARRAY != null && NULL_STR_ARRAY.length > 0) {
			for (String nullStr : NULL_STR_ARRAY) {
				if (str.equalsIgnoreCase(nullStr)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 *       
	 * @Description：判断非null
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		return !isNull(str);
	}
	
	/**
	 *        
	 * @Description：判断是否为空串
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0 || str.equalsIgnoreCase(EMPTY_STR));
	}
	
	
	/**
	 *       
	 * @Description：判断非空串
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * 	        
	 * @Description：左边补齐长度
	 * @param originStr	原始字符串
	 * @param destLen	目标字符串长度
	 * @param fillChar	填充字符
	 * @return
	 */
	public static String fillLenAtLeft (String originStr, int destLen, char fillChar) {
		int needFillLen = destLen - originStr.length();
		StringBuffer destStrBuff = new StringBuffer();
		for (int i = 0;i < needFillLen;i++) {
			destStrBuff.append(fillChar);
		}
		destStrBuff.append(originStr);
		return destStrBuff.toString();
	}

	/**
	 * 	        
	 * @Description：右边补齐长度
	 * @param originStr	原始字符串
	 * @param destLen	目标字符串长度
	 * @param fillChar	填充字符
	 * @return
	 */
	public static String fillLenAtRight (String originStr, int destLen, char fillChar) {
		int needFillLen = destLen - originStr.length();
		StringBuffer destStrBuff = new StringBuffer(originStr);
		for (int i = 0;i < needFillLen;i++) {
			destStrBuff.append(fillChar);
		}
		return destStrBuff.toString();
	}
	
	/**
	 *        
	 * @Description：
	 * @param nullStr 空字符串, 如果非空, 则返回原来的字符串.
	 * @return
	 */
	public static String nullToString (String nullStr) {
		if (isNull(nullStr) || isEmpty(nullStr)) {
			return "";
		}
		return nullStr;
	}

	public static boolean inArrayIgnoreCase (String str, String[] strArray) {
		if (str == null || strArray == null || strArray.length == 0) {
			return false;
		}
		for (String str1 : strArray) {
			if (equalsIgnoreCase(str1, str)) {
				return true;
			}
		}
		return false;
	}
}
