package com.example.day28;

import com.example.day28.cache.UserCacheService;
import com.example.day28.domain.User;
import com.example.day28.lock.RedisLock;
import com.example.day28.store.InMemoryUserDb;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Day28 自动演示：启动后按顺序跑四个场景，把笔记 §1 的概念变成可观察的输出。
 * 对照笔记 §2「运行验证」的预期输出。
 */
@Component
public class Day28DemoRunner implements CommandLineRunner {

    private final UserCacheService cacheService;
    private final InMemoryUserDb db;
    private final RedisLock lock;
    private final StringRedisTemplate redis;

    public Day28DemoRunner(UserCacheService cacheService, InMemoryUserDb db,
                           RedisLock lock, StringRedisTemplate redis) {
        this.cacheService = cacheService;
        this.db = db;
        this.lock = lock;
        this.redis = redis;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========== Day28 Redis 综合实战演示 ==========\n");

        // ========== 场景 1：Cache-Aside 读路径（对照笔记 §1.1）==========
        // 第一次读：缓存未命中 → 回源 DB（100ms）→ 写缓存
        // 第二次读：缓存命中 → 微秒级返回，db-hits 不再增长
        db.resetDbHitCount();
        long t1 = System.currentTimeMillis();
        cacheService.getById(1L);
        long first = System.currentTimeMillis() - t1;

        long t2 = System.currentTimeMillis();
        cacheService.getById(1L);
        long second = System.currentTimeMillis() - t2;

        System.out.println("[场景1 Cache-Aside] 首次(回源DB): " + first + "ms, 二次(命中缓存): " + second
                + "ms, DB访问次数: " + db.getDbHitCount());

        // ========== 场景 2：缓存穿透 → 空值缓存（对照笔记 §1.2）==========
        // 查询不存在的 id=999：第一次回源 DB 后缓存空值，之后 2 分钟内不再打 DB
        db.resetDbHitCount();
        cacheService.getById(999L);
        cacheService.getById(999L);
        cacheService.getById(999L);
        System.out.println("[场景2 穿透防护] 查询不存在id=999 三次, DB访问次数: " + db.getDbHitCount()
                + " (预期=1, 空值已缓存)");
        System.out.println("  证据: redis 中 nulluser:999 = " + redis.opsForValue().get("nulluser:999"));

        // ========== 场景 3：缓存击穿 → 互斥锁重建（对照笔记 §1.3）==========
        // 删掉热点 key 模拟失效，8 个线程同时读：只有 1 个回源 DB
        redis.delete("user:2");
        db.resetDbHitCount();
        ExecutorService pool = Executors.newFixedThreadPool(8);
        List<Future<User>> futures = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            futures.add(pool.submit(() -> cacheService.getById(2L)));
        }
        for (Future<User> f : futures) {
            f.get();
        }
        pool.shutdown();
        System.out.println("[场景3 击穿防护] 热点key失效后 8 线程并发读, DB访问次数: " + db.getDbHitCount()
                + " (预期=1, 互斥锁生效)");

        // ========== 场景 4：分布式锁互斥（对照笔记 §1.5）==========
        // 同一把锁，第二个加锁者必须失败；解锁后才能再加
        String token1 = lock.tryLock("lock:demo", Duration.ofSeconds(30));
        String token2 = lock.tryLock("lock:demo", Duration.ofSeconds(30));
        boolean released = lock.unlock("lock:demo", token1);
        String token3 = lock.tryLock("lock:demo", Duration.ofSeconds(30));
        System.out.println("[场景4 分布式锁] 首次加锁: " + (token1 != null)
                + ", 重复加锁: " + (token2 != null) + " (预期false)"
                + ", 解锁: " + released + ", 解锁后再加锁: " + (token3 != null));
        lock.unlock("lock:demo", token3);

        System.out.println("\n========== 演示完毕，Web 接口在 8080 端口可继续手动验证 ==========\n");
    }
}
