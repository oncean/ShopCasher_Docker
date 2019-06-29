package com.wangsheng.ShopCasher.order.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.ShopCasher.order.info.QueryOrderReq;
import com.wangsheng.ShopCasher.order.service.OrderService;
import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.auth.service.token.TokenUtils;
import com.wangsheng.auth.service.token.constant.TokenConstants;
import com.wangsheng.comm.BaseResponse;
import com.wangsheng.comm.CountResponse;
import com.wangsheng.model.OrderModel;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/add")
	public BaseResponse addOrder(@RequestBody OrderModel orderModel, @RequestHeader(TokenConstants.HEADER_TOKEN) String token) {
		BaseResponse rsp = ResultConstants.SUCCESS;
		orderService.addOrder(orderModel, TokenUtils.getCurrencyUserId(token));
		return rsp;
	}
	
	@PostMapping("/queryOrderGroup")
	public BaseResponse queryOrderGroup(@RequestBody QueryOrderReq req) throws ParseException {
		return new BaseResponse(orderService.queryOrderGroup(req));
	}
	
	@PostMapping("/query")
	public CountResponse query(@RequestBody QueryOrderReq req) throws ParseException {
		return orderService.query(req);
	}
	
	@GetMapping("/getOrderItems/{orderId}")
	public BaseResponse getOrderItems(@PathVariable("orderId") String orderId){
		return new BaseResponse(orderService.getOrderItems(orderId));
	}
}
