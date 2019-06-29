package com.wangsheng.wx.auth.model;

import lombok.Data;

@Data
public class WXLoginRequest {
	//url
	String requestUrl;
	//小程序唯一标识
	String appid;
	//小程序的 app secret
	String secret;  
    //小程序的 app secret
	String js_code;	
	//填写为 authorization_code
	String grant_type;	
}
