package com.example.day8.service;

import com.example.day8.domain.User;
import com.example.day8.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Day 8：@Service —— 业务层刻板注解 + 构造器 DI。
 * 对照笔记 §1.3 IoC/DI、§1.4。综合项目：User Service。
 */
@Service
public class UserService {

    /** 依赖由 Container 注入：为什么——业务类不负责 new Repository */
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String id, String name) {
        return userRepository.save(new User(id, name));
    }

    public User find(String id) {
        return userRepository.findById(id);
    }
}
