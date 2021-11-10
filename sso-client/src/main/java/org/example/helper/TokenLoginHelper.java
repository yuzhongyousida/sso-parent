package org.example.helper;

import org.example.constant.SsoConfig;
import org.example.entity.SsoUser;
import org.example.util.LoginStoreUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangteng05
 * @description: SsoTokenHelper类
 * @date 2021/10/28 15:45
 */
public class TokenLoginHelper {

    /**
     * Token方式登录时，用户信息存储
     * @param sessionId
     * @param ssoUser
     */
    public static void login(String sessionId, SsoUser ssoUser) {

        String userId = SsoSessionIdHelper.parseUserId(sessionId);
        if (userId == null) {
            throw new RuntimeException("parseUserId Fail, sessionId:" + sessionId);
        }

        LoginStoreUtil.put(userId, ssoUser);
    }


    /**
     * Token方式登出时，用户信息清理
     * @param request
     */
    public static void logout(HttpServletRequest request) {
        String headerSessionId = request.getHeader(SsoConfig.SSO_SESSIONID);
        String userId = SsoSessionIdHelper.parseUserId(headerSessionId);
        if (userId == null) {
            return;
        }

        LoginStoreUtil.remove(userId);
    }

    /**
     * 登录状态检查
     *
     * @param request
     * @return
     */
    public static SsoUser loginCheck(HttpServletRequest request){
        String headerSessionId = request.getHeader(SsoConfig.SSO_SESSIONID);
        return loginCheck(headerSessionId);
    }

    /**
     * 登录状态检查
     * @param sessionId
     * @return
     */
    public static SsoUser loginCheck(String  sessionId){
        String userId = SsoSessionIdHelper.parseUserId(sessionId);
        if (userId == null) {
            return null;
        }

        SsoUser ssoUser = LoginStoreUtil.getSsoUser(userId);
        if (ssoUser != null) {
            String version = SsoSessionIdHelper.parseVersion(sessionId);
            if (ssoUser.getVersion().equals(version)) {
                // 过期时间如果过了一半，则自动刷新
                if ((System.currentTimeMillis() - ssoUser.getExpireFreshTime()) > ssoUser.getExpireMinute()/2) {
                    ssoUser.setExpireFreshTime(System.currentTimeMillis());
                    LoginStoreUtil.put(userId, ssoUser);
                }

                return ssoUser;
            }
        }
        return null;
    }


}
