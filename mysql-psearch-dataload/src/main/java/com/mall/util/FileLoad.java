package com.mall.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class FileLoad {
	private static final Logger logger = Logger.getLogger(FileLoad.class);
	private static final String preUrl = "http://image01.ncp365.cn/h0/";
	/**
	* GET方式请求
	* 
	* @param uri
	* 服务器的uri要用物理IP或域名,不识别localhost或127.0.0.1形式!
	* @return
	* @throws IOException
	* @throws ClientProtocolException
	*/
	public static String get(String uri) {
		//throws ClientProtocolException,IOException 
		uri = preUrl + uri;
		HttpGet httpGet = new HttpGet(uri);
		String charset = "UTF-8";
		HttpClient httpClient = new DefaultHttpClient();
		  // 连接超时  
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 5); //链接超时5秒
         // 读取超时  
		httpClient.getParams().setParameter(CoreConnectionPNames. SO_TIMEOUT, 1000 * 5);  //读取超时5秒
		
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
		
			int statusCode;
			if ((statusCode = httpResponse.getStatusLine().getStatusCode()) == 200) {
				String result;
				
				result = EntityUtils.toString(httpResponse.getEntity(),charset);
				//System.out.println(result);
			    result = FileLoad.delHTMLTag(result);
				//System.out.println(result);
				//logger.info("获取文件status is OK:" + statusCode);
				return result;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.info("获取文件status is failed,ParseException:", e);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			logger.info("获取文件status is failed,ClientProtocolException:", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("获取文件status is failed,IOException:", e);
		}
		return "";
	}
	
	/**
	 * 过滤html标签
	 * @param htmlStr
	 * @return
	 */
	 public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    } 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String uri = "group1/M00/00/38/wKg9M1dFRyGALdr3AAABZa589gU4168097";
		String uri = "group1/M00/04/E2/wKgFCVZviGyAUIb4AAANHxsXkU87400279";
		try {
			FileLoad.get(uri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
