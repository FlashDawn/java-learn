# Day14 Spring Boot 项目第一版

独立 Maven 工程：`com.example.day14`。User + Task CRUD，整合 REST / 校验 / 全局异常 / 日志切面 / 事务。

## 运行

```bash
mvn -q -f day14/pom.xml spring-boot:run
```

默认端口 `8080`。

## 快速试调

```bash
curl -s -X POST http://localhost:8080/users -H "Content-Type: application/json" -d "{\"name\":\"Alice\",\"email\":\"a@example.com\"}"
curl -s http://localhost:8080/users
curl -s -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d "{\"userId\":1,\"title\":\"import-csv\"}"
curl -s -X PUT http://localhost:8080/tasks/1/status -H "Content-Type: application/json" -d "{\"status\":\"RUNNING\"}"
```

## 打包验证

```bash
mvn -q -f day14/pom.xml -DskipTests package
```
