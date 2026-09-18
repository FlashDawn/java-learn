FROM alpine:3.20
# 教学注释：每一个指令都会产生一个新的只读层
COPY hello.txt /app/hello.txt
# CMD 是容器启动时的默认命令，可被 docker run 后面的参数覆盖
CMD ["cat", "/app/hello.txt"]
