package com.example.day38.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/** 用户服务契约（与 Day37 一致）。 */
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
