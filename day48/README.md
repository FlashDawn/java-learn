# Day48：Docker Compose

## 目标

一份 YAML 编排多容器应用，一条命令整体起停：

```powershell
cd day48
docker compose up -d
```

## 本日拓扑

```text
        ┌──────────── day48_backend（自建桥接网络）────────────┐
        │                                                      │
 宿主机 18080 ──→ day48-app（day14-api:1.0，Spring Boot）        │
        │           │ depends_on（service_healthy）            │
        │           ↓                    ↓                     │
        │     day48-postgres:5432   day48-redis:6379           │
        │     （仅内部，不发布端口）  （仅内部，不发布端口）       │
        │           ↓                    ↓                     │
        │      volume:pgdata        volume:redisdata           │
        └──────────────────────────────────────────────────────┘
```

## 关键收获

1. **声明式编排**：三条 `docker run` 的参数收敛进一个 YAML；
2. **健康依赖**：`depends_on + service_healthy` 解决「应用比 DB 起得快」的经典事故；
3. **配置外置**：`environment` 覆盖 `application.yml`，同一镜像处处运行；
4. **数据卷独立生命周期**：`down` 保留卷，`down -v` 才删数据；
5. **内部服务不发布端口**：暴露面最小化，也避开宿主机端口冲突。

## 文件

- `docker-compose.yml` —— 编排文件（逐行教学注释）
- `学习笔记.md` —— 完整笔记
- `commands.md` —— 实操命令手册

## 预留

RabbitMQ / Nacos 待 Day29–42 回补后按 `services:` 追加即可（笔记 §5 给了 RabbitMQ 示例块）。
