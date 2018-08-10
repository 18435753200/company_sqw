package com.mall.utils;

import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public static String getHttp(String urlStr) throws Exception { 
		
		String strCon = "";//返回的内容
		
		//Random random1 = new Random(100);
		
		//urlStr = urlStr + "?r=" + random1.nextInt();
		
		// 创建HttpClient实例     
        HttpClient httpclient = new DefaultHttpClient();  
        // 创建Get方法实例     
        HttpGet httpgets = new HttpGet(urlStr);   
        logger.info("回传订单链接: " + urlStr);
        try {
			HttpResponse response = httpclient.execute(httpgets);    
			HttpEntity entity = response.getEntity(); 
			
			if (entity != null) {  
				//logger.info("Response content length: " + entity.getContentLength());
				strCon = EntityUtils.toString(entity);
				logger.info("Response content: " + strCon);
			    EntityUtils.consume(entity);  
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("订单信息回传http失败");
			logger.error("HttpClientUtil:" + e,e.fillInStackTrace());
			throw new Exception(e);
		}
		
        return strCon;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {//
			//HttpClientUtil.getHttp("http://o.yiqifa.com/servlet/handleCpsIn?ta=1|2&dt=m&os=&ps=&pw=&far=&fav=&fac=&cid=101&wi=67yundl&on=504686452811382544&pp=259.00&sd=2015-11-07+10%3A48%3A48&pna=%E5%BE%B7%E5%9B%BD+%E7%A2%A7%E7%84%B6%E5%BE%B7+Brita+%E9%87%91%E5%85%B8%E6%BB%A4%E6%B0%B4%E5%A3%B6+%E5%8D%95%E8%8A%AF%E8%A3%85+2.4L++&pn=3652990621475345&encoding=utf-8");
			//String aa = URLEncoder.encode("A100217250|10|5|wap|","utf-8");
			//HttpClientUtil.getHttp("http://service.linktech.cn/purchase_cps.php?a_id="+aa+"&m_id=xsj&mbr_id=11111111111&o_cd=504703655135765533&p_cd=2356641566954198&price=79.0&it_cnt=1&c_cd=00000000000");
			//HttpClientUtil.getHttp("http://service.linktech.cn/purchase_cps.php?a_id=A100217250&m_id=xsj&mbr_id=11111111111&o_cd=504703655135765534&p_cd=2356641566954198&price=79.0&it_cnt=1&c_cd=00000000000");
			System.out.println(URLEncoder.encode("%7C","utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
