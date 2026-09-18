# Day23：Spring Data Redis 与 User/Task Cache

## 目标

- 掌握 `RedisTemplate` vs `StringRedisTemplate` 的区别与选用
- 用 JSON 序列化器配置类型安全的 Redis 模板
- 完成 `UserCache`、`TaskCache` 两个业务缓存组件
- 演示 String 结构计数器与简易锁

## 前置条件

本机 Redis 已启动（参考 Day22 README 的 Docker 方法）：

```bash
docker exec -it redis22 redis-cli PING
# 应返回 PONG
```

## 运行

```bash
cd day23
mvn spring-boot:run
```

## 代码结构

```
day23
├── pom.xml
├── src/main/resources/application.properties
└── src/main/java/com/example/day23
    ├── Day23Application.java
    ├── DemoRunner.java
    ├── config/RedisConfig.java
    ├── domain/User.java
    ├── domain/Task.java
    ├── cache/UserCache.java
    ├── cache/TaskCache.java
    └── string/StringValueDemo.java
```

## 常用命令

```bash
# 查看所有 Key
docker exec -it redis22 redis-cli KEYS "*"

# 查看 User JSON
docker exec -it redis22 redis-cli GET user:1001

# 查看 TTL
docker exec -it redis22 redis-cli TTL user:1001
```
