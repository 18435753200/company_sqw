package com.mall.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import com.mall.customer.model.User;
import com.mall.vo.ProtectionVO;

// JDK 1.7及以下使用第三方Jar包, JDK 1.8 可以使用JDK集成的 java.util.Base64类 进行编码
// import java.util.Base64;                         // JDK 1.8
// import org.apache.commons.codec.binary.Base64;   // JDK 1.7及以下使用第三方Jar包 commons-codec
import sun.misc.BASE64Encoder; // 本示例使用JDK内置的BASE64Encoder

/**
 * 天御服务API工具类
 */
class BspAPI {
	/* Basic request URL */
	private static final String URL = "csec.api.qcloud.com/v2/index.php";

	/**
	 * 编码
	 * 
	 * @param bstr
	 * @return String
	 */
	private static String encode(byte[] bstr) {
		String s = System.getProperty("line.separator");
		// return Base64.getEncoder().encodeToString(bstr).replaceAll(s, ""); //
		// JDK 1.8 可使用
		// return Base64.encodeBase64String(bstr).replaceAll(s, ""); // JDK
		// 1.7及以下使用第三方Jar包 commons-codec
		return new BASE64Encoder().encode(bstr).replaceAll(s, "");
	}

	/* Signature algorithm using HMAC-SHA1 */
	public static String hmacSHA1(String key, String text) throws InvalidKeyException, NoSuchAlgorithmException {
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA1"));
		return encode(mac.doFinal(text.getBytes()));
	}

	/* Assemble query string */
	private static String makeQueryString(Map<String, String> args, String charset) throws UnsupportedEncodingException {
		String url = "";

		for (Map.Entry<String, String> entry : args.entrySet())
			url += entry.getKey() + "="
					+ (charset == null ? entry.getValue() : URLEncoder.encode(entry.getValue(), charset)) + "&";

		return url.substring(0, url.length() - 1);
	}

	/* Generates an available URL */
	public static String makeURL(String method, String action, String region, String secretId, String secretKey,
			Map<String, String> args, String charset) throws InvalidKeyException, NoSuchAlgorithmException,
			UnsupportedEncodingException {
		SortedMap<String, String> arguments = new TreeMap<String, String>();

		/* Sort all parameters, then calculate signature */
		arguments.putAll(args);
		arguments.put("Nonce", String.valueOf((int) (Math.random() * 0x7fffffff)));
		arguments.put("Action", action);
		arguments.put("Region", region);
		arguments.put("SecretId", secretId);
		arguments.put("Timestamp", String.valueOf(System.currentTimeMillis() / 1000));
		arguments.put("Signature",
				hmacSHA1(secretKey, String.format("%s%s?%s", method, URL, makeQueryString(arguments, null))));

		/* Assemble final request URL */
		return String.format("https://%s?%s", URL, makeQueryString(arguments, charset));
	}

	/* Message-Struct constructor for API `UgcAntiSpam` */
	public abstract static class Message {
		/* Basic TLV structure */
		public abstract static class TLV {
			/* The "T" and "V" part of "TLV" */
			private final int type;
			private final String value;

			public TLV(int type) {
				this(type, "");
			}

			public TLV(int type, String value) {
				this.type = type;
				this.value = value;
			}

			/* Assemble a TLV element */
			public byte[] assemble() {
				byte[] bytes = value.getBytes();
				byte[] result = new byte[bytes.length + 8];

				/* Type */
				result[0] = (byte) ((type & 0xff000000) >>> 24);
				result[1] = (byte) ((type & 0x00ff0000) >>> 16);
				result[2] = (byte) ((type & 0x0000ff00) >>> 8);
				result[3] = (byte) ((type & 0x000000ff) >>> 0);

				/* Length */
				result[4] = (byte) ((bytes.length & 0xff000000) >>> 24);
				result[5] = (byte) ((bytes.length & 0x00ff0000) >>> 16);
				result[6] = (byte) ((bytes.length & 0x0000ff00) >>> 8);
				result[7] = (byte) ((bytes.length & 0x000000ff) >>> 0);

				/* Value */
				System.arraycopy(bytes, 0, result, 8, bytes.length);
				return result;
			}
		}

		/* Message items of the Message-Struct */
		public static class Content extends TLV {
			public Content() {
				super(1);
			}

			public Content(String value) {
				super(1, value);
			}
		}

		public static class ImageURL extends TLV {
			public ImageURL() {
				super(2);
			}

			public ImageURL(String value) {
				super(2, value);
			}
		}

		public static class VideoURL extends TLV {
			public VideoURL() {
				super(3);
			}

			public VideoURL(String value) {
				super(3, value);
			}
		}

		public static class AudioURL extends TLV {
			public AudioURL() {
				super(5);
			}

