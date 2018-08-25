package com.songsy.admin.dao;

import com.songsy.admin.dto.BlogArticlesDTO;
import com.songsy.admin.dto.BlogArticlesTypeDTO;
import com.songsy.admin.dto.EchartsDTO;
import com.songsy.admin.entity.BlogArticles;
import com.songsy.base.BaseMapper;

import java.util.List;

/**
 * 博客文章mapper
 * @author songshuiyang
 * @date 2018/03/04 20:12
 */
public interface BlogArticlesMapper extends BaseMapper<BlogArticles,Integer> {
    /**
     * 获取最后插入MySQL记录的自增ID值
     * @return
     */
    int getLastInsertRecordId();

    /**
     * 热文
     * @return
     */
    List<BlogArticles> hotArticles(int pageSize);

    /**
     * 获取博客分类数量统计
     *
     * @return
     */
    List<BlogArticlesTypeDTO> getArticlesTypeCount();


    /**
     * 获取博客归档数量统计
     *
     * @return
     */
    List<EchartsDTO> getBlogArticlesCategoryCount();

    /**
     * 根据查询条件查询文章
     * @return
     */
    List<BlogArticles> findBlogArticlesByParam(BlogArticlesDTO blogArticlesDTO);
}