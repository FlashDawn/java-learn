package com.example.day14.task;

import com.example.day14.common.BusinessException;
import com.example.day14.common.NotFoundException;
import com.example.day14.user.InMemoryUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Day14：TaskService —— CRUD + 状态变更，写方法 @Transactional。
 * 对照笔记：§1.5。为什么创建前校验 user 存在：保持领域一致性，避免孤儿任务。
 */
@Service
public class TaskService {

    private final InMemoryTaskRepository taskRepository;
    private final InMemoryUserRepository userRepository;

    public TaskService(InMemoryTaskRepository taskRepository, InMemoryUserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Task create(TaskCreateRequest request) {
        userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found: " + request.getUserId()));
        Task task = new Task(null, request.getUserId(), request.getTitle(), TaskStatus.CREATED);
        return taskRepository.saveNew(task);
    }

    @Transactional(readOnly = true)
    public Task get(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Task> list() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task updateStatus(Long id, TaskStatusRequest request) {
        Task task = get(id);
        TaskStatus next = request.getStatus();
        // 为什么校验迁移：防止随意把 SUCCESS 改回 CREATED（简化状态机）
        if (task.getStatus() == TaskStatus.SUCCESS || task.getStatus() == TaskStatus.FAILED) {
            throw new BusinessException("终态任务不可再改状态: " + task.getStatus());
        }
        task.setStatus(next);
        return taskRepository.update(task);
    }
}
