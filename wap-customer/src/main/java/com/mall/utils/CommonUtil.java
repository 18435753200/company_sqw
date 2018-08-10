/**
 * 
 */
package com.mall.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhengqiang.shi 2014年9月12日 下午3:41:40
 */
public class CommonUtil {
	/**
	 * 获取所有投诉类型 这些数据本应存在DB，暂时放在Util中
	 * 
	 * @return
	 */
	public static Map<Integer, String> getSuggestType() {
		Map<Integer, String> suggestType = new HashMap<Integer, String>();
		suggestType.put(1, "众聚猫公司相关问题");
		suggestType.put(2, "消费者相关问题");
		/*suggestType.put(3, "红旗手相关问题");
		suggestType.put(4, "指导师培训相关问题");
		suggestType.put(5, "子公司建议相关问题");
		suggestType.put(6, "企业相关问题");
		suggestType.put(7, "连锁企业相关问题");*/
		return suggestType;
	}

	/**
	 * 获取文件后缀格式
	 * 
	 * @param filename
	 * @return 文件后缀格式 eg: (jpg)
	 */
	public static String getFileExt2(String filename) {
		if (StringUtils.isBlank(filename) || filename.lastIndexOf(".") == -1) {
			return "";
		}
		String extName = filename.substring(filename.lastIndexOf(".") + 1)
				.toLowerCase();
		return extName;
	}

	/**
	 * 获取文件后缀格式
	 * @param filename
	 * @return 文件后缀格式 eg:  (.jpg)
	 */
	public static String getFileExt(String filename) {
		if(CommonUtil.isEmpty(filename) || filename.lastIndexOf(".") == -1) {
			return "";
		}
		String extName = filename.substring(filename.lastIndexOf(".")).toLowerCase();
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
	
	/**
	 * 隨機生成指定長度的密碼
	 * 格式：小寫字母+數字
	 * @param length
	 * @return
	 */
	public static String randomPassword(int length){
	    String val = "";     
	             
	    Random random = new Random();     
	    for(int i = 0; i < length; i++)     
	    {     
	        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字     
	                 
	        if("char".equalsIgnoreCase(charOrNum)) // 字符串     
	        {     
	            int choice = 97; //取得大写字母还是小写字母 
	            val += (char) (choice + random.nextInt(26));     
	        }     
	        else if("num".equalsIgnoreCase(charOrNum)) // 数字     
	        {     
	            val += String.valueOf(random.nextInt(10));     
	        }     
	    }     
	             
	    return val;     
	}  
}
