package com.wangsheng.ShopCasher.settlement.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.ShopCasher.settlement.data.SettlementEntity;
import com.wangsheng.ShopCasher.settlement.info.QuerySettlementReq;
import com.wangsheng.ShopCasher.settlement.info.SettlementByCatogeryReq;
import com.wangsheng.ShopCasher.settlement.info.SettlementCatogeryCount;
import com.wangsheng.ShopCasher.settlement.service.SettlementService;
import com.wangsheng.comm.BaseResponse;

@RestController
@RequestMapping("/settlement")
public class SettlementController {

	@Autowired
	private SettlementService settlementService;

	/**
	 * 创建商店，并且更新用户为老用户
	 * 
	 * @param shopEntity
	 * @param token
	 * @return
	 */
	@GetMapping("/getToday/{shopId}")
	public BaseResponse getToday(@PathVariable("shopId") String shopId) {
		SettlementEntity settlementEntity = settlementService.get(DateFormatUtils.format(new Date(), "yyyy-MM-dd"),
				shopId);
		return new BaseResponse(settlementEntity);
	}

	@PostMapping("/query")
	public BaseResponse query(@RequestBody QuerySettlementReq req) {
		return new BaseResponse(settlementService.querySettlement(req));
	}

	@GetMapping("/getDay/{shopId}/{day}")
	public BaseResponse getDay(@PathVariable("shopId") String shopId, @PathVariable("day") String day) {
		SettlementEntity settlementEntity = settlementService.get(day, shopId);
		return new BaseResponse(settlementEntity);
	}

	@PostMapping("/settlementByCatogery")
	public BaseResponse settlementByCatogery(@RequestBody SettlementByCatogeryReq req) throws ParseException {
		List<SettlementCatogeryCount> settlementCatogeryCounts = settlementService.settlementByCatogery(req);
		return new BaseResponse(settlementCatogeryCounts);
	}
}
