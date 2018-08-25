package com.songsy.admin.dto;

import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author songshuiyang
 * @date 2018/3/29 21:23
 */
@Getter
@Setter
@ToString
public class PermissionDTO extends BaseEntity {
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


}
