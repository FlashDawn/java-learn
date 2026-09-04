/**
 * Day2 · Interface —— 可重试能力；一个类可 implements 多个接口（ImportTask 同时是 Task 与 Retryable）。
 * <p>
 * 对照笔记 §1.6：能力用接口拼装，不要塞进深继承树。
 */
public interface Retryable {

    /** 最大重试次数；MQ 消费失败重试时会用到同类概念。 */
    int maxRetry();
}
