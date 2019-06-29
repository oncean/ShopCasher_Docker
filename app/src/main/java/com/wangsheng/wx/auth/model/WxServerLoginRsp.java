package com.wangsheng.wx.auth.model;

import lombok.Data;

@Data
public class WxServerLoginRsp {
	//用户唯一标识
	private String openId;
	//会话密钥
	private String session_key;  
	
	private String errcode = "0";
	
	private String errmsg;
}
