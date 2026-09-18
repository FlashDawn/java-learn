package com.example.day23;

import com.example.day23.cache.TaskCache;
import com.example.day23.cache.UserCache;
import com.example.day23.domain.Task;
import com.example.day23.domain.User;
import com.example.day23.string.StringValueDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动后自动演示：写入 → 读取 → 删除，一条链路走通。
 */
@Component
public class DemoRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoRunner.class);

    private final UserCache userCache;
    private final TaskCache taskCache;
    private final StringValueDemo stringDemo;

    public DemoRunner(UserCache userCache, TaskCache taskCache, StringValueDemo stringDemo) {
        this.userCache = userCache;
        this.taskCache = taskCache;
        this.stringDemo = stringDemo;
    }

    @Override
    public void run(String... args) {
        log.info("=== Day23 Spring Data Redis Demo 开始 ===");

        // 1) User 缓存：JSON 序列化对象
        User u = new User(1001L, "李雷");
        userCache.put(u);
        log.info("写入 User: {}", u);
        userCache.get(1001L).ifPresent(found -> log.info("读取 User: {}", found));

        // 2) Task 缓存：同一套配置复用另一类型
        Task t = new Task(2001L, "写周报", false);
        taskCache.put(t);
        log.info("写入 Task: {}", t);
        taskCache.get(2001L).ifPresent(found -> log.info("读取 Task: {}", found));

        // 3) StringRedisTemplate：计数器与简单锁
        long pv = stringDemo.incrementPageView("home");
        log.info("页面 home 访问次数: {}", pv);
        boolean locked = stringDemo.tryLock("lock:order:9001", "runner-1", 10);
        log.info("尝试占用锁 lock:order:9001 -> {}", locked);
        log.info("锁值: {}", stringDemo.get("lock:order:9001").orElse("<none>"));

        log.info("=== Demo 结束 ===");
    }
}
