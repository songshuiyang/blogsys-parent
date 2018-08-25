package com.songsy.admin.service;

import com.songsy.admin.entity.*;
import com.songsy.base.BaseService;

import java.util.List;

/**
 * 菜单service
 * @author songshuiyang
 * @date 2018/3/27 22:12
 */
public interface MenuService extends BaseService<Menu,Integer> {
    /**
     * 获取菜单栏资源
     * @return
     */
    List<MenuBar> getMenuBars();

    /**
     * 获取权限tree数据(表格使用)
     * @return
     */
    List<MenuTreeGird> getMenuTreeGird ();

}
