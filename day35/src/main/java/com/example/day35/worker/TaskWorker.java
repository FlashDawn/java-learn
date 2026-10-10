package com.example.day35.worker;

import com.example.day35.domain.Task;
import com.example.day35.domain.TaskRepository;
import com.example.day35.domain.TaskStatus;
import com.example.day35.mq.MqConfig;
import com.example.day35.mq.TaskMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 任务 Worker（对照笔记 §2.3）。
 * 演示三个 Week 5 核心概念：
 *   幂等  —— 重复消息看到 DONE 直接跳过，不重复执行
 *   Retry —— 执行抛异常，Spring AMQP 自动重试 3 次（见 application.yml）
 *   DLQ   —— 重试耗尽消息入死信队列，由死信监听标记 FAILED
 */
@Component
public class TaskWorker {

    private final TaskRepository repository;

    public TaskWorker(TaskRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = MqConfig.TASK_QUEUE)
    public void onTaskCreated(TaskMessage message) throws InterruptedException {
        Task task = repository.findById(message.taskId()).orElse(null);
        if (task == null) {
            // 消息对应的记录已不存在（脏消息），正常返回即 ACK 丢弃，不占重试
            System.out.println("[Worker] task " + message.taskId() + " 不存在，丢弃消息");
            return;
        }

        // 幂等关口：MQ 保证至少一次投递，重复消息必然出现；
        // 用业务状态判断「已处理过」是最常用的幂等实现
        if (task.getStatus() == TaskStatus.DONE) {
            System.out.println("[Worker] task " + task.getId() + " 已 DONE，重复消息跳过（幂等）");
            return;
        }

        // 故障注入：标题含 fail 的任务用于演示 Retry → DLQ 链路
        if (task.getTitle().contains("fail")) {
            System.out.println("[Worker] task " + task.getId() + " 执行失败，抛出异常触发重试");
            throw new RuntimeException("模拟执行失败: task " + task.getId());
        }

        task.setStatus(TaskStatus.RUNNING);
        repository.save(task);
        System.out.println("[Worker] task " + task.getId() + " RUNNING，开始执行: " + task.getTitle());

        Thread.sleep(1500); // 模拟真实耗时操作（发邮件/生成报表/调第三方）

        task.setStatus(TaskStatus.DONE);
        task.setFinishedAt(LocalDateTime.now());
        repository.save(task);
        System.out.println("[Worker] task " + task.getId() + " DONE");
    }
}
