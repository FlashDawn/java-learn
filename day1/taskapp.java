package day1;

/**
 * Day1 单文件 Demo：对照「源码 .java → 字节码 .class → JVM 执行」。
 * <p>
 * 学习点：JDK 含 javac；本机 JDK 21 也可直接 {@code java day1/taskapp.java}（JDK11+ 单文件）。
 * Maven 多文件工程见 {@code maventask-day1}，不要把单文件快捷方式当成后端构建方式。
 */
public class taskapp {

    /**
     * JVM 入口：必须是 public static void main。
     * static：启动时尚无对象实例，故方法挂在类上。
     */
    public static void main(String[] args) {
        // 改这一行后：JDK8 需重新 javac；JDK11+ 用 java 跑源文件可直接看到新输出
        System.out.println("输出新的88");
    }
}
