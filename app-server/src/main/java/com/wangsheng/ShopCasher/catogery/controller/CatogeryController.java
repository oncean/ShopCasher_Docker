package com.wangsheng.ShopCasher.catogery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangsheng.ShopCasher.catogery.service.CatogeryService;
import com.wangsheng.comm.BaseResponse;

@RestController
@RequestMapping("/catogery")
public class CatogeryController {

	@Autowired
	private CatogeryService catogeryService;
	
	@GetMapping("/getStructure")
	public BaseResponse getStructure() {
		return new BaseResponse(catogeryService.getStructure());
	}
	
	@GetMapping("/getAll")
	public BaseResponse getAllCatogery() {
		return new BaseResponse(catogeryService.getAll());
	}
}
