package org.example.helper;

import org.example.entity.SsoUser;

/**
 * @author wangteng05
 * @description: SsoSessionHelper类
 * @date 2021/10/28 15:45
 */
public class SsoSessionIdHelper {

    /**
     * 用户sessionId的生成
     * @param ssoUser
     * @return
     */
    public static String makeSessionId(SsoUser ssoUser){
        String sessionId = ssoUser.getUserId().concat("_").concat(ssoUser.getVersion());
        return sessionId;
    }

    /**
     * 从sessionId中解析userId
     * @param sessionId
     * @return
     */
    public static String parseUserId(String sessionId) {
        if (sessionId!=null && sessionId.indexOf("_")>-1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length==2
                    && sessionIdArr[0]!=null
                    && sessionIdArr[0].trim().length()>0) {
                return sessionIdArr[0].trim();
            }
        }
        return null;
    }

    /**
     * 从sessionId中解析version
     * @param sessionId
     * @return
     */
    public static String parseVersion(String sessionId) {
        if (sessionId!=null && sessionId.indexOf("_")>-1) {
            String[] sessionIdArr = sessionId.split("_");
            if (sessionIdArr.length==2
                    && sessionIdArr[1]!=null
                    && sessionIdArr[1].trim().length()>0) {
                return sessionIdArr[1].trim();
            }
        }
        return null;
    }


}
