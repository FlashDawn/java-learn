# Java 学习进度

- 当前周：Week 4
- 当前日：Day 23（Spring Data Redis）— **理论已讲，代码包已落盘并编译通过**
- 节奏：标准（当前）；Day6–Day9 已重学
- 今日主题：RedisTemplate vs StringRedisTemplate；Jackson 序列化配置；User Cache / Task Cache；计数器与简易锁
- 学习包：`day23/`（pom.xml、README、学习笔记、Day23Application、RedisConfig、UserCache、TaskCache、StringValueDemo、DemoRunner）
- 编译状态：✅ `mvn compile` 通过（已补 `jackson-databind` 依赖）
- 运行状态：⛔ **阻塞** — Docker Desktop Linux 引擎未就绪，Redis 容器无法启动

## 阻塞记录

| 时间 | 事项 | 阻塞原因 | 下一步动作 |
|---|---|---|---|
| 2026-09-11 | Day23 Demo 运行 | Docker Desktop 报 `500 Internal Server Error` on `dockerDesktopLinuxEngine/_ping`；WSL2 疑似无可用发行版 | 1. 重启 Docker Desktop；2. 检查 `wsl --list --verbose`；3. 必要时 `wsl --install` 后重启电脑；4. 或改用 Memurai/云 Redis |

## 本周学习记录（2026-09-07 起）

### Week 1 重学（多线程/并发）

- **Day6**（2026-09-07）：标准节奏重学「多线程与并发」；学员跑通 unsafeCount ≠ 10000；闸门三问通过（第 3 问曾误判 `synchronized` 无可见性，当日补答订正）
- **Day7**（2026-09-08）：标准节奏重学「线程池」；闸门三问通过（第 2 问曾误判「无界队列无限建线程」，当日补答订正为「任务堆队列 → OOM」）

### Week 2 重学（Spring Boot 基础）

- **Day8**（2026-09-11）：重学「Spring IoC」；深挖 Bean 创建流程与字段注入坏味道；闸门三问通过
- **Day9**（2026-09-11）：重学「Spring AOP」；深挖代理原理、自调用失效；闸门三问标准答案已补记（学员未答，导师直给）

### Week 4 新学（Redis）

- **Day22**（2026-09-10）：Redis 基础理论 + 原生 Lettuce Demo；闸门三问通过；环境启动要点已写入 `day22/学习笔记.md` §7
- **Day23**（2026-09-11）：Spring Data Redis 理论 + `day23/` 学习包落盘；`mvn compile` 通过；运行待 Redis 环境就绪

## 跳过项

- Day15–21 未学

## 下一计划日

- Day 24：缓存策略（穿透/击穿/雪崩）与写一致性（可在 Redis 环境就绪后连跑 Day23+Day24 Demo）
