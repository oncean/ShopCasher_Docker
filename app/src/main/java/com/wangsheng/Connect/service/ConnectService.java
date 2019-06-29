package com.wangsheng.Connect.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.auth.service.token.TokenManager;
import com.wangsheng.auth.service.token.model.TokenModel;
import com.wangsheng.exception.ServiceException;
import com.wangsheng.comm.utils.UUIDGenerator;

@Service
public class ConnectService {
	
	/**
	 * connectCode和token的映射缓存
	 */
	private static Map<String,String> connectCodeMap = new HashMap<>();

	
	@Autowired
	private TokenManager tokenManager;
	
	/**
	 * 根据token获取连接码
	 * @param token
	 * @return
	 * @throws Exception 
	 */
	public String getConnectCode(String token) throws ServiceException{
		if(StringUtils.isEmpty(token)){
			throw ResultConstants.ERROR_TOKEN.toException();
		}
		/*判断token是否有效*/
		if(!tokenManager.checkToken(token)){
			throw ResultConstants.ERROR_TOKEN.toException();
		}
		TokenModel tokenModel = tokenManager.getUserFromToken(token);
		String connectCode = "";
		if(null ==tokenModel || StringUtils.isEmpty(tokenModel.getConnectCode())){
			/*如果token为空，或者token中没有connectCode，重新生成连接码，并保存在缓存和redis中*/
			connectCode = UUIDGenerator.randomID();
			tokenModel.setConnectCode(connectCode);
			tokenManager.updateToken(token, tokenModel);
		}else{
			connectCode = tokenModel.getConnectCode();
		}
		connectCodeMap.put(connectCode, token);
		return connectCode;
	}
	
	/**
	 * 根据连接码获取token
	 * @param connectCode
	 * @return
	 * @throws Exception
	 */
	public String getTokenByConnectCode(String connectCode) throws ServiceException{
		String token = connectCodeMap.get(connectCode);
		if(StringUtils.isEmpty(token)){
			throw ResultConstants.ERROR_TOKEN.toException();
		}
		/*判断token是否有效*/
		if(!tokenManager.checkToken(token)){
			throw ResultConstants.ERROR_TOKEN.toException();
		}
		TokenModel tokenModel = tokenManager.getUserFromToken(token);
		/*判断token中是否存在连接码，如果不存在，说明连接码已失效*/
		if(null !=tokenModel && connectCode.equals(tokenModel.getConnectCode())){
			return token;
		}else{
			throw ResultConstants.CONNECT_ERROR.toException();
		}
	}
	
}
