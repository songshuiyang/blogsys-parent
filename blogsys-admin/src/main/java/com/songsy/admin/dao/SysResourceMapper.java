package com.songsy.admin.dao;

import com.songsy.admin.entity.SysResource;
import com.songsy.base.BaseMapper;

import java.util.List;

/**
 * 系统资源
 * @author songshuiyang
 * @date 2018/03/13 20:12
 */
public interface SysResourceMapper extends BaseMapper<SysResource,Integer> {
    /**
     * 获取所有资源
     * @return
     */
    List<SysResource> findAll();


    /**
     * 根据主键获取资源
     * @return
     */
    List<SysResource> findResourceByPkey(String pkey);

    /**
     * 随机取一张头像
     * @return
     */
    SysResource getRandomHeadPortrait();


    /**
     * 获取时间线资源
     * @return
     */
    List<SysResource> getTimeLine();

    /**
     * 获取时间线资源
     * @return
     */
    List<SysResource> getPhotoAlbumCategory();

}