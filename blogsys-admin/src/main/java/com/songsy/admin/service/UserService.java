package com.songsy.admin.service;

import com.songsy.admin.dto.UserDTO;
import com.songsy.admin.entity.User;
import com.songsy.base.BaseService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Set;

/**
 * 用户信息service
 * @author songshuiyang
 * @date 2017/12/17 11:56
 */
public interface UserService extends BaseService<User,Integer> {
    /**
     * 根据username获取用户
     * @param username
     * @return
     */
    User getByUsername(String username);
    /**
     * 根据用户名获取角色code
     * @param username
     * @return
     */
    Set<String> getRoles(String username);
    /**
     * 根据用户名获取权限code
     * @param username
     * @return
     */
    Set<String> getPermissions(String username);
    /**
     * 分页查询
     * @param user
     * @return
     */
    Page<User>  findPageList(int pageNum, int pageSize, UserDTO user);
    /**
     * 检查用户名是否存在
     * @return
     */
    Boolean checkUser(String username);

    /**
     * 获取所有
     * @return
     */
    List<User> findAll();
}
