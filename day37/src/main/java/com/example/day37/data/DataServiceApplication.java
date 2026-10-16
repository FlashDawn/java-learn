package com.example.day37.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Day37 数据服务（端口 18092）。
 * 职责单一：演示 Nacos 的另一半能力——配置中心（对照笔记 §1.4）。
 */
@SpringBootApplication(scanBasePackages = "com.example.day37.data")
public class DataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataServiceApplication.class, args);
    }
}
