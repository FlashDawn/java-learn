package com.example.day39.worker;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Day39 Worker 服务 = Dubbo Provider（对照笔记 §1.1）。
 * 无 Web 端口：它只通过 Dubbo 协议（20880）对外提供 RPC 能力。
 */
@SpringBootApplication
@EnableDubbo
public class WorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkerApplication.class, args);
    }
}
