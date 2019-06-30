package com.wangsheng.wx.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangsheng.auth.data.UserInfo;
import com.wangsheng.auth.service.UserService;
import com.wangsheng.auth.service.token.TokenManager;
import com.wangsheng.configure.service.ConfigureService;
import com.wangsheng.exception.ServiceException;
import com.wangsheng.wx.auth.model.WxServerLoginRsp;

@Service
public class WxAuthService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ConfigureService configureService;
	
	@Autowired
	private TokenManager tokenManager;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 1、传入用户的code，去微信服务器校验
	 * 2、如果成功，判断用户是否已存在系统中，如果不存在，新增用户
	 * 3、生成用户相关token，返回小程序
	 * 4、如果校验失败，抛出错误
	 * @param code
	 * @return
	 * @throws ServiceException 
	 */
	public String login(String code) throws ServiceException{
		String url =configureService.get("requestUrl");
		url = url.replace("{APPID}", configureService.get("appID"));
		url = url.replace("{SECRET}", configureService.get("secret"));
		url = url.replace("{code}", code);
		String resultString = restTemplate.getForObject(url,String.class);
		JSONObject resultJson = JSONObject.parseObject(resultString);
		WxServerLoginRsp wxServerLoginRsp = JSON.toJavaObject(resultJson, WxServerLoginRsp.class);
		if("0".equals(wxServerLoginRsp.getErrcode())){
			String openId = wxServerLoginRsp.getOpenId();
			//判断用户是否存在
			UserInfo userInfo = userService.getUserByOpenId(openId);
			if(userInfo == null){
				//新建用户 
				userInfo = userService.createUserFromWX(openId);
			}
			return tokenManager.createToken(userInfo.getUserId());
		}else{
			throw new ServiceException(wxServerLoginRsp.getErrcode(), wxServerLoginRsp.getErrmsg());
		}
	}
	
}