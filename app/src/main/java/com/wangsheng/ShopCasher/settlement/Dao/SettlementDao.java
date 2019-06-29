package com.wangsheng.ShopCasher.settlement.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangsheng.ShopCasher.settlement.data.SettlementEntity;

public interface SettlementDao extends JpaRepository<SettlementEntity, String>, JpaSpecificationExecutor<SettlementEntity> {

	SettlementEntity getBySettlementDateAndShopId(String settlementDate, String shopId);
}
