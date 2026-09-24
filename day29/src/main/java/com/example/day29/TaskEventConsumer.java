package com.example.day29;

/**
 * Day29 · Consumer（对照笔记 §1.2）。
 * 只负责处理已经到达的 Message，不关心消息是谁 new 出来的。
 */
public class TaskEventConsumer {

    /**
     * 模拟发通知：慢，但是业务上不该挡住「创建任务」本身。
     */
    public void handle(Message message) {
        sleepQuietly(400);
        System.out.println("consumed " + message.id() + " task=" + message.taskId() + " event=" + message.event());
    }

    private static void sleepQuietly(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
