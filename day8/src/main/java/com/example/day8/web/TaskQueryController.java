package com.example.day8.web;

import com.example.day8.domain.Task;
import com.example.day8.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Day 8：@RestController = @Controller + @ResponseBody。
 * 对照笔记 §1.5。本日 web-application-type=none，不监听端口，但仍是 Container 里的 Bean。
 * 综合项目：Task 查询 API 入口雏形。
 */
@RestController
public class TaskQueryController {

    private final TaskService taskService;

    public TaskQueryController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks/{id}")
    public Task find(@PathVariable String id) {
        return taskService.find(id);
    }
}
