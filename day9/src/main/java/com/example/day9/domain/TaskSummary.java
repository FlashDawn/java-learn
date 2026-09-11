package com.example.day9.domain;

/**
 * Day 9：Task 摘要 DTO（普通对象，非切面目标）。
 * 对照笔记 §2。综合项目：Task 查询响应。
 */
public class TaskSummary {

    private final String id;
    private final String title;

    public TaskSummary(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "TaskSummary{id='" + id + "', title='" + title + "'}";
    }
}
