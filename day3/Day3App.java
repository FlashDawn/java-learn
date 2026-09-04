import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Day3 入口 —— 计划条目与代码块一一对应（见下方分段注释）。
 * <p>
 * 实践：用户列表、按 ID 查找、排序、去重。原理重点在 HashMap（笔记 §1.4）。
 * 对照：{@code day3/学习笔记.md}
 */
public class Day3App {

    public static void main(String[] args) {
        // ========== ArrayList：有序列表，允许重复 id ==========
        List<User> list = new ArrayList<>();
        list.add(new User("u-2", "Bob"));
        list.add(new User("u-1", "Ada"));
        list.add(new User("u-3", "Cara"));
        list.add(new User("u-1", "Ada-dup")); // 故意重复 id，后面用 Set 去重
        System.out.println("list.size=" + list.size()); // 4

        // ========== HashMap：按 key O(1) 查找；putIfAbsent 保留先放入的 Ada ==========
        // 原理：hash → 桶 → 冲突拉链/红黑树 → 扩容与负载因子 0.75（见笔记）
        Map<String, User> byId = new HashMap<>();
        for (User user : list) {
            byId.putIfAbsent(user.getId(), user);
        }
        System.out.println("lookup=" + byId.get("u-1").getName()); // Ada

        // ========== 排序：依赖 User.compareTo ==========
        Collections.sort(list);
        System.out.println("sortedFirst=" + list.get(0).getId()); // u-1

        // ========== HashSet：去重，依赖 equals/hashCode → distinct=3 ==========
        Set<User> distinct = new HashSet<>(list);
        System.out.println("distinct=" + distinct.size());

        // ========== TreeMap：key 有序（红黑树），适合按 id 顺序遍历 ==========
        Map<String, User> tree = new TreeMap<>(byId);
        System.out.println("treeFirst=" + tree.keySet().iterator().next());

        // ========== Queue（LinkedList 实现）：FIFO，任务排队 ==========
        Queue<String> queue = new LinkedList<>();
        queue.add("t-1");
        queue.add("t-2");
        System.out.println("queuePoll=" + queue.poll()); // t-1；poll 空则 null

        // ========== Deque：双端；紧急任务 addFirst 插队 ==========
        Deque<String> deque = new ArrayDeque<>();
        deque.addLast("t-3");
        deque.addFirst("urgent");
        System.out.println("dequeFirst=" + deque.pollFirst()); // urgent

        // ========== ConcurrentHashMap：并发场景替代 HashMap（本 Demo 单线程仅示类型）==========
        ConcurrentHashMap<String, User> chm = new ConcurrentHashMap<>(byId);
        System.out.println("chm.size=" + chm.size());
    }
}
