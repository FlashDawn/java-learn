package com.example.day8.repository;

import com.example.day8.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Day 8：@Repository —— 数据访问层刻板注解（本质仍是 @Component）。
 * 对照笔记 §1.5 Component Scan / 注解。综合项目：User 持久化入口。
 */
@Repository
public class UserRepository {

    /** 内存 Map 代替 DB：为什么——本日只证 IoC，不引入 JDBC */
    private final Map<String, User> store = new ConcurrentHashMap<>();

    public User save(User user) {
        store.put(user.getId(), user);
        return user;
    }

    public User findById(String id) {
        return store.get(id);
    }
}
