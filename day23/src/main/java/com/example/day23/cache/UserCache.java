package com.example.day23.cache;

import com.example.day23.domain.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * User 缓存：用 {@link RedisTemplate} 存取 User 对象。
 *
 * <p>key 约定：{@code user:1}、{@code user:2}… 一目了然、方便用 KEYS/MEMORY 排查。</p>
 */
@Component
public class UserCache {

    private static final String KEY_PREFIX = "user:";
    private static final long TTL_SECONDS = 300;

    private final RedisTemplate<String, User> redisTemplate;

    public UserCache(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void put(User user) {
        // 带 TTL 写入，避免永久堆积脏数据
        redisTemplate.opsForValue().set(KEY_PREFIX + user.getId(), user, TTL_SECONDS, TimeUnit.SECONDS);
    }

    public Optional<User> get(Long id) {
        User u = redisTemplate.opsForValue().get(KEY_PREFIX + id);
        return Optional.ofNullable(u);
    }

    public void evict(Long id) {
        redisTemplate.delete(KEY_PREFIX + id);
    }
}
