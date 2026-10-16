package com.example.day37.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Day37 任务服务（端口 18091）。
 * 对照笔记 §1.2：@LoadBalanced 让 RestTemplate 支持「服务名寻址」——
 * Day36 的硬编码 http://127.0.0.1:18090 在本日被消灭。
 */
@SpringBootApplication(scanBasePackages = "com.example.day37.task")
public class TaskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced  // 关键注解：拦截 RestTemplate 请求，把「服务名」换成 Nacos 里的健康实例（含负载均衡）
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
