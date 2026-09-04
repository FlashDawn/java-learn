/**
 * Day5 · 运行时异常（unchecked）—— 继承 RuntimeException，签名无需声明。
 * <p>
 * 用于编程错误/非法状态；不强迫每个调用方写 throws。对照笔记 §1.2。
 */
public class BadTaskStateException extends RuntimeException {

    public BadTaskStateException(String message) {
        super(message);
    }
}
