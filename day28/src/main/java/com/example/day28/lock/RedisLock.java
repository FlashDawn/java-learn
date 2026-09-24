package com.example.day28.lock;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

/**
 * Redis 分布式锁（对照笔记 §1.5）。
 * 为什么不用 synchronized：它只锁单个 JVM 进程；服务多实例部署时，
 * 两个实例可以同时进入「临界区」，必须用所有实例共享的第三方（Redis）做仲裁。
 */
@Component
public class RedisLock {

    private final StringRedisTemplate redis;

    public RedisLock(StringRedisTemplate redis) {
        this.redis = redis;
    }

    /**
     * 尝试加锁。SET key value NX PX ttl 是一条原子命令：
     * NX = key 不存在才设置（互斥）；PX = 带过期时间（持锁者宕机也不会死锁）。
     * 拆成 setIfAbsent + expire 两条命令会有「加锁后、设过期前宕机」的死锁窗口。
     *
     * @return 锁令牌（解锁时校验用）；null 表示没抢到
     */
    public String tryLock(String lockKey, Duration ttl) {
        String token = UUID.randomUUID().toString();
        Boolean ok = redis.opsForValue().setIfAbsent(lockKey, token, ttl);
        return Boolean.TRUE.equals(ok) ? token : null;
    }

    /**
     * 解锁。为什么用 Lua 而不是直接 delete：
     * 「判断是自己的锁」和「删除」必须是原子的，否则会出现——
     * A 判断通过 → A 的锁刚好过期被 B 抢到 → A 执行 delete 把 B 的锁删了。
     * Lua 脚本在 Redis 内原子执行，杜绝这个窗口。
     */
    public boolean unlock(String lockKey, String token) {
        String lua = """
                if redis.call('get', KEYS[1]) == ARGV[1] then
                    return redis.call('del', KEYS[1])
                else
                    return 0
                end
                """;
        Long result = redis.execute(
                new DefaultRedisScript<>(lua, Long.class),
                Collections.singletonList(lockKey),
                token);
        return Long.valueOf(1L).equals(result);
    }
}
