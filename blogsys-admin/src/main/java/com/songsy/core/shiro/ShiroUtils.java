package com.songsy.core.shiro;

import com.songsy.admin.entity.CurrentUser;
import com.songsy.admin.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;

/**
 * Shiro 工具类
 * @author songshuiyang
 * @date 2018/3/11 15:56
 */
public class ShiroUtils {
    /**
     * 获取Subject
     * @return
     */
    public static Subject getSubject () {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前Session
     * @return
     */
    public static Session getSession () {
        return getSubject().getSession();
    }

    /**
     * 获取当前登入用户名
     * @return
     */
    public static String getCurrentUsername () {
        return (String)getSubject().getPrincipal();
    }

    /**
     * 获取Session中的当前用户
     * @return
     */
    public static CurrentUser getCurrentUser () {
        User user = (User)getSession().getAttribute("user");
        if (user == null) {
           return null;
        }
        CurrentUser currentUser = new CurrentUser();
        BeanUtils.copyProperties(user,currentUser);
        return  currentUser;
    }

}
