package com.wangsheng.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangsheng.comm.BaseResponse;

@ControllerAdvice
public class ExceptionHandle {

	@ExceptionHandler(value = ServiceException.class)
	@ResponseBody
	public BaseResponse defaultErrorHandler(ServiceException e){
		return new BaseResponse(e.code,e.message);
	}
}
