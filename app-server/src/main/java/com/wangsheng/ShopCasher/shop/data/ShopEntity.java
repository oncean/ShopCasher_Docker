package com.wangsheng.ShopCasher.shop.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "t_shop")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShopEntity {
	
	@Id
	private String shopId;

	private String shopName;
	
	private String location;
	
	private String createUserId;
	
	private Date createTime;
	
	/**
	 * 1:商店正在使用
	 * 2：商店已删除
	 */
	private Integer status;
}
