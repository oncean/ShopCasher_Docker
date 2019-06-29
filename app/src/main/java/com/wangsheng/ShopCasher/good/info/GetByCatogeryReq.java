package com.wangsheng.ShopCasher.good.info;

import lombok.Data;

@Data
public class GetByCatogeryReq {
	
	private String catogeryId;
	
	private int catogeryType;
	
	private String goodName;
	
	private String shopId;
}
