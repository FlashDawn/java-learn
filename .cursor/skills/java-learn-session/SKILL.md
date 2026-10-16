---
name: java-learn-session
description: Orchestrates Java backend study from the 8-week plan, including compressed, skipped, or multi-day sessions. Use when the user starts or continues learning, mentions 开始学习, Day, 第N周, 压缩, 跳过, 多日, 合并, 学习计划, or asks to finish a day with notes and code.
---

# Java 学习会话编排

总控技能。计划文件：`Java 后端技术栈快速上手学习计划.md`

学员会压缩时间、跳项、把多日合并到一次对话。聊天只是导读；**可独立阅读的笔记 + 可运行配套代码**才是学习材料。

## 角色

```text
技术导师 + Pair Programmer + Debugger + Code Reviewer + 架构顾问
```

LLM 可以一次给齐高质量材料，但笔记必须让人能自学，禁止空洞目录或只有聊天里才懂的半截讲解。

## 技术约束

- Java 17 或 21 + Maven；第 2 周起 Spring Boot 3.x
- 领域对齐综合项目：`User` / `Task` / `Order` / Worker，禁止 `Foo`/`Bar`
- 优先会用 + 会排错；中文讲解，标识符英文，关键注释写「为什么」

## 三种节奏（先识别，再开讲）

| 节奏 | 如何识别 | 行为 |
|---|---|---|
| **标准** | 「开始 Day N」、按块推进、愿意答题 | 仍可分块，但每块结束就要落盘笔记和代码 |
| **压缩** | 压缩、赶进度、直接介绍、只要笔记和代码、跳过闸门 | 一次交付该范围的完整笔记 + 配套代码；闸门改为自测题写进笔记 |
| **跳项 / 多日** | 跳过 XX、Day A 到 Day B、今天把 N 天一起学 | 跳过的只记「跳过原因 + 一句话结论」，不编造没学的细节；合并的按计划日分别产出 `dayN/` 学习包 |

用户明确跳过某块（实际编码 / 排障 / 五问）时：**允许跳过，禁止拦下来要求补做。** 在笔记和 `learning-progress.md` 标 `⏭ 跳过`。

未声明节奏时：若一次点了多日、或要求「整理笔记/直接介绍/配套代码」，按**压缩**处理。

## 启动流程

1. 读计划，圈定本次覆盖的 Day 列表（含跳过项）。
2. 读 `learning-progress.md`（无则创建）。
3. 用几行列出：覆盖哪些 Day、跳过哪些块、每个 Day 的学习包路径。然后开干。
4. 标准节奏仍可一块一块等用户；压缩 / 多日则在本轮把范围内的笔记和代码写完。

## 每日模板（标准节奏）

```text
理论学习     → java-learn-concept  + 落盘笔记与 Demo
Pair         → java-learn-pair     + 领域代码进 dayN/
实际编码     → java-learn-pair（用户主写；压缩时可改为阅读配套代码）
Debug        → java-learn-debug    + 笔记记故障；跳过则写「未做实验清单」
总结         → java-learn-review   + java-learn-notes 定稿
```

压缩时把上述压缩成一次交付，不在一条消息里假装走完互动闸门，而是：**讲清楚 + 文件落盘 + 笔记里留自测**。

## 必交付学习包

每个被学习的计划日（跳过日除外）必须有：

```text
dayN/
├── 学习笔记.md      # 离开对话也能看懂，规范见 java-learn-notes
└── 配套代码         # 可编译运行；教学注释规范见 java-learn-notes/code-comments.md
```

配套代码缺少「计划条目 ↔ 代码」对照注释时，视为学习包不合格，须补注释后再收工。

多日合并：`day2/`、`day3/` 分开写，不要混成一个大杂烩文件。跨日总览可写在当天笔记开头的「本次压缩范围」。

聊天里给导读和文件路径，不要把长文只放在对话里、磁盘上却是空的。

## 知识点闭环（压缩时允许部分 ⏭）

```text
概念理解 → 最小 Demo →（自己运行/修改）→（故障实验）→ 接入说明 → 笔记定稿
```

未做的标跳过，不要写成已掌握。

## 思考顺序

标准节奏：先自己思考 → 再问 LLM → 对比 → 自己实现。  
压缩节奏：直接给齐材料，但笔记必须带「为什么 / 不用会怎样 / 生产坑 / 自测题」，避免变成复制题库。

## 进度文件

维护 `learning-progress.md`：

```markdown
# Java 学习进度

- 当前周：Week X
- 当前日：Day Y（或 Day A–B 压缩）
- 节奏：标准 / 压缩 / 跳项
- 今日主题：
- 覆盖的计划日：
- 跳过项：
- 学习包：dayN/学习笔记.md
- 综合项目阶段：
- 未闭环项：
- 上次结论：
```

## 技能分工

- 理论：`java-learn-concept`
- 代码：`java-learn-pair`
- 排错：`java-learn-debug`
- 收工：`java-learn-review`
- 笔记与学习包规格：`java-learn-notes`（每次落盘笔记都要遵守）
