package com.example.day38.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Day38 网关（18099）：系统唯一入口（对照笔记 §1.1）。
 * 链路：Client → Gateway(18099) → lb://服务名 → Nacos 发现 → Service。
 */
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
