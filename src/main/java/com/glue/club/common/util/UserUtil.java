package com.glue.club.common.util;

import com.glue.club.common.constant.Const;
import com.glue.club.web.model.User;

/**
 * Description:系统用户工具类
 *
 * @author Jin
 * @create 2017-05-31
 **/
public class UserUtil {

    public static User getUser() {
        User user =   ((User) ControllerUtil.getHttpSession().getAttribute(Const.SITE_USER_SESSION_KEY));
        return user;
    }
}
