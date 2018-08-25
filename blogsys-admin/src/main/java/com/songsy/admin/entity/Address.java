package com.songsy.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 使用百度API 根据用户ip获取用户地址
 * @author songshuiyang
 * @date 2018/3/16 23:56
 */
@Getter
@Setter
@ToString
public class Address {
    private String headInfo;

    private String province;
    private String city;
    private String district;
    private String street;
    private String streetNumber;
    private Integer cityCode;

    private String address;
    private String pointX;
    private String pointY;

    private String detail;
    private String sourceStr;
    private String status;

}
