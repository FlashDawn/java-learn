package com.example.day35.domain;

/**
 * 任务状态机（对照笔记 §1 链路各节点）：
 * PENDING  —— 已落库、消息已发，等待 Worker 领取
 * RUNNING  —— Worker 已消费，正在执行
 * DONE     —— 执行成功（幂等判断依据：重复消息看到 DONE 直接跳过）
 * FAILED   —— 重试耗尽进入 DLQ，由死信监听器标记
 */
public enum TaskStatus {
    PENDING,
    RUNNING,
    DONE,
    FAILED
}
