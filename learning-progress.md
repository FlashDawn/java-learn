# Java 学习进度

- 当前周：Week 6
- 当前日：Day 36（微服务基础）— **已闭环**（2026-10-10 闸门三问通过）
- 节奏：标准
- 今日主题：Monolith vs Microservice；拆分 / 通信 / 发现 / 配置中心 / 负载均衡
- 学习包：`day36/`（双服务工程、学习笔记、README）；无外部中间件
- 环境状态：✅ user-service 18090 + task-service 18091；跨进程聚合接口实测通过

## 阻塞记录（已更新）

| 时间 | 事项 | 状态 |
|---|---|---|
| 2026-09-11 | Docker Desktop Linux 引擎未就绪 | ✅ 2026-09-15 已解决，Docker 可用 |
| 2026-09-15 | `docker pull redis:7-alpine` 报 EOF | ✅ 已用 DaoCloud 加速器拉取并打标签 |

## 学习记录

### Week 1 重学（多线程/并发）

- **Day6**（2026-09-07）：标准节奏重学「多线程与并发」；学员跑通 unsafeCount ≠ 10000；闸门三问通过（第 3 问曾误判 `synchronized` 无可见性，当日补答订正）
- **Day7**（2026-09-08）：标准节奏重学「线程池」；闸门三问通过（第 2 问曾误判「无界队列无限建线程」，当日补答订正为「任务堆队列 → OOM」）

### Week 2 重学（Spring Boot 基础）

- **Day8**（2026-09-11）：重学「Spring IoC」；深挖 Bean 创建流程与字段注入坏味道；闸门三问通过
- **Day9**（2026-09-11）：重学「Spring AOP」；深挖代理原理、自调用失效；闸门三问标准答案已补记（学员未答，导师直给）
- **Day14**（2026-09-18）：重学「项目第一版」；深挖请求全链路（含「校验先于代理」时序点）与 DTO/事务边界/子资源动作三个设计决策；闸门三问通过（第 1 问框架层正确、应用层补全）——**Week 2 正式收官**

### Week 4 新学（Redis）— **已完全闭环**

- **Day22**（2026-09-10 开课 / 09-18 补跑成功）：Redis 基础理论 + 原生 Lettuce Demo；闸门三问通过；09-18 实跑输出六种结构全部正确，redis-cli 抽查 Hash/ZSet/Stream 数据无误
- **Day23**（2026-09-11 开课 / 09-18 补跑成功）：Spring Data Redis 理论 + 代码包；09-18 实跑全链路通过，redis-cli 复验 JSON 序列化、TTL 倒计时、锁过期证据齐全

### Week 7 新学（Docker）

- **Day46**（2026-09-15 开课 / 09-18 闭环）：Docker 基础。实操全通：Registry 加速修复（DaoCloud + tag）、自建 `day46-hello:1.0` 镜像构建运行、`day46-data` Volume 两容器共享读写、`day46-net` 按容器名 ping 通；连带解锁 `redis22` 容器（Day22/23 Demo 可补跑）。闸门三问通过（第 3 问差异表导师补齐，见笔记 §5）
- **Day47**（2026-09-18 开课并闭环）：Docker Spring Boot。day14 打 jar（26.1MB）→ `day14-api:1.0` 镜像 → `day14-app` 容器（18080→8080）全链路跑通；排障两则：8080 Windows 保留端口、PowerShell 中文显示假象（curl.exe 复验正确）；非 root 运行 / Java 17.0.20 / 182MB。闸门三问通过（第 2 问「映射方向」经纠正：映射是主动开门而非保护端口）
- **Day48**（2026-09-18 开课并闭环）：Docker Compose。三服务（app+postgres+redis）一键起；实证健康依赖排序、环境变量注入（应用名改名）、内部 DNS、PostgreSQL 中文读写、down→up 数据持久化；闸门三问通过（批语入笔记 §6）。RabbitMQ/Nacos ⏭ 预留

### Week 5 新学（消息队列）

