# Day47 实操命令手册（本机已验证 2026-09-18）

> 环境：Windows 11 + Docker Desktop；项目 `day14`（H2 内存库，容器自包含）。

## 1. 链路总览

```powershell
# Java → JAR（仓库根目录）
mvn -q -f day14/pom.xml -DskipTests package

# JAR → Image（day14 目录，Dockerfile 所在处）
cd day14
docker build -t day14-api:1.0 .

# Image → Container
docker run -d --name day14-app -p 18080:8080 day14-api:1.0
```

## 2. 验证

```powershell
# 日志：Started Day14Application / Tomcat started on port 8080
docker logs day14-app --tail 5

# 用 curl.exe（PowerShell 5.1 显示 UTF-8 有坑）
[Console]::OutputEncoding = [Text.Encoding]::UTF8
curl.exe -s -X POST http://127.0.0.1:18080/users -H "Content-Type: application/json" -d "{\"name\":\"Docker用户\",\"email\":\"c@example.com\"}"
curl.exe -s http://127.0.0.1:18080/users/1

# 容器内部状态
docker exec day14-app whoami          # app（非 root）
docker exec day14-app java -version   # 17.0.20
docker stats day14-app --no-stream    # 内存 ~182MB
```

## 3. 常见运维

```powershell
docker stop day14-app; docker start day14-app
docker rm -f day14-app                # 删容器（H2 内存数据随之消失）
docker rmi day14-api:1.0              # 删镜像（需先删容器）
```

## 4. 当日坑位复现

```powershell
# 坑①：8080 在 Windows 保留段内 → bind 被拒
docker run -d --name x -p 8080:8080 day14-api:1.0   # 报 ports are not available
netsh interface ipv4 show excludedportrange protocol=tcp   # 查保留段
# 解法：-p 18080:8080（只换宿主机侧）

# 坑②：PowerShell 显示中文 ?? → 换 curl.exe 验证，确认服务端无问题
```
