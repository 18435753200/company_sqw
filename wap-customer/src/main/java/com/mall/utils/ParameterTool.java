package com.mall.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 参数转换的工具类
 * @author ccigQA01
 *
 */
public class ParameterTool {
	/**
	 * 获取回跳地址
	 * 
	 * @param requestUrl
	 * @param parames
	 * @return
	 */
	public static String getRedirectUrl(String requestUrl, String parames) {
		return requestUrl + "?" + parames;
	}

	/**
	 * map2url
	 * 
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String mapToUrl(Map<String, Object> map)
			throws UnsupportedEncodingException {

		StringBuffer sb = new StringBuffer();
		boolean isFirst = true;
		for (String key : map.keySet()) {
			Object object = map.get(key);
			String value = null;
			if (object != null) {
				value = (String) object;
			}
			if (value != null && !"".equals(value)) {
				if (isFirst) {
					sb.append(key + "=" + URLEncoder.encode(value, "UTF-8"));
					isFirst = false;
				} else {
					sb.append("&" + key + "="
							+ URLEncoder.encode(value, "UTF-8"));
				}
			}
		}

		return sb.toString();
	}

	/**
	 * bean 转换为map
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<String, Object> convertRequestToMap(T t)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		BeanInfo bean = Introspector.getBeanInfo(t.getClass());
		PropertyDescriptor[] propertyDescriptors = bean
				.getPropertyDescriptors();

		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
			String name = propertyDescriptor.getName();
			if (!"class".equals(name) && !"requestUrl".equals(name)) {
				Method readMethod = propertyDescriptor.getReadMethod();
				Object invoke = readMethod.invoke(t);
				if (invoke != null) {
					map.put(name, invoke);
				} else {
					map.put(name, "");
				}
			}

		}
		return map;
	}

	
	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
	
}