			public AudioURL(String value) {
				super(4, value);
			}
		}

		public static class WebsiteURL extends TLV {
			public WebsiteURL() {
				super(5);
			}

			public WebsiteURL(String value) {
				super(5, value);
			}
		}

		public static class Emoticon extends TLV {
			public Emoticon() {
				super(6);
			}

			public Emoticon(String value) {
				super(6, value);
			}
		}

		public static class Title extends TLV {
			public Title() {
				super(7);
			}

			public Title(String value) {
				super(7, value);
			}
		}

		public static class Location extends TLV {
			public Location() {
				super(8);
			}
		}

		public static class Custom extends TLV {
			public Custom() {
				super(9);
			}
		}

		public static class File extends TLV {
			public File() {
				super(10);
			}
		}

		public static class Other extends TLV {
			public Other() {
				super(1000);
			}
		}

		/* Factory method of Message-Struct */
		public static String build(TLV... items) {
			int length = 0;
			ArrayList<byte[]> parts = new ArrayList<byte[]>();

			/* Assemble each part */
			for (TLV item : items) {
				byte[] part = item.assemble();

				parts.add(part);
				length += part.length;
			}

			int current = 0;
			byte[] result = new byte[length];

			/* Copy to result */
			for (byte[] part : parts) {
				System.arraycopy(part, 0, result, current, part.length);
				current += part.length;
			}

			/* Encode using Base64 */
			return encode(result);
		}
	}
}

/**
 * Http 请求工具类
 */
