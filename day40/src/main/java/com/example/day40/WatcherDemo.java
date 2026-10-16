package com.example.day40;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Day40 Demo 3：Watcher（对照笔记 §1.3）。
 *
 * Watcher = ZK 的「变更推送」机制：客户端对节点注册监听，
 * 节点变化时 ZK 主动通知——免去轮询。这是 ZK 做协调的核心武器。
 *
 * 本 Demo 实证两件事：
 * ① 原生 Watcher 是 **一次性** 的：触发一次就自动注销，改两次只收到第一次通知
 *    ——忘了重注册 = 后续变更全丢，这是 ZK 新手第一大坑；
 * ② Curator 的 NodeCache 帮你「自动重注册」，实现持续监听。
 *
 * 与 Day37 对照：Nacos 用 gRPC 长连接推送服务列表变更，ZK 用 Watcher——
 * 「订阅推送」是所有注册中心/协调服务的共同语言，只是协议不同。
 */
public class WatcherDemo {

    public static void main(String[] args) throws Exception {
        try (CuratorFramework client = Zk.newClient()) {
            ZNodeDemo.deleteQuietly(client, "/day40");
            client.create().creatingParentsIfNeeded()
                    .forPath("/day40/config", "v1".getBytes());

            // ============ ① 原生 Watcher：一次性 ============
            CountDownLatch firstFire = new CountDownLatch(1);
            // usingWatcher 注册的就是原生一次性 Watcher
            client.getData().usingWatcher((org.apache.zookeeper.Watcher) event ->
                    System.out.println("[一次性 Watcher] 收到通知: " + event.getType()
                            + " —— 这次之后我就被注销了")
            ).forPath("/day40/config");

            client.setData().forPath("/day40/config", "v2".getBytes());
            firstFire.await(3, TimeUnit.SECONDS);

            client.setData().forPath("/day40/config", "v3".getBytes()); // 第二次变更
            Thread.sleep(1_000); // 等一等，证明没有第二次通知
            System.out.println("[一次性 Watcher] 第二次变更 v3 已写入，但上面没有新通知 —— 一次性实锤");

            // ============ ② NodeCache：Curator 封装的持续监听 ============
            java.util.concurrent.atomic.AtomicInteger received = new java.util.concurrent.atomic.AtomicInteger();
            NodeCache cache = new NodeCache(client, "/day40/config");
            cache.getListenable().addListener(() -> {
                received.incrementAndGet();
                System.out.println("[NodeCache] 第 " + received.get() + " 次通知，当前值 -> "
                        + new String(cache.getCurrentData().getData()));
            });
            cache.start(true);

            client.setData().forPath("/day40/config", "v4".getBytes());
            Thread.sleep(500); // 留出投递间隔：变更快于投递时通知会合并，只能看到最新值（笔记 §1.3 坑）
            client.setData().forPath("/day40/config", "v5".getBytes());
            Thread.sleep(1_500);
            System.out.println("[NodeCache] 共收到 " + received.get() + " 次通知"
                    + " —— 持续监听成立；生产请用 Curator 封装，别手写重注册");
            cache.close();
        }
    }
}
