package com.wangsheng.auth.service.token.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.wangsheng.auth.service.token.TokenManager;
import com.wangsheng.auth.service.token.TokenUtils;
import com.wangsheng.auth.service.token.constant.TokenConstants;
import com.wangsheng.auth.service.token.model.TokenModel;
import com.wangsheng.comm.utils.UUIDGenerator;

@Service("tokenManager")
public class RedisTokenManager implements TokenManager{

	@Resource
    private RedisTemplate<String,Object> redis;

    public String createToken(String userId) {
        String token = TokenUtils.generateTokenString();
    	TokenModel tokenModel = new TokenModel();
    	tokenModel.setToken(token);
    	tokenModel.setUserId(userId);
        //使用uuid作为源token
        //存储到redis并设置过期时间
        redis.boundValueOps(token).set(tokenModel, TokenConstants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return token;
    }

    public String createToken(TokenModel tokenModel) {
        //使用uuid作为源token
        String token = UUIDGenerator.random();
        //存储到redis并设置过期时间
        redis.boundValueOps(token).set(tokenModel, TokenConstants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return token;
    }

    public boolean checkToken(String token) {
        Object tokenModel = redis.boundValueOps(token).get();
        if (tokenModel == null) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(token).expire(TokenConstants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }
    
    public TokenModel getUserFromToken(String token){
    	Object tokenModel = redis.boundValueOps(token).get();
        if (tokenModel == null) {
            return null;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(token).expire(TokenConstants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return (TokenModel) tokenModel;
    }

    public void deleteToken(String token) {
        redis.delete(token);
    }

	@Override
	public void updateToken(String token, TokenModel tokenModel) {
        //存储到redis并设置过期时间
        redis.boundValueOps(token).set(tokenModel, TokenConstants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
	}
}
