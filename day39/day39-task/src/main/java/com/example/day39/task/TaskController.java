package com.example.day39.task;

import com.example.day39.api.TaskJob;
import com.example.day39.api.WorkerService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Consumer 入口（对照笔记 §1.1/§1.6）。
 * @DubboReference 注入的不是本地实现，而是 Dubbo 生成的 RPC 代理：
 * 调方法 → 序列化参数 → 从 Nacos 拿到 Provider 地址（含负载均衡选一个）
 * → TCP 长连接发送 → 反序列化结果返回。全程看起来像本地调用。
 */
@RestController
public class TaskController {

    @DubboReference
    private WorkerService workerService;

    @GetMapping("/tasks/dispatch")
    public String dispatch(@RequestParam String title) {
        // 这一行是一次跨进程 RPC，不是本地方法调用
        String result = workerService.execute(new TaskJob(1L, title));
        return "[task-service] 已派发，RPC 返回: " + result;
    }
}
