package com.example.maventask;

/**
 * Day1 Maven 工程入口 —— 对照学习：
 * <ul>
 *   <li>Maven 不是 javac 马甲：坐标 / 依赖 / 生命周期 / 插件</li>
 *   <li>{@code mvn clean package} 编译测试并打 jar；{@code java -jar} 仍靠 JVM</li>
 *   <li>pom 里 {@code mainClass} 写入 MANIFEST，否则 jar 无法直接启动</li>
 * </ul>
 * 第 1 周留在本模块；第 2 周再接入综合项目 Task Service。
 */
public class TaskApp {

    public static void main(String[] args) {
        // main 只负责启动；业务字符串交给可测的纯函数，便于 JUnit 断言
        System.out.println(startMessage("sync-user"));
    }

    /**
     * 抽出纯函数：测试断言返回值，而不用去抓 System.out。
     * 对应 Day1 实践「Hello World + 依赖(JUnit) + 构建」。
     */
    public static String startMessage(String taskName) {
        return "Task platform starting: " + taskName;
    }
}
