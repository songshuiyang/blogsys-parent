package com.songsy.admin.service;

import com.songsy.admin.entity.BlogArticles;
import com.songsy.admin.entity.BlogArticlesTag;
import com.songsy.admin.entity.BlogTag;
import com.songsy.base.BaseService;

import java.util.List;

/**
 * 博客标签 博客文章中间关系service
 * @author songshuiyang
 * @date 2018/3/4 10:12
 */
public interface BlogArticlesTagService extends BaseService<BlogArticlesTag,Integer> {

    /**
     * 根据标签id 获取文章
     * @param tagId
     * @return
     */
    List<BlogArticles> findArticlesByTagId(Integer tagId);

    /**
     * 根据文章id 获取tag
     * @param articlesId
     * @return
     */
    List<BlogTag> findTagByArticlesId(Integer articlesId);

    /**
     * 根据文章id 删除博客文章与Tag关系表
     * @param articlesId
     * @return
     */
    int deleteArticlesTagByArticlesId(Integer articlesId);
}
