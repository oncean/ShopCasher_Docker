package com.wangsheng.ShopCasher.good.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangsheng.ShopCasher.good.data.GoodInfo;

public interface GoodDao extends JpaRepository<GoodInfo, String>, JpaSpecificationExecutor<GoodInfo> {
	List<GoodInfo> getByCode(String code);
	
	GoodInfo getByCodeAndShopId(String code,String shopId);
	
	List<GoodInfo> queryByShopId(String shopId);
	
	List<GoodInfo> findByCategoryInAndNameContainingAndShopId(List<String> catogeryId,String goodName,String shopId);
	
	List<GoodInfo> findByNameContainingAndShopId(String goodName,String shopId);
	
	List<GoodInfo> findByNameContainingAndShopIdAndCode(String goodName,String shopId,String code);
}
