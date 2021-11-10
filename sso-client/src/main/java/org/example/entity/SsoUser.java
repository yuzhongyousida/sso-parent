package org.example.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wangteng05
 * @description: SsoUser类
 * @date 2021/10/28 15:48
 */
public class SsoUser implements Serializable {
    private static final long serialVersionUID = -8198098934588767449L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 版本号
     */
    private String version;

    /**
     * 过期时间（单位：分钟）
     */
    private int expireMinute;

    /**
     * 最近一次过期时间的刷新时间戳
     */
    private long expireFreshTime;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getExpireMinute() {
        return expireMinute;
    }

    public void setExpireMinute(int expireMinute) {
        this.expireMinute = expireMinute;
    }

    public long getExpireFreshTime() {
        return expireFreshTime;
    }

    public void setExpireFreshTime(long expireFreshTime) {
        this.expireFreshTime = expireFreshTime;
    }
}
