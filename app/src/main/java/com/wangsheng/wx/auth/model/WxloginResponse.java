package com.wangsheng.wx.auth.model;

import lombok.Data;

@Data
public class WxloginResponse {
		private String code = "0";
		
		private String desc = "success";
		
		private Object result;
		
		public WxloginResponse(String code,String desc){
			this.code=code;
			this.desc=desc;
		};
		

}
