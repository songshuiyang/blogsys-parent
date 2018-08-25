package com.songsy.admin.service;

import com.songsy.admin.entity.LoginHistory;
import com.songsy.base.BaseService;

import java.util.List;

/**
 * 用户登录记录service
 * @author songshuiyang
 * @date 2018/3/4 10:12
 */
public interface LoginHistoryService extends BaseService<LoginHistory,Integer> {
    /**
     * 插入用户登入记录
     * @param loginHistory
     */
    void insertUserLoginHistory(LoginHistory loginHistory);

    /**
     * 获取地址信息不为空的数据
     * @return
     */
    List<LoginHistory> getAddressInfo();
}
