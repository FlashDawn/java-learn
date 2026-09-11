package com.example.day8.service;

import com.example.day8.domain.Task;
import com.example.day8.domain.User;
import com.example.day8.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Day 8：@Service + @Autowired 字段注入示例（教学对照；生产更推荐构造器注入）。
 * 对照笔记 §1.3、§1.4。综合项目：Task Service。
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    /** 字段注入：为什么单独演示——计划条目含 @Autowired，对照构造器注入的差异 */
    @Autowired
    private UserService userService;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createForUser(String taskId, String title, String userId) {
        User owner = userService.find(userId);
        if (owner == null) {
            throw new IllegalArgumentException("user not found: " + userId);
        }
        return taskRepository.save(new Task(taskId, title, owner.getId()));
    }

    public Task find(String id) {
        return taskRepository.findById(id);
    }
}
