package com.wangsheng.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangsheng.auth.data.UserInfo;

public interface UserDao extends JpaRepository<UserInfo, String>, JpaSpecificationExecutor<UserInfo> {

	UserInfo getByUserName(String username);

	UserInfo getByUserNameOrPhoneOrEmail(String username, String phone, String email);

	UserInfo getByOpenId(String openId);

	UserInfo getByEmail(String email);
}
