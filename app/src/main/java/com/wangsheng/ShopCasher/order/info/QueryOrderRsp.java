package com.wangsheng.ShopCasher.order.info;

import java.util.List;

import com.wangsheng.ShopCasher.order.data.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryOrderRsp {
	
	private Integer totalNum;
	
	private List<Order> orders;
}
