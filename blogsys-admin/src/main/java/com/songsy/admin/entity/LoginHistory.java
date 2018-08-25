package com.songsy.admin.entity;

import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登录记录
 * @author songshuiyang
 * @date 2018/2/11 20:07
 */
@Getter
@Setter
@ToString
public class LoginHistory extends BaseEntity {
    private Integer userId;

    private String username;

    private String ip;

    private String ipLocation;

    private String ipLocationProvince;

    private String ipLocationCity;

    private String deviceType;

    private String deviceSystem;

    private String deviceVersion;

    private String browser;

    private String useragent;
}