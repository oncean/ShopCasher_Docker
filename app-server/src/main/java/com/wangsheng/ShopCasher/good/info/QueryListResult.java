package com.wangsheng.ShopCasher.good.info;

import java.util.List;

import lombok.Data;

@Data
public class QueryListResult<T> {
	private int total;

	private List<T> list;
}
