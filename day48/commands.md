# Day48 实操命令手册（本机已验证 2026-09-18）

## 1. 一键起停

```powershell
cd day48
docker compose up -d        # 起全部服务（幂等；--build 可强制重建镜像）
docker compose ps           # 看状态（健康检查会显示 healthy）
docker compose logs -f app  # 跟踪某服务日志
docker compose down         # 停并删容器+网络，保留卷
docker compose down -v      # ⚠️ 连数据卷一起删
```

## 2. 当日验证命令

```powershell
# 应用（宿主机唯一对外口）
[Console]::OutputEncoding = [Text.Encoding]::UTF8
curl.exe -s http://127.0.0.1:18080/users

# 环境变量注入证据：日志里应用名已变成 day14-api-compose
docker compose logs app | Select-String "day14-api-compose"

# PostgreSQL 读写（中文）
docker exec day48-postgres psql -U day14 -c "CREATE TABLE demo_note(id serial primary key, txt text); INSERT INTO demo_note(txt) VALUES('compose 持久化验证'); SELECT * FROM demo_note;"

# Redis
docker exec day48-redis redis-cli PING

# 内部 DNS：app 容器内可按服务名解析 postgres
docker exec day48-app sh -c "wget -q -O- http://day48-postgres:5432 || echo OK-DNS"

# 持久化：down 后 up，数据仍在
docker compose down; docker compose up -d
docker exec day48-postgres psql -U day14 -c "SELECT * FROM demo_note;"
```

## 3. 与散养容器的关系

| 容器 | 用途 | 宿主机端口 |
|---|---|---|
| `redis22` | Day22/23 原生/Spring Data 演示 | 6379 |
| `day48-redis` | compose 内部服务 | 不发布（仅内部网络） |
| `day48-app` | compose 应用 | 18080 |

两套互不干扰；`docker compose` 只管理本项目（`day48_*`）资源。
