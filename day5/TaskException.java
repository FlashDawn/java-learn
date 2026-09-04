/**
 * Day5 · 受检异常（checked）—— 继承 Exception，调用方必须 catch 或 throws。
 * <p>
 * 用于「可预期、调用方应处理」的业务失败。对照笔记 §1.1。
 */
public class TaskException extends Exception {

    public TaskException(String message) {
        super(message);
    }
}
