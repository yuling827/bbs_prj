package com.zx.bbsprj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement    //如果service的实现类中添加了事物，此处必须添加该注解用于启动事务管理
public class BbsPrjApplication {

    public static void main(String[] args) {
        SpringApplication.run(BbsPrjApplication.class, args);
    }
}
