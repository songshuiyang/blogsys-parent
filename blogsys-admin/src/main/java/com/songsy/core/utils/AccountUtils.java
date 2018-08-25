package com.songsy.core.utils;

import com.songsy.admin.entity.CurrentUser;
import com.songsy.admin.entity.Permission;
import com.songsy.admin.entity.Role;
import com.songsy.admin.entity.User;
import com.songsy.admin.service.PermissionService;
import com.songsy.admin.service.RoleService;
import com.songsy.admin.service.UserService;
import com.songsy.core.shiro.ShiroUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户工具类
 * @author songshuiyang
 * @date 2018/4/1 14:05
 */
public class AccountUtils {

    private static UserService userService = SpringContextUtils.getBean(UserService.class);

    private static RoleService roleService = SpringContextUtils.getBean(RoleService.class);

    private static PermissionService permissionService = SpringContextUtils.getBean(PermissionService.class);

    /**
     * 获取当前用户(带角色信息)
     * @return
     */
    public static CurrentUser getCurrentUser () {
        CurrentUser currentUser = ShiroUtils.getCurrentUser();
        if (null != currentUser) {
            String username = currentUser.getUsername();
            User userDB = userService.getByUsername(username);
            Integer userId = userDB.getId();
            BeanUtils.copyProperties(userDB,currentUser);

            List<Role> roleList = roleService.findRolesByUserId(userId);
            currentUser.setCurrentRoleList(roleList);
        }
        return currentUser;
    }

    /**
     * 获取当前用户角色
     * @return
     */
    public static List<Role> getCurrentUserRole () {
        CurrentUser currentUser = ShiroUtils.getCurrentUser();
        if (null != currentUser) {
            Integer userId = currentUser.getId();
            List<Role> roleList = roleService.findRolesByUserId(userId);
            return roleList;
        }
        return null;
    }

    /**
     * 获取当前用户权限
     * @return
     */
    public static List<Permission> getCurrentUserPermission () {
        CurrentUser currentUser = ShiroUtils.getCurrentUser();
        if (null != currentUser) {
            Integer userId = currentUser.getId();
            List<Permission> permissionsResult = new ArrayList<>();
            List<Role> roleList = roleService.findRolesByUserId(userId);
            if (roleList.size()!=0) {
                List<Integer> roleIdList = roleList.stream().map(e -> new Integer(e.getId())).collect(Collectors.toList());
                permissionsResult = roleService.findPermissionByRoleIdList(roleIdList);
            }
            return  permissionsResult;
        }
        return null;
    }
}
