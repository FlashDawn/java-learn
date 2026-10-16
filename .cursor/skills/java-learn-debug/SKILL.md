---
name: java-learn-debug
description: Debugs Java/Spring Boot issues with layered analysis first, and documents fault experiments in study notes. Use for 排查, 根因, Debug, 故障实验, 故意制造 Bug, or when compressed study skips labs but still needs a fault checklist in dayN notes.
---

# Java Debug 与故障实验

用于真实报错、故障实验，以及压缩学习时把「未做实验」写进笔记。目录见 [fault-catalog.md](fault-catalog.md)。

## 真实排错

**不要直接给出修改后的代码。** 先：

1. 判断问题属于哪一层
2. 分析可能原因
3. 给出排查路径
4. 指出最可能的根因
5. 最后再给出修复方案

分层：客户端/网关 → 应用代码 → 框架配置 → 中间件 → JVM/OS → 容器/K8s。

排查路径必须可执行（命令、日志、配置项 + 每步预期）。只给一个最可能根因。用户跳过「先解释」且明确只要补丁时（压缩节奏）：给补丁，同时把根因写进 `dayN/学习笔记.md`，不要只在聊天里改完就算。

## 故障实验

每天最多 1 个（多日压缩：每日笔记各列 1 个建议实验，不要一次注入一串）。

标准：说明如何制造 → 用户做 → 分层分析 → 用户先解释 → 再修复。

用户跳过排障：允许。在笔记「故障清单」写：

- ⏭ 未做
- 建议的 1 个实验名称
- 预期现象
- 对应 `fault-catalog.md` 条目

不要把跳过写成「已掌握排错」。

## 禁止

- 真实排错首条就丢完整修好的类（压缩且用户只要补丁时除外，但必须带根因）
- 一次实验多种故障
- 不引用具体日志行就下结论
