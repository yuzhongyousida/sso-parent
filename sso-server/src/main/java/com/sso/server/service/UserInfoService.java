package com.sso.server.service;

import com.sso.server.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    /**
     * 新增用户
     * @param userInfo
     * @return
     */
    public Integer addUser(UserInfo userInfo);


    /**
     * 根据ID查询user信息
     * @return
     */
    public UserInfo getUserInfoById(Long id);


    /**
     * 根据条件查询userList
     * @param userInfo
     * @return
     */
    public List<UserInfo> getUserList(UserInfo userInfo);
}
