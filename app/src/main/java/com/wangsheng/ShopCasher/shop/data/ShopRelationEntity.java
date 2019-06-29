package com.wangsheng.ShopCasher.shop.data;

import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 商店和用户关系表
 * @author 10170
 *
 */
@Data
@Entity
@Table(name = "t_shop_user_relation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ShopRelationEntity {
	
	@Id
	private String relationId;

	private String shopId;
	
	private String userId;
	
	/**
	 * 关系类型
	 * 0：所有者
	 * 1：店长
	 * 2：店员
	 */
	private Integer relationType;
	
	private Date updateTime;
	
	private Date createTime;
}