class ApiRequest {
	/**
	 * Get 请求
	 *
	 * @param url http请求地址
	 * @param params http请求参数
	 * @return String
	 */
	public static ApiResponse sendGet(String url, String params) {
		BufferedReader br = null;
		ApiResponse response = new ApiResponse();
		try {
			String urlNameString = url;
			if (0 != params.length()) {
				String linkOperator = urlNameString.contains("?") ? "&" : "?";
				urlNameString += linkOperator + params;
			}

			// 打开连接
			HttpURLConnection connection = (HttpURLConnection) new URL(urlNameString).openConnection();

			// 设置请求头
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			connection.connect();

			// 获取请求头字段
			Map<String, List<String>> header = connection.getHeaderFields();

			// 获取URL的输入流，读取请求响应
			String body = readString(connection.getInputStream());
			response.setHeader(header);
			response.setBody(body);

		} catch (Exception e) {
			System.out.println("发送GET请求出现异常!" + e);
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * POST 请求
	 *
	 * @param url http请求地址
	 * @param params http请求参数
	 * @return String
	 */
	public static ApiResponse sendPost(String url, String params) {
		PrintWriter pw = null;
		BufferedReader br = null;
		ApiResponse response = new ApiResponse();
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

			// 设置请求头
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			// 设置 POST
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// Post 请求不能使用缓存
			connection.setUseCaches(false);

			connection.setConnectTimeout(5000);

			// 获取URL的输出流, 发送请求参数
			pw = new PrintWriter(connection.getOutputStream());
			pw.print(params);
			pw.flush();

			// 获取请求头字段
			Map<String, List<String>> header = connection.getHeaderFields();

			// 获取URL的输入流，读取请求响应
			String body = readString(connection.getInputStream());

			response.setHeader(header);
			response.setBody(body);

		} catch (Exception e) {
			System.out.println("发送POST请求出现异常!" + e);
			e.printStackTrace();
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	private static String readString(InputStream is) {
		BufferedReader br = null;
		String content = "";
		try {
			br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String line;
			while ((line = br.readLine()) != null) {
				content += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return content;
	}
}

class ApiResponse {
	private Map header;
	private Object body;

	public ApiResponse() {
		this.header = new TreeMap();
		this.body = "";
	}

	public ApiResponse(Map header, String body) {
		this.header = header;
		this.body = body;
	}

	public void setHeader(Map header) {
		this.header = header;
	}

	public Map getHeader() {
		return this.header;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public Object getBody() {
		return this.body;
	}
}

/* Demo section */
public class LoginProtection {

	/**
	 * 
	 * @param user
	 * @param regIp
	 * @param accountType 用户账号类型 0：其他账号 1：QQ开放帐号 2：微信开放帐号 4：手机号 6：手机动态码 7：邮箱
	 * @param loginSource 登录来源 0：其他 1：PC网页 2：移动页面 3：APP 4：微信公众号
	 * @param loginType 登录方式 0：其他 1：手动帐号密码输入 2：动态短信密码登录 3：二维码扫描登录
	 * @param imei
	 */
	public static ProtectionVO securityLogin(User user, String regIp, String accountType, String loginSource,String loginType, String imei) {
		/* 密钥,请进行替换,密钥申请地址 https://console.qcloud.com/capi */
		ProtectionVO pr = null;
		final String SECRET_ID = "AKIDrbAZmnMKUaXL3atKYlqkXBevGh5Kir7E";
		final String SECRET_KEY = "97tT5bwvzxmQ2HQMYdWDj7IUp592jFco";
		Map<String, String> args = new TreeMap<String, String>();
		/* 帐号信息参数 */
		args.put("accountType", accountType);
		args.put("uid", user.getUserId() + "");
		args.put("nickName", user.getNickName());
		args.put("phoneNumber", "086+" + user.getMobile());
		if (StringUtils.isNotEmpty(user.getEmail())) {
			args.put("emailAddress", user.getEmail());
		}
		Date date = new Date();
		Long registerTime = date.getTime() / 1000;
		// args.put("registerTime", registerTime.toString());
		// args.put("registerIp", regIp);
		// args.put("passwordHash", "f158abb2a762f7919846ee9bf8445c7f22a244c5");

		/* 行为信息参数 */
		args.put("loginIp", regIp);
		args.put("loginTime", registerTime.toString());
		args.put("loginSource", loginSource);
		args.put("loginType", loginType);
		// args.put("referer", "https://ui.ptlogin2.qq.com/cgi-bin/login");
		// args.put("jumpUrl", "D692D87319F2098C3877C3904B304706");
		// args.put("cookieHash", "D692D87319F2098C3877C3904B304706");
		// args.put("userAgent","Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
		// args.put("xForwardedFor", "121.14.96.121");
		// args.put("mouseClickCount", "10");
		// args.put("keyboardClickCount", "34");
		// args.put("result", "2");
		// args.put("reason", "4");

		/* 设备信息 */
		// args.put("macAddress", "00-05-9A-3C-7A-00");
		// args.put("vendorId", "tencent.com");
		if (StringUtils.isNotEmpty(imei)) {
			args.put("imei", imei);
		}
		// args.put("appVersion", "10.0.1");

		/* 其他信息 */
		// args.put("businessId", "1");

		try {
			String url = BspAPI.makeURL("GET", "LoginProtection", "gz", SECRET_ID, SECRET_KEY, args, "utf-8");
			ApiResponse res = ApiRequest.sendGet(url, "");
			pr = JSON.parseObject(res.getBody().toString(), ProtectionVO.class);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pr;
	}

	public static void main(String[] argv) throws InvalidKeyException, NoSuchAlgorithmException,
			UnsupportedEncodingException {
		// /* 密钥,请进行替换,密钥申请地址 https://console.qcloud.com/capi */
		// final String SECRET_ID = "";
		// final String SECRET_KEY = "";
		//
		// Map<String, String> args = new TreeMap<String, String>();
		//
		// /* 帐号信息参数 */
		// args.put("accountType", "5");
		// args.put("uid", "D692D87319F2098C3877C3904B304706");
		// args.put("associateAccount", "373909726");
		// args.put("nickName", "helloword");
		// args.put("phoneNumber", "086+15166666666");
		// args.put("emailAddress", "hellword@qq.com");
		// args.put("registerTime", "1440416972");
		// args.put("registerIp", "121.14.96.121");
		// args.put("passwordHash", "f158abb2a762f7919846ee9bf8445c7f22a244c5");
		//
		// /* 行为信息参数 */
		// args.put("loginIp", "10.23.23.20");
		// args.put("loginTime", "11254");
		// args.put("loginSource", "4");
		// args.put("loginType", "3");
		// args.put("referer", "https://ui.ptlogin2.qq.com/cgi-bin/login");
		// args.put("jumpUrl", "D692D87319F2098C3877C3904B304706");
		// args.put("cookieHash", "D692D87319F2098C3877C3904B304706");
		// args.put("userAgent",
		// "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
		// args.put("xForwardedFor", "121.14.96.121");
		// args.put("mouseClickCount", "10");
		// args.put("keyboardClickCount", "34");
		// args.put("result", "2");
		// args.put("reason", "4");
		//
		// /* 设备信息 */
		// args.put("macAddress", "00-05-9A-3C-7A-00");
		// args.put("vendorId", "tencent.com");
		// args.put("imei", "54654654646");
		// args.put("appVersion", "10.0.1");
		//
		// /* 其他信息 */
		// args.put("businessId", "1");
		//
		// String url = BspAPI.makeURL("GET", "LoginProtection", "gz",
		// SECRET_ID, SECRET_KEY, args, "utf-8");
		// // ApiResponse res = ApiRequest.sendGet(url, "");
		// // System.out.println(res.getBody());
		User user = new User();
		user.setUserId(1266205L);
		user.setMobile("18810159163");
		user.setNickName("188****9163");
		LoginProtection.securityLogin(user, "101.251.232.210", "4", "3", "1", "");
	}
}