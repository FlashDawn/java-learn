package com.example.day14.common;

/**
 * Day14：业务规则冲突（如终态改状态）。对照笔记：§1.3。
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
