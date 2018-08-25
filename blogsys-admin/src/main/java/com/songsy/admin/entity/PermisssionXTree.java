package com.songsy.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.songsy.base.BaseEntity;

import java.util.List;

/**
 * 权限tree结构
 * @author songshuiyang
 * @date 2018/3/31 22:26
 */
@Getter
@Setter
@ToString
public class PermisssionXTree {
    /**
     * 显示的值
     */
    private String title;
    /**
     * 代表的值
     */
    private Integer  value;
    /**
     * 是否选中
     */
    private Boolean checked;
    /**
     * true 为禁用  false为可用
     */
    private Boolean disabled;
    /**
     * 子菜单
     */
    private List<PermisssionXTree> data;
}
