package com.wangsheng.configure.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_configure")
@Data
public class ConfigureInfo {

	@Id
	private String id;
	
	private String configureKey;
	
	private String configureValue;
	
	private String description;
}
