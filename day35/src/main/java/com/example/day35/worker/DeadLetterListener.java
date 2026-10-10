package com.example.day35.worker;

import com.example.day35.domain.TaskRepository;
import com.example.day35.domain.TaskStatus;
import com.example.day35.mq.MqConfig;
import com.example.day35.mq.TaskMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 死信监听器（对照笔记 §2.4）。
 * DLQ 不是垃圾场，是「失败的最终归宿」：这里把任务标记 FAILED 并留下记录，
 * 生产上还应接告警（钉钉/邮件）通知人工介入。
 */
@Component
public class DeadLetterListener {

    private final TaskRepository repository;

    public DeadLetterListener(TaskRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = MqConfig.DLQ)
    public void onDeadLetter(TaskMessage message) {
        repository.findById(message.taskId()).ifPresent(task -> {
            task.setStatus(TaskStatus.FAILED);
            task.setFinishedAt(LocalDateTime.now());
            repository.save(task);
            System.out.println("[DLQ] task " + task.getId() + " 重试耗尽，标记 FAILED: " + task.getTitle());
        });
    }
}
