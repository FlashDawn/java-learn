package com.example.day40;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Day40 Demo 4：Leader Election（对照笔记 §1.4）。
 *
 * 场景：3 个 task-worker 实例，同一时刻只能有一个当 Leader 负责调度
 * （比如只有 Leader 能扫描待办任务并派发——避免 3 个实例重复派发）。
 *
 * 原理（§1.4 的手动版）：每个实例在 /day40/election 下建 EPHEMERAL_SEQUENTIAL 节点，
 * 序号最小者当选；其余实例各自 Watch「自己前一个序号的节点」——
 * 前任死亡才轮到自己继位，而不是所有人一起扑上去（防羊群效应）。
 *
 * 本 Demo 用 Curator 的 LeaderLatch（上述原理的工业封装）：
 * 三实例同时抢 → 一个当选；主线程随后「杀掉」Leader（close 其客户端 = Session 死亡），
 * 观察剩余实例自动继位——故障转移无需人工介入。
 */
public class LeaderElectionDemo {

    public static void main(String[] args) throws Exception {
        ZNodeDemo.deleteQuietly(Zk.newClient(), "/day40");
        CountDownLatch firstLeaderElected = new CountDownLatch(1);

        List<CuratorFramework> clients = new ArrayList<>();
        List<LeaderLatch> latches = new ArrayList<>();

        // ============ 3 个实例同时上线抢 Leader ============
        for (int i = 1; i <= 3; i++) {
            String name = "worker-" + i;
            CuratorFramework client = Zk.newClient();
            LeaderLatch latch = new LeaderLatch(client, "/day40/election", name);
            latch.addListener(new LeaderLatchListener() {
                @Override
                public void isLeader() {
                    System.out.println(">>> " + name + " 当选 Leader，开始负责派发任务");
                    firstLeaderElected.countDown();
                }

                @Override
                public void notLeader() {
                    System.out.println("<<< " + name + " 失去 Leader 身份（Session 异常时回调）");
                }
            });
            latch.start();
            clients.add(client);
            latches.add(latch);
        }

        firstLeaderElected.await(5, TimeUnit.SECONDS);
        Thread.sleep(500); // 等选举尘埃落定

        // ============ 找到当前 Leader 并「杀死」它 ============
        // close 客户端 = Session 死亡 = 临时节点被删 = 触发重新选举
        for (int i = 0; i < latches.size(); i++) {
            if (latches.get(i).hasLeadership()) {
                System.out.println("—— 模拟故障：杀死 Leader 实例 "
                        + latches.get(i).getId() + "（关闭其 Session）");
                latches.get(i).close();
                clients.get(i).close();
                break;
            }
        }

        // 剩余两个实例应自动完成权力交接
        Thread.sleep(3_000);
        System.out.println("—— 观察上方输出：新 Leader 已自动继位，无需人工介入");

        // 清理存活的实例
        for (int i = 0; i < latches.size(); i++) {
            try {
                latches.get(i).close();
                clients.get(i).close();
            } catch (Exception ignored) {
            }
        }
    }
}
