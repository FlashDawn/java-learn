/**
 * Day2 · User —— 钉死概念：Class / Object / Constructor / Encapsulation / final / static。
 * <p>
 * 与笔记关系：§1.1 类与对象、§1.2 构造、§1.3 封装、§1.8 static、§1.9 final。
 * 第 2 周进入 User Service 时仍是这个形状：id 创建后不变。
 */
public class User {

    /**
     * 【static 字段】全类共用一份。Demo 单线程；生产勿用 static 存「当前用户」等业务状态。
     */
    private static int registeredCount = 0;

    /** 【final + 封装】创建后不允许改；外面只能 getId，没有 setId。 */
    private final String id;
    private final String name;

    /**
     * 【构造】new 时调用；把对象放到合法初始状态，并维护 static 计数。
     * this.xxx = 参数：区分字段与形参同名。
     */
    public User(String id, String name) {
        this.id = id;
        this.name = name;
        registeredCount++;
    }

    /** 【封装·只读暴露】Getter，不提供随意 setter。 */
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /** 【static 方法】无 this，只能碰 static 成员；调用：User.getRegisteredCount()。 */
    public static int getRegisteredCount() {
        return registeredCount;
    }
}
