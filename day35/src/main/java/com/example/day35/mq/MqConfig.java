package com.example.day35.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 拓扑声明（对照笔记 §2.2）。
 * 业务队列挂死信交换机：Worker 重试耗尽后，消息自动转入 DLQ，
 * 由 DlqListener 把任务标记为 FAILED——失败有归宿，不丢消息。
 */
@Configuration
public class MqConfig {

    public static final String EXCHANGE = "day35.task.exchange";
    public static final String ROUTING_KEY = "task.created";
    public static final String TASK_QUEUE = "day35.task.queue";
    public static final String DLX = "day35.task.dlx";
    public static final String DLQ = "day35.task.dlq";

    @Bean
    public DirectExchange taskExchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue taskQueue() {
        return QueueBuilder.durable(TASK_QUEUE)
                // 关键两行：本队列的死信（被拒绝/过期/重试耗尽的消息）转投 DLX
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument("x-dead-letter-routing-key", "dead")
                .build();
    }

    @Bean
    public Binding taskBinding() {
        return BindingBuilder.bind(taskQueue()).to(taskExchange()).with(ROUTING_KEY);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX, true, false);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with("dead");
    }

    /** 消息体用 JSON 序列化：跨语言可读，RabbitMQ 管理台也能直接看内容 */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
