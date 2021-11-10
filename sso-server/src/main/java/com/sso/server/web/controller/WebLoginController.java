package com.sso.server.web.controller;

import com.sso.server.entity.UserInfo;
import com.sso.server.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.example.constant.SsoConfig;
import org.example.entity.SsoUser;
import org.example.helper.SsoSessionIdHelper;
import org.example.helper.WebLoginHelper;
import org.example.util.CookieUtil;
import org.example.util.LoginStoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @author wangteng05
 * @description: WebLoginController类
 * @date 2021/11/1 14:59
 */
@RestController
public class WebLoginController {

    @Autowired
    private UserInfoService userInfoService;


    /**
     * home 页
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response){
        SsoUser ssoUser = WebLoginHelper.loginCheck(request,response);

        if (ssoUser==null){
            return "redirect:login";
        } else {
            model.addAttribute(SsoConfig.SSO_USER, ssoUser);
            return "index";
        }
    }

    /**
     * 登录请求处理
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(SsoConfig.SSO_LOGIN)
    public String login(Model model, HttpServletRequest request, HttpServletResponse response){
        SsoUser ssoUser = WebLoginHelper.loginCheck(request,response);
        if (ssoUser != null) {
            String redirectUrl = request.getParameter(SsoConfig.REDIRECT_URL);
            if (StringUtils.isNotEmpty(redirectUrl)) {
                String sessionId = CookieUtil.getValue(request, SsoConfig.SSO_SESSIONID);
                String redirectUrlFinal = redirectUrl + "?" + SsoConfig.SSO_SESSIONID + "=" + sessionId;;

                return "redirect:" + redirectUrlFinal;
            } else {
                return "redirect:/";
            }
        }

        model.addAttribute("errorMsg", request.getParameter("errorMsg"));
        model.addAttribute(SsoConfig.REDIRECT_URL, request.getParameter(SsoConfig.REDIRECT_URL));
        return "login";
    }


    /**
     * 登出请求处理
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(SsoConfig.SSO_LOGOUT)
    public String login(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        WebLoginHelper.logout(request, response);

        redirectAttributes.addAttribute(SsoConfig.REDIRECT_URL, request.getParameter(SsoConfig.REDIRECT_URL));
        return "redirect:/login";
    }


    /**
     * 登录页面点击登录请求处理
     *
     * @param request
     * @param redirectAttributes
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request,
                          HttpServletResponse response,
                          RedirectAttributes redirectAttributes,
                          String username,
                          String password,
                          String ifRemember) {

        boolean ifRem = (ifRemember!=null && "on".equals(ifRemember))? true : false;

        // 根据用户名和密码查询用户信息
        UserInfo dto = new UserInfo();
        dto.setUserName(username);
        dto.setPassWord(password);
        List<UserInfo> userInfoList = userInfoService.getUserList(dto);

        if (CollectionUtils.isEmpty(userInfoList)) {
            redirectAttributes.addAttribute("errorMsg", "用户名或密码有误");
            redirectAttributes.addAttribute(SsoConfig.REDIRECT_URL, request.getParameter(SsoConfig.REDIRECT_URL));
            return "redirect:/login";
        }
        UserInfo userInfoVo = userInfoList.get(0);

        // 封装SsoUser对象
        SsoUser ssoUser = new SsoUser();
        ssoUser.setUserId(String.valueOf(userInfoVo.getId()));
        ssoUser.setUserName(userInfoVo.getUserName());
        ssoUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        ssoUser.setExpireMinute(LoginStoreUtil.REDIS_EXPIRE_MINUTE);
        ssoUser.setExpireFreshTime(System.currentTimeMillis());

        // 获取sessionId
        String sessionId = SsoSessionIdHelper.makeSessionId(ssoUser);

        // 登录操作
        WebLoginHelper.login(response, sessionId, ssoUser, ifRem);

        String redirectUrl = request.getParameter(SsoConfig.REDIRECT_URL);
        if (StringUtils.isNotEmpty(redirectUrl)) {
            String redirectUrlFinal = redirectUrl + "?" + SsoConfig.SSO_SESSIONID + "=" + sessionId;
            return "redirect:" + redirectUrlFinal;
        } else {
            return "redirect:/";
        }
    }

}
