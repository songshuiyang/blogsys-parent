package com.songsy.admin.entity;

import com.songsy.admin.common.DictEnum;
import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户权限
 * @author songshuiyang
 * @date 2017/11/28 21:36
 */
@Getter
@Setter
@ToString
public class Permission extends BaseEntity{
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

    public String getPermissionTypeAlias() {
        return this.permissionType !=null? DictEnum.System.PermissionType.getMap().get(this.permissionType):null;
    }


}