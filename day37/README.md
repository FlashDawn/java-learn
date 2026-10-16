# Day 37：Nacos

三服务（user / task / data）注册到 Nacos；服务名寻址替代硬编码；配置中心热更新。

## 前置

```bash
docker start day37-nacos   # Nacos 2.3.2 standalone，控制台 http://127.0.0.1:8848/nacos
```

## 运行（三个终端）

```bash
cd day37
mvn compile

mvn spring-boot:run -Dspring-boot.run.main-class=com.example.day37.user.UserServiceApplication -Dspring-boot.run.arguments=--spring.profiles.active=user
mvn spring-boot:run -Dspring-boot.run.main-class=com.example.day37.task.TaskServiceApplication -Dspring-boot.run.arguments=--spring.profiles.active=task
mvn spring-boot:run -Dspring-boot.run.main-class=com.example.day37.data.DataServiceApplication -Dspring-boot.run.arguments=--spring.profiles.active=data
```

## 验证

```bash
# 注册表出现三个服务
curl "http://127.0.0.1:8848/nacos/v1/ns/service/list?pageNo=1&pageSize=20"

# 服务名寻址（无硬编码 IP）
curl http://127.0.0.1:18091/tasks/1/detail

# 配置热更新（不重启生效）
curl http://127.0.0.1:18092/data/message
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs" \
  --data-urlencode "dataId=data-service.yaml" \
  --data-urlencode "group=DEFAULT_GROUP" \
  --data-urlencode "content=app.message: 来自Nacos的新配置"
curl http://127.0.0.1:18092/data/message
```

## 结构

```
src/main/java/com/example/day37/
├── user/  UserServiceApplication（18090，注册到 Nacos）、UserController
├── task/  TaskServiceApplication（18091，@LoadBalanced RestTemplate 按服务名调用）、TaskController
└── data/  DataServiceApplication（18092）、DataController（@RefreshScope 配置热更新）
resources/ application.yml（共用 Nacos 地址）+ application-{user,task,data}.yml（按 profile 区分）
```

理论见 `学习笔记.md`。
