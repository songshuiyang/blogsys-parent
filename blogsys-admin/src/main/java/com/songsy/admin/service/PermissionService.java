package com.songsy.admin.service;

import com.songsy.admin.entity.PermisssionTreeGrid;
import com.songsy.admin.entity.Permission;
import com.songsy.admin.entity.PermisssionXTree;
import com.songsy.base.BaseService;

import java.util.List;

/**
 * 权限service
 * @author songshuiyang
 * @date 2018/3/27 22:12
 */
public interface PermissionService extends BaseService<Permission,Integer> {
    /**
     * 获取权限tree数据
     * @return
     */
    List<PermisssionTreeGrid> getPermissionTreeGrid ();
    /**
     * 获取权限XTree数据 -使用layui社区插件
     * @return
     */
    List<PermisssionXTree> getPermissionXTree (Integer roleId);

}
