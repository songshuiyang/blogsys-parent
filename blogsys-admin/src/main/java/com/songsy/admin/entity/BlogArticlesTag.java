package com.songsy.admin.entity;

import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 博客文章 标签中间关系
 * @author songshuiyang
 * @date 2018/03/04 20:12
 */
@Getter
@Setter
@ToString
public class BlogArticlesTag extends BaseEntity {

    private Integer blogTagId;

    private Integer blogArticlesId;

}