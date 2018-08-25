package com.songsy.admin.entity;

import com.songsy.admin.common.DictEnum;
import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户菜单
 * @author songshuiyang
 * @date 2018/03/27 22:36
 */
@Getter
@Setter
@ToString
public class Menu extends BaseEntity {
    /**
     * 一级菜单
     */
    private String firstMenu;
    /**
     * 二级菜单
     */
    private String secondMenu;
    /**
     * 菜单类型 0-一级菜单 1:二级菜单
     */
    private Integer type;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 链接
     */
    private String url;
    /**
     * 排序号
     */
    private Integer orderNum;

    public String getTypeAlias() {
        return this.type !=null? DictEnum.System.MenuType.getMap().get(this.type):null;
    }

}