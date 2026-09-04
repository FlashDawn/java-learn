import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Day4 · Generic —— {@code Repo<T>}：编译期约束仓库元素类型。
 * <p>
 * {@code Repo<Task>} 不能 add 错类型；取出无需强转。擦除后运行时无 T，故不能 {@code new T()}。
 * 对照笔记 §1.1。返回不可变视图，防止外面对内 list 乱改。
 */
public class Repo<T> {

    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public List<T> list() {
        return Collections.unmodifiableList(items);
    }
}
