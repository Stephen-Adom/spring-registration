package com.alaska.securitylearn.Logs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InfoLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfoLogger.class);

    @Pointcut("execution(* com.alaska.securitylearn.controller.*.*(..))")
    public void loggingPointCut() {
    }

    @After("loggingPointCut()")
    public void logInfo(JoinPoint joinpoint) {
        System.out.println(joinpoint.getArgs().toString());
        LOGGER.warn("------------------------------logging information----------------------------------");
    }
}
