package com.songsy.admin.dao;

import com.songsy.admin.entity.PermisssionTreeGrid;
import com.songsy.admin.entity.Permission;
import com.songsy.base.BaseMapper;

import java.util.List;

/**
 * 用户权限
 * @author songshuiyang
 * @date 2017/12/17 20:12
 */
public interface PermissionMapper extends BaseMapper<Permission,Integer> {
    /**
     * 根据父节点获取子元素
     * @param parentId
     * @return
     */
    List<PermisssionTreeGrid> findPermissionChildren(Integer parentId);

    /**
     * 获取所有权限
     * @return
     */
    List<PermisssionTreeGrid> findAllPermission ();

    /**
     * 根据角色id获取权限
     * @param roleId
     * @return
     */
    List<PermisssionTreeGrid> findPermissionByRoleId(Integer roleId);

}