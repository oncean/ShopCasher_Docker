package com.wangsheng.wx.auth.model;

import lombok.Data;
import java.util.List;

@Data
public class WxQueryEntity<T> {

	private List<T> data;

	private WxQueryPager pager;  
	
	private int errcode;
	
	private String errmsg;
}
