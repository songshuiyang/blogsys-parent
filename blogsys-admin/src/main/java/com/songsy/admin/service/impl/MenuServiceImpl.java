package com.songsy.admin.service.impl;

import com.songsy.admin.dao.MenuMapper;
import com.songsy.admin.entity.*;
import com.songsy.admin.service.MenuService;
import com.songsy.admin.service.RoleService;
import com.songsy.base.BaseMapper;
import com.songsy.core.base.impl.BaseServiceImpl;
import com.songsy.core.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单service
 * @author songshuiyang
 * @date 2018/3/27 22:12
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu,Integer> implements MenuService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuMapper mapper;

    @Autowired
    private RoleService roleService;

    @Override
    public BaseMapper<Menu,Integer> getMappser(){
        return mapper;
    }

    /**
     * 获取菜单栏资源
     * @return
     */
    @Override
    public List<MenuBar> getMenuBars(){
        List<MenuBar> menuBars = new ArrayList<>();
        List<Permission> currentUserPermission = AccountUtils.getCurrentUserPermission();
        Set<String> ownPermission =  currentUserPermission.stream().map(e -> new String(e.getPermissionName())).collect(Collectors.toSet());
        // 先获取一级菜单
        List<Menu> firstMenuList = mapper.findFirstMenuList();
        for(Menu firstMenu: firstMenuList) {
            // 根据权限筛选一级目录
            if (!ownPermission.contains(firstMenu.getFirstMenu())) {
                continue;
            }
            String firstMenuName = firstMenu.getFirstMenu();
            MenuBar menuBar = new MenuBar();
            menuBar.setFirstMenu(firstMenuName);
            menuBar.setIcon(firstMenu.getIcon());
            // 根据一级菜单查找二级菜单
            List<Menu> secondMenuList = mapper.findSecondMenuListByFirstMenu(firstMenuName);

            // 根据权限筛选二级目录
            List<Menu> secondMenuReusltList = secondMenuList.stream().filter(e -> ownPermission.contains(e.getSecondMenu())).collect(Collectors.toList());
            menuBar.setSecondMenuList(secondMenuReusltList);
            menuBars.add(menuBar);
        }
        return menuBars;
    }
    /**
     * 获取权限tree数据(表格使用)
     * @return
     */
    @Override
    public List<MenuTreeGird> getMenuTreeGird (){
        List<MenuTreeGird> firstMenuTreeGirdList= new ArrayList<>();
        // 先获取一级菜单
        List<Menu> firstMenuList = mapper.findFirstMenuList();
        for (Menu firstMenu: firstMenuList) {
            // 一级菜单
            MenuTreeGird level1MenuTreeGird = new MenuTreeGird();
            copyProperties(firstMenu,level1MenuTreeGird,1);

            // 根据一级菜单名查找二级菜单
            String firstMenuName = firstMenu.getFirstMenu();
            List<Menu> secondMenuList = mapper.findSecondMenuListByFirstMenu(firstMenuName);
            List<MenuTreeGird> secondMenuTreeGirdList= new ArrayList<>();
            for (Menu secondMenu: secondMenuList) {
                // 二级菜单
                MenuTreeGird level2MenuTreeGird = new MenuTreeGird();
                copyProperties(secondMenu,level2MenuTreeGird,2);
                secondMenuTreeGirdList.add(level2MenuTreeGird);
            }
            level1MenuTreeGird.setChildren(secondMenuTreeGirdList);
            firstMenuTreeGirdList.add(level1MenuTreeGird);
        }
        return firstMenuTreeGirdList;
    }

    /**
     * 复制菜单
     * @param treeGird
     * @param menu
     * @param level
     */
    private void copyProperties (Menu menu,MenuTreeGird treeGird, Integer level) {
        treeGird.setId(menu.getId());
        if (level ==1 ){
            treeGird.setName(menu.getFirstMenu());
        } else {
            treeGird.setName(menu.getSecondMenu());
        }
        treeGird.setIcon(menu.getIcon());
        treeGird.setOrderNum(menu.getOrderNum());
        treeGird.setUrl(menu.getUrl());
        treeGird.setStatus(menu.getStatus());
    }
}
