package com.wangsheng.ShopCasher.order.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_order_item")
@Data
public class OrderItem {
	
	@Id
	private String itemId;
	
	/*订单行总金额*/
	private double totalPrice;
	
	/*订单行数量*/
	private Integer number;
	
	/*商品ID*/
	private String goodId;
	
	/*订单ID*/
	private String orderId;
}
