package com.fise.framework.exception;

/** 
 * @author 大表哥
 * @email xingyuanzuo@gmail.com
 * @date 2016-8-6
 * @desc 鉴权异常
 */
public class AuthException extends RuntimeException {
	public AuthException (String message) {
		super(message);
	}
}
