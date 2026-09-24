package com.example.day30;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.nio.charset.StandardCharsets;

/**
 * Day30 入口：用同一个 Broker 对比 Direct、Fanout、Topic。
 * 对照笔记 §1。可靠性（ACK / 死信）是 Day31，这里 basicGet 自动确认，只看路由。
 */
public class Day30App {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        // guest 只能从 Broker 自己的 localhost 连入；宿主机经端口映射进来会被拒绝
        factory.setUsername("day30");
        factory.setPassword("day30pwd");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            direct(channel);
            fanout(channel);
            topic(channel);
        }
    }

    // ========== Direct：Routing Key 必须与 Binding Key 完全相等 ==========
    private static void direct(Channel channel) throws Exception {
        String exchange = "day30.direct";
        channel.exchangeDeclare(exchange, "direct");
        bind(channel, "day30.email", exchange, "task.created");
        bind(channel, "day30.sms", exchange, "task.done");

        publish(channel, exchange, "task.created", "task 2 created");
        publish(channel, exchange, "task.done", "task 2 done");

        System.out.println("direct email <- " + pull(channel, "day30.email"));
        System.out.println("direct sms   <- " + pull(channel, "day30.sms"));
        System.out.println("direct email <- " + pull(channel, "day30.email"));
    }

    // ========== Fanout：忽略 Routing Key，绑上的队列都收到 ==========
    private static void fanout(Channel channel) throws Exception {
        String exchange = "day30.fanout";
        channel.exchangeDeclare(exchange, "fanout");
        bind(channel, "day30.email.fanout", exchange, "");
        bind(channel, "day30.audit.fanout", exchange, "");

        publish(channel, exchange, "this-key-is-ignored", "task 2 created");

        System.out.println("fanout email <- " + pull(channel, "day30.email.fanout"));
        System.out.println("fanout audit <- " + pull(channel, "day30.audit.fanout"));
    }

    // ========== Topic：Binding Key 用 * 和 # 匹配 Routing Key ==========
    private static void topic(Channel channel) throws Exception {
        String exchange = "day30.topic";
        channel.exchangeDeclare(exchange, "topic");
        // * 只匹配一个词；# 匹配零个或多个词
        bind(channel, "day30.created", exchange, "task.created");
        bind(channel, "day30.all", exchange, "task.#");

        publish(channel, exchange, "task.created", "task 2 created");
        publish(channel, exchange, "task.created.email", "notify task 2");

        System.out.println("topic created <- " + pull(channel, "day30.created"));
        System.out.println("topic created <- " + pull(channel, "day30.created"));
        System.out.println("topic all     <- " + pull(channel, "day30.all"));
        System.out.println("topic all     <- " + pull(channel, "day30.all"));
    }

    private static void bind(Channel channel, String queue, String exchange, String bindingKey) throws Exception {
        channel.queueDeclare(queue, false, false, false, null);
        channel.queuePurge(queue);
        channel.queueBind(queue, exchange, bindingKey);
    }

    private static void publish(Channel channel, String exchange, String routingKey, String body) throws Exception {
        channel.basicPublish(exchange, routingKey, null, body.getBytes(StandardCharsets.UTF_8));
    }

    /** 演示用拉取。真正的 Consumer 是 Broker 推送；ACK 策略留到 Day31。 */
    private static String pull(Channel channel, String queue) throws Exception {
        GetResponse response = channel.basicGet(queue, true);
        if (response == null) {
            return "(empty)";
        }
        return new String(response.getBody(), StandardCharsets.UTF_8);
    }
}
