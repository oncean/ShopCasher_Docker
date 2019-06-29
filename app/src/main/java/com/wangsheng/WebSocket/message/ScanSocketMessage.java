package com.wangsheng.WebSocket.message;

import com.wangsheng.ShopCasher.good.data.GoodInfo;

import lombok.Data;

@Data
public class ScanSocketMessage {

	public GoodInfo goodInfo;

	/*扫描类型*/
	public int type;
	
	/*扫描是否查询到商品的结果 0表示没有，1表示有*/
	public int result;

}
