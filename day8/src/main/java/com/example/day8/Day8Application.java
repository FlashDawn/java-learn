package com.example.day8;

import com.example.day8.domain.Task;
import com.example.day8.domain.User;
import com.example.day8.lifecycle.BeanLifecycleProbe;
import com.example.day8.service.TaskService;
import com.example.day8.service.UserService;
import com.example.day8.web.AdminHintController;
import com.example.day8.web.TaskQueryController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Day 8：Spring IoC / Container / Bean / DI / Component Scan。
 * 对照笔记 §1、§2。综合项目将来落 User Service / Task Service。
 * 本入口用 CommandLineRunner 打印注入结果，不启动 Web 端口。
 */
@SpringBootApplication
public class Day8Application implements CommandLineRunner {

    /** 构造器注入：Container 创建本 Bean 时把依赖填进来（DI） */
    private final UserService userService;
    private final TaskService taskService;
    private final TaskQueryController taskQueryController;
    private final AdminHintController adminHintController;
    private final BeanLifecycleProbe lifecycleProbe;
    private final ApplicationContext applicationContext;

    public Day8Application(UserService userService,
                           TaskService taskService,
                           TaskQueryController taskQueryController,
                           AdminHintController adminHintController,
                           BeanLifecycleProbe lifecycleProbe,
                           ApplicationContext applicationContext) {
        this.userService = userService;
        this.taskService = taskService;
        this.taskQueryController = taskQueryController;
        this.adminHintController = adminHintController;
        this.lifecycleProbe = lifecycleProbe;
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(Day8Application.class, args);
    }

    @Override
    public void run(String... args) {
        // ========== Container：从 ApplicationContext 按类型取 Bean ==========
        // 为什么：证明 IoC 容器已接管对象创建，业务代码不再 new UserService
        System.out.println("container=" + applicationContext.getClass().getSimpleName());
        System.out.println("beanCount=" + applicationContext.getBeanDefinitionCount());

        // ========== DI / @Autowired(构造器)：UserService 已注入 Repository ==========
        User alice = userService.register("u-1", "alice");
        System.out.println("injected-user=" + alice);

        // ========== @Service 协作：TaskService 依赖 UserService ==========
        Task task = taskService.createForUser("t-1", "import-orders", "u-1");
        System.out.println("injected-task=" + task);

        // ========== @Controller / @RestController：都是被扫描的 Bean ==========
        System.out.println("mvc-controller=" + adminHintController.getClass().getSimpleName());
        System.out.println("rest-controller=" + taskQueryController.getClass().getSimpleName());
        System.out.println("controller-find=" + taskQueryController.find("t-1"));

        // ========== Bean 生命周期：@PostConstruct 已在启动时执行 ==========
        System.out.println("lifecycle-ready=" + lifecycleProbe.isReady());
    }
}
