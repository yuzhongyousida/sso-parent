package com.sso.server.service.impl;

import com.sso.server.dao.UserInfoMapper;
import com.sso.server.entity.UserInfo;
import com.sso.server.exception.AddUserException;
import com.sso.server.service.UserInfoService;
import com.sso.server.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (StringUtils.isEmpty(userInfo.getPassWord())){
            throw new AddUserException("AddUser Excepiton： passWord is null");
        }

        // 明文密码加密
        String pwd = MD5Util.getEncryptedPwd(userInfo.getPassWord());
        userInfo.setPassWord(pwd);

        return userInfoMapper.insertSelective(userInfo);
    }


    /**
     * 根据ID查询user信息
     * @return
     */
    public UserInfo getUserInfoById(Long id){
       return userInfoMapper.selectByPrimaryKey(id);
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
