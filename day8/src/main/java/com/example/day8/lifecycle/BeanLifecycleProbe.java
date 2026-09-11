package com.example.day8.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

/**
 * Day 8：@Component + Bean 生命周期回调。
 * 对照笔记 §1.6 生命周期。综合项目：通用探针可换成健康检查组件。
 */
@Component
public class BeanLifecycleProbe {

    private boolean ready;

    /**
     * 依赖注入完成后、Bean 可用前调用。
     * 为什么：证明「创建 → 注入 → 初始化」顺序。
     */
    @PostConstruct
    public void onInit() {
        this.ready = true;
        System.out.println("lifecycle=@PostConstruct BeanLifecycleProbe");
    }

    /**
     * Container 关闭时调用。
     * 为什么：资源释放钩子（连接池、线程池）落在这里或 DisposableBean。
     */
    @PreDestroy
    public void onDestroy() {
        System.out.println("lifecycle=@PreDestroy BeanLifecycleProbe");
        this.ready = false;
    }

    public boolean isReady() {
        return ready;
    }
}
