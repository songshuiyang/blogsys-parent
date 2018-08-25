package com.songsy.admin.dto;

import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 登录标签DTO
 * @author songshuiyang
 * @date 2018/3/10 19:52
 */
@Getter
@Setter
@ToString
public class BlogTagDTO extends BaseEntity {
    /**
     * 标签名称
     */
    private String name;
}
