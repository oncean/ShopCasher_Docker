package com.wangsheng.wx.auth.model;

import lombok.Data;

@Data
public class WxQueryPager {

	private int Offset;
	
	private int Limit;

  private int Total;
}
