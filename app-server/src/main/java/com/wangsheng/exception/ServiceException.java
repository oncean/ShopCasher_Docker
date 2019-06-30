package com.wangsheng.exception;

import com.wangsheng.comm.BaseResponse;

public class ServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String code;
	
	public String message;

	public ServiceException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public ServiceException(BaseResponse baseResponse) {
		super(baseResponse.getDesc());
		this.code = baseResponse.getCode();
		this.message = baseResponse.getDesc();
	}
}
