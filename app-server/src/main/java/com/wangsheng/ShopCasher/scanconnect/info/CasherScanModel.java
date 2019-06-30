package com.wangsheng.ShopCasher.scanconnect.info;

import lombok.Data;

@Data
public class CasherScanModel {

	// 连接码
	private String connectCode;

	// 商品code
	private String code;
	
	private String shopId;
}
