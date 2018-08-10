package com.mall.utils;

import com.mall.pay.common.StringUtils;

public class SensitiveInfoUtils {

	private static final char SYMBOL = '*';

	/**
	 * 手机号脱敏 脱敏规则：隐藏后四位 例如：18922221143 脱敏为189****1143
	 * 
	 * @param phoneNum
	 * @return
	 */
	public static String phoneNumHyposensitize(String phoneNum) {
		if (StringUtils.isBlank(phoneNum))
			return "";
		return StringUtils.left(phoneNum, 3).concat(StringUtils.removeStart(
				StringUtils.leftPad(StringUtils.right(phoneNum, 4), StringUtils.length(phoneNum), SYMBOL), "***"));

	}
}
