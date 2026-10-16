package com.example.day38.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Day38 用户服务（18100）：与 Day37 相同，注册到 Nacos 供 Gateway 路由与 Feign 调用。 */
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
