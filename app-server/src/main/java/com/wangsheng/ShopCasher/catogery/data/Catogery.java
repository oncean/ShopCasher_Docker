package com.wangsheng.ShopCasher.catogery.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_catogery")
@Data
public class Catogery {
 
	@Id
	private String id;
	
	/*父类型的Id*/
	private String parentId;
	
	private String name;
	
	/*是否有效  0  无效 1有效*/
	private int status = 1;
	
	/*1主类型 1子类型*/
	private int type;
}
