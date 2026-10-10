# Day 36：微服务基础

单工程双服务演示：单体拆成 user-service + task-service，RestTemplate 跨进程通信。

## 运行（两个终端）

```bash
cd day36
mvn compile

# 终端 1：user-service，18090
mvn spring-boot:run -Dspring-boot.run.main-class=com.example.day36.user.UserServiceApplication -Dspring-boot.run.arguments=--server.port=18090

# 终端 2：task-service，18091
mvn spring-boot:run -Dspring-boot.run.main-class=com.example.day36.task.TaskServiceApplication -Dspring-boot.run.arguments=--server.port=18091
```

## 验证

```bash
curl http://127.0.0.1:18090/users/1          # 直连 user-service
curl http://127.0.0.1:18091/tasks/1/detail   # task-service 聚合（内部跨进程调 user-service）
```

## 结构

```
src/main/java/com/example/day36/
├── user/  UserServiceApplication（18090）、UserController（用户契约）
└── task/  TaskServiceApplication（18091）、TaskController（聚合接口，RestTemplate 远程调用）
```

`TaskController.USER_SERVICE_URL` 的硬编码就是 Day37 Nacos 服务发现要消灭的痛点。
理论见 `学习笔记.md`。
