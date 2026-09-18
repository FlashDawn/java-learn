package com.example.day23.string;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * StringRedisTemplate 演示：Key 和 Value 都是 String。
 *
 * <p>典型用途：计数器、分布式锁、简单开关、Session 片段。比 {@code RedisTemplate} 轻量。</p>
 */
@Component
public class StringValueDemo {

    private final StringRedisTemplate stringRedisTemplate;

    public StringValueDemo(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /** 演示 incr：Redis 单线程原子自增，常用于阅读数、库存预扣。 */
    public long incrementPageView(String pageId) {
        Long v = stringRedisTemplate.opsForValue().increment("pv:" + pageId);
        return v == null ? -1 : v;
    }

    /** 演示 setIfAbsent + TTL：最简单的「占位锁」雏形。 */
    public boolean tryLock(String lockKey, String owner, long ttlSeconds) {
        Boolean ok = stringRedisTemplate.opsForValue()
                .setIfAbsent(lockKey, owner, ttlSeconds, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(ok);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(stringRedisTemplate.opsForValue().get(key));
    }
}
