package com.example.day38.task;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务聚合接口：Feign 调 user-service + 熔断降级（对照笔记 §1.2/§1.4）。
 */
@RestController
public class TaskController {

    private final UserClient userClient;

    private final Map<Long, Map<String, Object>> tasks = Map.of(
            1L, Map.of("id", 1L, "title", "写周报", "ownerId", 1L),
            2L, Map.of("id", 2L, "title", "修线上 Bug", "ownerId", 2L));

    public TaskController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/tasks/{id}/detail")
    @CircuitBreaker(name = "userQuery", fallbackMethod = "detailFallback")
    public Object detail(@PathVariable Long id) {
        Map<String, Object> task = tasks.get(id);
        if (task == null) {
            return Map.of("error", "task " + id + " not found");
        }
        // 像调本地 Bean 一样调远程服务——Feign 的全部意义就在这一行
        Object owner = userClient.getUser((Long) task.get("ownerId"));

        Map<String, Object> detail = new HashMap<>(task);
        detail.put("owner", owner);
        detail.put("degraded", false);
        return detail;
    }

    /**
     * 降级方法：user-service 挂掉/熔断打开时由 Resilience4j 调用。
     * 签名 = 原方法参数 + Throwable 收尾。返回「够用但不完整」的响应，
     * 让用户看到任务本体，只是暂时没有负责人详情——核心链路不崩。
     */
    public Object detailFallback(Long id, Throwable t) {
        Map<String, Object> task = tasks.get(id);
        if (task == null) {
            return Map.of("error", "task " + id + " not found");
        }
        Map<String, Object> detail = new HashMap<>(task);
        detail.put("owner", Map.of("degraded", true, "reason", "user-service 不可用，已降级"));
        detail.put("degraded", true);
        return detail;
    }
}
