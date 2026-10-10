package com.example.day35.web;

import com.example.day35.domain.Task;
import com.example.day35.domain.TaskRepository;
import com.example.day35.mq.TaskEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 任务 REST 入口（对照笔记 §1 链路起点）。
 * POST 只做两件事：落库(PENDING) + 发消息，立刻返回——
 * 不等 Worker 执行完，调用方无感知，这就是异步解耦。
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository repository;
    private final TaskEventPublisher publisher;

    public TaskController(TaskRepository repository, TaskEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @PostMapping
    public Task create(@RequestParam String title) {
        // 先落库再发消息：顺序不能反——若先发消息，Worker 回库查不到记录会丢消息
        Task task = repository.save(new Task(title));
        publisher.publishTaskCreated(task.getId(), task.getTitle());
        return task;
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable Long id) {
        return repository.findById(id).map(t -> (Object) t).orElse("task " + id + " not found");
    }

    @GetMapping
    public List<Task> list() {
        return repository.findAll();
    }
}
