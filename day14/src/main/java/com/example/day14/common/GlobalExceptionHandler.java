package com.example.day14.common;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Day14：全局异常处理 —— 统一错误 JSON。
 * 钉死概念：GlobalExceptionHandler + 计划错误格式。对照笔记：§1.3。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleValidation(MethodArgumentNotValidException ex) {
        // 为什么拼字段错误：调试参数问题时比笼统 "Invalid parameter" 更可操作
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatField)
                .collect(Collectors.joining("; "));
        if (msg.isBlank()) {
            msg = "Invalid parameter";
        }
        return ApiResponse.fail(400, msg);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNotFound(NotFoundException ex) {
        return ApiResponse.fail(404, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleBusiness(BusinessException ex) {
        return ApiResponse.fail(409, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleOther(Exception ex) {
        return ApiResponse.fail(500, "Internal error: " + ex.getMessage());
    }

    private String formatField(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}
