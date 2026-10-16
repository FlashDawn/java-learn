package com.example.day38.task;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * user-service 的声明式客户端（对照笔记 §1.2）。
 * 对比 Day37 的 RestTemplate：没有 URL 拼接、没有 getForObject——
 * 接口 + 注解描述「调哪个服务的哪个接口」，Feign 动态代理出实现，
 * 底层仍走 Nacos 服务发现 + LoadBalancer 选实例。
 * 调用方写法与调本地 Spring Bean 完全一样，这是 Feign 的核心价值。
 */
@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/users/{id}")
    Object getUser(@PathVariable("id") Long id);
}
