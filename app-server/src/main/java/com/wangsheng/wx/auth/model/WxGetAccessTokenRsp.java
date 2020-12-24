package com.wangsheng.wx.auth.model;

import lombok.Data;

@Data
public class WxGetAccessTokenRsp {
	//获取到的凭证
	private String access_token;
	//凭证有效时间，单位：秒。目前是7200秒之内的值。
	private int expires_in;  
	
	private int errcode;
	
	private String errmsg;
}
