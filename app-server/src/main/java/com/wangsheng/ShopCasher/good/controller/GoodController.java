package com.wangsheng.ShopCasher.good.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.ShopCasher.good.data.GoodInfo;
import com.wangsheng.ShopCasher.good.info.AddGoodNumBatchReq;
import com.wangsheng.ShopCasher.good.info.GetByCatogeryReq;
import com.wangsheng.ShopCasher.good.info.GoodInfoSnap;
import com.wangsheng.ShopCasher.good.info.GoodNumInfo;
import com.wangsheng.ShopCasher.good.info.QueryGoodReq;
import com.wangsheng.ShopCasher.good.info.QueryListResult;
import com.wangsheng.ShopCasher.good.info.QuerySuggestGoodReq;
import com.wangsheng.ShopCasher.good.service.GoodService;
import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.auth.service.token.TokenUtils;
import com.wangsheng.auth.service.token.constant.TokenConstants;
import com.wangsheng.comm.BaseResponse;
import com.wangsheng.comm.CountResponse;
import com.wangsheng.exception.ServiceException;

@RestController
@RequestMapping("/good")
public class GoodController {

	@Autowired
	private GoodService goodService;

	@PostMapping("/create")
	public BaseResponse addGood(@RequestBody GoodInfo good, @RequestHeader(TokenConstants.HEADER_TOKEN) String token)
			throws ServiceException {
		good.setUpdateUserId(TokenUtils.getCurrencyUserId(token));
		goodService.createGood(good);
		return ResultConstants.SUCCESS;
	}

	@PostMapping("/edit")
	public BaseResponse editGood(@RequestBody GoodInfo good, @RequestHeader(TokenConstants.HEADER_TOKEN) String token)
			throws ServiceException {
		good.setUpdateUserId(TokenUtils.getCurrencyUserId(token));
		goodService.editGood(good);
		return ResultConstants.SUCCESS;
	}

	@GetMapping("/addGoodNum/{shopId}/{goodCode}/{num}")
	public BaseResponse addGoodNum(@PathVariable("goodCode") String goodCode, @PathVariable("num") Integer num,
			@PathVariable("shopId") String shopId) throws ServiceException {
		goodService.addGoodNum(goodCode, num, shopId);
		return ResultConstants.SUCCESS;
	}

	@PostMapping("/addGoodNumBatch")
	public BaseResponse addGoodNumBatch(@RequestBody AddGoodNumBatchReq req) throws ServiceException {
		if (!CollectionUtils.isEmpty(req.getGoods())) {
			for (GoodNumInfo goodNumInfo : req.getGoods()) {
				goodService.addGoodNum(goodNumInfo.getGoodCode(), goodNumInfo.getNumber(), req.getShopId());
			}
		}
		return ResultConstants.SUCCESS;
	}
	


	@GetMapping("/delete/{shopId}/{goodId}")
	public BaseResponse deleteGoodInfo(@PathVariable("shopId") String shopId, @PathVariable("goodId") String goodId)
			throws ServiceException {
		if (StringUtils.isEmpty(goodId)) {
			return ResultConstants.REQ_ERROR;
		}
		goodService.deleteGood(goodId);
		return ResultConstants.SUCCESS;
	}

	@GetMapping("/view/{shopId}/{goodCode}")
	public BaseResponse viewGoodInfo(@PathVariable("goodCode") String goodCode, @PathVariable("shopId") String shopId)
			throws ServiceException {
		if (StringUtils.isEmpty(goodCode)) {
			return ResultConstants.REQ_ERROR;
		}
		BaseResponse rsp = ResultConstants.SUCCESS;
		GoodInfo good = goodService.viewGoodByCodeAndShopId(goodCode, shopId);
		rsp.setResult(good);
		return rsp;
	}

	/**
	 * 获取商品列表快照
	 * 
	 * @param shopId
	 * @return
	 */
	@GetMapping("/getSnap/{shopId}")
	public BaseResponse getGoodsSnap(@PathVariable("shopId") String shopId) {
		if (StringUtils.isEmpty(shopId)) {
			return ResultConstants.REQ_ERROR;
		}
		List<GoodInfoSnap> snaps = goodService.getGoodSnap(shopId);
		return new BaseResponse(snaps);

	}

	@PostMapping("/findByCatogery")
	public BaseResponse getByCatogery(@RequestBody GetByCatogeryReq req) {
		return new BaseResponse(goodService.getByCatogery(req));
	}
	
	@PostMapping("/query")
	public CountResponse query(@RequestBody QueryGoodReq req){
		QueryListResult<GoodInfo> result = goodService.queryByPage(req);
		return new CountResponse(result);
	}
	
	@PostMapping("/querySuggestion")
	public BaseResponse querySuggestion(@RequestBody QuerySuggestGoodReq req){
		List<GoodInfo> result = goodService.querySuggestion(req);
		return new BaseResponse(result);
	}
}
