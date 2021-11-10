package com.sso.server.web.resolver;

import com.sso.server.exception.SsoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 返回前端的统一异常处理的自定义渲染类
 */
@Component
public class WebExceptionResolver implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {

        LOGGER.error("WebExceptionResolver:{}", ex);

        ModelAndView mv = new ModelAndView();

        // 转换成页面上便于显示的格式
        String exceptionMessage = ex.toString().replaceAll("\n", "<br/>");

        boolean isJson = false;
        HandlerMethod method = (HandlerMethod)handler;
        ResponseBody responseBody = method.getMethodAnnotation(ResponseBody.class);
        if (responseBody != null) {
            isJson = true;
        }

        if (isJson) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter pWriter = null;
            try {
                pWriter = response.getWriter();
                pWriter.write("{\"exceptionMessag\":\"" + exceptionMessage + "\"}");
            } catch (IOException e) {
                LOGGER.error("returnJsonObject:" + e);
            } finally {
                if (pWriter != null) {
                    pWriter.flush();
                    pWriter.close();
                }
            }
        } else {
            mv.addObject("exceptionMessage", exceptionMessage);
            mv.setViewName("/common/common.exception");
        }

        return mv;
    }

}