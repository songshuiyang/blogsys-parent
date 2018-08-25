package com.songsy.admin.entity;

import com.songsy.admin.common.DictEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.songsy.base.BaseEntity;

import java.util.Date;

/**
 * 相册
 * @author songshuiyang
 * @date 2018/04/06 10:39
 */
@Getter
@Setter
@ToString
public class PhotoAlbum extends BaseEntity{
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
     *
     * 转载("转载", 0),

     原创("原创", 1),

     精图("精图", 2);
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

    /**
     * 拍摄时间 str
     */
    private String shootingTimeStr;


    private String getTypeAlias() {
        return type!=null? DictEnum.PhotoAlbum.Type.getMap().get(type):"";
    }


}