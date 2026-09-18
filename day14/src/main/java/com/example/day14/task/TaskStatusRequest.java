package com.example.day14.task;

import jakarta.validation.constraints.NotNull;

/**
 * Day14：更新任务状态请求。对照笔记：§1.1 PUT /tasks/{id}/status。
 */
public class TaskStatusRequest {

    @NotNull(message = "status 不能为空")
    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
