package com.wangsheng.ShopCasher.shop.info;

import java.util.List;

import com.wangsheng.ShopCasher.settlement.data.SettlementEntity;
import com.wangsheng.ShopCasher.shop.data.ShopEntity;
import com.wangsheng.ShopCasher.shop.data.ShopRelationEntity;

import lombok.Data;

@Data
public class ShopRelationDetail {

	private List<ShopRelationEntity> relations;

	private ShopEntity shopInfo;
	
	private SettlementEntity today;
	
	private String userId;
}
