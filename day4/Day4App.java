import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Day4 入口 —— 泛型仓库 + Stream 计划操作全覆盖。
 * <p>
 * 每段注释标注：filter / map / flatMap / sorted / collect / groupingBy / reduce / Optional。
 * Lambda = 函数式接口的短写法；方法引用 {@code Task::getId} 同理。
 * 对照：{@code day4/学习笔记.md}
 */
public class Day4App {

    public static void main(String[] args) {
        // 【泛型】Repo<Task> 编译期挡错类型
        Repo<Task> repo = new Repo<>();
        repo.add(new Task("t-1", "IMPORT", "CREATED", 10));
        repo.add(new Task("t-2", "IMPORT", "RUNNING", 30));
        repo.add(new Task("t-3", "SYNC", "CREATED", 20));
        repo.add(new Task("t-4", "SYNC", "COMPLETED", 40));

        // filter → sorted → map → collect：筛 CREATED，按 cost 排序，取出 id 列表
        List<String> createdIds = repo.list().stream()
                .filter(task -> "CREATED".equals(task.getStatus())) // Lambda
                .sorted((a, b) -> Integer.compare(a.getCost(), b.getCost()))
                .map(Task::getId) // 方法引用：一对一变换
                .collect(Collectors.toList());
        System.out.println("createdIds=" + createdIds);

        // map 出 List 后再 flatMap 摊平，再 distinct（教学用标签集合）
        List<String> tags = repo.list().stream()
                .map(task -> List.of(task.getType(), task.getStatus()))
                .flatMap(List::stream) // 一对多再合并成一条流
                .distinct()
                .collect(Collectors.toList());
        System.out.println("tags=" + tags.size());

        // groupingBy：按 type 分组 → Map<String, List<Task>>
        Map<String, List<Task>> byType = repo.list().stream()
                .collect(Collectors.groupingBy(Task::getType));
        System.out.println("importCount=" + byType.get("IMPORT").size());

        // reduce：把多个 cost 聚合成一个总和
        int totalCost = repo.list().stream()
                .map(Task::getCost)
                .reduce(0, Integer::sum);
        System.out.println("totalCost=" + totalCost);

        // Optional：可能没有 t-9；用 orElseGet 提供默认，避免暴力 get() NPE
        Optional<Task> missing = repo.list().stream()
                .filter(task -> "t-9".equals(task.getId()))
                .findFirst();
        System.out.println("missing=" + missing.orElseGet(() -> new Task("none", "NONE", "NONE", 0)).getId());
    }
}
