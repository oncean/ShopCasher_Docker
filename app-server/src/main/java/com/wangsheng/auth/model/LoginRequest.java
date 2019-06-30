package com.wangsheng.auth.model;

import lombok.Data;

@Data
public class LoginRequest {
	String username;
	
	String password;
}
