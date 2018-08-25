package com.songsy.admin.dto;

import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户角色
 * @author songshuiyang
 * @date 2017/11/28 21:36
 */
@Getter
@Setter
@ToString
public class RoleDTO extends BaseEntity{

    private String roleName;

    private String roleCode;

    private String roleDescribe;

}