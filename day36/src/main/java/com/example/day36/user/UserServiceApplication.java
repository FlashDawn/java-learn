package com.example.day36.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Day36 用户服务（独立进程，端口 18090）。
 * 对照笔记 §1.1：这就是从 Day14 单体里「拆」出来的第一个微服务——
 * 只管 User 域，自己的进程、自己的端口、将来自己的数据库。
 * scanBasePackages 限定只扫本包：同一工程里跑两个服务，互不加载对方 Bean。
 */
@SpringBootApplication(scanBasePackages = "com.example.day36.user")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
