package com.songsy.admin.dto;

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
public class MenuDTO extends BaseEntity{
    private String firstMenu;

    private String secondMenu;

    private Integer type;

    private String icon;

    private String url;

    private Integer orderNum;

}