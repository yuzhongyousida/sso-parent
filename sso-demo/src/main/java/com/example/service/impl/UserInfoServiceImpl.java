package com.example.service.impl;

import com.example.dao.UserInfoMapper;
import com.example.entity.UserInfo;
import com.example.service.UserInfoService;
import com.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author wangteng05
 * @description: UserInfoService类
 * @date 2021/10/26 21:09
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 新增用户
     * @param userInfo
     * @return
     */
    public Integer addUser(UserInfo userInfo){
        if (userInfo.getId()!=null){
            userInfo.setId(null);
        }

        return userInfoMapper.insertSelective(userInfo);
    }



    /**
     * 根据条件查询userList
     * @param userInfo
     * @return
     */
    public List<UserInfo> getUserList(UserInfo userInfo){
        if (userInfo==null){
            return null;
        }
        return userInfoMapper.getUserList(userInfo);
    }

}
