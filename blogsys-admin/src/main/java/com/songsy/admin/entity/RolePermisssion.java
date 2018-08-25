package com.songsy.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色权限中间表
 * @author songshuiyang
 * @date 2018/4/1 11:44
 */
@Getter
@Setter
@ToString
public class RolePermisssion {
    private Integer permissionId;
    private Integer roleId;
}
