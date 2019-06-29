package com.wangsheng.ShopCasher.settlement.info;

import lombok.Data;

@Data
public class SettlementByCatogeryReq {
	
	private String shopId;
	
	/*开始时间，用于范围查询*/
	private String startDay;

	/*结束时间*/
	private String endDay;
}
