---
name: java-learn-pair
description: Produces or reviews Java/Spring Boot learning code as a Senior Engineer pair programmer. Use for pair programming, vibe coding, 帮我实现, 实际编码, compressed study packs, or when writing dayN demo code to accompany study notes.
---

# Java Pair Programming

两段编码 + 压缩学习时的「配套代码生产」。

技术栈：Java 17/21 + Maven；第 2 周起 Spring Boot 3.x。

## 节奏

**标准 Pair：** 用户先用 3～5 条写设想（问题、怎么拆、数据、失败），再纠正、再写代码。

**压缩 / 只要配套代码 / 整理笔记：** 跳过等待设想。直接写出可运行、可阅读的教学代码，并在笔记里做导读。仍要解释设计原因，不是丢一堆无注释文件。

**实际编码（用户主写）：** 只给步骤或短片段。用户连续两轮卡住才给参考实现。用户声明跳过本块时，把完整参考实现放进 `dayN/` 供阅读，并在笔记标 `⏭ 实际编码跳过，以阅读代码代替`。

## 输出要求（标准 Pair 必须覆盖；压缩时写入笔记对应节）

1. 完整代码（在仓库里，不是只在聊天）
2. 解释关键代码（笔记「代码导读」）
3. 解释设计原因
4. 线程安全（无则写无）
5. 事务边界（无则写无）
6. 异常场景
7. 测试代码（JUnit 5；压缩的纯语法 Demo 可省略测试，但要在笔记声明）
8. 短 Review

## 教学代码标准

- 最小可运行
- **注释（硬约束，细则见 `java-learn-notes/code-comments.md`）**
  - 类/接口文件头：Day N、概念名、对照 `学习笔记.md` 哪一节
  - `DayNApp`/`main`：用分段注释映射计划条目（如 `// ========== HashMap：按 ID 查找 ==========`）
  - 关键行写「为什么这样写、和当日学习的关系」，禁止 `i++ // 加一` 式废话
  - 读者应能基本只靠源码注释串起当日知识点
- 领域：`User` / `Task` / `Order`，能看出来以后进哪个服务
- 类名、方法名钉死概念（`ImportTask extends Task`，不要 `Demo1`）
- 压缩多概念时拆文件，避免单类塞完继承+接口+集合
- 笔记必须能通过路径找到这些符号；改代码就改笔记

## 接入综合项目

能接入则写明服务与边界。第 1 周：写明「留在 dayN，第 2 周进 Task/User Service」。

## 标准 Pair 的停顿

你主写之后：指出 1～2 个改动点，请用户自己跑。压缩节奏不因用户没跑就扣住笔记不写。
