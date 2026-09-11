# Day 22：Redis 基础 — 启动说明

本机若还没有 Redis，**先起服务，再跑 Java**。Demo 需要 `127.0.0.1:6379`，且版本建议 **Redis 7**（至少 5+，因为用了 Stream）。

> 不要用 winget 里的 `Redis.Redis`（3.0.x）：太旧，**没有 Stream**，`Day22App` 会失败。

---

## 推荐：Docker Desktop + 官方 Redis 镜像

### 1. 安装 Docker Desktop

1. 打开：https://www.docker.com/products/docker-desktop/
2. 下载 Windows 版并安装（可能要求开启虚拟化 / WSL2，按安装向导走）。
3. 启动 **Docker Desktop**，等托盘图标显示 Running。

### 2. 启动 Redis 容器（PowerShell）

```powershell
docker run -d --name redis22 -p 6379:6379 redis:7-alpine
```

检查：

```powershell
docker ps
docker exec -it redis22 redis-cli PING
```

应返回 `PONG`。

### 3. 跑 Day22 Java Demo

```powershell
cd d:\java_program\JAVA_learn\day22
mvn -q -DskipTests compile exec:java
```

预期类似：

```text
string=CREATED
hash.name=Ada
list.pop=t-1
set.size=2
zset.top=[t-1]
stream.id=...
```

### 4. 用完可停掉（可选）

```powershell
docker stop redis22
# 以后再开：
docker start redis22
```

---

## 备选 A：WSL2 + Ubuntu 里装 Redis

若你更想用 Linux 工具链：

```powershell
wsl --install
```

重启后打开 Ubuntu：

```bash
sudo apt update
sudo apt install -y redis-server
sudo service redis-server start
redis-cli PING
```

Windows 里的 Java 连 `127.0.0.1:6379` 一般即可（WSL2 端口转发）。若连不上，在 WSL 里查 Redis 是否监听 `0.0.0.0:6379`。

---

## 备选 B：Memurai Developer（Windows 原生）

1. 打开 https://www.memurai.com/ 下载 Developer（Redis 兼容，适合本机学习）。
2. 安装并启动 Memurai 服务（默认也是 6379）。
3. 同样执行上面的 `mvn ... exec:java`。

---

## 排错

| 现象 | 处理 |
|---|---|
| `connectFailed=...` / Connection refused | Redis 没起来，或端口不是 6379 |
| Stream / XADD 报错 | Redis 版本过旧，换 `redis:7-alpine` |
| Docker 命令不存在 | 先装并启动 Docker Desktop |
| `docker run` 很慢 | 等镜像拉完；可再执行一次 `docker ps` |

Spring Data Redis 仍留到 **Day 23**；今天只要容器/`PING` 通 + Demo 六行输出即可。
