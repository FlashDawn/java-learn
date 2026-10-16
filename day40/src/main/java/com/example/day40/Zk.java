package com.example.day40;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;

/**
 * Day40 公共连接工厂（对照笔记 §1.2 Session）。
 * 每个 Demo 通过 Zk.newClient() 拿到一个已启动的 Curator 连接，
 * 避免五个 Demo 各写一遍连接样板——单一职责。
 */
public final class Zk {

    /** day40-zk 容器映射的客户端端口 */
    public static final String CONNECT_STRING = "127.0.0.1:2181";

    private Zk() {
    }

    /**
     * 创建并启动一个 Curator 客户端。
     * sessionTimeoutMs = 10s：客户端超过这个时间没心跳，ZK 才判定会话死亡、
     * 删除它的所有临时节点——注意「TCP 断开」≠「Session 死亡」，重连快的话
     * 会话和临时节点都还在（SessionDemo 专门实证这一点）。
     */
    public static CuratorFramework newClient() {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_STRING)
                .sessionTimeoutMs(10_000)
                .connectionTimeoutMs(5_000)
                // 学习期重试一次就够；生产用 ExponentialBackoffRetry 防重试风暴
                .retryPolicy(new RetryOneTime(1_000))
                .build();
        client.start();
        return client;
    }
}
