package com.wangsheng.comm;

import com.wangsheng.exception.ServiceException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
	private String code = "0";
	
	private String desc = "success";
	
	private Object result;
	
	public BaseResponse(String code,String desc){
		this.code=code;
		this.desc=desc;
	};
	
	public BaseResponse(Object result){
		this.result=result;
	};
	
	public ServiceException toException(){
		return new ServiceException(this.getCode(), this.getDesc());
	}
}
