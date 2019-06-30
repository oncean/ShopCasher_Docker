package com.wangsheng.comm.info;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class Page {
	private Integer pageSize;
	
	private Integer pageNo;
	
	public Pageable toPageable(){
		return new PageRequest(pageNo, pageSize);
	}
}
