package com.wangsheng.auth.model;

import lombok.Data;

@Data
public class RegistRequest {
	
	/*输入的邮箱*/
	private String address;
	
	/*输入的用户名*/
	private String userName;
	
	/*输入的密码*/
	private String password;
	
	/*输入验证码*/
	private String code;
}
