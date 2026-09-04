import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Day5 入口 —— 分段对应：受检异常 / 运行时异常 / 文件 IO / 注解+反射 / SuppressWarnings。
 * <p>
 * 计划重点：{@code @Override}（见 Day2）、{@code @Deprecated}、{@code @SuppressWarnings}，
 * 以及 Spring 为何依赖 Annotation + Reflection（本 Demo 手写迷你版扫描）。
 * 对照：{@code day5/学习笔记.md}
 */
public class Day5App {

    public static void main(String[] args) {
        // ========== 受检异常：方法签名 throws，调用方必须处理 ==========
        try {
            startOnlyWhenCreated("RUNNING");
        } catch (TaskException e) {
            System.out.println("checked=" + e.getMessage());
        }

        // ========== 运行时异常：可不声明 throws，但仍应在边界捕获打日志 ==========
        try {
            requireCreated("RUNNING");
        } catch (BadTaskStateException e) {
            System.out.println("runtime=" + e.getMessage());
        }

        // ========== IO：Files API；失败多为 IOException（受检）；固定 UTF-8 ==========
        try {
            Path file = Path.of("task-note.txt");
            Files.writeString(file, "task=t-1\n", StandardCharsets.UTF_8);
            String content = Files.readString(file, StandardCharsets.UTF_8).trim();
            System.out.println("io=" + content);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            System.out.println("ioFailed=" + e.getMessage());
        }

        // ========== 反射：读注解 → newInstance → invoke（Spring 扫描的简化模型）==========
        Class<ImportHandler> clazz = ImportHandler.class;
        TaskHandler ann = clazz.getAnnotation(TaskHandler.class);
        System.out.println("ann=" + ann.value());
        try {
            ImportHandler handler = clazz.getDeclaredConstructor().newInstance();
            clazz.getMethod("handle").invoke(handler);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }

        // ========== @SuppressWarnings：压制警告（教学演示；生产勿滥用掩盖真问题）==========
        @SuppressWarnings("deprecation")
        Object ignored = new ImportHandler();
        System.out.println("suppress=ok");
    }

    /** 受检：非法则抛 TaskException，强迫调用方面对。 */
    static void startOnlyWhenCreated(String status) throws TaskException {
        if (!"CREATED".equals(status)) {
            throw new TaskException("只能从 CREATED 启动");
        }
    }

    /** 非受检：非法则抛 RuntimeException 子类。 */
    static void requireCreated(String status) {
        if (!"CREATED".equals(status)) {
            throw new BadTaskStateException("状态非法: " + status);
        }
    }
}
