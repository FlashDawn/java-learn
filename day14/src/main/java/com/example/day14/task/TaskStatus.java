package com.example.day14.task;

/**
 * Day14：任务状态枚举（状态机雏形）。对照笔记：§1.1。
 */
public enum TaskStatus {
    CREATED,
    RUNNING,
    SUCCESS,
    FAILED
}
