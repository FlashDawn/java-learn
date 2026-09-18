package com.example.day14.common;

/**
 * Day14：资源不存在。对照笔记：§1.3。
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
