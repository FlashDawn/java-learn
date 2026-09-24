# Day 28：Redis 综合实战

把 Redis 正式接入 User/Task 综合项目：Cache-Aside 读写、穿透/击穿/雪崩三防护、分布式锁。

## 前置

```bash
docker start redis22   # Redis 7 在 6379
```

## 运行

```bash
cd day28
mvn compile spring-boot:run
```

启动后 `Day28DemoRunner` 自动跑四个场景（Cache-Aside / 穿透 / 击穿 / 分布式锁），
然后 Web 留在 18080 端口可手动验证：

```bash
curl http://127.0.0.1:18080/users/1          # 走缓存
curl http://127.0.0.1:18080/users/db-hits    # 看回源计数
curl -X PUT "http://127.0.0.1:18080/users/1?name=王五"   # 写 DB + 删缓存
```

## 结构

```
src/main/java/com/example/day28/
├── Day28Application.java      # 入口
├── Day28DemoRunner.java       # 启动自动演示四场景
├── domain/User.java           # 被缓存的领域对象
├── store/InMemoryUserDb.java  # 模拟 DB（100ms 慢查询 + 回源计数）
├── cache/UserCacheService.java# Cache-Aside + 三防护（核心）
├── lock/RedisLock.java        # SET NX PX + Lua 解锁
├── config/RedisConfig.java    # JSON 序列化 + 类型化模板
└── web/UserController.java    # REST 验证入口
```

理论（含 Redis 集群 / Geode 集群补充）见 `学习笔记.md`。
