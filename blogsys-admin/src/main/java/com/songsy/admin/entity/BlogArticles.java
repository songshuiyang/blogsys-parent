package com.songsy.admin.entity;

import com.songsy.admin.common.DictEnum;
import com.songsy.admin.service.BlogArticlesTagService;
import com.songsy.base.BaseEntity;
import com.songsy.core.utils.SpringContextUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
/**
 * 博客文章
 * @author songshuiyang
 * @date 2018/03/04 20:12
 */
@Getter
@Setter
@ToString
public class BlogArticles extends BaseEntity {
    /**
     * 文章标题
     */
    private String title;
    /**
     * 概要
     */
    private String outline;
    /**
     * 封面图片
     */
    private String coverImage;
    /**
     * 作者
     */
    private String author;
    /**
     * 分类目录
     */
    private String category;
    /**
     * 文章类型
     */
    private Integer type;
    /**
     * 文章标签
     */
    private String tag;
    /**
     * 点击数
     */
    private Integer hitsNum;
    /**
     * 赞同数
     */
    private Integer satisfactoryNum;
    /**
     * 不赞同数
     */
    private Integer dissatisfiedNum;
    /**
     * 评论数量
     */
    private Integer commentNum;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 获取分类目录
     * @return
     */
    public String getCategoryAlias() {
        return category;
    }

    /**
     * 获取文章类型
     * @return
     */
    public String getTypeAlias() {
        return DictEnum.BlogArticles.Type.getAliasByIndex(this.type);
    }

    /**
     * 获取文章状态
     * @return
     */
    public String getStatusAlias() {
        return DictEnum.BlogArticles.Status.getAliasByIndex(this.getStatus());
    }

    /**
     * 获取TagList
     * @return
     */
    public List<BlogTag> getBlogTagList() {
        if (this.getTag() == null) {
            return null;
        }
        BlogArticlesTagService blogArticlesTagService = SpringContextUtils.getBean(BlogArticlesTagService.class);
        if (blogArticlesTagService != null && this.getId() !=null) {
           return blogArticlesTagService.findTagByArticlesId(this.getId());
        }
        return null;
    }
}