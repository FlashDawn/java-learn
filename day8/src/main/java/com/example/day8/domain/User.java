package com.example.day8.domain;

/**
 * Day 8：领域模型 User（普通 POJO，不是 Spring Bean）。
 * 对照笔记 §1.2 Bean：只有被 Container 管理的对象才叫 Bean。
 * 综合项目：User Service 实体。
 */
public class User {

    private final String id;
    private final String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name + "'}";
    }
}
