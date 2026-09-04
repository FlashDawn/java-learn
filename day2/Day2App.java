/**
 * Day2 入口：把「类/对象/封装/继承/多态/接口/抽象类/static/final」串成一条可读路径。
 * <p>
 * 阅读顺序建议：User → Order → Executable/Retryable → Task → ImportTask → 本类。
 * 对照笔记：{@code day2/学习笔记.md} §1 各小节。
 */
public class Day2App {

    public static void main(String[] args) {
        // 【Class/Object/构造】new = 按图纸造实例；user 是引用，不是对象拷贝
        User user = new User("u-1", "Ada");
        // 【组合而非继承】Order 持有 userId，不 extends User（「属于」≠「是一种」）
        Order order = new Order("o-1", user.getId(), 9900);

        // 【多态】编译期类型 Task，运行期对象 ImportTask → type()/execute() 走子类
        Task task = new ImportTask("t-1", user.getId(), order.getId());
        System.out.println(task.type() + " " + task.getStatus());

        // 【接口 Executable】execute 在 Task 上声明，ImportTask 实现具体流程
        task.execute();
        System.out.println(task.getId() + " " + task.getStatus());
        System.out.println("order=" + order.getId() + " amountFen=" + order.getAmountFen());
        // 【多接口】ImportTask 还是 Retryable；生产应让方法直接收接口，少强转
        System.out.println("retry=" + ((Retryable) task).maxRetry());
        // 【static】注册计数挂在类上，无需某个 User 实例
        System.out.println("users=" + User.getRegisteredCount());
    }
}
