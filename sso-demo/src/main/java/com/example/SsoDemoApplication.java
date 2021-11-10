package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangteng05
 * @description: Application类
 * @date 2021/10/26 20:57
 */
@MapperScan("com.example.dao")
@SpringBootApplication
public class SsoDemoApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SsoDemoApplication.class);
        application.run(args);
    }
}
