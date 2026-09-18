package com.example.day23.cache;

import com.example.day23.domain.Task;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Task 缓存：与 UserCache 同款写法，演示同一套 {@link RedisTemplate} 模板复用不同 Value 类型。
 */
@Component
public class TaskCache {

    private static final String KEY_PREFIX = "task:";
    private static final long TTL_SECONDS = 120;

    private final RedisTemplate<String, Task> redisTemplate;

    public TaskCache(RedisTemplate<String, Task> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void put(Task task) {
        redisTemplate.opsForValue().set(KEY_PREFIX + task.getId(), task, TTL_SECONDS, TimeUnit.SECONDS);
    }

    public Optional<Task> get(Long id) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(KEY_PREFIX + id));
    }

    public void evict(Long id) {
        redisTemplate.delete(KEY_PREFIX + id);
    }
}
