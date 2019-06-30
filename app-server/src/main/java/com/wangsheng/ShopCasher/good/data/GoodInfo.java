package com.wangsheng.ShopCasher.good.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_good")
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GoodInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	/*商品名称*/
	private String name;
	
	/*单价*/
	private double price;
	
	/*进价*/
	private double bid;
	
	/*库存*/
	private Integer stock;
	
	/*商品描述*/
	private String description;
	
	/*商品条形码*/
	private String code;
	
	/*商品照片*/
	private String picUrl;
	
	/*商品类别*/
	private String category;
	
	/*商品规格*/
	private String spec;
	
	/*商品所在店铺*/
	private String shopId;
	
	/*录入人ID*/
	private String updateUserId;
	
	/*新增时间*/
	private Date createTime;
	
	private Date updateTime;
}
