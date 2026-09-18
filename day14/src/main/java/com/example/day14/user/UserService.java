package com.example.day14.user;

import com.example.day14.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Day14：UserService —— 写操作加 @Transactional 演示事务边界（内存库 + H2 TM）。
 * 对照笔记：§1.5。为什么 Service 层开事务：Controller 保持薄，业务一致性放在 Service。
 */
@Service
public class UserService {

    private final InMemoryUserRepository repository;

    public UserService(InMemoryUserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User create(UserRequest request) {
        User user = new User(null, request.getName(), request.getEmail());
        return repository.saveNew(user);
    }

    @Transactional(readOnly = true)
    public User get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<User> list() {
        return repository.findAll();
    }

    @Transactional
    public User update(Long id, UserRequest request) {
        User existing = get(id);
        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        return repository.update(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("User not found: " + id);
        }
    }
}
