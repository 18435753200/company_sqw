package com.mall.utils;

public class ProduceUserName {

	/*public static void main(String[] args) {
		System.out.println(produce("11111111111"));
	}*/

	public static String produce(String mobile) {
		String NNN = mobile.substring(0, 3);
		String xxxx = "";
		for (int i = 0; i < 4; i++) {
			String chars = "abcdefghijklmnopqrstuvwxyz";
			Character c = chars.charAt((int) (Math.random() * 26));
			xxxx += c.toString();
		}
		String MMMM = mobile.substring(7);
		//System.out.println(xxxx);
		//System.out.println(NNN);
		//System.out.println(MMMM);
		String userName = NNN+xxxx+MMMM;
		return userName;

	}
}
