package com.mall.search.utils;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串常用操作类
 * 增加：extends org.apache.commons.lang.StringUtils
 */
public class StringUtils extends org.apache.commons.lang.StringUtils{

	public static boolean compareStrSplitByComma(String s, String s1) {
		if (s == null || s1 == null)
			return false;
		String as[] = s.split(",");
		String as1[] = s1.split(",");
		Map<String, String> hashmap = new HashMap<String, String>();
		if (as.length != as1.length)
			return false;
		for (int i = 0; i < as1.length; i++)
			hashmap.put(as1[i], as1[i]);

		for (int j = 0; j < as.length; j++)
			if (!hashmap.containsKey(as[j]))
				return false;

		return true;
	}

	public static String doubleToCurrency(double d) {
		Object aobj[] = { new Double(d) };
		return MessageFormat.format(
				"{0,number,\uFFE5,#,###,###,###,###,###,##0.00}", aobj);
	}

	public static String encodeString(String s, String s1, String s2) {
		if (s == null || s1 == null || s2 == null)
			return null;
		String s3 = null;
		try {
			s3 = new String(s.getBytes(s1), s2);
		} catch (UnsupportedEncodingException unsupportedencodingexception) {
			s3 = s;
		}
		return s3;
	}

	public static boolean isEmptyString(String s) {
		return s == null || s.trim().length() < 1
				|| s.trim().equalsIgnoreCase("null");
	}

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() < 1
				|| s.trim().equalsIgnoreCase("null");
	}

	public static boolean isNotEmpty(String s) {
		return s != null && s.trim().length() >= 1
				&& !s.trim().equalsIgnoreCase("null");
	}

	public static boolean isNumber(String s) {
		if (isEmptyString(s))
			return false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c < '0' || c > '9')
				return false;
		}

		return true;
	}

	public static String[] getNumStringArray(String s) {
		if (s == null)
			return null;
		ArrayList<String> arraylist = new ArrayList<String>();
		Pattern pattern = Pattern.compile("([0-9])+");
		for (Matcher matcher = pattern.matcher(s); matcher.find(); arraylist
				.add(matcher.group()))
			;
		return (String[]) arraylist.toArray(new String[0]);
	}

	public static int stringToInt(String s) {
		return stringToInt(s, -1);
	}

	public static int stringToInt(String s, int i) {
		int j = i;
		if (s != null)
			try {
				j = Integer.parseInt(s);
			} catch (NumberFormatException numberformatexception) {
				j = i;
			}
		return j;
	}

	public static String toGBKString(String s) {
		return encodeString(s, "ISO8859_1", "GBK");
	}

}