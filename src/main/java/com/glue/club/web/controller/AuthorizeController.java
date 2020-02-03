package com.glue.club.web.controller;

import com.glue.club.common.constant.Const;
import com.glue.club.common.util.ControllerUtil;
import com.glue.club.provider.GithubProvider;
import com.glue.club.web.dto.AccessTokenDTO;
import com.glue.club.web.dto.GithubUser;
import com.glue.club.web.model.User;
import com.glue.club.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author jiao
 * @date 日期
 */
@Controller
public class AuthorizeController {

    @Resource
    private GithubProvider githubProvider;

    @Value("${github.redirect.uri}")
    private String uri;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Resource
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(uri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            //写入cookie
            response.addCookie(new Cookie(Const.SITE_USER_TOKEN_KEY,user.getToken()));
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(){
        ControllerUtil.getHttpSession().removeAttribute(Const.SITE_USER_SESSION_KEY);
        Cookie cookie = new Cookie(Const.SITE_USER_TOKEN_KEY,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        ControllerUtil.getHttpServletResponse().addCookie(cookie);
        return "redirect:/";
    }

}
