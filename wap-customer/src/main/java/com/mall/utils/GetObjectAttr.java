package com.mall.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * toString
 * @author yuanxiayang
 *
 */
public class GetObjectAttr {
	
	
	public static String getFiledName(Object o){    
		try{  
			Field[] fields = o.getClass().getDeclaredFields();  
			//String[] fieldNames = new String[fields.length]; 
			
			//自定义map
			Map<String, String> map = new HashMap<String, String>();
			
			for (int i=0; i < fields.length; i++){
				//初始化value为null
				String value = null;
				//获取属性名称
				String key = fields[i].getName();
				//获取属性值
				Object fieldValueByName = getFieldValueByName(fields[i].getName(), o);
				
				if(fieldValueByName!=null){
					value = getFieldValueByName(fields[i].getName(), o).toString();
				}
				map.put(key, value);
			}    
				return map.toString();
		} catch (SecurityException e){ 
			e.printStackTrace();  
			System.out.println(e.toString());
			return o.toString();
		}
		} 
	
	
	//根据属性名获得属性值
    private static Object getFieldValueByName(String fieldName, Object o) { 
        try {  
            String firstLetter = fieldName.substring(0, 1).toUpperCase();  
            String getter = "get" + firstLetter + fieldName.substring(1);  
            Method method = o.getClass().getMethod(getter, new Class[] {});  
            Object value = method.invoke(o, new Object[] {});  
            return value;  
        } catch (Exception e) {  
            return null;  
        }  
    } 
	
	/*public static void main(String[] args) {
		
		PageBean pg = new PageBean();
		
		pg.setPc(1);
		pg.setPs(2);
		
		
		String str = getFiledName(pg);
		
		System.out.println(str);
		
	}*/
}
