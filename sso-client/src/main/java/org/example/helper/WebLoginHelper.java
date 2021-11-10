package org.example.helper;

import org.example.constant.SsoConfig;
import org.example.entity.SsoUser;
import org.example.util.CookieUtil;
import org.example.util.LoginStoreUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangteng05
 * @description: WebLoginHelper类
 * @date 2021/10/28 16:09
 */
public class WebLoginHelper {


    /**
     * 登录
     * @param response
     * @param sessionId
     * @param ssoUser
     * @param ifRemember
     */
    public static void login(HttpServletResponse response,
                             String sessionId,
                             SsoUser ssoUser,
                             boolean ifRemember) {

        String userId = SsoSessionIdHelper.parseUserId(sessionId);
        if (userId == null) {
            throw new RuntimeException("parseStoreKey Fail, sessionId:" + sessionId);
        }

        LoginStoreUtil.put(userId, ssoUser);
        CookieUtil.set(response, SsoConfig.SSO_SESSIONID, sessionId, ifRemember);
    }

    /**
     * 登出
     * @param request
     * @param response
     */
    public static void logout(HttpServletRequest request,
                              HttpServletResponse response) {

        String cookieSessionId = CookieUtil.getValue(request, SsoConfig.SSO_SESSIONID);
        if (cookieSessionId==null) {
            return;
        }

        String userId = SsoSessionIdHelper.parseUserId(cookieSessionId);
        if (userId != null) {
            LoginStoreUtil.remove(userId);
        }

        CookieUtil.remove(request, response, SsoConfig.SSO_SESSIONID);
    }


    /**
     * 登录检查
     * @param request
     * @param response
     * @return
     */
    public static SsoUser loginCheck(HttpServletRequest request, HttpServletResponse response){
        String cookieSessionId = CookieUtil.getValue(request, SsoConfig.SSO_SESSIONID);

        // cookie中的user信息
        SsoUser ssoUser = TokenLoginHelper.loginCheck(cookieSessionId);
        if (ssoUser != null) {
            return ssoUser;
        }

        // 删除老cookie
        CookieUtil.remove(request, response, SsoConfig.SSO_SESSIONID);

        // 设置新cookie
        String paramSessionId = request.getParameter(SsoConfig.SSO_SESSIONID);
        ssoUser = TokenLoginHelper.loginCheck(paramSessionId);
        if (ssoUser != null) {
            CookieUtil.set(response, SsoConfig.SSO_SESSIONID, paramSessionId, false);
            return ssoUser;
        }

        return null;
    }



}
