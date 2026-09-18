package com.example.day14.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Day14：创建/更新用户请求体。
 * 钉死概念：@Valid + Bean Validation 注解。对照笔记：§1.2。
 */
public class UserRequest {

    @NotBlank(message = "name 不能为空")
    @Size(max = 64, message = "name 最长 64")
    private String name;

    @NotBlank(message = "email 不能为空")
    @Email(message = "email 格式不正确")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
