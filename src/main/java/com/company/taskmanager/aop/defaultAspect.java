package com.company.taskmanager.aop;

import com.company.taskmanager.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class defaultAspect {

    @Pointcut("within(com.company.taskmanager.service.*)")
    public void loggingPointCut(){}

    //    @Pointcut("execution(* com.company.taskmanager.controller.*.*(..))")
//    public void loggingPointcut(){}

    @Before("loggingPointcut()")
    public void before(JoinPoint joinPoint){
        log.info("Before method invoked: "+joinPoint.getSignature());
    }
//
//    @After("loggingPointcut()")
//    public void after(JoinPoint joinPoint){
//        log.info("@After method invoked: "+joinPoint.getSignature());
//    }

//    @AfterReturning(value = "execution(* com.company.taskmanager.controller.*.*(..))",returning = "result")
//     public void after(JoinPoint joinPoint, Object result){
//        log.info("After the method invoked: "+result);
//    }
//
//    @AfterThrowing(value = "execution(* com.company.taskmanager.controller.*.*(..))",throwing = "exception")
//    public void afterThrowing(JoinPoint joinPoint, Exception exception){
//        log.info("After the method Throw the Exception: "+exception.getMessage());
//    }
//
//    @Around("loggingPointcut()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
//        log.info("Around ~ @Before method invoked:::: "+joinPoint.getSignature());
//        Object object = joinPoint.proceed();
//
//        log.info("Object is the {} this type",String.valueOf(object instanceof Task));
//        if(object instanceof Task){
//
//            log.info("Around ~ @After method invoked:::: "+joinPoint.getSignature());
//        }
//
//        return object;
//    }

    }
