package com.example.day8.web;

import org.springframework.stereotype.Controller;

/**
 * Day 8：@Controller —— MVC 控制器刻板注解（返回视图名；本日不渲染页面）。
 * 对照笔记 §1.5。与 @RestController 对照：后者额外带 @ResponseBody。
 */
@Controller
public class AdminHintController {
    // 空类即可：目的是让 Container 扫描到 @Controller Bean
}
