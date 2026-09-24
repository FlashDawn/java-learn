# Day30：RabbitMQ

容器：

```powershell
docker start day30-rabbit
```

首次创建（账号不是 guest，原因见笔记 §5）：

```powershell
docker run -d --name day30-rabbit -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=day30 -e RABBITMQ_DEFAULT_PASS=day30pwd rabbitmq:3.13-management-alpine
```

演示：

```powershell
cd d:\java_program\JAVA_learn\day30
mvn -q compile exec:java
```

管理界面：<http://127.0.0.1:15672>（`day30` / `day30pwd`）。
