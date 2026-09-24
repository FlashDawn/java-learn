package com.example.day28.web;

import com.example.day28.cache.UserCacheService;
import com.example.day28.domain.User;
import com.example.day28.store.InMemoryUserDb;
import org.springframework.web.bind.annotation.*;

/**
 * User REST 接口。Day28 的验证入口：
 * GET 走缓存（Cache-Aside），PUT 先写 DB 再删缓存。
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserCacheService cacheService;
    private final InMemoryUserDb db;

    public UserController(UserCacheService cacheService, InMemoryUserDb db) {
        this.cacheService = cacheService;
        this.db = db;
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable Long id) {
        User user = cacheService.getById(id);
        return user == null ? "user " + id + " not found" : user;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestParam String name) {
        User user = new User(id, name, "updated@example.com");
        cacheService.updateUser(user);
        return "updated: " + user;
    }

    /** 回源计数器：证明缓存命中时 DB 零访问 */
    @GetMapping("/db-hits")
    public String dbHits() {
        return "DB hit count = " + db.getDbHitCount();
    }

    @PostMapping("/db-hits/reset")
    public String resetDbHits() {
        db.resetDbHitCount();
        return "reset";
    }
}
