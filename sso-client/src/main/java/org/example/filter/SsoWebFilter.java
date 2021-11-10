package org.example.filter;

import org.example.constant.ResultEnum;
import org.example.constant.SsoConfig;
import org.example.entity.SsoUser;
import org.example.helper.WebLoginHelper;
import org.example.util.AntPathMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对web端基于cookie接入方式的filter
 * @author wangteng05
 * @description: SsoWebFilter类
 * @date 2021/10/27 21:08
 */
public class SsoWebFilter extends HttpServlet implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SsoWebFilter.class);



    /**
     * 不进行登录验证的path（一般需要用户自己配置）
     */
    private String excludePaths;

    private List<String> excludePathList = new ArrayList<>();

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("-----------SsoWebFilter init start-----------");

        excludePaths = filterConfig.getInitParameter(SsoConfig.SSO_EXCLUDE_PATHS);

        // 解析excludePaths字符串
        String[] paths = null;
        if (excludePaths!=null && excludePaths.trim().length()>0){
            excludePaths = excludePaths.trim();
            paths = excludePaths.split("\\,");
        }
        if (paths!=null && paths.length>0){
            for (int i=0; i<paths.length; i++){
                excludePathList.add(paths[i]);
            }
        }

        LOGGER.info("-----------SsoWebFilter init end  -----------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 取出请求path
        HttpServletRequest request =(HttpServletRequest) servletRequest;
        HttpServletResponse response =(HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath().trim();

        // 校验是否与excludePaths中配置的path正则匹配
        if (excludePathList.size()>0 && excludePathList.contains(servletPath)){
            for (String excludePath: excludePathList) {
                excludePath = excludePath.trim();
                if (antPathMatcher.match(excludePath, servletPath)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        // 校验是否是logout请求，若是，则处理登出逻辑
        if (SsoConfig.SSO_LOGOUT.equals(servletPath)) {
            // 登出的一些cookie和session信息的清理
            WebLoginHelper.logout(request, response);

            // redirect到sso-server端的logout请求
            String logoutPageUrl = SsoConfig.SSO_SERVER_URL.concat(SsoConfig.SSO_LOGOUT);
            response.sendRedirect(logoutPageUrl);
            return;
        }

        // 检测登录状态
        SsoUser ssoUser = WebLoginHelper.loginCheck(request, response);

        // 未登录处理
        if (ssoUser == null) {
            String header = request.getHeader("content-type");
            boolean isJson = header!=null && header.contains("json");
            if (isJson) {
                // 异步请求处理
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().println("{\"code\":" + ResultEnum.FAIL.getCode()+", \"msg\":\""+ ResultEnum.FAIL.getDescription() +"\"}");
                return;
            } else {
                // 同步请求处理
                String link = request.getRequestURL().toString();

                // 直接跳转到登录页面去(参数中加上redirectUrl，方便登录成功后的回转)
                String loginPageUrl = SsoConfig.SSO_SERVER_URL.concat(SsoConfig.SSO_LOGIN)
                        + "?" + SsoConfig.REDIRECT_URL + "=" + link;

                response.sendRedirect(loginPageUrl);
                return;
            }

        }

        // 已登录，则将用户信息重新set到request中
        request.setAttribute(SsoConfig.SSO_USER, ssoUser);

        filterChain.doFilter(request, response);
        return;
    }




    public String getExcludePaths() {
        return excludePaths;
    }

    public void setExcludePaths(String excludePaths) {
        this.excludePaths = excludePaths;
    }
}
