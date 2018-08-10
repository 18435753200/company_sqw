package com.mall.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
//import com.mall.controller.promotions.CouponController;

public class CollectionUtils {
	private static final Log LOGGER = LogFactory.getLogger(CollectionUtils.class);
	//map转换成lisy
		public static List<String>  mapTransitionList(Map<String, String> map) {
			List<String> list = new ArrayList<String>();
			Iterator<Entry<String, String>> iter = map.entrySet().iterator();  //获得map的Iterator
			while(iter.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>)iter.next();
				list.add(entry.getValue());
			}
			return list;
		}
		
		public static String printList(List list){
			if(list == null){
				return "[]";
			}else{
				StringBuffer buf = new StringBuffer("[");
				
				for (int i=0;i<list.size();i++) {
					Object object = list.get(i);
					if(i==list.size()-1){
						buf.append(object);
					}else{
						buf.append(object).append(",");
					}
					
				}
				buf.append("]");
				return buf.toString();
			}
		}
		
		public static void main(String[] args) {
			List<Long> list = null;new ArrayList<Long>();
//			list.add(1L);
//			list.add(2L);
			printList(list);
		}
		
		/**
		 * 获取文件后缀格式
		 * 
		 * @param filename
		 * @return 文件后缀格式 eg: (jpg)
		 */
		public static String getFileExt2(String filename) {
			if (isEmpty(filename) || filename.lastIndexOf(".") == -1) {
				return "";
			}
			String extName = filename.substring(filename.lastIndexOf(".") + 1)
					.toLowerCase();
			return extName;
		}
		
		
		/**
		 * 判断是否为空字符串
		 * 
		 * @param string
		 * @return 字符串是否为空
		 */
		public static boolean isEmpty(String string) {
			boolean result = false;
			if (string == null) {
				return true;
			}
			if ("".equals(string.trim())) {
				return true;
			}
			return result;
		}

}
