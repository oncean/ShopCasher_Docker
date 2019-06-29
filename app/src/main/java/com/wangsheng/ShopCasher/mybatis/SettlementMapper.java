package com.wangsheng.ShopCasher.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangsheng.ShopCasher.settlement.info.SettlementCatogeryCount;

public interface SettlementMapper {

    List<SettlementCatogeryCount> settlementByCatogery(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("shopId")String shopId);
    
}
