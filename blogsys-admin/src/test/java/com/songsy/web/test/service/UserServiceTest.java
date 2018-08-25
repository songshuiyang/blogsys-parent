package com.songsy.web.test.service;

import com.songsy.admin.entity.User;
import com.songsy.admin.service.UserService;
import com.songsy.web.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author songshuiyang
 * @title:
 * @description:
 * @date 2017/11/29 0:06
 */
public class UserServiceTest extends BaseTest {

    @Resource
    private UserService userService;

//    @Test
//    public void transactionManage() {
//        userService.insertUser();
//    }

    @Test
    public void test1() {
        User user = userService.getByUsername("admin");
        System.out.println(user);
    }
}
