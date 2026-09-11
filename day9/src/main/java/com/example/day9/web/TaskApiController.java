package com.example.day9.web;

import com.example.day9.domain.TaskSummary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Day 9：被切面拦截的 API（Join Point 所在类）。
 * 对照笔记 §1.1 AOP、§2。综合项目：Task 查询接口。
 */
@RestController
public class TaskApiController {

    /**
     * 简单 GET：切面记录请求、参数、耗时；fail=true 时抛异常供 Advice 记录。
     */
    @GetMapping("/tasks")
    public TaskSummary getTask(@RequestParam(defaultValue = "t-1") String id,
                               @RequestParam(defaultValue = "false") boolean fail) {
        // ========== Join Point：业务方法本身不知道日志横切 ==========
        if (fail) {
            // 为什么抛业务异常：让 Around Advice 的 catch 路径被走到
            throw new IllegalStateException("simulated failure for task " + id);
        }
        return new TaskSummary(id, "import-orders");
    }
}
