package com.songsy.admin.dao;

import com.songsy.admin.entity.LoginHistory;
import com.songsy.base.BaseMapper;

import java.util.List;

/**
 * 用户登录记录
 * @author songshuiyang
 * @date 2018/03/16 22:56
 */
public interface LoginHistoryMapper extends BaseMapper<LoginHistory,Integer> {
    /**
     * 获取地址信息不为空的数据
     * @return
     */
    List<LoginHistory> getAddressInfo();
}