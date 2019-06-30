package com.wangsheng.auth.service.token;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.wangsheng.auth.service.token.model.TokenModel;
import com.wangsheng.comm.utils.UUIDGenerator;

@Component
public class TokenUtils implements ApplicationContextAware  {
	 /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        TokenUtils.applicationContext = applicationContext;
    }
    
	public static String getCurrencyUserId(String token){
		TokenManager tokenManager = (TokenManager) applicationContext.getBean("tokenManager");
		TokenModel tokenModel = tokenManager.getUserFromToken(token);
		if(tokenModel == null)
			return null;
		return tokenModel.getUserId();
	}
	
	public static String generateTokenString(){
		return UUIDGenerator.random();
	}
	
}
