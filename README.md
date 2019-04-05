#### hangs-config

##### 说明
```
1. 模仿去哪儿网 QConfig 的一个小玩具
2. 目前尚未成型
```

##### 技术栈
```
SpringCloud（服务框架）
SpringBoot（基础）
SpringSecurity（权限）
MyBatis（ORM）
MySQL（数据库）
Kafka（消息队列）：选择kafka的原因是在分布式配置中心中，消息的可达性并不是很重要，就是消息丢失，也不会影响到配置中心的正常使用
Redis（NoSQL）
Elasticsearch（搜索）
```

##### 模块说明
```
admin：hconfig上传，版本处理，配置推送，权限管理
server：集中管理配置文件，包括存储，推送，修改
client：加载，同步，解析并且使用字段
```
  
