package com.songsy.admin.dao;

import com.songsy.admin.entity.BlogTag;
import com.songsy.base.BaseMapper;

import java.util.List;

/**
 * 博客Tagmapper
 * @author songshuiyang
 * @date 2018/03/04 20:12
 */
public interface BlogTagMapper extends BaseMapper<BlogTag,Integer> {
    /**
     * 获取所有tag
     * @return
     */
    List<BlogTag> findAll();
}