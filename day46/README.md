# Day46：Docker 基础

## 目标

- 掌握 Image / Container / Dockerfile / Volume / Network / Registry 六个核心概念
- 本机真实跑通：拉镜像、跑容器、自建镜像、跨容器共享数据、容器间按名通信
- 修复 docker.io 直连 EOF 问题（Registry 加速器）

## 前置条件

Docker Desktop 已启动（任务栏鲸鱼图标稳定）。

## 快速验证

```powershell
docker version                                  # Server 正常即就绪
docker exec redis22 redis-cli PING              # 已有 Redis 容器 → PONG
```

## 本日实操

见 `commands.md`，全部命令本机已验证通过（2026-09-15）。

## 目录结构

```
day46
├── README.md
├── 学习笔记.md
├── commands.md
└── docker-demo
    ├── Dockerfile      # FROM alpine + COPY + CMD
    └── hello.txt
```

## 连带成果

- `redis:7-alpine` 镜像已通过 DaoCloud 加速器拉取并打标签；
- `redis22` 容器运行中（`-p 6379:6379`），Day22 / Day23 的 Demo 随时可补跑：
  - Day22：`cd day22; mvn -q compile exec:java`
  - Day23：`cd day23; mvn spring-boot:run`
