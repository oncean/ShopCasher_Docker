package com.wangsheng.ShopCasher.good.info;

import com.wangsheng.comm.info.Page;

import lombok.Data;

@Data
public class QueryGoodReq {
	private Page page;
	
	private String goodCode;
	
	private String goodName;
	
	private String shopId;
	
	private String catogeryId;
	
	private int catogeryType;
}
