# Day 2 配套代码

在本目录执行（JDK 21）：

```powershell
javac *.java
java Day2App
```

预期输出：

```text
IMPORT CREATED
t-1 COMPLETED
order=o-1 amountFen=9900
retry=3
users=1
```

阅读顺序：`User` → `Order` → `Executable` / `Retryable` → `Task` → `ImportTask` → `Day2App`。讲解在 `学习笔记.md`。
