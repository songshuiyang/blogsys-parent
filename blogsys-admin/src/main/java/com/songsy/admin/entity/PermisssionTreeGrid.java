package com.songsy.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.songsy.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限tree结构
 * @author songshuiyang
 * @date 2018/3/29 22:26
 */
@Getter
@Setter
@ToString
public class PermisssionTreeGrid extends BaseEntity {

    /**
     * 权限code
     */
    private String permissionCode;
    /**
     * 权限名称
     */
    private String permissionName;
    /**
     * 权限类型
     */
    private Integer permissionType;
    /**
     * 父节点id
     */
    private Integer parentId;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 排序号
     */
    private Integer orderNum;
    /**
     * 描述
     */
    private String description;

    /**
     * 子元素
     */
    private List<PermisssionTreeGrid> children=new ArrayList<PermisssionTreeGrid>(0);

    public String getName() {
        return this.permissionName;
    }
}
