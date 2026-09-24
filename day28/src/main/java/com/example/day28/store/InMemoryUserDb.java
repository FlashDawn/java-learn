package com.example.day28.store;

import com.example.day28.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟数据库（MySQL）。Day28 用内存 Map 代替真实 DB，
 * 但每次查询故意 sleep 100ms，让「缓存命中 vs 回源 DB」的耗时差异肉眼可见（对照笔记 §1.1）。
 * dbHitCount 计数器用于证明：缓存生效时 DB 没有被访问。
 */
@Repository
public class InMemoryUserDb {

    private final Map<Long, User> table = new ConcurrentHashMap<>();
    /** 回源次数计数：缓存工作的直接证据 */
    private final AtomicInteger dbHitCount = new AtomicInteger(0);

    public InMemoryUserDb() {
        table.put(1L, new User(1L, "张三", "zhangsan@example.com"));
        table.put(2L, new User(2L, "李四", "lisi@example.com"));
    }

    public User selectById(Long id) {
        simulateSlowDisk();
        dbHitCount.incrementAndGet();
        return table.get(id);
    }

    public void update(User user) {
        simulateSlowDisk();
        table.put(user.getId(), user);
    }

    public int getDbHitCount() {
        return dbHitCount.get();
    }

    public void resetDbHitCount() {
        dbHitCount.set(0);
    }

    /** 模拟磁盘 IO：没有这 100ms，缓存的价值在 Demo 里体现不出来 */
    private void simulateSlowDisk() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
