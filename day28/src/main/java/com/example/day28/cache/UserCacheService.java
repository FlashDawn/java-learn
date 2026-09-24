package com.example.day28.cache;

import com.example.day28.domain.User;
import com.example.day28.lock.RedisLock;
import com.example.day28.store.InMemoryUserDb;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * User 缓存服务：Cache-Aside 模式的完整实现（对照笔记 §1.1～§1.4）。
 * 一个类串起四个当日概念：
 *   §1.1 Cache-Aside 读写路径
 *   §1.2 穿透 → 空值缓存
 *   §1.3 击穿 → 互斥锁重建
 *   §1.4 雪崩 → TTL 加随机抖动
 */
@Service
public class UserCacheService {

    /** 基础 TTL 30 分钟；为什么加抖动见 jitterTtl() */
    private static final Duration BASE_TTL = Duration.ofMinutes(30);

    /** 空值 TTL 必须短：2 分钟。太长会把「后来真实创建的用户」挡在缓存外 */
    private static final Duration NULL_TTL = Duration.ofMinutes(2);

    /** 真实用户用类型化模板（JSON 直接反序列化成 User） */
    private final RedisTemplate<String, User> userRedis;
    /** 空值占位用独立 key + String 模板，避免和 User 类型混在一个序列化器里 */
    private final StringRedisTemplate stringRedis;
    private final InMemoryUserDb db;
    private final RedisLock lock;

    public UserCacheService(RedisTemplate<String, User> userRedis, StringRedisTemplate stringRedis,
                            InMemoryUserDb db, RedisLock lock) {
        this.userRedis = userRedis;
        this.stringRedis = stringRedis;
        this.db = db;
        this.lock = lock;
    }

    /**
     * Cache-Aside 读路径：先查缓存 → 未命中回源 DB → 写入缓存。
     * 击穿防护：缓存未命中时先抢锁，只有抢到锁的线程回源 DB；
     * 没抢到的线程短暂等待后重读缓存（此时锁持有者已把数据回填）。
     */
    public User getById(Long id) {
        String key = keyOf(id);

        // 第一次读缓存：先看空值占位（防穿透），再看真实缓存
        if (isNullCached(id)) {
            return null;
        }
        User cached = userRedis.opsForValue().get(key);
        if (cached != null) {
            return cached;
        }

        // 缓存未命中：抢互斥锁，防击穿（热点 key 失效瞬间，只有一线程回源）
        String lockKey = "lock:user:" + id;
        String token = lock.tryLock(lockKey, Duration.ofSeconds(10));
        if (token == null) {
            // 没抢到锁：等 50ms 让持锁者回填缓存，然后重读
            sleep(50);
            if (isNullCached(id)) {
                return null;
            }
            return userRedis.opsForValue().get(key);
        }

        try {
            // 双重检查：拿到锁后再读一次缓存——等锁期间可能已被别的线程回填
            if (isNullCached(id)) {
                return null;
            }
            User doubleCheck = userRedis.opsForValue().get(key);
            if (doubleCheck != null) {
                return doubleCheck;
            }

            User user = db.selectById(id);
            if (user == null) {
                // 穿透防护：DB 没有也缓存空值占位（短 TTL），挡住对不存在 id 的反复查询
                stringRedis.opsForValue().set(nullKeyOf(id), "1", NULL_TTL);
                return null;
            }
            userRedis.opsForValue().set(key, user, jitterTtl());
            return user;
        } finally {
            lock.unlock(lockKey, token);
        }
    }

    /**
     * Cache-Aside 写路径：先更新 DB，再删缓存（不是更新缓存）。
     * 为什么删而不是改：缓存可能是「DB 多表 join 的聚合结果」，改缓存成本高且易错；
     * 删掉后下次读自然回源重建，懒加载永远拿到最新值。
     * 为什么先 DB 后缓存：反过来「先删缓存再写 DB」中间窗口内，
     * 并发读会把旧值重新回填缓存，造成长时间不一致。
     */
    public void updateUser(User user) {
        db.update(user);
        userRedis.delete(keyOf(user.getId()));
        stringRedis.delete(nullKeyOf(user.getId()));
    }

    private boolean isNullCached(Long id) {
        return Boolean.TRUE.equals(stringRedis.hasKey(nullKeyOf(id)));
    }

    private String keyOf(Long id) {
        return "user:" + id;
    }

    private String nullKeyOf(Long id) {
        return "nulluser:" + id;
    }

    /**
     * 雪崩防护：TTL = 30min + [0, 5min) 随机抖动。
     * 如果一批 key 同时写入且 TTL 相同，它们会在同一时刻集体失效，
     * 瞬间流量全部打到 DB。加抖动让失效时间错开。
     */
    private Duration jitterTtl() {
        return BASE_TTL.plusMillis(ThreadLocalRandom.current().nextInt(300_000));
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
