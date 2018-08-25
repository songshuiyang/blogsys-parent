package com.songsy.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.songsy.base.BaseEntity;


/**
 * 系统资源
 *
 * @author songshuiyang
 * @date 2018/3/13 21:29
 */
@Getter
@Setter
@ToString
public class SysResource extends BaseEntity{
    /**
     * 资源key
     */
    private String pkey;

    /**
     * 资源value
     */
    private String value;

    /**
     * 资源value1
     */
    private String value1;
    /**
     * 资源value2
     */
    private String value2;

}