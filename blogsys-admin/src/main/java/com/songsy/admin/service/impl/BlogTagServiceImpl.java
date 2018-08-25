package com.songsy.admin.service.impl;

import com.songsy.admin.dao.BlogTagMapper;
import com.songsy.admin.entity.BlogTag;
import com.songsy.admin.service.BlogTagService;
import com.songsy.base.BaseMapper;
import com.songsy.core.base.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 博客标签service
 * @author songshuiyang
 * @date 2018/3/4 10:12
 */
@Service
public class BlogTagServiceImpl extends BaseServiceImpl<BlogTag,Integer> implements BlogTagService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogTagMapper mapper;

    @Override
    public BaseMapper<BlogTag,Integer> getMappser(){
        return mapper;
    }

    @Override
    public List<BlogTag> findAll(){
        return mapper.findAll();
    }
}
