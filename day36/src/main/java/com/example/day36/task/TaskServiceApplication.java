package com.example.day36.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Day36 任务服务（独立进程，端口 18091）。
 * 需要调用 user-service 才能拼出完整视图——这就是「服务间通信」的最小实例（对照笔记 §1.3）。
 */
@SpringBootApplication(scanBasePackages = "com.example.day36.task")
public class TaskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
