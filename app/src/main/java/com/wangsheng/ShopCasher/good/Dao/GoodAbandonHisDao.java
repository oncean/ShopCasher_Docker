package com.wangsheng.ShopCasher.good.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangsheng.ShopCasher.good.data.GoodInfoAbandonHis;

public interface GoodAbandonHisDao extends JpaRepository<GoodInfoAbandonHis, String>, JpaSpecificationExecutor<GoodInfoAbandonHis> {
}
