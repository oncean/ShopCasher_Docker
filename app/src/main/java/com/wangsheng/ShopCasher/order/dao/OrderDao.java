package com.wangsheng.ShopCasher.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wangsheng.ShopCasher.order.data.Order;

public interface OrderDao extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {

	@Query(value = "select * from t_order a where a.shopId=:shopId and date_format(settlementTime,'%Y-%m-%d')=:day", nativeQuery = true)
	List<Order> queryDayOrders(@Param("shopId") String shopId, @Param("day") String day);
}
