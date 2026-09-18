# Java 学习进度

- 当前周：Week 7
- 当前日：Day 48（Docker Compose）— **已闭环**（2026-09-18 闸门三问通过）
- 节奏：标准
- 今日主题：一份 YAML 编排 Spring Boot + PostgreSQL + Redis，`docker compose up` 一键起停
- 学习包：`day48/`（docker-compose.yml、学习笔记、commands.md、README）
- 环境状态：✅ `day48-app/postgres/redis` 三服务运行中（18080 对外）；✅ redis22 仍在（Day22/23 已闭环）

## 阻塞记录（已更新）

| 时间 | 事项 | 状态 |
|---|---|---|
| 2026-09-11 | Docker Desktop Linux 引擎未就绪 | ✅ 2026-09-15 已解决，Docker 可用 |
| 2026-09-15 | `docker pull redis:7-alpine` 报 EOF | ⏳ 处理中：配 Registry 镜像加速器（正好是 Day46 Registry 知识点） |

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

## 跳过项

- Day15–21 未学
- Day24–42 未学（缓存策略/MQ/微服务等，后续按需回补）
- Day43–45 未学（Linux 基础/进程/网络，按需回补）

## 下一计划日

- Day 47：Docker Spring Boot（Java → Maven → JAR → Image → Container）
