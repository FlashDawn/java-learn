package com.example.day35;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Day35 入口：MQ 综合实战。
 * 链路（对照笔记 §1）：POST /tasks → PostgreSQL(PENDING) → RabbitMQ → Worker 执行 → 更新状态(DONE/FAILED)。
 * 串起 Week 5 全部概念：异步解耦、削峰、ACK、Retry、DLQ、幂等。
 */
@SpringBootApplication
public class Day35Application {

    public static void main(String[] args) {
        SpringApplication.run(Day35Application.class, args);
    }
}
