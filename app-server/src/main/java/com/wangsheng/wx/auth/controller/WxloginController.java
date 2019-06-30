package com.wangsheng.wx.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.comm.BaseResponse;
import com.wangsheng.exception.ServiceException;
import com.wangsheng.wx.auth.service.WxAuthService;

@RequestMapping("/wxAuth")
@RestController
public class WxloginController {
	
	@Autowired
	private WxAuthService wxAuthService;
	
	/**
	 * 微信登录操作
	 * @param code
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public BaseResponse wxLoginAuth(@RequestBody String code) throws ServiceException{
		String token = wxAuthService.login(code);
		return new BaseResponse(token);
	}
}
