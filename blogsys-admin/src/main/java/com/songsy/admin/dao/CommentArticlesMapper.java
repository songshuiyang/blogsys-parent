package com.songsy.admin.dao;

import com.songsy.admin.entity.CommentArticles;
import com.songsy.base.BaseMapper;

import java.util.List;

/**
 * 文章评论
 * @author songshuiyang
 * @date 2018/03/24 20:12
 */
public interface CommentArticlesMapper extends BaseMapper<CommentArticles,Integer> {
    /**
     * 根据文章id获取文章评论
     * @param blogArticlesId
     * @return
     */
    List<CommentArticles> findCommentByArticlesId(Integer blogArticlesId);
    /**
     * 根据文章id获取文章评论（详细数据）
     * @param blogArticlesId
     * @return
     */
    List<CommentArticles> findCommentDetailByArticlesId(Integer blogArticlesId);

    /**
     * 获取留言
     * @return
     */
    List<CommentArticles> findLeavingMessage();

}