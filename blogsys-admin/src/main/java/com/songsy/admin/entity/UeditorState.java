package com.songsy.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.songsy.base.BaseEntity;


/**
 * 返回ueditor需要的结果
 * UEDITOR的规则:不为SUCCESS则显示state的内容
 * 图片src属性
 * 图片title属性
 * 图片alt属性
 * @author songshuiyang
 * @date 2018/3/5 21:36
 */
@Getter
@Setter
@ToString
public class UeditorState {
    /**
     * 状态
     */
    String state;
    /**
     * 文件路径
     */
    String url;
    /**
     * 文件名称
     */
    String title;
    /**
     * 源文件名称
     */
    String original;

    public UeditorState(String state, String url, String title, String original) {
        this.state = state;
        this.url = url;
        this.title = title;
        this.original = original;
    }
}
