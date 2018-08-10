/**
 * 
 */
package com.mall.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * @author gaojianping
 *  
 * 2014年9月10日
 */
public class JsonConvertException extends NestedRuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 590422274179778425L;

	/**
	 * Create a new JsonConvertException.
	 * 
	 * @param msg
	 *            the detail message
	 * @param cause
	 *            the root cause (if any)
	 */
	public JsonConvertException(String msg, Throwable cause) {
		super(msg, cause);
	}
}