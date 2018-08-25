package com.songsy.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户角色中间表
 * @author songshuiyang
 * @date 2018/3/27 0:25
 */
@Getter
@Setter
@ToString
public class UserRole {
    private Integer userId;
    private Integer roleId;

}
