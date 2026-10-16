# Day 40：ZooKeeper（ZNode / Session / Watcher / Leader / Follower / Quorum）

纯 Java + Curator 5.7.1，五个 Demo 各钉一个概念。ZK 3.9 跑在容器 `day40-zk`（2181）。

## 运行

```powershell
docker start day40-zk
mvn compile
# PowerShell 里 -D 参数必须加引号
mvn -q exec:java "-Dexec.mainClass=com.example.day40.ZNodeDemo"
mvn -q exec:java "-Dexec.mainClass=com.example.day40.SessionDemo"
mvn -q exec:java "-Dexec.mainClass=com.example.day40.WatcherDemo"
mvn -q exec:java "-Dexec.mainClass=com.example.day40.LeaderElectionDemo"
mvn -q exec:java "-Dexec.mainClass=com.example.day40.DistributedLockDemo"   # 锁回合约 1 分钟
```

## Demo ↔ 概念 ↔ 预期输出

| Demo | 钉死的概念 | 预期关键输出 |
|---|---|---|
| `ZNodeDemo` | ZNode 四类型 | `task-0000000002/03` 序号递增 |
| `SessionDemo` | Session 挂临时节点 | close 后实例列表 `[worker-1]` → `[]` |
| `WatcherDemo` | Watcher 一次性 / NodeCache | v2 收、v3 漏；v4、v5 收齐 |
| `LeaderElectionDemo` | Leader Election | 杀 worker-2 → worker-1 自动继位 |
| `DistributedLockDemo` | Distributed Lock | 无锁 67554/期望 50 万；ZK 锁 10000 精确 |

## 排障速查

| 症状 | 根因 | 修法 |
|---|---|---|
| `Unknown lifecycle phase ".mainClass=..."` | PowerShell 吞 `-D` 参数 | 参数整体加引号 |
| 无锁计数居然全对 | 裸 `++` 竞态窗口太小，假象 | 读-yield-写 + 加大剂量 |
| 连改两次只收一次通知 | Watcher 通知合并（语义如此） | 收到通知后重新 getData 读当前值 |

详见 `学习笔记.md`。
