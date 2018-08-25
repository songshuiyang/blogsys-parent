package com.songsy.admin.service;

import com.songsy.admin.dto.BlogArticlesDTO;
import com.songsy.admin.dto.BlogArticlesTypeDTO;
import com.songsy.admin.dto.EchartsDTO;
import com.songsy.admin.entity.BlogArticles;
import com.songsy.base.BaseService;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 博客文章service
 * @author songshuiyang
 * @date 2018/3/4 10:12
 */
public interface BlogArticlesService extends BaseService<BlogArticles,Integer> {
    /**
     * 获取最后插入MySQL记录的自增ID值
     * @return
     */
    int getLastInsertRecordId();

    /**
     * 获取博客热文,使用缓存
     * @param pageSize 取多少条
     * @return
     */
    List<BlogArticles> getHotArticlesInCache(int pageSize);

    /**
     * 分页查询-从缓存中取
     * @param pageNum  页码
     * @param pageSize 一页数量
     * @param params   查询参数
     * @return
     */
    Page<BlogArticles> findPageListIncache(int pageNum, int pageSize, BlogArticles params);

    /**
     * 获取博客分类数量统计
     *
     * @return
     */
    List<BlogArticlesTypeDTO> getArticlesTypeCount();

    /**
     * 获取博客归档数量统计
     * @return
     */
    List<EchartsDTO> getBlogArticlesCategoryCount();

    /**
     * 根据查询条件查询文章
     * @return
     */
    List<BlogArticles> findBlogArticlesByParam(BlogArticlesDTO blogArticlesDTO);

}
