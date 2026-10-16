# Day 38：Spring Cloud（Gateway / OpenFeign / Load Balancing / Circuit Breaker）

多模块 Maven 工程：Client → **Gateway(18099)** → Nacos 发现 → user-service(18100) / task-service(18101)；task-service 用 Feign 调 user-service，并带 Resilience4j 熔断降级。

## 模块

| 模块 | 端口 | 栈 | 职责 |
|---|---|---|---|
| `gateway-service` | 18099 | WebFlux | 统一入口，`lb://` 路由到后端服务 |
| `user-service` | 18100 | MVC | 用户查询 `GET /users/{id}` |
| `task-service` | 18101 | MVC | 任务聚合 `GET /tasks/{id}/detail`（Feign + 熔断） |

> 为什么多模块：Gateway 是 WebFlux（Netty），与 MVC（Tomcat）互斥，必须独占模块；这也是真实微服务的工程形态——每服务独立打包部署。

## 运行

前置：Nacos 在跑（`docker start day37-nacos`）。

```powershell
mvn clean install
# 三个终端各起一个（顺序不限，网关最后起体验更好）
mvn spring-boot:run -pl user-service
mvn spring-boot:run -pl task-service
mvn spring-boot:run -pl gateway-service
```

## 验证

```powershell
# 1. 网关路由到 user-service
curl http://127.0.0.1:18099/users/1

# 2. 网关 → task-service → Feign 调 user-service 聚合
curl http://127.0.0.1:18099/tasks/1/detail
# 期望：{"id":1,"title":"写周报","ownerId":1,"owner":{...},"degraded":false}

# 3. 熔断降级：停掉 user-service，再访问同一接口
curl http://127.0.0.1:18099/tasks/1/detail
# 期望：仍 200，owner 变为降级提示，"degraded":true
```

## 排障速查

| 症状 | 根因 | 修法 |
|---|---|---|
| `No Feign Client for loadBalancing defined` | 缺 LB starter | task-service 引 `spring-cloud-starter-loadbalancer` |
| 熔断注解不生效，下游挂了直接 500 | 缺 AOP starter，切面未织入 | task-service 引 `spring-boot-starter-aop` |

详见 `学习笔记.md`。
