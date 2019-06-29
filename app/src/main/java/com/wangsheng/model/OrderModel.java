package com.wangsheng.model;

import java.util.List;

import com.wangsheng.ShopCasher.order.data.Order;
import com.wangsheng.ShopCasher.order.data.OrderItem;

import lombok.Data;

@Data
public class OrderModel {
	
	private Order order;
	
	private List<OrderItem> orderItems;
	
}
