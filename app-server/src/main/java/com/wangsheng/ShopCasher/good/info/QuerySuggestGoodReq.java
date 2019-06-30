package com.wangsheng.ShopCasher.good.info;

import lombok.Data;

@Data
public class QuerySuggestGoodReq {
	
	//需要返回多少条
	private Integer totalNum;
	
	private String value;
	
	private String shopId;
}
