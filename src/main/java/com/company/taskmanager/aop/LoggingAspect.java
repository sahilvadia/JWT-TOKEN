package com.company.taskmanager.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class LoggingAspect {

    @Pointcut("execution(* com.company.taskmanager.controller.*.*(..))")
    public void getterPointcut(){}

    @Before("getterPointcut()")
    public void before(JoinPoint joinPoint){
        log.info("Before method invoked:::::{}",joinPoint.getSignature());
    }

}
