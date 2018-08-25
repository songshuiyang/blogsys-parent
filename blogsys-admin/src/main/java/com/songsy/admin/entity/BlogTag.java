package com.songsy.admin.entity;

import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博客标签
 * @author songshuiyang
 * @date 2018/03/04 20:12
 */
@Getter
@Setter
@ToString
public class BlogTag extends BaseEntity {

    private String name;
}