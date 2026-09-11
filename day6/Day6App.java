/**
 * Day6 入口 —— 多线程基础与三种现象：
 * <ol>
 *   <li>Race / Atomicity：无锁 {@code ++} 丢失更新</li>
 *   <li>synchronized：互斥修复计数</li>
 *   <li>volatile：停止旗标的可见性；另附 FutureTask/Callable</li>
 * </ol>
 * 实践：10 个线程各加 1000 次。对照：{@code day6/学习笔记.md}
 */
public class Day6App {

    /** 无同步的共享变量：并发 ++ 非原子 → unsafeCount 常 &lt; 10000。 */
    private static int unsafeCount = 0;
    /** 在同一把 LOCK 上 ++ → 期望精确 10000。 */
    private static int safeCount = 0;
    /** 锁对象必须各方一致；各 new 各的等于没锁。 */
    private static final Object LOCK = new Object();
    /**
     * 【volatile】保证写对其他线程可见；不保证 count++ 原子性。
     * 用于「停止信号」这类单写多读旗标。
     */
    private static volatile boolean running = true;

    public static void main(String[] args) throws Exception {
        // ========== Thread + Runnable：start 启动新线程；join 等待结束 ==========
        Thread[] unsafe = new Thread[10];
        for (int i = 0; i < 10; i++) {
            unsafe[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    unsafeCount++; // 读-改-写竞态
                }
            }, "unsafe-" + i);
            unsafe[i].start(); // 若误调 run() 则仍在主线程同步执行
        }
        for (Thread t : unsafe) {
            t.join();
        }
        System.out.println("unsafeCount=" + unsafeCount);

        // ========== synchronized：临界区互斥，恢复原子性 ==========
        Thread[] safe = new Thread[10];
        for (int i = 0; i < 10; i++) {
            safe[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    synchronized (LOCK) {
                        safeCount++;
                    }
                }
            }, "safe-" + i);
            safe[i].start();
        }
        for (Thread t : safe) {
            t.join();
        }
        System.out.println("safeCount=" + safeCount);

        // ========== volatile 可见性：主线程改 false，工作线程应能退出循环 ==========
        Thread worker = new Thread(() -> {
            while (running) {
                // 空转等待停止信号（教学用；生产用 wait/通知或打断）
            }
            System.out.println("worker=stopped");
        }, "volatile-worker");
        worker.start();
        Thread.sleep(50);
        running = false;
        worker.join();

        // ========== Callable + FutureTask：有返回值的异步任务；get 阻塞取结果 ==========
        java.util.concurrent.FutureTask<String> futureTask =
                new java.util.concurrent.FutureTask<>(() -> "task-done");
        new Thread(futureTask, "callable").start();
        System.out.println("future=" + futureTask.get());
    }
}
