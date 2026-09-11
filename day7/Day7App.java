import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Day7 入口 —— 手写 ThreadPoolExecutor，对照核心参数与拒绝策略，再演示 CompletableFuture。
 * <p>
 * 参数：core=2，max=4，有界队列容量 4，keepAlive=30s，AbortPolicy。
 * 顺序：先正常提交 → 再 CF（池空闲）→ 最后压测拒绝；若先压测再 CF，Abort 会拒掉异步任务。
 * 对照：{@code day7/学习笔记.md}
 */
public class Day7App {

    public static void main(String[] args) throws Exception {
        // 【BlockingQueue】有界队列：满了才可能扩到 max；无界队列会导致永远扩不到 max
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(4);

        // 【ThreadPoolExecutor】生产应手写池，慎用 Executors 无界队列工厂
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                2,                          // corePoolSize 常驻
                4,                          // maximumPoolSize 上限
                30, TimeUnit.SECONDS,       // keepAliveTime 非核心空闲存活
                queue,                      // workQueue
                new ThreadPoolExecutor.AbortPolicy() // 满则抛 RejectedExecutionException
        );

        // ========== 任务提交 + 并发执行：6 个任务，观察线程名复用 ==========
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            final int id = i;
            futures.add(pool.submit(() -> {
                System.out.println("run=t-" + id + " thread=" + Thread.currentThread().getName());
                sleep(100);
                return null;
            }));
        }
        for (Future<?> f : futures) {
            f.get(); // 等待完成；生产常加超时
        }
        System.out.println("core=" + pool.getCorePoolSize()
                + " max=" + pool.getMaximumPoolSize()
                + " queueCap=4");

        // ========== CompletableFuture：指定自家 pool，避免默认 commonPool 被阻塞任务占满 ==========
        CompletableFuture<String> cf = CompletableFuture
                .supplyAsync(() -> "import-orders", pool)
                .thenApply(name -> "done:" + name)
                .exceptionally(ex -> "failed:" + ex.getMessage());
        System.out.println("cf=" + cf.get());

        // ========== 超载 + 拒绝策略：队列与 max 都满后 Abort → 统计 rejected ==========
        int rejected = 0;
        for (int i = 0; i < 20; i++) {
            try {
                pool.execute(() -> sleep(500));
            } catch (RejectedExecutionException e) {
                rejected++;
            }
        }
        System.out.println("rejected=" + rejected);

        // 必须 shutdown，否则进程可能不退出
        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
