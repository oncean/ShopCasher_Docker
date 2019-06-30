package com.wangsheng.auth.service.token;

import com.wangsheng.auth.service.token.model.TokenModel;

/**
 * 对token进行操作的接口
 */
public interface TokenManager {

    /**
     * 创建一个token关联上指定用户
     * @param userId 指定用户的id
     * @return 生成的token
     */
    public String createToken(TokenModel userId);

    /**
     * 创建一个token关联上指定用户
     * @param userId 指定用户的id
     * @return 生成的token
     */
    public String createToken(String userId);

    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    public boolean checkToken(String token);

    /**
     * 从字符串中解析token
     * @param authentication 加密后的字符串
     * @return
     */
    public TokenModel getUserFromToken(String token);

    /**
     * 清除token
     * @param userId 登录用户的id
     */
    public void deleteToken(String userId);

    /**
     * updateRedis
     * @param userId 登录用户的id
     */
    public void updateToken(String token,TokenModel tokenModel);

}