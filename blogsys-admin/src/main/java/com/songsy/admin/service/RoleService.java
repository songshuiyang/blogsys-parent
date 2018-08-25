package com.songsy.admin.service;

import com.songsy.admin.entity.*;
import com.songsy.base.BaseService;

import java.util.List;

/**
 * 角色信息service
 * @author songshuiyang
 * @date 2017/12/17 11:56
 */
public interface RoleService extends BaseService<Role,Integer> {

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
     * 删除用户角色关系（中间表）
     * @param userRole
     */
    void deleteUserRoleRelation(UserRole userRole);
    /**
     * 插入新的用户角色关系（中间表）
     * @param userRole
     */
    void insertUserRoleRelation(UserRole userRole);




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
     * @param roleIdList
     * @return
     */
    List<Permission> findPermissionByRoleIdList(List<Integer> roleIdList);
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
}
