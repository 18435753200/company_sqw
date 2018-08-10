package com.mall.utils;
import java.util.Calendar;

/**
 * 处理字符串以及数组
 * @author michuang
 * 
 */
public class DateFormatUtil {

	/**
	 * 获取当前时间距离00:00有多少秒
	 * @return overplus second
	 */
	public static int getDifference() {
	    final Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    final double diff = cal.getTimeInMillis() - System.currentTimeMillis();
	    int seconds=(new Double(diff/1000)).intValue();
	    return seconds;
	}
	/**
	 * 获取每天距离当天10点钟剩余多少秒
	 * @return overplus second
	 */
	public static int getDifferenceByTen() {
	    final Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.HOUR_OF_DAY, 10);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    final double diff = cal.getTimeInMillis() - System.currentTimeMillis();
	    int seconds=(new Double(diff/1000)).intValue();
	    return seconds;
	}
}
