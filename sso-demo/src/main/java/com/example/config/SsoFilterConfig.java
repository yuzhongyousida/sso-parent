package com.example.config;

import org.example.constant.SsoConfig;
import org.example.filter.SsoWebFilter;
import org.example.util.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangteng05
 * @description: SsoFilterConfig类
 * @date 2021/11/3 10:55
 */
@Configuration
public class SsoFilterConfig implements DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(SsoFilterConfig.class);

    @Bean
    public FilterRegistrationBean ssoFilterRegistrationBean(){
        LOGGER.info("-------------SsoFilter register start-------------");

        // 初始化redis
        JedisUtil.initJedisPool();

        // 注册SsoWebFilter
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("ssoWebFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        registration.setFilter(new SsoWebFilter());
        registration.addInitParameter(SsoConfig.SSO_EXCLUDE_PATHS, "/static/*,/api/*,/sso-server/*");

        LOGGER.info("-------------SsoFilter register end-------------");

        return registration;
    }


    @Override
    public void destroy() throws Exception {
        LOGGER.info("----------SsoFilterConfig destroy----------");
    }
}
