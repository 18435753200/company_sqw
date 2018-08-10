/**
 * 
 */
package com.mall.exception;

/**
 * @author zhengqiang.shi
 * 2014年9月23日 上午11:43:00
 */
public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = -2911979829198660331L;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(Throwable cause) {
		super(cause);
	}

}
