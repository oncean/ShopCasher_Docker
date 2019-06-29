package com.wangsheng.auth.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_user")
@Data
public class UserInfo {
	
	@Id
	private String userId;
	private String userName;
	private String phone;
	private String password;
	
	/*微信的用户唯一标识*/
	private String openId;
	
	private Date createTime;
	
	/*创建渠道*/
	private Integer createType;
	
	/*用户状态*/
	private Integer status;
	
	/*用户邮箱*/
	private String email;
}
