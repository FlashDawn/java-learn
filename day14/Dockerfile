# Day47：把 Spring Boot 应用装进容器
# 链路：Java → Maven → JAR → Docker Image → Container
# 用法（仓库根目录或本目录执行均可）：
#   mvn -q -DskipTests package
#   docker build -t day14-api:1.0 .
#   docker run -d --name day14-app -p 8080:8080 day14-api:1.0

# ---- 运行基底：只装 JRE，不带 JDK，镜像更小、攻击面更小 ----
FROM eclipse-temurin:17-jre-alpine

# 教学点：容器内用非 root 用户运行是生产基线要求
RUN addgroup -S app && adduser -S app -G app

WORKDIR /app

# 构建上下文已由 .dockerignore 精简，只有 jar 会被 COPY 进来
COPY target/day14-api-0.0.1-SNAPSHOT.jar app.jar

# 换到非 root 用户
USER app

# 声明性文档：容器内应用监听 8080（真正生效靠 docker run -p 映射）
EXPOSE 8080

# JVM 容器感知：JDK 17 默认开启 UseContainerSupport，MaxRAMPercentage 按需调
ENTRYPOINT ["java", "-jar", "app.jar"]
