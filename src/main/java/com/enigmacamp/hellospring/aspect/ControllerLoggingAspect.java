package com.enigmacamp.hellospring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ControllerLoggingAspect {

    Logger logger = LoggerFactory.getLogger(ControllerLoggingAspect.class);

    @Pointcut("within(com.enigmacamp.hellospring.controller.ErrorController)")
    public void logPointCut() {
    }

    @AfterReturning(value = "logPointCut()", returning = "returnValue")
    public void logErrorGetAdvice(JoinPoint joinPoint, ResponseEntity returnValue) {
        Object[] args = joinPoint.getArgs();
        Exception ex = (Exception) args[0];
        HttpServletRequest request = (HttpServletRequest) args[1];
        logger.error("==> exception: {}, msg: {}, path: {}, method: {}, status: {}", ex.getClass().getName(), ex.getMessage(), request.getRequestURI(), request.getMethod(), returnValue.getStatusCodeValue());
    }

}
