package com.sso.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangteng05
 * @description: Application类
 * @date 2021/10/26 20:57
 */
@MapperScan("com.sso.server.dao")
@SpringBootApplication
public class SsoServerApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SsoServerApplication.class);
        application.run(args);
    }
}
