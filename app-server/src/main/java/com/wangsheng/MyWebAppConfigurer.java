package com.wangsheng;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wangsheng.auth.check.AuthenticationInterceptor;

/**
 * 解决跨域问题
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
	
	/**
	 * 解决跨域问题
	 */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
          .allowCredentials(true).maxAge(3600);
    }
    
    @Bean
    AuthenticationInterceptor authenticationInterceptor()
    {
    	return new AuthenticationInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);

    }
    
}