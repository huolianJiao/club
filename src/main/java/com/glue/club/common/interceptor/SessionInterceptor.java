package com.glue.club.common.interceptor;

import com.glue.club.common.constant.Const;
import com.glue.club.web.model.User;
import com.glue.club.web.service.NotificationService;
import com.glue.club.web.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * @author jiao
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;

    @Resource
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getCookies() != null) {
            Cookie token = Arrays.asList(request.getCookies()).stream().filter(c -> c.getName().equals("token")).findAny().orElse(null);
            if (token != null) {
                String tokenValue = token.getValue();
                User user = userService.findUserByToken(tokenValue);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute(Const.SITE_USER_SESSION_KEY, user);
                    Long unreadCount = notificationService.unreadCount(user.getId());
                    session.setAttribute("unreadCount", unreadCount);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
