package com.company.taskmanager.samrtlogger.aspect;


import com.company.taskmanager.samrtlogger.LogRestCall;
import com.company.taskmanager.samrtlogger.TrackError;
import com.company.taskmanager.samrtlogger.TrackExecutionTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class SmartLoggerAspect {

    @Around("@annotation(trackExecutionTime)")
    public Object handleExecutionTime(ProceedingJoinPoint joinPoint, TrackExecutionTime trackExecutionTime) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        long start = System.nanoTime();
        Object result = joinPoint.proceed();
        long duration = System.nanoTime() - start;

        System.out.printf("[%s] %s took %dms%n",
                trackExecutionTime.tag(), method.getName(), duration);
        return result;
    }

    @Around("@annotation(logRestCall)")
    public Object logRestCall(ProceedingJoinPoint joinPoint, LogRestCall logRestCall){
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        String methodName = method.getDeclaringClass().getSimpleName()+"."+method.getName();


        if(logRestCall.logInput())
            System.out.printf("[%s] Input: %s%n", methodName, Arrays.toString(args));

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        if (logRestCall.logOutPut())
            System.out.printf("[%s] Output: %s%n", methodName, result);

        return result;

    }


    @AfterThrowing(pointcut = "@annotation(trackError)", throwing = "ex")
    public void handleErrors(JoinPoint joinPoint, TrackError trackError, Throwable ex) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String module = trackError.module();

        System.err.printf("❌ [%s] Error in %s: %s%n",
                module, method.getName(), ex.getMessage());

        if (trackError.sendToMonitoring()) {
            // send to monitoring system (e.g., Sentry, Grafana)
        }
    }

}
