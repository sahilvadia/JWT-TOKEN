package com.company.taskmanager.samrtlogger;


import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRestCall {
    boolean logInput() default true;
    boolean logOutPut() default true;
    LogLevel level() default LogLevel.INFO;

    enum LogLevel{ INFO, DEBUG, ERROR}
}
