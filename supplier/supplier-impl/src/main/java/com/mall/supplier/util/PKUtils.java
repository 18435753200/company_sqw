package com.mall.supplier.util;

import org.apache.commons.lang.RandomStringUtils;

/**
 * <p>文件名称: SequenceUtils.java</p>
 * <p>文件描述: 序列主键工具类</p>
 * <p>版权所有: 版权所有(C)2010</p>
 * <p>内容摘要: 序列工具类，用来产生序列</p>
 * <p>其他说明: 其它内容的说明</p>
 * <p>完成日期: 2010-12-15</p>
 * <p>修改记录1:</p>
 * <pre>
 *    修改日期:
 *    修 改 人:
 *    修改内容:
 * </pre>
 * <p>修改记录2：…</p>
 */
public class PKUtils {
	private static int suffixLen = 5;

	/**
	 * @date：2010-12-15
	 * @Description：生成主键
	 * @return 主键
	 */
	public synchronized static String getPrimaryKey () {
		String currentTimeMillisStr = new Long(System.currentTimeMillis()).toString();
		String randomStr = RandomStringUtils.randomNumeric(suffixLen);
		
		return currentTimeMillisStr + randomStr;
	}
	
	/**
	 * @date：2010-12-15
	 * @Description：生成主键
	 * @return 主键
	 */
	public synchronized static Long getLongPrimaryKey () {
		String currentTimeMillisStr = new Long(System.currentTimeMillis()).toString();
		String randomStr = RandomStringUtils.randomNumeric(suffixLen);
		String temp = currentTimeMillisStr + randomStr;
		
		return Long.parseLong(temp);
	}
	
	/**
	 * 

	 * @date：2011-4-2
	 * @Description：批量生成主键
	 * @param count	 主键个数
	 * @return
	 */
	public synchronized static String[] getPrimaryKeyArray (int count) {
		String currentTimeMillisStr = new Long(System.currentTimeMillis()).toString();
		String[] pkSeqArray = new String[count];
		int _suffixLen = 0;
		for (int i = 0;i < count;i++) {
			String iStr = new Integer(i).toString();
			int iCount = iStr.length();
			_suffixLen = suffixLen >= iCount ? suffixLen : iCount;
			String suffixStr = StringUtils.fillLenAtLeft(iStr, _suffixLen, '0');
			pkSeqArray[i] = currentTimeMillisStr + suffixStr;
		}
		return pkSeqArray;
	}
	
	public static void main (String[] args) {
		System.out.println(getPrimaryKey().length());
		String currentTimeMillisStr = new Long(System.currentTimeMillis()).toString();
		System.out.println(currentTimeMillisStr.length());
		for (int i = 0;i < 20;i++) {
			System.out.println(RandomStringUtils.randomNumeric(1));
		}
		
		System.out.println("*******"+getLongPrimaryKey());//140296949984432231
	}
	
}
