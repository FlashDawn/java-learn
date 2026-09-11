package com.example.day22;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;

/**
 * Day22 入口 —— Redis 基础：六种结构 + 「为什么快」在客户端侧的最小验证。
 * <p>
 * 对照笔记 §1。综合项目将来：Task Cache / User Cache（Day23+）。
 * 前置：本机 {@code localhost:6379} 有 Redis；没有则先看 README。
 */
public class Day22App {

    public static void main(String[] args) {
        RedisURI uri = RedisURI.Builder.redis("127.0.0.1", 6379)
                .withTimeout(Duration.ofSeconds(3))
                .build();
        RedisClient client = RedisClient.create(uri);

        try (StatefulRedisConnection<String, String> conn = client.connect()) {
            RedisCommands<String, String> redis = conn.sync();
            redis.flushdb(); // 教学库清空，勿对生产实例执行

            // ========== String：任务状态快照 / 简单 KV ==========
            redis.set("task:t-1:status", "CREATED");
            System.out.println("string=" + redis.get("task:t-1:status"));

            // ========== Hash：User 对象字段，避免多次 String key ==========
            redis.hset("user:u-1", "name", "Ada");
            redis.hset("user:u-1", "email", "ada@example.com");
            System.out.println("hash.name=" + redis.hget("user:u-1", "name"));

            // ========== List：任务待处理队列（左进右出示意）==========
            redis.rpush("queue:tasks", "t-1", "t-2", "t-3");
            System.out.println("list.pop=" + redis.lpop("queue:tasks"));

            // ========== Set：某任务的标签去重集合 ==========
            redis.sadd("task:t-1:tags", "import", "urgent", "import");
            System.out.println("set.size=" + redis.scard("task:t-1:tags"));

            // ========== ZSet：按优先级排序的待办（score 越大越优先示例）==========
            redis.zadd("board:priority", 10.0, "t-2");
            redis.zadd("board:priority", 30.0, "t-1");
            redis.zadd("board:priority", 20.0, "t-3");
            System.out.println("zset.top=" + redis.zrevrange("board:priority", 0, 0));

            // ========== Stream：简易事件流（任务状态变更日志）==========
            String msgId = redis.xadd(
                    "stream:task-events",
                    java.util.Map.of("taskId", "t-1", "status", "CREATED")
            );
            System.out.println("stream.id=" + msgId);
        } catch (Exception e) {
            System.err.println("connectFailed=" + e.getMessage());
            System.err.println("hint=请先启动 Redis(localhost:6379)，见 day22/README.md");
            System.exit(1);
        } finally {
            client.shutdown();
        }
    }
}
