# Java 后端技术栈快速上手学习计划

> **学习目标：** 8 周内建立完整的 Java 后端研发能力，能够独立使用 Spring Boot 开发业务服务，并逐步掌握 Redis、MQ、微服务、Docker、K8s、JVM/G1 等企业级技术。
>
> **学习方式：** 项目驱动 + LLM Pair Programming + Vibe Coding
>
> **核心原则：**
>
> - 不追求一次性学完所有 Java 知识
> - 不采用“看完视频再做项目”的线性学习方式
> - 每学习一个技术，立即写 Demo
> - 每个 Demo 最终接入综合项目
> - LLM 可以写代码，但自己必须理解代码
> - 优先掌握“会用 + 会排错”，再深入底层原理
>
> **建议投入：**
>
> - 每天 3～4 小时：8～10 周
> - 每天 6～8 小时：6～8 周
>
> ---
>
> ## 一、最终技术栈
>
> ```text
> Java
> ├── Java 基础
> ├── 集合
> ├── 泛型
> ├── Lambda / Stream
> ├── 多线程
> └── JVM / G1
>
> Spring Boot
> ├── IoC / DI
> ├── AOP
> ├── Web
> ├── Validation
> ├── Exception Handling
> ├── Transaction
> └── Configuration
>
> 数据层
> ├── PostgreSQL
> ├── Spring Data JPA
> ├── MyBatis
> └── MyBatis-Plus
>
> 中间件
> ├── Redis
> ├── RabbitMQ
> └── RocketMQ
>
> 分布式
> ├── Nacos
> ├── ZooKeeper
> ├── Apollo
> ├── Spring Cloud
> └── Dubbo
>
> 基础设施
> ├── Linux
> ├── Docker
> └── Kubernetes
> ```
>
> ---
>
> # 二、学习方法
>
> ## 2.1 LLM 不只是代码生成器
>
> 将 LLM 定位为：
>
> ```text
> 技术导师
>     +
> Pair Programmer
>     +
> Debugger
>     +
> Code Reviewer
>     +
> 架构顾问
> ```
>
> 每学习一个知识点，都执行：
>
> ```text
> 概念理解
>     ↓
> 最小 Demo
>     ↓
> 自己运行
>     ↓
> 修改代码
>     ↓
> 故意制造 Bug
>     ↓
> LLM Debug
>     ↓
> 自己解释原因
>     ↓
> 接入综合项目
>     ↓
> Code Review
> ```
>
> ---
>
> ## 2.2 推荐的 LLM Prompt 模板
>
> ### 学习概念
>
> ```text
> 我正在学习 Java 后端开发。
>
> 当前学习主题是：XXX。
>
> 请按照：
> 1. 概念
> 2. 为什么需要它
> 3. 工作原理
> 4. 最小 Java Demo
> 5. Spring Boot 中的实际使用
> 6. 常见错误
> 7. 生产环境注意事项
>
> 的顺序进行讲解。
>
> 不要一次引入过多其他技术。
> ```
>
> ### Vibe Coding
>
> ```text
> 请作为我的 Java Senior Engineer Pair Programmer。
>
> 使用 Java 17/21 + Spring Boot 3.x + Maven。
>
> 帮我实现 XXX 功能。
>
> 要求：
> 1. 给出完整代码
> 2. 解释关键代码
> 3. 解释设计原因
> 4. 指出线程安全问题
> 5. 指出事务边界
> 6. 指出异常场景
> 7. 给出测试代码
> 8. 最后进行一次 Code Review
> ```
>
> ### Debug
>
> ```text
> 下面是我的 Java/Spring Boot 代码以及错误日志。
>
> 不要直接给出修改后的代码。
>
> 请先：
> 1. 判断问题属于哪一层
> 2. 分析可能原因
> 3. 给出排查路径
> 4. 指出最可能的根因
> 5. 最后再给出修复方案
> ```
>
> ---
>
> # 三、综合项目
>
> 整个 8 周学习过程中维护一个统一项目：
>
> ## 分布式任务/数据处理平台
>
> 项目目标：
>
> ```text
> 用户
>   ↓
> API Gateway
>   ↓
> Nacos
>   ↓
> ┌──────────────┬──────────────┬──────────────┐
> │ User Service │ Task Service │ Data Service │
> └──────┬───────┴──────┬───────┴──────┬───────┘
>        │               │              │
>        │             Redis            │
>        │                              │
>        └──────────────┬───────────────┘
>                       ↓
>                  PostgreSQL
>                       ↓
>                  RabbitMQ
>                       ↓
>                Worker Service
> ```
>
> 后续逐渐加入：
>
> ```text
> Apollo
> ZooKeeper
> RocketMQ
> Dubbo
> Docker
> Kubernetes
> JVM / G1
> ```
>
> ### 项目功能
>
> 第一阶段：
>
> - 用户注册
> - 用户登录
> - 用户信息管理
> - 任务创建
> - 任务查询
> - 任务状态管理
>
> 第二阶段：
>
> - Redis 缓存
> - 异步任务
> - MQ 消息
> - Worker
> - 失败重试
>
> 第三阶段：
>
> - 微服务拆分
> - 服务注册发现
> - 配置中心
> - RPC
>
> 第四阶段：
>
> - Docker 化
> - K8s 部署
> - JVM 监控
> - G1 调优
>
> ---
>
> # 四、第 1 周：Java 核心 + 多线程
>
> ## Day 1：Java 开发环境
>
> ### 学习
>
> - JDK
> - JVM / JRE / JDK
> - Java 编译过程
> - `.java`
> - `.class`
> - Maven
> - IntelliJ IDEA
> - Git
>
> ### 实践
>
> 创建第一个 Maven 项目：
>
> ```text
> java-learning/
> ├── pom.xml
> └── src/
>     └── main/
>         └── java/
> ```
>
> 完成：
>
> - Hello World
> - Maven dependency
> - Maven build
> - Git 初始化
>
> ### 验收
>
> - 能独立创建 Maven Java 项目
> - 理解 `pom.xml`
> - 能执行 `mvn clean package`
>
> ---
>
> ## Day 2：Java 面向对象
>
> 学习：
>
> - Class
> - Object
> - Constructor
> - Encapsulation
> - Inheritance
> - Polymorphism
> - Interface
> - Abstract Class
> - static
> - final
>
> 实践：
>
> ```text
> User
> Order
> Task
> ```
>
> 建立简单领域模型。
>
> ---
>
> ## Day 3：集合
>
> 重点：
>
> ```text
> ArrayList
> LinkedList
> HashMap
> HashSet
> TreeMap
> Queue
> Deque
> ConcurrentHashMap
> ```
>
> 重点理解：
>
> - HashMap 原理
> - hash
> - 冲突
> - 扩容
> - 负载因子
> - 红黑树
>
> 实践：
>
> - 用户列表
> - 用户 Map
> - 根据 ID 查找用户
> - 集合排序
> - 去重
>
> ---
>
> ## Day 4：泛型 + Lambda + Stream
>
> 学习：
>
> - Generic
> - Lambda
> - Functional Interface
> - Stream
> - Optional
>
> 重点掌握：
>
> ```java
> filter
> map
> flatMap
> sorted
> collect
> groupingBy
> reduce
> ```
>
> 实践：
>
> 对任务列表进行：
>
> - 筛选
> - 排序
> - 分组
> - 聚合
>
> ---
>
> ## Day 5：异常 + IO + 注解 + 反射
>
> 学习：
>
> - Exception
> - RuntimeException
> - try/catch
> - 自定义异常
> - Annotation
> - Reflection
>
> 重点理解：
>
> ```text
> @Override
> @Deprecated
> @SuppressWarnings
> ```
>
> 并了解 Spring 为什么大量依赖：
>
> ```text
> Annotation + Reflection
> ```
>
> ---
>
> ## Day 6：多线程基础
>
> 学习：
>
> - Thread
> - Runnable
> - Callable
> - Future
> - FutureTask
> - synchronized
> - volatile
>
> 实践：
>
> 创建 10 个线程同时处理任务。
>
> 观察：
>
> - Race Condition
> - Visibility
> - Atomicity
>
> ---
>
> ## Day 7：线程池
>
> 重点学习：
>
> ```text
> ExecutorService
> ThreadPoolExecutor
> BlockingQueue
> RejectedExecutionHandler
> CompletableFuture
> ```
>
> 重点理解：
>
> ```text
> corePoolSize
> maximumPoolSize
> workQueue
> keepAliveTime
> rejectionPolicy
> ```
>
> 实践：
>
> 创建任务线程池。
>
> 要求：
>
> - 任务提交
> - 并发执行
> - 超载
> - 拒绝策略
> - 异常处理
>
> ### 第一周验收
>
> 必须能够回答：
>
> 1. HashMap 为什么线程不安全？
> 2. ConcurrentHashMap 怎么解决？
> 3. synchronized 和 Lock 有什么区别？
> 4. volatile 能保证什么？
> 5. ThreadPoolExecutor 有哪些核心参数？
> 6. 为什么不能无限创建线程？
> 7. CompletableFuture 如何实现异步任务？
>
> ---
>
> # 五、第 2 周：Spring Boot
>
> ## Day 8：Spring IoC
>
> 学习：
>
> - Spring Container
> - Bean
> - IoC
> - DI
> - Component Scan
>
> 注解：
>
> ```text
> @Component
> @Service
> @Repository
> @Controller
> @RestController
> @Autowired
> ```
>
> 重点：
>
> ```text
> Bean 是怎么创建的？
> Bean 是怎么注入的？
> Bean 生命周期是什么？
> ```
>
> ---
>
> ## Day 9：Spring AOP
>
> 学习：
>
> - AOP
> - Proxy
> - Pointcut
> - Advice
> - Aspect
>
> 实践：
>
> 实现：
>
> ```text
> API 日志切面
> ```
>
> 记录：
>
> - 请求
> - 参数
> - 执行时间
> - 异常
>
> ---
>
> ## Day 10：Spring Boot Web
>
> 学习：
>
> - Controller
> - RequestMapping
> - GET
> - POST
> - PUT
> - DELETE
> - RequestBody
> - PathVariable
> - RequestParam
>
> 实践：
>
> ```text
> /users
> /tasks
> ```
>
> 实现 REST API。
>
> ---
>
> ## Day 11：参数校验 + 异常处理
>
> 学习：
>
> ```text
> @Valid
> @NotNull
> @NotBlank
> @Size
> @Pattern
> ```
>
> 实现：
>
> ```text
> GlobalExceptionHandler
> ```
>
> 建立统一错误格式：
>
> ```json
> {
>   "code": 400,
>   "message": "Invalid parameter",
>   "data": null
> }
> ```
>
> ---
>
> ## Day 12：Spring Configuration
>
> 学习：
>
> - application.yml
> - profiles
> - Environment
> - ConfigurationProperties
> - Bean Configuration
>
> 建立：
>
> ```text
> application.yml
> application-dev.yml
> application-test.yml
> application-prod.yml
> ```
>
> ---
>
> ## Day 13：事务
>
> 重点：
>
> ```text
> @Transactional
> ```
>
> 理解：
>
> - Transaction
> - Commit
> - Rollback
> - Isolation
> - Propagation
>
> 重点理解：
>
> > 为什么 `@Transactional` 有时候会失效？
>
> ---
>
> ## Day 14：Spring Boot 项目第一版
>
> 完成：
>
> ```text
> User
> Task
> ```
>
> API：
>
> ```text
> POST   /users
> GET    /users/{id}
> GET    /users
> PUT    /users/{id}
> DELETE /users/{id}
>
> POST   /tasks
> GET    /tasks/{id}
> GET    /tasks
> PUT    /tasks/{id}/status
> ```
>
> ### 第二周验收
>
> 能够独立创建：
>
> ```text
> Spring Boot
> + REST API
> + 参数校验
> + 全局异常
> + 日志
> + Transaction
> ```
>
> ---
>
> # 六、第 3 周：PostgreSQL + ORM
>
> ## Day 15：SQL
>
> 学习：
>
> ```text
> SELECT
> INSERT
> UPDATE
> DELETE
> JOIN
> GROUP BY
> HAVING
> ORDER BY
> LIMIT
> ```
>
> ---
>
> ## Day 16：高级 SQL
>
> 学习：
>
> - Subquery
> - CTE
> - Window Function
> - CASE WHEN
> - UNION
>
> ---
>
> ## Day 17：数据库设计
>
> 学习：
>
> - Primary Key
> - Foreign Key
> - Unique
> - NOT NULL
> - Normalization
> - Denormalization
>
> 设计：
>
> ```text
> user
> task
> task_log
> ```
>
> ---
>
> ## Day 18：Index
>
> 学习：
>
> - B-Tree
> - Composite Index
> - Covering Index
> - Index Selectivity
>
> 重点：
>
> ```sql
> EXPLAIN
> EXPLAIN ANALYZE
> ```
>
> 实践：
>
> 对任务查询建立索引并比较查询性能。
>
> ---
>
> ## Day 19：Spring Data JPA
>
> 学习：
>
> ```text
> Entity
> Repository
> JpaRepository
> Query
> Relationship
> Transaction
> ```
>
> 实现：
>
> ```text
> UserEntity
> TaskEntity
> TaskLogEntity
> ```
>
> ---
>
> ## Day 20：MyBatis
>
> 学习：
>
> ```text
> Mapper
> XML
> ResultMap
> Dynamic SQL
> ```
>
> 对比：
>
> ```text
> JPA
> vs
> MyBatis
> ```
>
> ---
>
> ## Day 21：MyBatis-Plus
>
> 学习：
>
> ```text
> BaseMapper
> QueryWrapper
> LambdaQueryWrapper
> Pagination
> UpdateWrapper
> ```
>
> 项目中完成：
>
> ```text
> Task CRUD
> ```
>
> ### 第三周验收
>
> 必须理解：
>
> - ORM 是什么
> - JPA 做什么
> - MyBatis 做什么
> - MyBatis-Plus 解决什么问题
> - Index 为什么有效
> - EXPLAIN 怎么看
> - Transaction 为什么重要
>
> ---
>
> # 七、第 4 周：Redis
>
> ## Day 22：Redis 基础
>
> 学习：
>
> ```text
> String
> Hash
> List
> Set
> ZSet
> Stream
> ```
>
> 理解：
>
> - Redis 为什么快
> - 内存数据库
> - 单线程模型的历史原因
> - I/O Multiplexing
>
> ---
>
> ## Day 23：Spring Data Redis
>
> 实现：
>
> ```text
> RedisTemplate
> StringRedisTemplate
> ```
>
> 完成：
>
> ```text
> User Cache
> Task Cache
> ```
>
> ---
>
> ## Day 24：缓存设计
>
> 学习：
>
> ```text
> Cache Aside
> Read Through
> Write Through
> ```
>
> 项目采用：
>
> ```text
> Cache Aside
> ```
>
> 流程：
>
> ```text
> Request
>   ↓
> Redis
>   ↓ miss
> PostgreSQL
>   ↓
> Redis
> ```
>
> ---
>
> ## Day 25：Redis 分布式锁
>
> 学习：
>
> ```text
> SET NX EX
> ```
>
> 理解：
>
> - Lock
> - Unlock
> - TTL
> - Lock Ownership
> - Expiration
>
> 实践：
>
> 防止多个 Worker 同时处理同一个 Task。
>
> ---
>
> ## Day 26：缓存问题
>
> 必须理解：
>
> ```text
> Cache Penetration
> Cache Breakdown
> Cache Avalanche
> Hot Key
> Big Key
> ```
>
> 分别设计解决方案。
>
> ---
>
> ## Day 27：Redis 数据结构实战
>
> 实现：
>
> ```text
> 热门任务排行榜
> 在线用户
> 用户任务计数
> 任务状态缓存
> ```
>
> ---
>
> ## Day 28：Redis 综合实战
>
> 将 Redis 正式接入综合项目。
>
> ### 第四周验收
>
> 能够回答：
>
> 1. Redis 为什么快？
> 2. String / Hash / Set / ZSet 分别适合什么？
> 3. 什么是缓存穿透？
> 4. 什么是缓存击穿？
> 5. 什么是缓存雪崩？
> 6. Redis 分布式锁有什么坑？
> 7. 数据库和缓存不一致怎么办？
>
> ---
>
> # 八、第 5 周：RabbitMQ + RocketMQ
>
> ## Day 29：消息队列基础
>
> 学习：
>
> ```text
> Producer
> Consumer
> Queue
> Message
> Broker
> ```
>
> 理解：
>
> ```text
> 同步
> vs
> 异步
> ```
>
> ---
>
> ## Day 30：RabbitMQ
>
> 学习：
>
> ```text
> Exchange
> Queue
> Binding
> Routing Key
> ```
>
> Exchange：
>
> ```text
> Direct
> Fanout
> Topic
> ```
>
> ---
>
> ## Day 31：RabbitMQ 可靠性
>
> 学习：
>
> ```text
> ACK
> NACK
> Retry
> Dead Letter Queue
> Prefetch
> ```
>
> 实现：
>
> ```text
> Task Created
>      ↓
> RabbitMQ
>      ↓
> Worker
> ```
>
> ---
>
> ## Day 32：消息可靠性
>
> 重点：
>
> ```text
> At-most-once
> At-least-once
> Exactly-once
> ```
>
> 重点理解：
>
> > MQ 重复消息为什么无法简单避免？
>
> 学习：
>
> ```text
> Idempotency
> Deduplication
> Retry
> Transaction
> ```
>
> ---
>
> ## Day 33：RocketMQ
>
> 学习：
>
> ```text
> Topic
> Producer
> Consumer
> Consumer Group
> Offset
> Retry
> DLQ
> ```
>
> ---
>
> ## Day 34：RocketMQ 高级特性
>
> 学习：
>
> - Delay Message
> - Transaction Message
> - Ordered Message
> - Consumer Retry
>
> ---
>
> ## Day 35：MQ 综合实战
>
> 项目实现：
>
> ```text
> 创建任务
>      ↓
> PostgreSQL
>      ↓
> MQ
>      ↓
> Worker
>      ↓
> 执行任务
>      ↓
> 更新状态
> ```
>
> ### 第五周验收
>
> 必须理解：
>
> - MQ 为什么存在
> - MQ 如何削峰
> - MQ 如何异步解耦
> - ACK
> - Retry
> - DLQ
> - 消息幂等
> - 消息丢失
> - 消息重复
>
> ---
>
> # 九、第 6 周：微服务
>
> ## Day 36：微服务基础
>
> 学习：
>
> ```text
> Monolith
> vs
> Microservice
> ```
>
> 理解：
>
> - 服务拆分
> - 服务间通信
> - 服务发现
> - 配置中心
> - 负载均衡
>
> ---
>
> ## Day 37：Nacos
>
> 学习：
>
> ```text
> Service Registration
> Service Discovery
> Health Check
> Configuration
> ```
>
> 项目：
>
> ```text
> user-service
> task-service
> data-service
> ```
>
> 注册到 Nacos。
>
> ---
>
> ## Day 38：Spring Cloud
>
> 学习：
>
> - Gateway
> - OpenFeign
> - Load Balancing
> - Circuit Breaker
>
> 建立：
>
> ```text
> Client
>   ↓
> Gateway
>   ↓
> Nacos
>   ↓
> Service
> ```
>
> ---
>
> ## Day 39：Dubbo
>
> 学习：
>
> ```text
> Provider
> Consumer
> Registry
> RPC
> Serialization
> Load Balance
> ```
>
> 实现：
>
> ```text
> Task Service
>      ↓ RPC
> Worker Service
> ```
>
> ---
>
> ## Day 40：ZooKeeper
>
> 学习：
>
> ```text
> ZNode
> Session
> Watcher
> Leader
> Follower
> Quorum
> ```
>
> 理解：
>
> - 服务发现
> - 分布式协调
> - Leader Election
> - Distributed Lock
>
> 不要求第一阶段深入源码。
>
> ---
>
> ## Day 41：Apollo
>
> 学习：
>
> ```text
> Namespace
> Environment
> Cluster
> Configuration
> Gray Release
> Dynamic Configuration
> ```
>
> ---
>
> ## Day 42：微服务综合
>
> 最终结构：
>
> ```text
>                    Gateway
>                       │
>                     Nacos
>                       │
>       ┌───────────────┼───────────────┐
>       ↓               ↓               ↓
>   User Service   Task Service    Data Service
>       │               │               │
>       └───────────────┼───────────────┘
>                       │
>                  PostgreSQL
>                       │
>                     Redis
>                       │
>                       MQ
>                       │
>                    Worker
> ```
>
> ### 第六周验收
>
> 能解释：
>
> ```text
> Nacos
> ZooKeeper
> Apollo
> Spring Cloud
> Dubbo
> ```
>
> 的职责区别。
>
> 并能够独立完成：
>
> ```text
> 服务注册
> 服务发现
> 服务调用
> 配置管理
> RPC
> ```
>
> ---
>
> # 十、第 7 周：Linux + Docker
>
> ## Day 43：Linux 基础
>
> 必须熟练：
>
> ```bash
> pwd
> ls
> cd
> cp
> mv
> rm
> mkdir
> cat
> less
> head
> tail
> grep
> find
> awk
> sed
> ```
>
> ---
>
> ## Day 44：Linux 进程和资源
>
> 学习：
>
> ```bash
> ps
> top
> kill
> free
> df
> du
> ```
>
> Java：
>
> ```bash
> jps
> jstack
> jmap
> jstat
> ```
>
> ---
>
> ## Day 45：网络
>
> 学习：
>
> ```bash
> curl
> wget
> ping
> netstat
> ss
> ```
>
> 理解：
>
> ```text
> IP
> Port
> TCP
> HTTP
> DNS
> ```
>
> ---
>
> ## Day 46：Docker
>
> 学习：
>
> ```text
> Image
> Container
> Dockerfile
> Volume
> Network
> Registry
> ```
>
> ---
>
> ## Day 47：Docker Spring Boot
>
> 将 Spring Boot 项目 Docker 化。
>
> ```text
> Java
> ↓
> Maven
> ↓
> JAR
> ↓
> Docker Image
> ↓
> Container
> ```
>
> ---
>
> ## Day 48：Docker Compose
>
> 一次启动：
>
> ```text
> Spring Boot
> PostgreSQL
> Redis
> RabbitMQ
> Nacos
> ```
>
> 使用：
>
> ```bash
> docker compose up
> ```
>
> ---
>
> ## Day 49：生产部署模拟
>
> 模拟：
>
> ```text
> Linux Server
>      ↓
> Docker
>      ↓
> Spring Boot
>      ↓
> PostgreSQL
>      ↓
> Redis
>      ↓
> MQ
> ```
>
> ---
>
> ## Day 50：故障排查
>
> 人为制造：
>
> ```text
> Port 冲突
> Container Crash
> Database Connection Failed
> Redis Connection Failed
> MQ Connection Failed
> Out Of Memory
> ```
>
> 使用 Linux + Docker 命令排查。
>
> ### 第七周验收
>
> 能够：
>
> - SSH Linux
> - 查看进程
> - 查看日志
> - 查看资源
> - 查看网络
> - 构建 Docker Image
> - 启动 Container
> - 查看 Container Log
> - 进入 Container
> - 使用 Docker Compose
>
> ---
>
> # 十一、第 8 周：Kubernetes + JVM/G1
>
> ## Day 51：Kubernetes 基础
>
> 学习：
>
> ```text
> Cluster
> Node
> Pod
> Container
> ```
>
> ---
>
> ## Day 52：Deployment + Service
>
> 学习：
>
> ```text
> Deployment
> ReplicaSet
> Pod
> Service
> ```
>
> 理解：
>
> ```text
> Deployment
>      ↓
> ReplicaSet
>      ↓
> Pod
> ```
>
> ---
>
> ## Day 53：ConfigMap + Secret
>
> 学习：
>
> ```text
> ConfigMap
> Secret
> Namespace
> Environment Variable
> ```
>
> ---
>
> ## Day 54：Ingress + Volume
>
> 学习：
>
> ```text
> Ingress
> Volume
> PersistentVolume
> PersistentVolumeClaim
> ```
>
> ---
>
> ## Day 55：kubectl
>
> 必须熟悉：
>
> ```bash
> kubectl get pods
> kubectl get svc
> kubectl get deployment
> kubectl describe pod
> kubectl logs
> kubectl exec
> kubectl apply
> kubectl delete
> ```
>
> ---
>
> ## Day 56：部署 Spring Boot
>
> 将：
>
> ```text
> User Service
> Task Service
> Worker Service
> ```
>
> 部署到 K8s。
>
> 实现：
>
> ```text
> Deployment
> +
> Service
> +
> ConfigMap
> +
> Secret
> ```
>
> ---
>
> # 十二、JVM + G1 专项学习
>
> JVM 不要求一天学完。
>
> 建议贯穿整个 8 周，最后集中深入。
>
> ---
>
> ## JVM 基础
>
> 学习：
>
> ```text
> Class Loader
> Runtime Data Area
> Execution Engine
> ```
>
> Runtime Data Area：
>
> ```text
> Heap
> Stack
> Metaspace
> PC Register
> Direct Memory
> ```
>
> ---
>
> ## GC
>
> 学习：
>
> ```text
> GC Roots
> Reachability Analysis
> Minor GC
> Major GC
> Full GC
> STW
> ```
>
> ---
>
> ## G1
>
> 重点：
>
> ```text
> Region
> Young Region
> Old Region
> Humongous Object
> Remembered Set
> Concurrent Marking
> Mixed GC
> ```
>
> 理解：
>
> ```text
> Heap
> ┌────┬────┬────┬────┬────┬────┐
> │ R1 │ R2 │ R3 │ R4 │ R5 │ R6 │
> ├────┼────┼────┼────┼────┼────┤
> │ R7 │ R8 │ R9 │... │    │    │
> └────┴────┴────┴────┴────┴────┘
> ```
>
> G1 根据 Region 状态选择回收收益较高的区域。
>
> ---
>
> ## JVM 参数
>
> 重点掌握：
>
> ```text
> -Xms
> -Xmx
> -Xss
> -XX:+UseG1GC
> -XX:MaxGCPauseMillis
> -Xlog:gc*
> ```
>
> ---
>
> ## JVM 工具
>
> 掌握：
>
> ```bash
> jps
> jstack
> jmap
> jstat
> ```
>
> 以及：
>
> ```text
> JFR
> GC Log
> Heap Dump
> Thread Dump
> ```
>
> ---
>
> # 十三、最终综合项目
>
> ## 目标架构
>
> ```text
>                         Client
>                           │
>                           ▼
>                      API Gateway
>                           │
>                           ▼
>                         Nacos
>                           │
>             ┌─────────────┼─────────────┐
>             │             │             │
>             ▼             ▼             ▼
>       User Service   Task Service   Data Service
>             │             │             │
>             │             ▼             │
>             │           Redis           │
>             │                           │
>             └────────────┬──────────────┘
>                          ▼
>                     PostgreSQL
>                          │
>                          ▼
>                       MQ
>                          │
>             ┌────────────┴────────────┐
>             ▼                         ▼
>        Worker Service           Notification
> ```
>
> 基础设施：
>
> ```text
> Docker
>   ↓
> Kubernetes
> ```
>
> 配置：
>
> ```text
> Nacos / Apollo
> ```
>
> RPC：
>
> ```text
> Dubbo
> ```
>
> 协调：
>
> ```text
> ZooKeeper
> ```
>
> ---
>
> # 十四、综合项目功能清单
>
> ## 用户模块
>
> - 用户注册
> - 登录
> - 用户查询
> - 用户更新
> - 权限基础设计
>
> ## Task 模块
>
> - 创建任务
> - 查询任务
> - 修改任务
> - 删除任务
> - 修改任务状态
>
> 状态：
>
> ```text
> CREATED
>    ↓
> PENDING
>    ↓
> RUNNING
>    ↓
> SUCCESS
> ```
>
> 异常：
>
> ```text
> RUNNING
>    ↓
> FAILED
>    ↓
> RETRY
> ```
>
> ## Redis
>
> - 用户缓存
> - Task 缓存
> - 热点数据
> - 分布式锁
> - 排行榜
>
> ## MQ
>
> - Task Created
> - Task Execute
> - Task Finished
> - Task Failed
>
> ## 微服务
>
> ```text
> Gateway
> User Service
> Task Service
> Worker Service
> Data Service
> ```
>
> ---
>
> # 十五、项目最终需要解决的问题
>
> 不只是“功能跑起来”，而要主动制造工程问题。
>
> ## 1. 缓存一致性
>
> ```text
> PostgreSQL
>      ↕
>    Redis
> ```
>
> 思考：
>
> - 更新 DB 后什么时候删除 Cache？
> - 删除失败怎么办？
> - 并发更新怎么办？
>
> ---
>
> ## 2. MQ 重复消费
>
> ```text
> Message
>    ↓
> Consumer
>    ↓
> Crash
>    ↓
> Retry
> ```
>
> 思考：
>
> > 如何保证业务幂等？
>
> ---
>
> ## 3. MQ 消息丢失
>
> 思考：
>
> - Producer 发送失败
> - Broker 故障
> - Consumer 崩溃
>
> ---
>
> ## 4. 数据库连接池
>
> 思考：
>
> ```text
> 1000 requests
>       ↓
> DB Connection Pool
>       ↓
> PostgreSQL
> ```
>
> 为什么数据库连接不能无限创建？
>
> ---
>
> ## 5. 线程池耗尽
>
> 思考：
>
> ```text
> HTTP Requests
>       ↓
> Thread Pool
>       ↓
> Queue
>       ↓
> Rejection
> ```
>
> 如何监控和处理？
>
> ---
>
> ## 6. JVM OOM
>
> 人为制造：
>
> ```text
> OutOfMemoryError
> ```
>
> 然后使用：
>
> ```text
> Heap Dump
> jmap
> GC Log
> JFR
> ```
>
> 排查问题。
>
> ---
>
> # 十六、每周必须产出的东西
>
> 每周不能只留下“看过的视频”。
>
> ## Week 1
>
> ```text
> Java Demo
> ThreadPool Demo
> CompletableFuture Demo
> ```
>
> ## Week 2
>
> ```text
> Spring Boot CRUD
> ```
>
> ## Week 3
>
> ```text
> PostgreSQL Database
> JPA Demo
> MyBatis Demo
> ```
>
> ## Week 4
>
> ```text
> Redis Cache
> Redis Lock
> ```
>
> ## Week 5
>
> ```text
> RabbitMQ Demo
> RocketMQ Demo
> Async Worker
> ```
>
> ## Week 6
>
> ```text
> Microservices
> Nacos
> Spring Cloud
> Dubbo
> ```
>
> ## Week 7
>
> ```text
> Dockerfile
> docker-compose.yml
> Linux Deployment
> ```
>
> ## Week 8
>
> ```text
> Kubernetes YAML
> JVM Analysis
> G1 GC Analysis
> ```
>
> ---
>
> # 十七、技术掌握程度检查表
>
> ## Java
>
> - [ ] 熟悉 Java 基础语法
> - [ ] 熟悉集合
> - [ ] 理解 HashMap
> - [ ] 熟悉 Lambda / Stream
> - [ ] 理解异常体系
> - [ ] 理解泛型
> - [ ] 理解反射
>
> ## 多线程
>
> - [ ] Thread
> - [ ] Runnable
> - [ ] Callable
> - [ ] Future
> - [ ] synchronized
> - [ ] volatile
> - [ ] Lock
> - [ ] Atomic
> - [ ] ThreadPoolExecutor
> - [ ] CompletableFuture
>
> ## Spring Boot
>
> - [ ] IoC
> - [ ] DI
> - [ ] Bean
> - [ ] AOP
> - [ ] REST API
> - [ ] Validation
> - [ ] Exception Handling
> - [ ] Transaction
> - [ ] Configuration
>
> ## PostgreSQL
>
> - [ ] SQL
> - [ ] JOIN
> - [ ] CTE
> - [ ] Window Function
> - [ ] Index
> - [ ] Transaction
> - [ ] EXPLAIN
> - [ ] EXPLAIN ANALYZE
>
> ## ORM
>
> - [ ] JPA
> - [ ] MyBatis
> - [ ] MyBatis-Plus
> - [ ] 理解 ORM 优缺点
>
> ## Redis
>
> - [ ] String
> - [ ] Hash
> - [ ] List
> - [ ] Set
> - [ ] ZSet
> - [ ] Cache
> - [ ] Distributed Lock
> - [ ] Cache Penetration
> - [ ] Cache Breakdown
> - [ ] Cache Avalanche
>
> ## MQ
>
> - [ ] RabbitMQ
> - [ ] RocketMQ
> - [ ] ACK
> - [ ] Retry
> - [ ] DLQ
> - [ ] Idempotency
> - [ ] Message Reliability
>
> ## Microservices
>
> - [ ] Nacos
> - [ ] Spring Cloud
> - [ ] Gateway
> - [ ] OpenFeign
> - [ ] Dubbo
> - [ ] ZooKeeper
> - [ ] Apollo
>
> ## Linux
>
> - [ ] 文件操作
> - [ ] 进程管理
> - [ ] 日志分析
> - [ ] 网络排查
> - [ ] JVM 命令
>
> ## Docker
>
> - [ ] Image
> - [ ] Container
> - [ ] Dockerfile
> - [ ] Volume
> - [ ] Network
> - [ ] Docker Compose
>
> ## Kubernetes
>
> - [ ] Pod
> - [ ] Deployment
> - [ ] Service
> - [ ] ConfigMap
> - [ ] Secret
> - [ ] Ingress
> - [ ] Volume
> - [ ] kubectl
>
> ## JVM
>
> - [ ] JVM Memory
> - [ ] GC
> - [ ] G1
> - [ ] GC Log
> - [ ] JFR
> - [ ] Heap Dump
> - [ ] Thread Dump
>
> ---
>
> # 十八、最终能力标准
>
> 完成 8 周后，不以“看完多少课程”作为标准，而以以下能力作为验收：
>
> ### Level 1：能读
>
> 能看懂 Java/Spring Boot 项目。
>
> ### Level 2：能写
>
> 能独立完成 CRUD 和业务逻辑。
>
> ### Level 3：能集成
>
> 能接入：
>
> ```text
> PostgreSQL
> Redis
> MQ
> Nacos
> ```
>
> ### Level 4：能部署
>
> ```text
> Linux
> Docker
> K8s
> ```
>
> ### Level 5：能排错
>
> 能定位：
>
> ```text
> DB 慢
> Redis 慢
> MQ 堵塞
> Thread Pool 耗尽
> JVM GC 异常
> Pod Crash
> ```
>
> ### Level 6：能做技术决策
>
> 能回答：
>
> ```text
> 为什么用 Redis？
> 为什么用 MQ？
> 为什么用微服务？
> 为什么用 Dubbo？
> 为什么用 Spring Cloud？
> JPA 和 MyBatis 怎么选？
> Nacos 和 ZooKeeper 怎么选？
> 什么场景适合 RabbitMQ？
> 什么场景适合 RocketMQ？
> ```
>
> 这才意味着真正建立了 Java 后端工程能力。
>
> ---
>
> # 十九、每天的固定学习模板
>
> 建议以后每天固定按照这个节奏：
>
> ```text
> 30 min
> └── 理论学习
>
> 60 min
> └── LLM Pair Programming
>
> 60 min
> └── 实际编码
>
> 30 min
> └── Debug / 故障实验
>
> 30 min
> └── 总结 + Code Review
> ```
>
> 每天结束前回答 5 个问题：
>
> ```text
> 1. 今天学了什么？
> 2. 为什么需要这个技术？
> 3. 它解决什么问题？
> 4. 如果不用它怎么办？
> 5. 它在生产环境有什么坑？
> ```
>
> ---
>
> # 二十、最重要的学习原则
>
> ## 原则 1：不要追求“学完”
>
> Java 后端技术栈没有真正的“学完”。
>
> 第一阶段只追求：
>
> ```text
> 能开发
> +
> 能调试
> +
> 能部署
> ```
>
> ---
>
> ## 原则 2：不要让 LLM 替你思考
>
> 推荐：
>
> ```text
> 先自己思考
>     ↓
> 再问 LLM
>     ↓
> 对比答案
>     ↓
> 自己实现
> ```
>
> 而不是：
>
> ```text
> 问 LLM
> ↓
> 复制
> ↓
> 运行
> ↓
> 下一题
> ```
>
> ---
>
> ## 原则 3：每个技术必须进入项目
>
> ```text
> Redis
>       ↓
> Task Cache
>
> MQ
>       ↓
> Task Worker
>
> Nacos
>       ↓
> Service Discovery
>
> Dubbo
>       ↓
> Service RPC
>
> Docker
>       ↓
> Service Container
>
> K8s
>       ↓
> Service Deployment
>
> G1
>       ↓
> JVM Performance
> ```
>
> ---
>
> ## 原则 4：主动制造故障
>
> 真正的后端工程能力，很大一部分来自：
>
> ```text
> “系统为什么挂了？”
> ```
>
> 而不是：
>
> ```text
> “这个 API 怎么调用？”
> ```
>
> 所以项目中要主动制造：
>
> ```text
> DB Connection Timeout
> Redis Failure
> MQ Failure
> Duplicate Message
> Thread Pool Exhaustion
> Deadlock
> OOM
> GC Pause
> Pod Crash
> ```
>
> 然后使用工具解决。
>
> ---
>
> # 二十一、最终学习路径
>
> ```text
> Week 1
> Java + 多线程
>       ↓
> Week 2
> Spring Boot
>       ↓
> Week 3
> PostgreSQL + JPA + MyBatis
>       ↓
> Week 4
> Redis
>       ↓
> Week 5
> RabbitMQ + RocketMQ
>       ↓
> Week 6
> Nacos + Spring Cloud + Dubbo
>       ↓
> Week 7
> Linux + Docker
>       ↓
> Week 8
> Kubernetes + JVM/G1
>       ↓
>       ↓
> 综合项目
>       ↓
> 分布式后端研发能力
> ```
>
> **最终目标不是“掌握一堆 Java 技术”，而是能够从 0 构建、运行、部署、监控并排查一个真实的分布式 Java 后端系统。**

