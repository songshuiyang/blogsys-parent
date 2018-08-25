package com.songsy.admin.entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 用户菜单
 * @author songshuiyang
 * @date 2018/03/27 22:36
 */
@Getter
@Setter
@ToString
public class MenuBar {
    /**
     * 一级菜单
     */
    private String firstMenu;
    /**
     * 一级菜单排序号
     */
    private Integer orderNum;
    /**
     *一级菜单图标
     */
    private String icon;
    /**
     * 二级菜单
     */
    private List<Menu> secondMenuList;
}
