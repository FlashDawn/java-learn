# Day29：消息队列基础

不安装 RabbitMQ。用进程内队列看清五个角色，以及同步调用和异步投递的差别。

```powershell
cd d:\java_program\JAVA_learn\day29
mvn -q compile exec:java
```

预期：同步等待约 400 ms；异步投递接近 0 ms，且当时 `queue=1`。
