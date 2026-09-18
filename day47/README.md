# Day47：Docker Spring Boot

## 目标

把 Spring Boot 项目（day14）容器化，走通完整链路：

```text
Java → Maven → JAR → Docker Image → Container
```

## 产物位置

| 产物 | 位置 |
|---|---|
| Dockerfile | `day14/Dockerfile`（随项目，业界惯例） |
| .dockerignore | `day14/.dockerignore` |
| 镜像 | `day14-api:1.0` |
| 容器 | `day14-app`（`-p 18080:8080`） |
| 笔记 / 命令手册 | `day47/学习笔记.md`、`day47/commands.md` |

## 一分钟跑通

```powershell
mvn -q -f day14/pom.xml -DskipTests package
cd day14; docker build -t day14-api:1.0 .
docker run -d --name day14-app -p 18080:8080 day14-api:1.0
curl.exe -s http://127.0.0.1:18080/users
```

## 关键认知

1. Maven 管「源码 → jar」，Docker 管「jar → 镜像 → 运行」，职责分界清晰；
2. 容器内应用永远听 8080；宿主机映射端口是部署细节（本机 8080 在 Windows 保留段，故用 18080）；
3. 非 root 运行 + JRE 基底 + `.dockerignore` 是三个随手就该做的基线。

## 下一日预告

Day 48 Docker Compose：一次编排 Spring Boot + PostgreSQL + Redis，届时 `redis22` 将并入 compose 网络统一管理。
