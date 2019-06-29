package com.wangsheng.ShopCasher.shop.Dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangsheng.ShopCasher.shop.data.ShopEntity;

public interface ShopDao extends JpaRepository<ShopEntity, String> {
}

