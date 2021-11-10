package com.example.service;

import com.example.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    /**
     * 新增用户
     * @param userInfo
     * @return
     */
    public Integer addUser(UserInfo userInfo);



    /**
     * 根据条件查询userList
     * @param userInfo
     * @return
     */
    public List<UserInfo> getUserList(UserInfo userInfo);

}
