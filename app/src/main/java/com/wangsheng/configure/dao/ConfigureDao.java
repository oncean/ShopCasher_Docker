package com.wangsheng.configure.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangsheng.configure.data.ConfigureInfo;

public interface ConfigureDao extends  JpaRepository<ConfigureInfo,String>{
	
	ConfigureInfo getByConfigureKey(String key);
	
}
