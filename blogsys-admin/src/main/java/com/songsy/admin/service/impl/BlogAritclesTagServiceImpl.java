package com.songsy.admin.service.impl;

import com.songsy.admin.dao.BlogArticlesTagMapper;
import com.songsy.admin.entity.BlogArticles;
import com.songsy.admin.entity.BlogArticlesTag;
import com.songsy.admin.entity.BlogTag;
import com.songsy.admin.service.BlogArticlesTagService;
import com.songsy.base.BaseMapper;
import com.songsy.core.base.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 博客标签 博客文章中间关系service
 * @author songshuiyang
 * @date 2018/3/4 10:12
 */
@Service
public class BlogAritclesTagServiceImpl extends BaseServiceImpl<BlogArticlesTag,Integer> implements BlogArticlesTagService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogArticlesTagMapper mapper;

    @Override
    public BaseMapper<BlogArticlesTag,Integer> getMappser(){
        return mapper;
    }

    /**
     * 根据标签id 获取文章
     * @param tagId
     * @return
     */
    @Override
    public List<BlogArticles> findArticlesByTagId(Integer tagId){
        return mapper.findArticlesByTagId(tagId);
    }

    /**
     * 根据文章id 获取tag
     * @param articlesId
     * @return
     */
    @Override
    public List<BlogTag> findTagByArticlesId(Integer articlesId){
        return mapper.findTagByArticlesId(articlesId);
    }


    /**
     * 根据文章id 删除博客文章与Tag关系表
     * @param articlesId
     * @return
     */
    @Override
    public int deleteArticlesTagByArticlesId(Integer articlesId){
        return mapper.deleteArticlesTagByArticlesId(articlesId);
    }
}
