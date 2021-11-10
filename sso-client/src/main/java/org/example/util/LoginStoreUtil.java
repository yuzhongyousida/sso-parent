package org.example.util;

import org.example.constant.SsoConfig;
import org.example.entity.SsoUser;

/**
 * @author wangteng05
 * @description: LoginStore类
 * @date 2021/10/28 17:48
 */
public class LoginStoreUtil {

    /**
     *  用户信息默认失效时间分钟：7天
     */
    public static int REDIS_EXPIRE_MINUTE = 7*24*60;



    /**
     * 保存用户信息
     * @param userId
     * @param ssoUser
     */
    public static void put(String userId, SsoUser ssoUser) {
        String redisKey = redisKey(userId);
        JedisUtil.setex(redisKey, ssoUser, REDIS_EXPIRE_MINUTE*60);
    }

    /**
     * 根据userId获取用户信息
     * @param userId
     * @return
     */
    public static SsoUser getSsoUser(String userId) {
        // 拼装redisKey
        String redisKey = redisKey(userId);

        // 从redis中取出user object
        Object objectValue = JedisUtil.getObj(redisKey);
        if (objectValue != null) {
            SsoUser ssoUser = (SsoUser) objectValue;
            return ssoUser;
        }
        return null;
    }

    /**
     * 根据userId删除用户信息
     * @param userId
     */
    public static void remove(String userId) {
        String redisKey = redisKey(userId);
        JedisUtil.delObj(redisKey);
    }

    /**
     * 拼装redis用户信息的存储key
     * @param userId
     * @return
     */
    private static String redisKey(String userId){
        return SsoConfig.SSO_SESSIONID.concat("#").concat(userId);
    }


}
