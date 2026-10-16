# Day 39：Dubbo（Provider / Consumer / Registry / RPC / Serialization / Load Balance）

多模块 Maven 工程：`task-service`(Consumer, Web 18110) → **Dubbo RPC** → `worker-service`(Provider, dubbo 协议 20880)；注册中心复用 Nacos（`day37-nacos`）。

## 模块

| 模块 | 端口 | 职责 |
|---|---|---|
| `day39-api` | — | **契约 jar**：`WorkerService` 接口 + `TaskJob` DTO（`implements Serializable`） |
| `day39-worker` | 20880（Dubbo） | Provider：`@DubboService` 实现，纯 RPC 进程（`web-application-type: none`） |
| `day39-task` | 18110（Web） | Consumer：`@DubboReference` 注入代理，`GET /tasks/dispatch` |

> api 模块是 Dubbo 的标志性结构：Provider 与 Consumer 只共享契约，不共享实现。

## 运行

前置：Nacos 在跑（`docker start day37-nacos`）。

```powershell
mvn clean install
# 终端一：Provider
mvn spring-boot:run -pl day39-worker
# 终端二：Consumer
mvn spring-boot:run -pl day39-task
```

## 验证

```powershell
curl --data-urlencode "title=生成月度报表" -G http://127.0.0.1:18110/tasks/dispatch
# 期望返回：[task-service] 已派发，RPC 返回: worker 已完成任务 1: 生成月度报表
# 且 worker 终端打印：[Worker] RPC 收到任务: id=1, title=生成月度报表（task 终端不打印——执行在另一个进程）
```

## 排障速查

| 症状 | 根因 | 修法 |
|---|---|---|
| 编译报「程序包 org.springframework.boot 不存在」 | `dubbo-spring-boot-starter` 不传递基础 starter | 显式引 `spring-boot-starter` |
| 启动报 `NoSuchMethodError: SpringFactoriesLoader.forDefaultResourceLocation` | dubbo-bom 把 spring 压到 5.3.x | 父 pom 显式 import `spring-framework-bom:6.1.14` 并放在 dubbo-bom 之前，`mvn dependency:tree` 复验 |
| RPC 500：`Fail to decode request ... parameterTypes=null`，重试 3 次 | DTO 用了 `record`，Hessian2 反序列化需要无参构造 | DTO 改经典 JavaBean（无参构造 + getter/setter + Serializable） |

详见 `学习笔记.md`。
