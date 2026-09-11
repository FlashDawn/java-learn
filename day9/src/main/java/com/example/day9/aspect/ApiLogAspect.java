package com.example.day9.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Day 9：Aspect = Pointcut + Advice；Spring 默认用 JDK/CGLIB Proxy 织入。
 * 对照笔记 §1.2～§1.5。综合项目：API 访问日志切面。
 */
@Aspect
@Component
public class ApiLogAspect {

    /**
     * Pointcut：匹配 web 包下所有 public 方法。
     * 为什么用 execution：精确定位 Controller，避免切到无关 Bean。
     */
    @Pointcut("execution(public * com.example.day9.web..*(..))")
    public void apiMethods() {
    }

    /**
     * Advice（Around）：记录请求签名、参数、执行时间、异常。
     * 为什么用 Around：一种 Advice 同时覆盖成功与失败路径。
     */
    @Around("apiMethods()")
    public Object logApi(ProceedingJoinPoint pjp) throws Throwable {
        String signature = pjp.getSignature().toShortString();
        Object[] args = pjp.getArgs();
        long start = System.currentTimeMillis();
        System.out.println("aop-request=" + signature + " args=" + Arrays.toString(args));
        try {
            Object result = pjp.proceed();
            long cost = System.currentTimeMillis() - start;
            System.out.println("aop-ok=" + signature + " costMs=" + cost + " result=" + result);
            return result;
        } catch (Throwable ex) {
            long cost = System.currentTimeMillis() - start;
            // 记录后原样抛出：为什么——切面不应吞掉业务异常
            System.out.println("aop-error=" + signature + " costMs=" + cost + " ex=" + ex.getMessage());
            throw ex;
        }
    }
}
