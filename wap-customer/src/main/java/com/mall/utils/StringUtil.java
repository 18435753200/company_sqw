package com.mall.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

/**
 * 处理字符串以及数组
 * @author yuanxiayang
 *
 */
public class StringUtil {
	
	public static void distinct(String [] str,Model model){
		
		//倒叙
		List<String> list = new ArrayList<String>();
		
		for (int i=str.length-1;i>=0;i--) {
			if(!list.contains(str[i])){
				list.add(str[i]);
			}
		}
		List<String> list2 = new ArrayList<String>();
		//如果大于10,只取后10位，并倒叙排列
		if(list.size()>10){
			for(int i=0;i<10;i++){
				list2.add(list.get(i));
			}
			model.addAttribute("cookieVlaues", list2);
		}else{
			for(int i=0;i<list.size();i++){
				list2.add(list.get(i));
			}
			model.addAttribute("cookieVlaues", list2);
		}
	}
	
	public static String distinct(String [] str){
		
		//倒叙
		List<String> list = new ArrayList<String>();
		
		for (int i=str.length-1;i>=0;i--) {
			if(!list.contains(str[i])){
				list.add(str[i]);
			}
		}
		List<String> list2 = new ArrayList<String>();
		//如果大于10,只取后10位，并倒叙排列
		if(list.size()>10){
			for(int i=0;i<10;i++){
				list2.add(list.get(i));
			}
			return getStringBuffer(list2);
		}else{
			for(int i=0;i<list.size();i++){
				list2.add(list.get(i));
			}
			return getStringBuffer(list2);
		}
	}
	
	public static String getStringBuffer(List<String> list){
		StringBuilder sb= new StringBuilder();
		for(int i=0;i<list.size();i++){
			if(list.size()==1){
				sb.append(list.get(i));
			}else{
				sb.append(list.get(i)+"_");
			}
		}
		return sb.toString();
	}
	
	/** 
     * 获取当前网络ip 
     * @param request 
     * @return 
     */  
    public static String getIpAddr(HttpServletRequest request){  
        String ipAddress = request.getHeader("x-forwarded-for");  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getRemoteAddr();  
                if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                    //根据网卡取本机配置的IP  
                    InetAddress inet=null;  
                    try {  
                        inet = InetAddress.getLocalHost();  
                    } catch (UnknownHostException e) {  
                        e.printStackTrace();  
                    }  
                    ipAddress= inet.getHostAddress();  
                }  
            }  
            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
                if(ipAddress.indexOf(",")>0){  
                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
                }  
            }  
            return ipAddress;   
    } 
    
    
    
    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     * @author lzf
     */
    private static String TruncateUrlPage(String strURL){
        String strAllParam=null;
        String[] arrSplit=null;
        strURL=strURL.trim().toLowerCase();
        arrSplit=strURL.split("[?]");
        if(strURL.length()>1){
          if(arrSplit.length>1){
              for (int i=1;i<arrSplit.length;i++){
                  strAllParam = arrSplit[i];
              }
          }
        }
        return strAllParam;   
    }
    
    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     * @author lzf
     */
    public static Map<String, String> urlSplit(String URL){
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit=null;
        String strUrlParam=TruncateUrlPage(URL);
        if(strUrlParam==null){
            return mapRequest;
        }
        arrSplit=strUrlParam.split("[&]");
        for(String strSplit:arrSplit){
              String[] arrSplitEqual=null;         
              arrSplitEqual= strSplit.split("[=]");
              //解析出键值
              if(arrSplitEqual.length>1){
                  //正确解析
                  mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
              }else{
                  if(arrSplitEqual[0]!=""){
                  //只有参数没有值，不加入
                  mapRequest.put(arrSplitEqual[0], "");       
                  }
              }
        }   
        return mapRequest;   
    }

}
