package com.songsy.admin.dao;

import com.songsy.admin.entity.BlogArticles;
import com.songsy.admin.entity.BlogArticlesTag;
import com.songsy.admin.entity.BlogTag;
import com.songsy.base.BaseMapper;

import java.util.List;

/**
 * 博客文章与Tag的中间表mapper
 * @author songshuiyang
 * @date 2018/03/04 20:12
 */
public interface BlogArticlesTagMapper extends BaseMapper<BlogArticlesTag,Integer> {
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