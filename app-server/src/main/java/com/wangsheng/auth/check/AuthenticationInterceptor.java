package com.wangsheng.auth.check;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.auth.service.token.TokenManager;
import com.wangsheng.auth.service.token.constant.TokenConstants;
import com.wangsheng.exception.ServiceException;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenManager tokenManager;

	/**
	 * 如果类上或者方法有鉴权注解，则进行鉴权
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader(TokenConstants.HEADER_TOKEN);
		// 如果不是映射到方法直接通过
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Class class1 = handlerMethod.getBeanType();
		Object test = class1.getAnnotations();
		Annotation classAnnotation = class1.getAnnotation(CustomerAuth.class);
		Method method = handlerMethod.getMethod();
		// 有CustomerAuth注解则校验token
		CustomerAuth methodAnnotation = method.getAnnotation(CustomerAuth.class);
		if (methodAnnotation != null || classAnnotation != null) {
			if (tokenManager.checkToken(token)) {
				return true;
			} else {
				throw new ServiceException(ResultConstants.ERROR_TOKEN);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
