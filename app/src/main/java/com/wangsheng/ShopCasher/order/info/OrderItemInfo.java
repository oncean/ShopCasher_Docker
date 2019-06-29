package com.wangsheng.ShopCasher.order.info;

import com.wangsheng.ShopCasher.order.data.OrderItem;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderItemInfo extends OrderItem{
	
	private String goodName;
	
	private String goodCode;
}
