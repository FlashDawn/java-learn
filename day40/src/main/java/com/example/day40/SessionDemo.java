package com.example.day40;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * Day40 Demo 2：Session 生命周期（对照笔记 §1.2）。
 *
 * 关键认知：临时节点挂在 **Session** 上，不是挂在 **TCP 连接** 上。
 * - 网络抖动断开 → 只要在 sessionTimeout 内重连，会话和临时节点都还在；
 * - Session 真正过期/关闭 → ZK 自动删除该会话创建的所有临时节点。
 *
 * 这就是「服务发现」的信任机制：ZK 不需要收到「我要下线」的通知，
 * 实例宕机 → 心跳停 → Session 过期 → 节点自动消失 → 订阅者收到 Watcher 通知。
 * 与 Day37 Nacos 临时实例的心跳剔除（15s 不健康/30s 剔除）是同一思想的不同实现。
 *
 * 本 Demo 用两个客户端（观察者 + 短命注册者）实证完整链条。
 */
public class SessionDemo {

    public static void main(String[] args) throws Exception {
        // 观察者：模拟 Consumer，全程盯着 /day40/workers 下的实例列表
        try (CuratorFramework watcher = Zk.newClient()) {
            ZNodeDemo.deleteQuietly(watcher, "/day40");
            watcher.create().creatingParentsIfNeeded().forPath("/day40/workers");

            // 模拟一个 worker 实例上线：另一个客户端注册临时节点
            try (CuratorFramework worker = Zk.newClient()) {
                worker.create().withMode(CreateMode.EPHEMERAL)
                        .forPath("/day40/workers/worker-1", "172.30.48.1:20880".getBytes());
                System.out.println("[1] worker-1 上线，实例列表="
                        + watcher.getChildren().forPath("/day40/workers"));

                // ---- 场景 A：TCP 断开但 Session 未过期（网络抖动）----
                // 这里用「主动断开再立刻重连」模拟抖动：Curator 内部会重连，
                // sessionTimeout=10s 内恢复 → 临时节点纹丝不动。
                System.out.println("[2] （原理）TCP 断开≠Session 死亡：10s 内重连，节点保留。"
                        + "本 Demo 直接演示真正的死亡场景 B。");
            }
            // worker 客户端 close() → Session 被显式关闭
            System.out.println("[3] worker 客户端已 close（Session 关闭）");

            // ---- 场景 B：Session 死亡 → 临时节点被 ZK 自动删除 ----
            // 删除是服务端行为，但观察者侧看到可能有毫秒级延迟，轮询等一下
            boolean gone = false;
            for (int i = 0; i < 20; i++) {
                if (watcher.getChildren().forPath("/day40/workers").isEmpty()) {
                    gone = true;
                    break;
                }
                Thread.sleep(200);
            }
            System.out.println("[4] Session 死亡后实例列表="
                    + watcher.getChildren().forPath("/day40/workers")
                    + (gone ? " —— ZK 自动剔除，无需任何「下线通知」" : " —— 仍在？不应发生"));
        }
    }
}
