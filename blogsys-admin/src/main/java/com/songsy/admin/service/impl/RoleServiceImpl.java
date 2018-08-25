package com.songsy.admin.service.impl;

import com.songsy.admin.dao.RoleMapper;
import com.songsy.admin.entity.*;
import com.songsy.admin.service.RoleService;
import com.songsy.base.BaseMapper;
import com.songsy.core.base.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色
 * @author songshuiyang
 * @date 2017/12/17 11:58
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,Integer> implements RoleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleMapper mapper;

    @Override
    public BaseMapper<Role, Integer> getMappser() {
        return mapper;
    }
    /**
     * 根据用户id获取所属角色
     * @param userId
     * @return
     */
    @Override
    public List<Role> findRolesByUserId(Integer userId){
        return mapper.findRolesByUserId(userId);
    }

    /**
     * 根据角色id获取所属用户
     * @param roleId
     * @return
     */
    @Override
    public List<User> findUsersByRoleId(Integer roleId){
        return mapper.findUsersByRoleId(roleId);
    }
    /**
     * 获取所有role
     * @return
     */
    @Override
    public List<Role> findAllRole(){
        return mapper.findAllRole();
    }
    /**
     * 删除用户角色关系
     * @param userRole
     */
    @Override
    public void deleteUserRoleRelation(UserRole userRole){
        mapper.deleteUserRoleRelation(userRole);
    }
    /**
     * 插入新的用户角色关系
     * @param userRole
     */
    @Override
    public void insertUserRoleRelation(UserRole userRole){
        mapper.insertUserRoleRelation(userRole);
    }

    /**
     * 删除权限角色关系（中间表）
     * @param rolePermisssion
     */
    @Override
    public void deleteRolePermisssionRelation(RolePermisssion rolePermisssion){
        mapper.deleteRolePermisssionRelation(rolePermisssion);

    }
    /**
     * 插入新的权限角色关系（中间表）
     * @param rolePermisssion
     */
    @Override
    public void insertRolePermisssionRelation(RolePermisssion rolePermisssion){
        mapper.insertRolePermisssionRelation(rolePermisssion);
    }


    /**
     * 根据权限id获取所属角色
     *
     * @param permissionId
     * @return
     */
    @Override
    public List<Role> findRolesByPermissionId(Integer permissionId) {
        return mapper.findRolesByPermissionId(permissionId);
    }

    /**
     * 根据角色id获取所拥有的权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Permission> findPermissionByRoleId(Integer roleId) {
        return mapper.findPermissionByRoleId(roleId);
    }

    /**
     * 获取角色list所拥有的权限
     * @param roleIdList
     * @return
     */
    @Override
    public List<Permission> findPermissionByRoleIdList(List<Integer> roleIdList){
        return mapper.findPermissionByRoleIdList(roleIdList);
    }
}
