import java.util.Objects;

/**
 * Day3 · User —— 为集合课服务的领域对象。
 * <p>
 * 必须正确实现 {@link #equals}/{@link #hashCode}（按 id），HashSet 才能按「同一个人」去重；
 * 实现 {@link Comparable} 以便 {@code Collections.sort} / TreeMap 相关排序。
 * 对照笔记：day3/学习笔记.md §1.5 HashSet、§1.1 排序。
 */
public final class User implements Comparable<User> {

    private final String id;
    private final String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /** 按 id 字典序；供 ArrayList 排序。 */
    @Override
    public int compareTo(User other) {
        return id.compareTo(other.id);
    }

    /**
     * HashSet/HashMap 判等：同 id 即同一用户（即使 name 不同，如 Ada-dup）。
     * 与 hashCode 必须成对一致，否则 Set/Map 行为错乱。
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id.equals(((User) o).id);
    }

    /** 参与 equals 的字段必须进入 hashCode。 */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
