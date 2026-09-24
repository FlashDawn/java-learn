package com.example.day33;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Day33 入口：Topic / Producer / Consumer / Consumer Group / Offset / Retry / DLQ。
 * 对照笔记 §1。与 RabbitMQ 的 Exchange/Queue/Binding 不同，RocketMQ 只有 Topic 和 Consumer Group。
 */
public class Day33App {

    private static final String NAMESRV = "127.0.0.1:9876";
    private static final String TOPIC = "day33-task-events";
    private static final String GROUP = "day33-task-consumer-group";

    public static void main(String[] args) throws Exception {
        // ========== Producer：发消息到 Topic ==========
        DefaultMQProducer producer = new DefaultMQProducer("day33-producer-group");
        producer.setNamesrvAddr(NAMESRV);
        producer.start();

        for (int i = 1; i <= 3; i++) {
            String body = "task " + i + " created";
            Message msg = new Message(TOPIC, body.getBytes(StandardCharsets.UTF_8));
            producer.send(msg);
            System.out.println("sent <- " + body);
        }
        producer.shutdown();

        // ========== Consumer：Consumer Group 订阅 Topic，按 Offset 消费 ==========
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP);
        consumer.setNamesrvAddr(NAMESRV);
        consumer.subscribe(TOPIC, "*");

        CountDownLatch done = new CountDownLatch(3);
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                String body = new String(msg.getBody(), StandardCharsets.UTF_8);
                // Offset：这条消息在队列里的位置。Broker 记住每个 Consumer Group 消费到哪个 Offset
                System.out.println("consumed <- " + body + " (offset=" + msg.getQueueOffset() + ", group=" + GROUP + ")");
                done.countDown();
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        done.await(10, TimeUnit.SECONDS);
        consumer.shutdown();
    }
}
