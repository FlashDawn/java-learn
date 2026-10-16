package com.example.day40;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * Day40 Demo 1：ZNode 四种类型（对照笔记 §1.1）。
 *
 * ZK 的数据模型是一棵「类文件系统的树」，每个节点叫 ZNode：
 * 既能存少量数据（≤1MB），又能有子节点——目录与文件的合体。
 *
 * 本 Demo 依次创建四种 ZNode，观察命名与生命周期差异：
 * ① PERSISTENT             持久节点：显式 delete 才消失
 * ② EPHEMERAL              临时节点：创建它的 Session 死亡即自动消失 ← 服务发现/锁的基石
 * ③ PERSISTENT_SEQUENTIAL  持久顺序：名字自动追加 10 位单调递增序号
 * ④ EPHEMERAL_SEQUENTIAL   临时顺序：②+③ 合体 ← Leader 选举/公平锁的基石
 */
public class ZNodeDemo {

    public static void main(String[] args) throws Exception {
        try (CuratorFramework client = Zk.newClient()) {
            // 先清掉上次运行的残留（持久节点不会自己消失——这正是「持久」的含义）
            deleteQuietly(client, "/day40");

            // ============ ① 持久节点 ============
            String persistent = client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath("/day40/config", "maxRetry=3".getBytes());
            System.out.println("[1] 持久节点: " + persistent
                    + " 数据=" + new String(client.getData().forPath(persistent)));

            // ============ ② 临时节点 ============
            // 服务发现的写法：worker 实例上线就注册一个临时节点，内容放自己的地址
            String ephemeral = client.create()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath("/day40/worker-1", "172.30.48.1:20880".getBytes());
            System.out.println("[2] 临时节点: " + ephemeral
                    + " —— 本进程 Session 关闭后它会被 ZK 自动删除（SessionDemo 实证）");

            // ============ ③ 持久顺序节点 ============
            // 同一个路径前缀连建两次，ZK 自动追加 0000000000、0000000001……
            // 全局单调递增——「谁先谁后」这个分布式系统里极难回答的问题，ZK 用序号直接给出答案
            String seq1 = client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath("/day40/task-", "taskA".getBytes());
            String seq2 = client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath("/day40/task-", "taskB".getBytes());
            System.out.println("[3] 顺序节点: " + seq1 + " , " + seq2 + " —— 注意尾部序号");

            // ============ ④ 临时顺序节点 ============
            // 选举与公平锁的原材料：大家排队拿号（顺序），人走号销（临时）
            String es = client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath("/day40/lock-", "c1".getBytes());
            System.out.println("[4] 临时顺序节点: " + es + " —— 选举/锁 Demo 会大量使用它");

            // ============ stat：每个 ZNode 自带的元数据 ============
            // 版本号（乐观锁用）、创建/修改事务 id、子节点数等
            System.out.println("[5] /day40 的 stat: " + client.checkExists().forPath("/day40"));

            System.out.println("—— main 结束，客户端关闭，观察输出 [2]/[4] 的节点将在 Session 过期后消失");
        }
    }

    static void deleteQuietly(CuratorFramework client, String path) {
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception ignored) {
            // 节点不存在时删除会报错，清理场景直接忽略
        }
    }
}
