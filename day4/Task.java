/**
 * Day4 · 只读任务模型 —— 供 Stream 筛选/分组/聚合，无状态机干扰。
 * <p>
 * cost 用于 sorted / reduce；type/status 用于 filter、groupingBy、flatMap。
 * 对照：{@code day4/学习笔记.md}
 */
public final class Task {

    private final String id;
    private final String type;
    private final String status;
    private final int cost;

    public Task(String id, String type, String status, int cost) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public int getCost() {
        return cost;
    }
}
