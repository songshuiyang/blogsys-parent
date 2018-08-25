package com.songsy.admin.dto;

import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 登录记录DTO
 * @author songshuiyang
 * @date 2018/3/16 23:02
 */
@Getter
@Setter
@ToString
public class LoginHistoryDTO extends BaseEntity {
    private Integer userId;

    private String username;

    private String ip;

    private String ipLocation;

    private String deviceType;

    private String deviceSystem;

    private String deviceVersion;

    private String browser;

    private String useragent;
}
