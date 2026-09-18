package com.example.day14.task;

/**
 * Day14：任务领域模型。对照笔记：§2。
 */
public class Task {

    private Long id;
    private Long userId;
    private String title;
    private TaskStatus status;

    public Task() {
    }

    public Task(Long id, Long userId, String title, TaskStatus status) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
