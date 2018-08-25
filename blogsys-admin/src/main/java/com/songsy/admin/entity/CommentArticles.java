package com.songsy.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.songsy.base.BaseEntity;

/**
 * 文章评论
 * @author songshuiyang
 * @date 2018/03/24 20:12
 */
@Getter
@Setter
@ToString
public class CommentArticles extends BaseEntity{
    /**
     * 博客文章id
     */
    private Integer blogArticlesId;
    /**
     * 博客文章title
     */
    private String articlesTitle;
    /**
     * 类型 0-评论 1-留言
     */
    private Integer type;
    /**
     * 评论人头像
     */
    private String coverImage;
    /**
     * 评论人
     */
    private String author;
    /**
     * 邮件
     */
    private String email;
    /**
     * 省
     */
    private String authorLocationProvince;
    /**
     * 市
     */
    private String authorLocationCity;
    /**
     * 赞同数
     */
    private Integer satisfactoryNum;
    /**
     * 不赞同数
     */
    private Integer dissatisfiedNum;
    /**
     * 内容
     */
    private String content;


}