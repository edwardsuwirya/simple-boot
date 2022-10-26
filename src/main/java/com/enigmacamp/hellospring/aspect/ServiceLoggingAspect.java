package com.enigmacamp.hellospring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggingAspect {

    Logger logger = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    @Pointcut("@annotation(com.enigmacamp.hellospring.aspect.Loggable)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void logAllMethodCallAdvice(JoinPoint joinPoint) {
        System.out.println("===>" + joinPoint.getTarget().getClass().getName());
    }

    @AfterThrowing(pointcut = "logPointCut()", throwing = "error")
    public void logErrorAdvice(JoinPoint joinPoint, Throwable error) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        logger.error("==> exception: {}, msg: {}, class: {}, method: {}", error.getClass().getName(), error.getMessage(), className, methodName);
    }
}
