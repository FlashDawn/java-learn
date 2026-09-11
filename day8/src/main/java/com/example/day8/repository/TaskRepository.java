package com.example.day8.repository;

import com.example.day8.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Day 8：@Repository —— Task 内存仓储。
 * 对照笔记 §1.5。综合项目：Task 持久化入口。
 */
@Repository
public class TaskRepository {

    private final Map<String, Task> store = new ConcurrentHashMap<>();

    public Task save(Task task) {
        store.put(task.getId(), task);
        return task;
    }

    public Task findById(String id) {
        return store.get(id);
    }
}
