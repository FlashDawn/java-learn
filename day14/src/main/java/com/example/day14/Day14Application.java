package com.example.day14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Day14：Spring Boot 项目第一版入口。
 * 钉死概念：REST + 校验 + 全局异常 + 日志切面 + @Transactional（第二周验收整合）。
 * 对照笔记：§1、§2、§附录验收清单。综合项目 User/Task API 骨架。
 */
@SpringBootApplication
@EnableTransactionManagement
public class Day14Application {

    public static void main(String[] args) {
        SpringApplication.run(Day14Application.class, args);
    }
}
