package com.example.day31;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Day31 入口：Task Created → RabbitMQ → Worker，演示 ACK / NACK / Retry / DLQ / Prefetch。
 * 对照笔记 §1。路由沿用 Day30 的 direct，今天只看「消息会不会丢、会不会重复、会不会卡死」。
 */
public class Day31App {

    private static final String EXCHANGE = "day31.direct";
    private static final String QUEUE = "day31.tasks";
    private static final String DLX = "day31.dlx";
    private static final String DLQ = "day31.dlq";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("day30");
        factory.setPassword("day30pwd");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            setup(channel);

            // ========== 重试演示：task-2 第一次失败，NACK requeue 回队列，第二次成功 ==========
            publish(channel, "task 1 created");
            publish(channel, "task 2 created");
            publish(channel, "task 3 created");
            runWorker(channel, 3);
            System.out.println("dlq <- " + pull(channel, DLQ));

            // ========== 死信演示：task-9 连续失败两次，第二次 NACK requeue=false 进 DLQ ==========
            publish(channel, "task 9 created");
            runWorker(channel, 1);
            System.out.println("dlq <- " + pull(channel, DLQ));
        }
    }

    /**
     * 声明：业务队列 + 死信交换 + 死信队列。
     * 业务队列带 x-dead-letter-exchange：被 NACK 且 requeue=false 的消息会被搬去 DLX。
     */
    private static void setup(Channel channel) throws IOException {
        channel.exchangeDeclare(EXCHANGE, "direct");
        channel.exchangeDeclare(DLX, "direct");

        Map<String, Object> queueArgs = new HashMap<>();
        queueArgs.put("x-dead-letter-exchange", DLX);
        queueArgs.put("x-dead-letter-routing-key", "dead");
        channel.queueDeclare(QUEUE, false, false, false, queueArgs);
        channel.queueBind(QUEUE, EXCHANGE, "task.created");

        channel.queueDeclare(DLQ, false, false, false, null);
        channel.queueBind(DLQ, DLX, "dead");

        channel.queuePurge(QUEUE);
        channel.queuePurge(DLQ);
    }

    private static void publish(Channel channel, String body) throws IOException {
        channel.basicPublish(EXCHANGE, "task.created", null, body.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Worker：手动 ACK。prefetch=1 表示「一次只给我一条，做完再发下一条」。
     * task-2 第一次处理失败 → NACK requeue=true → 回到队列重试；第二次成功 → ACK。
     * task-9 两次都失败 → 第二次 NACK requeue=false → 进 DLQ。
     */
    private static void runWorker(Channel channel, int expectedDone) throws Exception {
        channel.basicQos(1); // Prefetch：未确认消息最多 1 条，防止一次全塞给这个 Worker

        CountDownLatch done = new CountDownLatch(expectedDone);
        Map<String, Integer> attempts = new HashMap<>();

        channel.basicConsume(QUEUE, false, "day31-worker", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String text = new String(body, StandardCharsets.UTF_8);
                String id = text.split(" ")[1]; // 1 / 2 / 3 / 9
                int attempt = attempts.merge(id, 1, Integer::sum);
                long tag = envelope.getDeliveryTag();

                try {
                    if (("2".equals(id) && attempt == 1) || "9".equals(id)) {
                        throw new IllegalStateException("处理失败（模拟）");
                    }
                    System.out.println("worker ok    <- " + text + " (attempt " + attempt + ")");
                    channel.basicAck(tag, false); // 处理成功才确认
                    done.countDown();
                } catch (Exception e) {
                    if (attempt < 2) {
                        System.out.println("worker fail  <- " + text + " (attempt " + attempt + ")，NACK requeue");
                        channel.basicNack(tag, false, true); // 重试：回队列
                    } else {
                        System.out.println("worker giveup <- " + text + "，NACK 进 DLQ");
                        channel.basicNack(tag, false, false); // 放弃：进死信
                        done.countDown();
                    }
                }
            }
        });

        done.await(10, TimeUnit.SECONDS);
        channel.basicCancel("day31-worker");
    }

    private static String pull(Channel channel, String queue) throws IOException {
        GetResponse response = channel.basicGet(queue, true);
        if (response == null) {
            return "(empty)";
        }
        return new String(response.getBody(), StandardCharsets.UTF_8);
    }
}
