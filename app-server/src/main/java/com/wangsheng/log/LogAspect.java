package com.wangsheng.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	// 自定义注解切点
	@Pointcut("@within(com.wangsheng.log.AopLog)")
	public void commonAspect() {
	}

	// Controller层切点
	@Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
	public void controllerAspect() {
	}

	// 配置controller环绕通知,使用在方法aspect()上注册的切入点
	@Around("commonAspect() || controllerAspect()")
	public Object around(ProceedingJoinPoint pjd) throws Throwable {
		String className = pjd.getTarget().getClass().getSimpleName();
		String methodName = pjd.getSignature().getName();
		Object[] os = pjd.getArgs();
		logger.info("{}->{} start,the req is {}", className, methodName, os);

		Object result = pjd.proceed();
		if (result == null) {
			logger.info("{}->{} end", className, methodName);
		}
		logger.info("{}->{} end,the rsp is {}", className, methodName, result);
		return result;
	}

	/**
	 * 异常通知 用于拦截记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "commonAspect() || controllerAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		/* ==========记录本地异常日志========== */
		logger.error("异常方法:{}异常代码:{}异常信息:{}",
				joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(),
				e);

	}
}
