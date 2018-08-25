package com.songsy.admin.entity;

import com.songsy.admin.common.DictEnum;
import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 用户
 * @author songshuiyang
 * @date 2017/11/28 21:36
 */
@Getter
@Setter
@ToString
public class User extends BaseEntity{

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

    /**
     * 得到盐值: 用户名 + 密码
     * @return
     */
    public String getCredentialsSalt() {
        return username + salt;
    }

    public String getSexAlias() {
        return DictEnum.User.Gender.getAliasByIndex(this.sex);
    }
}