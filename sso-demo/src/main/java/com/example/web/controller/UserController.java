package com.example.web.controller;

import com.example.constant.CommonConstant;
import com.example.entity.UserInfo;
import com.example.service.UserInfoService;
import com.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author wangteng05
 * @description: UserController类
 * @date 2021/10/26 20:53
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/addUser")
    public String addUserInfo(@RequestBody UserInfo userInfo){
        // 入参空校验
        if (!CommonUtil.checkParamBlank(userInfo,
                userInfo.getUserName(),
                userInfo.getSex(),
                userInfo.getAge(),
                userInfo.getPassWord())){
            return CommonConstant.FAIL;
        }

        // 创建人和创建时间属性设置
        userInfo.setCreateTime(new Date());
        userInfo.setCreateBy("wangteng05");

        // 新增
        Integer ret = userInfoService.addUser(userInfo);
        if(ret==null || ret<=0){
            return CommonConstant.FAIL;
        }

        return CommonConstant.SUCCESS;
    }

    /**
     * 根据条件查询userList
     * @param userName
     * @return
     */
    @RequestMapping("/getUsers")
    @ResponseBody
    public List<UserInfo> getUserInfo(String userName){
        // 入参空校验
        if (!CommonUtil.checkParamBlank(userName)){
            return null;
        }

        UserInfo dto = new UserInfo();
        dto.setUserName(userName);
        return userInfoService.getUserList(dto);
    }
}
