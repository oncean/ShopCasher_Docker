package com.wangsheng.ShopCasher.order.info;

import com.wangsheng.comm.info.Page;

import lombok.Data;

@Data
public class QueryOrderReq {

	private String shopId;
	
	private String day;
	
	private Page page;
	
	/**
	 * YYYY-MM-DD hh:mm:ss
	 */
	private String startTime;
	
	/**
	 * YYYY-MM-DD hh:mm:ss
	 */
	private String endTime;
	
	private String orderId;
	
	private Integer payMethod;
	
}
