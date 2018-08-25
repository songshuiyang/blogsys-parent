package com.songsy.core.shiro;

import com.songsy.admin.entity.User;
import com.songsy.admin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author songsy
 * @Package com.songsy.admin.web.shiro
 * @Title: subject.login(token);这里会跳转到我们自定义的realm中
 * @Description:
 * @date 2017/11/29
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 获取该用户的角色
        Set<String> roleSet = userService.getRoles(username);
        authorizationInfo.setRoles(roleSet);
        // 获取该用户的权限
        Set<String> permissionSet = userService.getPermissions(username);
        authorizationInfo.setStringPermissions(permissionSet);
        return authorizationInfo;
    }

    /**
     * 验证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userService.getByUsername(username);
        if (user == null || StringUtils.isBlank(user.getUsername())) {
            throw new UnknownAccountException("用户名不存在");
        }
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
        return authcInfo;

    }
}
