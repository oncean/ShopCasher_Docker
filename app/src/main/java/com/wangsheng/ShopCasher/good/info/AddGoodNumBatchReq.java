package com.wangsheng.ShopCasher.good.info;

import java.util.List;

import lombok.Data;

@Data
public class AddGoodNumBatchReq {
	
	private String shopId;
	
	private List<GoodNumInfo> goods;
}