- **Day29**（2026-09-21 开课并闭环）：消息队列基础。内存 `Broker` 对比同步调用与异步投递；Demo 已跑通。闸门三问通过（第 1 问划清寄存与 Day30 交换；第 2、3 问补了接口变慢和消息仍在调用方）。RabbitMQ 留到 Day30
- **Day30**（2026-09-21 开课 / 09-22 闭环）：RabbitMQ。容器 `day30-rabbit`；Direct / Fanout / Topic 路由演示已跑通。闸门三问通过（第 3 问曾误答 Topic，订正为 Direct 二次拉取为空）
- **Day31**（2026-09-22 开课并闭环）：RabbitMQ 可靠性。复用 `day30-rabbit`；ACK / NACK / Retry / DLQ / Prefetch 演示已跑通（task-2 重试成功、task-9 进死信）。闸门三问通过（第 1 问「Worker 崩了消息被删」订正为「Unacked 重投」；第 3 问 Prefetch 方向纠正）
- **Day33**（2026-09-22 开课并闭环）：RocketMQ 基础。容器 `day33-rocketmq`（NameServer 9876 / Broker 10911，需 `brokerIP1=127.0.0.1`）；Topic / Producer / Consumer / Consumer Group / Offset 概念已学，Demo 跑通（3 条消息收发成功）。排障一则：Broker 默认注册容器内网地址导致客户端连不上，写 `broker.conf` 重启解决。闸门三问通过（第 1 问补正「Exchange 也不存消息，纯路由」；第 3 问补充「Offset 未提交即宕机仍可能重复」幂等前提）。Retry / DLQ 待 Day34 深入
- **Day35**（2026-10-10 开课并闭环）：MQ 综合实战。`day35/` 工程落地完整链路：POST 落库(PENDING) → RabbitMQ → Worker 执行 → DONE；失败任务 3 次本地重试 → DLX → DLQ → DeadLetterListener 标 FAILED，全链路实测通过。幂等用 DONE 状态关口；消息只带 taskId 回库查。排障两则：curl 中文 query 400（改 --data-urlencode）、day48-postgres 无端口映射（新建 day35-postgres）。验收七问通过（第 2 题后半导师代答 Unacked→requeue；第 5 题补四种幂等实现；第 7 题补正「ACK 丢失→重发」根源与 at-least-once+业务幂等共识）——**Week 5 验收达成**（Day32/34 未学挂起）

### Week 4 新学（Redis）

- **Day28**（2026-09-23 开课 / 09-24 闭环）：Redis 综合实战 + 第四周验收。`day28/` 工程把 Cache-Aside 接入 User 服务：穿透（空值缓存）/ 击穿（互斥锁重建）/ 雪崩（TTL 抖动）/ 分布式锁（SET NX PX + Lua）Demo 四场景全跑通（DB 回源各 1 次），REST 在 18080 复验缓存命中。应学员要求补充 Redis Cluster（slot/Gossip/MOVED）与 Geode 集群（Locator/Region/PDX/WAN）理论对比。排障两则：Object 泛型模板 LinkedHashMap 强转异常（改类型化模板）、8080 残留 JVM 端口占用（清理 + 改 18080）。验收七问通过（第 1 题补三支柱、第 6 题补 RedLock 之争、第 7 题补延迟双删/Canal）——**Week 4 正式收官**

## 跳过项

- Day15–21 未学
- Day24–28 未学（缓存策略与 Redis 综合，按需回补）→ **Day28 已于 2026-09-23 回补**；Day24–27 仍未学
- Day32–42 未学（消息可靠性、RocketMQ、微服务等，按需回补）→ **Day33/35/36 已学**；Day32、34、37–42 仍未学

### Week 6 新学（微服务）

- **Day36**（2026-10-10 开课并闭环）：微服务基础。单工程双服务：user-service(18090) + task-service(18091)，RestTemplate 跨进程聚合实测通过（`/tasks/1/detail` 返回任务+owner）。理论覆盖单体 vs 微服务、按域拆分、同步/异步通信选型、服务发现（硬编码 URL 痛点实证）、配置中心、客户端/服务端 LB。闸门三问通过（第 3 问服务发现导师代答）
- Day43–45 未学（Linux 基础/进程/网络，按需回补）

## 下一计划日

- Day 32：消息可靠性（At-most-once / At-least-once / Exactly-once；幂等 / 去重 / 事务）
