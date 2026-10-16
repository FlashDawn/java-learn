package com.example.day39.task;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Day39 Task 服务 = Dubbo Consumer（Web 18110，对照笔记 §1.1）。
 * 接 HTTP 请求 → 转 RPC 调 Worker——对外 REST、对内 RPC 是常见组合。
 */
@SpringBootApplication
@EnableDubbo
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
