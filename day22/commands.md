# Day22 redis-cli 速查（与 Day22App 同一套 key）

```redis
SET task:t-1:status CREATED
GET task:t-1:status

HSET user:u-1 name Ada email ada@example.com
HGET user:u-1 name

RPUSH queue:tasks t-1 t-2 t-3
LPOP queue:tasks

SADD task:t-1:tags import urgent import
SCARD task:t-1:tags

ZADD board:priority 10 t-2 30 t-1 20 t-3
ZREVRANGE board:priority 0 0

XADD stream:task-events * taskId t-1 status CREATED
```
