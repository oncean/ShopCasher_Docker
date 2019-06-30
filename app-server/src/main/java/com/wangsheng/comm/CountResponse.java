package com.wangsheng.comm;

import org.springframework.data.domain.Page;

import com.wangsheng.ShopCasher.good.data.GoodInfo;
import com.wangsheng.ShopCasher.good.info.QueryListResult;
import com.wangsheng.exception.ServiceException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountResponse {
	private String code = "0";

	private String desc = "success";

	private Object result;

	private Integer total;

	public CountResponse(String code, String desc) {
		this.code = code;
		this.desc = desc;
	};

	public CountResponse(Object result, Integer total) {
		this.result = result;
		this.total = total;
	};

	public CountResponse(Page<GoodInfo> pageObject) {
		this.result = pageObject.getContent();
		this.total = (int) pageObject.getTotalElements();
	};

	public CountResponse(QueryListResult result) {
		this.result = result.getList();
		this.total = result.getTotal();
	};

	public ServiceException toException() {
		return new ServiceException(this.getCode(), this.getDesc());
	}
}
