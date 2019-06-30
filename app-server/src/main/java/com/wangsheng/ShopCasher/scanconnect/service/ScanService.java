package com.wangsheng.ShopCasher.scanconnect.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangsheng.WebSocket.service.WebSocketService;
import com.wangsheng.comm.utils.UUIDGenerator;

@Service
public class ScanService {

	@Autowired
	private WebSocketService webSocketService;
	
	//商店Id和连接码的映射
	private static Map<String, String> shopIdConnectMap = new HashMap<>();
	
	private String getConnectCode(String shopId){
		String code = shopIdConnectMap.get(shopId);
		if(StringUtils.isEmpty(code)){
			code = UUIDGenerator.random() + shopId;
			shopIdConnectMap.put(shopId, code);
		}
		return code;
	}
	
	/**
	 * 获取商店的连接码
	 * @param shopId
	 * @return
	 */
	public String getShopConnectCode(String shopId){
		return getConnectCode(shopId);
	}
	
	/**
	 * 发送code到商店
	 * @param shopId
	 * @param code
	 */
	public void sendCodeToShop(String shopId,String code){
		webSocketService.send(code, "/topic/sendScanCode/"+getConnectCode(shopId));
	}
}
