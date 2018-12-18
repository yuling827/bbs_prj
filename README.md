# bbs_prj
A simple Spring Boot application for forums or blogs.

## 数据库配置

```properties
spring.datasource.druid.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.druid.url=jdbc\:mysql\://127.0.0.1\:3306/apdsp?useUnicode\=true&allowMultiQueries\=true&characterEncoding\=utf8
spring.datasource.druid.username=root
spring.datasource.druid.password=password
spring.datasource.druid.driver-class-name=com.mysql.jdbc.Driver
```

> 应用采用mysql数据库，根据自己的环境，修改数据库的用户名和密码，以及对应的schema(该schema要已创建，否则服务启动会报错)，代码中采用的schema为apdsp。

## 应用启动

> 执行启动类：BbsPrjApplication

