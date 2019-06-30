package com.wangsheng.ShopCasher.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangsheng.ShopCasher.order.data.Order;
import com.wangsheng.ShopCasher.order.info.OrderItemInfo;
import com.wangsheng.ShopCasher.order.info.QueryOrderReq;

public interface OrderMapper {

    List<Order> queryOrder(@Param("req") QueryOrderReq req);
    
    Integer queryOrderCount(@Param("req") QueryOrderReq req);
    
    List<OrderItemInfo> getOrderItems(@Param("orderId") String orderId);
}
