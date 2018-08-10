package com.mall.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author zhouzb
 *
 */
public class DateTool extends org.apache.commons.lang3.time.DateUtils{
	
	/**
	 * 标准日期格式.
	 */
	private static final String DAY_PATTERN = "yyyy-MM-dd";
	
	/**
	 * 标准时间格式.
	 */
	private static final String TIME_PATTERN = "HH:mm:ss";
	
	/**
	 * SimpleDateFormat 私有方法 方便所有方法调用.
	 */
	//private static SimpleDateFormat formatTool  = null;
	/**
	 *   将Date格式化成符合默认日期格式的字符串.
	 *
	 * @param date Util Date
	 * @return 返回样例:2012-03-29
	 */
    public static String formatDate(Date date) {
    	
    	SimpleDateFormat formatTool = new SimpleDateFormat();
        formatTool.applyPattern(DAY_PATTERN +" "+TIME_PATTERN);
        return formatTool.format(date);
    
    }
 
    /**
     * 将Date格式化成符合默认时间格式的字符串.
     *
     * @param date Util Date
     * @return 返回样例:14:32:23
     */
    public static String formatTime(Date date) {
    		SimpleDateFormat formatTool = new SimpleDateFormat();
	        formatTool.applyPattern(TIME_PATTERN);
	        return formatTool.format(date);
	    }
	 
	    /**
	     * 按照pattern格式格式化Date.
	     *
	     * @param date Util Date
	     * @param pattern 样例: yyyy/MM/dd
	     * @return 返回样例:2012/03/29
	     */
	    public static String format(Date date, String pattern) {
	       SimpleDateFormat formatTool = new SimpleDateFormat();
	        formatTool.applyPattern(pattern);
	        return formatTool.format(date);
	    }
	 
	    /**
	     * 将符合默认格式的字符串转换成Date.
	     *
	     * @param dateValue
	     *            样例:2012-03-29 14:32:23
	     * 
	     * @throws ParseException ParseException
	     * @return Date
	     */
	    public static Date parse(String dateValue) throws ParseException {
	       SimpleDateFormat formatTool = new SimpleDateFormat();
	        formatTool.applyPattern(DAY_PATTERN + " " + TIME_PATTERN);
	        return formatTool.parse(dateValue);
	    }
 
     /**
	     * 将符合默认格式的日期字符串转换成Date.
	     *
	     * @param dateValue
	     *            样例:2012-03-29
	     * 
	     * @throws ParseException String
	     * @return Date
	     */
	    public static Date parseDate(String dateValue) throws ParseException {
	       SimpleDateFormat formatTool = new SimpleDateFormat();
	        formatTool.applyPattern(DAY_PATTERN);
	        return formatTool.parse(dateValue);
	    }
 
	    /**.
	     * 将符合pattern格式的dateValue转换成Date.
	     *
	     * @param dateValue String
	     *            样例:2012/03/29
	     * @param pattern String
	     *            样例:yyyy/MM/dd 
	     * @return Date
	     * @throws ParseException ParseException
	     */
	    public static Date parse(String dateValue, String pattern) throws ParseException{
	    	SimpleDateFormat formatTool = new SimpleDateFormat();
	    	formatTool.applyPattern(pattern);
	    	return formatTool.parse(dateValue);
	    }
	    
	    /**
	     * 指定时间加天后的时间.
	     * @param date 增加之前的日期
	     * @param num 增加的天数
	     * @return Date 返回的日期
	     */
	    public static Date add(Date date , int num ){
	    	Calendar a = Calendar.getInstance();
	    	a.setTime(date);
	    	a.add(Calendar.DAY_OF_MONTH, num);
	    	return a.getTime();
	    }
	    /**
	     * 判断时间先后  1.指定时间在当前时间之后.
	     * @param  beforDate beforDate
	     * @param  afterDate afterDate
	     * @throws ParseException  ParseException
	     * @return boolean
	     */
	    public static boolean isAfterTime(Date beforDate,Date afterDate) throws ParseException {
	    	boolean b = beforDate.after(afterDate);
	    	return b;
	    }
	    
	    
		public static int getDateYear(Date datetime) {
	        int year = 0;
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(datetime);
	        year = cal.get(Calendar.YEAR);// 获取年份
	        return year;
	    }
		
		/**
		 * @return 时间戳.
		 */
		public static String getLeanTime(){
			
        	Date date = new Date();
    	    long time = date.getTime();
        	String dateline = time + "";
        	dateline = dateline.substring(Constants.INT0, Constants.INT10);
        	
        	return dateline;
		}
		
		/**
		 * 时间大小比较
		 * @param dateOne
		 * @param dateAnother
		 * @return
		 */
		public static int compareTime(Date dateOne,Date dateAnother){
			int result = -2;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				 Date newDate = sdf.parse(sdf.format(dateOne));
				 Date endDate = sdf.parse(sdf.format(dateAnother));
				 result = newDate.compareTo(endDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	         return result;
		}
	}

	
