package com.songsy.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.songsy.base.BaseEntity;

/**
 * 阿里云oss
 * @author songshuiyang
 * @date 2018/2/11 20:07
 */
@Getter
@Setter
@ToString
public class OssFile extends BaseEntity{
    /**
     * 文件名称
     */
    private String	fileName;
    /**
     * 文件大小
     */
    private Long	fileSize;
    /**
     * 文件类型
     */
    private String	fileType;
    /**
     * 文件http路径
     */
    private String	fileSrc;
    /**
     * 附件key
     */
    private String fileKey;
    /**
     * 上传状态
     */
    private String uploadStatus;
    /**
     * 状态信息
     */
    private String uploadMessage;
}
