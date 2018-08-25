package com.songsy.admin.service.impl;

import com.songsy.admin.dao.CommentArticlesMapper;
import com.songsy.admin.entity.CommentArticles;
import com.songsy.admin.service.CommentArticlesService;
import com.songsy.base.BaseMapper;
import com.songsy.core.base.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章评论
 * @author songshuiyang
 * @date 2018/3/24 20:31
 */
@Service
public class CommentArticlesServiceImpl extends BaseServiceImpl<CommentArticles, Integer> implements CommentArticlesService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentArticlesMapper mapper;

    @Override
    public BaseMapper<CommentArticles, Integer> getMappser() {
        return mapper;
    }
    /**
     * 根据文章id获取文章评论
     * @param blogArticlesId
     * @return
     */
    @Override
    public List<CommentArticles> findCommentByArticlesId(Integer blogArticlesId){
        return mapper.findCommentByArticlesId(blogArticlesId);
    }
    /**
     * 获取留言
     * @return
     */
    @Override
    public List<CommentArticles> findLeavingMessage(){
        return mapper.findLeavingMessage();
    }
}
