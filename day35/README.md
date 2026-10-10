# Day 35：MQ 综合实战

创建任务 → PostgreSQL → RabbitMQ → Worker 执行 → 更新状态。

## 前置

```bash
docker start day35-postgres day30-rabbit
# PG: 127.0.0.1:5432 day14/day14pwd db=day14
# RabbitMQ: 127.0.0.1:5672 day30/day30pwd（管理台 15672）
```

## 运行

```bash
cd day35
mvn compile spring-boot:run    # 端口 18082
```

## 验证

```bash
# 正常任务：PENDING → 约1.6s后 DONE
curl -X POST http://127.0.0.1:18082/tasks --data-urlencode "title=写周报"
curl http://127.0.0.1:18082/tasks/1

# 失败任务：重试3次 → DLQ → FAILED
curl -X POST http://127.0.0.1:18082/tasks --data-urlencode "title=fail-数据备份"
curl http://127.0.0.1:18082/tasks/2
```

## 结构

```
src/main/java/com/example/day35/
├── Day35Application.java
├── domain/    Task（JPA 实体）、TaskStatus（状态机）、TaskRepository
├── mq/        MqConfig（Exchange/Queue/DLQ 声明）、TaskMessage、TaskEventPublisher
├── worker/    TaskWorker（消费+幂等+故障注入）、DeadLetterListener（DLQ 兜底）
└── web/       TaskController（POST 落库+发消息，GET 查状态）
```

理论与验收问答见 `学习笔记.md`。
