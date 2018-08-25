package com.songsy.admin.dto;

import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文章评论数据传输对象
 * @author songshuiyang
 * @date 2018/3/24 20:35
 */
@Getter
@Setter
@ToString
public class CommentArticlesDTO  extends BaseEntity {

    private Integer blogArticlesId;

    private Integer type;

    private String coverImage;

    private String author;

    private String email;

    private String authorLocationProvince;

    private String authorLocationCity;

    private Integer satisfactoryNum;

    private Integer dissatisfiedNum;

    private String content;

    private String articlesTitle;
}
