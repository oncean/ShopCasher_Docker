package com.wangsheng.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.comm.BaseResponse;
import com.wangsheng.log.AopLog;

@RestController
@AopLog
public class TestController {

	@RequestMapping("/test")
	public BaseResponse testMyProject(){
		return new BaseResponse("0","success");
	} 
}
