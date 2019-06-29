package com.wangsheng.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.auth.dao.UserDao;
import com.wangsheng.auth.data.UserInfo;
import com.wangsheng.auth.model.LoginRequest;
import com.wangsheng.auth.service.token.TokenManager;
import com.wangsheng.auth.service.token.model.TokenModel;
import com.wangsheng.comm.BaseResponse;

@RestController
public class LoginController	{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TokenManager tokenMangager;
	
	@RequestMapping(value="/login/auth",method=RequestMethod.POST)
	public BaseResponse loginAuth(@RequestBody LoginRequest req){
		UserInfo user = userDao.getByUserNameOrPhoneOrEmail(req.getUsername(),req.getUsername(),req.getUsername());
		if(null == user){
			return ResultConstants.NO_USER;
		}else if(req.getPassword().equals(user.getPassword())){
			BaseResponse rsp = ResultConstants.SUCCESS;
			TokenModel tokenModel = new TokenModel();
			tokenModel.setUserId(user.getUserId());
			String token = tokenMangager.createToken(tokenModel);
			tokenModel.setToken(token);
			rsp.setResult(tokenModel);
			return rsp;
		}
		return ResultConstants.ERROR_PASS;
	}
}
