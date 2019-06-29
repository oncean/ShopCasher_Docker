package com.wangsheng.ShopCasher.order.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_order")
@Data
public class Order {
	
	
	@Id
	private String orderId;
	
	/*应收金额*/
	private double totalPrice;
	
	/*实收金额*/
	private double receipts;
	
	/*找零*/
	private double giveChange;
	
	/*总数*/
	private Integer totalNum;
	
	/*付款方式*/
	private String payMethod;
	
	/*结算时间*/
	private Date settlementTime;
	
	/*结算人ID*/
	private String settlementUserId;
	
	/*商店ID*/
	private String shopId;
	
	/* 利润 */
	private double profit;
}
