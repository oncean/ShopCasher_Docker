package com.wangsheng.ShopCasher.settlement.info;

import java.util.List;

import com.wangsheng.ShopCasher.settlement.data.SettlementEntity;

import lombok.Data;

@Data
public class QuerySettlementRsp {
	
	private List<SettlementEntity> settlements;
	
	private long totalNum;
}
