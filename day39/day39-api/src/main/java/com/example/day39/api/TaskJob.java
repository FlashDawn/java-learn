package com.example.day39.api;

import java.io.Serializable;

/**
 * RPC 传输对象（对照笔记 §1.5 序列化）。
 * 跨网络传输的 DTO 三要素：
 * ① implements Serializable（标识可序列化）；
 * ② 无参构造（Hessian2 反序列化先new空对象，再用反射逐字段注入——
 *    本工程曾用 record，因没有无参构造在 Provider 侧解码直接失败，见笔记 §5 排障三）；
 * ③ 字段有 getter/setter。
 */
public class TaskJob implements Serializable {

    private Long taskId;
    private String title;

    public TaskJob() {
        // Hessian2 反序列化必需的无参构造
    }

    public TaskJob(Long taskId, String title) {
        this.taskId = taskId;
        this.title = title;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
