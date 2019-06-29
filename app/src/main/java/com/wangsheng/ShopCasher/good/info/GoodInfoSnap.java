package com.wangsheng.ShopCasher.good.info;

import lombok.Data;

@Data
public class GoodInfoSnap {
	
	private String id;
	
	/*商品名称*/
	private String name;
	
	/*单价*/
	private double price;
	
	/*进价*/
	private double bid;
	
	/*库存*/
	private Integer stock;
	
	/*商品描述*/
	private String description;
	
	/*商品条形码*/
	private String code;
	
	/*商品类别*/
	private String category;
	
	/*商品规格*/
	private String spec;
}
