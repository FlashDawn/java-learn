package com.example.day14.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Day14：用户内存仓库。为什么 ConcurrentHashMap：单机 Demo 并发读写安全，第 3 周再换 ORM。
 * 对照笔记：§2。
 */
@Repository
public class InMemoryUserRepository {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public User saveNew(User user) {
        long id = seq.getAndIncrement();
        user.setId(id);
        store.put(id, user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public User update(User user) {
        store.put(user.getId(), user);
        return user;
    }

    public boolean deleteById(Long id) {
        return store.remove(id) != null;
    }
}
