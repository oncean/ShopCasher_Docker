package com.wangsheng.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.auth.service.UserService;
import com.wangsheng.auth.service.token.TokenUtils;
import com.wangsheng.auth.service.token.constant.TokenConstants;
import com.wangsheng.comm.BaseResponse;
import com.wangsheng.exception.ServiceException;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/checkIfNew")
	public BaseResponse checkIfNewUser(@RequestHeader(TokenConstants.HEADER_TOKEN) String token) throws ServiceException {
		String userId = TokenUtils.getCurrencyUserId(token);
		return new BaseResponse(userService.checkIfNewUser(userId));
	}
}
