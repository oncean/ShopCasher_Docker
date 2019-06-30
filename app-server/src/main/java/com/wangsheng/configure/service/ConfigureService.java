package com.wangsheng.configure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangsheng.configure.dao.ConfigureDao;
import com.wangsheng.configure.data.ConfigureInfo;

@Service
public class ConfigureService {
	
	@Autowired
	private ConfigureDao configureDao;
	
	public String get(String key){
		ConfigureInfo configureInfo = configureDao.getByConfigureKey(key);
		if(configureInfo == null){
			return null;
		}else{
			return configureInfo.getConfigureValue();
		}
	}
}
