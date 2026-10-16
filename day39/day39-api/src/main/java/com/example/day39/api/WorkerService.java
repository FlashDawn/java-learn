package com.example.day39.api;

/**
 * Worker 服务的 RPC 契约（对照笔记 §1.1 Provider/Consumer）。
 * Provider 实现它、Consumer 引用它——双方只共享这个接口 jar，
 * 实现细节（Worker 怎么执行）Consumer 完全不知道，也不需要知道。
 */
public interface WorkerService {

    /** 执行一个任务，返回执行结果描述 */
    String execute(TaskJob job);
}
