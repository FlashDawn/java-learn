package com.example.day14.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Day14：创建任务请求。对照笔记：§1.2。
 */
public class TaskCreateRequest {

    @NotNull(message = "userId 不能为空")
    private Long userId;

    @NotBlank(message = "title 不能为空")
    @Size(max = 128, message = "title 最长 128")
    private String title;

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
}
