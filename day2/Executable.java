/**
 * Day2 · Interface —— 「能做什么」，不规定「是哪一种」。
 * <p>
 * Task implements Executable；Worker 将来可只依赖本接口。对照笔记 §1.6。
 */
public interface Executable {

    /** 执行任务；具体步骤由实现类决定（多态）。 */
    void execute();
}
