package com.example.day38.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Day38 任务服务（18101）。
 * @EnableFeignClients 扫描本包下 @FeignClient 接口，动态代理生成实现（对照笔记 §1.2）。
 */
@SpringBootApplication
@EnableFeignClients
public class TaskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
    }
}
