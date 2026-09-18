package com.example.day14.task;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Day14：Task REST API。
 * 分段对齐计划：POST/GET /tasks、GET list、PUT /tasks/{id}/status。
 * 对照笔记：§1.1。
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ========== POST /tasks ==========
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@Valid @RequestBody TaskCreateRequest request) {
        return taskService.create(request);
    }

    // ========== GET /tasks/{id} ==========
    @GetMapping("/{id}")
    public Task get(@PathVariable Long id) {
        return taskService.get(id);
    }

    // ========== GET /tasks ==========
    @GetMapping
    public List<Task> list() {
        return taskService.list();
    }

    // ========== PUT /tasks/{id}/status ==========
    @PutMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id, @Valid @RequestBody TaskStatusRequest request) {
        return taskService.updateStatus(id, request);
    }
}
