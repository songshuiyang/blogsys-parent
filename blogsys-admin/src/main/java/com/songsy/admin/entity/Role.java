package com.songsy.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.songsy.base.BaseEntity;

/**
 * 用户角色
 * @author songshuiyang
 * @date 2017/11/28 21:36
 */
@Getter
@Setter
@ToString
public class Role extends BaseEntity{

    private String roleName;

    private String roleCode;

    private String roleDescribe;
}