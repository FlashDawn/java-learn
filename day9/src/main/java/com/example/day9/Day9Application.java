package com.example.day9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Day 9：Spring AOP（Proxy / Pointcut / Advice / Aspect）。
 * 对照笔记 §1、§2。综合项目：横切日志将来落网关或各服务统一切面。
 */
@SpringBootApplication
public class Day9Application {

    public static void main(String[] args) {
        // ========== 启动 Web + 自动启用 @EnableAspectJAutoProxy（starter-aop）==========
        SpringApplication.run(Day9Application.class, args);
    }
}
