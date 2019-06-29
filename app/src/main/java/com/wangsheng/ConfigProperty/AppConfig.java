package com.wangsheng.ConfigProperty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "")
@PropertySource(value = "classpath:AppConfig.properties")//配置文件路径  
@Component
public class AppConfig {
	
	private String uploadGoodFileDir;

	public String getUploadGoodFileDir() {
		return uploadGoodFileDir;
	}

	public void setUploadGoodFileDir(String uploadGoodFileDir) {
		this.uploadGoodFileDir = uploadGoodFileDir;
	}

}
