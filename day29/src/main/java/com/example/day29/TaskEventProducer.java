package com.example.day29;

/**
 * Day29 · Producer（对照笔记 §1.1）。
 * 只负责把「任务已创建」变成一条 Message 交给 Broker，自己不发通知。
 */
public class TaskEventProducer {

    private final Broker broker;

    public TaskEventProducer(Broker broker) {
        this.broker = broker;
    }

    public boolean publishTaskCreated(long taskId) {
        Message message = new Message("msg-" + taskId, taskId, "CREATED");
        return broker.publish(message);
    }
}
