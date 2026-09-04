/**
 * Day5 · 带注解的处理器 —— 供反射读取 {@link TaskHandler} 并 invoke handle。
 * <p>
 * 同时演示 {@link Deprecated}：过时 API，调用方会收到编译警告。
 */
@TaskHandler("import-orders")
public class ImportHandler {

    public void handle() {
        System.out.println("handle=import-orders");
    }

    /** 计划内注解：标记过时，勿在新代码调用。 */
    @Deprecated
    public void oldHandle() {
        System.out.println("old");
    }
}
