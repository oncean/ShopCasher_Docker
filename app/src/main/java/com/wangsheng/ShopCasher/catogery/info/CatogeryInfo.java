package com.wangsheng.ShopCasher.catogery.info;

import java.util.List;

import com.wangsheng.ShopCasher.catogery.data.Catogery;

import lombok.Data;

@Data
public class CatogeryInfo {

	private Catogery catogery;

	private List<Catogery> children;
}
