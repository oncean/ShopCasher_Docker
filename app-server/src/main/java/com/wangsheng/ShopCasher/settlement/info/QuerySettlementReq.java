package com.wangsheng.ShopCasher.settlement.info;

import java.util.List;

import com.wangsheng.comm.info.Page;

import lombok.Data;

/**
 * 分页查询账期接口
 * @author 10170
 *
 */
@Data
public class QuerySettlementReq {
	
	private String shopId;
	
	/*选择时间数组*/
	private List<String> settlementDates;
	
	/*开始时间，用于范围查询*/
	private String startDay;

	/*结束时间*/
	private String endDay;
	
	private Page page;
}
