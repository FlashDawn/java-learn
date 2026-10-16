package com.example.day37.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Day37 用户服务（端口 18090）。
 * 与 Day36 的唯一差异：启动时自动向 Nacos 注册自己（对照笔记 §1.1）——
 * 靠 classpath 上的 nacos-discovery starter，零代码。
 * 用 `--spring.profiles.active=user` 启动以加载 application-user.yml。
 */
@SpringBootApplication(scanBasePackages = "com.example.day37.user")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
