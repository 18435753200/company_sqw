package com.mall.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mall.comment.po.UserInfo;

/**
 * 微信用户授权接口
 * 
 * @author wenjian
 */
@Component
public class Oauth {

    private static final String CODE_URI = "http://open.weixin.qq.com/connect/oauth2/authorize";
    private static final String TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static final String REFRESH_TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    private static final String USER_INFO_URI = "https://api.weixin.qq.com/sns/userinfo";
    
    private static final String ACCESS_TOKEN_URI = "https://api.weixin.qq.com/cgi-bin/token";
    
    private static final String JSAPI_TICKET_URI = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    
/*    public static final String APPID = "wx4c4a74a571a985da";
    private static final String APPSECRET = "566c1c0f2c7e81bac507532129fd5b85";
    
    private static final String SECURITY_KEY = "1dc3d365c68a468cba5d37fd3f09bc3b";*/

    // 聚众猫
    public static final String APPID = "wx56967d6f3bd56bf9";
    private static final String APPSECRET = "ee5dbdf9e7c24f474f045a72563ce84d";

    
    
//    private static final String APPID = "wx229b9fe47c113b0d";
//    private static final String APPSECRET = "16010ec86365f3f4b026262bbd4045eb";
//    
    private static final Logger log = LoggerFactory.getLogger(Oauth.class);
    
    //@Autowired
	//private static IWeixinKitService weixinKit = new WeixinKitServiceImpl();
    
    
    
    /**
     * 请求code
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String getCode(String redirectUri, boolean isNeedUserInfo) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", APPID);
        params.put("response_type", "code");
        params.put("redirect_uri", redirectUri);
        if(isNeedUserInfo){
        	params.put("scope", "snsapi_userinfo");
        } else {
        	params.put("scope", "snsapi_base");
        }
        // snsapi_base（不弹出授权页面，只能拿到用户openid）snsapi_userinfo
        // （弹出授权页面，这个可以通过 openid 拿到昵称、性别、所在地）
        params.put("state", "STATE#wechat_redirect");
        String para = MapUtil.createSign(params, false);
        log.info("授权登录的url为:"+ para);
        return CODE_URI + "?" + para;
    }
    
    /**
     * 请求code
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String getCode(String redirectUri, boolean isNeedUserInfo, String state) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", APPID);
        params.put("response_type", "code");
        params.put("redirect_uri", redirectUri);
        if(isNeedUserInfo){
        	params.put("scope", "snsapi_userinfo");
        } else {
        	params.put("scope", "snsapi_base");
        }
        // snsapi_base（不弹出授权页面，只能拿到用户openid）snsapi_userinfo
        // （弹出授权页面，这个可以通过 openid 拿到昵称、性别、所在地）
        params.put("state", state + "#wechat_redirect");
        String para = MapUtil.createSign(params, false);
        return CODE_URI + "?" + para;
    }

    /**
     * 通过code 换取 access_token
     * @param code
     * @return
     * @throws IOException 
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    public static String getToken(String code) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("appid="+APPID);
        sb.append("&secret="+APPSECRET);
        sb.append("&code="+code);
        sb.append("&grant_type="+ "authorization_code");
        String requestUrl = TOKEN_URI+ "?" + sb.toString();
        return HttpClientUtil.getHttp(requestUrl);
    }
    
    /**
     * 通过code 换取 access_token
     * @param code
     * @return
     * @throws IOException 
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    public static String getAccessToken() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=client_credential");
        sb.append("&appid="+APPID);
        sb.append("&secret="+APPSECRET);
        String requestUrl = ACCESS_TOKEN_URI+ "?" + sb.toString();
        return HttpClientUtil.getHttp(requestUrl);
    }
    /**
     * 获取jsapi_ticket
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static String getJsapiTicket(String accessToken) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("access_token=" + accessToken);
        sb.append("&type=jsapi");
        String requestUrl = JSAPI_TICKET_URI+ "?" + sb.toString();
        return HttpClientUtil.getHttp(requestUrl);
    }

    /**
     * 刷新 access_token
     * @param refreshToken
     * @return
     * @throws IOException 
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    public static String getRefreshToken(String refreshToken) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", APPID);
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);
        return HttpKit.get(REFRESH_TOKEN_URI, params);
    }
    
    /**
	 * 拉取用户信息
	 * @param accessToken 这个AccessToken是这里临时的，不是全局的那个
	 * @param openid
	 * @return
	 * @throws IOException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	public static UserInfo getUserInfo(String openid) throws Exception {
//		IWeixinKitService weixinKit = WeixinKitFactory.getWeixinKit();
		//String accessToken = weixinKit.getAccessToken();
		String accessToken = "weixinKit.getAccessToken()";
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		params.put("openid", openid);
		params.put("lang", "zh_CN");
		String  jsonStr = HttpKit.get(USER_INFO_URI, params);
		if(StringUtils.isNotEmpty(jsonStr)){
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if(obj.get("errcode") != null){
				throw new Exception(obj.getString("errmsg"));
			}
			UserInfo user = JSONObject.toJavaObject(obj, UserInfo.class);
			return user;
		}
		return null;
	}
	//权限验证签名
	public static Map<String, String> signature(String jsapiTicket, String url) throws Exception {
		Map<String, String> dataMap = new TreeMap<String, String>();
		dataMap.put("jsapi_ticket", jsapiTicket);
		dataMap.put("noncestr", nonceStr());
		dataMap.put("timestamp", new SimpleDateFormat("yyyyHHmmss").format(new Date()));
		dataMap.put("url", url);
		dataMap.put("signature", sign(dataMap));
		dataMap.put("appid", APPID);
		
        return dataMap;
    }
	
	/**
	 * 生成32位随机数
	 * @return
	 */
	public static String nonceStr() {
		return UUID.randomUUID().toString().replace("-","");
	}
	
	/**
	 * 签名
	 * @param packageParams
	 * @param privateKey
	 * @return
	 */
	public static String sign(Map<String, String> values) {
		String signature = null;
		StringBuffer sb = new StringBuffer();
		// 排序
		List<String> keys = new ArrayList<String>(values.keySet());
		Collections.sort(keys);
		// 拼接
		for (int i=0, len=keys.size(); i < len; i++) {
			sb.append(keys.get(i) + "=" + values.get(keys.get(i)) + "&");
		}
		
		try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sb.toString().getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		
		return signature;
	}
	/**
     * byte转Hex
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    /**
   	 * js高级接口签名
   	 * @param jsapi_ticket
   	 * @param url
   	 * @return
   	 */
       public static Map<String, String> sign(String jsapi_ticket, String url) {
           Map<String, String> ret = new HashMap<String, String>();
           String nonce_str = UUID.randomUUID().toString();
           String timestamp = Long.toString(System.currentTimeMillis() / 1000);
           String string1;
           String signature = "";

           //注意这里参数名必须全部小写，且必须有序
           string1 = "jsapi_ticket=" + jsapi_ticket +
                   "&noncestr=" + nonce_str +
                   "&timestamp=" + timestamp +
                   "&url=" + url;
           try {
               MessageDigest crypt = MessageDigest.getInstance("SHA-1");
               crypt.reset();
               crypt.update(string1.getBytes("UTF-8"));
               signature = byteToHex(crypt.digest());
           } catch (NoSuchAlgorithmException e) {
               e.printStackTrace();
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           }

           ret.put("url", url);
           ret.put("jsapi_ticket", jsapi_ticket);
           ret.put("noncestr", nonce_str);
           ret.put("timestamp", timestamp);
           ret.put("signature", signature);
           ret.put("appid", APPID);

           return ret;
       }
    
}
