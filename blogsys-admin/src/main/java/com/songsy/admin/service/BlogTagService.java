package com.songsy.admin.service;

import com.songsy.admin.entity.BlogTag;
import com.songsy.base.BaseService;

import java.util.List;

/**
 * 博客标签service
 * @author songshuiyang
 * @date 2018/3/4 10:12
 */
public interface BlogTagService extends BaseService<BlogTag,Integer> {

    List<BlogTag> findAll();
}
