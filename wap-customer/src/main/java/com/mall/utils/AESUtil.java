package com.mall.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;




public class AESUtil {
	private static byte[] KEY = { 97, 53, 48, 99, 49, 115, 50, 49, 48, 50, 110, 51, 54, 56, 57, 113 };

	private static final String IV = "1567895450321478";

//	// 加密
//	public static String encrypt(String sSrc) {
//
//		try {
//			SecretKeySpec skeySpec = new SecretKeySpec(KEY, "AES");
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
//			IvParameterSpec iv = new IvParameterSpec(IV.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
//			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//			try {
//				byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
//				return new Base64Encoder().encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//				return "";
//			}
//		} catch (IllegalBlockSizeException e) {
//			e.printStackTrace();
//			return "";
//		} catch (BadPaddingException e) {
//			e.printStackTrace();
//			return "";
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//			return "";
//		} catch (NoSuchPaddingException e) {
//			e.printStackTrace();
//			return "";
//		} catch (InvalidKeyException e) {
//			e.printStackTrace();
//			return "";
//		} catch (InvalidAlgorithmParameterException e) {
//			e.printStackTrace();
//			return "";
//		}
//	}

//	// 解密
//	public static String decrypt(String sSrc) {
//		try {
//			SecretKeySpec skeySpec = new SecretKeySpec(KEY, "AES");
//			// System.out.println("1"+skeySpec);
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//			// System.out.println("2"+cipher);
//			IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
//			// System.out.println("3"+iv);
//			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//			// System.out.println("4"+iv);
//			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(new String(sSrc.getBytes("UTF-8"), "UTF-8"));// 先用base64解密
//			// System.out.println("5"+encrypted1);
//			try {
//				byte[] original = cipher.doFinal(encrypted1);
//				// System.out.println("6"+encrypted1);
//				String originalString = new String(original);
//				// System.out.println("7"+originalString);
//				return originalString;
//			} catch (Exception e) {
//				System.out.println(e.toString());
//				return null;
//			}
//		} catch (Exception ex) {
//			System.out.println(ex.toString());
//			return null;
//		}
//	}

	public static void main(String[] args) throws Exception {
//		try {
//			String password = URLEncoder.encode(AESUtil.encrypt("111111"), "UTF-8");
//			System.out.println(password);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		String s = "aNnWw6g9twKln%2BcC2e7wnA%3D%3D";
		System.out.println(URLDecoder.decode(s, "UTF-8"));
//		System.out.println(decrypt(URLDecoder.decode(s, "UTF-8")));
		/*
		 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
		 * 此处使用AES-128-CBC加密模式，key需要为16位。
		 */
		// String cKey = "1234567890abcdef";
		// // 需要加密的字串
		// String cSrc = "user＝15063142270,pwd＝110120";
		// System.out.println(cSrc);
		// // 加密
		// long lStart = System.currentTimeMillis();
		// String enString = AESUtil.Encrypt(cSrc, cKey);
		// System.out.println("加密后的字串是：" + enString);
		//
		// long lUseTime = System.currentTimeMillis() - lStart;
		// System.out.println("加密耗时：" + lUseTime + "毫秒");
		// // 解密
		// lStart = System.currentTimeMillis();
		// String DeString = AESUtil.Decrypt(
		// "TOjSolgVnmr0Gv9wWT1BUctFmdkHKUq16KQiyLKrMq4=", cKey);
		// System.out.println("解密后的字串是：" + DeString);
		// lUseTime = System.currentTimeMillis() - lStart;
		// System.out.println("解密耗时：" + lUseTime + "毫秒");

		// --------------------------------------------------------------
		/*
		 * String pwd = URLEncoder.encode(AESUtil.encrypt("a12345"));
		 * System.out.println("===========:"+pwd); String decoder =
		 * URLDecoder.decode(pwd, "UTF-8");
		 * 
		 * String aaa = decrypt(decoder);
		 * System.out.println("=============:"+aaa);
		 * 
		 * String pwd1 = URLEncoder.encode(AESUtil.encrypt("a123456"));
		 * System.out.println(pwd1);
		 * 
		 * String decoder1 = URLDecoder.decode(pwd, "UTF-8");
		 * 
		 * String aaa1 = decrypt(decoder1);
		 * System.out.println("=============:"+aaa1);
		 */

		// ----------------------------------------------------------------
	}

}