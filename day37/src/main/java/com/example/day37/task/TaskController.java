package com.example.day37.task;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务服务聚合接口（对照笔记 §1.2）。
 * 与 Day36 对比：URL 里写的是服务名 user-service，不是 IP:端口——
 * 实例在哪、有几个，全由 Nacos + LoadBalancer 回答。
 */
@RestController
public class TaskController {

    /** 不再硬编码地址：写「服务名」，具体实例由注册中心发现 */
    private static final String USER_SERVICE = "http://user-service";

    private final RestTemplate restTemplate;

    private final Map<Long, Map<String, Object>> tasks = Map.of(
            1L, Map.of("id", 1L, "title", "写周报", "ownerId", 1L),
            2L, Map.of("id", 2L, "title", "修线上 Bug", "ownerId", 2L));

    public TaskController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/tasks/{id}/detail")
    public Object detail(@PathVariable Long id) {
        Map<String, Object> task = tasks.get(id);
        if (task == null) {
            return Map.of("error", "task " + id + " not found");
        }

        // 服务名寻址：LoadBalancer 拦截后向 Nacos 查 user-service 的健康实例并选一个
        Object owner = restTemplate.getForObject(
                USER_SERVICE + "/users/" + task.get("ownerId"), Object.class);

        Map<String, Object> detail = new HashMap<>(task);
        detail.put("owner", owner);
        return detail;
    }
}
