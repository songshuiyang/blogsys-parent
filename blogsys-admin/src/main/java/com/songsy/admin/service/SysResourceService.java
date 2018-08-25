package com.songsy.admin.service;

import com.songsy.admin.entity.SysResource;
import com.songsy.base.BaseService;

import java.util.List;

/**
 * 系统资源service
 * @author songshuiyang
 * @date 2018/3/13 10:12
 */
public interface SysResourceService extends BaseService<SysResource,Integer> {
    /**
     * 获取所有资源
     * @return
     */
    List<SysResource> findAll();

    /**
     * 根据主键获取资源
     * @return
     */
    List<SysResource> findResourceByPkey(String Pkey);
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
     * 获取相册分类资源
     * @return
     */
    List<SysResource> getPhotoAlbumCategory();
}
