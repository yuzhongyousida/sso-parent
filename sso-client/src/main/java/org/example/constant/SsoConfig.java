package org.example.constant;

/**
 * @author wangteng05
 * @description: SsoConfig类
 * @date 2021/10/27 21:26
 */
public class SsoConfig {

    /**
     * sso中央认证服务URL
     */
    public static final String SSO_SERVER_URL = "http://127.0.0.1:8080";

    /**
     * 不进行sso验证的paths配置值在header或cookie中对应的key值
     */
    public static final String SSO_EXCLUDE_PATHS = "sso_exclude_paths";

    /**
     * redis存储用户信息的key值前缀，以及header或cookie中sessionId的key值
     */
    public static final String SSO_SESSIONID = "example_sso_sessionid";

    /**
     * redirect url
     */
    public static final String REDIRECT_URL = "redirect_url";

    /**
     * header或cookie中user对象对应的key值
     */
    public static final String SSO_USER = "example_sso_user";

    /**
     * login path
     */
    public static final String SSO_LOGIN = "/login";

    /**
     * logout path
     */
    public static final String SSO_LOGOUT = "/logout";




}
