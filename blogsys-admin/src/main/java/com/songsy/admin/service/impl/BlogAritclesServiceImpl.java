package com.songsy.admin.service.impl;

import com.songsy.admin.dao.BlogArticlesMapper;
import com.songsy.admin.dto.BlogArticlesDTO;
import com.songsy.admin.dto.BlogArticlesTypeDTO;
import com.songsy.admin.dto.EchartsDTO;
import com.songsy.admin.entity.BlogArticles;
import com.songsy.admin.service.BlogArticlesService;
import com.songsy.base.BaseMapper;
import com.songsy.core.base.impl.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 博客文章
 * @author songshuiyang
 * @date 2018/3/4 10:12
 */
@Service
public class BlogAritclesServiceImpl extends BaseServiceImpl<BlogArticles, Integer> implements BlogArticlesService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogArticlesMapper mapper;

    @Override
    public BaseMapper<BlogArticles, Integer> getMappser() {
        return mapper;
    }


    /**
     * 获取最后插入MySQL记录的自增ID值
     *
     * @return
     */
    @Override
    public int getLastInsertRecordId() {
        return mapper.getLastInsertRecordId();
    }

    /**
     * 第一次会缓存文章
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "articlesDetail", key = "#id")
    public BlogArticles selectByPrimaryKey(Integer id) {
        return getMappser().selectByPrimaryKey(id);
    }

    /**
     * 保存文章，同时删除失效或无用的缓存数据
     * @param record
     * @return
     */
    @Override
    @CacheEvict(value = "articlesDetail", key = "#record.getId()")
    public int updateByPrimaryKeySelective(BlogArticles record) {
        setCurrentOperator(record);
        return getMappser().updateByPrimaryKeySelective(record);
    }
    /**
     * 获取博客热文,使用缓存
     * @param pageSize 取多少条
     * @return
     */
    @Override
    @Cacheable(value = "hotArticles")
    public List<BlogArticles> getHotArticlesInCache(int pageSize){
        List<BlogArticles> hotArticles = mapper.hotArticles(pageSize);
        return hotArticles;
    }
    /**
     * 分页查询
     * 缓存 key: pageNum + pageSize + params.getTag() + params.getCategory()
     * @param pageNum  页码
     * @param pageSize 一页数量
     * @param params   查询参数
     * @return
     */
    @Override
    //@Cacheable(value = "articlesList", key="T(String).valueOf(#pageNum).concat('-').concat(T(String).valueOf(#pageSize)).concat('-').concat(#params.getTag()).concat(#params.getCategory())")
    public Page<BlogArticles> findPageListIncache(int pageNum, int pageSize, BlogArticles params) {
        PageHelper.startPage(pageNum, pageSize);
        Page<BlogArticles> blogArticles = (Page<BlogArticles>) getMappser().findPageList(params);
        return blogArticles;
    }
    /**
     * 获取博客分类数量统计
     *
     * @return
     */
    @Override
    public List<BlogArticlesTypeDTO> getArticlesTypeCount(){
        List<BlogArticlesTypeDTO> intMapList = mapper.getArticlesTypeCount();
        return intMapList;
    }
    /**
     * 获取博客分类数量统计
     *
     * @return
     */
    @Override
    public List<EchartsDTO> getBlogArticlesCategoryCount(){
        List<EchartsDTO> intMapList = mapper.getBlogArticlesCategoryCount();
        return intMapList;
    }


    /**
     * 根据查询条件查询文章
     * @return
     */
    @Override
    public List<BlogArticles> findBlogArticlesByParam(BlogArticlesDTO blogArticlesDTO){
        return mapper.findBlogArticlesByParam(blogArticlesDTO);
    }

}
