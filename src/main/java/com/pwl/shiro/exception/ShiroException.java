package com.pwl.shiro.exception;


public class ShiroException extends RuntimeException{

	private static final long serialVersionUID = -7677648646902145997L;

	public ShiroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ShiroException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ShiroException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ShiroException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
