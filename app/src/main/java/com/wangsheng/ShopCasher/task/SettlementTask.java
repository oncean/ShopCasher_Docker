package com.wangsheng.ShopCasher.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wangsheng.ShopCasher.settlement.service.SettlementService;
import com.wangsheng.ShopCasher.shop.data.ShopEntity;
import com.wangsheng.ShopCasher.shop.service.ShopService;

@Component
public class SettlementTask {
	
	@Autowired
	private ShopService shopService;

	@Autowired
	private SettlementService settlementService;

	@Scheduled(cron = " 0 1 0 * * ?")
	public void executeUploadTask() {
		// 每天0点1分执行一次 ，初始化所有商店今日的结算
		Date date = new Date();
		String today = DateFormatUtils.format(date, "yyyy-MM-dd");
		List<ShopEntity> shops = shopService.getAllShop();
		for (ShopEntity shop : shops) {
			settlementService.get(today, shop.getShopId());
		}
		
	}
}
