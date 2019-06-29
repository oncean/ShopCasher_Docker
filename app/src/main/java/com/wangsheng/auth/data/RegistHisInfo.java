package com.wangsheng.auth.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

//TODO 需要编写定时删除邮件
@Entity
@Table(name = "t_regist_his")
@Data
public class RegistHisInfo {
	
	@Id
	private String id;
	
	/*输入的邮箱*/
	private String address;
	
	/*输入的用户名*/
	private String userName;
	
	/*输入的密码*/
	private String password;
	
	/*发往邮箱的校验码*/
	private String code;
	
	/*注册创建时间*/
	private Date createTime;
	
	/*验证码过期时间*/
	private Date expiredTime;
	
	/*注册成功时间*/
	private Date successTime;
	
	/* 0：等待注册
	 * 1：注册成功
	 * 2：验证码过期*/
	private Integer status;
	
}
