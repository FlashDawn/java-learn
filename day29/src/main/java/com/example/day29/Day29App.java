package com.example.day29;

/**
 * Day29 入口：用同一条「任务已创建」对比同步调用和异步投递。
 * 对照笔记 §1.6。不连接 RabbitMQ，那是 Day30。
 */
public class Day29App {

    public static void main(String[] args) throws InterruptedException {
        TaskEventConsumer consumer = new TaskEventConsumer();

        // ========== 同步：Producer 直接调用 Consumer，必须等通知做完 ==========
        // 为什么：这就是不用消息队列时的写法。创建任务的人被通知耗时拖住。
        long syncStart = System.nanoTime();
        consumer.handle(new Message("sync-1", 1L, "CREATED"));
        long syncMs = (System.nanoTime() - syncStart) / 1_000_000;
        System.out.println("sync producer waited " + syncMs + " ms");

        // ========== 异步：Producer 只把 Message 放进 Broker 的 Queue ==========
        // 为什么：投递返回后，创建任务的线程就可以结束；慢的通知交给另一个线程。
        Broker broker = new Broker();
        TaskEventProducer producer = new TaskEventProducer(broker);
        long asyncStart = System.nanoTime();
        boolean accepted = producer.publishTaskCreated(2L);
        long asyncMs = (System.nanoTime() - asyncStart) / 1_000_000;
        // 先看队列：消息已在 Broker 里，Consumer 还没开工，Producer 已经返回
        System.out.println("async publish accepted=" + accepted + " waited " + asyncMs + " ms, queue=" + broker.size());

        Thread worker = new Thread(() -> {
            try {
                Message message = broker.take(2_000);
                if (message != null) {
                    consumer.handle(message);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "task-consumer");
        worker.start();
        worker.join();
    }
}
