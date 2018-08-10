package com.mall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.sendMessage.SendSMS;

/**
 * 发送验证码工具类
 * 
 * @author dell003
 * 
 */
public class SendSMSUtil {

	/**
	 * slf4j log4j
	 */
	private static Logger logger = LoggerFactory.getLogger(SendSMSUtil.class);

	/**
	 * 发送验证码
	 * 
	 * @param mobile
	 *            手机号 多个之间用(英文逗号 ',') 隔开
	 * @return randNum 六位验证码
	 */
	public final static String sendMessage(String mobile,String password,String name) {
		String content = "尊敬的"+name+"会员,感谢您注册加入众聚商城。目前，众聚商城正在跑步开发过程中，第一期已经上线，您的登录账户和密码如下："
				+ "登录名："+mobile+" 密码："+password+"  ";
		String send = SendSMS.send("d5g6mvjqsm", content, mobile, "1");
		return send;

	}
	
	/**
	 * 发送验证码
	 * 
	 * @param mobile
	 *            手机号 多个之间用(英文逗号 ',') 隔开
	 * @return randNum 六位验证码
	 */
	public final static Integer sendMessage(final String mobile) {
		final int randNum = generateCaptcha();
		String content = "众聚商城提示您，您的验证码是：" + randNum + "。验证码30分钟有效，超时请重新获取。";
		SendSMS.send("d5g6mvjqsm", content, mobile, "1");
		return randNum;

	}
	/**
	 * <p>创建账号发送短信</p>
	 * @param mobile
	 * @return
	 * @auth:zw
	 */
	public final static String sendCreateAccountMessage(final String mobile,final String password,final String name,final String username) {
		String content = "您已经创建帐号成功，企业账号用户名："+name+", 密码："+password+"  与此关联的企业个人账号用户名："+username+
				", 密码： "+password+" 请您及时登陆修改。";
		String send = SendSMS.send("d5g6mvjqsm", content, mobile, "1");
		return send;

	}

	/**
	 * 生成验证码
	 * 
	 * @return
	 */
	private synchronized static int generateCaptcha() {
		return new Random().nextInt(899999) + 100000;
	}

	/**
	 * 获取当前时间的时间戳串
	 * 
	 * @param (format)
	 * @return String Timestamp
	 */
	public static String getNowStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}
}
