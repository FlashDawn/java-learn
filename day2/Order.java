/**
 * Day2 · Order —— 封装 + <b>组合</b>（订单属于用户，持有 userId，绝不 extends User）。
 * <p>
 * 金额用分（int），避免 double 表示钱。对照笔记 §1.3 / §1.4「能组合就别继承」。
 */
public class Order {

    private final String id;
    /** 关联用户：组合字段，不是继承关系。 */
    private final String userId;
    private final int amountFen;

    public Order(String id, String userId, int amountFen) {
        this.id = id;
        this.userId = userId;
        this.amountFen = amountFen;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public int getAmountFen() {
        return amountFen;
    }
}
