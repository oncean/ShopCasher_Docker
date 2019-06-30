package com.wangsheng.ShopCasher.shop.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangsheng.ShopCasher.shop.data.ShopRelationEntity;

public interface ShopRelationDao extends JpaRepository<ShopRelationEntity, String> {
	List<ShopRelationEntity> getShopsByUserId(String userId);

	List<ShopRelationEntity> getShopsByUserIdAndShopIdAndRelationType(String userId, String shopId,
			int relationType);
}
