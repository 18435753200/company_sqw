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
	public final static Integer sendMessage(final String mobile) {
		final int randNum = generateCaptcha();
		String content = "众聚猫提示您，您的验证码是：" + randNum + "。验证码30分钟有效，超时请重新获取。";
		SendSMS.send("d5g6mvjqsm", content, mobile, "1");
		return randNum;

	}
	
	public final static Integer sendMessage(final String mobile, String orgin) {
		final int randNum = generateCaptcha();
		String content = null;
		if("906".equals(orgin)) {//微票
			content = "您的验证码是"+randNum+"； 感谢注册“众聚猫”，畅购全球，与众不同！";
		} else if ("907".equals(orgin)) {
			content = "您的验证码是"+randNum+"； 感谢关注“众聚猫”，畅购全球，与众不同！";
		} else if("910".equals(orgin) || "911".equals(orgin) || "912".equals(orgin)) {
			content = "您的验证码是"+randNum+"； 感谢关注“众聚猫”，畅购全球，与众不同！";
		}else {
			content = "众聚猫提示您，您的验证码是：" + randNum + "。验证码30分钟有效，超时请重新获取。";
		}
		SendSMS.send("d5g6mvjqsm", content, mobile, "1");
		return randNum;

	}
	
	
	/**
	 * 发送验证码
	 * 
	 * @param mobile  手机号 多个之间用(英文逗号 ',') 隔开
	 * @return randNum 六位验证码
	 */
	public final static Integer sendTradeMessage(final String mobile) {
		final int randNum = generateCaptcha();
		String content = "众聚猫提示您，您的支付密码的验证码是：" + randNum + "。验证码30分钟有效，超时请重新获取。";
		SendSMS.send("d5g6mvjqsm", content, mobile, "1");
		return randNum;
	}
	
	public static String sendPassword(String mobile,String password) {
		String content = "亲爱的的用户，恭喜您获得众聚猫480元优惠券！欢迎下载并登录众聚猫APP查看。登录名："+mobile+",密码："+password+"  进口食品美妆母婴商品一站式平台，100%原产地直采，优质低价全新体验！";
		String send = SendSMS.send("d5g6mvjqsm", content, mobile, "1");
		return send;

	}

	public static String sendErrorMessageByTelecom(String mobile,String msg){
		String send = SendSMS.send("d5g6mvjqsm", msg, mobile, "1");
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
