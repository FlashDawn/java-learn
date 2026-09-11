package com.example.day8.domain;

/**
 * Day 8：领域模型 Task（普通 POJO，不是 Spring Bean）。
 * 对照笔记 §1.2。综合项目：Task Service 实体。
 */
public class Task {

    private final String id;
    private final String title;
    private final String ownerUserId;

    public Task(String id, String title, String ownerUserId) {
        this.id = id;
        this.title = title;
        this.ownerUserId = ownerUserId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    @Override
    public String toString() {
        return "Task{id='" + id + "', title='" + title + "', ownerUserId='" + ownerUserId + "'}";
    }
}
