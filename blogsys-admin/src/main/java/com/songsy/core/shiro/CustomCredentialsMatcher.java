package com.songsy.core.shiro;

import com.songsy.admin.entity.User;
import com.songsy.admin.service.UserService;
import com.songsy.core.utils.CacheUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songshuiyang
 * @title: 自定义 密码验证类
 * @description:
 * @date 2017/12/11 23:00
 */
@Component
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    private static final String passwordRetryCacheName = "passwordRetryCache";

    private static final Integer passwordRetryCount = 10;

    @Autowired
    private UserService userService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());
        // 重试次数太多，直接抛异常
        Map<String, Integer> retryCache = (Map<String, Integer>)CacheUtils.get(passwordRetryCacheName,username);
        if (retryCache != null) {
            Integer count = retryCache.get(username);
            if (count > passwordRetryCount - 1) {
                throw new ExcessiveAttemptsException();
            }
        }
        User user = userService.getByUsername(username);
        if (user != null) {
            // 用户是否锁定校验
            if (!user.getEnable()) {
                throw new LockedAccountException();
            }
            Object tokenCredentials = SecurityUtils.entryptPassword(username, password, user.getSalt());
            Object accountCredentials = getCredentials(info);
            // 将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
            boolean result = equals(tokenCredentials, accountCredentials);

            // 重复输错密码校验
            if (!result) {
                Map<String, Integer> retryCacheName = (Map<String, Integer>)CacheUtils.get(passwordRetryCacheName,username);
                Map<String, Integer> integerMap = new HashMap<>();
                if (retryCacheName == null) {
                    integerMap.put(username,1);
                    CacheUtils.put(passwordRetryCacheName,username,integerMap);
                } else {
                    Integer count = retryCacheName.get(username);
                    count ++;
                    integerMap.put(username,count);
                    CacheUtils.put(passwordRetryCacheName,username,integerMap);
                }
            }
            return result;
        } else {
            throw new UnknownAccountException("用户名不存在");
        }
    }
}
