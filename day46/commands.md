# Day46 实操命令手册（本机已验证）

> 环境：Windows 11 + Docker Desktop（Engine 29.7.2）。PowerShell 逐段执行。

## 0. Registry 加速（直连 docker.io 报 EOF 时）

```powershell
# 办法一：用加速器完整路径拉取，再打别名（本次采用）
docker pull docker.m.daocloud.io/library/redis:7-alpine
docker tag docker.m.daocloud.io/library/redis:7-alpine redis:7-alpine

# 办法二（推荐根治）：Docker Desktop → Settings → Docker Engine 加：
#   { "registry-mirrors": ["https://docker.m.daocloud.io"] }
# 应用重启后 docker pull redis:7-alpine 直连即走加速
```

## 1. Image

```powershell
docker images                                  # 看本地镜像（注意同 ID 双标签）
docker pull redis:7-alpine                     # 拉取
docker tag <源名> <新名>                        # 起别名，不复制数据
docker rmi <镜像>                               # 删除（需无容器引用）
```

## 2. Container

```powershell
docker run -d --name redis22 -p 6379:6379 redis:7-alpine   # 后台常驻
docker run --rm alpine:3.20 cat /etc/os-release            # 一次性任务，退出即删
docker ps / docker ps -a                                   # 运行中 / 全部
docker logs -f redis22                                     # 看日志
docker exec -it redis22 redis-cli PING                     # 进容器执行命令
docker stop redis22; docker start redis22; docker rm -f redis22
docker inspect redis22                                     # 全部配置详情
```

## 3. Dockerfile（day46/docker-demo）

```powershell
cd day46
docker build -t day46-hello:1.0 .\docker-demo
docker run --rm day46-hello:1.0
# 预期输出：Hello from Day46 Docker image! + 中文行
```

## 4. Volume

```powershell
docker volume create day46-data
docker run --rm -v day46-data:/data alpine:3.20 sh -c "echo 'written by container A' > /data/msg.txt"
docker run --rm -v day46-data:/data alpine:3.20 sh -c "cat /data/msg.txt"
# 预期输出：written by container A
docker volume rm day46-data                                # 清理
```

## 5. Network

```powershell
docker network create day46-net
docker run -d --name svc-a --network day46-net alpine:3.20 sleep 300
docker run --rm --network day46-net alpine:3.20 ping -c 2 svc-a
# 预期：PING svc-a (172.18.0.x) ... 0% packet loss
docker rm -f svc-a; docker network rm day46-net            # 清理
```
