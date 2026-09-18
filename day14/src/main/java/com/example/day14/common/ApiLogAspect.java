package com.example.day14.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Day14：API 日志切面（整合 Day9 AOP 验收能力）。
 * 钉死概念：记录请求方法、参数、耗时、异常。对照笔记：§1.4。
 */
@Aspect
@Component
public class ApiLogAspect {

    private static final Logger log = LoggerFactory.getLogger(ApiLogAspect.class);

    @Around("execution(* com.example.day14..*Controller.*(..))")
    public Object aroundController(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        String signature = pjp.getSignature().toShortString();
        // 为什么打参数：排障需要；生产应对敏感字段脱敏，Demo 从简
        log.info("API start {} args={}", signature, Arrays.toString(pjp.getArgs()));
        try {
            Object result = pjp.proceed();
            log.info("API ok {} costMs={}", signature, System.currentTimeMillis() - start);
            return result;
        } catch (Throwable ex) {
            log.warn("API fail {} costMs={} ex={}",
                    signature, System.currentTimeMillis() - start, ex.toString());
            throw ex;
        }
    }
}
