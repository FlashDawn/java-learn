package com.example.day35.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息发布者（对照笔记 §2.2）。
 * Controller 只负责「落库 + 发消息」两件小事，立刻返回——
 * 耗时执行被异步甩给 Worker，这就是削峰与解耦的落点。
 */
@Component
public class TaskEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public TaskEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishTaskCreated(Long taskId, String title) {
        rabbitTemplate.convertAndSend(
                MqConfig.EXCHANGE, MqConfig.ROUTING_KEY, new TaskMessage(taskId, title));
    }
}
