package com.example.day35.mq;

/**
 * 任务事件消息（对照笔记 §2.2）。
 * 为什么只带 taskId 而不是整个 Task：消息是「通知」，不是「数据拷贝」——
 * Worker 拿到 id 回库查最新状态，天然拿到执行时刻的真实数据，
 * 也避免消息里的旧快照与 DB 不一致。title 仅用于日志展示。
 */
public record TaskMessage(Long taskId, String title) {
}
