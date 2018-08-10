/**
 * 
 */
package com.mall.exception;

/**
 * @author zhengqiang.shi
 * 2014年9月23日 上午11:43:00
 */
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = -2911979829198660331L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}
