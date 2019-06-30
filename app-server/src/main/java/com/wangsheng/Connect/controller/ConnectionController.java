package com.wangsheng.Connect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.Connect.service.ConnectService;
import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.comm.BaseResponse;

/**
 * 获取扫码连接的有关操作
 * 
 * @author 10170
 *
 */
@RestController
@RequestMapping("/connect")
public class ConnectionController {
	
	@Autowired
	private ConnectService connectService;

	@RequestMapping(value="/getConnectCode",method=RequestMethod.GET)
	public BaseResponse getConnectCode(@RequestHeader("Authorization") String token) throws Exception{
		if(StringUtils.isEmpty(token)){
			return ResultConstants.REQ_ERROR;
		}
		BaseResponse rsp = ResultConstants.SUCCESS;
		String connectCode =connectService.getConnectCode(token);
		rsp.setResult(connectCode);
		return rsp;
	}

	@RequestMapping(value="/getTokenByConnectCode/{connectCode}",method=RequestMethod.GET)
	public BaseResponse getTokenByConnectCode(@PathVariable String connectCode) throws Exception{
		if(StringUtils.isEmpty(connectCode)){
			return ResultConstants.REQ_ERROR;
		}
		BaseResponse rsp = ResultConstants.SUCCESS;
		String token =connectService.getTokenByConnectCode(connectCode);
		rsp.setResult(token);
		return rsp;
	}
}
