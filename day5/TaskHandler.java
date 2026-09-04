import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Day5 · 自定义注解 —— 元数据：「这是可被扫描的任务处理器」。
 * <p>
 * {@link RetentionPolicy#RUNTIME} 必须保留到运行时，反射才能 {@code getAnnotation}。
 * Spring 大量 {@code @Service}/{@code @Autowired} 同理：注解声明意图，反射/扫描执行意图。
 * 对照笔记 §1.4 / §1.6。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TaskHandler {

    /** 处理器逻辑名，例如 import-orders。 */
    String value();
}
