package com.wangsheng.ShopCasher.scanconnect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.ShopCasher.good.data.GoodInfo;
import com.wangsheng.ShopCasher.good.service.GoodService;
import com.wangsheng.ShopCasher.scanconnect.info.CasherScanModel;
import com.wangsheng.ShopCasher.scanconnect.service.ScanService;
import com.wangsheng.WebSocket.message.ScanSocketMessage;
import com.wangsheng.WebSocket.message.ScanSocketMessageType;
import com.wangsheng.WebSocket.service.WebSocketService;
import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.comm.BaseResponse;
import com.wangsheng.exception.ServiceException;

@RestController
@RequestMapping("/scan")
public class ScanController {

	public static Logger logger = LoggerFactory.getLogger(ScanController.class);

	@Autowired
	private GoodService goodService;

	@Autowired
	private WebSocketService webSocketService;

	@Autowired
	private ScanService scanService;

	/*
	 * 收银台的扫码操作
	 */
	@RequestMapping(value = "/casherScan/postCode/shopId", method = RequestMethod.POST)
	public BaseResponse casherScanPostCode(@RequestBody CasherScanModel casherScanModel) throws ServiceException {
		logger.info("the phone has scan a code,request is {}", casherScanModel);
		GoodInfo good = goodService.viewGoodByCodeAndShopId(casherScanModel.getCode(), casherScanModel.getShopId());

		ScanSocketMessage message = new ScanSocketMessage();
		message.setType(ScanSocketMessageType.SCAN_FOR_CASHER);
		message.setResult(1);
		if (good == null) {
			message.setResult(0);
		} else {
			message.setGoodInfo(good);
		}
		webSocketService.send(message, "/topic/sendGoodInfo/"+casherScanModel.getConnectCode());
		logger.info("send message success");
		return ResultConstants.SUCCESS;
	}

	/*
	 * 增加商品的扫码操作
	 */
	@RequestMapping(value = "/addGoodScan/postCode", method = RequestMethod.POST)
	public BaseResponse addGoodScanPostCode(@RequestBody CasherScanModel casherScanModel) throws ServiceException {
		logger.info("the phone has scan a code,request is {}", casherScanModel);
		GoodInfo good = goodService.viewGoodByCodeAndShopId(casherScanModel.getCode(), casherScanModel.getShopId());

		ScanSocketMessage message = new ScanSocketMessage();
		message.setType(ScanSocketMessageType.SCAN_FOR_ADD);
		message.setResult(1);
		if (good == null) {
			message.setResult(0);
		} else {
			message.setGoodInfo(good);
		}
		webSocketService.send(message, "/topic/sendGoodInfo/"+casherScanModel.getConnectCode());
		logger.info("send message success");
		return ResultConstants.SUCCESS;
	}


	/*
	 * 获取商店的连接码
	 */
	@GetMapping("/getShopConnectCode/{shopId}")
	public BaseResponse getShopConnectCode(@PathVariable("shopId") String shopId){
		return new BaseResponse(scanService.getShopConnectCode(shopId));
	}


	/*
	 * 通过连接码向商店发送扫描的条码值
	 */
	@PostMapping("/sendCodeToShop")
	public BaseResponse sendCodeToShop(@RequestBody CasherScanModel req){
		String code = scanService.getShopConnectCode(req.getShopId());
		if(!code.equals(req.getConnectCode())){
			return ResultConstants.CONNECT_ERROR;
		}
		scanService.sendCodeToShop(req.getShopId(), req.getCode());
		return ResultConstants.SUCCESS;
	}
	
}
