package com.songsy.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.songsy.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author songshuiyang
 * @date 2018/4/1 12:23
 */
@Getter
@Setter
@ToString
public class MenuTreeGird extends BaseEntity {
    private Integer id;

    /**
     * 菜单名
     */
    private String name;
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
    /**
     * 菜单状态
     */
    private Integer status;
    /**
     * 子元素, 防止null
     */
    private List<MenuTreeGird> children = new ArrayList<>();
}
