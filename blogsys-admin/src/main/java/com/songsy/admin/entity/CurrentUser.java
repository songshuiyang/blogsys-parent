package com.songsy.admin.entity;

import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 当前登入用户
 * @author songshuiyang
 * @date 2018/2/28 22:53
 */
@Getter
@Setter
@ToString
public class CurrentUser extends BaseEntity {

    private String username;

    private String password;

    private String nickname;

    private Integer sex;

    private Integer age;

    private String phone;

    private String email;

    private String address;

    private String salt;

    private String headPortrait;

    private List<Role> currentRoleList;

    private List<Permission> currentPermissionList;

    private static final long serialVersionUID = 1L;
}
