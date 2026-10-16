---
name: java-learn-review
description: Closes a Java study session with Code Review, study-pack finalization, and optional wrap-up questions. Use for 总结, 收工, 结束 Day, 跳过问答, 压缩收工, code review, or when merging notes after skipped blocks.
---

# Java 学习总结与 Code Review

收工时定稿学习包（`java-learn-notes`），并更新 `learning-progress.md`。

## 收工顺序

1. 对照本次新增代码做短 Review。
2. **五问：** 标准节奏让用户先答，再给对照。用户跳过问答 / 压缩收工：不要拦住；由你把五问写成「当日结论」写进笔记（标明是导师代写结论，不是学员作答）。
3. 按笔记模板检查 `dayN/学习笔记.md`：覆盖表、概念节、代码导读、运行方式、自测、跳过项。缺的补上。确认代码与笔记一致。
4. 更新进度：节奏、覆盖 Day、跳过项、学习包路径、未闭环项。
5. 预告下一个计划日标题（多日压缩后预告未覆盖的下一 Day）。

## 五问原文（交互时用户先答；压缩时写入笔记结论）

```text
1. 今天学了什么？
2. 为什么需要这个技术？
3. 它解决什么问题？
4. 如果不用它怎么办？
5. 它在生产环境有什么坑？
```

## Code Review

- 必须改 / 建议改 / 可延后 / 优点（具体到符号）
- 覆盖正确性、线程、事务、异常、是否只停在 Demo
- 压缩且用户几乎没手写代码：Review 改为「阅读用参考实现的注意点」，不要指责闭环不完整（跳过已是约定）

## 输出（对话里短，细节在笔记）

```markdown
## 收工
- 节奏与跳过
- 学习包路径
- Review 三条以内
- 进度已更新
- 下一计划日
```

周验收仍用计划第十八章 Level 1～6，不用「看完了吗」。
