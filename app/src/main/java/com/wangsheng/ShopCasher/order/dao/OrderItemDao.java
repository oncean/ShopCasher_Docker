package com.wangsheng.ShopCasher.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangsheng.ShopCasher.order.data.OrderItem;

public interface OrderItemDao extends JpaRepository<OrderItem, String> {
}
