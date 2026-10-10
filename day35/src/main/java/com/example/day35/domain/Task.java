package com.example.day35.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * 任务实体。Day35 与 Day14 的关键差异：状态真正落在 PostgreSQL，
 * 服务重启不丢——这是「消息驱动 + 状态落库」架构的前提（对照笔记 §2.1）。
 */
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime finishedAt;

    protected Task() {
        // JPA 要求无参构造
    }

    public Task(String title) {
        this.title = title;
        this.status = TaskStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }
}
