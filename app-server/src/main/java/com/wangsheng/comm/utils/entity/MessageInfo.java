package com.wangsheng.comm.utils.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageInfo {
	
	/*收件人*/
	private String address;
	
	/*标题*/
	private String title;
	
	/*内容*/
	private String content;
	
	public MessageInfo(String address,String title) {
		this.title = title;
		this.address = address;
	}
	
}
