package com.songsy.admin.dto;



import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户DTO
 * @author songshuiyang
 * @date 2018/2/8 22:44
 */
@Getter
@Setter
@ToString
public class UserDTO extends BaseEntity {

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

}
