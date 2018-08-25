package com.songsy.admin.dao;

import com.songsy.admin.entity.*;
import com.songsy.base.BaseMapper;

import java.util.List;

/**
 * 用户角色
 * @author songshuiyang
 * @date 2017/11/29 20:12
 */
public interface RoleMapper extends BaseMapper<Role,Integer> {
    /**
     * 获取所有role
     * @return
     */
    List<Role> findAllRole();

    /**
     * 根据用户id获取所属角色
     * @param userId
     * @return
     */
    List<Role> findRolesByUserId(Integer userId);

    /**
     * 根据角色id获取所属用户
     * @param roleId
     * @return
     */
    List<User> findUsersByRoleId(Integer roleId);

    /**
     * 删除用户角色关系
     * @param userRole
     */
    void deleteUserRoleRelation(UserRole userRole);

    /**
     * 插入新的用户角色关系
     * @param userRole
     */
    void insertUserRoleRelation(UserRole userRole);

    /**
     * 删除权限角色关系（中间表）
     * @param rolePermisssion
     */
    void deleteRolePermisssionRelation(RolePermisssion rolePermisssion);
    /**
     * 插入新的权限角色关系（中间表）
     * @param rolePermisssion
     */
    void insertRolePermisssionRelation(RolePermisssion rolePermisssion);

    /**
     * 根据权限id获取所属角色
     * @param permissionId
     * @return
     */
    List<Role> findRolesByPermissionId(Integer permissionId);

    /**
     * 根据角色id获取所拥有的权限
     * @param roleId
     * @return
     */
    List<Permission> findPermissionByRoleId(Integer roleId);
    /**
     * 获取角色list所拥有的权限
     * @param list
     * @return
     */
    List<Permission> findPermissionByRoleIdList(List<Integer> list);

}