package com.wangsheng.wx.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.wangsheng.wx.auth.model.WxGetAccessTokenRsp;
import java.util.Date;

@Service
public class WxAuthService {
	public static Logger logger = LoggerFactory.getLogger(WxAuthService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ConfigureService configureService;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private UserService userService;

	// token过期时间
	private static Date token_expires_in;

	// token
	private static String access_token;

	private static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}";

	private static String DATABASE_UPDATE_URL = "https://api.weixin.qq.com/tcb/databaseupdate?access_token=";

	private static String DATABASE_QUERY_URL = "https://api.weixin.qq.com/tcb/databasequery?access_token=";
	
	private static String DATABASE_ENV = "shopcasher-46rcu";

	/**
	 * 1、传入用户的code，去微信服务器校验 2、如果成功，判断用户是否已存在系统中，如果不存在，新增用户 3、生成用户相关token，返回小程序
	 * 4、如果校验失败，抛出错误
	 * 
	 * @param code
	 * @return
	 * @throws ServiceException
	 */
	public String login(String code) throws ServiceException {
		String url = configureService.get("requestUrl");
		url = url.replace("{APPID}", configureService.get("appID"));
		url = url.replace("{SECRET}", configureService.get("secret"));
		url = url.replace("{code}", code);
		String resultString = restTemplate.getForObject(url, String.class);
		JSONObject resultJson = JSONObject.parseObject(resultString);
		WxServerLoginRsp wxServerLoginRsp = JSON.toJavaObject(resultJson, WxServerLoginRsp.class);
		if ("0".equals(wxServerLoginRsp.getErrcode())) {
			String openId = wxServerLoginRsp.getOpenId();
			// 判断用户是否存在
			UserInfo userInfo = userService.getUserByOpenId(openId);
			if (userInfo == null) {
				// 新建用户
				userInfo = userService.createUserFromWX(openId);
			}
			return tokenManager.createToken(userInfo.getUserId());
		} else {
			throw new ServiceException(wxServerLoginRsp.getErrcode(), wxServerLoginRsp.getErrmsg());
		}
	}

	public String getAccessToken(){
		if (token_expires_in == null || token_expires_in.before(new Date())) {
			String url = GET_ACCESS_TOKEN_URL;
			url = url.replace("{APPID}", configureService.get("appID"));
			url = url.replace("{APPSECRET}", configureService.get("secret"));

			String resultString = restTemplate.getForObject(url, String.class);
			logger.info("get access_token from weixin server the result is {}",resultString);
			JSONObject resultJson = JSONObject.parseObject(resultString);
			WxGetAccessTokenRsp wxGetAccessTokenRsp = JSON.toJavaObject(resultJson, WxGetAccessTokenRsp.class);
			token_expires_in = new Date();

			long time = (wxGetAccessTokenRsp.getExpires_in() - 10) * 1000;
			token_expires_in = new Date(token_expires_in.getTime() + time);
			access_token = wxGetAccessTokenRsp.getAccess_token();
		}
		return access_token;
	}

	public String queryDataBase(String queryString) {
		String url = DATABASE_QUERY_URL + getAccessToken();
		JSONObject req = new JSONObject();
		req.put("env", DATABASE_ENV);
		req.put("query", queryString);
		String resultString = restTemplate.postForObject(url, req, String.class);
		logger.info("query dataBase from weixin server the result is {}",resultString);
		return resultString;
	}

	public String updateDateBase(String sence){
		String url = DATABASE_UPDATE_URL + getAccessToken();
		JSONObject req = new JSONObject();
		req.put("env", DATABASE_ENV);
		req.put("query", sence);
		String resultString = restTemplate.postForObject(url, req, String.class);
		logger.info("query dataBase from weixin server the result is {}",resultString);
		return resultString;
	}

}