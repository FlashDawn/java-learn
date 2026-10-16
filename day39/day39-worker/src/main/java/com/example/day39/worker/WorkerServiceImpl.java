package com.example.day39.worker;

import com.example.day39.api.TaskJob;
import com.example.day39.api.WorkerService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * Provider 实现（对照笔记 §1.1）。
 * @DubboService 做两件事：① 把本类发布为 RPC 服务（20880 端口监听）；
 * ② 向 Nacos 注册「com.example.day39.api.WorkerService → 我的地址」。
 */
@DubboService
public class WorkerServiceImpl implements WorkerService {

    @Override
    public String execute(TaskJob job) {
        System.out.println("[Worker] RPC 收到任务: id=" + job.getTaskId() + ", title=" + job.getTitle());
        // 模拟真实执行（发邮件/跑报表/调算法）
        return "worker 已完成任务 " + job.getTaskId() + ": " + job.getTitle();
    }
}
