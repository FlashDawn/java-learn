package com.example.day29;

/**
 * Day29 · Message（对照笔记 §1.4）。
 * 综合项目里对应「任务状态变更」这条事件，而不是 Task 实体本身。
 *
 * @param id      消息自己的身份，用来排查「这条事件有没有被消费」
 * @param taskId  载荷：指向哪张任务
 * @param event   载荷：发生了什么，例如 CREATED
 */
public record Message(String id, long taskId, String event) {
}
