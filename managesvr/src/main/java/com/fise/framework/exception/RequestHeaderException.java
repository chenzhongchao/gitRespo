package com.fise.framework.exception;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-14
 * @desc 请求头异常
 */
public class RequestHeaderException extends RuntimeException {
	public RequestHeaderException (String message) {
		super(message);
	}
}
