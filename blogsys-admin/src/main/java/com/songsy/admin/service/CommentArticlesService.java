package com.songsy.admin.service;

import com.songsy.admin.entity.CommentArticles;
import com.songsy.base.BaseService;

import java.util.List;

/**
 * 文章评论
 * @author songshuiyang
 * @date 2018/3/24 20:30
 */
public interface CommentArticlesService extends BaseService<CommentArticles,Integer> {
    /**
     * 根据文章id获取文章评论
     * @param blogArticlesId
     * @return
     */
    List<CommentArticles> findCommentByArticlesId(Integer blogArticlesId);

    /**
     * 获取留言
     * @return
     */
    List<CommentArticles> findLeavingMessage();
}
