# Day 8 配套代码（Spring IoC）

```powershell
cd d:\java_program\JAVA_learn\day8
mvn -q -DskipTests spring-boot:run
```

预期输出要点：

```text
lifecycle=@PostConstruct BeanLifecycleProbe
container=AnnotationConfigApplicationContext
injected-user=User{id='u-1', name='alice'}
injected-task=Task{...}
mvc-controller=AdminHintController
rest-controller=TaskQueryController
lifecycle-ready=true
lifecycle=@PreDestroy BeanLifecycleProbe
```

仅打包：`mvn -q -f day8/pom.xml -DskipTests package`
