package com.example.maventask;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Day1：第一个 Maven {@code test} 依赖（scope=test，不打进业务 jar）。
 * {@code mvn clean package} 默认先跑测试；失败则不会打出可用业务结论。
 */
class TaskAppTest {

    @Test
    void startMessage_shouldIncludeTaskName() {
        // 断言的是 startMessage 的契约，与 main 里打印的 sync-user 无关
        assertEquals(
                "Task platform starting: import-orders",
                TaskApp.startMessage("import-orders")
        );
    }
}
