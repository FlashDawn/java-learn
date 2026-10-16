package com.example.day37.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置热更新演示（对照笔记 §1.4）。
 * app.message 来自 Nacos 配置中心的 data-service.yaml；
 * @RefreshScope 是关键：Nacos 推送变更时，这个 Bean 被重建，
 * @Value 重新注入新值——不重启进程，配置生效。
 */
@RestController
@RefreshScope
public class DataController {

    @Value("${app.message:本地默认值（Nacos 未配置时兜底）}")
    private String message;

    @GetMapping("/data/message")
    public String message() {
        return "app.message = " + message;
    }
}
