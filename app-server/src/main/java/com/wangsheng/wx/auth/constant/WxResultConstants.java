package com.wangsheng.wx.auth.constant;

import com.wangsheng.wx.auth.model.WxloginResponse;

public class WxResultConstants {
	public final static WxloginResponse SUCCESS = new WxloginResponse("0", "success");

	/* code无效,40029为调用微信服务器错误时返回的码*/
	public final static WxloginResponse INVALID_CODE = new WxloginResponse("40029", "invalid code");
}
