package com.example.day40;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Day40 Demo 5：Distributed Lock（对照笔记 §1.5）。
 *
 * 场景呼应 Day6：unsafeCount 多线程 +1，JVM 内可用 synchronized 保护；
 * 但如果计数器在「另一个进程/另一台机器」手里（比如库存服务），
 * synchronized 管不到——需要跨进程都认的锁：分布式锁。
 *
 * ZK 锁原理（InterProcessMutex 封装）：争抢者在 /day40/mutex 下建
 * EPHEMERAL_SEQUENTIAL 节点，序号最小者持锁，其余 Watch 自己前一个节点
 * ——「排队等通知」。对比 Day28 Redis 锁（SET NX PX + 自旋重试）：
 * - Redis：抢不到就自己轮询再抢，客户端活跃自旋；
 * - ZK：抢不到就挂起等前驱的 Watcher 通知，天然公平（先来先得）+ 无惊群。
 *
 * 本 Demo：10 个线程（模拟 10 个进程实例）对同一计数器 +1000，
 * 无锁跑一次（脏数据），ZK 锁跑一次（精确值），对比输出。
 */
public class DistributedLockDemo {

    private static int unsafeCount = 0;
    private static int lockedCount = 0;

    public static void main(String[] args) throws Exception {
        ZNodeDemo.deleteQuietly(Zk.newClient(), "/day40");

        // ============ 第一回合：无锁（复刻 Day6 的脏数据） ============
        // 50k 次/线程：竞态是小概率事件，剂量放大到必然暴露（锁的正确性与剂量无关，见下）
        int unlocked = runRace(false, 50_000);
        System.out.println("[无锁] 期望 " + unlocked + "，实际 " + unsafeCount
                + "，丢失更新 " + (unlocked - unsafeCount) + " 次 —— 与 Day6 同一病因");

        // ============ 第二回合：ZK 分布式锁 ============
        // 每次 acquire 是一次 ZK 网络往返（约毫秒级），1k 次/线程已经足够证明互斥性
        int locked = runRace(true, 1_000);
        System.out.println("[ZK锁] 期望 " + locked + "，实际 " + lockedCount
                + " —— 跨进程互斥成立（线程换成独立 JVM 结论相同，锁在 ZK 服务端）");
    }

    /**
     * 模拟真实业务的「读-改-写」：先读出值，yield 放大竞态窗口（等价于业务处理耗时），
     * 再写回。裸 `count++` 在现代 JIT 下太快，竞态窗口太小，可能侥幸不丢数据——
     * 真实业务（查库存→扣减→写回）的窗口本来就大，yield 是在还原真实，不是作弊。
     */
    private static void readModifyWrite(boolean locked) {
        if (locked) {
            int v = lockedCount;
            Thread.yield();
            lockedCount = v + 1;
        } else {
            int v = unsafeCount;
            Thread.yield();
            unsafeCount = v + 1;
        }
    }

    /**
     * @param useLock   true=每轮 +1 都在 ZK 锁保护内；false=裸奔
     * @param perThread 每线程递增次数；返回期望值（线程数 × perThread）
     */
    private static int runRace(boolean useLock, int perThread) throws Exception {
        int threads = 10;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch startGate = new CountDownLatch(1); // 发令枪：10 线程同时起跑，最大化重叠
        CountDownLatch done = new CountDownLatch(threads);

        for (int t = 0; t < threads; t++) {
            // 每个线程一个独立客户端连接——模拟「10 个不同进程」，
            // 证明互斥不是 JVM 内的锁效果，而是 ZK 服务端裁决的
            CuratorFramework client = Zk.newClient();
            InterProcessMutex mutex = new InterProcessMutex(client, "/day40/mutex");
            pool.submit(() -> {
                try {
                    startGate.await();
                    for (int i = 0; i < perThread; i++) {
                        if (useLock) {
                            mutex.acquire();
                            try {
                                readModifyWrite(true);
                            } finally {
                                mutex.release();
                            }
                        } else {
                            readModifyWrite(false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    done.countDown();
                    try {
                        client.close();
                    } catch (Exception ignored) {
                    }
                }
            });
        }
        startGate.countDown(); // 鸣枪
        done.await(180, TimeUnit.SECONDS);
        pool.shutdown();
        return threads * perThread;
    }
}
