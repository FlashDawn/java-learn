# Day 9 配套代码（Spring AOP）

```powershell
cd d:\java_program\JAVA_learn\day9
mvn -q -DskipTests spring-boot:run
```

另开终端：

```powershell
curl "http://localhost:8089/tasks?id=t-9"
curl "http://localhost:8089/tasks?id=t-9&fail=true"
```

预期控制台：`aop-request=...`、`aop-ok=... costMs=...`；失败时 `aop-error=...`。

仅打包：`mvn -q -f day9/pom.xml -DskipTests package`
