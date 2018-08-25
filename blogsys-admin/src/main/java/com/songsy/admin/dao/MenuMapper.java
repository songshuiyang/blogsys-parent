package com.songsy.admin.dao;

import com.songsy.admin.entity.Menu;
import com.songsy.base.BaseMapper;

import java.util.List;

/**
 * 菜单
 * @author songshuiyang
 * @date 2018/03/27 20:12
 */
public interface MenuMapper extends BaseMapper<Menu,Integer> {
    /**
     * 获取一级菜单
     * @return
     */
    List<Menu> findFirstMenuList();

    /**
     * 获取二级菜单
     * @return
     */
    List<Menu> findSecondMenuList();

    /**
     * 根据一级目录查找二级目录
     * @return
     */
    List<Menu> findSecondMenuListByFirstMenu(String firstMenu);
}