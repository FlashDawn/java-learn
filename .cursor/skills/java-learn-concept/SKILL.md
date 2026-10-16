---
name: java-learn-concept
description: Teaches Java backend topics using the 7-section lesson and writes them into dayN notes plus runnable demos. Use for 理论学习, 概念, 原理, 为什么需要, 直接介绍, compressed or multi-day concept dumps of Java, Spring Boot, Redis, MQ, JVM, or related tech.
---

# Java 概念讲解

用于理论学习，以及压缩/多日时的集中讲解。计划第二章 7 节模板仍是每个主题的骨架。讲解完成后必须按 `java-learn-notes` 落盘，不能只留在对话里。

## 7 节骨架（每个主题都要有，可按节奏伸缩）

1. 概念
2. 为什么需要它
3. 工作原理
4. 最小 Java Demo（必须写成仓库文件）
5. Spring Boot 中的实际使用（第 1 周只记结论；第 2 周起给最小用法）
6. 常见错误
7. 生产环境注意事项

## 范围

- **标准：** 一次主讲一个主题；其余条目一两句挂到「结对/后文」。
- **压缩 / 用户要求直接介绍：** 可以把**同一计划日**的全部条目讲完，但每个条目仍走 7 节的压缩版（是什么 / 为什么 / 怎么做 / 代码锚点 / 坑），禁止一天十条只剩名词表。
- **多日：** 按 Day 切章，分别写入 `dayN/学习笔记.md`，不要把集合和 OOP 揉成一节。

不要一次引入计划日以外的中间件。需要背景时标明「先记结论」。

## 写法

- 中文；专有名词英文。优先会用 + 会排错。
- 第 4 节 Demo 必须可运行，领域用 `User`/`Task`/`Order`。
- 压缩时 Demo 可以多个小类，但每个类职责单一，笔记里有文件索引。
- 第 6、7 节每主题至少 2 条坑，各带「为什么会踩」。

## 落盘（相对只在聊天里贴代码，优先这个）

1. 把 Demo 写到 `dayN/`（或当天 Maven 工程）；注释按 `java-learn-notes/code-comments.md`（文件头 + 分段 + 关键旁注）。
2. 更新 `dayN/学习笔记.md`：计划条目表 + 每概念小节 + 如何运行 + 代码导读（符号与源码注释一致）。
3. 对话只给导读、路径、自测题。

## 闸门

标准节奏：理论结束仍出 3 问，用户先答再结对。

压缩 / 直接介绍 / 用户跳过问答：不要拦住。把 3 问写入笔记「自测」并附参考答案。

三问口径：

1. 用自己的话定义
2. 为什么需要？不用会怎样？
3. Demo 里最关键的一行在做什么？
