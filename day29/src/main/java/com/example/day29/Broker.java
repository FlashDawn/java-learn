package com.example.day29;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Day29 · Broker + Queue（对照笔记 §1.3、§1.5）。
 * 本日用进程内队列代替真正的消息中间件，把「谁生产」和「谁消费」拆开。
 * 综合项目里这个角色以后由 RabbitMQ 承担，应用代码不再直接 new 对方。
 */
public class Broker {

    /** Queue：有界，满了就拒绝，避免无界堆积把内存打爆（对照 Day7 无界队列 OOM）。 */
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>(8);

    /**
     * 投递。返回 false 表示队列已满，消息没有进入 Broker。
     * 调用方必须处理失败，不能假装发出去了。
     */
    public boolean publish(Message message) {
        return queue.offer(message);
    }

    /**
     * 取一条。超时返回 null，避免消费者在空队列上永远堵住演示线程。
     */
    public Message take(long timeoutMillis) throws InterruptedException {
        return queue.poll(timeoutMillis, TimeUnit.MILLISECONDS);
    }

    public int size() {
        return queue.size();
    }
}
