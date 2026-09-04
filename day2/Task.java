/**
 * Day2 · Abstract Class —— 「是一种任务」：可有字段与已实现方法，不能直接 {@code new Task(...)}。
 * <p>
 * 同时 {@code implements Executable}：抽象类承载实体状态机，接口承载「能执行」能力。
 * 对照笔记 §1.3 封装、§1.7 抽象类、§1.5 多态点 {@link #type()}。
 */
public abstract class Task implements Executable {

    /** 【final】任务主键创建后不变，防止对不齐 DB/MQ。 */
    private final String id;
    private final String userId;
    /** 【封装】禁止 public；只允许 start/complete 按状态机修改。 */
    private String status;

    /**
     * 【protected 构造】只给子类调用（ImportTask 里 super(...)）。
     * 初始状态固定 CREATED，避免残缺对象。
     */
    protected Task(String id, String userId) {
        this.id = id;
        this.userId = userId;
        this.status = "CREATED";
    }

    /** final 方法：子类不能覆盖，防止破坏只读契约。 */
    public final String getId() {
        return id;
    }

    public final String getUserId() {
        return userId;
    }

    public final String getStatus() {
        return status;
    }

    /**
     * 【封装·状态机】CREATED → RUNNING。标 final，禁止子类绕开校验直接改 status。
     */
    public final void start() {
        if (!"CREATED".equals(status)) {
            throw new IllegalStateException("只能从 CREATED 启动，当前=" + status);
        }
        this.status = "RUNNING";
    }

    /** RUNNING → COMPLETED；非法跳转抛异常（Day5 会系统化讲异常）。 */
    public final void complete() {
        if (!"RUNNING".equals(status)) {
            throw new IllegalStateException("只能从 RUNNING 完成，当前=" + status);
        }
        this.status = "COMPLETED";
    }

    /**
     * 【多态钩子】抽象方法逼子类实现；Day2App 里 Task 引用调用时走 ImportTask。
     */
    public abstract String type();
}
