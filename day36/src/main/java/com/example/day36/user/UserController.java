package com.example.day36.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户服务的对外契约。微服务里「契约即边界」：
 * 别的服务只认这个 HTTP 接口，看不到也碰不到 user-service 内部实现（对照笔记 §1.2）。
 * 学习阶段用内存 Map 当数据源，重点在拆分与通信，不在存储。
 */
@RestController
public class UserController {

    private final Map<Long, Map<String, Object>> users = Map.of(
            1L, Map.of("id", 1L, "name", "张三", "email", "zhangsan@example.com"),
            2L, Map.of("id", 2L, "name", "李四", "email", "lisi@example.com"));

    @GetMapping("/users/{id}")
    public Object get(@PathVariable Long id) {
        return users.getOrDefault(id, Map.of("error", "user " + id + " not found"));
    }
}
