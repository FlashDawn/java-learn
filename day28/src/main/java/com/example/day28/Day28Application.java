package com.example.day28;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Day28 入口：Redis 综合实战——把缓存正式接入 User/Task 项目。
 * 对照笔记 §1（Cache-Aside / 穿透 / 击穿 / 雪崩 / 分布式锁 / 一致性）。
 */
@SpringBootApplication
public class Day28Application {

    public static void main(String[] args) {
        SpringApplication.run(Day28Application.class, args);
    }
}
