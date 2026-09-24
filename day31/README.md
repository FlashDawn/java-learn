# Day31：RabbitMQ 可靠性

复用 Day30 的容器：

```powershell
docker start day30-rabbit
```

演示：

```powershell
cd d:\java_program\JAVA_learn\day31
mvn -q compile exec:java
```

预期：task-2 第一次失败重试后成功；task-9 两次失败后进死信队列。

管理界面：<http://127.0.0.1:15672>（`day30` / `day30pwd`），可看到 `day31.tasks` 和 `day31.dlq`。
