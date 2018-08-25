package com.songsy.admin.dto;

import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 相册DTO
 * @author songshuiyang
 * @date 2018/4/6 10:49
 */
@Getter
@Setter
@ToString
public class PhotoAlbumDTO extends BaseEntity {
    /**
     * 图片标题
     */
    private String title;
    /**
     * 图片简介
     */
    private String outline;
    /**
     * 图片链接
     */
    private String url;
    /**
     * 属于哪个相册
     */
    private String category;
    /**
     * 图片类型
     */
    private Integer type;
    /**
     * 排序号
     */
    private Integer orderNum;
    /**
     * 拍摄时间
     */
    private Date shootingTime;
}
