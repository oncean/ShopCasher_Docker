package com.wangsheng.ShopCasher.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.ShopCasher.Constant.ShopUserRelationType;
import com.wangsheng.ShopCasher.shop.data.ShopEntity;
import com.wangsheng.ShopCasher.shop.data.ShopRelationEntity;
import com.wangsheng.ShopCasher.shop.info.ShopRelationDetail;
import com.wangsheng.ShopCasher.shop.service.ShopService;
import com.wangsheng.auth.check.CustomerAuth;
import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.auth.service.token.TokenUtils;
import com.wangsheng.auth.service.token.constant.TokenConstants;
import com.wangsheng.comm.BaseResponse;
import com.wangsheng.comm.CountResponse;
import com.wangsheng.exception.ServiceException;
import com.wangsheng.log.AopLog;

@RestController
@RequestMapping("/shop")
@AopLog
@CustomerAuth
public class ShopController {

	@Autowired
	private ShopService shopService;

	/**
	 * 创建商店，并且更新用户为老用户
	 * 
	 * @param shopEntity
	 * @param token
	 * @return
	 */
	@PostMapping("/create")
	public BaseResponse create(@RequestBody ShopEntity shopEntity,
			@RequestHeader(TokenConstants.HEADER_TOKEN) String token) {
		String userId = TokenUtils.getCurrencyUserId(token);
		shopEntity.setCreateUserId(userId);
		String shopId = shopService.createShop(userId,shopEntity);
		return new BaseResponse(shopId);
	}

	@GetMapping("/get/{shopId}")
	@ResponseBody
	public BaseResponse get(@PathVariable("shopId") String shopId,
			@RequestHeader(TokenConstants.HEADER_TOKEN) String token) {
		ShopEntity shopEntity = shopService.getShop(shopId);
		return new BaseResponse(shopEntity);
	}

	//TODO 等待开放
	@RequestMapping("/getDefaultShop")
	public BaseResponse getDefaultShop(@RequestHeader(TokenConstants.HEADER_TOKEN) String token)
			throws ServiceException {
		String userId = TokenUtils.getCurrencyUserId(token);
		//ShopEntity shopEntity = shopService.getDefaultShop(userId);
		return new BaseResponse(null);
	}

	@GetMapping("/getMyShops")
	public CountResponse getMyShops(@RequestHeader(TokenConstants.HEADER_TOKEN) String token) {
		String userId = TokenUtils.getCurrencyUserId(token);
		List<ShopRelationDetail> shopEntity = shopService.getShopsByUserId(userId);
		Integer total = 0;
		if(!CollectionUtils.isEmpty(shopEntity)){
			total = shopEntity.size();
		}
		return new CountResponse(shopEntity,total);
	}

	@DeleteMapping("/deleteShop/{shopId}")
	public BaseResponse deleteShop(@PathVariable("shopId") String shopId, @RequestHeader(TokenConstants.HEADER_TOKEN) String token) {
		String userId = TokenUtils.getCurrencyUserId(token);
		List<ShopRelationEntity> shopEntitys = shopService.getRelation(userId,shopId,ShopUserRelationType.OWNER);
		if (CollectionUtils.isEmpty(shopEntitys)) {
			return ResultConstants.PERMISSION_DENIED;
		}
		shopService.deleteShopByShopId(shopId);
		return ResultConstants.SUCCESS;
	}
}
