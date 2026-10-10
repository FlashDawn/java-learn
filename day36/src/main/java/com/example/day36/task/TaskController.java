package com.example.day36.task;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务服务的聚合接口：任务数据在本地，负责人数据在 user-service。
 * 对照笔记 §1.3（同步通信）与 §1.6（硬编码地址的痛点——Day37 Nacos 要解决的问题）。
 */
@RestController
public class TaskController {

    /**
     * 痛点现场：对端地址写死在代码里。
     * user-service 换机器/换端口/扩成 3 个实例，这里全部失效——
     * 所以生产微服务不用硬编码，用服务发现（Day37 Nacos）。
     */
    private static final String USER_SERVICE_URL = "http://127.0.0.1:18090";

    private final RestTemplate restTemplate;

    private final Map<Long, Map<String, Object>> tasks = Map.of(
            1L, Map.of("id", 1L, "title", "写周报", "ownerId", 1L),
            2L, Map.of("id", 2L, "title", "修线上 Bug", "ownerId", 2L));

    public TaskController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /** 聚合接口：本地任务 + 远程用户，拼成完整视图返回 */
    @GetMapping("/tasks/{id}/detail")
    public Object detail(@PathVariable Long id) {
        Map<String, Object> task = tasks.get(id);
        if (task == null) {
            return Map.of("error", "task " + id + " not found");
        }

        // 跨进程调用：单体里这是一句本地方法调用，微服务里变成一次 HTTP 网络请求
        // ——延迟、超时、对端宕机都成了必须考虑的问题（对照笔记 §1.3 的代价）
        Object owner = restTemplate.getForObject(
                USER_SERVICE_URL + "/users/" + task.get("ownerId"), Object.class);

        Map<String, Object> detail = new HashMap<>(task);
        detail.put("owner", owner);
        return detail;
    }
}
