/**
 * Day2 · Inheritance + Polymorphism + 多接口。
 * <p>
 * {@code extends Task} = 是一种任务（复用状态机）；{@code implements Retryable} = 额外能力。
 * {@code @Override} 写错方法名会编译失败。对照笔记 §1.4 / §1.5 / §1.6。
 */
public class ImportTask extends Task implements Retryable {

    private final String orderId;

    public ImportTask(String id, String userId, String orderId) {
        // 必须先初始化父类字段（id/userId/status）
        super(id, userId);
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String type() {
        return "IMPORT";
    }

    /**
     * Executable.execute 的具体实现：走父类封装好的状态机。
     * 真实导入会读 Order/写库；教学只演示状态变迁。
     */
    @Override
    public void execute() {
        start();
        complete();
    }

    @Override
    public int maxRetry() {
        return 3;
    }
}
